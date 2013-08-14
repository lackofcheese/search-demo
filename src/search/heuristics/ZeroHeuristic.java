package search.heuristics;
import search.states.State;

/**
 * A trivial admissible heuristic that always returns zero.
 * This is the simplest heuristic estimate - it always returns zero, 
 * and hence is also 100% useless as a heuristic.
 * 
 * Notably, using this heuristic with A* causes it to function exactly
 * the same as a Uniform Cost Search, hence no separate implementation
 * of UCS is required.
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public class ZeroHeuristic<S extends State> implements Heuristic<S> {
	/**
	 * Returns an estimate (zero) of the cost to reach the goal
	 * from the given state.
	 * @param s the state.
	 * @return always estimates the cost as zero.
	 */
	@Override
	public double estimate(S s) {
		return 0;
	}
}
