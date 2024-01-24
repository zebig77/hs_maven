package mechanics.events

import game.Card

class AnyMinionTakesDamage extends GlobalEvent {
	
	Card target
	
	AnyMinionTakesDamage(Card target) {
		super("a minion takes damage")
		this.target = target
	}
	
}
