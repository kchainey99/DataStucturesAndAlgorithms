package flightPlanning;

import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;

//Responsible for reading in files and determining what we're optimizing for (cost or time)
public final class FlightManager {
	//files we're reading from
	File flightdata = new File("FlightData.txt");
	File requestedpaths = new File("FlightPaths.txt");
	
	//HashMap to store src V & adj E.
	HashMap<City, LinkedList<Flight>> graph_data; //Key source,  Value = L.L. of flights from that source
	LinkedList<String[]> requestedFlights;
	
	private void readFlightData() throws FileNotFoundException{
		Scanner s = new Scanner(flightdata);
		int numoflines = s.nextInt(); // is this faster than checking if s.hasNextLine?
		graph_data = new HashMap<City, LinkedList<Flight>>(numoflines); //give this HashMap an initial capacity to avoid unnecessary amortization
		for (int i = 0; i < numoflines; i++) {
			String[] data = s.nextLine().split("|");
			// src = data[0], dest = data[1], cost = data[2], time = data[3]					
			
			City source = new City(data[0]);
			Flight flight = new Flight(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
			
			//add first Key,Value Pair for a source
			if (!graph_data.containsKey(source)) {
			graph_data.put(source, new LinkedList<Flight>()); 
				graph_data.get(source).add(flight);
			}
			else
				graph_data.get(source).add(flight); //if source is already there, then add to the adjacency list
			
			//add reciprocal edge to make it undirected.
			City reciprocalSrc = new City(data[1]);
			Flight returnLeg = new Flight(data[0], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
			
			if (!graph_data.containsKey(reciprocalSrc)) {
				graph_data.put(reciprocalSrc, new LinkedList<Flight>()); 
				graph_data.get(reciprocalSrc).add(returnLeg);
				
			}
			else
				graph_data.get(reciprocalSrc).add(returnLeg); //if source is already there, then add to the adjacency list
		}
		s.close();
	}
	
	private void getRequestedFlights() throws FileNotFoundException {
		Scanner s = new Scanner(requestedpaths);
		int numoflines = s.nextInt();
		requestedFlights = new LinkedList<>(); // a little more readable in my opinion if LL is initialized here
		while (numoflines > 0) {
			requestedFlights.add(s.nextLine().split("|"));
			numoflines--;
		}
		s.close();
	}

	LinkedList<FlightPath> getFirstKShortestPaths(City source, City dest, int k, char optimizingFor, FlightMap map){
		ArrayList<FlightPath> allPaths = map.getAllPaths(source, dest, optimizingFor); //get all Paths from src -> dest
		LinkedList<FlightPath> shortestPaths = new LinkedList<FlightPath>();
		PriorityQueue<FlightPath> shortestPathQueue = new PriorityQueue<FlightPath>();
		shortestPathQueue.addAll(allPaths);
		while (k-- > 0)
			shortestPaths.add(shortestPathQueue.poll()); // first k shortest paths result from popping from our min queue k times
		return shortestPaths;
	}
	
	void doYourThing(int howManyPaths) throws FileNotFoundException {
		//read in data
		readFlightData();
		getRequestedFlights();
		FlightMap map = new FlightMap(graph_data); //create graph
		for (String[] request : requestedFlights) {
			
		}
	}
}
