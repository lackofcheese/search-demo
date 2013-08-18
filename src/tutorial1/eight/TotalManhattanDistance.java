package tutorial1.eight;

import search.heuristics.Heuristic;

/**
 * An admissible heuristic for the 8-puzzle based on the total Manhattan
 * distance between the tiles and their proper locations.
 * 
 * @author lackofcheese
 * 
 */
public class TotalManhattanDistance implements Heuristic<EightPuzzleState> {
	/** The desired state */
	private EightPuzzleState goalState;

	/**
	 * Constructs a total Manhattan distance heuristic to the given goal state.
	 * 
	 * @param goalState
	 *            the goal state.
	 */
	public TotalManhattanDistance(EightPuzzleState goalState) {
		this.goalState = goalState;
	}

	@Override
	public double estimate(EightPuzzleState s) {
		int estimate = 0;
		for (int tileNo = 1; tileNo < 9; tileNo++) {
			estimate += Math.abs(s.getRowOf(tileNo)
					- goalState.getRowOf(tileNo));
			estimate += Math.abs(s.getColOf(tileNo)
					- goalState.getColOf(tileNo));
		}
		return estimate;
	}
}