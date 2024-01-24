package mechanics.events

import game.HeroPower

class BeforeUsePower extends LocalEvent {
	
	BeforeUsePower(HeroPower hp) {
		super( "we check if power can be used", hp )
	}
	
}
