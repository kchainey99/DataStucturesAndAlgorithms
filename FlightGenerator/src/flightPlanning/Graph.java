package flightPlanning;

import java.util.ArrayList;
import java.util.Stack;

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
	
	/** return stack containing shortest path */
	public Stack<City> getShortestPath(City src, City dest);
	
	/** Helper method for getNextShortestPath.
	 * For a path with N cities, generate n-1 new graphs. Each graph has one edge from the original path removed.
	 * @return ArrayList containing the new graphs*/
	public ArrayList<Graph> removePath(Stack<City> path);
}
