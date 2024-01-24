package mechanics.events

import game.Hero

class AnyHeroTakesDamage extends GlobalEvent {
	
	Hero target
	int amount
	
	AnyHeroTakesDamage(Hero target, int amount) {
		super("a hero takes damage")
		this.target = target
		this.amount = amount
	}
	
}
