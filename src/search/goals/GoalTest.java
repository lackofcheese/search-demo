package search.goals;

import search.states.State;

/**
 * An interface for querying whether a state is a goal state or not.
 * 
 * @author lackofcheese
 * @param <S>
 *            the type of state needed.
 */
public interface GoalTest<S extends State> {
	/**
	 * Returns true if the state is a goal state, and false otherwise.
	 * 
	 * @param s
	 *            the state in question
	 * @return true if the state is a goal state, and false otherwise.
	 */
	public boolean isGoal(S s);
}