package mechanics.events

import game.Target

class AnySecretIsRevealed extends GlobalEvent {
	
	Target target
	
	AnySecretIsRevealed(Target revealed) {
		super("a secret is revealed")
		this.target = revealed
	}
	
}
