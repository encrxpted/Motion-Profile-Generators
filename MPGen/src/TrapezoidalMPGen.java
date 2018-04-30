import java.util.ArrayList;
import java.util.List;

public class TrapezoidalMPGen {
	// Profile spits out velocity/distance for every 10ms
	// INPUT VALUES - for now using a distance unts param.
	// TODO use scanner
	public static double cruiseVelocity = 1; //units per 100ms
	public static double acceleration = 1; // units per 100ms per sec
	private static double distanceUnits = 20; // distance to travel in encoder units
	
	private static List<Double> speedList = new ArrayList<Double>();
	private static List<Double> posList = new ArrayList<Double>();

	private static double acceleration10ms = acceleration / 100; // units per 100ms per 10ms
	private static double timeToAccel10ms = cruiseVelocity / acceleration10ms; // time it takes get to cruise velocity in 10ms
	private static double cruiseVelocity10ms = cruiseVelocity / 10; // cruise velo in units per 10ms
	private static double totalAccelDistanceUnits = cruiseVelocity10ms * timeToAccel10ms; //distance traveled during accel and deccel
	
	public static void main(String[] args) {
		int i = 0;
		// Writes a velocity starting from 0 that increments until it gets to the cruise velocity
		for(double s = 0; s < cruiseVelocity; s = s + acceleration10ms) {
			speedList.add(i, s);
			i++;
		}
		
		if(distanceUnits >= totalAccelDistanceUnits) {
			double cruiseDistanceUnits = distanceUnits - totalAccelDistanceUnits; // Distance to travel during cruise in units
			int timeToCruise10ms = (int) Math.round(cruiseDistanceUnits / cruiseVelocity10ms); // amount of time in 10ms to cruise
			for(int s = 0; s < timeToCruise10ms; s++) {
				speedList.add(i, cruiseVelocity);
				i++;
			}
		}
		else {
			System.out.println("too small distance"); //TODO make triangular motion profile for this case
		}
		
		// Writes the de-acceleration velocities with same method as above
		for(double s = cruiseVelocity; s >= 0; s = s - acceleration10ms) {
			speedList.add(i, s);
			i++;
		}
		
		double pos = 0; // Variable to help write the pos array
		for(int m = 0; m < speedList.size(); m++) {
			pos = pos + (speedList.get(m) / 10);
			posList.add(m, pos);
		}
		
		for(int m = 0; m < speedList.size(); m++) {
			System.out.println("{" + speedList.get(m) + ", " + posList.get(m) + "}");
		}

	}
	
}
