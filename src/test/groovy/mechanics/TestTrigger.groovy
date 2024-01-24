package mechanics;

import static org.junit.Assert.*
import game.ScriptObject;
import mechanics.buffs.BuffType
import mechanics.events.ItComesInPlay

import org.junit.Test

class TestTrigger {

	@Test
	public void Trigger_new() {
		def c = { ScriptObject.this_minion.gain(BuffType.TAUNT) }
		def trigger = new Trigger( ItComesInPlay.class, c, null )
		assert trigger.event_class == ItComesInPlay.class
		assert trigger.script == c
		assert trigger.container == null
	}

}
