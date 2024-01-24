package state;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

class StateTest {

	@Test
	public void testState() {
		def s = new MapState()
		s.test = "hello"
		assert s.test == "hello"
		s.test = "world"
		assert s.test == "world"
	}
	
	@Test
	public void testStateList() {
		def sl = new ListState()
		sl.add("Hello")
		sl.add("World")
		assert sl.size() == 2
	}
	
	@Test
	public void testClone() {
		def ls = new ListState()
		ls.add("Hello")
		ls.add("World")
		ListState ls2 = ls.clone()
		assert ls2.size() == 2
		assert ls2.contains("World")
		
		def ps = new MapState()
		ps.test1 = "hello"
		ps.test2 = "world"
		def ps2 = new MapState()
		ps.@storage.each { k,v ->
			ps2[k] = v
		}
		assert ps2.size() == 2
	}
	

	@Test
	public void testTransaction() {
		def sl = new ListState()
		Transaction.begin()
		sl.add("Hello")
		sl.add("World")
		assert sl.size() == 2
		Transaction.rollback()
		assert sl.size() == 0
	}

	@Test
	public void testClear() {
		def sl = new ListState()
		sl.add("Hello")
		sl.add("World")
		assert sl.size() == 2
		sl.clear()
		assert sl.size() == 0
		sl.add("Hello")
		sl.add("World")
		Transaction.begin()
		sl.clear()
		assert sl.size() == 0
		//println Transaction.instance.change_log
		Transaction.rollback()
		assert sl.size() == 2
	}
	
	@Test
	public void testPrint() {
		def ps = new MapState()
		ps.day = 13
		ps.month = "September"
		ps.year = 1964
		def ls = []
		ls.add("Sylvie")
		ls.add("Aurélien")
		ls.add("Camille")
		//State.print()
	}
	
	@Test
	public void testEquals() {
		def s1 = new MapState()
		def s2 = new MapState()
		assert s1.@storage != null
		assert s2.@storage != null
		assert s1.@storage == s2.@storage
		assert s1 == s2
		s1.x = 1
		assert s1.@storage != s2.@storage
		s2.x = 1
		assert s1.@storage == s2.@storage
		assert s1 == s2
	}

}
