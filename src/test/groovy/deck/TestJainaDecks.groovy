package deck;

import static org.junit.Assert.*

import org.junit.Test

import utils.TestHelper
import decks.JainaDeck1

class TestJainaDecks extends TestHelper {

	@Test
	public void JainaDecks_deck1() {
		def deck1 = new JainaDeck1()
		assert deck1.size() == 30
		assert deck1.cards.findAll{ it.name == "Ice Lance" }.size() == 2
	}

}
