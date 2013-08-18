package search.heuristics;

import search.states.State;

/**
 * An interface representing a heuristic function. Note that, in order for A* to
 * find an optimal solution, the heuristic must be admissible - it should never
 * overestimate the cost of reaching the goal.
 * 
 * @author lackofcheese
 * @param <S>
 *            the type of state used.
 */
public interface Heuristic<S extends State> {
	/**
	 * Returns an estimate of the cost of reaching the goal state from the given
	 * state.
	 * 
	 * @param s
	 *            the state.
	 * @return an estimate of the cost to the goal.
	 */
	public double estimate(S s);
}
