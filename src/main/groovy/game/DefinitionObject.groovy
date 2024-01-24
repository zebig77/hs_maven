package game

import mechanics.Trigger
import mechanics.buffs.Buff

class DefinitionObject extends ScriptObject {

	String name
	String text
	List<Trigger> triggers = []
	List<Buff> buffs = []
	List<Closure> get_targets

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
