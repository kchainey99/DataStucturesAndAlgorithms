package flightPlanning;

import java.util.*;

/**Holds flight paths and their associated costs*/
public class FlightPath implements Comparable<FlightPath>{
	LinkedList<String> flightPath;
	int cost,time;
	char optimizingFor; // T or C
	
	FlightPath(String src, Stack<Flight> path, char optimizingFor){
		cost = 0;
		time = 0;
		this.optimizingFor = optimizingFor;
		flightPath = new LinkedList<String>();
		// pop off the stack & add it to the flightPath
		while (!path.isEmpty()) {
			Flight lastVisited = path.pop();
			cost += lastVisited.cost;
			time += lastVisited.time;
			flightPath.addFirst(lastVisited.destination);
		}
		flightPath.addFirst(src); //add starting node last
	}

	@Override
	public int compareTo(FlightPath o) {
		if (optimizingFor == 'T')
			return time - o.time;
		else
			return cost - o.cost;
	}
	
	void printPath() {
		StringBuilder s = new StringBuilder();
		for (String city : flightPath) {
			s.append(city);
			s.append(" -> ");
		}
		s.setLength(s.length() - 4); //remove last arrow
		s.append(" | cost: " + cost + " | ");
		s.append(" time: " + time + " | ");
		System.out.println(s.toString());
	}
}
