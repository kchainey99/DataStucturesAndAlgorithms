package flightPlanning;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

//Responsible for reading in files, building FlightMap, and sourcing shortest paths
public class FlightManager {
	public class Flight{
		String source, destination;
		int cost, time;
		
		// Weighted Edge holding source & destination cities and cost & time for a leg.
		public Flight(String source, String dest, int cost, int time) {
			this.source = source;
			destination = dest;
			this.cost = cost;
			this.time = time;
		}
	}
	
	File flightdata = new File("FlightData.txt");
	File requestedpaths = new File("FlightPaths.txt");
	
	//HashMap to store src V & adj E.
	HashMap<String, LinkedList<Flight>> graph_data; //Key source,  Value = L.L. of flights from that source
	LinkedList<String[]> requestedflights;
	
	
	HashMap<String, LinkedList<Flight>> readFlightData(File file) throws FileNotFoundException{

		Scanner s = new Scanner(file);
		int numoflines = s.nextInt(); // is this faster than checking if s.hasNextLine?
		graph_data = new HashMap<String, LinkedList<Flight>>(numoflines); //give this HashMap an initial capacity to avoid unnecessary amortization
		for (int i = 0; i < numoflines; i++) {
			String[] data = s.nextLine().split("|");
								
			//adding to edge ArrayList
			Flight flight = new Flight(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3])); //flight = (src, dest, cost, time)
			Flight returnleg = new Flight(data[1], data[0], Integer.parseInt(data[2]), Integer.parseInt(data[3])); //add reciprocal edge so it's undirected
			
			//add first Key,Value Pair for a source
			if (!graph_data.containsKey(flight.source)) {
				graph_data.put(flight.source, new LinkedList<Flight>()); 
				graph_data.get(flight.source).add(flight);
				
			}
			else
				graph_data.get(flight.source).add(flight); //if source is already there, then add to the adjacency list
			
			//add reciprocal edge
			if (!graph_data.containsKey(returnleg.source)) {
				graph_data.put(returnleg.source, new LinkedList<Flight>()); 
				graph_data.get(returnleg.source).add(returnleg);
				
			}
			else
				graph_data.get(returnleg.source).add(returnleg); //if source is already there, then add to the adjacency list
		}
		s.close();
		return graph_data;
	}
	
	LinkedList<String[]> getRequestedFlights(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		int numoflines = s.nextInt();
		requestedflights = new LinkedList<>(); // a little more readable in my opinion if LL is initialized here
		while (numoflines > 0) {
			requestedflights.add(s.nextLine().split("|"));
			numoflines--;
		}
		s.close();
		return requestedflights;
	}
}
