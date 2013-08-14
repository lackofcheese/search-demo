package search.algorithms.norevisits;
import java.util.LinkedList;
import java.util.Queue;

import search.goals.GoalTest;
import search.sfs.SuccessorFunction;
import search.states.State;

/**
 * An implementation of a BFS that does not revisit states, 
 * using a LinkedList as a queue for holding the states to visit.
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public class BreadthFirstSearch<S extends State> extends SearchWithoutRevisits<S> {
	/**
	 * Constructs a BFS with the given parameters.
	 * @param root the initial state.
	 * @param goalTest a test for goal states.
	 * @param sf the successor function.
	 */
	public BreadthFirstSearch(S root, GoalTest<S> goalTest,
			SuccessorFunction<S> sf) {
		super(root, goalTest, sf);
	}

	/** The queue holding the states to be visited */
	private Queue<QueueEntry> queue;
	
	@Override
	protected void initSearch() {
		super.initSearch();
		queue = new LinkedList<QueueEntry>();
		this.enqueue(new QueueEntry(getRoot(), null, 0, 0.0, 0.0, null));
	}
	@Override
	protected void enqueue(QueueEntry qe) {
		queue.add(qe);	
	}
	@Override
	protected QueueEntry dequeue() {
		return queue.remove();
	}
	@Override
	protected boolean queueEmpty() {
		return queue.isEmpty();
	}
}
