package tictactoe;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Cell class - handles each cell on the game board
 * 
 * @author Open Polytechnic, TODOs completed by Sinnovah White
 */
public class Cell {

	// Content of this cell (empty, cross, or nought)
	Player content;

	// Row and column of this cell
	int row, col;

	/**
	 * Constructor to initialise this cell with the specified row and col.
	 * 
	 * @param row an integer to determine which row the Cell is in (0, 1, or 2).
	 * @param col an integer to determine the column the Cell is in (0, 1, or 2).
	 */
	public Cell(int row, int col) {

		// Variable initialisation for row
		this.row = row;

		// Variable initialisation for col
		this.col = col;

		// Set this cell's content to empty by calling the clear() method
		this.clear();
	}

	/**
	 * Paint itself (Cell) on the graphics canvas, given the Graphics context g
	 * 
	 * @param g the Graphics object
	 */
	public void paint(Graphics g) {

		// Graphics2D allows the setting of pen's stroke size
		Graphics2D graphic2D = (Graphics2D) g;

		// Set the pen's stroke size
		graphic2D.setStroke(
				new BasicStroke(GameMain.SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

		// Draw the symbol in the position...

		// An x coordinate for both X and 0 symbols
		int x1 = col * GameMain.CELL_SIZE + GameMain.CELL_PADDING;

		// A y coordinate for both X and 0 symbols
		int y1 = row * GameMain.CELL_SIZE + GameMain.CELL_PADDING;

		// If the cell's content is X (the cross player)
		if (content == Player.Cross) {

			// Set the pen's colour to red
			graphic2D.setColor(Color.RED);

			// An additional x coordinate for drawing the X's diagonal lines
			int x2 = (col + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;

			// An additional y coordinate for drawing the X's diagonal lines
			int y2 = (row + 1) * GameMain.CELL_SIZE - GameMain.CELL_PADDING;

			// Draw a diagonal line for the X (top left to bottom right)
			graphic2D.drawLine(x1, y1, x2, y2);

			// Draw the other diagonal line for the X (top right to bottom left)
			graphic2D.drawLine(x2, y1, x1, y2);
		}
		// Else if the cell's content is O (the nought player)
		else if (content == Player.Nought) {

			// Set the pen's colour to blue
			graphic2D.setColor(Color.BLUE);

			// Draw a symbol sized oval at the x and y coordinates
			graphic2D.drawOval(x1, y1, GameMain.SYMBOL_SIZE, GameMain.SYMBOL_SIZE);
		}
	}

	/**
	 * Sets this cell's content to Empty
	 */
	public void clear() {

		// Set the content of this Cell to empty (enumeration)
		this.content = Player.Empty;
	}
}
// (Open Polytechnic, 2022)
