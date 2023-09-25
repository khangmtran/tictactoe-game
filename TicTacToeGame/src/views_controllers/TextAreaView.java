package views_controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import model.OurObserver;
import model.TicTacToeGame;
/**
 * This is the beginning of one view of a Tic Tac Toe game using
 * two TextField objects and one TextArea. The other two views
 * of ButtonView and DrawingView follow the same structure as this.
 * 
 * @author Rick Mercer and Khang Tran
 */
public class TextAreaView extends BorderPane implements OurObserver {

	private TicTacToeGame theGame;
	private TextField rowInput = new TextField();
	private TextField colInput = new TextField();
	private Label rowLabel = new Label();
	private Label colLabel = new Label();
	private TextArea message = new TextArea();
	private Button inputButton = new Button("Make Move");

	public TextAreaView(TicTacToeGame theModel) {
		theGame = theModel;
		rowInput.setText("");
		colInput.setText("");
		rowLabel.setText("Enter row");
		colLabel.setText("Enter Column");
		initializePanel();
		registerListener();
	}

	private void initializePanel() {
		// Use a grid pane inside a border pane
		// to store the row, col input and label
		// and the input button
		GridPane gridPane = new GridPane();
		rowInput.setMaxWidth(50);
		colInput.setMaxWidth(50);
		gridPane.add(rowLabel, 6, 4);
		gridPane.add(rowInput, 5, 4);
		gridPane.add(colLabel, 6, 5);
		gridPane.add(colInput, 5, 5);
		gridPane.add(inputButton, 5, 6);
		gridPane.setHgap(12);
		gridPane.setVgap(12);
		this.setCenter(gridPane);
		message.setStyle("-fx-border-color: blue; border-width: 10;");
		// TextArea with courier new font
		Font font = new Font("Courier New", 32);
		message.setFont(font);
		message.setEditable(false);
		message.setText(theGame.toString());
		this.setBottom(message);
		Insets marginForMessage = new Insets(20, 0, 0, 0); // Top margin of 10
		BorderPane.setMargin(message, marginForMessage);
	}

	/**
	 * Set the action listeners for the buttons
	 */
	private void registerListener() {
		// TODO Auto-generated method stub
		inputButton.setOnAction(new inputButtonListener());
	}

	public class inputButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			// Handler for the button
			// Send a trim message to both getText messages
			int row = Integer.parseInt(rowInput.getText().trim());
			int col = Integer.parseInt(colInput.getText().trim());
			if (theGame.available(row, col)) {
				theGame.humanMove(row, col, false);
			}
			// Set the button's text to "Invalid Choice" if the row or column is out of
			// range, not an integer, or the space was already chosen.
			else {
				inputButton.setText("Invalid choice");
			}

		}

	}

	// This method is called by Observable's notifyObservers()
	@Override
	public void update(Object observable) {
		// Send a message to the game with a valid move
		// Clear the TextFields after a valid move is made
		inputButton.setText("Make Move");
		message.setText(theGame.toString());
		// Set the text of the button to either "X wins", "O wins", or "Tie"
		if (theGame.tied())
			inputButton.setText("Tie");
		else if (theGame.didWin('X'))
			inputButton.setText("X wins");
		else if (theGame.didWin('O'))
			inputButton.setText("O wins");
		System.out.println("update called from OurObservable TicTacToeGame\n" + theGame);
	}
}