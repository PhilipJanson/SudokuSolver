package sudoku;

public interface SudokuSolver {
	
	/**
	 * Tries to solve the sudoku by using recursive backtracking. 
	 * 
	 * @return true if it is solved successfully, false if it fails
	 */
	boolean solve();

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range [0..9]
	 */
	void add(int row, int col, int digit);

	/**
	 * Removes digit in the box row, col.
	 * 
	 * @param row The row
	 * @param col The column
	 */
	void remove(int row, int col);

	/**
	 * Returns the digit in the box row, col.
	 * 
	 * @param row The row
	 * @param col The column
	 * @return digit The digit in the box row, col
	 */
	int get(int row, int col);

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 * 
	 * @return true if all digits follow sudoku rules, false if it doesn't
	 */
	boolean isValid();

	/**
	 * Clears the matrix.
	 */
	void clear();

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains values outside the range [0..9]
	 */
	void setMatrix(int[][] m);

	/**
	 * Returns a copy of the matrix used to store the sudoku values
	 * 
	 * @return a copy of the matrix
	 */
	int[][] getMatrix();
}