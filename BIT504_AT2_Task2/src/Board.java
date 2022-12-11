import java.awt.Color;
import java.awt.Graphics;

/**
 * Board class - handles the Tic-Tac-Toe game board
 * 
 * @author Open Polytechnic, TODOs completed by Sinnovah White
 */
public class Board {

	// Grid line width
	public static final int GRID_WIDTH = 8;

	// Grid line half width
	public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

	// 2D array of ROWS-by-COLS Cell instances
	Cell[][] cells;

	/**
	 * Constructor to create the game board
	 */
	public Board() {

		// Initialise the cells 2D array using ROWS and COLS constants from GameMain
		cells = new Cell[GameMain.ROWS][GameMain.COLS];

		// Iterate over the number of rows
		for (int row = 0; row < GameMain.ROWS; ++row) {

			// Iterate over the number of columns
			for (int col = 0; col < GameMain.COLS; ++col) {

				// Initialise the current index in the cell array to a new instance of Cell
				// Pass the constructor row and col (0, 1, or 2)
				cells[row][col] = new Cell(row, col);
			}
		}
	}

	/**
	 * Checks whether or not the game has ended in a draw
	 * 
	 * @return Returns true if it is a draw (i.e., there are no more Empty cells).
	 *         Returns false if it is not a draw (i.e., has Empty cells)
	 */
	public boolean isDraw() {

		// Check whether the game has ended in a draw...

		// Iterate over the number of rows
		for (int row = 0; row < GameMain.ROWS; ++row) {

			// Iterate over the number of columns
			for (int col = 0; col < GameMain.COLS; ++col) {

				// If the current cell is empty
				if (cells[row][col].content == Player.Empty) {

					// Return false - not a draw
					return false;
				}
			}
		}

		// Return true - a draw, the loop executed and no cells were empty
		return true;
	}

	/**
	 * Checks whether the current player has completed a row, column or diagonal
	 * 
	 * @param thePlayer the current player
	 * @param playerRow the row clicked by the current player
	 * @param playerCol the column clicked by the current player
	 * @return Returns true if the current player "thePlayer" has won after making
	 *         their move. Returns false if not
	 */
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {

		// Check if player has 3-in-that-row
		if (cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer
				&& cells[playerRow][2].content == thePlayer) {

			// Return true - the player has completed a row
			return true;
		}

		// Check if player has 3-in-that-column
		if (cells[0][playerCol].content == thePlayer && cells[1][playerCol].content == thePlayer
				&& cells[2][playerCol].content == thePlayer) {

			// Return true - the player has completed a column
			return true;
		}

		// Check if player has 3-in-the-diagonal top left to bottom right
		if (cells[0][0].content == thePlayer && cells[1][1].content == thePlayer && cells[2][2].content == thePlayer) {

			// Return true - the player has completed a diagonal
			return true;
		}

		// Check if player has 3-in-the-diagonal top right to bottom left
		if (cells[0][2].content == thePlayer && cells[1][1].content == thePlayer && cells[2][0].content == thePlayer) {

			// Return true - the player has completed a diagonal
			return true;
		}

		// Return false - there is no winner, keep playing
		return false;
	}

	/**
	 * Draws the grid (rows then columns) using constant sizes, then call on the
	 * Cells to paint themselves into the grid
	 * 
	 * @param g the Graphics object
	 */
	public void paint(Graphics g) {

		// Draw the grid...

		// Set the graphics colour to grey for the grid
		g.setColor(Color.gray);

		// Iterate over the rows (rows = 1 so this will execute twice)
		for (int row = 1; row < GameMain.ROWS; ++row) {

			// Draw a horizontal rounded rectangle for each grid row using constant sizes
			g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF, GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
					GRID_WIDTH, GRID_WIDTH);
		}

		// Iterate over the columns (col = 1 so this will execute twice)
		for (int col = 1; col < GameMain.COLS; ++col) {

			// Draw a vertical rounded rectangle for each grid column using constant sizes
			g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
					GRID_WIDTH, GRID_WIDTH);
		}

		// Draw the cells...

		// Iterate over the number of rows
		for (int row = 0; row < GameMain.ROWS; ++row) {

			// Iterate over the number of columns
			for (int col = 0; col < GameMain.COLS; ++col) {

				// Call the Cell.paint() method to paint each cell in the 2D array
				cells[row][col].paint(g);
			}
		}
	}
}
// (Open Polytechnic, 2022)
