package mechanics.events

import game.GameObject

abstract class Event {
	
	String name
	GameObject origin	// this_minion, this_spell, this_card, this_target...
	
	Event( String name ) {
		this.name = name
	}
		
	String toString() {
		return "$name"
	}
	
	/**
	 * 
	 * @return the list of targets that will receive this event
	 */
	abstract List<GameObject> get_scope()
	
	def check() {
		get_scope().each { it.check_event(this) }
		return this
	}

}