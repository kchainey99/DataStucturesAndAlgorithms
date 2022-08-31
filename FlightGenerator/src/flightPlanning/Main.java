package flightPlanning;

import java.io.FileNotFoundException;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		FlightManager flightManager = new FlightManager(args);
		flightManager.doYourThing(3); //we want the 3 shortest paths
	}
}
