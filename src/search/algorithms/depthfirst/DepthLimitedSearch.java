package search.algorithms.depthfirst;

import search.goals.GoalTest;
import search.sfs.SuccessorFunction;
import search.states.State;

/**
 * A depth-limited search, implemented as a modification of the DFS
 * in which states are not enqueued if they are past the depth limit.
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public class DepthLimitedSearch<S extends State> extends DepthFirstSearch<S> {
	/** The depth limit for the search. */
	private int depthLimit;
	
	/**
	 * Constructs a depth-limited search with the given parameters.
	 * @param depthLimit the depth limit.
	 * @param root the initial state.
	 * @param goalTest the test for the goal state.
	 * @param sf the successor function.
	 */
	public DepthLimitedSearch(int depthLimit, S root, GoalTest<S> goalTest,
			SuccessorFunction<S> sf) {
		super(root, goalTest, sf);
		this.depthLimit = depthLimit;
	}
	
	/**
	 * Returns the depth limit for this search.
	 * @return the depth limit for this search.
	 */
	public int getDepthLimit() {
		return depthLimit;
	}

	/**
	 * {@inheritDoc}
	 * As a modification, the state is not enqueued if it is beyond the
	 * depth limit.
	 */
	@Override
	protected void enqueue(QueueEntry qe) {
		if (qe.getDepth() <= depthLimit) {
			queue.push(qe);
		}
	}
}
