package mechanics.events

import game.Target

class AnyMinionIsSummoned extends GlobalEvent {
	
	Target target
	
	AnyMinionIsSummoned(Target minion) {
		super("a minion is summoned")
		this.target = minion
	}
	
}
