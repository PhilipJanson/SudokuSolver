package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	void testAdd() {
		sudoku.add(1, 1, 8);
		assertEquals(8, sudoku.get(1, 1));
		assertThrows(IllegalArgumentException.class, () -> sudoku.add(9, 0, 1));
		assertThrows(IllegalArgumentException.class, () -> sudoku.add(0, -1, 1));
		assertThrows(IllegalArgumentException.class, () -> sudoku.add(0, 0, 10));
	}
	
	@Test
	void testRemove() {
		sudoku.add(0, 0, 1);
		sudoku.add(4, 4, 7);
		assertEquals(1, sudoku.get(0, 0));
		assertEquals(7, sudoku.get(4, 4));
		sudoku.remove(0, 0);
		sudoku.remove(4, 4);
		assertEquals(0, sudoku.get(0, 0));
		assertEquals(0, sudoku.get(4, 4));
	}
	
	@Test
	void testRowRules() {
		sudoku.add(0, 0, 5);
		sudoku.add(0, 1, 5);
		assertFalse(sudoku.isValid());
		sudoku.remove(0, 1);
		assertTrue(sudoku.isValid());
	}

	@Test
	void testColumnRules() {
		sudoku.add(0, 2, 5);
		sudoku.add(1, 2, 5);
		assertFalse(sudoku.isValid());
		sudoku.remove(1, 2);
		assertTrue(sudoku.isValid());
	}

	@Test
	void testBoxRules() {
		sudoku.add(4, 4, 5);
		sudoku.add(5, 5, 5);
		assertFalse(sudoku.isValid());
		sudoku.remove(5, 5);
		assertTrue(sudoku.isValid());
	}
	
	@Test
	void testClear() {
		int[][] matrix = {
			{0, 0, 8, 0, 0, 9, 0, 6, 2},
			{0, 0, 0, 0, 0, 0, 0, 0, 5},
			{1, 0, 2, 5, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 1, 0, 0, 9, 0},
			{0, 5, 0, 0, 0, 0, 6, 0, 0},
			{6, 0, 0, 0, 0, 0, 0, 2, 8},
			{4, 1, 0, 6, 0, 8, 0, 0, 0},
			{8, 6, 0, 0, 3, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 4, 0, 0} 
		};

		sudoku.setMatrix(matrix);
		sudoku.clear();
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				assertEquals(0, sudoku.get(row, col));
			}
		}
	}
	
	@Test
	void testSetMatrix() {
		int[][] matrix = {
			{0, 0, 8, 0, 0, 9, 0, 6, 2},
			{0, 0, 0, 0, 0, 0, 0, 0, 5},
			{1, 0, 2, 5, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 1, 0, 0, 9, 0},
			{0, 5, 0, 0, 0, 0, 6, 0, 0},
			{6, 0, 0, 0, 0, 0, 0, 2, 8},
			{4, 1, 0, 6, 0, 8, 0, 0, 0},
			{8, 6, 0, 0, 3, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 4, 0, 0} 
		};
		
		sudoku.setMatrix(matrix);
		
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				assertEquals(matrix[row][col], sudoku.get(row, col));
			}
		}
		
		sudoku.clear();
		assertThrows(IllegalArgumentException.class, () -> sudoku.setMatrix(new int[9][2]));
		assertThrows(IllegalArgumentException.class, () -> sudoku.setMatrix(new int[7][9]));
		assertThrows(IllegalArgumentException.class, () -> {
			int[][] m = new int[9][9];
			m[5][4] = 10;
			sudoku.setMatrix(m);
		});
	}
	
	@Test
	void testGetMatrix() {
		int[][] matrix = {
			{0, 0, 8, 0, 0, 9, 0, 6, 2},
			{0, 0, 0, 0, 0, 0, 0, 0, 5},
			{1, 0, 2, 5, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 1, 0, 0, 9, 0},
			{0, 5, 0, 0, 0, 0, 6, 0, 0},
			{6, 0, 0, 0, 0, 0, 0, 2, 8},
			{4, 1, 0, 6, 0, 8, 0, 0, 0},
			{8, 6, 0, 0, 3, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 4, 0, 0} 
		};
		
		sudoku.setMatrix(matrix);
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				assertEquals(matrix[row][col], sudoku.getMatrix()[row][col]);
			}
		}
	}
	
	@Test
	void testEmptySudoku() {
		assertTrue(sudoku.solve());
	}

	@Test
	void testUnsolvable() {
		int[][] matrix = {
			{1, 2, 3, 0, 0, 0, 0, 0, 0},
			{4, 5, 6, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 7, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0},
		};

		sudoku.setMatrix(matrix);
		assertFalse(sudoku.solve());
		sudoku.remove(2, 3);
		assertTrue(sudoku.solve());
	}
	
	@Test
	void testSolvable() {
		int[][] matrix = {
			{0, 0, 8, 0, 0, 9, 0, 6, 2},
			{0, 0, 0, 0, 0, 0, 0, 0, 5},
			{1, 0, 2, 5, 0, 0, 0, 0, 0},
			{0, 0, 0, 2, 1, 0, 0, 9, 0},
			{0, 5, 0, 0, 0, 0, 6, 0, 0},
			{6, 0, 0, 0, 0, 0, 0, 2, 8},
			{4, 1, 0, 6, 0, 8, 0, 0, 0},
			{8, 6, 0, 0, 3, 0, 1, 0, 0},
			{0, 0, 0, 0, 0, 0, 4, 0, 0} 
		};

		sudoku.setMatrix(matrix);
		assertTrue(sudoku.solve());
	}
}
