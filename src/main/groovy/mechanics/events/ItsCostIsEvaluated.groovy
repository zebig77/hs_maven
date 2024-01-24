package mechanics.events

import game.Target

class ItsCostIsEvaluated extends LocalEvent {
	
	int cost_increase = 0
	
	ItsCostIsEvaluated(Target origin) {
		super( "its cost is evaluated", origin )
	}

}
