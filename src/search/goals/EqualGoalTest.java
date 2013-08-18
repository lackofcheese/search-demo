package search.goals;

import search.states.State;

/**
 * A simple goal test implementation for the case where there is exactly one
 * goal state.
 * 
 * @author lackofcheese
 * @param <S>
 *            the type of state required.
 */
public class EqualGoalTest<S extends State> implements GoalTest<S> {
	/** Stores the goal state to check for equality. */
	private S goalState;

	/**
	 * Class constructor; takes the goal state
	 * 
	 * @param goalState
	 */
	public EqualGoalTest(S goalState) {
		this.goalState = goalState;
	}

	@Override
	public boolean isGoal(S s) {
		return s.equals(goalState);
	}

}
