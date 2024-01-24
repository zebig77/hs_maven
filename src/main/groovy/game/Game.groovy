package game

import mechanics.buffs.BuffType
import mechanics.events.AnyCharacterAttacks
import mechanics.events.AnyHeroTakesDamage
import mechanics.events.AnyMinionDealsDamage
import mechanics.events.AnyMinionIsSummoned
import mechanics.events.AnyMinionTakesDamage
import mechanics.events.AnyTurnEnds
import mechanics.events.AnyTurnStarts
import mechanics.events.Event
import mechanics.events.ItAttacks
import mechanics.events.ItComesInPlay
import mechanics.events.ItDealsDamage
import mechanics.events.ItIsAttacked
import mechanics.events.ItTakesDamage
import mechanics.events.ItsControllerTurnEnds
import mechanics.events.ItsControllerTurnStarts
import mechanics.events.ItsDurabilityIsReduced
import state.MapState
import utils.RandomGenerator
import autoplay.PlayerAction
import logger.Log

class Game extends GameObject {

	static Game current
	RandomGenerator random
	
	MapState ps = new MapState()
	
	List<Player> players = []
	Stack<Event> events = new Stack<Event>()
	
	Game(long random_seed=System.currentTimeMillis()) {	
		current = this
		random = new RandomGenerator(random_seed)
		next_id = 100 // 1-99 reserved for heroes and deck cards
		play_id = 1
		is_started = false
		is_ended = false
		turn_timeout = 90
		feugen_died = false
		stalagg_died = false
	}
	
	Game(String p1_name, Class p1_hero, Class p1_deck, String p2_name, Class p2_hero, Class p2_deck) {
		this()
		players.add( new Player( p1_name, p1_hero.newInstance(), p1_deck.newInstance() ) )
		players.add( new Player( p2_name, p2_hero.newInstance(), p2_deck.newInstance() ) )
	}
	
	boolean getFeugen_died() { ps.feugen_died }
	void setFeugen_died(boolean b) { ps.feugen_died = b }
	
	boolean getStalagg_died() { ps.stalagg_died }
	void setStalagg_died(boolean b) { ps.stalagg_died = b }

	int getNext_id() { ps.next_id }
	void setNext_id(int ni) { ps.next_id = ni }
	
	int getPlay_id() { ps.play_id }
	void setPlay_id(int pi) { ps.play_id = pi }
	
	boolean getIs_started() { ps.is_started }
	void setIs_started(boolean is) { ps.is_started = is }

	boolean getIs_ended() { ps.is_ended }
	void setIs_ended(boolean ie) { ps.is_ended = ie }
	
	int getTurn_timeout() { ps.turn_timeout }
	void setTurn_timeout(int tt) { 
		ps.turn_timeout = tt }
	
	Player getActive_player() { ps.active_player }
	void setActive_player(Player p) { ps.active_player = p }
	 
	Player getPassive_player() { ps.passive_player }
	void setPassive_player(Player p) { ps.passive_player = p }
	 

	static check(boolean check_result, String error_message) {
		if (check_result == false) {
			throw new IllegalActionException("check failed: $error_message")
		}
	}
	
	static check_end_of_game() {
		if (!current.players[0].hero.is_dead() && !current.players[1].hero.is_dead()) {
			return // not ended
		}
		current.is_ended = true
		Log.info ""
		def win_message
		if (current.players[0].hero.is_dead() && current.players[1].hero.is_dead()) {
			win_message = "!!! both heroes are dead: it is a draw !!!"
		}
		else if (current.players[1].hero.is_dead()) {
			win_message = "!!! ${current.players[0]} wins !!!"
		}
		else {
			win_message = "!!! ${current.players[1]} wins !!!"
		}
		println '!'*win_message.size()
		println win_message
		println '!'*win_message.size()
		current.end_game()
	}
	
	def end_game() {
		Log.info "---- end of game"
	}
	
	def end_turn() {
		Log.info "\n - $active_player ends its turn"
		// kill minions scheduled to die at end of turn
		new ItsControllerTurnEnds(active_player).check()
		new AnyTurnEnds().check()
		active_player.minions().findAll{ it.has_buff(BuffType.DIE_AT_THE_END_OF_TURN) }.each { 
			Log.info "   - executing '${BuffType.DIE_AT_THE_END_OF_TURN}' for $it"
			(it as Card).dies()
		}
		// remove freeze buff for minions who have not attacked
		(active_player.minions() + active_player.hero).findAll{ it.is_frozen() }.each { Target t ->
			if (t.attack_counter == 0) {
				t.remove_all_buff(BuffType.FROZEN)
			}
		}
		// swap active and opponent
		def x = current.active_player
		active_player = passive_player
		passive_player = x
	}
	
	static int get_random_int(int bound) {
		return Game.current.random.get_random_int(bound)
	}

	static Player opponent_of(Player p) {
		return p == current.active_player ? current.passive_player : current.active_player
	}
	
	static Event getCurrent_event() {
		if (current.events.isEmpty()) {
			throw new InvalidDefinitionException( "no current event" )
		}
		return current.events.peek()
	}

	void play_turn() {
		Log.info "\n---- ${active_player}'s turn begins"
		turn_timeout = 90 // default timeout, can be changed by Nozdormu
		new AnyTurnStarts().check()
		active_player.init_turn()
		new ItsControllerTurnStarts(active_player).check()
		active_player.draw(1)
		// check for corruption: "Choose an enemy minion. At the start of your turn, destroy it"
		passive_player.minions().each {
			if (it.has_buff(BuffType.CORRUPTION)) {
				Log.info "      . corruption kills $it"
				it.dies()
			}
		}
		// check for "destroy all minions at the start of your turn"
		active_player.minions().each {
			if (it.has_buff(BuffType.DESTROY_ALL_MINIONS_AT_START_OF_TURN)) {
				Log.info "   - destroying all minions"
				active_player.minions()*.dies()
				passive_player.minions()*.dies()
			}
		}
	}
	
	static remove_deads_from_battlefield() {
		current.active_player.minions().each {
			if (it.get_health() <= 0) {
				it.dies()
			}
		}
		current.passive_player.minions().each {
			if (it.get_health() <= 0) {
				it.dies()
			}
		}
	}
	
	static fight( Target attacker, Target attacked ) {
		
		Log.info "   - fight begins between $attacker and $attacked"
		Log.info "      . $attacker is ${attacker.get_attack()}/${attacker.get_health()}"
		Log.info "      . $attacked is ${attacked.get_attack()}/${attacked.get_health()}"
		
		attacker.attack_counter++
		attacker.is_attacking = true
		
		// check what happens when someone attacks
		new ItAttacks(attacker).check()
		AnyCharacterAttacks e = new AnyCharacterAttacks(attacker, attacked).check()
		if (attacker.is_a_minion() && !attacker.get_is_in_play()) {
			Log.info "      . $attacker is no longer on the battlefield -> attack cancelled "
			return
		}
		if (e.changed_attacked != null) {
			Log.info "     . $attacked is replaced by $e.changed_attacked"
			attacked = e.changed_attacked
		}
		
		// check what happens when someone is attacked
		new ItIsAttacked(attacked).check()
		
		// damage is dealt and taken at the same time for the attacker and the attacked
		// triggered events are checked later
		
		// check if attacked takes damage and compute how much
		def attacker_damage = attacker.get_attack()
		def attacked_health_loss = 0
		if (attacker_damage > 0)  {
			if (attacked.has_buff(BuffType.IMMUNE) == false) {
				if (attacked.has_buff(BuffType.DIVINE_SHIELD)) {
					Log.info "      . $attacked loses its divine shield and takes no damage"
					attacked.remove_all_buff(BuffType.DIVINE_SHIELD)
				} 
				else { // no divine shield
					attacked_health_loss = attacked.receive_combat_damage(attacker_damage)
				}
			}
			else { // immune
				Log.info "      . $attacked receives no damage because it is immune"
			}
		}
		
		// check if attacker takes damage and compute how much
		def attacked_damage = attacked.get_attack()
		def attacker_health_loss = 0
		if (attacked_damage > 0)  {
			if (attacker.has_buff(BuffType.IMMUNE) == false) {
				if (attacker.has_buff(BuffType.DIVINE_SHIELD)) {
					Log.info "      . $attacker loses its divine shield and takes no damage"
					attacker.remove_all_buff(BuffType.DIVINE_SHIELD)
				} 
				else { // no divine shield
					attacker_health_loss = attacker.receive_combat_damage(attacked_damage)
				}
			}
			else { // immune
				Log.info "      . $attacker receives no damage because it is immune"
			}
		}
		
		// check enrage status
		attacker.check_enrage_status()
		attacked.check_enrage_status()
		
		// attacker 'deal damage' events
		if (attacked_health_loss > 0) {
			new ItDealsDamage(attacker, attacked).check()
			if (attacker.is_a_minion()) {
				new AnyMinionDealsDamage(attacker, attacked).check()
			}
		}
		
		// attacked 'deal damage' events
		if (attacker_health_loss > 0) {
			new ItDealsDamage(attacked, attacker).check()
			if (attacked.is_a_minion()) {
				new AnyMinionDealsDamage(attacked, attacker).check()
			}
		}

		// attacked 'take damage' events		
		if (attacked_health_loss > 0) {
			Log.info "      . checking 'take damage' events for $attacked"
			if (attacked.is_a_minion()) {
				new AnyMinionTakesDamage(attacked).check()
			}
			if (attacked.is_a_hero()) {
				new AnyHeroTakesDamage(attacked, attacked_health_loss).check()
			}
			new ItTakesDamage(attacked).check()
		}
		
		// attacker 'take damage' events		
		if (attacker_health_loss > 0) {
			Log.info "      . checking 'take damage' events for $attacker"
			if (attacker.is_a_minion()) {
				new AnyMinionTakesDamage(attacker).check()
			}
			if (attacker.is_a_hero()) {
				new AnyHeroTakesDamage(attacker, attacker_health_loss).check()
			}
			new ItTakesDamage(attacker).check()
		}
		
		attacker.is_attacking = false		
		
		// check if attacker dies
		if (attacker.get_health() <= 0 && !attacker.is_destroyed) {
			attacker.dies()
		}
		
		// check if attacked dies
		if (attacked.get_health() <= 0 && !attacked.is_destroyed) {
			attacked.dies()
		}
		
	}	

	static player_attacks(Target attacker, Target attacked) {
		if (attacker.controller == attacked.controller) {
			throw new IllegalActionException("$attacker and $attacked have the same controller")
		}
		Log.info "\n- ${Game.current.active_player} orders $attacker to attack $attacked"
		attacker.check_can_attack()
		attacked.check_can_be_attacked()
		/*
		 * if a defender has taunt, it must be attacked
		 */
		def l = current.passive_player.minions.findAll{ it.has_buff(BuffType.TAUNT) }
		if (! l.isEmpty() && ! l.contains(attacked)) {
			throw new IllegalActionException("you must attack a minion with taunt")
		}
		// if attack has stealth, remove it
		if (attacker.has_buff(BuffType.STEALTH)) {
			attacker.remove_all_buff(BuffType.STEALTH)
		}
		
		// attacker and defender deals their damage at the same time
		fight( attacker, attacked )
		
		// reduce weapon durability if player has attacked with hero / weapon
		if (attacker.is_a_hero()) {
			Hero hero = attacker
			if (hero.weapon != null) {
				ItsDurabilityIsReduced e = new ItsDurabilityIsReduced(hero.weapon, attacked).check()
				if (e.stop_action == false) {
					hero.weapon.add_durability(-1)
				}
			}
		}
		
		Game.check_end_of_game()
	}

	static Card summon( Player p, Card c, int place ) {
		if (place >= 7) {
			// no room for it
			Log.info "      . $c is destroyed because there is no place in battlefield"
			c.dies()
		}
		Log.info "      . $c is put in play for $p at x=$place"
		
		// set the play order
		c.play_order = Game.current.play_id++
		
		// set the controller and place in battlefield
		p.controls(c, place)
		
		// signal the minion as summoned
		c.just_summoned = true
		c.is_destroyed = false
		new AnyMinionIsSummoned(c).check()
		
		if (! c.is_in_play) {
			// perform the 'come in play' actions only the first time
			new ItComesInPlay(c).check()
			c.is_in_play = true
		}
		return c
	}

	def possible_actions() {
		List<PlayerAction> pa = []
		// playable cards
		active_player.hand.cards.each { Card c ->
			pa += PlayerAction.possiblePlayCardActions(c)
		}
		// minion attacks
		active_player.minions.each { Card c ->
			pa += PlayerAction.possibleAttackActions(c)
		}
		// hero attack
		pa += PlayerAction.possibleAttackActions(active_player.hero)
		// use power
		pa += PlayerAction.possibleUsePowerActions(active_player.hero)
		return pa
	}

	static Card summon( Player p, Card c ) {
		if (c == null) {
			return
		}
		return summon( p, c, p.minions.size() )
	}

	static Card summon( Player p, String card_name ) {
		if (card_name == null) {
			return
		}
		return summon( p, new_card(card_name) )
	}

	static Card summon( Player p, String card_name, int place ) {
		if (card_name == null) {
			return
		}
		return summon( p, new_card(card_name), place )
	}
	
	def next_turn() {
		end_turn()
		play_turn()
	}

	static Card new_card(String card_name) {
		return CardLibrary.new_card(card_name)
	}
	
	void start() {
		players.each{ Player p ->
			p.deck.cards.shuffle()
			p.available_mana = 0
			p.max_mana = 0
		}
		active_player = players[ random.get_random_int(2) ]
		passive_player = players.find{ it != active_player }
		Log.info """\
---- Starting game, 
- $active_player plays first with ${active_player.hero}
- $passive_player plays second with ${passive_player.hero}"""
		active_player.draw(3)
		passive_player.draw(4)
		// TODO: each player can discard any number of cards and have them replaced
		passive_player.hand.add( new_card("The Coin") )
		is_started = true
		play_turn()
	}

	// single pick
	static random_pick(List choices) {
		if (choices.size() == 0) {
			return null
		}
		if (choices.size() == 1) {
			return choices.getAt(0)
		}
		return choices.getAt(current.random.get_random_int(choices.size()))
	}

	// multiple picks
	static List random_pick(int amount, List choices) {
		def result = []
		amount.times {
			result += random_pick(choices - result)
		}
		return result
	}
	
	String toString() { "game" }
	
}
