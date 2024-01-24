package game;

import static org.junit.Assert.*
import mechanics.buffs.BuffType

import org.junit.Test

import utils.TestHelper

class TestGame extends TestHelper {
	
	@Test
	public void Game_attack_fail_because_just_summoned() {
		def c = _play("Angry Chicken")
		try {
			assert c.just_summoned == true
			Game.player_attacks(c, g.passive_player.hero)
			fail("should not be possible for ${c.name} to attack immediately")
		}
		catch (Exception e) {
			println e // ok
		}
	}

	@Test
	public void Game_attack_fail_because_same_controller() {
		def c = _play("Bluegill Warrior")
		try {
			Game.player_attacks(c, p1.hero)
			fail("should not be possible for your minion to attack your hero")
		}
		catch (Exception e) {
			println e // ok
		}
	}
	
	@Test
	public void Game_attack_no_armor() {
		def blu = _play("Bluegill Warrior")
		p2.hero.armor = 0
		_attack(blu, p2.hero)
		assert p2.hero.armor == 0
		assert p2.hero.health == 30 - blu.attack
	}

	@Test
	public void Game_attack_armor_sup_damage() {
		def blu = _play("Bluegill Warrior")
		p2.hero.armor = 3
		_attack(blu, p2.hero)
		assert p2.hero.armor == 3 - blu.attack
		assert p2.hero.health == 30
	}

	@Test
	public void Game_attack_armor_inf_damage() {
		def blu = _play("Bluegill Warrior")
		p2.hero.armor = 1
		_attack(blu, p2.hero)
		assert p2.hero.armor == 0
		assert p2.hero.health == 29
	}
	
	@Test
	public void Game_attack_fail_because_cant_attack_buff() {
		def c = _play("Ancient Watcher") // text = "Can't attack"
		try {
			c.just_summoned = false
			Game.player_attacks(c, g.passive_player.hero)
			fail("should not be possible for ${c.name} to attack because of can't attack buff")
		}
		catch (Exception e) {
			println e // ok
		}
	}

	@Test
	public void Game_attack_fail_because_hero_has_no_power() {
		try {
			Game.player_attacks(p1.hero, p2.hero)
			fail("should not be possible for ${p1.hero} to attack without a weapon")
		}
		catch (Exception e) {
			println e // ok
		}
	}

	@Test
	public void Game_attack_fail_because_frozen() {
		def c = _play("Argent Commander") // text = "Charge, Divine Shield"
		_play_and_target("Ice Lance", c)
		assert c.is_frozen()
		try {
			Game.player_attacks(c, g.passive_player.hero)
			fail("should not be possible for ${c.name} to attack because it is frozen")
		}
		catch (Exception e) {
			println e // ok
		}
	}

	@Test
	public void Game_attack_ok_because_has_charge() {
		def c = _play("Argent Commander") // text = "Charge, Divine Shield"
		assert c.just_summoned == true
		assert c.has_charge()
		try {
			def h = g.passive_player.hero.get_health()
			Game.player_attacks(c, g.passive_player.hero)
			assert g.passive_player.hero.get_health() == h - c.get_attack()
		}
		catch (Exception e) {
			println e.printStackTrace()
			fail("should be possible for ${c.name} to attack because it has charge")
		}
	}

	@Test
	public void Game_attack_ok_divine_shield() {
		def bou = _play("Boulderfist ogre")
		_next_turn()
		def arg = _play("Argent Commander") // text = "Charge, Divine Shield"
		assert arg.has_charge()
		Game.player_attacks(arg, bou)
		assert bou.health == bou.card_definition.max_health - arg.attack
		assert arg.health == arg.card_definition.max_health // because of divine shield
		assert arg.has_buff(BuffType.DIVINE_SHIELD) == false
	}

	@Test
	public void Game_attack_ok_ancestral_spirit1() {
		def blu = _play("Bluegill warrior")
		_play("Ancestral Spirit", blu)
		assert blu.has_buff(BuffType.RETURN_TO_BATTLEFIELD_WHEN_DESTROYED) == true
		_next_turn()
		def arg = _play("Argent Commander") // text = "Charge, Divine Shield"
		Game.player_attacks(arg, blu)
		// blu should come back with no buff, full health
		assert blu.get_health() == blu.card_definition.max_health
		assert blu.has_buff(BuffType.RETURN_TO_BATTLEFIELD_WHEN_DESTROYED) == false
		assert blu.get_is_in_play()
	}

	@Test
	public void Game_attack_ok_ancestral_spirit_abomination() {
		
		def abo = _play("Abomination") // 4/4
		def blu = _play("Bluegill warrior")
		_play("Ancestral Spirit", blu) // Give a minion "Deathrattle: Resummon this minion
		assert blu.has_buff(BuffType.RETURN_TO_BATTLEFIELD_WHEN_DESTROYED) == true
		_next_turn()
		
		def arg = _play("Argent Commander") // text = "Charge, Divine Shield"
		Game.player_attacks(arg, abo) // should destroy blu, which should come back
		assert abo.is_dead()
		assert arg.is_dead() // killed by abo's deathrattle after shield removal
		
		// blu killed then resurrected by RETURN_TO_BATTLEFIELD_WHEN_DESTROYED
		assert blu.get_health() == blu.card_definition.max_health
		assert blu.has_buff(BuffType.RETURN_TO_BATTLEFIELD_WHEN_DESTROYED) == false
		assert blu.get_is_in_play()
	}
	
	@Test
	public void Game_attack_fail_because_of_taunt() {
		Game.summon(p2,"Shieldbearer")
		def c = _play("Argent Commander") // text = "Charge, Divine Shield"
		assert c.just_summoned == true
		try {
			Game.player_attacks(c, g.passive_player.hero)
			fail("should have failed because of taunt")
		}
		catch (Exception e) {
			println e // ok
		}
	}
	
	@Test
	public void Game_attack_fail_because_already_attacked() {
		def blu = _play("Bluegill Warrior")
		Game.player_attacks(blu, p2.hero)
		try {
			Game.player_attacks(blu, p2.hero)
			fail("should have failed: already attacked")
		}
		catch (Exception e) {
			println e // ok
		}
	}
	
	@Test
	public void attack_reduce_durability() {
		// If you have a weapon, give it +1/+1. Otherwise equip a 1/3 weapon
		p1.hero.equip_weapon(1,1)
		Game.player_attacks(p1.hero, p2.hero)
		assert p1.hero.weapon == null // 0 durability
		p1.hero.equip_weapon(2,2)
		_next_turn()
		_next_turn()
		Game.player_attacks(p1.hero, p2.hero)
		assert p1.hero.weapon != null // 1 durability
		assert p1.hero.weapon.get_durability() == 1
		_next_turn()
		_next_turn()
		Game.player_attacks(p1.hero, p2.hero)
		assert p1.hero.weapon == null // 0 durability
	}
	
	@Test
	public void Game_init() {
		assert g != null
		assert g.players.size() == 2
		assert g.is_started == true
		assert g.is_ended == false

	}

	@Test
	public void Game_new_turn() {
		Player p = g.active_player
		assert p.available_mana == 1
		assert p.max_mana == 1
		def c = Game.summon(g.passive_player, "Demolisher")
		assert c != null
		def life = g.active_player.hero.get_health()
		g.next_turn() // new active player should receive 2 damages
		assert g.passive_player.hero.get_health() == life-2
	}

	@Test
	public void Game_play_minion_location() {
		Card c1 = Game.new_card("Abomination")
		Card c2 = Game.new_card("Amani Berserker")
		Card c3 = Game.new_card("Abomination")
		Game.summon(p1, c1)
		assert c1.place == 0
		Game.summon(p1, c2)
		assert c2.place == 1
		Game.summon(p1, c3, 1)
		assert c1.place == 0
		assert c2.place == 2
		assert c3.place == 1
	}
	
	@Test
	public void Game_play_fail_not_enough_mana() {
		try {
			p1.play(Game.new_card("Abomination"))
			fail("should have failed: not enough mana")
		}
		catch( Exception e ) {
			println e // ok
		}
	}
	
	@Test
	public void Game_play_minion_success() {
		Card c = Game.new_card("Abomination")
		Game.summon(p1, c)
		assert p1.minions.contains(c)
		assert c.place == 0
		assert c.just_summoned == true
		assert c.has_buff(BuffType.TAUNT) == true
	}

	@Test
	public void Game_fake_random() {
		// multiple picks -> list
		g.random.forced_ints = [1, 2]
		assert Game.random_pick(2, ["a","b","c","d"]) == ["b","d"]
		// single pick -> item
		g.random.forced_ints = [3]
		assert Game.random_pick(["a","b","c","d"]) == "d"
	}
	
	@Test
	public void hero_dies() {
		assert g.is_ended == false
		p2.hero.health = 2
		def blu = _play("Bluegill Warrior")
		_attack(blu, p2.hero)
		assert p2.hero.is_dead()
		assert g.is_ended
	}

	@Test
	public void fatigue() {
		p2.deck.cards.clear()
		_next_turn() // should trigger a fatigue
		assert p1.hero.health == 29	
	}

}


