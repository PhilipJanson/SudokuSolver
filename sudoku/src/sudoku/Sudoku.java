package sudoku;

public class Sudoku implements SudokuSolver {

	private int[][] matrix = new int[9][9];

	/**
	 * Tries to solve the sudoku by using recursive backtracking.
	 * 
	 * @return true if it is solved successfully, false if it fails
	 */
	@Override
	public boolean solve() {
		return solve(0, 0);
	}

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range [0..9]
	 */
	@Override
	public void add(int row, int col, int digit) {
		if (notInRange(row, col, digit)) {
			throw new IllegalArgumentException("Row, column or digit out of range");
		}

		matrix[row][col] = digit;
	}

	/**
	 * Removes digit in the box row, col.
	 * 
	 * @param row The row
	 * @param col The column
	 */
	@Override
	public void remove(int row, int col) {
		matrix[row][col] = 0;
	}

	/**
	 * Returns the digit in the box row, col.
	 * 
	 * @param row The row
	 * @param col The column
	 * @return digit The digit in the box row, col
	 */
	@Override
	public int get(int row, int col) {
		return matrix[row][col];
	}

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 * 
	 * @return true if all digits follow sudoku rules, false if it doesn't
	 */
	@Override
	public boolean isValid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (matrix[i][j] != 0) {
					if (!(checkRules(i, j, matrix[i][j]))) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Clears the matrix.
	 */
	@Override
	public void clear() {
		matrix = new int[9][9];
	}

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains values outside the range [0..9]
	 */
	@Override
	public void setMatrix(int[][] m) {
		for (int row = 0; row < 9; row++) {
			if (m.length != matrix.length || m[row].length != matrix[row].length) {
				throw new IllegalArgumentException("Wrong dimension for input matrix");
			}

			for (int col = 0; col < 9; col++) {
				if (invalidDigit(m[row][col])) {
					throw new IllegalArgumentException("Digit at row: " + row + ", col: " + col + "is out of range");
				}
			}
		}

		matrix = m;
	}

	/**
	 * Returns a copy of the matrix used to store the sudoku values
	 * 
	 * @return a copy of the matrix
	 */
	@Override
	public int[][] getMatrix() {
		int[][] temp = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				temp[i][j] = matrix[i][j];
			}
		}

		return temp;
	}

	/*
	 *  Solves the sudoku
	 */
	private boolean solve(int row, int col) {
		int newRow = row;
		int newCol;

		if (col == 8) {
			newCol = 0;
			newRow = row + 1;
		} else {
			newCol = col + 1;
		}

		if (row == 9) {
			return true;
		}

		if (matrix[row][col] == 0) {
			for (int i = 1; i < 10; i++) {
				if (checkRules(row, col, i)) {
					matrix[row][col] = i;

					if (solve(newRow, newCol)) {
						return true;
					} else {
						matrix[row][col] = 0;
					}
				}
			}

			return false;
		}

		return solve(newRow, newCol);
	}

	/*
	 *  Checks that all the rules of sudoku is being followed
	 */
	private boolean checkRules(int row, int col, int digit) {
		return checkRow(row, col, digit) && checkColumn(row, col, digit) && checkBox(row, col, digit);
	}

	/*
	 *  Check that the digit is allowed in its row
	 */
	private boolean checkRow(int row, int col, int digit) {
		for (int i = 0; i < 9; i++) {
			if (col != i && matrix[row][i] == digit) {
				return false;
			}
		}

		return true;
	}

	/*
	 *  Checks that the digit is allowed in its column
	 */
	private boolean checkColumn(int row, int col, int digit) {
		for (int i = 0; i < 9; i++) {
			if (row != i && matrix[i][col] == digit) {
				return false;
			}
		}

		return true;
	}

	/*
	 *  Checks that the digit is allowed in its box
	 */
	private boolean checkBox(int row, int col, int digit) {
		int bx = row / 3;
		int by = col / 3;

		for (int i = bx * 3; i < bx * 3 + 3; i++) {
			for (int j = by * 3; j < by * 3 + 3; j++) {
				if ((row != i || col != j) && matrix[i][j] == digit) {
					return false;
				}
			}
		}

		return true;
	}

	/*
	 *  Checks if the row, col and digit is in range
	 */
	private boolean notInRange(int row, int col, int digit) {
		return row < 0 || row > 8 || col < 0 || col > 8 || invalidDigit(digit);
	}

	/*
	 *  Checks if the digit is in range
	 */
	private boolean invalidDigit(int digit) {
		return digit < 0 || digit > 9;
	}
}
