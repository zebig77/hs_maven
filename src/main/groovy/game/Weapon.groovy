package game

import mechanics.Trigger
import mechanics.events.ItIsDestroyed


class Weapon extends Target {
	
	Weapon(CardDefinition weapon_definition) {
		super( weapon_definition.name, 'weapon', weapon_definition.max_health )
		attack = weapon_definition.attack
		durability = weapon_definition.max_health 
		triggers.clear()
		weapon_definition.triggers.each{ t ->
			triggers.add( new Trigger(t.event_class, t.script, this) )
		}
	}
	
	int getDurability() { ps.durability }
	void setDurability(int d) { ps.durability = d }
	
	boolean is_a_weapon() {
		return true
	}
	
	void add_attack(int value) {
		attack += value
		println "      . ${controller.hero}'s weapon attack is now ${get_attack()}"
	}
	
	void add_durability(int value) {
		durability += value
		println "      . ${controller.hero}'s weapon durability is now ${get_durability()}"
		if (get_durability() <= 0) {
			demolish()
		}
	}
	
	int get_durability() {
		return evaluate_health(durability)
	}
	
	void demolish() {
		new ItIsDestroyed(this).check()
		controller.hero.weapon = null
		println "      . ${controller.hero}'s weapon destroyed"
	}
	
}
