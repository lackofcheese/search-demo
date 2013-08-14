package search.algorithms.norevisits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import search.algorithms.QueueSearch;
import search.goals.GoalTest;
import search.heuristics.Heuristic;
import search.sfs.SuccessorFunction;
import search.states.State;

/**
 * A queue-based search that will only expand each state it visits at most once.
 * 
 * This is done by remembering which states it has visited in the predecessor
 * map, which is a simple representation of the search tree that maps each
 * state to its predecessor.
 * This map is also used in order to return the path taken to the goal state.
 * @author lackofcheese
 * @param <S> the type of state used.
 */
public abstract class SearchWithoutRevisits<S extends State> extends QueueSearch<S, Object> {
	/**
	 * Constructs an SWR with the given parameters.
	 * @param root the initial state.
	 * @param goalTest a test for goal states.
	 * @param sf the successor function.
	 * @param heuristic the heuristic function.
	 */
	public SearchWithoutRevisits(S root, GoalTest<S> goalTest,
			SuccessorFunction<S> sf, Heuristic<S> heuristic) {
		super(root, goalTest, sf, heuristic);
	}
	/**
	 * Constructs an SWR with the given parameters, and no heuristics.
	 * @param root the initial state.
	 * @param goalTest a test for goal states.
	 * @param sf the successor function.
	 */
	public SearchWithoutRevisits(S root, GoalTest<S> goalTest,
			SuccessorFunction<S> sf) {
		super(root, goalTest, sf);
	}

	/** 
	 * A representation of the search tree; remembers which states 
	 * were expanded, and the predecessor for each.
	 */
	protected Map<S, S> predMap;
	
	@Override
	protected void initSearch() {
		predMap = new HashMap<S, S>();
	}
	
	@Override
	public boolean processCurrentEntry() {
		S currentState = currentEntry.getState();
		if (predMap.containsKey(currentState)) {
			return false;
		}
		int currentDepth = currentEntry.getDepth();
		double currentCost = currentEntry.getTotalCost();
		predMap.put(currentState, currentEntry.getPred());
		if (getGoalTest().isGoal(currentState)) {
			return true;
		}
		
		Map<S, Double> succMap = getSF().getSuccessors(currentState);
		for (Map.Entry<S, Double> entry : succMap.entrySet()) {
			S s2 = entry.getKey();
			if (!predMap.containsKey(s2)) {
				this.enqueue(new QueueEntry(
						s2,
						currentState,
						currentDepth + 1, 
						currentCost + entry.getValue(),
						getHeuristic().estimate(s2),
						null)
				);
			}
		}
		return false;
	}
	
	@Override
	public List<S> getGoalPath() {
		List<S> path = new ArrayList<S>();
		S s = currentEntry.getState();
		path.add(s);
		while ((s = predMap.get(s)) != null) {
			path.add(s);
		}
		Collections.reverse(path);
		return path;
	}
}