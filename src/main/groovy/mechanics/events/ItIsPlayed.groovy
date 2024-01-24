package mechanics.events

import game.Target

class ItIsPlayed extends LocalEvent {
	
	ItIsPlayed(Target origin) {
		super( "it is played", origin )
	}

}
