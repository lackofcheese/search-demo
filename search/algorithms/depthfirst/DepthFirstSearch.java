package search.algorithms.depthfirst;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import search.algorithms.QueueSearch;
import search.goals.GoalTest;
import search.sfs.SuccessorFunction;
import search.states.State;

/**
 * An implementation of a DFS using the QueueSearch class with a Stack as the queue.
 * 
 * In order to remember the path taken to the current state, expanded states are put
 * back on the stack under their successors, with a boolean flag so that they
 * are known to have been expanded.
 * 
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public class DepthFirstSearch<S extends State> extends QueueSearch<S, Boolean> {
	/**
	 * Constructs a depth first search with the given parameters.
	 * @param root the initial state.
	 * @param goalTest a test for the goal.
	 * @param sf a successor function.
	 */
	public DepthFirstSearch(S root, GoalTest<S> goalTest,
			SuccessorFunction<S> sf) {
		super(root, goalTest, sf);
	}
	
	/** A stack to store the path taken to reach the current state. */
	protected Stack<S> pathStack; 
	/** The stack of states to be processed. */
	protected Stack<QueueEntry> queue;
	
	@Override
	protected void initSearch() {
		queue = new Stack<QueueEntry>();
		pathStack = new Stack<S>();
		this.enqueue(new QueueEntry(getRoot(), null, 0, 0.0, 0.0, false));
	}
	@Override
	protected void enqueue(QueueEntry qe) {
		queue.push(qe);
	}
	@Override
	protected QueueEntry dequeue() {
		return queue.pop();
	}
	@Override
	protected boolean queueEmpty() {
		return queue.isEmpty();
	}
	@Override
	public boolean processCurrentEntry() {
		/* If the current entry was already expanded, we are backtracking;
		 * this means it cannot be the goal, and is no longer on the path. */
		if (currentEntry.getData()) {
			pathStack.pop();
			return false;
		}
		
		S currentState = currentEntry.getState();
		// If this is the goal state, we add it to the path and conclude.
		if (getGoalTest().isGoal(currentState)) {
			pathStack.push(currentState);
			return true;
		}
		
		// It's not the goal state, so we expand it.
		int currentDepth = currentEntry.getDepth();
		double currentCost = currentEntry.getTotalCost();
		// To remember the path, we re-queue the state under its successors, and add it to the path.
		this.enqueue(new QueueEntry(currentState, currentEntry.getPred(), currentDepth,
				currentCost, 0.0, true));
		pathStack.push(currentState);
		
		// Retrieve and process the successors.
		Map<S, Double> succMap = getSF().getSuccessors(currentState);
		for (Map.Entry<S, Double> entry : succMap.entrySet()) {
			S s2 = entry.getKey();
			// Enqueue the successors.
			this.enqueue(new QueueEntry(s2,	currentState, currentDepth + 1, 
					currentCost + entry.getValue(),	0.0, false));
		}
		return false;
	}

	@Override
	public List<S> getGoalPath() {
		return new ArrayList<S>(pathStack);
	}
}
