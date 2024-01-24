package game;

import static org.junit.Assert.*
import mechanics.buffs.BuffType;

import org.junit.Test

import utils.TestHelper

class TestTarget extends TestHelper {
	
	@Test
	public void Target_new() {
		Card c2 = Game.new_card('Timber Wolf')
		assert c2 != null
		assert c2.get_attack() == 1
		assert c2.is_a_minion()
		assert c2.is_a_beast()
		assert c2.has_buff(BuffType.TAUNT) == false
		Card c3 = Game.new_card("Abomination")
		assert c3 != null
		assert c3.has_buff(BuffType.TAUNT) == false
		assert c3.has_buff(BuffType.WINDFURY) == false
		Game.summon(p1, c3)
		assert p1.minions.contains(c3)
		assert c3.has_buff(BuffType.TAUNT) == true // as an effect of being put in play
	}
	
	@Test
	public void Target_plus() {
		Card c1 = Game.new_card('Abomination')		
		Card c2 = Game.new_card('Timber Wolf')
		Card c3 = Game.new_card('Cruel Taskmaster')
		assert c1 + c2 == [ c1, c2 ]
	}

	@Test
	public void Target_silence() {
		Card abo = Game.new_card('Abomination')	
		Game.summon(p1, abo)
		assert abo.has_buff(BuffType.TAUNT)	
		Card ibo = Game.new_card('Ironbeak Owl')
		p1.hand.add(ibo)
		p1.next_choices = [ abo ]
		p1.available_mana = ibo.get_cost()
		p1.play(ibo)
		assert abo.has_buff(BuffType.TAUNT) == false	
	}

}
