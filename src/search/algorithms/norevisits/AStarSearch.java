package search.algorithms.norevisits;

import java.util.PriorityQueue;

import search.goals.GoalTest;
import search.heuristics.Heuristic;
import search.sfs.SuccessorFunction;
import search.states.State;

/**
 * An implementation of an A* search that does not revisit states, using a
 * PriorityQueue to rank the states to be visited.
 * 
 * Notably, if an always-zero heuristic is used, A* search is equivalent to a
 * uniform cost search.
 * 
 * @author lackofcheese
 * @param <S>
 *            the type of state used.
 */
public class AStarSearch<S extends State> extends SearchWithoutRevisits<S> {
	/**
	 * Constructs an A* search with the given parameters.
	 * 
	 * @param root
	 *            the initial state.
	 * @param goalTest
	 *            a test for goal states.
	 * @param sf
	 *            the successor function.
	 * @param heuristic
	 *            the heuristic function.
	 */
	public AStarSearch(S root, GoalTest<S> goalTest, SuccessorFunction<S> sf,
			Heuristic<S> heuristic) {
		super(root, goalTest, sf, heuristic);
	}

	/**
	 * Constructs a uniform cost search with the given parameters.
	 * 
	 * @param root
	 *            the initial state.
	 * @param goalTest
	 *            a test for goal states.
	 * @param sf
	 *            the successor function.
	 */
	public AStarSearch(S root, GoalTest<S> goalTest, SuccessorFunction<S> sf) {
		super(root, goalTest, sf);
	}

	/** A priority queue holding the states to be searched. */
	PriorityQueue<QueueEntry> queue;

	@Override
	protected void initSearch() {
		super.initSearch();
		queue = new PriorityQueue<QueueEntry>();
		this.enqueue(new QueueEntry(getRoot(), null, 0, 0.0, getHeuristic()
				.estimate(getRoot()), null));
	}

	@Override
	protected boolean queueEmpty() {
		return queue.isEmpty();
	}

	@Override
	protected void enqueue(QueueEntry qe) {
		queue.add(qe);
	}

	@Override
	protected QueueEntry dequeue() {
		return queue.remove();
	}
}
