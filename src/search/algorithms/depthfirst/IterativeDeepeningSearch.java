package search.algorithms.depthfirst;

import java.util.ArrayList;
import java.util.List;

import search.algorithms.AbstractSearchAlgorithm;
import search.goals.GoalTest;
import search.sfs.SuccessorFunction;
import search.states.State;

/**
 * An Iterative Deepening Search, implemented by successive depth-limited
 * searches with increasing depth.
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public class IterativeDeepeningSearch<S extends State> extends AbstractSearchAlgorithm<S> {
	/** 
	 * Constructs an IDS with the given parameters.
	 * @param root the initial state.
	 * @param goalTest a test for the goal state.
	 * @param sf the successor function.
	 */
	public IterativeDeepeningSearch(S root, GoalTest<S> goalTest,
			SuccessorFunction<S> sf) {
		super(root, goalTest, sf);
	}

	/** True if the goal was found, and false otherwise. */
	private boolean goalFound;
	/** The goal state (if found). */
	private S goalState;
	/** The depth of the goal state (if found). */
	private int goalDepth;
	/** The final cost to reach the goal state (if found). */
	private double goalCost;
	/** The path taken to reach the goal state (if found). */
	private List<S> goalPath;
	
	@Override
	public void search() {
		this.goalFound = false;
		
		for (int maxDepth = 0; ; maxDepth++) {
			System.out.println("Depth: " + maxDepth);
			DepthLimitedSearch<S> dls = new DepthLimitedSearch<S>(maxDepth, getRoot(), getGoalTest(), getSF());
			dls.search();
			if (dls.goalFound()) {
				this.goalFound = true;
				this.goalState = dls.getGoalState();
				this.goalCost = dls.getGoalCost();
				this.goalDepth = dls.getGoalDepth();
				this.goalPath = dls.getGoalPath();
				return;
			}
		}
	}

	@Override
	public List<S> getGoalPath() {
		return new ArrayList<S>(this.goalPath);
	}

	@Override
	public boolean goalFound() {
		return goalFound;
	}

	@Override
	public S getGoalState() {
		return goalState;
	}

	@Override
	public int getGoalDepth() {
		return goalDepth;
	}

	@Override
	public double getGoalCost() {
		return goalCost;
	}
}
