package mechanics.events

import game.Player
import game.Target

class ItsControllerHeals extends PlayerEvent {
	
	Target healer
	Target healed
	int heal_amount
	boolean stop_action = false
	
	ItsControllerHeals(Player p, Target healer, Target healed, int heal_amount) {
		super( "its controller heals", p )
		this.healer = healer
		this.healed = healed
		this.heal_amount = heal_amount
	}

}
