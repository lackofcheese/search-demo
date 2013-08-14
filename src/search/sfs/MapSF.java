package search.sfs;

import java.util.HashMap;
import java.util.Map;

import search.states.State;

/**
 * A successor function implemented directly as a mapping.
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public class MapSF<S extends State> implements SuccessorFunction<S> {
	/** Stores the mapping of states to their successors and costs. */
	private Map<S, Map<S, Double>> stateMap;
	
	/**
	 * Constructor; creates a blank mapping where all states have no successors.
	 */
	public MapSF() {
		this.stateMap = new HashMap<S, Map<S, Double>>();
	}
	
	/**
	 * Private method for retrieving the successors of a state,
	 * and adding that state to the mapping if it is not yet present.
	 * @param s the state
	 * @return a mapping of successor states to costs.
	 */
	private Map<S, Double> get(S s) {
		Map<S, Double> map = stateMap.get(s);
		if (map == null) {
			map = new HashMap<S, Double>();
			stateMap.put(s, map);
		}
		return map;
	}
	
	/**
	 * Adds an edge with a given cost from the predecessor state
	 * to the successor state.
	 * @param pred the predecessor state
	 * @param succ the successor state
	 * @param cost the cost of the edge between the two states.
	 */
	public void addSuccessor(S pred, S succ, double cost) {
		this.get(pred).put(succ, cost);
	}
	
	@Override
	public Map<S, Double> getSuccessors(S s) {
		return new HashMap<S, Double>(this.get(s));
	}
}
