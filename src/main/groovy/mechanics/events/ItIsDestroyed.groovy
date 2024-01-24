package mechanics.events

import game.Target

class ItIsDestroyed extends LocalEvent {
	
	ItIsDestroyed(Target origin) {
		super( "it is destroyed", origin )
	}

}
