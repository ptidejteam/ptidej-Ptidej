package fr.emn.oadymppac.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import salvo.jesus.graph.DirectedGraph;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.GraphAddEdgeEvent;
import salvo.jesus.graph.GraphAddVertexEvent;
import salvo.jesus.graph.GraphListener;
import salvo.jesus.graph.GraphRemoveEdgeEvent;
import salvo.jesus.graph.GraphRemoveVertexEvent;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.adaptor.GraphDelegator;

/**
 * Towards InstrumentalJazz
 * 
 * @author Jean-Daniel Fekete
 * @date 6 sept. 2002
 */
public class NamedGraphDelegator extends GraphDelegator implements
		GraphListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6815033452032087670L;
	//, Comparator
	//SortedMap vertexMap;
	Map vertexMap;

	/**
	 * Constructor for NamedGraphDelegator.
	 * 
	 * @param graph
	 */
	public NamedGraphDelegator(final Graph graph) {
		super(graph);
		//vertexMap = new TreeMap();
		this.vertexMap = new HashMap();
		this.populateVertexMap();
		graph.addListener(this);
	}

	//	public int compare(Object o1, Object o2) {
	//
	//		String s1 = ((Vertex) o1).toString();
	//		String s2 = ((Vertex) o1).toString();
	//		return s1.compareTo(s2);
	//	}

	/**
	 * @see salvo.jesus.graph.GraphListener#afterEdgeAdded(GraphAddEdgeEvent)
	 */
	public void afterEdgeAdded(final GraphAddEdgeEvent event) {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#afterEdgeRemoved(GraphRemoveEdgeEvent)
	 */
	public void afterEdgeRemoved(final GraphRemoveEdgeEvent event) {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#afterVertexAdded(GraphAddVertexEvent)
	 */
	public void afterVertexAdded(final GraphAddVertexEvent event) {
		this.vertexMap.put(event.getVertex().toString(), event.getVertex());
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#afterVertexRemoved(GraphRemoveVertexEvent)
	 */
	public void afterVertexRemoved(final GraphRemoveVertexEvent event) {
		this.vertexMap.remove(event.getVertex().toString());
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#beforeEdgeAdded(GraphAddEdgeEvent)
	 */
	public void beforeEdgeAdded(final GraphAddEdgeEvent event) throws Exception {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#beforeEdgeRemoved(GraphRemoveEdgeEvent)
	 */
	public void beforeEdgeRemoved(final GraphRemoveEdgeEvent event)
			throws Exception {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#beforeVertexAdded(GraphAddVertexEvent)
	 */
	public void beforeVertexAdded(final GraphAddVertexEvent event)
			throws Exception {
		final String label = event.getVertex().toString();
		if (label == null) {
			throw new Exception("Adding unnamed vertex is illegal");
		}
		if (this.vertexMap.containsKey(label)) {
			throw new Exception("Vertex name already in graph");
		}
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#beforeVertexRemoved(GraphRemoveVertexEvent)
	 */
	public void beforeVertexRemoved(final GraphRemoveVertexEvent event)
			throws Exception {
	}

	/**
	 * Returns the edge having v1 as the first vertex and v2 as the second
	 * vertex. It creates an edge between the passed vertices if necessary.
	 * 
	 * @param v1
	 *            first vertex
	 * @param v2
	 *            second vertex
	 * 
	 * @return the corresponding edge.
	 */
	public Edge findEdge(final Vertex v1, final Vertex v2) {
		Edge e = this.getEdge(v1, v2);
		if (e != null) {
			return e;
		}
		e = this.getGraphFactory().createEdge(v1, v2);
		try {
			this.addEdge(e);
		}
		catch (final Exception ex) {
			return null;
		}
		return e;
	}

	public Vertex findVertex(final String label) {
		//		System.out.print(" vertexMap ");
		//		Iterator it;
		//		for (it = getGraph().getVerticesIterator();
		//		it.hasNext(); ) {
		//		Vertex v = (Vertex)it.next();
		//		System.out.print(" " + v.toString());
		//	}
		//		System.out.print("\n");

		Vertex v = this.getVertex(label);
		if (v == null) {
			v = this.getGraphFactory().createVertex();
			v.setLabel(label);
			try {
				this.add(v);
			}
			catch (final Exception e) {
				v = null;
			}
		}
		return v;
	}

	/**
	 * Returns the edge having v1 as the first vertex and v2 as the second
	 * vertex. It returns <code>null</code> when no such edge can be found in
	 * the graph or when either vertex is <code>null</code>.
	 * 
	 * @param v1
	 *            first vertex
	 * @param v2
	 *            second vertex
	 * 
	 * @return the corresponding edge if available or <code>null</code>.
	 */
	public Edge getEdge(final Vertex v1, final Vertex v2) {
		if (v1 == null || v2 == null) {
			return null;
		}

		final Iterator iter =
			this.getUnderlyingGraph() instanceof DirectedGraph ? ((DirectedGraph) this
				.getUnderlyingGraph()).getOutgoingEdges(v1).iterator() : this
				.getEdges(v1)
				.iterator();

		//System.out.println("iterating on "+graph.getEdges(v1).size()+"
		// edges");
		while (iter.hasNext()) {
			final Edge currentEdge = (Edge) iter.next();

			if (currentEdge.getOppositeVertex(v1) == v2) {
				return currentEdge;
			}
		}

		return null;
	}

	public final Graph getUnderlyingGraph() {
		return super.getGraph();
	}

	public Vertex getVertex(final String label) {
		return (Vertex) this.vertexMap.get(label);
	}

	protected void populateVertexMap() {
		for (final Iterator it = this.getGraph().getVerticesIterator(); it
			.hasNext();) {
			final Vertex v = (Vertex) it.next();
			this.vertexMap.put(v.toString(), v);
		}

	}

}