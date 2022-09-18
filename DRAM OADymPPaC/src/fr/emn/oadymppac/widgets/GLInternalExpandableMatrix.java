package fr.emn.oadymppac.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreeModel;
import salvo.jesus.graph.DirectedGraph;
import salvo.jesus.graph.DirectedWeightedEdge;
import salvo.jesus.graph.DirectedWeightedEdgeImpl;
import salvo.jesus.graph.Vertex;
import dram.utils.salvo.MyDirectedGraphImpl;
import dram.utils.salvo.MyGraphImpl;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.graph.NamedGraphDelegator;
import fr.emn.oadymppac.graph.clustering.AbstractMatrixClusterizer;
import fr.emn.oadymppac.tree.StateNode;

/**
 * @author Mohammad Ghoniem
 *
 * Fill in documentation !
 */
public class GLInternalExpandableMatrix extends JInternalFrame implements
		TreeExpansionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6035586773162163268L;
	GLAdjacencyMatrix glmatrix;
	NamedGraphDelegator graph;
	AdjacencyMatrixRowColumn row;
	AdjacencyMatrixRowColumn column;
	final AdjacencyMatrixControlPanel controlPanel;
	GLTreeBrowser hjtb;
	GLTreeBrowser vjtb;
	TreeModel treeModel1;
	TreeModel treeModel2;
	HashMap graphMap;

	/**
	 * Constructor for InternalExpandableMatrix.
	 */
	public GLInternalExpandableMatrix(final NamedGraphDelegator graph) {
		super("test", true, true, true, true);
		this.graph = graph;
		this.graphMap = new HashMap();
		this.glmatrix = new GLAdjacencyMatrix(graph, 200, 200);
		this.initClusterTrees();

		((AbstractMatrixClusterizer) this.glmatrix.getClusterizer())
			.addChangeListener(new ChangeListener() {
				public void stateChanged(final ChangeEvent event) {
					GLInternalExpandableMatrix.this.hjtb
						.setTreeModel(GLInternalExpandableMatrix.this.glmatrix
							.getClusterizer()
							.getHorizontalTreeModel());
					GLInternalExpandableMatrix.this.hjtb.repaint();
					GLInternalExpandableMatrix.this.vjtb
						.setTreeModel(GLInternalExpandableMatrix.this.glmatrix
							.getClusterizer()
							.getVerticalTreeModel());
					GLInternalExpandableMatrix.this.vjtb.repaint();
				}
			});

		this.controlPanel =
			new AdjacencyMatrixControlPanel(this.glmatrix, this.hjtb, this.vjtb);

		final JPanel content = (JPanel) this.getContentPane();
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints c = new GridBagConstraints();

		content.setLayout(layout);

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;

		final Component corner = Box.createGlue();
		corner.setForeground(Color.white);

		layout.setConstraints(corner, c);
		content.add(corner);

		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 5;
		c.weighty = 1;

		layout.setConstraints(this.hjtb, c);
		content.add(this.hjtb);

		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 5;

		layout.setConstraints(this.vjtb, c);
		content.add(this.vjtb);

		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 5;
		c.weighty = 5;

		layout.setConstraints(this.glmatrix, c);
		content.add(this.glmatrix);

	}

	/**
	 * Returns the controlPanel.
	 * @return AdjacencyMatrixControlPanel
	 */
	public AdjacencyMatrixControlPanel getControlPanel() {
		return this.controlPanel;
	}

	public NamedGraphDelegator getGraphForLevel(final int level) {
		if (this.graphMap.containsKey(Integer.valueOf(level))) {
			return (NamedGraphDelegator) this.graphMap.get(Integer.valueOf(level));
		}

		NamedGraphDelegator g;
		if (this.graph.getUnderlyingGraph() instanceof DirectedGraph) {
			g = new NamedGraphDelegator(new MyDirectedGraphImpl());
		}
		else {
			g = new NamedGraphDelegator(new MyGraphImpl());
		}

		final StateNode root1 = (StateNode) this.treeModel1.getRoot();
		final StateNode root2 = (StateNode) this.treeModel2.getRoot();
		final StateNode[] clusters1 = root1.getNodesAtLevel(level);
		final StateNode[] clusters2 = root2.getNodesAtLevel(level);

		int edgeCount = 0;
		StateNode[] leaves1;
		StateNode[] leaves2;
		DirectedWeightedEdge edge;
		Vertex v1, v2;
		for (int i = 0; i < clusters1.length; i++) {
			leaves1 = clusters1[i].getLeaves();
			for (int j = 0; j < clusters2.length; j++) {
				leaves2 = clusters2[j].getLeaves();
				for (int k = 0; k < leaves1.length; k++) {
					v1 =
						this.graph.getVertex((String) leaves1[k]
							.getUserObject());
					for (int h = 0; h < leaves2.length; h++) {
						v2 =
							this.graph.getVertex((String) leaves2[h]
								.getUserObject());
						if (this.graph.getEdge(v1, v2) != null) {
							edgeCount++;
						}
					}
				}
				if (edgeCount > 0) {
					if (g.getEdge(
						g.findVertex((String) clusters1[i].getUserObject()),
						g.findVertex((String) clusters2[j].getUserObject())) == null) {
						edge =
							new DirectedWeightedEdgeImpl(
								g.findVertex((String) clusters1[i]
									.getUserObject()),
								g.findVertex((String) clusters2[j]
									.getUserObject()),
								edgeCount);
						try {
							g.addEdge(edge);

						}
						catch (final Exception ex) {
							System.out.println("problème d'insertion");
						}
					}
				}
				edgeCount = 0;
			}
		}
		System.out
			.println("new graph has" + g.getVerticesCount() + " vertices");
		System.out.println("new graph has" + g.getEdgesCount() + " edges");

		this.graphMap.put(Integer.valueOf(level), g);
		return g;
	}

	public void init(final int level) {
		if (this.graphMap.isEmpty()) {
			this.graphMap.put(Integer.valueOf(((StateNode) this.hjtb
				.getTreeModel()
				.getRoot()).getFirstLeaf().getLevel()), this.graph);
		}

		this.graph = this.getGraphForLevel(level);
		this.glmatrix.setGraph(this.graph);
		this.row = new AdjacencyMatrixRowColumn(this.graph);
		this.column = new AdjacencyMatrixRowColumn(this.graph);

		final StateNode[] rowNodes =
			((StateNode) this.vjtb.getTreeModel().getRoot())
				.getNodesAtLevel(level);
		final String[] rowNames = new String[rowNodes.length];
		for (int i = 0; i < rowNodes.length; i++) {
			rowNames[i] = (String) rowNodes[i].getUserObject();
		}

		final StateNode[] columnNodes =
			((StateNode) this.hjtb.getTreeModel().getRoot())
				.getNodesAtLevel(level);
		final String[] columnNames = new String[columnNodes.length];
		for (int i = 0; i < columnNodes.length; i++) {
			columnNames[i] = (String) columnNodes[i].getUserObject();
		}

		this.row.populateMatrix(rowNames);
		this.column.populateMatrix(columnNames);
		this.glmatrix.initData(this.row, this.column);
	}

	void initClusterTrees() {
		System.out.println("initializing clusters");
		this.treeModel1 =
			this.glmatrix.getClusterizer().getHorizontalTreeModel();

		this.hjtb =
			new GLTreeBrowser(
				this.treeModel1,
				IcicleTreeBrowser.HORIZONTAL,
				200,
				50);
		this.hjtb.setRootVisible(true);

		this.hjtb.addClusterListener(this.glmatrix);
		this.hjtb.addTreeExpansionListener(this);

		this.treeModel2 = this.glmatrix.getClusterizer().getVerticalTreeModel();

		this.vjtb =
			new GLTreeBrowser(
				this.treeModel2,
				IcicleTreeBrowser.VERTICAL,
				50,
				200);
		this.vjtb.setRootVisible(true);

		this.vjtb.addClusterListener(this.glmatrix);
		this.vjtb.addTreeExpansionListener(this);
	}

	public void removeAllMappings() {
		this.graphMap.clear();
	}

	public void removeMapping(final int level) {
		this.graphMap.remove(Integer.valueOf(level));
	}

	/**
	 * @see javax.swing.event.TreeExpansionListener#treeCollapsed(TreeExpansionEvent)
	 */
	public void treeCollapsed(final TreeExpansionEvent event) {
		if (event.getSource().equals(this.hjtb)) {
			((StateNode) this.vjtb.getTreeModel().getRoot()).expandLevel(event
				.getPath()
				.getPathCount() - 1, false);
			this.vjtb.repaint();
		}
		else {
			((StateNode) this.hjtb.getTreeModel().getRoot()).expandLevel(event
				.getPath()
				.getPathCount() - 1, false);
			this.hjtb.repaint();
		}
		this.init(event.getPath().getPathCount() - 1);
	}

	/**
	 * @see javax.swing.event.TreeExpansionListener#treeExpanded(TreeExpansionEvent)
	 */
	public void treeExpanded(final TreeExpansionEvent event) {
		if (event.getSource().equals(this.hjtb)) {
			((StateNode) this.vjtb.getTreeModel().getRoot()).expandLevel(event
				.getPath()
				.getPathCount() - 1, true);
			this.vjtb.repaint();
		}
		else {
			((StateNode) this.hjtb.getTreeModel().getRoot()).expandLevel(event
				.getPath()
				.getPathCount() - 1, true);
			this.hjtb.repaint();
		}
		this.init(event.getPath().getPathCount());
	}

}
