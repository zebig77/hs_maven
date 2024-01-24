package mechanics.events

import game.Target

class EnragedOn extends LocalEvent {
	
	EnragedOn(Target origin) {
		super( "it has become enraged", origin )
	}

}
