package views_controllers;

import java.util.Scanner;

import model.IntermediateAI;
import model.RandomAI;
import model.TicTacToeGame;
/**
 * @author Khang Tran
 * main class for testing the use of 
 * intermediate and random AI
 */
public class TicTacToeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TicTacToeGame game = new TicTacToeGame();
		//Set to IntermediateAI
		game.setComputerPlayerStrategy(new IntermediateAI());
		Scanner kb = new Scanner(System.in);
		boolean stop = false;
		while (!stop) {
			System.out.print("Enter row and column: ");
			int row = kb.nextInt();
			int col = kb.nextInt();
			if (game.available(row, col)) {
				game.humanMove(row, col, stop);
				System.out.println(game.toString());
				if (game.tied()) { 
					stop = true;
					System.out.println("Tie");
				} else if (game.didWin('X')) {
					stop = true;
					System.out.println("X wins");
				} else if (game.didWin('O')) {
					stop = true;
					System.out.println("O wins");
				}
			} else {
				System.out.println("Square taken, try again.");
			}

		}

	}

}
