package fr.emn.oadymppac.widgets;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import salvo.jesus.graph.DirectedWeightedEdgeImpl;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.WeightedGraphImpl;
import dram.utils.salvo.MyDirectedGraphImpl;
import fr.emn.oadymppac.graph.GraphUtils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JRandomAdjacencyMatrix extends JAdjacencyMatrix {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7980552884263273792L;
	int maxWeight = 0;

	public JRandomAdjacencyMatrix() {
		this.setPreferredSize(new Dimension(500, 500));
		this.columns = new Vector();
		this.rows = new Vector();
		this.columnIndexes = new HashMap();
		this.rowIndexes = new HashMap();
		this.nameVertex = new HashMap();
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				JRandomAdjacencyMatrix.this
					.setPreferredSize(JRandomAdjacencyMatrix.this.getSize());
				JRandomAdjacencyMatrix.this.repaint();
			}
		});
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public JRandomAdjacencyMatrix(final Graph g) {
		super(g);
	}

	public float getIntensity(final Edge e) {
		if (e instanceof DirectedWeightedEdgeImpl) {
			return 1 - (float) ((DirectedWeightedEdgeImpl) e).getWeight()
					/ this.maxWeight;
		}
		else {
			System.out.println("simple edges");
			return 0.0f;
		}
	}

	public void loadGraph(final File f, final int graphtype) {
		switch (graphtype) {
			case GraphUtils.DIRECTED :
				this.graph = new MyDirectedGraphImpl();
				System.out.println("type = directed");
				break;
			case GraphUtils.WEIGHTED :
				this.graph = new WeightedGraphImpl();
				System.out.println("type = weighthed");
				break;
			case GraphUtils.DIRECTEDWEIGHTED :
				this.graph = new WeightedGraphImpl();
				System.out.println("type = directed & weighthed");
				break;
			default :
				this.graph = new WeightedGraphImpl();
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
					this.stripBuffer(this.graph, buffer.toString());
					buffer.setLength(0);
				}
			}
			fis.close();
		}
		catch (final FileNotFoundException e) {
		}
		catch (final IOException ex) {
		}
		System.out.println(this.graph.getVerticesCount());
		this.populateMatrix();
	}

	private void stripBuffer(final Graph g, final String buffer) {
		//System.out.println("stripping "+buffer);
		final StringTokenizer st = new StringTokenizer(buffer, " ");
		final Vector v = new Vector();
		while (st.hasMoreElements()) {
			v.addElement(st.nextElement());
		}
		final String name1 = (String) v.firstElement();
		final String name2 = (String) v.elementAt(1);

		final Vertex v1 = this.findVertex(name1);
		final Vertex v2 = this.findVertex(name2);
		final double w = Double.valueOf((String) v.lastElement()).doubleValue();
		if (w > this.maxWeight) {
			this.maxWeight = (int) w;
		}
		final DirectedWeightedEdgeImpl edge =
			new DirectedWeightedEdgeImpl(v1, v2, w);
		try {
			g.addEdge(edge);
		}
		catch (final Exception e) {
			System.err.println(e.getStackTrace());
		}
	}
}