package mechanics.events

import game.Target

class BeforePlay extends LocalEvent {
	
	BeforePlay(Target origin) {
		super( "we check if it can be played", origin )
	}
	
}
