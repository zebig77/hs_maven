package game;

import static org.junit.Assert.*;

import org.junit.Test;

class TestCardLibrary {

	@Test
	public void testLoad() {
		CardDefinition c = CardLibrary.getCardDefinition('Execute')
		assert c.type == 'spell'
	}

}
