package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Window {

	public static final Color GRAY = new Color(0xEAEAEA);
	public static final Color ORANGE = new Color(0xFFD2B5);

	private SudokuSolver sudoku;
	private Font font;

	public Window(SudokuSolver s) {
		sudoku = s;
		font = new Font("Arial", 0, 48);
		SwingUtilities.invokeLater(() -> createWindow("Sudoku Solver", 800, 800));
	}

	// Creates the window and initializes its components
	private void createWindow(String title, int width, int height) {
		JFrame frame = new JFrame(title);
		Container pane = frame.getContentPane();

		JPanel board = initBoard();
		pane.add(board, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		pane.add(panel, BorderLayout.SOUTH);

		JButton solve = new JButton("Solve");
		JButton clear = new JButton("Clear");
		solve.addActionListener(e -> solve(board));
		clear.addActionListener(e -> clear(board));

		panel.add(solve);
		panel.add(clear);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(width, height);
		//frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	// Creates the board and fills it the UI with values from the SudokuSolver's matrix
	private JPanel initBoard() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(9, 1));

		for (int i = 0; i < 9; i++) {
			JPanel group = new JPanel();
			group.setLayout(new GridLayout(1, 9));

			for (int j = 0; j < 9; j++) {
				JTextField box = new JTextField(sudoku.get(i, j) == 0 ? "" : String.valueOf(sudoku.get(i, j)));
				box.addKeyListener(new KeyHandler());
				box.setFont(font);
				box.setHorizontalAlignment(SwingConstants.CENTER);
				box.setBackground((i / 3 + j / 3) % 2 == 0 ? ORANGE : GRAY);

				group.add(box);
			}

			board.add(group);
		}

		return board;
	}

	// Clears the both the UI and the SudokuSolver's matrix
	private void clear(JPanel board) {
		for (Component group : board.getComponents()) {
			for (Component textField : ((JPanel) group).getComponents()) {
				((JTextField) textField).setText("");
			}
		}

		sudoku.clear();
	}

	// Solves the sudoku and then fills the UI with the values from the SudokuSolver's matrix
	private void solve(JPanel board) {
		fillMatrix(board);
		
		int i = 0;
		int j = 0;

		if (sudoku.isValid()) {
			if (sudoku.solve()) {
				i = 0;
				j = 0;

				for (Component group : board.getComponents()) {
					for (Component textField : ((JPanel) group).getComponents()) {
						JTextField box = (JTextField) textField;
						box.setText(String.valueOf(sudoku.get(i, j)));
						j++;
					}

					j = 0;
					i++;
				}

				JOptionPane.showMessageDialog(board, "Success!", "Done", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(board, "Solution does not exist", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(board, "Invalid placement of digits", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Fills the matrix in SudokuSolver with the values in the boxes from the UI
	private void fillMatrix(JPanel board) {
		int i = 0;
		int j = 0;
		
		for (Component group : board.getComponents()) {
			for (Component textField : ((JPanel) group).getComponents()) {
				JTextField box = (JTextField) textField;
				sudoku.add(i, j, box.getText().isEmpty() ? 0 : Integer.valueOf(box.getText()));
				j++;
			}

			j = 0;
			i++;
		}
	}
	
	private static class KeyHandler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent event) {
			char c = event.getKeyChar();
			JTextField box = (JTextField) event.getComponent();

			if (((c < '1') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
				JOptionPane.showMessageDialog(event.getComponent().getParent().getParent(), "Invalid character, please use digits 1-9", "Error", JOptionPane.ERROR_MESSAGE);
				event.consume();
			} else if ((box.getText().length() > 0)) {
				box.setText("");
			}
		}

		@Override
		public void keyPressed(KeyEvent event) {
		}

		@Override
		public void keyReleased(KeyEvent event) {
		}
	}
}
