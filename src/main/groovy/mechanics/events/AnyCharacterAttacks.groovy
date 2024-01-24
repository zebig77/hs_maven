package mechanics.events

import game.Target

class AnyCharacterAttacks extends GlobalEvent {
	
	Target attacker
	Target attacked
	Target changed_attacked = null
	
	AnyCharacterAttacks(Target attacker, Target attacked) {
		super( "a character attacks")
		this.attacker = attacker
		this.attacked = attacked
	}

}
