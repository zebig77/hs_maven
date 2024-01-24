package mechanics.events

import game.Target
import game.Weapon

class ItsDurabilityIsReduced extends LocalEvent {
	
	boolean stop_action = false
	Target attacked
	
	ItsDurabilityIsReduced(Weapon origin, Target attacked) {
		super( "its durability is reduced", origin )
		this.attacked = attacked
	}

}
