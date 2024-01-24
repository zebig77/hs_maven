

package deck;

import static org.junit.Assert.*
import game.Game

import org.junit.Test

import utils.TestHelper
import decks.GarroshDeck1

class TestGarroshDecks extends TestHelper {

	@Test
	public void testGarroshDeck1() {
		def deck1 = new GarroshDeck1()
		assert deck1.size() == 30
		assert deck1.cards.findAll{ it.name == "Execute" }.size() == 2
	}

}
