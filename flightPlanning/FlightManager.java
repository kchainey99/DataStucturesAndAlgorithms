package flightPlanning;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

//Reponsible for finding shortest paths & determining whether we want smallest cost or shortest time
public class FlightManager {
	public class Flight{
		String source, destination;
		int cost, time;
		
		// Make a flight
		public Flight(String source, String dest, int cost, int time) {
			this.source = source;
			destination = dest;
			this.cost = cost;
			this.time = time;
		}
	}
	
	File flightdata = new File("FlightData.txt");
	File requestedpaths = new File("FlightPaths.txt");
	
	//arraylist to store V & E lists. 
	ArrayList<LinkedList<Object>> graph_data; //V will be a list of Strings, while E = list of flights
	
	ArrayList<LinkedList<Object>> readFlightData(File source) throws FileNotFoundException{

		Scanner s = new Scanner(source);
		int numoflines = s.nextInt(); // is this faster than checking if s.hasNextLine?
		graph_data = new ArrayList<LinkedList<Object>>(numoflines); //give this arraylist an initial capacity to avoid unnecessary amortization
		for (int i = 0; i < numoflines; i++) {
			String[] data = s.nextLine().split("|");
					
			//adding to edge ArrayList
			Flight flight = new Flight(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3])); //flight = (src, dest, cost, time)
			
			for (LinkedList<Object> connections : graph_data)	
				if (connections.getFirst().equals(data[0]))
						connections.add(flight);
			
			if (!graph_data.get(1).contains(flight))
				graph_data.get(0).add(flight);
		}
		return graph_data;
	}
}
