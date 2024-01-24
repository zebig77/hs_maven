package mechanics.events

import game.Player

class AnySpellHealingIsEvaluated extends GlobalEvent {
	
	Player player
	int spell_healing_amount 	// base damage before increase
	int spell_healing_increase
	
	AnySpellHealingIsEvaluated(Player player, int amount) {
		super("player's spell healing is evaluated")
		this.player = player
		this.spell_healing_amount = amount
	}
	
}
