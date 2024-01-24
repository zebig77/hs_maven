package state

import game.Game

class Transaction {
	
	static private Transaction instance = null
	
	Stack<Change> change_log = new Stack<Change>()
	boolean in_rollback = false
	
	public static begin() {
		assert Game.current == null || Game.current.events.size() == 0
		instance = new Transaction()
	}
	
	public static end() {
		instance = null
	}
	
	public static rollback() {
		if (instance == null) {
			return
		}
		assert instance.in_rollback == false
		instance.in_rollback = true
		while(! instance.change_log.isEmpty()) {
			instance.change_log.pop().undo()
		}
		end()
	}
	
	void log(Change c) {
		change_log.push(c)
	}
	
	public static logPropertyCreate(MapState s, String property_name) {
		if (instance != null && instance.in_rollback == false) {
			instance.log(new PropertyChange(s, property_name, null, 'C'))
		}
	}
	
	public static logPropertyUpdate(MapState s, String property_name, Object old_value) {
		if (instance != null && instance.in_rollback == false) {
			instance.log(new PropertyChange(s, property_name, old_value, 'U'))
		}
	}
	
	public static logListAdd(ListState sl, Object item) {
		if (instance != null && instance.in_rollback == false) {
			instance.log(new ListChange(sl, item, 'A'))
		}
	}

	public static logListRemove(ListState sl, Object item) {
		if (instance != null && instance.in_rollback == false) {
			instance.log(new ListChange(sl, item, 'R'))
		}
	}
	
	public static logListRemove(ListState sl, Object item, int position) {
		if (instance != null && instance.in_rollback == false) {
			instance.log(new ListChange(sl, item, 'R', position))
		}
	}
	
	public static logListClear(ListState sl) {
		if (instance != null) {
			instance.log(new ListChange(sl, sl.clone().storage, 'C'))
		}
	}
	
}
