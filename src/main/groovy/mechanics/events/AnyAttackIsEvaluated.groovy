package mechanics.events

import game.Target

class AnyAttackIsEvaluated extends GlobalEvent {
	
	int attack_change = -1 // for 'change Attack to x' buff, -1 means no change
	int attack_increase // for '+x Attack' buff
	Target target // for whom the attack is evaluated
	
	AnyAttackIsEvaluated(Target target) {
		super("attack is evaluated")
		this.target = target
	}
	
}
