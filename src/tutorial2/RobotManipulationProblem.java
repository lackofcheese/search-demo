package tutorial2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import search.states.*;
import search.goals.*;
import search.sfs.*;
import search.heuristics.*;
import search.algorithms.*;
import search.algorithms.norevisits.*;
import search.algorithms.depthfirst.*;

public class RobotManipulationProblem {
	/** The default file to read input from. */
	public static final String DEFAULT_INPUT = "src/robot.in";
	
	private static RobotArmState initialState;
	private static RobotArmState approxInitialState;
	private static RobotArmState goalState;
	private static RobotArmState approxGoalState;
	private static double len1;
	private static double len2;
	private static List<AlignedRectangle> obstacles  = new ArrayList<AlignedRectangle>();
	
	private static SquareLattice lattice;
	private static Map<List<Integer>, RobotArmState> stateLattice = new HashMap<List<Integer>, RobotArmState>();
	private static MapSF<RobotArmState> stateMap = new MapSF<RobotArmState>();
	
	public static boolean hasCollision(RobotArmState s1, List<AlignedRectangle> obstacles) {
		for (AlignedRectangle obstacle : obstacles) {
			if (hasCollision(s1, obstacle)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasCollision(RobotArmState s1, RobotArmState s2, List<AlignedRectangle> obstacles) {
		for (AlignedRectangle obstacle : obstacles) {
			if (hasCollision(s1, s2, obstacle)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasCollision(RobotArmState s1, AlignedRectangle obstacle) {
		double a1 = Math.toRadians(s1.getAngle1());
		double a2 = Math.toRadians(s1.getAngle2());
		double[][] points = new double[3][];
		points[0] = new double[] {0, 0};
		points[1] = new double[] {len1 * Math.cos(a1), len1 * Math.sin(a1)};
		points[2] = new double[] {len2 * Math.cos(a1+a2), len2 * Math.sin(a1+a2)};
		//System.out.println(Arrays.toString(points[0]));
		//System.out.println(Arrays.toString(points[1]));
		//System.out.println(Arrays.toString(points[2]));
		
		for (double[] point : points) {
			if (point[0] >= obstacle.getMin(0) && point[0] <= obstacle.getMax(0) &&
					point[1] >= obstacle.getMin(1) && point[1] <= obstacle.getMax(1)) {
				return true;
			}
		}
		
		for (int i = 0; i < 2; i++) {
			double[] p = points[i];
			double[] q = points[i+1];
			double[] dp = new double[] {q[0] - p[0], q[1] - p[1]};
			for (int dim = 0; dim < 2; dim++) {
				for (double target : new double[] {obstacle.getMin(dim), obstacle.getMax(dim)}) {
					double t = (target - p[dim]) / dp[dim];
					if (t < 0 || t > 1) {
						continue;
					}
					double oc = p[1-dim] + dp[1-dim] * t;
					if (oc >= obstacle.getMin(1-dim) && oc <= obstacle.getMax(1-dim)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static boolean hasCollision(RobotArmState s1, RobotArmState s2, AlignedRectangle obstacle) {
		return false;
	}
	
	/**
	 * Reads the search parameters from the given file.
	 * @param inputFileName the file to read.
	 * @throws IOException if there are issues reading the file.
	 */
	private static void readFile(String inputFileName) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(inputFileName));
		// Read the arm lengths.
		String[] tokens = input.readLine().trim().split("\\s+");
		len1 = Double.valueOf(tokens[0]);
		len2 = Double.valueOf(tokens[1]);
		
		tokens = input.readLine().trim().split("\\s+");
		initialState = new RobotArmState(Double.valueOf(tokens[0]), Double.valueOf(tokens[1]));
		
		tokens = input.readLine().trim().split("\\s+");
		goalState = new RobotArmState(Double.valueOf(tokens[0]), Double.valueOf(tokens[1]));
		
		String line;
		while ((line = input.readLine()) != null) {
			obstacles.add(new AlignedRectangle(line));
		}
		System.out.println("Obstacles: " + obstacles);
	}
	
	private static List<Integer> getNearestInLattice(RobotArmState s) {
		for (List<Integer> inds : lattice.getNearbyPoints(Arrays.asList(s.getAngle1(), s.getAngle2())).values()) {
			if (stateLattice.containsKey(inds)) {
				return inds;
			}
		}
		return null;
	}
	
	private static List<RobotArmState> compressPath(List<RobotArmState> path) {
		List<RobotArmState> newPath = new ArrayList<RobotArmState>();
		RobotArmState state = path.get(0);
		newPath.add(state);
		boolean sameA1 = true;
		boolean sameA2 = true;
		
		RobotArmState prev = state;
		
		for (int i = 1; i < path.size() - 1; i++) {
			state = path.get(i);
			System.out.println(state);
			if (sameA1 && state.getAngle1() == prev.getAngle1()) {
				sameA2 = false;
			} else if (sameA2 && state.getAngle2() == prev.getAngle2()) {
				sameA1 = false;
			} else {
				newPath.add(prev);
				sameA1 = !sameA1;
				sameA2 = !sameA2;
			}
			prev = state;
		}
		newPath.add(path.get(path.size()-1));
		return newPath;
	}
	
	private static void constructMappings() {
		lattice = new SquareLattice(
				new double[] {-180, -180}, 
				new double[] {180, 180}, 
				new int[] {360, 360}, 
				new boolean[] {true, false});
		
		System.out.println(1);
		for (Map.Entry<List<Integer>, List<Double>> entry : lattice.getLattice().entrySet()) {
			RobotArmState state = new RobotArmState(entry.getValue().get(0), entry.getValue().get(1));
			if (hasCollision(state, obstacles)) {
				System.out.println("Collision at " + state);
				continue;
			}
			stateLattice.put(entry.getKey(), state);
		}
		System.out.println(2);
		
		for (Map.Entry<List<Integer>, RobotArmState> entry : stateLattice.entrySet()) {
			RobotArmState state = entry.getValue();
			for (List<Integer> nb : lattice.getNeighbours(entry.getKey())) {
				if (!stateLattice.containsKey(nb)) {
					continue;
				}
				RobotArmState succ = stateLattice.get(nb);
				if (hasCollision(state, succ, obstacles)) {
					continue;
				}
				double cost = Math.abs(state.getAngle1() - succ.getAngle1());
				cost += Math.abs(state.getAngle2() - succ.getAngle2());
				stateMap.addSuccessor(state, succ, cost);
			}
		}
	}
	
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
		
		constructMappings();
		
		//System.out.println(lattice.getLattice());
		List<Integer> initInds = getNearestInLattice(initialState);
		approxInitialState = stateLattice.get(initInds);
		List<Integer> goalInds = getNearestInLattice(goalState);
		approxGoalState = stateLattice.get(goalInds);
		System.out.println(initialState + " -> " + initInds + " -> " + approxInitialState);
		System.out.println(goalState + " -> " + goalInds + " -> " + approxGoalState);
		
		GoalTest<RobotArmState> goalTest = new EqualGoalTest<RobotArmState>(approxGoalState);
		Heuristic<RobotArmState> heuristic = new ZeroHeuristic<RobotArmState>();
		
		AbstractSearchAlgorithm<RobotArmState> algo;
		//algo = new IterativeDeepeningSearch<RobotArmState>(initialState, goalTest, stateMap);
		//algo = new BreadthFirstSearch<RobotArmState>(initialState, goalTest, stateMap);
		//algo = new DepthFirstSearch<RobotArmState>(initialState, goalTest, stateMap);
		//algo = new DepthLimitedSearch<RobotArmState>(4, initialState, goalTest, stateMap);
		algo = new AStarSearch<RobotArmState>(approxInitialState, goalTest, stateMap, heuristic);
		
		algo.verboseSearch();
		System.out.println(compressPath(algo.getGoalPath()));
	}
}
