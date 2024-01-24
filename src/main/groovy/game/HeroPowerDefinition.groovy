package game


class HeroPowerDefinition extends DefinitionObject {
	
	int cost	
	
	HeroPowerDefinition(name, text) {
		this.name = name
		this.text = text
		this.cost = 2
	}	
}

class LesserHeal extends HeroPowerDefinition {
	static instance = new LesserHeal()
	private LesserHeal() {
		super( 'Lesser Heal', 'Restore 2 Health')
		get_targets = [ { all_targets } ]
		when_this_power_is_used(text) {
			your_hero.restore_health(2, select_target(all_targets))
		}
	}
}

class LifeTap extends HeroPowerDefinition {
	static instance = new LifeTap()
	private LifeTap() {
		super( 'Life Tap', 'Draw a card and take 2 damage')
		when_this_power_is_used(text) {
			you.draw(1)
			your_hero.receive_damage(2)
		}
	}
}

class MindSpike  extends HeroPowerDefinition {
	static instance = new MindSpike()
	private MindSpike() {
		super( 'Mind Spike', 'Deal 2 damage')
		get_targets = [ { all_targets } ]
		when_this_power_is_used(text) {
			your_hero.deal_power_damage(2, select_target(all_targets))
		}
	}
} 

class MindShatter  extends HeroPowerDefinition {
	static instance = new MindShatter()
	private MindShatter() {
		super( 'Mind Shatter', 'Deal 3 damage')
		get_targets = [ { all_targets } ]
		when_this_power_is_used(text) {
			your_hero.deal_power_damage(3, select_target(all_targets))
		}
	}
} 

class DaggerMastery extends HeroPowerDefinition {
	static instance = new DaggerMastery()
	private DaggerMastery() {
		super( 'Dagger Mastery', 'Equip a 1/2 Dagger')
		when_this_power_is_used(text) {
			your_hero.equip_weapon("Wicked Knife")
		}
	}
}

class Fireblast extends HeroPowerDefinition {
	static instance = new Fireblast()
	private Fireblast() {
		super( 'Fireblast', 'Deal 1 damage')
		get_targets = [ { all_targets } ]
		when_this_power_is_used(text) {
			your_hero.deal_power_damage(1, select_target(all_targets - your_hero))
		}
	}
}

class ArmorUp extends HeroPowerDefinition {
	static instance = new ArmorUp()
	private ArmorUp() {
		super( 'Armor Up!', 'Gain 2 armor')
		when_this_power_is_used(text) {
			your_hero.add_armor(2)
		}
	}
}

class Reinforce extends HeroPowerDefinition {
	static instance = new Reinforce()
	private Reinforce() {
		super( 'Reinforce', 'Summon a 1/1 Silver Hand Recruit')
		when_this_power_is_used(text) {
			Game.summon(you, "Silver Hand Recruit")
		}
	}
}

class Shapeshift extends HeroPowerDefinition {
	static instance = new Shapeshift()
	private Shapeshift() {
		super( 'Shapeshift', '+1 Attack this turn, +1 Armor' )
		when_this_power_is_used(text) {
			your_hero.add_armor(1)
			your_hero.gain('+1 Attack').until_end_of_turn()
		}
	}
}

class TotemicCall extends HeroPowerDefinition {
	static instance = new TotemicCall()
	static power_totems = [ "Healing Totem", "Wrath of Air Totem", "Searing Totem", "Stoneclaw Totem" ]
	private TotemicCall() {
		super("Totemic Call", "Summon a random totem")		
		before_use_power(text) {
			// check that there is a random totem to summon
			List<Card> in_play_totems = your_minions.findAll {it.creature_type == "totem"}
			if (in_play_totems*.name.containsAll(power_totems)) {
				throw new IllegalActionException("all totems have already been summoned")
			} 
		}
		when_this_power_is_used(text) {
			def possible_totems = power_totems - your_minions.findAll {it.creature_type == "totem"}
			Game.summon(you, random_pick(possible_totems))
		}
	}
}

class SteadyShot extends HeroPowerDefinition {
	static instance = new SteadyShot()
	private SteadyShot() {
		super( 'Steady Shot', 'Deal 2 damage to the enemy hero' )
		when_this_power_is_used(text) {
			your_hero.deal_power_damage(2, opponent_hero)
		}
	}
}

class Inferno extends HeroPowerDefinition {
	static instance = new Inferno()
	private Inferno() {
		super( 'Inferno', 'Summon a 6/6 Infernal')
		when_this_power_is_used(text) {
			Game.summon(you, "Infernal")
		}
	}
}
