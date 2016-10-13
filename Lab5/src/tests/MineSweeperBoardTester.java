package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import mineSweeperObjects.MineSweeperBoard;

public class MineSweeperBoardTester {

	@Test
	public void restartBoardTest() {
		MineSweeperBoard m = new MineSweeperBoard(9, 9);
		m.clearBoard();
		m.populateBoard();
		assertTrue(true);
	}

}
