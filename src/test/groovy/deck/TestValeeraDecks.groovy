package deck;

import static org.junit.Assert.*

import org.junit.Test

import utils.TestHelper;
import decks.ValeeraDeck1

class TestValeeraDecks extends TestHelper {

	@Test
	public void testValeeraDeck1() {
		def deck1 = new ValeeraDeck1()
		assert deck1.size() == 30
		assert deck1.cards.findAll{ it.name == "Molten Giant" }.size() == 2
	}

}
