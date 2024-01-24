package mechanics.events

import game.Player

class ItsControllerTurnStarts extends PlayerEvent {
	
	ItsControllerTurnStarts(Player p) {
		super( "its controller turn starts", p )
	}

}
