package mechanics.events

import game.Target
import mechanics.buffs.BuffType

class AnyBuffIsEvaluated extends GlobalEvent {

	BuffType buff_type	
	Target target
	boolean has_buff = false
	boolean stop_action = false
	
	AnyBuffIsEvaluated(BuffType buff_type, Target target) {
		super("buff is evaluated")
		this.buff_type = buff_type
		this.target = target
	}
	
}
