package game;

import static org.junit.Assert.*
import mechanics.Trigger
import mechanics.events.ThisPowerIsUsed

import org.junit.Test

import utils.TestHelper

class TestHero extends TestHelper {

	@Test
	public void Hero_new() {
		assert p1.hero != null
		assert p2.hero != null
		assert p1.hero.name != null
		assert p2.hero.name != null
		assert p1.hero.power != null
		assert p2.hero.power.text != null
		assert p1.hero.power.triggers.find{ Trigger t -> t.event_class == ThisPowerIsUsed.class } != null
		assert p2.hero.power.triggers.find{ Trigger t -> t.event_class == ThisPowerIsUsed.class } != null
	}

}
