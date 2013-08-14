package tutorial2;

import search.states.State;

public class RobotArmState implements State {
	private double angle1;
	private double angle2;
	
	public RobotArmState(double angle1, double angle2) {
		this.angle1 = angle1;
		this.angle2 = angle2;
	}
	
	public boolean equals (Object obj) {
		if (obj == null || !(obj instanceof RobotArmState)) {
			return false;
		}
		RobotArmState rs2 = (RobotArmState)obj;
		return (this.angle1 == rs2.angle1 && this.angle2 == rs2.angle2);
	}
	
	public double getAngle1() {
		return angle1;
	}

	public double getAngle2() {
		return angle2;
	}
	
	public String toString() {
		return "(" + angle1 + ", " + angle2 + ")";
	}

	public int hashCode() {
		return Double.valueOf(angle1).hashCode() + 7 * Double.valueOf(angle2).hashCode();
	}
}