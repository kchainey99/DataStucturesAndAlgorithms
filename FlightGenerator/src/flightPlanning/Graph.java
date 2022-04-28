package flightPlanning;

public interface Graph<V> {
	/** Return the number of vertices in the graph */
	public int getSize();

	/** Retun the number of vertices in the graph */
	public java.util.List<V> getVertices();

	/** Return the object for the specified vertex index */
	public V getVertex(int index);

	/** Return the index for the specified vertex object */
	public int getIndex(V v);

	/** Return the neighbors of a vertex with the specified index */
	public java.util.List<Integer> getNeighbors(int index);

	/** Return the neighbors of a vertex with a specified index */
	public int getDegree(String v);

	/** Print the edges */
	public void printEdges();

	/** Clear the graph */
	public void clear();

	/** Add a vertex to the graph */
	public boolean addVertex(V vertex);

	/** Add an edge to the graph */
	public boolean addEdge(String u, String v);

	/** Add an edge to the graph */
	public boolean addEdge(Edge e);

	/** Remove a vertex v from the graph; return true if successful */
	public boolean remove(V v);

	/** Remove an edge (u,v) from the graph; return T if successful */
	public boolean remove(String u, String v);

	public class Edge {
		String incident, adjacent;

		public Edge(String u, String v) {
			incident = u;
			adjacent = v;
		}
	}

	public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
		public double weight; // the weight on edge (u,v)

		public WeightedEdge(String u, String v, double weight) {
			super(u, v);
			this.weight = weight;
		}

		@Override
		public int compareTo(WeightedEdge edge) {
			return (weight > edge.weight) ? 1 : (weight == edge.weight) ? 0 : -1; // 1 if this > comparison, 0 if equal,
																					// -1 if this < comparison
		}
	}
}
