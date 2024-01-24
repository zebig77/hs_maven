package deck;

import static org.junit.Assert.*

import org.junit.Test

import utils.TestHelper
import decks.AnduinDeck1

class TestAnduinDecks extends TestHelper {

	@Test
	public void testAnduinDeck1() {
		def deck1 = new AnduinDeck1()
		assert deck1.size() == 30
		assert deck1.cards.findAll{ it.name == "Northshire Cleric" }.size() == 2
	}

}
