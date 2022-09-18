/*
 * Created on 25 sept. 2003
 */
package fr.emn.oadymppac.widgets;

import java.awt.Dimension;
import java.io.File;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import salvo.jesus.graph.DirectedGraph;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.Vertex;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.graph.NamedGraphDelegator;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This convenience frame displays a node link diagram using
 * AT&T programs : dot, neato and twopi. It is executed in a new Thread. 
 */
public class NodeLinkThread extends SwingWorker {

	JNodeLinkManager nodeLink;
	JFrame frame;
	String pgm;

	public NodeLinkThread(final File dotfile) {
		this.pgm = JNodeLinkManager.PGM_DEFAULT;
		this.nodeLink = new JNodeLinkManager(dotfile);
	}

	public NodeLinkThread(final GLAdjacencyMatrix glMatrix) {
		this(glMatrix, JNodeLinkManager.PGM_DEFAULT);
	}

	public NodeLinkThread(final GLAdjacencyMatrix glMatrix, final String pgm) {
		this.pgm = pgm;
		this.nodeLink = new JNodeLinkManager();
		final NamedGraphDelegator graph = glMatrix.graph;
		final AdjacencyMatrixRowColumn row = glMatrix.getRow();
		final AdjacencyMatrixRowColumn column = glMatrix.getColumn();
		final Graph g = row.getGraph().getUnderlyingGraph();
		String ext1 = null;
		String ext2 = null;
		double weight = 1.0;
		String columnName;
		Vertex vertex;
		Edge edge;
		for (int i = 0; i < column.getCount(); i++) {
			columnName = column.getName(i);
			vertex = graph.getVertex(columnName);
			java.util.List l;

			if (vertex == null) {
				continue;
			}

			if (g instanceof DirectedGraph) {
				l = ((DirectedGraph) g).getOutgoingEdges(vertex);
			}
			else {
				l = g.getEdges(vertex);
			}

			for (final Iterator it = l.iterator(); it.hasNext();) {
				edge = (Edge) it.next();

				if (glMatrix.getIntensity(edge) <= 0) {
					continue; // skip null edges
				}

				weight = glMatrix.getIntensity(edge);
				final String rowName =
					edge.getOppositeVertex(vertex).getLabel();
				// TODO: handle external names of vertices
				//ext1 = getExternalNameFor(columnName);
				//ext2 = getExternalNameFor(rowName);
				if (ext1 == null) {
					ext1 = columnName;
				}
				if (ext2 == null) {
					ext2 = rowName;
				}
				this.nodeLink.addEdge(columnName + " = " + ext1, rowName
						+ " = " + ext2, weight);
			}
		}

	}

	public NodeLinkThread(final String pgm) {
		this.pgm = pgm;
		this.nodeLink = new JNodeLinkManager();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public Object construct() {
		this.layoutWith(this.pgm);
		return null;
	}

	/**
	 * @return
	 */
	public JFrame getFrame() {
		return this.frame;
	}

	/**
	 * Lays out the graph using the passed
	 * program;
	 * 
	 * @param pgm
	 */
	void layoutWith(final String pgm) {
		if (pgm == JNodeLinkManager.PGM_EXPORT) {
			this.nodeLink.exportFile("graph.dot");
			this.nodeLink = null;
		}
		else {

			this.frame = new JFrame(pgm);
			final JSplitPane mainSplitPane =
				new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			this.frame.getContentPane().add(mainSplitPane);

			this.nodeLink.computeLayout(pgm);

			final JScrollPane jsp = new JScrollPane();
			jsp.setPreferredSize(new Dimension(600, 600));
			jsp.setViewportView(this.nodeLink);

			mainSplitPane.setLeftComponent(jsp);
			mainSplitPane.setContinuousLayout(false);
			mainSplitPane.setResizeWeight(0.8);
			mainSplitPane.setRightComponent(new NodeLinkControlPanel(
				this.nodeLink));
			mainSplitPane.setDividerLocation(600);

			// TODO: remove the following
			this.frame.pack();
			this.frame.setVisible(true);
		}
	}

	/**
	 * @param frame
	 */
	public void setFrame(final JFrame frame) {
		this.frame = frame;
	}

}
