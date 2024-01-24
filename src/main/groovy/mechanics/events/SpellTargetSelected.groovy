package mechanics.events

import game.Player
import game.Target

class SpellTargetSelected extends GlobalEvent {
	
	Player player
	Target choice	// can be changed by effect (Spellbender)
	
	SpellTargetSelected(Player player, Target spell_target) {
		super("a spell target is selected")
		this.player = player
		this.choice = spell_target
	}
	
}
