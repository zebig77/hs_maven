package state;

import static org.junit.Assert.*
import game.Card

import org.junit.Test

import utils.TestHelper

class StateGameTest extends TestHelper {
	
	@Test
	public void testStateChange() {
		Transaction.begin()
		Card abo = _play("Abomination")
		assert p1.minions.contains(abo)
		Transaction.rollback()
		assert p1.minions.contains(abo) == false
	}
	
	@Test
	public void testRollback() {
		def before_tree = State.buildGameTree().clone()
		Transaction.begin()
		Card abo = _play("Abomination")
		def after_tree = State.buildGameTree().clone()
		assert before_tree.equalsTree(after_tree) == false
		Transaction.rollback()
		after_tree = State.buildGameTree().clone()
		assert before_tree.equalsTree(after_tree) == true
	}

	@Test
	public void testStateTree() {
		def gameNode = State.buildGameTree()
		println gameNode.dump()
	}
	
	@Test
	public void testCloneTree() {
		def gameNode = State.buildGameTree()
		println gameNode.dump()
		def n1 = State.countNodes(gameNode)
		def cloneNode = gameNode.clone()
		println gameNode.dump()
		def n2 = State.countNodes(cloneNode)
		assert n1 == n2		
		assert cloneNode.equalsTree(gameNode)
	}
	
	@Test
	public void testMapTree() {
		def gameNode = State.buildGameTree()
		def map = State.mapTree(gameNode)
		assert State.countNodes(gameNode) == map.size()
	}
	
	@Test
	public void testEqualsTree() {		
		def gameNode1 = State.buildGameTree()
		def gameNode2 = gameNode1.clone()
		assert gameNode1.equalsTree(gameNode2)
		g.next_turn()
		def gameNode3 = State.buildGameTree()
		assert gameNode3.equalsTree(gameNode2) == false
	}
	
	@Test
	public void testBuffProblem() {
		def c1 = _play("Argent Commander")
		assert c1.has_charge()
		assert c1.has_divine_shield()
		_next_turn()
		def c2 = _play("Argent Commander")
		assert c2.has_charge()
		assert c2.has_divine_shield()

	}

}
