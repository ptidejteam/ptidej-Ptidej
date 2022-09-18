package fr.emn.oadymppac.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import salvo.jesus.graph.DirectedWeightedEdgeImpl;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.VertexImpl;
import salvo.jesus.graph.WeightedEdge;
import salvo.jesus.graph.WeightedGraphImpl;
import dram.utils.salvo.MyDirectedGraphImpl;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @version 1.0
 * @author unascribed
 */
public class GraphUtils {
	/** DOCUMENT ME! */
	public static final int DIRECTED = 0;
	/** DOCUMENT ME! */
	public static final int WEIGHTED = 1;
	/** DOCUMENT ME! */
	public static final int DIRECTEDWEIGHTED = 2;

	/**
	 * DOCUMENT ME!
	 *
	 * @param edges List of edges
	 *
	 * @return the maximum weight over the list.
	 */
	public static double computeMaxWeight(final List edges) {
		double w = 0;

		for (final Iterator it = edges.iterator(); it.hasNext();) {
			final Edge e = (Edge) it.next();

			if (e instanceof WeightedEdge) {
				final WeightedEdge we = (WeightedEdge) e;
				w = Math.max(w, we.getWeight());
			}
			else {
				w = Math.max(w, 1);
			}
		}

		return w;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param edges DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static double computeWeight(final List edges) {
		double w = 0;

		for (final Iterator it = edges.iterator(); it.hasNext();) {
			final Edge e = (Edge) it.next();

			if (e instanceof WeightedEdge) {
				final WeightedEdge we = (WeightedEdge) e;
				w += we.getWeight();
			}
			else {
				w++;
			}
		}

		return w;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param f DOCUMENT ME!
	 * @param type DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static Graph loadGraph(final File f, final int type) {
		Graph graph = null;

		switch (type) {
			case DIRECTED :
				graph = new MyDirectedGraphImpl();
				System.out.println("type = directed");

				break;

			case WEIGHTED :
				graph = new WeightedGraphImpl();
				System.out.println("type = weighthed");

				break;

			case DIRECTEDWEIGHTED :
				graph = new WeightedGraphImpl();
				System.out.println("type = directed & weighthed");

				break;

			default :
				graph = new WeightedGraphImpl();
		}

		try {
			final FileInputStream fis = new FileInputStream(f);
			final StringBuffer buffer = new StringBuffer();

			for (byte b = (byte) fis.read(); b != (byte) -1; b =
				(byte) fis.read()) {
				if (b != (byte) '\n') {
					buffer.append((char) b);
				}
				else {
					GraphUtils.stripBuffer(graph, buffer.toString());
					buffer.setLength(0);
				}
			}
			fis.close();
		}
		catch (final FileNotFoundException e) {
		}
		catch (final IOException ex) {
		}

		return graph;
	}

	private static void stripBuffer(final Graph g, final String buffer) {
		System.out.println("stripping " + buffer);

		final StringTokenizer st = new StringTokenizer(buffer, " ");
		final Vector v = new Vector();

		while (st.hasMoreElements()) {
			v.addElement(st.nextElement());
		}

		final VertexImpl v1 = new VertexImpl(v.firstElement());
		final VertexImpl v2 = new VertexImpl(v.elementAt(1));

		final DirectedWeightedEdgeImpl edge =
			new DirectedWeightedEdgeImpl(v1, v2, Double.valueOf(
				(String) v.lastElement()).doubleValue());

		try {
			g.addEdge(edge);
		}
		catch (final Exception e) {
			System.err.println(e.getStackTrace());
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param graph DOCUMENT ME!
	 * @param label DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static int vertexDegree(
		final NamedGraphDelegator graph,
		final String label) {
		return GraphUtils.vertexDegree(graph, graph.getVertex(label));
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param graph DOCUMENT ME!
	 * @param v DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static int vertexDegree(
		final NamedGraphDelegator graph,
		final Vertex v) {
		if (v == null) {
			return 0;
		}

		return graph.getEdges(v).size();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param graph DOCUMENT ME!
	 * @param label DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static double vertexMaxWeightedDegree(
		final NamedGraphDelegator graph,
		final String label) {
		return GraphUtils
			.vertexMaxWeightedDegree(graph, graph.getVertex(label));
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param graph DOCUMENT ME!
	 * @param v DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static double vertexMaxWeightedDegree(
		final NamedGraphDelegator graph,
		final Vertex v) {
		if (v == null) {
			return 0;
		}

		return GraphUtils.computeMaxWeight(graph.getEdges(v));
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param graph DOCUMENT ME!
	 * @param label DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static double vertexWeightedDegree(
		final NamedGraphDelegator graph,
		final String label) {
		return GraphUtils.vertexWeightedDegree(graph, graph.getVertex(label));
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param graph DOCUMENT ME!
	 * @param v DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public static double vertexWeightedDegree(
		final NamedGraphDelegator graph,
		final Vertex v) {
		if (v == null) {
			return 0;
		}

		return GraphUtils.computeWeight(graph.getEdges(v));
	}

	private GraphUtils() {
	}
}