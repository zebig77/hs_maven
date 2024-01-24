package game;

import static org.junit.Assert.*
import mechanics.Trigger

import org.junit.Test

class TestCardDefinition {

	@Test
	public void CardDefinition_new() {
		CardDefinition cd = CardLibrary.getCardDefinition("Abomination")
		assert cd != null
	}

}
