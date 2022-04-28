package flightPlanning;

import java.util.*;

public class WeightedGraph<V> implements Graph<V>{
	protected List<V> vertices = new ArrayList<>(); //store vertices
	protected List<List<Edge>> neighbors = new ArrayList<>(); //adjacency edge lists
	

	
	/**Construct an empty weighted graph*/
	public WeightedGraph() {}
	
	/**Construct a Weighted Graph from vertices and edges in arrays*/
	public WeightedGraph(V[] vertices, int[][] edges) {
		createWeightedGraph(Arrays.asList(vertices),edges);
	}
	
	/**Construct a WeightedGraph from vertices and edges in list*/
	public WeightedGraph(int[][] edges, int numberOfVertices) {
		List<V> vertices = new ArrayList<>();
		for (int i =  0; i < numberOfVertices; i++)
			vertices.add((V) (Integer.valueOf(i)));
		createWeightedGraph(vertices, edges);
	}

	/**Construct a WeightedGraph for vertices 0,1,2 and edge list*/
	public WeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
		createWeightedGraph(vertices, edges);
	}
	
	/**Construct a WeightedGraph from vertices 0,1, and edge array*/
	public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
		List<V> vertices = new ArrayList<>();
		for (int i = 0; i < numberOfVertices; i++)
			vertices.add((V)(Integer.valueOf(i)));
		createWeightedGraph(vertices, edges);
	}
	
	/**Create AdjacencyLists from Edge Arrays*/
	private void createWeightedGraph(List<V> vertices, int[][] edges) {
		this.vertices = vertices;
		for (int i = 0; i < vertices.size(); i++)
			neighbors.add(new ArrayList<Edge>()); //createa a list for vertices
		for (int i = 0; i < edges.length; i++)
			neighbors.get(edges[i][0]).add(new WeightedEdge(edges[i][0], edges[i][1], edges[i][2]));
	}
	
	/**Create adjacency lists from edge lists*/
	private void createWeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
		this.vertices = vertices;
		for (int i = 0; i < vertices.size(); i++)
			neighbors.add(new ArrayList<Edge>());
		for (WeightedEdge edge : edges)
			neighbors.get(edge.incident).add(edge); //add an edge into list
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
	public boolean addEdge(int u, int v, double weight) {
		return (addEdge(new WeightedEdge(u, v, weight)));
	}
	
	/**Get a minimum spanning tree rooted at vertex 0*/
	public MST getMinimumSpanningTree() {
		return getMinimumSpanningTree(0);
	}
	
	/**Get a minimum spanning tree rooted at a specified vertex*/
	public MST getMinimumSpanningTree(int root) {
		//cost[v] stores the cost by adding v to the tree
		double[] cost = new double[getSize()];
		for (int i = 0; i < cost.length; i++)
			cost[i] = Double.POSITIVE_INFINITY; //initial cost
		cost[root] = 0; //cost of source is 0
		
		int[] parent = new int[getSize()]; //parent of a vertex
		parent[root] = -1;
		double totalWeight = 0; //total weight of tree so far
		List<Integer> T = new ArrayList<>();
		
		//Expand T
		while (T.size() < getSize()) {
			//find the smallest cost u in V-T
			int u = -1; //vertex to be determined
			double currentMinCost = Double.POSITIVE_INFINITY;
			for (int i = 0; i < getSize(); i++)
				if (!T.contains(i) && cost[i] < currentMinCost) {
					currentMinCost = cost[i];
					u = i;
				}
			
			if (u == -1) break; else T.add(u); //add a new vertex to T
			totalWeight += cost[u]; // add cost[u] to the tree
			
			//Adjust cost[v] for v that is adjacent to u and v in V-T
			for (Edge e : neighbors.get(u))
				if(!T.contains(e.adjacent) && cost[e.adjacent] > ((WeightedEdge)e).weight) {
					cost[e.adjacent] = ((WeightedEdge)e).weight;
					parent[e.adjacent] = u;
				}
		} //end of while
		return new MST(root, parent, T, totalWeight);
	}
	
	/**Prints MST from root vertex*/
	public void PrintMinimumSpanningTree() {
		System.out.println("MST from root vertex: ");
		List<Integer> searchorder = getMinimumSpanningTree().getSearchOrder();
		StringBuilder s = new StringBuilder();
		for (Integer i : searchorder)
			s.append(vertices.get(i) + " -> "); //converting integer values to names
		s.setLength(s.length() - 4); //removing last arrow
		System.out.println(s.toString());
	}
	
	/**MST is an inner class in WeightedGraph*/
	public class MST extends SearchTree{
		private double totalWeight; //total weight of all edges in the tree

		public MST(int root, int[] parent, List<Integer> searchOrder, double totalWeight) {
			super(root, parent, searchOrder);
			this.totalWeight = totalWeight;
		}
		
		public double getTotalWeight() {
			return totalWeight;
		}
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
}
