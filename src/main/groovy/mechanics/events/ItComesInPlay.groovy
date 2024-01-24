package mechanics.events

import game.Target

class ItComesInPlay extends LocalEvent {
	
	ItComesInPlay(Target origin) {
		super( "it comes in play", origin )
	}

}
