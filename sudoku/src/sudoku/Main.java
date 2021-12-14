package sudoku;

public class Main {
	public static void main(String[] args) {
		
		Sudoku sudoku = new Sudoku();
		
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
