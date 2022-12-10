import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {

	// Fixes warning - Eclipse suggested solution
	private static final long serialVersionUID = 1L;

	// Constants for game
	// Number of ROWS by COLS cell constants
	public static final int ROWS = 3;
	public static final int COLS = 3;
	public static final String TITLE = "Tic Tac Toe";

	// Constants for dimensions used for drawing
	// Cell width and height
	public static final int CELL_SIZE = 100;

	// Drawing canvas
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

	// Noughts and Crosses are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
	public static final int SYMBOL_STROKE_WIDTH = 8;

	/* Declare game object variables */
	// The game board
	private Board board;

	// COMPLETE: create the enumeration for the variable below (GameState
	// currentState)

	// The game's current state
	private GameState currentState;

	// The current player
	private Player currentPlayer;

	// For displaying game status message
	private JLabel statusBar;

	/**
	 * Constructor to setup the UI and game components on the panel
	 */
	public GameMain() {

		// COMPLETE: This JPanel fires a MouseEvent on MouseClicked so add required
		// event listener to 'this'.

		// Listen for users' mouse interactions/events
		addMouseListener(this);

		// Setup the status bar (JLabel) to display status message
		statusBar = new JLabel("         ");
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
		statusBar.setOpaque(true);
		statusBar.setBackground(Color.LIGHT_GRAY);

		// Layout of the panel is in border layout
		setLayout(new BorderLayout());
		add(statusBar, BorderLayout.SOUTH);
		
		// Account for statusBar height in overall height
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

		// COMPLETE: Create a new instance of the game "Board" class.
		
		// Assign a new Board instance to the board variable
		board = new Board();

		// COMPLETE: Call the method to initialise the game board
		
		// Initialise the game board by calling initGame()
		initGame();
	}

	/**
	 * Main entry point for the program
	 */
	public static void main(String[] args) {

		// Run GUI code in Event Dispatch thread for thread safety.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// Create a main window to contain the panel
				JFrame frame = new JFrame(TITLE);

				// TODO: create the new GameMain panel and add it to the frame

				// COMPLETE: set the default close operation of the frame to exit_on_close
				// (Jakhotia, n.d.) - Article used to find the correct code
				// https://www.codespeedy.com/how-to-exit-jframe-on-close-in-java-swing/
				
				// Exit the JFrame when the user clicks close
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Custom painting codes on this JPanel
	 */
	public void paintComponent(Graphics g) {
		// Fill background and set colour to white
		super.paintComponent(g);
		setBackground(Color.WHITE);
		// Ask the game board to paint itself
		board.paint(g);

		// Set status bar message
		if (currentState == GameState.Playing) {
			statusBar.setForeground(Color.BLACK);
			if (currentPlayer == Player.Cross) {

				// TODO: use the status bar to display the message "X"'s Turn

			} else {

				// TODO: use the status bar to display the message "O"'s Turn

			}
		} else if (currentState == GameState.Draw) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("It's a Draw! Click to play again.");
		} else if (currentState == GameState.Cross_won) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("'X' Won! Click to play again.");
		} else if (currentState == GameState.Nought_won) {
			statusBar.setForeground(Color.RED);
			statusBar.setText("'O' Won! Click to play again.");
		}
	}

	/**
	 * Initialise the game-board contents and the current status of GameState and
	 * Player)
	 */
	public void initGame() {
		for (int row = 0; row < ROWS; ++row) {
			for (int col = 0; col < COLS; ++col) {
				// All cells empty
				board.cells[row][col].content = Player.Empty;
			}
		}
		currentState = GameState.Playing;
		currentPlayer = Player.Cross;
	}

	/**
	 * After each turn check to see if the current player hasWon by putting their
	 * symbol in that position, If they have the GameState is set to won for that
	 * player If no winner then isDraw is called to see if deadlock, if not
	 * GameState stays as PLAYING
	 */
	public void updateGame(Player thePlayer, int row, int col) {
		// Check for win after play
		if (board.hasWon(thePlayer, row, col)) {

			// TODO: check which player has won and update the currentstate to the
			// appropriate gamestate for the winner

		} else if (board.isDraw()) {

			// TODO: set the currentstate to the draw gamestate

		}
		// Otherwise no change to current state of playing
	}

	/**
	 * Event handler for the mouse click on the JPanel. If selected cell is valid
	 * and Empty then current player is added to cell content. UpdateGame is called
	 * which will call the methods to check for winner or Draw. if none then
	 * GameState remains playing. If win or Draw then call is made to method that
	 * resets the game board. Finally a call is made to refresh the canvas so that
	 * new symbol appears
	 */
	public void mouseClicked(MouseEvent e) {
		// Get the coordinates of where the click event happened
		int mouseX = e.getX();
		int mouseY = e.getY();

		// Get the row and column clicked
		int rowSelected = mouseY / CELL_SIZE;
		int colSelected = mouseX / CELL_SIZE;

		// If the game is currently being played
		if (currentState == GameState.Playing) {

			// If the cell clicked is currently empty
			if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
					&& board.cells[rowSelected][colSelected].content == Player.Empty) {

				// Move
				board.cells[rowSelected][colSelected].content = currentPlayer;

				// Update currentState
				updateGame(currentPlayer, rowSelected, colSelected);

				// Switch player
				if (currentPlayer == Player.Cross) {
					currentPlayer = Player.Nought;
				} else {
					currentPlayer = Player.Cross;
				}
			}
		} else {
			// Game over and restart
			initGame();
		}

		// TODO: redraw the graphics on the UI

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
