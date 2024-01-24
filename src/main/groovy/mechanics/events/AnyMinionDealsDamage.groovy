package mechanics.events

import game.GameObject
import game.Target

class AnyMinionDealsDamage extends GlobalEvent {
	
	GameObject origin
	Target target
	
	AnyMinionDealsDamage(GameObject origin, Target damaged_target) {
		super( "a minion deals damage")
		this.target = damaged_target
	}

}
