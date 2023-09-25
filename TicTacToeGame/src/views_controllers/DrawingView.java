package views_controllers;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import model.OurObserver;
import model.TicTacToeGame;

/**
 * @author Khang Tran a view that draws the TTT squares and adds X and O images
 *         when the square is clicked
 */
public class DrawingView extends BorderPane implements OurObserver {
	private TicTacToeGame theGame;
	private Label gameLabel = new Label("Click to make a move");
	private GraphicsContext gc;
	private Canvas canvas;
	Image X = new Image("file:images/X.png", false);
	Image O = new Image("file:images/O.png", false);

	/**
	 * constructor
	 * 
	 * @param theModel
	 */
	public DrawingView(TicTacToeGame theModel) {
		theGame = theModel;
		initializePanel();
		registerHandlers();
		stateOfGame();
	}

	/**
	 * set up the game and canvas
	 */
	private void initializePanel() {
		Font labelFont = new Font("Courier New", 16);
		gameLabel.setFont(labelFont);
		gameLabel.setPadding(new Insets(30, 0, 0, 20));
		this.setTop(gameLabel);
		canvas = new Canvas(230, 230);
		this.setCenter(canvas);
		gc = canvas.getGraphicsContext2D();
		drawSquares(gc);
	}

	/**
	 * x1 the X coordinate of the starting point of the line. y1 the Y coordinate of
	 * the starting point of the line. x2 the X coordinate of the ending point of
	 * the line. y2 the Y coordinate of the ending point of the line.
	 * 
	 * @param gc
	 */
	private void drawSquares(GraphicsContext gc) {
		gc.strokeRect(1, 1, 228, 228);
		// Draw the outer border as a square
		gc.strokeLine(75, 0, 75, 228);
		gc.strokeLine(150, 0, 150, 228);
		gc.strokeLine(0, 75, 228, 75);
		gc.strokeLine(0, 150, 228, 150);
	}

	private void stateOfGame() {
		if (theGame.tied()) {
			gameLabel.setText("Tie");
			canvas.setOnMousePressed(null);
		} else if (theGame.didWin('X')) {
			gameLabel.setText("X wins");
			canvas.setOnMousePressed(null);
		} else if (theGame.didWin('O')) {
			gameLabel.setText("O wins");
			canvas.setOnMousePressed(null);
		}
		for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++) {
	            if (theGame.getCharAt(i, j) == 'X')
	                gc.drawImage(X, j * cellSize, i * cellSize, cellSize, cellSize); // Notice the j (col) and i (row)
	            if (theGame.getCharAt(i, j) == 'O')
	                gc.drawImage(O, j * cellSize, i * cellSize, cellSize, cellSize); // Notice the j (col) and i (row)
	        }
	    }
	}

	/**
	 * capture a mouse click
	 */
	private void registerHandlers() {
		// capture a mouse click in canvas
		canvas.setOnMousePressed(new MousePressed());
	}

	double cellSize = 76;
	double cursorX, cursorY;
	int playerRow, playerCol;

	/**
	 * capture a mouse click
	 */
	private class MousePressed implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			// Toggle drawing
			cursorX = event.getX();
			cursorY = event.getY();
			playerCol = (int) (cursorX / cellSize);
			playerRow = (int) (cursorY / cellSize);
			if (theGame.available(playerRow, playerCol)) {
				theGame.humanMove(playerRow, playerCol, false);
			} else {
				gameLabel.setText("Invalid choice");
			}
		}
	}

	@Override
	public void update(Object theObserved) {
		// TODO Auto-generated method stub
		gameLabel.setText("Click to make a move");
		gc.drawImage(X, playerCol * cellSize, playerRow * cellSize, cellSize, cellSize);
		gc.drawImage(O, theGame.getComCol() * cellSize, theGame.getComRow() * cellSize, cellSize, cellSize);
		if (theGame.tied()) {
			gameLabel.setText("Tie");
			canvas.setOnMousePressed(null);
		} else if (theGame.didWin('X')) {
			gameLabel.setText("X wins");
			canvas.setOnMousePressed(null);
		} else if (theGame.didWin('O')) {
			gameLabel.setText("O wins");
			canvas.setOnMousePressed(null);
		}
		System.out.println("update called from OurObservable TicTacToeGame\n" + theGame);
	}

}
