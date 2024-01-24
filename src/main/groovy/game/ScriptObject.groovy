package game

import mechanics.Trigger
import mechanics.buffs.Buff
import mechanics.buffs.BuffType
import mechanics.events.AnyAttackIsEvaluated
import mechanics.events.AnyBuffIsEvaluated
import mechanics.events.AnyBuffListIsEvaluated
import mechanics.events.AnyCharacterAttacks
import mechanics.events.AnyCharacterIsHealed
import mechanics.events.AnyCostIsEvaluated
import mechanics.events.AnyHealthIsEvaluated
import mechanics.events.AnyHeroTakesDamage
import mechanics.events.AnyMinionDealsDamage
import mechanics.events.AnyMinionDies
import mechanics.events.AnyMinionIsPlayed
import mechanics.events.AnyMinionIsSummoned
import mechanics.events.AnyMinionTakesDamage
import mechanics.events.AnyPowerDamageIsEvaluated
import mechanics.events.AnyPowerHealingIsEvaluated
import mechanics.events.AnySecretIsRevealed
import mechanics.events.AnySpellDamageIsEvaluated
import mechanics.events.AnySpellHealingIsEvaluated
import mechanics.events.AnySpellIsPlayed
import mechanics.events.AnyTurnEnds
import mechanics.events.AnyTurnStarts
import mechanics.events.BeforeItsControllerPlaysACard
import mechanics.events.BeforePlay
import mechanics.events.BeforeUsePower
import mechanics.events.EnragedOff
import mechanics.events.EnragedOn
import mechanics.events.ItAttacks
import mechanics.events.ItComesInPlay
import mechanics.events.ItDealsDamage
import mechanics.events.ItIsAttacked
import mechanics.events.ItIsDestroyed
import mechanics.events.ItIsPlayed
import mechanics.events.ItTakesDamage
import mechanics.events.ItsControllerHeals
import mechanics.events.ItsControllerPlaysACard
import mechanics.events.ItsControllerTurnEnds
import mechanics.events.ItsControllerTurnStarts
import mechanics.events.ItsCostIsEvaluated
import mechanics.events.ItsDurabilityIsReduced
import mechanics.events.SpellTargetSelected
import mechanics.events.ThisPowerIsUsed


abstract class ScriptObject {
	
	abstract Trigger add_trigger(Class event_class, Closure c)
	abstract Trigger add_trigger(Class event_class, Closure c, String comment)
	
	int getAttack_increase() {
		return Game.current_event.attack_increase
	}

	int getCost_increase() {
		return Game.current_event.cost_increase
	}

	int getCost_change() {
		(Game.current_event as AnyCostIsEvaluated).cost_change
	}

	void setCost_change(int value) {
		println "      . cost change = $value"
		(Game.current_event as AnyCostIsEvaluated).cost_change = value
	}

	void setLowest_cost(int value) {
		println "      . lowest cost = $value"
		(Game.current_event as AnyCostIsEvaluated).lowest_cost = value
	}

	void setChosen_target(Target t) {
		println "      . chosen target = $t"
		Game.current_event.choice = t
	}
	
	int getHealth_increase() {
		return Game.current_event.health_increase
	}

	int getSpell_damage_increase() {
		return (Game.current_event as AnySpellDamageIsEvaluated).spell_damage_increase
	}
	
	int getPower_damage_increase() {
		return (Game.current_event as AnyPowerDamageIsEvaluated).power_damage_increase
	}
	
	int getSpell_healing_increase() {
		return (Game.current_event as AnySpellHealingIsEvaluated).spell_healing_increase
	}
	
	int getPower_healing_increase() {
		return (Game.current_event as AnyPowerHealingIsEvaluated).power_healing_increase
	}
	
	int getSpell_damage_amount() {
		return (Game.current_event as AnySpellDamageIsEvaluated).spell_damage_amount
	}
	
	int getPower_damage_amount() {
		return (Game.current_event as AnyPowerDamageIsEvaluated).power_damage_amount
	}
	
	int getSpell_healing_amount() {
		return (Game.current_event as AnySpellHealingIsEvaluated).spell_healing_amount
	}
	
	int getPower_healing_amount() {
		return (Game.current_event as AnyPowerHealingIsEvaluated).power_healing_amount
	}
	
	void setAttack_increase(int value) {
		println "      . attack_increase = $value"
		Game.current_event.attack_increase = value
	}
	
	void setAttack_change(int value) {
		println "      . attack_change = $value"
		Game.current_event.attack_change = value
	}
	
	void setCost_increase(int value) {
		println "      . cost_increase = $value"
		Game.current_event.cost_increase = value
	}
	
	BuffType getThat_buff_type() {
		return (Game.current_event as AnyBuffIsEvaluated).buff_type
	}
	/*
	 * 
	 */
	
	Card select_card(List<Card> choices) { you.select_card(choices) }
	Target select_target(List<Target> choices) { you.select_target(choices) }
	Target select_spell_target(List<Target> choices) { you.select_spell_target(choices) }

	void setHas_buff(boolean value) {
		(Game.current_event as AnyBuffIsEvaluated).has_buff = value
	}

	void setHealth_increase(int value) {
		println "      . health increase = $value"
		Game.current_event.health_increase = value
	}

	void setSpell_damage_increase(int value) {
		println "      . spell damage increase = $value"
		(Game.current_event as AnySpellDamageIsEvaluated).spell_damage_increase = value
	}

	void setSpell_healing_increase(int value) {
		println "      . spell healing increase = $value"
		(Game.current_event as AnySpellHealingIsEvaluated).spell_healing_increase = value
	}

	void setPower_damage_increase(int value) {
		println "      . power damage increase = $value"
		(Game.current_event as AnyPowerDamageIsEvaluated).power_damage_increase = value
	}

	void setPower_healing_increase(int value) {
		println "      . power healing increase = $value"
		(Game.current_event as AnyPowerHealingIsEvaluated).power_healing_increase = value
	}

	static List<Target> getAll_characters() {
		return [your_hero, opponent_hero ]+ your_minions + enemy_minions
	}

	static List<Target> getActive_secrets() {
		return opponent.secrets.storage
	}

	static List<Card> getAll_minion_targets() {
		return all_minions.findAll{ it.can_be_targeted() }
	}

	static List<Card> getAll_minions() {
		return your_minions + enemy_minions
	}

	static List<Target> getAll_targets() {
		return all_characters.findAll{ it.can_be_targeted() }
	}
	
	static Target getAttacked() {
		return (Game.current_event as AnyCharacterAttacks).attacked
	}

	static Target getAttacker() {
		return (Game.current_event as AnyCharacterAttacks).attacker
	}
	
	static void setChanged_attacked(Target t) {
		(Game.current_event as AnyCharacterAttacks).changed_attacked = t
	}

	static Target getDamaged_target() {
		return Game.current_event.target
	}

	static int getDamage_amount() {
		return Game.current_event.amount
	}

	static List<Card> getEnemy_minion_targets() {
		return Game.current.passive_player.minions().findAll{ it.can_be_targeted() }
	}

	static List<Card> getEnemy_minions() {
		return Game.current.passive_player.minions()
	}

	static int getHeal_amount() {
		return Game.current_event.heal_amount
	}

	static Target getHealed() {
		return Game.current_event.healed
	}

	static Target getHealer() {
		return Game.current_event.healer
	}

	static List<Buff> getThat_buff_list() {
		return Game.current_event.additional_buffs
	}

	static Card getThat_card() {
		return Game.current_event.target
	}

	static Target getThat_character() {
		return Game.current_event.target
	}

	static Card getThat_minion() {
		assert Game.current_event.target.is_a_minion()
		return Game.current_event.target
	}

	static Player getThat_player() {
		return Game.current_event.player
	}

	static Card getThat_secret() {
		return Game.current_event.target
	}

	static Card getThat_spell() {
		return Game.current_event.target
	}

	static Target getThat_target() {
		return Game.current_event.target
	}

	static Weapon getThat_weapon() {
		return Game.current_event.target
	}
	
	static Target getThe_attacked() {
		return (Game.current_event as ItsDurabilityIsReduced).attacked
	}

	static Card getThis_card() {
		return Game.current_event.origin
	}

	static Card getThis_minion() {
		return Game.current_event.origin
	}
	
	static Player opponent_of(Player p) {
		return Game.opponent_of(p)
	}
	
	static Card random_card(List<Card> choices) {
		return Game.random_pick(choices)
	}

	static Object random_pick(List choices) {
		return Game.random_pick(choices)
	}

	static Object random_pick(int count, List choices) {
		return Game.random_pick(count, choices)
	}

	static Card getThis_spell() {
		return Game.current_event.origin
	}

	static Target getThis_target() {
		return Game.current_event.origin
	}

	static Weapon getThis_weapon() {
		return Game.current_event.origin
	}

	static Player getOpponent() {
		return Game.current.passive_player
	}

	static Hero getOpponent_hero() {
		return Game.current.passive_player.hero
	}

	static Player getYou() {
		return Game.current.active_player
	}

	static Hero getYour_hero() {
		return Game.current.active_player.hero
	}

	static List<Card> getYour_minion_targets() {
		return Game.current.active_player.minions().findAll{ it.can_be_targeted() }
	}

	static List<Card> getYour_minions() {
		return Game.current.active_player.minions()
	}

	static setStop_action(boolean value) {
		println "      . setting stop_action = $value for event '${Game.current_event}'"
		Game.current_event.stop_action = value
	}
	
	Trigger before_play(String comment='', Closure c) {
		return add_trigger( BeforePlay.class, c, "before play: $comment" )
	}
	
	Trigger before_use_power(String comment='', Closure c) {
		return add_trigger( BeforeUsePower.class, c, "before using power: $comment" )
	}
	
	Trigger when_coming_in_play(String comment='', Closure c) {
		return add_trigger( ItComesInPlay.class, c, "when coming in play: $comment" )
	}
	
	Trigger when_a_buff_list_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnyBuffListIsEvaluated.class, c, "when a buff list is evaluated: $comment" )
	}
	
	Trigger when_a_buff_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnyBuffIsEvaluated.class, c, "when a buff is evaluated: $comment" )
	}
	
	Trigger when_a_character_is_healed(String comment='', Closure c) {
		return add_trigger( AnyCharacterIsHealed.class, c, "when a character is healed: $comment" )
	}
	
	Trigger when_a_minion_dies(String comment='', Closure c) {
		return add_trigger( AnyMinionDies.class, c, "when a minion dies: $comment" )
	}
	
	Trigger when_a_minion_takes_damage(String comment='', Closure c) {
		return add_trigger( AnyMinionTakesDamage.class, c, "when a minion takes damage: $comment" )
	}
	
	Trigger when_a_secret_is_revealed(String comment='', Closure c) {
		return add_trigger( AnySecretIsRevealed.class, c, "when a secret is revealed: $comment" )
	}
	
	Trigger when_attack_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnyAttackIsEvaluated.class, c, "when <attack> is evaluated: $comment")
	}
	
	Trigger when_its_cost_is_evaluated(String comment='', Closure c) {
		return add_trigger( ItsCostIsEvaluated.class, c, "when its cost is evaluated: $comment" )
	}

	Trigger when_a_cost_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnyCostIsEvaluated.class, c, "when a cost is evaluated: $comment" )
	}
	
	Trigger when_enraged_no_more(String comment='', Closure c) {
		return add_trigger( EnragedOff.class, c, "when enraged no more: $comment" )
	}
	
	Trigger when_enraged(String comment='', Closure c) {
		return add_trigger( EnragedOn.class, c, "when enraged: $comment" )
	}
	
	Trigger when_health_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnyHealthIsEvaluated.class, c, "when <Health> is evaluated: $comment" )
	}
	
	Trigger when_it_attacks(String comment='', Closure c) {
		return add_trigger( ItAttacks.class, c, "when it attacks: $comment" )
	}
	
	Trigger when_it_is_attacked(String comment='', Closure c) {
		return add_trigger( ItIsAttacked.class, c, "when it is attacked: $comment" )
	}
	
	Trigger when_a_hero_takes_damage(String comment='', Closure c) {
		return add_trigger( AnyHeroTakesDamage.class, c, "when a hero takes damage: $comment" )
	}
	
	Trigger when_a_character_attacks(String comment='', Closure c) {
		return add_trigger( AnyCharacterAttacks.class, c, comment )
	}
	
	Trigger when_a_minion_deals_damage(String comment='', Closure c) {
		return add_trigger( AnyMinionDealsDamage.class, c, comment )
	}
	
	Trigger when_it_deals_damage(String comment='', Closure c) {
		return add_trigger( ItDealsDamage.class, c, comment )
	}
	
	Trigger when_it_is_destroyed(String comment='', Closure c) {
		return add_trigger( ItIsDestroyed.class, c, comment )
	}
	
	Trigger when_played(String comment = null, Closure c) {
		return add_trigger( ItIsPlayed.class, c, "when played: $comment" )
	}
	
	Trigger when_its_controller_heals(String comment = null, Closure c) {
		return add_trigger( ItsControllerHeals.class, c, comment )
	}
	
	Trigger before_its_controller_plays_a_card(String comment='', Closure c) {
		return add_trigger( BeforeItsControllerPlaysACard.class, c, comment )
	}
	
	Trigger when_its_controller_plays_a_card(String comment='', Closure c) {
		return add_trigger( ItsControllerPlaysACard.class, c, comment )
	}
	
	Trigger when_its_controller_turn_ends(String comment='', Closure c) {
		return add_trigger( ItsControllerTurnEnds.class, c, comment )
	}
	
	Trigger when_its_controller_turn_starts(String comment='', Closure c) {
		return add_trigger( ItsControllerTurnStarts.class, c, comment )
	}
	
	Trigger when_a_turn_ends(String comment='', Closure c) {
		return add_trigger( AnyTurnEnds.class, c, comment )
	}
	
	Trigger when_a_turn_starts(String comment='', Closure c) {
		return add_trigger( AnyTurnStarts.class, c, comment )
	}
	
	Trigger when_its_durability_is_reduced(String comment='', Closure c) {
		return add_trigger( ItsDurabilityIsReduced.class, c, comment )
	}
	
	Trigger when_this_power_is_used(String comment='', Closure c) {
		return add_trigger( ThisPowerIsUsed.class, c, comment )
	}
	
	Trigger when_it_takes_damage(String comment='', Closure c) {
		return add_trigger( ItTakesDamage.class, c, comment )
	}
	
	Trigger when_a_spell_target_is_selected(String comment='', Closure c) {
		return add_trigger( SpellTargetSelected.class, c, comment )
	}
	
	Trigger when_a_minion_is_played(String comment='', Closure c) {
		return add_trigger( AnyMinionIsPlayed.class, c, comment )
	}

	Trigger when_a_minion_is_summoned(String comment='', Closure c) {
		return add_trigger( AnyMinionIsSummoned.class, c, comment )
	}

	Trigger when_a_spell_is_played(String comment='', Closure c) {
		return add_trigger( AnySpellIsPlayed.class, c, comment )
	}

	Trigger when_spell_damage_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnySpellDamageIsEvaluated.class, c, comment )
	}

	Trigger when_power_damage_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnyPowerDamageIsEvaluated.class, c, comment )
	}

	Trigger when_spell_healing_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnySpellHealingIsEvaluated.class, c, comment )
	}

	Trigger when_power_healing_is_evaluated(String comment='', Closure c) {
		return add_trigger( AnyPowerHealingIsEvaluated.class, c, comment )
	}

}
