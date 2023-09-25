package views_controllers;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ComputerPlayer;
import model.IntermediateAI;
import model.OurObserver;
import model.RandomAI;
import model.TicTacToeGame;
/**
 * Play TicTacToe the computer that can have different AIs to beat you. 
 * Select the Options menus to begin a new game, switch strategies for 
 * the computer player (BOT or AI), and to switch between the two views.
 * 
 * This class represents an event-driven program with a graphical user 
 * interface as a controller between the view and the model. It has 
 * event handlers to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two 
 * views every time the state of the Tic Tac Toe game changes:
 * 
 *  1) whenever you make a move by clicking a button or an area of either view
 *  2) whenever the computer AI makes a move
 *  3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 * @author Rick Mercer and Khang Tran
 */
public class TicTacToeGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private TicTacToeGame theGame;
	private CompositeMenus menu;
	private OurObserver currentView;
	private OurObserver textAreaView;
	private OurObserver buttonView;
	private OurObserver drawingView;

	private BorderPane window;
	public static final int width = 254;
	public static final int height = 360;

	public void start(Stage stage) {
		stage.setTitle("Tic Tac Toe");
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);
		menu = new CompositeMenus(this);
		initializeGameForTheFirstTime();
		buttonView = new ButtonView(theGame);
		drawingView = new DrawingView(theGame);
		theGame.addObserver(buttonView);
		theGame.addObserver(drawingView);
		textAreaView = new TextAreaView(theGame);
		theGame.addObserver(textAreaView);
		setViewTo(drawingView);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Set the game to the default of an empty board and the random AI.
	 */
	public void initializeGameForTheFirstTime() {
		theGame = new TicTacToeGame();
		window.setTop(menu.getMenu());
		// This event driven program will always have
		// a computer player who takes the second turn
		theGame.setComputerPlayerStrategy(new IntermediateAI());
	}

	private void setViewTo(OurObserver newView) {
		window.setCenter(null);
		currentView = newView;
		window.setCenter((Node) currentView);
	}
	
	/**
	 * switch to textView during gameplay
	 */
	public void switchTextView() {
		setViewTo(textAreaView);
	}

	/**
	 * switch to buttonView during gameplay
	 */
	public void switchButtonView() {
		buttonView = new ButtonView(theGame);
		theGame.addObserver(buttonView);
		setViewTo(buttonView);
	}

	/**
	 * switch to drawingView during gameplay
	 */
	public void switchDrawingView() {
		drawingView = new DrawingView(theGame);
		theGame.addObserver(drawingView);
		setViewTo(drawingView);
	}

	/**
	 * switch AI during gameplay
	 */
	public void switchRandomAI() {
		theGame.setComputerPlayerStrategy(new RandomAI());
	}

	/**
	 * switch AI during gameplay
	 */
	public void switchIntermediateAI() {
		theGame.setComputerPlayerStrategy(new IntermediateAI());
	}

	/**
	 * start a new game while keep
	 * the same AI and view
	 */
	public void startNewGame() {
		ComputerPlayer com = theGame.getComputerPlayer();
		theGame = new TicTacToeGame();
		if (currentView.equals(textAreaView)) {
			textAreaView = new TextAreaView(theGame);
			theGame.addObserver(textAreaView);
			setViewTo(textAreaView);
			theGame.setComputerPlayerStrategy(com.getStrategy());
		}
		if (currentView.equals(buttonView)) {
			buttonView = new ButtonView(theGame);
			theGame.addObserver(buttonView);
			setViewTo(buttonView);
			theGame.setComputerPlayerStrategy(com.getStrategy());
		}
		if (currentView.equals(drawingView)) {
			drawingView = new DrawingView(theGame);
			theGame.addObserver(drawingView);
			setViewTo(drawingView);
			theGame.setComputerPlayerStrategy(com.getStrategy());
		}

	}
}