package views_controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.OurObserver;
import model.TicTacToeGame;

/**
 * @author Khang Tran Apply the Observer Design Pattern to get a second view
 *         that is notified whenever the state of the model (TicTacToeGame)
 *         changes
 */
public class ButtonView extends GridPane implements OurObserver {
	private TicTacToeGame theGame;
	private Label gameLabel = new Label("Click to make a move");
	private Button[][] buttons = new Button[3][3];
	private int humanRow;
	private int humanCol;

	/**
	 * constructor
	 * 
	 * @param theModel
	 */
	public ButtonView(TicTacToeGame theModel) {
		theGame = theModel;
		initializePanel();
		registerListener();
		stateOfGame();
	}

	/**
	 * set up the board with array of buttons
	 */
	private void initializePanel() {
		// TODO Auto-generated method stub
		this.setHgap(5);
		this.setVgap(5);
		Font buttonFont = new Font("Courier New", 32);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j] = new Button();
				buttons[i][j].setFont(buttonFont);
				buttons[i][j].setPrefSize(65, 65);
				this.add(buttons[i][j], j + 5, i + 5);
			}
		}
		this.setColumnSpan(gameLabel, 3);
		Font labelFont = new Font("Courier New", 16);
		gameLabel.setFont(labelFont);
		this.add(gameLabel, 5, 15);
	}

	private void stateOfGame() {
		if (theGame.tied() || theGame.didWin('X') || theGame.didWin('O')) {
			changeLabel(gameLabel);
			this.setColumnIndex(gameLabel, 6);
			disabledButtons(buttons);
		}
		if (theGame.tied())
			gameLabel.setText("Tie");
		else if (theGame.didWin('X'))
			gameLabel.setText("X wins");
		else if (theGame.didWin('O'))
			gameLabel.setText("O wins");

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (theGame.getCharAt(i, j) == 'X')
					buttons[i][j].setText("X");
				if (theGame.getCharAt(i, j) == 'O')
					buttons[i][j].setText("O");
			}
		}
	}

	/**
	 * Set the action listeners for the buttons
	 */
	private void registerListener() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setOnAction(new ButtonListener());
			}
		}
	}

	/**
	 * capture button click
	 */
	public class ButtonListener implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			Button buttonClicked = (Button) arg0.getSource();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (buttons[i][j] == buttonClicked) {
						humanRow = i;
						humanCol = j;
						if (theGame.available(humanRow, humanCol)) {
							theGame.humanMove(humanRow, humanCol, false);
						} else
							gameLabel.setText("Invalid choice");

					}
				}
			}
		}

	}

	@Override
	public void update(Object theObserved) {
		// TODO Auto-generated method stub
		gameLabel.setText("Click to make a move");
		buttons[humanRow][humanCol].setText("X");
		int comRowMoved = theGame.getComRow();
		int comColMoved = theGame.getComCol();
		buttons[comRowMoved][comColMoved].setText("O");
		if (theGame.tied() || theGame.didWin('X') || theGame.didWin('O')) {
			changeLabel(gameLabel);
			this.setColumnIndex(gameLabel, 6);
			disabledButtons(buttons);
		}
		if (theGame.tied()) {
			gameLabel.setText("Tie");
		} else if (theGame.didWin('X')) {
			gameLabel.setText("X wins");
		} else if (theGame.didWin('O')) {
			gameLabel.setText("O wins");
		}
		System.out.println("update called from OurObservable TicTacToeGame\n" + theGame);
	}

	/**
	 * disable button when game is done
	 * 
	 * @param buttons
	 */
	private void disabledButtons(Button[][] buttons) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				buttons[i][j].setDisable(true);
			}
		}
	}

	private void changeLabel(Label label) {
		Font labelFont = new Font("Courier New", 20);
		label.setFont(labelFont);
	}

}
