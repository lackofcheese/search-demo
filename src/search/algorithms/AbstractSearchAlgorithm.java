package search.algorithms;

import java.util.List;

import search.goals.GoalTest;
import search.heuristics.Heuristic;
import search.heuristics.ZeroHeuristic;
import search.sfs.SuccessorFunction;
import search.states.State;

/**
 * An abstraction of the functions of a search algorithm.
 * Includes a constructor that stores the core inputs of a search:
 * a root state, a goal test, a successor function, 
 * and (if appropriate) a heuristic function.
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public abstract class AbstractSearchAlgorithm<S extends State> {
	/** The root state of the search. */
	private S root;
	/**  A goal test to determine if the goal has been reached. */
	private GoalTest<S> goalTest;
	/** A successor function to retrieve the successors of a given state. */
	private SuccessorFunction<S> sf;
	/** A heuristic; estimates cost to reach the goal (if appropriate). */
	private Heuristic<S> heuristic;
	
	/**
	 * Constructor; stores the inputs for the search.
	 * @param root the root state of the search.
	 * @param goalTest a goal test to determine if the goal has been reached.
	 * @param sf a successor function that returns the successors of a state.
	 * @param heuristic a heuristic estimate function.
	 */
	public AbstractSearchAlgorithm(S root, GoalTest<S> goalTest, SuccessorFunction<S> sf, Heuristic<S> heuristic) {
		this.root = root;
		this.goalTest = goalTest;
		this.sf = sf;
		this.heuristic = heuristic;
	}
	
	/**
	 * Constructor with no heuristic; stores the inputs, and uses an
	 * always-zero heuristic as an equivalent to no heuristic at all.
	 * @param root the root state of the search.
	 * @param goalTest a goal test to determine if the goal has been reached.
	 * @param sf a successor function that returns the successors of a state.
	 */
	public AbstractSearchAlgorithm(S root, GoalTest<S> goalTest, SuccessorFunction<S> sf) {
		this(root, goalTest, sf, new ZeroHeuristic<S>());
	}
	
	/**
	 * Returns the root state of the search.
	 * @return the root state of the search.
	 */
	public S getRoot() {
		return root;
	}
	
	/**
	 * Returns the goal test.
	 * @return the goal test.
	 */
	public GoalTest<S> getGoalTest() {
		return goalTest;
	}
	
	/**
	 * Returns the successor function.
	 * @return the successor function.
	 */
	public SuccessorFunction<S> getSF() {
		return sf;
	}
	
	/**
	 * Returns the current heuristic function.
	 * @return the current heuristic function.
	 */
	public Heuristic<S> getHeuristic() {
		return heuristic;
	}
	
	/**
	 * The core method of the search; this should run whatever search
	 * algorithm is used until it concludes.
	 * The results of the search will then be available via
	 * the goalFound() method, as well as the
	 * getFinalState(), getFinalDepth(), getFinalCost(), and
	 * getFinalPath() methods.
	 * Note that if goalFound() returns false, or no search has been run yet,
	 * the results of the other 
	 */
	public abstract void search();
	
	/**
	 * Conducts a search, displaying the results of the search and the time
	 * taken to do it to standard output.
	 */
	public void verboseSearch() {
		long startTime = System.currentTimeMillis();
		this.search();
		System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + "ms");
		
		if (this.goalFound()) {
			S goalState = this.getGoalState();
			double finalCost = this.getGoalCost();
			int finalDepth = this.getGoalDepth();
			System.out.println(String.format("Arrived at %s for cost %.2f at depth %d", goalState, finalCost, finalDepth));
			List<S> path = this.getGoalPath();
			if (path.size() < 100) {
				System.out.println("Path taken:" + path);
			}
		} else {
			System.out.println("Failed to find the goal!");
		}
	}
	
	/**
	 * Returns whether a goal state has been reached.
	 * @return true if a search has been successful, and false otherwise.
	 */
	public abstract boolean goalFound();
	
	/**
	 * Returns the goal state found in a search;
	 * behaviour undefined if no goal has been found.
	 * @return the final state of the search.
	 */
	public abstract S getGoalState();
	
	/**
	 * Returns the depth at which the goal state was found; 
	 * behaviour undefined if no goal has been found.
	 * @return the goal state of the search.
	 */
	public abstract int getGoalDepth();
	
	/**
	 * Returns the cost of the path taken to the goal; 
	 * behaviour undefined if no goal has been found.
	 * @return the goal state of the search.
	 */
	public abstract double getGoalCost();
	
	/**
	 * Returns the path taken to reach the goal.
	 * @return the path to the goal as a list, where the first element is
	 * the initial state and the last is the goal.
	 */
	public abstract List<S> getGoalPath();
}
