package views_controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * @author Khang Tran Add a menu system so user can start a new game and swap
 *         views and/or strategies at runtime.
 */
public class CompositeMenus {
	private TicTacToeGUI gui;
	private MenuItem textArea = new MenuItem("TextArea");
	private MenuItem button = new MenuItem("Button");
	private MenuItem drawing = new MenuItem("Drawing");
	private Menu views = new Menu("Views");
	private MenuItem randomAI = new MenuItem("RandomAI");
	private MenuItem intermediateAI = new MenuItem("IntermediateAI");
	private Menu strategies = new Menu("Strategies");
	private MenuItem newGame = new MenuItem("New Game");
	private Menu option = new Menu("Options");
	private MenuBar menuBar = new MenuBar();

	/**
	 * constructor
	 * Add all menus into one that 
	 * contains menus
	 * @param guiRef
	 */
	public CompositeMenus(TicTacToeGUI guiRef) {
		gui = guiRef;
		views.getItems().addAll(textArea, button, drawing);
		strategies.getItems().addAll(randomAI, intermediateAI);
		option.getItems().addAll(views, strategies, newGame);
		menuBar.getMenus().addAll(option);
		registerListener();
	}

	public MenuBar getMenu() {
		return menuBar;
	}

	/**
	 * set menus on action
	 */
	private void registerListener() {
		textArea.setOnAction(new MenuListener());
		button.setOnAction(new MenuListener());
		drawing.setOnAction(new MenuListener());
		randomAI.setOnAction(new MenuListener());
		intermediateAI.setOnAction(new MenuListener());
		newGame.setOnAction(new MenuListener());
	}

	public class MenuListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			MenuItem menuClicked = (MenuItem) arg0.getSource();
			if (menuClicked.getText().equals("TextArea"))
				gui.switchTextView();
			if (menuClicked.getText().equals("Button"))
				gui.switchButtonView();
			if (menuClicked.getText().equals("Drawing"))
				gui.switchDrawingView();
			if (menuClicked.getText().equals("RandomAI"))
				gui.switchRandomAI();
			if (menuClicked.getText().equals("IntermediateAI"))
				gui.switchIntermediateAI();
			if (menuClicked.getText().equals("New Game"))
				gui.startNewGame();

		}

	}
}
