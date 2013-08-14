package tutorial2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SquareLattice {
	private double[] mins;
	private double[] maxs;
	private int[] ns;
	private double[] widths;
	private boolean[] wrap;
	
	private double[][] centres;
	private Map<List<Integer>, List<Double>> lattice;

	public SquareLattice(double[] mins, double[] maxs, int[] ns, boolean[] wrap) {
		this.mins = Arrays.copyOf(mins, 2);
		this.maxs = Arrays.copyOf(maxs, 2);
		this.ns = Arrays.copyOf(ns, 2);
		this.wrap = Arrays.copyOf(wrap, 2);
		this.widths = new double[] {maxs[0] - mins[0], maxs[1] - mins[1]};
		
		centres = new double[2][];
		for (int dim = 0; dim < 2; dim++) {
			int length = ns[dim];
			if (!wrap[dim]) {
				length += 1;
			}
			centres[dim] = new double[length];
			for (int i = 0; i < length; i++) {
				centres[dim][i] = mins[dim] + widths[dim] * i / ns[dim];
			}
		}
		lattice = getLattice();
	}
	
	public Map<List<Integer>, List<Double>> getLattice() {
		Map<List<Integer>, List<Double>> newLattice = new HashMap<List<Integer>, List<Double>>();
		for (int i = 0; i < centres[0].length; i++) {
			for (int j = 0; j < centres[1].length; j++) {
				newLattice.put(Arrays.asList(i, j), Arrays.asList(centres[0][i], centres[1][j]));
			}
		}
		return newLattice;
	}
	
	public List<Double> getCoords(List<Integer> indices) {
		return lattice.get(indices);
	}
	
	public TreeMap<Double, List<Integer>> getNearbyPoints(List<Double> coords) {
		TreeMap<Double, List<Integer>> results = new TreeMap<Double, List<Integer>>();
		List<List<Integer>> indexLists = new ArrayList<List<Integer>>();
		for (int dim = 0; dim < 2; dim++) {
			List<Integer> inds = new ArrayList<Integer>();
			double ix = (coords.get(dim) - mins[dim]) * ns[dim] / widths[dim];
			int index = (int)Math.floor(ix);
			inds.add(index);
			if (index != ix) {
				if (index+1 == ns[dim] && wrap[dim]) {
					inds.add(0);
				} else {
					inds.add(index+1);
				}
			}
			indexLists.add(inds);
		}
		
		for (int i : indexLists.get(0)) {
			for (int j : indexLists.get(1)) {
				List<Integer> indices = Arrays.asList(i, j);
				List<Double> pc = getCoords(indices);
				double squared_distance = 0;
				for (int dim = 0; dim < 2; dim++) {
					double d = pc.get(dim) - coords.get(dim);
					if (wrap[dim]) {
						if (d <= widths[dim] / 2) {
							d += widths[dim];
						} else if (d >= widths[dim] / 2) {
							d -= widths[dim];
						}
					}
					squared_distance += d*d;
				}
				results.put(Math.sqrt(squared_distance), indices);
			}
		}
		return results;
	}
	
	public List<List<Integer>> getNeighbours(List<Integer> indices) {
		List<List<Integer>> neighbours = new ArrayList<List<Integer>>();
		for (int[] delta : new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
			boolean valid = true;
			List<Integer> nb = new ArrayList<Integer>();
	
			for (int dim = 0; dim < 2; dim++) {
				int nv = indices.get(dim) + + delta[dim];
				if (wrap[dim]) {
					if (nv < 0) {
						nv += ns[dim];
					} else if (nv >= ns[dim]) {
						nv -= ns[dim];
					}
				} else {
					if (nv < 0 || nv > ns[dim]) {
						valid = false;
						break;
					}
				}
				nb.add(nv);
			}
			if (valid) {
				neighbours.add(nb);
			}
		}
		return neighbours;
	}
}
