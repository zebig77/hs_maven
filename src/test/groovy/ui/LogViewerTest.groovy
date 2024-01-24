package ui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import state.ListState;
import state.MapState;
import state.Transaction;

class LogViewerTest {

	@Test
	public void testState() {
		def lv = new LogViewer()
		lv.clear()
		lv.add("ligne 1")
		lv.add("ligne 2")
	}
	

}
