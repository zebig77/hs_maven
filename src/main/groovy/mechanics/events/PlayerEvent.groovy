package mechanics.events

import game.GameObject
import game.Player

abstract class PlayerEvent extends Event {
	
	Player p
	
	PlayerEvent(name, Player p) {
		super(name)
		this.p = p
	}
	
	@Override
	public List<GameObject> get_scope() {
		return p.hero + p.minions() + p.artefact
	}
	
	def check() {
		get_scope().each { GameObject o ->
			o.check_event(this) 
		}
	}
	
}
