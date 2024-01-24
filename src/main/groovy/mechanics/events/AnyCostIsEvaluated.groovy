package mechanics.events

import game.GameObject
import game.Target

class AnyCostIsEvaluated extends GlobalEvent {
	
	int cost_increase
	int cost_change = -1	// -1 means no cost change
	int lowest_cost = -1	// -1 means no minimum cost
	Target target
	
	AnyCostIsEvaluated(Target target) {
		super("a cost is evaluated")
		this.target = target
	}
	
	@Override
	public List<GameObject> get_scope() {
		if (target.controller == null) {
			return super.get_scope()
		}
		// special case: triggers for cards in hand apply
		return super.get_scope() + target.controller.hand.cards.storage
	}
	
}
