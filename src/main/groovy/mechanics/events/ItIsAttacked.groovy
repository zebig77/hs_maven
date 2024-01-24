package mechanics.events

import game.Target

class ItIsAttacked extends LocalEvent {
	
	ItIsAttacked(Target origin) {
		super( "it is attacked", origin )
	}

}
