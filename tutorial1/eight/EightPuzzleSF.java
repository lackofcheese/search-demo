package tutorial1.eight;

import java.util.HashMap;
import java.util.Map;

import search.sfs.SuccessorFunction;

/**
 * A successor function; generates successor states for an 8-puzzle state.
 * @author lackofcheese
 */
public class EightPuzzleSF implements SuccessorFunction<EightPuzzleState> {
	@Override
	public Map<EightPuzzleState, Double> getSuccessors(EightPuzzleState s) {
		// The mapping of successors to costs.
		HashMap<EightPuzzleState, Double> map = new HashMap<EightPuzzleState, Double>();
		int emptyRow = s.getRowOf(0); // The row of the empty tile.
		int emptyCol = s.getColOf(0); // The column of the empty tile.
		
		int newCol = emptyCol; // The column of the tile to swap with the empty tile.
		int newRow; // The row of the tile to swap with the empty tile.
		for (newRow = emptyRow-1; newRow <= emptyRow+1; newRow += 2) {
			if (newRow < 0 || newRow > 2) {
				continue;
			}
			map.put(s.createSwapped(emptyRow, emptyCol, newRow, newCol), 1.0);
		}
		newRow = emptyRow;
		for (newCol = emptyCol-1; newCol <= emptyCol+1; newCol += 2) {
			if (newCol < 0 || newCol > 2) {
				continue;
			}
			map.put(s.createSwapped(emptyRow, emptyCol, newRow, newCol), 1.0);
		}		
		return map;
	}
}