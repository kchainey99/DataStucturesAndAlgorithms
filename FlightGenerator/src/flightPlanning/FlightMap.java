package flightPlanning;

import java.util.*;

//weighted, undirected G for storing flight data
public final class FlightMap implements Graph{
	protected HashMap<String, LinkedList<Flight>> flightPlan; //adjacency list
	
	/**Construct an empty weighted graph*/
	public FlightMap() {}
	
	/**Create adjacency lists from flight lists*/
	FlightMap(HashMap<String, LinkedList<Flight>> data) {
		flightPlan = data;
	}

	@Override
	public int getSize() {
		return flightPlan.keySet().size();
	}

	@Override
	public int getDegree(String city) {
		return flightPlan.get(city).size();
	}

	@Override
	public void clear() {
		flightPlan.clear();		
	}

	@Override
	public void addString(String city) {
		if (!flightPlan.containsKey(city))
			flightPlan.put(city, new LinkedList<Flight>());
		else
			System.err.println("This city has already been added to the map.");
	}

	@Override
	public void addLeg(String src, Flight flight) {
		if (flightPlan.get(src).contains(flight)) {
			System.err.println("Flight has already been added to the map.");
			return;
		}
			
		if (!flightPlan.containsKey(src)) {
			flightPlan.put(src, new LinkedList<Flight>());
			flightPlan.get(src).add(flight);
		}
		else
			flightPlan.get(src).add(flight);
		
		// add reciprocal leg
		Flight returnLeg = new Flight(src, flight.cost, flight.time);
		
		if (!flightPlan.containsKey(flight.destination)) { //as much as I hate allocating more memory from heap, I have to here..
			flightPlan.put(flight.destination, new LinkedList<Flight>()); //well, memory is cheap though.. so who cares right?
			flightPlan.get(flight.destination).add(returnLeg);
		}
		else
			flightPlan.get(flight.destination).add(returnLeg);
	}

	@Override
	public void remove(String removal) {
		if (!flightPlan.containsKey(removal))
			return;
		
		for (Flight f : flightPlan.get(removal)) {
			Flight returnLeg = new Flight(removal, f.cost, f.time);
			removeLeg(removal, f);
			removeLeg(f.destination, returnLeg);
		}
	}

	@Override
	public void removeLeg(String src, Flight flight) {
		if (!flightPlan.get(src).contains(flight))
			return; // the leg either was never there or has already been removed. either way, return.
		
		// remove given leg
		flightPlan.get(src).remove(flight);
		
		//remove return leg
		removeLeg(flight.destination, new Flight(src, flight.cost, flight.time));		
	}
	
    // Prints all paths from
    // 's' to 'd'
	@Override
    public ArrayList<FlightPath> getAllPaths(String src, String dest, char optimizingFor) {
        HashMap<String, Boolean> isVisited = new HashMap<String, Boolean>(flightPlan.keySet().size());
        Stack<Flight> pathList = new Stack<Flight>(); //stores edges that form path
        ArrayList<FlightPath> allPaths = new ArrayList<FlightPath>(); //stores all paths & their associated cost & time weights
        for (String city : flightPlan.keySet())
        	isVisited.put(city, false); //initializing boolean HM to False
        // Call recursive utility
        printAllPathsUtil(src, src, dest, isVisited, allPaths, pathList, optimizingFor);
        
        return allPaths;
    }
 
    // A recursive function to print
    // all paths from 'u' to 'd'.
    // isVisited[] keeps track of
    // vertices in current path.
    // localPathList<> stores the flights that make up the path
    private void printAllPathsUtil(String src, String current, String dest, HashMap<String, Boolean> isVisited,
    							ArrayList<FlightPath> allPaths, Stack<Flight> thisPath, char optimizingFor) {
    	
    	// if match found then no need to traverse more till depth
        if (current.equals(dest)) {
        	allPaths.add(new FlightPath(src, thisPath, optimizingFor));
            if (!thisPath.isEmpty())
            	thisPath.pop();
            return;
        }
 
        // Mark the current node
        isVisited.put(current, true);
 
        // Recur for all the vertices
        // adjacent to current vertex
        for (Flight f : flightPlan.get(current)) {
        	if (!isVisited.get(f.destination).booleanValue()) {
                // store current flight in path
            	thisPath.push(f);
                printAllPathsUtil(src, f.destination, dest, isVisited, allPaths, thisPath, optimizingFor);
 
                // remove current node
                // in path[]
                if(!thisPath.isEmpty())
                	thisPath.pop();
            }
        }
 
        // Mark the current node
        isVisited.put(current, false);
    }
}
