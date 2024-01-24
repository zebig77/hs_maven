package game;

import static org.junit.Assert.*

import org.junit.Test

import utils.TestHelper

class TestHeroPower extends TestHelper {
	
	@Test
	public void ArmorUp_play() {
		p1.hero = new GarroshHellscream()
		assert p1.hero.armor == 0
		_use_hero_power()
		assert p1.hero.armor == 2
	}
	
	@Test
	public void DaggerMastery_play() {
		p1.hero = new ValeeraSanguinar()
		_use_hero_power()
		assert p1.hero.weapon != null
		assert p1.hero.weapon.name == "Wicked Knife"
		assert p1.hero.weapon.attack == 1
		assert p1.hero.weapon.durability == 2
		_attack(p1.hero, p2.hero)
		assert p2.hero.health == 29
		assert p1.hero.weapon.durability == 1
		_should_fail("has already attacked") { _attack(p1.hero, p2.hero) }
	}

	@Test
	public void Fireblast_play() {
		p1.hero = new JainaProudmoore()
		_use_hero_power(p2.hero)
		assert p2.hero.health == 29
	}
	
	@Test
	public void LesserHeal_play() {
		p1.hero = new AnduinWrynn()
		p1.hero.health = 20
		_use_hero_power(p1.hero)
		assert p1.hero.health == 22
	}
	
	@Test
	public void LifeTap_play() {
		p1.hero = new Guldan()
		def before = p1.hand.size()
		_use_hero_power()
		assert p1.hero.health == 28
		assert p1.hand.size() == before + 1
	}
	
	@Test
	public void Reinforce_play() {
		p1.hero = new UtherLightbringer()
		assert p1.minions.size() == 0
		_use_hero_power()
		assert p1.minions.size() == 1
		assert p1.minions[0].name == "Silver Hand Recruit"
		assert p1.minions[0].attack == 1
		assert p1.minions[0].health == 1
	}
	
	@Test
	public void Shapeshift_play() {
		p1.hero = new MalfurionStormrage()
		assert p1.hero.armor == 0
		assert p1.hero.get_attack() == 0
		_use_hero_power()
		assert p1.hero.armor == 1
		assert p1.hero.get_attack() == 1
		
		_next_turn()
		assert p2.hero.get_attack() == 0

	}

	@Test
	public void TotemicCall_possible() {
		p1.hero = new Thrall()
		_use_hero_power()
		assert p1.minions.size() == 1
		assert TotemicCall.power_totems.contains(p1.minions[0].name)
		_should_fail("Cannot use power") { _use_hero_power() } // already used
		
		_next_turn()
		_next_turn()
		_use_hero_power()
		assert p1.minions.size() == 2
		assert TotemicCall.power_totems.contains(p1.minions[1].name)
	}

	@Test
	public void TotemicCall_not_possible() {
		p1.hero = new Thrall()
		TotemicCall.power_totems.each {
			Game.summon(p1, it)
		}
		assert p1.minions.size() == 4
		_should_fail("Cannot use power") { _use_hero_power() } // all totems already summoned
	}
	
	@Test
	public void SteadyShot_play() {
		p1.hero = new Rexxar()
		_use_hero_power()
		assert p2.hero.health == 28
	}

}
