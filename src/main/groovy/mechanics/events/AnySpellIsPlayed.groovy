package mechanics.events

import game.Target

class AnySpellIsPlayed extends GlobalEvent {
	
	Target target
	boolean stop_action = false
	
	AnySpellIsPlayed(Target spell) {
		super("a spell is played")
		this.target = spell
	}
	
}
