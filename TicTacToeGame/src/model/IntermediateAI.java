/**
 * Rick suggests, the IntermediateAI first check to stop a win of the opponent, 
 * then look for its own win. If neither is found, select any other open
 * spot randomly. You may use any other strategy as long as it beats RandomAI.
 * 
 * @authors Rick Mercer and Khang Tran
 */
package model;

import java.util.Random;

public class IntermediateAI implements TicTacToeStrategy {
	private static Random generator;
	int count;
	private int plotRow;
	private int plotCol;

	public IntermediateAI() {
		generator = new Random();
		count = 0;
		plotRow = 0;
		plotCol = 0;
	}

	/**
	 * First move of AI should be random then the IntermediateAI look first to win
	 * If not possible, then stop the opponent from winning. If neither is possible,
	 * pick one of the available moves randomly.
	 */
	@Override
	public OurPoint desiredMove(TicTacToeGame theGame) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (!theGame.available(i, j)) {
					count++;
					break;
				}
			}
		}
		if (count < 1) {
			return computerStart(theGame);
		} else if (count == 1) {
			count++;
			return computerStart(theGame);
		} else if (count > 1) {
			return computerMove(theGame);
		}
		return null;
	}

	/*
	 * helper method for generating a random move return ourPoint
	 */
	private OurPoint computerStart(TicTacToeGame theGame) {
		boolean set = false;
		while (!set) {
			if (theGame.maxMovesRemaining() == 0)
				throw new IGotNowhereToGoException(" -- Hey there programmer, the board is filled");
			int row = generator.nextInt(3);
			int col = generator.nextInt(3);
			if (theGame.available(row, col)) {
				set = true;
				return new OurPoint(row, col);
			}
		}
		return null; // Avoid a compile-time error
	}

	private OurPoint computerMove(TicTacToeGame theGame) {
		// check if computer can win
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (theGame.available(i, j)) {
					char[][] tempBoard = copyBoard(theGame);
					tempBoard[i][j] = 'O';
					if (didWin('O', tempBoard))
						return new OurPoint(i, j);
				}
			}
		}
		// check if the computer need to block
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (theGame.available(i, j)) {
					char[][] tempBoard = copyBoard(theGame);
					tempBoard[i][j] = 'X';
					if (didWin('X', tempBoard))
						return new OurPoint(i, j);
				}
			}
		}
		// check if the human can't win then
		// find a possible way to win
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (theGame.getCharAt(i, j) == 'O') {
					if (possibleWin(theGame, i, j))
						return new OurPoint(plotRow, plotCol);
				}
			}
		}
		// If neither is possible, pick one of the available moves randomly
		return computerStart(theGame);
	}

	/*
	 * Check for the IntermediateAI look first to win
	 */
	private boolean possibleWin(TicTacToeGame theGame, int row, int col) {
		// Check the row for a win
		if (col == 0 && theGame.available(row, col + 1) && theGame.available(row, col + 2)) {
			plotRow = row;
			plotCol = col + 1;
			return true;
		}
		if (col == 1 && theGame.available(row, col - 1) && theGame.available(row, col + 1)) {
			plotRow = row;
			plotCol = col - 1;
			return true;
		}
		if (col == 2 && theGame.available(row, col - 2) && theGame.available(row, col - 1)) {
			plotRow = row;
			plotCol = col - 2;
			return true;
		}

		// Check the column for a win
		if (row == 0 && theGame.available(row + 1, col) && theGame.available(row + 2, col)) {
			plotRow = row + 1;
			plotCol = col;
			return true;
		}
		if (row == 1 && theGame.available(row - 1, col) && theGame.available(row + 1, col)) {
			plotRow = row - 1;
			plotCol = col;
			return true;
		}
		if (row == 2 && theGame.available(row - 2, col) && theGame.available(row - 1, col)) {
			plotRow = row - 2;
			plotCol = col;
			return true;
		}

		// Check the diagonal from top-left to bottom-right
		if (row == 0 && col == 0 && theGame.available(row + 1, col + 1) && theGame.available(row + 2, col + 2)) {
			plotRow = row + 1;
			plotCol = col + 1;
			return true;
		}
		if (row == 1 && col == 1 && theGame.available(row - 1, col - 1) && theGame.available(row + 1, col + 1)) {
			plotRow = row - 1;
			plotCol = col - 1;
			return true;
		}
		if (row == 1 && col == 1 && theGame.available(row - 1, col + 1) && theGame.available(row + 1, col - 1)) {
			plotRow = row - 1;
			plotCol = col + 1;
			return true;
		}
		if (row == 2 && col == 2 && theGame.available(0, 0) && theGame.available(1, 1)) {
			plotRow = row - 1;
			plotCol = col - 1;
			return true;
		}

		// Check the diagonal from top-right to bottom-left
		if (row == 0 && col == 2 && theGame.available(1, 1) && theGame.available(2, 0)) {
			plotRow = row + 1;
			plotCol = col - 1;
			return true;
		}
		if (row == 2 && col == 0 && theGame.available(0, 2) && theGame.available(1, 1)) {
			plotRow = row - 1;
			plotCol = col + 1;
			return true;
		}

		return false;
	}

	/*
	 * helper method to return a board from copying the original Game
	 */
	private char[][] copyBoard(TicTacToeGame theGame) {
		char[][] newBoard = new char[3][3];
		char[][] toCopy = theGame.getTicTacToeBoard();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newBoard[i][j] = toCopy[i][j];
			}
		}
		return newBoard;
	}

	/*
	 * helper method for the copyBoard to check if move is appropriate
	 */
	private boolean didWin(char playerChar, char[][] board) {
		return wonByRow(playerChar, board) || wonByCol(playerChar, board) || wonByDiagonal(playerChar, board);
	}

	private boolean wonByRow(char playerChar, char[][] board) {
		for (int r = 0; r < 3; r++) {
			int rowSum = 0;
			for (int c = 0; c < 3; c++)
				if (board[r][c] == playerChar)
					rowSum++;
			if (rowSum == 3)
				return true;
		}
		return false;
	}

	private boolean wonByCol(char playerChar, char[][] board) {
		for (int c = 0; c < 3; c++) {
			int colSum = 0;
			for (int r = 0; r < 3; r++)
				if (board[r][c] == playerChar)
					colSum++;
			if (colSum == 3)
				return true;
		}
		return false;
	}

	private boolean wonByDiagonal(char playerChar, char[][] board) {
		// Check Diagonal from upper left to lower right
		int sum = 0;
		for (int r = 0; r < 3; r++)
			if (board[r][r] == playerChar)
				sum++;
		if (sum == 3)
			return true;

		// Check Diagonal from upper right to lower left
		sum = 0;
		for (int r = 3 - 1; r >= 0; r--)
			if (board[3 - r - 1][r] == playerChar)
				sum++;
		if (sum == 3)
			return true;

		// No win on either diagonal
		return false;
	}
}
