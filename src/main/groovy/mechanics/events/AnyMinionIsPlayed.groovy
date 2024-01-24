package mechanics.events

import game.Target

class AnyMinionIsPlayed extends GlobalEvent {
	
	Target target
	
	AnyMinionIsPlayed(Target minion) {
		super("a minion is played")
		this.target = minion
	}
	
}
