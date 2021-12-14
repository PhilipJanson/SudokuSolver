package sudoku;

public class Main {
	public static void main(String[] args) {
		
		Sudoku sudoku = new Sudoku();

		int[][] board = {
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
		
		sudoku.setMatrix(board);
		
		new Window(sudoku);
	}
	
	//Used for testing before UI was implemented
	private static void printBoard(SudokuSolver sudoku) {
		String s = "";
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				s += "[" + sudoku.get(i, j) + "] ";
			}
			
			s += "\n";
		}
		
		System.out.println(s);
	}
}
