package flightPlanning;

import java.util.*;

//weighted, undirected G for storing flight data
public class FlightMap<V> implements Graph<V>{

	protected List<V> vertices = new ArrayList<>(); //store vertices
	protected List<List<Edge>> neighbors = new ArrayList<>(); //adjacency edge lists
	
	/**Construct an empty weighted graph*/
	public FlightMap() {}
	
	/**Create adjacency lists from edge lists*/
	void createFlightMap(HashMap<String, LinkedList<FlightManager.Flight>> data) {
		data.forEach(null(key, value) ->
		vertices.add(key);
		
		)
	}
	
	/**Return the weight on the edge (u,v)*/
	public double getWeight(int u, int v) throws Exception{
		for (Edge edge : neighbors.get(u))
			if (edge.adjacent == v)
				return ((WeightedEdge)edge).weight;
		throw new Exception("Edge does not exit");
	}
	
	/**Display edges with weights*/
	public void printWeightedEdges() {
		for (int i = 0; i < getSize(); i++) {
			System.out.print(getVertex(i) + " (" + i + "): ");
			for (Edge edge : neighbors.get(i))
				System.out.print(" (" + edge.incident + ", "  +edge.adjacent + ", " + ((WeightedEdge)edge).weight + ") ");
			System.out.println();
		}
	}
	
	/**Add edges to the weighted graph*/
	public boolean addEdge(String u, String v, double weight) {
		return (addEdge(new WeightedEdge(u, v, weight)));
	}
	
	/**Find single-source shortest paths -- Dijkstra's Algorithm.*/
	public ShortestPathTree getShortestPath(int source) {
		//cost[v] stores the cost of the path from v to the source
		double[] cost = new double[getSize()];
		for (int i = 0; i < cost.length; i++)
			cost[i] = Double.POSITIVE_INFINITY;
		cost[source] = 0; //cost of source vertex is 0
		
		//parent[v] stores the previous vertex of v in the path
		int[] parent = new int[getSize()];
		parent[source] = -1; //the parent of source = -1 (no parent)
		
		//T store the vertices whose path found so far
		List<Integer> T = new ArrayList<>();
		
		//Expand T
		while (T.size() < getSize()) {
			//smallest cost u in V - T
			int u = -1; //vertex TBD
			double currentMinCost = Double.POSITIVE_INFINITY;
			for (int i = 0; i < getSize(); i++)
				if (!T.contains(i) && cost[i] < currentMinCost) {
					currentMinCost = cost[i];
					u = i;
				}
			if (u == -1) break; else T.add(u); //add a new vertex to T
			//Adjust cost[v] for v that is adjacent to u and V in V - T
			for (Edge e: neighbors.get(u))
				if (!T.contains(e.adjacent) && cost[e.adjacent] > cost[u]) {
					cost[e.adjacent] = cost[u] + ((WeightedEdge)e).weight;
					parent[e.adjacent] = u;
				}
		} //End of while
		//create a shortest path tree
		return new ShortestPathTree(source, parent, T, cost);
	}
	
	/**Prints shortest path from each vertex to root*/
	public void PrintShortestPath() {
		System.out.println("------------------------------------------Shortest paths from every vertex------------------------------------------");
		for (int i = 0; i < vertices.size(); i++) {
			System.out.print("Shortest path from " + vertices.get(i) + ": ");
			List<Integer> searchorder = getShortestPath(i).getSearchOrder();
			StringBuilder s = new StringBuilder();
			for (Integer j : searchorder)
				s.append(vertices.get(j) + " -> "); //converting integer values to names
			s.setLength(s.length() - 4); //removing last arrow
			System.out.println(s.toString());
		}
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<V> getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V getVertex(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIndex(V v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getNeighbors(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getDegree(int v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void printEdges() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addVertex(V vertex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEdge(int u, int v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addEdge(Edge e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(V v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int u, int v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDegree(String v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addEdge(String u, String v) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(String u, String v) {
		// TODO Auto-generated method stub
		return false;
	}
}
