package mechanics.events

import game.Target

class ItAttacks extends LocalEvent {
	
	ItAttacks(Target origin) {
		super( "it attacks", origin )
	}

}
