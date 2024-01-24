package mechanics.events

import game.Target
import mechanics.buffs.Buff

class AnyBuffListIsEvaluated extends GlobalEvent {
	
	Target target
	List<Buff> additional_buffs = []
	
	AnyBuffListIsEvaluated(origin) {
		super( "its buff list is evaluated" )
		this.target = origin
	}

}
