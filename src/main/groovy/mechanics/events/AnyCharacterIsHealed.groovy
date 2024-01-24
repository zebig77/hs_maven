package mechanics.events

import game.Target

class AnyCharacterIsHealed extends GlobalEvent {
	
	Target target
	
	AnyCharacterIsHealed(Target target) {
		super("a character is healed")
		this.target = target
	}
	
}
