package mechanics.events

import game.Player

class ItsControllerTurnEnds extends PlayerEvent {
	
	ItsControllerTurnEnds(Player p) {
		super( "its controller turn ends", p )
	}

}
