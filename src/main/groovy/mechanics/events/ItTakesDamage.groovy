package mechanics.events

import game.Target

class ItTakesDamage extends LocalEvent {
	
	ItTakesDamage(Target origin) {
		super( "it takes damage", origin )
	}

}
