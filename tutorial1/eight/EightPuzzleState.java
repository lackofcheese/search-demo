package tutorial1.eight;

import search.states.State;

/**
 * A immutable representation of an 8-puzzle state.
 * @author lackofcheese
 */
public class EightPuzzleState implements State {
	/** A 3x3 grid to hold the state */
	private int[][] grid = new int[3][3];
	/** Stores the row for each tile */
	private int[] rowNumbers = new int[9];
	/** Stores the column for each tile */
	private int[] colNumbers = new int[9];
	
	/** 
	 * Constructs an 8-puzzle state from a given 3x3 grid.
	 * @param grid a 3x3 2-D array of integers which must contain each number
	 * from 0 to 8.
	 */
	public EightPuzzleState(int[][] grid) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				int value = grid[i][j];
				rowNumbers[value] = i;
				colNumbers[value] = j;
				this.grid[i][j] = value;
			}
		}
	}
	
	/** 
	 * Constructs an 8-puzzle state from a string representation,
	 * which must be the tile numbers separated by whitespace.
	 * @param s the string containing the state representation.
	 */
	public EightPuzzleState(String s) {
		String[] values = s.split("\\s+");
		for (int i = 0; i < 9; i++) {
			int value = Integer.valueOf(values[i]);
			rowNumbers[value] = i/3;
			colNumbers[value] = i%3;
			grid[i/3][i%3] = value;
		}
	}
	
	/**
	 * Returns a string representation, which is simply
	 * all nine tile numbers in order with spaces between them.
	 * @return a string representation of the state.
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i > 0 || j > 0) {
					builder.append(" ");
				}
				builder.append(grid[i][j]);
			}
		}
		return builder.toString();
	}
	
	/**
	 * Returns a copy of the eight puzzle grid.
	 * @return a copy of the eight puzzle grid.
	 */
	public int[][] getGrid() {
		int[][] newGrid = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newGrid[i][j] = grid[i][j];
			}
		}
		return newGrid;
	}
	
	/**
	 * Returns the number of the tile at the given row and column.
	 * @param row the row.
	 * @param col the column.
	 * @return the number of the tile at the given row and column.
	 */
	public int getValueAt(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Returns the row of the given tile.
	 * @param tileNo the tile.
	 * @return the row of the given tile.
	 */
	public int getRowOf(int tileNo) {
		return rowNumbers[tileNo];
	}

	/**
	 * Returns the column of the given tile.
	 * @param tileNo the tile.
	 * @return the column of the given tile.
	 */
	public int getColOf(int tileNo) {
		return colNumbers[tileNo];
	}
	
	/**
	 * Returns a new EightPuzzleState with the
	 * tiles at(r1, c1) and (r2, c2) swapped.
	 * @param r1 the row of tile #1.
	 * @param c1 the column of tile #1.
	 * @param r2 the row of tile #2.
	 * @param c2 the column of tile #2.
	 * @return a new EightPuzzleState with the given tile positions swapped.
	 */
	public EightPuzzleState createSwapped(int r1, int c1, int r2, int c2) {
		int[][] newGrid = this.getGrid();
		int temp = newGrid[r1][c1];
		newGrid[r1][c1] = newGrid[r2][c2];
		newGrid[r2][c2] = temp;
		return new EightPuzzleState(newGrid);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof EightPuzzleState)) {
			return false;
		}
		EightPuzzleState otherState = (EightPuzzleState)obj;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.grid[i][j] != otherState.grid[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int code = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				code = code*9 + grid[i][j];
			}
		}
		return code;
	}
}