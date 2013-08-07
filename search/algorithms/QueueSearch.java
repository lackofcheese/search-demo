package search.algorithms;

import search.goals.GoalTest;
import search.heuristics.Heuristic;
import search.sfs.SuccessorFunction;
import search.states.State;

/**
 * An abstraction of a search algorithm that works by using a queue to store
 * the states as it searches them, in addition to any extra information.
 * 
 * In order to realize this abstraction, the core functionality must be added to the 
 * methods 
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public abstract class QueueSearch<S extends State, Data> extends AbstractSearchAlgorithm<S> {
	/**
	 * Sets up a queue search with the given parameters.
	 * @param root the initial state.
	 * @param goalTest a test for the goal state(s).
	 * @param sf the successor function.
	 * @param heuristic a heuristic function.
	 */
	public QueueSearch(S root, GoalTest<S> goalTest, SuccessorFunction<S> sf, Heuristic<S> heuristic) {
		super(root, goalTest, sf, heuristic);
	}
	
	/**
	 * Sets up a queue search with the given parameters, and no heuristics.
	 * @param root the initial state.
	 * @param goalTest a test for the goal state(s).
	 * @param sf the successor function.
	 */
	public QueueSearch(S root, GoalTest<S> goalTest, SuccessorFunction<S> sf) {
		super(root, goalTest, sf);
	}

	/**
	 * A class representing a single entry in the search queue; this includes
	 * all information likely to be relevant to the search.
	 * 
	 * Also, the default comparator for this class compares based on the total
	 * value of the cost so far and the heuristic - this means that
	 * a PriorityQueue-based implementation of A* will function naturally.
	 * @author lackofcheese
	 */
	public class QueueEntry implements Comparable<QueueEntry> {
		/**
		 * The state itself.
		 */
		private S state;
		/**
		 * The predecessor of that state in the search tree.
		 */
		private S pred;
		/**
		 * The depth in the search tree.
		 */
		private int depth;
		/**
		 * The total cost so far to reach the state.
		 */
		private double totalCost;
		/**
		 * A heuristic estimate of the remaining cost to the goal; this will
		 * be zero if no heuristic is being used.
		 */
		private double heuristicEstimate;
		
		/**
		 * If applicable, this holds any additional data needed for specific
		 * search algorithms to function more conveniently.
		 */
		private Data data;
		
		/**
		 * Constructs a queue entry with the given parameters.
		 * @param s the state.
		 * @param pred the state's predecessor in the search tree.
		 * @param depth the depth in the search tree. 
		 * @param totalCost the total cost so far to reach the state.
		 * @param heuristicEstimate an estimate of the cost to the goal;
		 * this should be zero if no heuristic is used.
		 * @param data if applicable, any additional data required.
		 */
		public QueueEntry(S s, S pred, int depth, double totalCost, double heuristicEstimate, Data data) {
			this.state = s;
			this.pred = pred;
			this.depth = depth;
			this.totalCost = totalCost;
			this.heuristicEstimate = heuristicEstimate;
			this.data = data;
		}

		/**
		 * Returns the state this queue entry is for.
		 * @return the state this queue entry is for.
		 */
		public S getState() {
			return state;
		}
		
		/**
		 * Returns the predecessor of this state.
		 * @return the predecessor of this state.
		 */
		public S getPred() {
			return pred;
		}
		
		/**
		 * Returns the depth in the search tree.
		 * @return the depth in the search tree.
		 */
		public int getDepth() {
			return depth;
		}
		
		/**
		 * Returns the total cost so far.
		 * @return the total cost so far.
		 */
		public double getTotalCost() {
			return totalCost;
		}
		
		/**
		 * Returns a heuristic estimate of the remaining cost (zero if N/A).
		 * @return a heuristic estimate of the remaining cost (zero if N/A).
		 */
		public double getHeuristicEstimate() {
			return heuristicEstimate;
		}
		
		/**
		 * Returns any additional data stored.
		 * @return any additional data stored.
		 */
		public Data getData() {
			return data;
		}

		/**
		 * Implements a comparison between queue entries, based on the
		 * sum of the total cost so far and the heuristic estimate.
		 * This makes A* and UCS function naturally with a PriorityQueue.
		 */
		@Override
		public int compareTo(QueueEntry arg0) {
			return Double.compare(this.totalCost + this.heuristicEstimate, arg0.totalCost + arg0.heuristicEstimate);
		}
	}

	/** The queue entry currently being processed. */
	protected QueueEntry currentEntry;
	/** True if a goal has been found, and false otherwise. */
	protected boolean goalFound = false;
	/**
	 * A basic implementation of a queue-based search algorithm.
	 * The core is a loop that dequeues and processes states until
	 * either a goal is found, or the queue is empty.
	 */
	public void search() {
		this.initSearch();
		goalFound = false;
		while(!queueEmpty()) {
			currentEntry = this.dequeue();
			if (goalFound = processCurrentEntry()) {
				return;
			}
		}
	}
	
	/**
	 * Initialises the search structures, particularly the queue.
	 * This also includes placing the initial state as the first entry in the queue.
	 */
	protected abstract void initSearch();
	/**
	 * Returns true if the search queue is empty, and false otherwise.
	 * @return true if the search queue is empty, and false otherwise.
	 */
	protected abstract boolean queueEmpty();
	/**
	 * Adds a queue entry to the end of the search queue.
	 * @param qe the entry to add to the queue.
	 */
	protected abstract void enqueue(QueueEntry qe);
	/**
	 * Removes a queue entry from the front of the search queue and returns it.
	 * @return the entry removed from the front of the queue.
	 */
	protected abstract QueueEntry dequeue();
	/**
	 * Processes the current entry, and returns true if it is a goal state.
	 * @return true if the current entry is a goal state, and false otherwise.
	 */
	protected abstract boolean processCurrentEntry();
	
	@Override
	public boolean goalFound() {
		return goalFound;
	}
	@Override
	public S getGoalState() {
		return currentEntry.getState();
	}
	@Override
	public int getGoalDepth() {
		return currentEntry.getDepth();
	}
	@Override
	public double getGoalCost() {
		return currentEntry.getTotalCost();
	}
}