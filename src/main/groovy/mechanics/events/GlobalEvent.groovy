package mechanics.events

import game.Game
import game.GameObject
import game.ScriptObject

abstract class GlobalEvent extends Event {
	
	GlobalEvent(name) {
		super(name)
	}
		
	@Override
	public List<GameObject> get_scope() {
		List<GameObject> scope = [ Game.current ] + 
			ScriptObject.your_hero +
			ScriptObject.opponent_hero +
			ScriptObject.your_minions +
			ScriptObject.enemy_minions +
			ScriptObject.active_secrets +
			[ ScriptObject.you.artefact, ScriptObject.opponent.artefact ]
			
		if (ScriptObject.your_hero.weapon != null) {
			scope = scope + ScriptObject.your_hero.weapon
		}
		if (ScriptObject.opponent_hero.weapon != null) {
			scope = scope + ScriptObject.opponent_hero.weapon
		}
		return scope
	}

	def check() {
		get_scope().each { GameObject o ->
			o.check_event(this)
		}
		return this
	}

}
