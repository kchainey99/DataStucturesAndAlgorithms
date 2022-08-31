package flightPlanning;

import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

//Responsible for reading in files and determining what we're optimizing for (cost or time)
public final class FlightManager {
	//files we're reading from
	File flightdata;
	File requestedpaths;
	
	public FlightManager(String[] args) throws FileNotFoundException {
		flightdata = new File(args[0]);
		requestedpaths = new File(args[1]);
		PrintStream output = new PrintStream(new File(args[2]));
		System.setOut(output);
	}
	
	//HashMap to store src V & adj E.
	HashMap<String, LinkedList<Flight>> graph_data; //Key source,  Value = L.L. of flights from that source
	ArrayList<String[]> requestedFlights;
	
	private void readFlightData() throws FileNotFoundException{
		Scanner s = new Scanner(flightdata);
		int numoflines = Integer.parseInt(s.nextLine()); // is this faster than checking if s.hasNextLine?
		graph_data = new HashMap<String, LinkedList<Flight>>(numoflines); //give this HashMap an initial capacity to avoid unnecessary amortization
		for (int i = 0; i < numoflines; i++) {
			String[] data = s.nextLine().split("\\|");
			// data = data[0], dest = data[1], cost = data[2], time = data[3]					
			String source = data[0];
			Flight flight = new Flight(data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
			
			//add first Key,Value Pair for a source
			if (!graph_data.containsKey(source)) {
			graph_data.put(source, new LinkedList<Flight>()); 
				graph_data.get(source).add(flight);
			}
			else
				graph_data.get(source).add(flight); //if source is already there, then add to the adjacency list
			
			//add reciprocal edge to make it undirected.
			String reciprocalSrc = data[1];
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
		int numoflines = Integer.parseInt(s.nextLine());
		requestedFlights = new ArrayList<String[]>(numoflines); // a little more readable in my opinion if LL is initialized here
		while (numoflines > 0) {
			requestedFlights.add(s.nextLine().split("\\|"));
			numoflines--;
		}
		s.close();
	}

	LinkedList<FlightPath> getFirstKShortestPaths(String source, String dest, int k, char optimizingFor, FlightMap map){
		ArrayList<FlightPath> allPaths = map.getAllPaths(source, dest, optimizingFor); //get all Paths from src -> dest
		
		PriorityQueue<FlightPath> shortestPathQueue = new PriorityQueue<FlightPath>();
		shortestPathQueue.addAll(allPaths);
		
		LinkedList<FlightPath> shortestPaths = new LinkedList<FlightPath>();
		while (k-- > 0)
			shortestPaths.add(shortestPathQueue.poll()); // first k shortest paths result from popping from our min queue k times
		return shortestPaths;
	}
	
	void doYourThing(int numOfPaths) throws FileNotFoundException {
		//read in data
		readFlightData();
		getRequestedFlights();
		FlightMap map = new FlightMap(graph_data); //create graph
				
		for (int i = 0; i < requestedFlights.size(); i++) {
			String[] request = requestedFlights.get(i);
			String optimizer = request[2].equals("C") ? "(Cost)" : "(Time)";
			System.out.println("Flight " + (i + 1) + ": " + request[0] + " -> " + request[1] + optimizer);
			LinkedList<FlightPath> shortestPaths = getFirstKShortestPaths(request[0], request[1], numOfPaths,  request[2].charAt(0), map);
			if (shortestPaths.isEmpty())
				System.err.println("No paths found!");
			else
				for (FlightPath f : shortestPaths)
					if (f != null)
						f.printPath();
		}
	}
}
