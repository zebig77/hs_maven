package game

import mechanics.events.BeforeUsePower
import mechanics.events.ItComesInPlay

class Hero extends Target {
	
	Hero(String hero_name, HeroPowerDefinition power_definition) {
		super(hero_name, "hero", 30)
		this.controller = null // will be set later
		this.power = new HeroPower(power_definition)
		this.target_type = 'hero'
		this.armor = 0
		this.weapon = null
		this.is_a_demon = false // except Jaraxxus...
	}
	
	HeroPower getPower() { ps.power }
	void setPower(HeroPower hp) { ps.power = hp }
	
	int getArmor() { ps.armor }
	void setArmor(int a) { ps.armor = a }
	
	Weapon getWeapon() { ps.weapon }
	void setWeapon(Weapon w) { ps.weapon = w }
	
	boolean getIs_a_demon() { ps.is_a_demon }
	void setIs_a_demon(boolean iad) { ps.is_a_demon = iad }
	
	
	def add_armor(int amount) {
		armor += amount
		if (armor < 0) {
			armor = 0
		}
		println "   - $this armor = $armor"
	}
	
	boolean can_use_power(StringBuilder reason=new StringBuilder()) {
		if (power.use_counter > 0) {
			reason << "already used"
			return false
		}
		if (power.cost > Game.current.active_player.available_mana) {
			reason << "not enough mana"
			return false
		}
		try {
			new BeforeUsePower(power).check()
		}
		catch(IllegalActionException e) {
			reason << e.getMessage()
			return false
		}
		return true
	}
	
	def deal_power_damage(int amount, Target target) {
		int damage = controller.get_power_damage(amount)
		deal_damage(damage, target)
	}

	def equip_weapon(String card_name) {
		def w = new Weapon(CardLibrary.getCardDefinition(card_name))
		equip_weapon(w)
	}
	
	def equip_weapon(Weapon w) {
		this.weapon = w
		w.controller = this.controller
		println "      . $this equips $w (${w.get_attack()}/${w.get_durability()})"
		new ItComesInPlay(w).check()
	}
	
	// builds dynamically a weapon
	def equip_weapon(int attack, int durability) {
		def wd = new CardDefinition()
		wd.name = "Weapon"
		wd.attack = attack
		wd.max_health = durability
		wd.type = "weapon"
		equip_weapon( new Weapon(wd) )
	}
	
	int get_attack() {
		def weapon_attack = weapon == null ? 0 : weapon.get_attack()
		return evaluate_attack(weapon_attack)
	}
	
}

class AnduinWrynn extends Hero {
	AnduinWrynn() {
		super( "Anduin Wrynn", LesserHeal.instance )
	}
}

class GarroshHellscream extends Hero {	
	GarroshHellscream() {
		super( "Garrosh Hellscream", ArmorUp.instance)
	}
}

class Guldan extends Hero {	
	Guldan() {
		super( "Gul'dan", LifeTap.instance)
	}
}

class JainaProudmoore extends Hero {
	JainaProudmoore() {
		super( "Jaina Proudmoore", Fireblast.instance )
	}
}

class LordJaraxxus extends Hero {
	LordJaraxxus() {
		super("Lord Jaraxxus", Inferno.instance)
	}
	
	boolean is_a_demon() {
		return true
	}
}

class MalfurionStormrage extends Hero {
	MalfurionStormrage() {
		super("Malfurion Stormrage", Shapeshift.instance)
	}
}

class Rexxar extends Hero {
	Rexxar() {
		super("Rexxar", SteadyShot.instance)
	}
}

class Thrall extends Hero {
	Thrall() {
		super("Thrall", TotemicCall.instance)
	}
}

class UtherLightbringer extends Hero {
	UtherLightbringer() {
		super("Uther Lightbringer", Reinforce.instance)
	}
}


class ValeeraSanguinar extends Hero {
	ValeeraSanguinar() {
		super("Valeera Sanguinar", DaggerMastery.instance)
	}
}
