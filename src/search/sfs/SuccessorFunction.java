package search.sfs;

import java.util.Map;

import search.states.State;

/**
 * An interface representing a successor function, which is represented by the
 * getSuccessors method.
 * 
 * @author lackofcheese
 * @param <S>
 *            the type of state required.
 */
public interface SuccessorFunction<S extends State> {
	/**
	 * This method will, for any given state, return a mapping containing the
	 * successor states of that state and the associated costs of moving to
	 * those states.
	 * 
	 * @param s
	 *            the state in question.
	 * @return a mapping of successor states to edge costs.
	 */
	public Map<S, Double> getSuccessors(S s);
}
