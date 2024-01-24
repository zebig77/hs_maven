package game;

import org.junit.Test

import utils.TestHelper

public class TestUtherLightbringer extends TestHelper {
	
	@Test
	public void Uther_summon_recruit() {
		p1.hero = new UtherLightbringer()
		p1.hero.controller = p1
		_use_hero_power()
		assert p1.minions.size() == 1
		assert p1.minions[0].name == "Silver Hand Recruit"
		assert p1.minions[0].cost == 1
		assert p1.minions[0].attack == 1
		assert p1.minions[0].health == 1		
	}

}
