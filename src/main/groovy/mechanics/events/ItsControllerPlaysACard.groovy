package mechanics.events

import game.Card
import game.Player

class ItsControllerPlaysACard extends PlayerEvent {
	
	Card target
	
	ItsControllerPlaysACard(Player p, Card played_card) {
		super( "its controller plays a card", p )
		this.target = played_card
	}
	
}
