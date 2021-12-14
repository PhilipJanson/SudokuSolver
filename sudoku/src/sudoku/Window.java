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
	public static final Color RED = new Color(0xFF7777);

	private SudokuSolver sudoku;
	private Font font;

	public Window(SudokuSolver s) {
		sudoku = s;
		font = new Font("Arial", 0, 48);
		SwingUtilities.invokeLater(() -> createWindow("Sudoku Solver", 800, 800));
	}

	/*
	 * Creates the window and initializes its components
	 */
	private void createWindow(String title, int width, int height) {
		JFrame frame = new JFrame(title);
		Container pane = frame.getContentPane();

		JPanel board = initBoard();
		pane.add(board, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		pane.add(panel, BorderLayout.SOUTH);

		JButton solve = new JButton("Solve");
		JButton clear = new JButton("Clear");
		JTextField field = new JTextField("Enter number");
		JButton show = new JButton("Show");
		JButton hide = new JButton("Hide");

		solve.addActionListener(e -> solve(board));
		clear.addActionListener(e -> clear(board));
		show.addActionListener(e -> show(board, field));
		hide.addActionListener(e -> hide(board));
		field.addKeyListener(new KeyHandler());

		panel.add(solve);
		panel.add(clear);
		panel.add(field);
		panel.add(show);
		panel.add(hide);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(width, height);
		// frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

	/*
	 * Creates the board and fills it the UI with values from the SudokuSolver's matrix
	 */
	private JPanel initBoard() {
		JPanel board = new JPanel();
		board.setLayout(new GridLayout(9, 1));

		for (int row = 0; row < 9; row++) {
			JPanel group = new JPanel();
			group.setLayout(new GridLayout(1, 9));

			for (int col = 0; col < 9; col++) {
				JTextField box = new JTextField(sudoku.get(row, col) == 0 ? "" : String.valueOf(sudoku.get(row, col)));
				box.addKeyListener(new KeyHandler());
				box.setFont(font);
				box.setHorizontalAlignment(SwingConstants.CENTER);
				box.setBackground((row / 3 + col / 3) % 2 == 0 ? ORANGE : GRAY);

				group.add(box);
			}

			board.add(group);
		}

		return board;
	}

	/*
	 * Clears the both the UI and the SudokuSolver's matrix
	 */
	private void clear(JPanel board) {
		for (Component group : board.getComponents()) {
			for (Component textField : ((JPanel) group).getComponents()) {
				((JTextField) textField).setText("");
			}
		}

		sudoku.clear();
	}

	/*
	 * Solves the sudoku and then fills the UI with the values from the SudokuSolver's matrix
	 */
	private void solve(JPanel board) {
		fillMatrix(board);

		if (sudoku.isValid()) {
			if (sudoku.solve()) {
				int row = 0;
				int col = 0;

				for (Component group : board.getComponents()) {
					for (Component textField : ((JPanel) group).getComponents()) {
						JTextField box = (JTextField) textField;
						box.setText(String.valueOf(sudoku.get(row, col)));
						col++;
					}

					col = 0;
					row++;
				}

				JOptionPane.showMessageDialog(board, "Success!", "Done", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(board, "Solution does not exist", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(board, "Invalid placement of digits", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * Fills the matrix in SudokuSolver with the values in the boxes from the UI
	 */
	private void fillMatrix(JPanel board) {
		int row = 0;
		int col = 0;

		for (Component group : board.getComponents()) {
			for (Component textField : ((JPanel) group).getComponents()) {
				JTextField box = (JTextField) textField;
				sudoku.add(row, col, box.getText().isEmpty() ? 0 : Integer.valueOf(box.getText()));
				col++;
			}

			col = 0;
			row++;
		}
	}

	/*
	 * Used for debugging, highlights all the numbers specified in the textfield
	 */
	private void show(JPanel board, JTextField field) {
		hide(board);

		if (!field.getText().isEmpty()) {
			for (Component group : board.getComponents()) {
				for (Component textField : ((JPanel) group).getComponents()) {
					if (((JTextField) textField).getText().equals(field.getText())) {
						((JTextField) textField).setBackground(RED);
					}
				}
			}
		}
	}

	/*
	 * Used for debugging, hides all the the highlights
	 */
	private void hide(JPanel board) {
		int row = 0;
		int col = 0;

		for (Component group : board.getComponents()) {
			for (Component textField : ((JPanel) group).getComponents()) {
				((JTextField) textField).setBackground((row / 3 + col / 3) % 2 == 0 ? ORANGE : GRAY);
				col++;
			}

			col = 0;
			row++;
		}
	}

	/*
	 * A KeyListener used for checking the input of every box in the grid
	 */
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
