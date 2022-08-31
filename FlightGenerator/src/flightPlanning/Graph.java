package flightPlanning;

import java.util.*;

public interface Graph {
	/** Return the number of vertices in the graph */
	public int getSize();

	/** Return the neighbors of a vertex */
	public int getDegree(String city);

	/** Clear the graph */
	public void clear();

	/** Add a vertex to the graph */
	public void addString(String city);

	/** Add a leg(flight) to the graph */
	public void addLeg(String src, Flight flight);
	
	/** Add a leg(flight) to the graph */
	public void removeLeg(String src, Flight flight);

	/** Remove an edge (u,v) from the graph; return T if successful */
	public void remove(String removal);

	/* return list containing all paths */
	ArrayList<FlightPath> getAllPaths(String src, String dest, char optimizingFor);
	
	
}
