package mechanics.events

import game.Target

class EnragedOff extends LocalEvent {
	
	EnragedOff(Target origin) {
		super( "it is not enraged anymore", origin )
	}

}
