package game

import mechanics.Trigger
import mechanics.buffs.BuffType
import mechanics.events.Event
import state.ListState

class GameObject extends ScriptObject {
	
	ListState<Trigger> triggers = []

	/*
	 * check if the target has triggers the event type of e
	 */
	def check_event(Event e) {

		e.origin = this
		
		Game.current.events.push(e)

		List<Trigger> todo_triggers = triggers.findAll{(it as Trigger).event_class == e.class}
		todo_triggers.each{ Trigger t ->
			println "      . executing $t for ${this} because $e"
			t.script.call()
			if (t.last_call()) {
				println "      . removing $t because it was called for the last time"
				this.remove_trigger(t)
			}
		}

		Game.current.events.pop()
	}
	
	def remove_trigger(Trigger t) {
		triggers.remove(t)
	}

	boolean has_buff(String buff_string) {
		return has_buff( BuffType.getInstance(buff_string))
	}

	@Override
	Trigger add_trigger(Class event_class, Closure c) {
		Trigger t = new Trigger( event_class, c, this )
		triggers.add(t)
		return t
	}

	@Override
	Trigger add_trigger(Class event_class, Closure c, String comment) {
		Trigger t = new Trigger( event_class, c, this, comment )
		triggers.add(t)
		return t
	}
	
}
