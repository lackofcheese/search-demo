package tutorial2;

public class AlignedRectangle {
	private double[][] vertices;
	private double[] mins = new double[2];
	private double[] maxs = new double[2];
	
	public AlignedRectangle(String str) {
		String[] tokens = str.split("\\s+");
		vertices = new double[4][];
		for (int i = 0; i < 4; i ++) {
			vertices[i] = new double[2];
			vertices[i][0] = Double.valueOf(tokens[i*2]);
			vertices[i][1] = Double.valueOf(tokens[i*2+1]);
		}
		mins[0] = maxs[0] = vertices[0][0];
		mins[1] = maxs[1] = vertices[0][1];
		
		for (int i = 0; i < 4; i ++) {
			for (int dim = 0; dim < 2; dim++) {
				double val = vertices[i][dim];
				if (val < mins[dim]) {
					mins[dim] = val;				
				}	
				if (val > maxs[dim]) {
					maxs[dim] = val;
				}
			}
		}
	}

	public double getMin(int dim) {
		return mins[dim];
	}
	
	public double getMax(int dim) {
		return maxs[dim];
	}
	
	public String toString() {
		return String.format("(%s-%s; %s-%s)", mins[0], maxs[0], mins[1], maxs[1]);
	}
}
