package flightPlanning;

import java.util.*;

//weighted, undirected G for storing flight data
public class FlightMap implements Graph{
	protected HashMap<City, LinkedList<Flight>> flightPlan; //adjacency list
	
	/**Construct an empty weighted graph*/
	public FlightMap() {}
	
	/**Create adjacency lists from flight lists*/
	FlightMap(HashMap<City, LinkedList<Flight>> data) {
		flightPlan = data;
	}

	@Override
	public int getSize() {
		return flightPlan.keySet().size();
	}

	@Override
	public int getDegree(City city) {
		return flightPlan.get(city).size();
	}

	@Override
	public void clear() {
		flightPlan.clear();		
	}

	@Override
	public void addCity(City city) {
		if (!flightPlan.containsKey(city))
			flightPlan.put(city, new LinkedList<Flight>());
		else
			System.err.println("This city has already been added to the map.");
	}

	@Override
	public void addLeg(City src, Flight flight) {
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
	public void remove(City removal) {
		if (!flightPlan.containsKey(removal))
			return;
		
		for (Flight f : flightPlan.get(removal)) {
			Flight returnLeg = new Flight(removal, f.cost, f.time);
			removeLeg(removal, f);
			removeLeg(f.destination, returnLeg);
		}
	}

	@Override
	public void removeLeg(City src, Flight flight) {
		if (!flightPlan.get(src).contains(flight))
			return; // the leg either was never there or has already been removed. either way, return.
		
		// remove given leg
		flightPlan.get(src).remove(flight);
		
		//remove return leg
		removeLeg(flight.destination, new Flight(src, flight.cost, flight.time));		
	}

	
	/**Finds shortest path based on time or cost*/
	public Stack<City> getShortestPath(City src, City dest, char whattodo) {
		City current = src;
		if (src.compareTo(dest)) {
			System.err.println("Error: You are already at your destination. Please walk from here.");
			return null;
		}
		//how to store total weight?
		int path_cost; //stores total cost of path
		Stack<City> path = new Stack<City>();
		while (current != dest) {
			path.push(current);
			for (Flight f : flightPlan.get(current)) {
				
			}
		}
		return null;
	}
	

	public ArrayList<Graph> removePath(Stack<City> path) {
		// TODO Auto-generated method stub
		return null;
	}
}
