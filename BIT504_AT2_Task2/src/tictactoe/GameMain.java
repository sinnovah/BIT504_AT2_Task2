package tictactoe;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameMain class - handles the game logic. Extends JPanel class to inherit all
 * of its methods. Implements MouseListener to listen for users' mouse events.
 * 
 * @author Open Polytechnic, TODOs completed by Sinnovah White
 */
public class GameMain extends JPanel implements MouseListener {

	// Constants for the game...

	// Number of cell ROWS constant
	public static final int ROWS = 3;

	// Number of cell COLS constant
	public static final int COLS = 3;

	// Title for the game - displays at the top of the window
	public static final String TITLE = "Tic Tac Toe";

	// Constants for the dimensions used for drawing...

	// Cell width and height
	public static final int CELL_SIZE = 100;

	// Drawing canvas' width
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;

	// Drawing canvas' height
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

	// Noughts and Crosses are displayed inside a cell, with padding from border...

	// Distance the X or O is from each cell edge
	public static final int CELL_PADDING = CELL_SIZE / 6;

	// The X or O's symbol size (width and height)
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;

	// Thickness of the X or O
	public static final int SYMBOL_STROKE_WIDTH = 8;

	// Fixes a compiler warning - the Eclipse suggested solution, not used
	private static final long serialVersionUID = 1L;

	// Declare the game object variables...

	// The game board
	private Board board;

	// The game's current state, (enumeration)
	private GameState currentState;

	// The current player, (enumeration)
	private Player currentPlayer;

	// For displaying the game's status message
	private JLabel statusBar;

	/**
	 * Constructor to setup the mouse listener, UI, and game components on the panel
	 */
	public GameMain() {

		// Listen for users' mouse interactions/events
		addMouseListener(this);

		// Setup the status bar (JLabel) to display status messages...

		// Initialise the status bar with a new JLabel with space characters as the text
		statusBar = new JLabel("         ");

		// Set the status bar's font styles
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));

		// Set an invisible border to create padding for the status bar
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

		// Ensure all pixels are painted on the status bar
		statusBar.setOpaque(true);

		// Set the status bar's background colour to light grey
		statusBar.setBackground(Color.LIGHT_GRAY);

		// Layout of the panel is in border layout
		setLayout(new BorderLayout());

		// Add the status bar to layout, in the bottom region
		add(statusBar, BorderLayout.SOUTH);

		// Account for statusBar height in overall height
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

		// Assign a new Board instance to the board variable
		board = new Board();

		// Initialise the game board by calling initGame()
		initGame();
	}

	/**
	 * Main entry point for the program
	 * 
	 * @param args automatically generated String array
	 */
	public static void main(String[] args) {

		// Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// Create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);

				// Add a new GameMain panel to the frame
				frame.add(new GameMain());

				// Exit the frame when the user clicks close
				// (Jakhotia, n.d.) - Article used to find the correct code
				// https://www.codespeedy.com/how-to-exit-jframe-on-close-in-java-swing/
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Size the frame to fit its subcomponents
				frame.pack();

				// Centre the frame on the screen
				frame.setLocationRelativeTo(null);

				// Make the frame visible
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Custom painting code for this JPanel
	 * 
	 * @param g the Graphics object
	 */
	@Override
	public void paintComponent(Graphics g) {

		// Paint method for the user interface
		super.paintComponent(g);

		// Set the background colour to white
		setBackground(Color.WHITE);

		// Ask the game board to paint itself
		board.paint(g);

		// Set status bar message...

		// If the game is currently being played
		if (currentState == GameState.Playing) {

			// Set the status bar's foreground (text) colour to black
			statusBar.setForeground(Color.BLACK);

			// If the current player is X
			if (currentPlayer == Player.Cross) {

				// Display "X"'s Turn in the status bar
				statusBar.setText("\"X\"'s Turn");
			}
			// The current player is O
			else {

				// Display "O"'s Turn in the status bar
				statusBar.setText("\"O\"'s Turn");
			}
		}
		// Else if the game has ended in a draw
		else if (currentState == GameState.Draw) {

			// Set the status bar's foreground (text) colour to red
			statusBar.setForeground(Color.RED);

			// Display It's a Draw! Click to play again. in the status bar
			statusBar.setText("It's a Draw! Click to play again.");
		}
		// Else if X has won the game
		else if (currentState == GameState.Cross_won) {

			// Set the status bar's foreground (text) colour to red
			statusBar.setForeground(Color.RED);

			// Display 'X' Won! Click to play again. in the status bar
			statusBar.setText("'X' Won! Click to play again.");
		}
		// Else if O has won the game
		else if (currentState == GameState.Nought_won) {

			// Set the status bar's foreground (text) colour to red
			statusBar.setForeground(Color.RED);

			// Display 'O' Won! Click to play again. in the status bar
			statusBar.setText("'O' Won! Click to play again.");
		}
	}

	/**
	 * Initialises the game-board contents, current status of GameState, and Player
	 */
	public void initGame() {

		// Iterate over the number of rows
		for (int row = 0; row < ROWS; ++row) {

			// Iterate over the number of columns
			for (int col = 0; col < COLS; ++col) {

				// Set all of the cells' content to empty
				board.cells[row][col].content = Player.Empty;
			}
		}

		// Set the current state of the game to playing
		currentState = GameState.Playing;

		// Set X as the default current player to begin the game
		currentPlayer = Player.Cross;
	}

	/**
	 * Checks to see if the current player hasWon after each turn by putting their
	 * symbol in that position. If they have the GameState is set to won for that
	 * player. If there is no winner then isDraw is called to see if there is a
	 * deadlock. If not the GameState stays as Playing
	 * 
	 * @param thePlayer the current player who just had a turn
	 * @param row       the row clicked by the current player
	 * @param col       the column clicked by the current player
	 */
	public void updateGame(Player thePlayer, int row, int col) {

		// Check for a win after play
		if (board.hasWon(thePlayer, row, col)) {

			// If the current player who won is cross
			if (thePlayer == Player.Cross) {

				// Update the game's current state to Cross_won
				currentState = GameState.Cross_won;
			}
			// Else if the current player who won is nought
			else if (thePlayer == Player.Nought) {

				// Update the game's current state to Nought_won
				currentState = GameState.Nought_won;
			}
		}
		// Check for a game draw after play
		else if (board.isDraw()) {

			// Update the game's current state to Draw
			currentState = GameState.Draw;

		}

		// Otherwise there is no change to current state of playing
	}

	/**
	 * Event handler for the mouse click on the JPanel. If the selected cell is
	 * valid and Empty then the current player is added to the cell content.
	 * UpdateGame is called which will call the methods to check for a winner or
	 * Draw. If none, then the GameState remains playing. If there is a win or Draw
	 * then call is made to method that resets the game board. Finally a call is
	 * made to refresh the canvas so that the new symbol appears
	 * 
	 * @param e the mouse event for processing
	 */
	public void mouseClicked(MouseEvent e) {

		// Get the x coordinate of where the click event happened
		int mouseX = e.getX();

		// Get the y coordinate of where the click event happened
		int mouseY = e.getY();

		// Get the row clicked
		int rowSelected = mouseY / CELL_SIZE;

		// Get the column clicked
		int colSelected = mouseX / CELL_SIZE;

		// If the game is currently being played
		if (currentState == GameState.Playing) {

			// If the cell clicked is currently empty
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
					&& board.cells[rowSelected][colSelected].content == Player.Empty) {

				// Apply the current player's move to the board
				board.cells[rowSelected][colSelected].content = currentPlayer;

				// Check for the current player's win, a draw, or no change to the
				// currentState of playing by calling updateGame()
				updateGame(currentPlayer, rowSelected, colSelected);

				// Switch the player...

				// If X is currently playing
				if (currentPlayer == Player.Cross) {

					// Set the current player to O
					currentPlayer = Player.Nought;
				}
				// X is not currently playing
				else {

					// Set the current player to X
					currentPlayer = Player.Cross;
				}
			}
		}
		// The gameState is not playing
		else {

			// The game is over so restart
			initGame();
		}

		// Repaint/redraw the UI's graphics
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {

		// Auto-generated, event not used
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		// Auto-generated, event not used
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		// Auto-generated,event not used
	}

	@Override
	public void mouseExited(MouseEvent e) {

		// Auto-generated, event not used
	}
}
// (Open Polytechnic, 2022)
