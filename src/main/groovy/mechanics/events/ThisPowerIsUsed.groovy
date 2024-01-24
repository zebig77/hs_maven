package mechanics.events

import game.HeroPower

class ThisPowerIsUsed extends LocalEvent {
	
	ThisPowerIsUsed(HeroPower hero_power) {
		super( "this power is used", hero_power )
	}

}
