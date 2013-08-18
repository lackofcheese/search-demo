package tutorial1.navigation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import search.states.*;
import search.goals.*;
import search.sfs.*;
import search.heuristics.*;
import search.algorithms.*;
import search.algorithms.norevisits.*;
import search.algorithms.depthfirst.*;

/**
 * An implementation of the navigation problem from Tutorial 1.
 * 
 * @author lackofcheese
 */
@SuppressWarnings("unused")
public class NavigationSolver {
	/** The default file to read input from. */
	public static final String DEFAULT_INPUT = "src/navigation.in";

	/** A mapping to store the navigation data. */
	private static MapSF<NamedState> sf = new MapSF<NamedState>();
	/** A mapping to remember the states by their names. */
	private static Map<String, NamedState> byName = new HashMap<String, NamedState>();

	/**
	 * Reads the search parameters from the given file.
	 * 
	 * @param inputFileName
	 *            the file to read.
	 * @throws IOException
	 *             if there are issues reading the file.
	 */
	private static void readFile(String inputFileName) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(inputFileName));
		// Read the number of vertices from the file.
		int numVertices = Integer.valueOf(input.readLine().trim());
		// Read all of the buildings in and remember them by name.
		for (int i = 0; i < numVertices; i++) {
			String name = input.readLine().trim();
			NamedState building = new NamedState(name);
			byName.put(name, building);
		}
		// Read the number of edges.
		int numEdges = Integer.valueOf(input.readLine().trim());
		/*
		 * Read the edges, and store all of them in the mapping successor
		 * function.
		 */
		for (int i = 0; i < numEdges; i++) {
			String[] names = input.readLine().trim().split("\\s+");
			NamedState b0 = byName.get(names[0]);
			NamedState b1 = byName.get(names[1]);
			double cost = Double.valueOf(names[2]);
			sf.addSuccessor(b0, b1, cost);
			sf.addSuccessor(b1, b0, cost);
		}
		input.close();
	}

	/**
	 * @param args
	 *            the command-line arguments. If any are given, the first will
	 *            be taken as the file to read from.
	 */
	public static void main(String args[]) {
		try {
			if (args.length > 0) {
				readFile(args[0]);
			} else {
				readFile(DEFAULT_INPUT);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		NamedState initialState = byName.get("78");
		GoalTest<NamedState> goalTest = new EqualGoalTest<NamedState>(
				byName.get("82D"));
		Heuristic<NamedState> heuristic = new ZeroHeuristic<NamedState>();

		AbstractSearchAlgorithm<NamedState> algo;
		// algo = new IterativeDeepeningSearch<NamedState>(initialState,
		// goalTest, sf);
		// algo = new BreadthFirstSearch<NamedState>(initialState, goalTest,
		// sf);
		// algo = new DepthFirstSearch<NamedState>(initialState, goalTest, sf);
		algo = new DepthLimitedSearch<NamedState>(4, initialState, goalTest, sf);
		// algo = new AStarSearch<NamedState>(initialState, goalTest, sf,
		// heuristic);

		algo.verboseSearch();
	}
}
