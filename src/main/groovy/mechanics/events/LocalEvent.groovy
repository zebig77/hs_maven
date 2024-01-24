package mechanics.events

import game.GameObject

abstract class LocalEvent extends Event {
	
	LocalEvent(name, origin) {
		super(name)
		this.origin = origin
	}	
	
	@Override
	public List<GameObject> get_scope() {
		return [ origin ]
	}

}
