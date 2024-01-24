package mechanics.events

import game.Target

class AnyMinionDies extends GlobalEvent {
	
	Target target
	
	AnyMinionDies(Target destroyed) {
		super("a minion is destroyed")
		this.target = destroyed
	}
	
}
