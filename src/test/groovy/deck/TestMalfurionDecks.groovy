package deck;

import static org.junit.Assert.*

import org.junit.Test

import utils.TestHelper;
import decks.MalfurionDeck1

class TestMalfurionDecks extends TestHelper {

	@Test
	public void TestMalfurionDecks_deck1() {
		def deck1 = new MalfurionDeck1()
		assert deck1.size() == 30
		assert deck1.cards.findAll{ it.name == "Swipe" }.size() == 2
	}

}
