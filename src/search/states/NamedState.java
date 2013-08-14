package search.states;

/**
 * A simple implementation of a State, using a single name string
 * as a unique identifier. 
 * @author lackofcheese
 */
public class NamedState implements State {
	/** The name string. */
	String name;
	
	/**
	 * Constructor for the named state.
	 * @param name the name of the state
	 */
	public NamedState(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of the state.
	 * @return the name of the state.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the name of the state.
	 * @return the name of the state.
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Returns true if the other state is a NamedState with the same name,
	 * and false otherwise.
	 */
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof NamedState)) {
			return false;
		}
		return this.name.equals(((NamedState)obj).name);
	}
	
	/**
	 * Returns a hash code for this state, based on its name.
	 */
	public int hashCode() {
		return this.name.hashCode();
	}
}
