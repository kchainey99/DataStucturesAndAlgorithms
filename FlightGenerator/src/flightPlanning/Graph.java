package flightPlanning;

import java.util.*;

public interface Graph {
	/** Return the number of vertices in the graph */
	public int getSize();

	/** Return the neighbors of a vertex */
	public int getDegree(City city);

	/** Clear the graph */
	public void clear();

	/** Add a vertex to the graph */
	public void addCity(City city);

	/** Add a leg(flight) to the graph */
	public void addLeg(City src, Flight flight);
	
	/** Add a leg(flight) to the graph */
	public void removeLeg(City src, Flight flight);

	/** Remove an edge (u,v) from the graph; return T if successful */
	public void remove(City removal);

	/* return list containing all paths */
	ArrayList<FlightPath> getAllPaths(City src, City dest, char optimizingFor);
	
	
}
