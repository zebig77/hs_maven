package mechanics.events

import game.Target

class AnyHealthIsEvaluated extends GlobalEvent {
	
	int health_increase
	Target target
	
	AnyHealthIsEvaluated(Target target) {
		super("health is evaluated")
		this.target = target
	}
	
}
