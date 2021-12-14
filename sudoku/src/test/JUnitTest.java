package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sudoku.Sudoku;
import sudoku.SudokuSolver;

class JUnitTest {

	private SudokuSolver sudoku;

	@BeforeEach
	public void setUp() {
		sudoku = new Sudoku();
	}

	@AfterEach
	void tearDown() {
		sudoku = null;
	}

	@Test
	void testEmpty() {
		assertTrue(sudoku.solve());
	}
}
