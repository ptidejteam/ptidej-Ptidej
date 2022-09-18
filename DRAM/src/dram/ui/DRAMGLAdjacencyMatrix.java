/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package dram.ui;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import org.xml.sax.InputSource;
import dram.utils.salvo.MyDirectedGraphImpl;
import dram.utils.salvo.MyGraphImpl;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.SolverListener;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.graph.EdgeWithHistoryGetter;
import fr.emn.oadymppac.graph.NamedGraphDelegator;
import fr.emn.oadymppac.graph.RandomGraphLoader;
import fr.emn.oadymppac.graph.WeightedEdgeWeight;
import fr.emn.oadymppac.graph.aggregation.AggregatedGraphGetter;
import fr.emn.oadymppac.graph.aggregation.DefaultGraphGetter;
import fr.emn.oadymppac.graph.aggregation.GraphWithHistoryGetter;
import fr.emn.oadymppac.graph.clustering.AbstractMatrixClusterizer;
import fr.emn.oadymppac.helpers.Parser;
import fr.emn.oadymppac.tree.StateNode;
import fr.emn.oadymppac.utils.TimeComparator;
import fr.emn.oadymppac.widgets.AdjacencyMatrixControlPanel;
import fr.emn.oadymppac.widgets.ExplanationAdjacencyMatrixManager;
import fr.emn.oadymppac.widgets.GLAdjacencyMatrix;
import fr.emn.oadymppac.widgets.IcicleTreeBrowser;
import fr.emn.oadymppac.widgets.JClusterManipulator;

/**
 * This class demonstrates the use of an adjacency matrix along with two icicle
 * trees to visualize and interact with a graph.
 * 
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * 
 * @date 5 sept. 2002
 */
public class DRAMGLAdjacencyMatrix extends JFrame implements
		TreeExpansionListener {

	private static final long serialVersionUID = 2010780122593780997L;

	GLAdjacencyMatrix glmatrix;

	JSplitPane mainSplitPane;

	NamedGraphDelegator graph;

	AdjacencyMatrixRowColumn row;

	AdjacencyMatrixRowColumn column;

	AdjacencyMatrixControlPanel control;

	JClusterManipulator hjtb;

	JClusterManipulator vjtb;

	TreeModel treeModel1;

	TreeModel treeModel2;

	AggregatedGraphGetter graphGetter;

	boolean directed;

	int visibleLevel;

	public DRAMGLAdjacencyMatrix(boolean directed) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.directed = directed;

		this.graph =
			directed ? new NamedGraphDelegator(new MyDirectedGraphImpl())
					: new NamedGraphDelegator(new MyGraphImpl());

		this.glmatrix = new GLAdjacencyMatrix(this.graph);

		this.row = this.glmatrix.getRow();
		this.column = this.glmatrix.getColumn();

		this.treeModel1 = new DefaultTreeModel(new StateNode("root"));
		this.treeModel2 = new DefaultTreeModel(new StateNode("root"));
		this.glmatrix.getClusterizer().setHorizontalTreeModel(this.treeModel1);
		this.glmatrix.getClusterizer().setVerticalTreeModel(this.treeModel2);
		((StateNode) this.treeModel1.getRoot()).expandSubTree(true);
		((StateNode) this.treeModel2.getRoot()).expandSubTree(true);
		this.visibleLevel =
			((StateNode) this.treeModel1.getRoot())
				.getFirstCollapsedChild()
				.getLevel();

		this.hjtb =
			new JClusterManipulator(
				this.treeModel1,
				IcicleTreeBrowser.HORIZONTAL);
		this.hjtb.setRootVisible(true);
		this.hjtb.setPreferredSize(new Dimension(600, 50));

		this.hjtb.addClusterListener(this.glmatrix);
		this.hjtb.addTreeExpansionListener(this);

		this.vjtb =
			new JClusterManipulator(this.treeModel2, IcicleTreeBrowser.VERTICAL);
		this.vjtb.setRootVisible(true);
		this.vjtb.setPreferredSize(new Dimension(50, 600));

		this.vjtb.addClusterListener(this.glmatrix);
		this.vjtb.addTreeExpansionListener(this);

		this.control =
			new AdjacencyMatrixControlPanel(this.glmatrix, this.hjtb, this.vjtb);
		this.glmatrix.setVisibleEdges(this.control.getVisibleEdgesModel());

		((AbstractMatrixClusterizer) this.glmatrix.getClusterizer())
			.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent event) {
					System.out
						.println("cluster hierarchy has changed, repainting...");
					DRAMGLAdjacencyMatrix.this.graphGetter.removeAllMappings();
					((StateNode) DRAMGLAdjacencyMatrix.this.hjtb
						.getTreeModel()
						.getRoot()).expandToLevel(
						DRAMGLAdjacencyMatrix.this.visibleLevel,
						true);
					((StateNode) DRAMGLAdjacencyMatrix.this.vjtb
						.getTreeModel()
						.getRoot()).expandToLevel(
						DRAMGLAdjacencyMatrix.this.visibleLevel,
						true);
					DRAMGLAdjacencyMatrix.this.hjtb.repaint();
					DRAMGLAdjacencyMatrix.this.vjtb.repaint();
					init(((StateNode) DRAMGLAdjacencyMatrix.this.hjtb
						.getTreeModel()
						.getRoot()).getFirstCollapsedChild().getLevel());
					//glmatrix.repaint();

				}
			});

		//setLayout(new BorderLayout());
		this.mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		JPanel leftPane = new JPanel();
		leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.Y_AXIS));

		JPanel topPane = new JPanel();
		JPanel bottomPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.X_AXIS));

		topPane.add(Box.createRigidArea(new Dimension(50, 50)));
		topPane.add(this.hjtb);

		bottomPane.add(this.vjtb);
		bottomPane.add(this.glmatrix);

		leftPane.add(topPane);
		leftPane.add(bottomPane);

		getContentPane().add(this.mainSplitPane);
		this.mainSplitPane.setLeftComponent(leftPane);
		this.mainSplitPane.setContinuousLayout(false);
		this.mainSplitPane.setResizeWeight(0.8);
		this.mainSplitPane.setRightComponent(this.control);
		this.mainSplitPane.setDividerLocation(650);

	}

	static boolean isGraph(String fname) {
		return fname.endsWith(".xml") || fname.endsWith(".xml.gz");
	}

	public void loadGraph(String filename) {
		Thread t = null;

		if (isGraph(filename)) {

			try {
				InputSource is;

				if (filename.endsWith(".gz")) {
					is =
						new InputSource(new GZIPInputStream(
							new FileInputStream(filename)));
					is.setSystemId(filename);
				}
				else {
					is = new InputSource(filename);
					is.setSystemId(filename);
				}

				Parser parser = new Parser(is);
				t = new Thread(parser);

				Solver solver = parser.getSolver();

				/**
				 * Variables vs. Variables
				 */
				//                ExplanationVariableAdjacencyMatrixManager amgr =
				//                        new ExplanationVariableAdjacencyMatrixManager(
				//                                frame.glmatrix.getRow(), frame.glmatrix.getColumn());
				/**
				 * Constraints vs. Constraints
				 */
				ExplanationAdjacencyMatrixManager amgr =
					new ExplanationAdjacencyMatrixManager(
						this.glmatrix.getRow(),
						this.glmatrix.getColumn());
				/**
				 * Constraints vs. Variables
				 */
				//                ConstraintAdjacencyMatrixManager amgr =
				//                        new ConstraintAdjacencyMatrixManager(
				//                                frame.glmatrix.getRow(), frame.glmatrix.getColumn());
				amgr.register(solver);
				amgr.setMaxModel(this.control.getIntensityModel());
				amgr.setVisibleEdgeModel(this.control.getVisibleEdgesModel());
				this.control.getColumnControl().addSortMethod(
					"Time",
					new TimeComparator(solver));

				this.control.getRowControl().addSortMethod(
					"Time",
					new TimeComparator(solver));

				solver.addSolverListener(new SolverListener() {
					/**
					 * @see fr.emn.oadymppac.event.SolverListener#solver(BasicSolverEvent)
					 */
					public void solver(BasicSolverEvent e) {
						DRAMGLAdjacencyMatrix.this.control.setLast(e.getN());
					}
				});

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			RandomGraphLoader gl = new RandomGraphLoader();

			try {
				gl.load(new URL("file:" + filename), this.graph);
			}
			catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		this.glmatrix.getRow().populateMatrix();
		this.glmatrix.getColumn().populateMatrix();
		((AbstractMatrixClusterizer) this.glmatrix.getClusterizer())
			.extractNodes(this.glmatrix.getColumn(), this.hjtb.getTreeModel());
		((AbstractMatrixClusterizer) this.glmatrix.getClusterizer())
			.extractNodes(this.glmatrix.getColumn(), this.vjtb.getTreeModel());

		if (t == null) {
			final WeightedEdgeWeight w = new WeightedEdgeWeight(20);

			this.glmatrix.setWeightGetter(w);
			setGraphGetter(new DefaultGraphGetter(
				this.treeModel1,
				this.treeModel2,
				this.graph));
		}
		else {
			EdgeWithHistoryGetter w =
				new EdgeWithHistoryGetter(this.control.getHistoryModel());
			this.glmatrix.setWeightGetter(w);
			setGraphGetter(new GraphWithHistoryGetter(
				this.treeModel1,
				this.treeModel2,
				this.graph));
		}

		this.control.register(getGraphGetter());

		//frame.mainSplitPane.setPreferredSize()
		this.mainSplitPane.setResizeWeight(1);

		//frame.mainSplitPane.setDividerLocation(300);

		if (t != null) {
			t.start();
		}

	}

	/**
	 * @see javax.swing.event.TreeExpansionListener#treeCollapsed(TreeExpansionEvent)
	 */
	public void treeCollapsed(TreeExpansionEvent event) {
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
		init(((StateNode) this.vjtb.getTreeModel().getRoot()).getDepth()
				- ((StateNode) event.getPath().getLastPathComponent())
					.getLevel());
	}

	/**
	 * @see javax.swing.event.TreeExpansionListener#treeExpanded(TreeExpansionEvent)
	 */
	public void treeExpanded(TreeExpansionEvent event) {
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
		init(((StateNode) this.vjtb.getTreeModel().getRoot()).getDepth()
				- ((StateNode) event.getPath().getLastPathComponent())
					.getLevel() - 1);
	}

	private void init(int level) {
		this.visibleLevel =
			((StateNode) this.vjtb.getTreeModel().getRoot()).getDepth() - level;
		// the detailed graph is stored at key 0
		if (this.graphGetter.isEmpty()) {
			this.graphGetter.addMapping(0, this.graph);
		}

		this.graph = (NamedGraphDelegator) this.graphGetter.getGraphForLevel(level);
		this.glmatrix.setGraph(this.graph);
		this.row = new AdjacencyMatrixRowColumn(this.graph);
		this.column = new AdjacencyMatrixRowColumn(this.graph);

		StateNode[] rowNodes =
			((StateNode) this.vjtb.getTreeModel().getRoot())
				.getNodesAtLevel(this.visibleLevel);
		String[] rowNames = new String[rowNodes.length];
		for (int i = 0; i < rowNodes.length; i++)
			rowNames[i] = (String) rowNodes[i].getUserObject();

		StateNode[] columnNodes =
			((StateNode) this.hjtb.getTreeModel().getRoot())
				.getNodesAtLevel(this.visibleLevel);
		String[] columnNames = new String[columnNodes.length];
		for (int i = 0; i < columnNodes.length; i++)
			columnNames[i] = (String) columnNodes[i].getUserObject();

		this.row.populateMatrix(rowNames);
		this.column.populateMatrix(columnNames);
		this.glmatrix.initData(this.row, this.column);
	}

	/**
	 * Returns the graphGetter.
	 * 
	 * @return AggregatedGraphGetter
	 */
	public AggregatedGraphGetter getGraphGetter() {
		return this.graphGetter;
	}

	/**
	 * Sets the graphGetter.
	 * 
	 * @param graphGetter
	 *            The graphGetter to set
	 */
	public void setGraphGetter(AggregatedGraphGetter graphGetter) {
		this.graphGetter = graphGetter;
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param args
	 *            DOCUMENT ME!
	 */
	public static void main(String[] args) {

		if (args.length < 2) {
			System.err.println("Syntax : source directed");
			System.err.println("source is the source file");
			System.err.println("directed maybe true or false");
			System.exit(1);
		}

		final DRAMGLAdjacencyMatrix frame =
			new DRAMGLAdjacencyMatrix((Boolean.valueOf(args[1])).booleanValue());
		frame.loadGraph(args[0]);
		frame.pack();
		frame.setVisible(true);
	}

	// j'ai ajouté ce bloc aprés avoir comparé avec la nouvelle classe dans new
	// oadymppac

	public NamedGraphDelegator getGraph() {
		return this.graph;
	}

	/**
	 * @param delegator
	 */
	public void setGraph(NamedGraphDelegator newGraph) {
		if (this.graph == newGraph)
			return;

		this.graph = newGraph;
		update();
	}

	public void update() {
		// NB: the following line is redundant with the next one
		//glmatrix.setGraph(graph);
		this.glmatrix.initData(
			new AdjacencyMatrixRowColumn(this.graph),
			new AdjacencyMatrixRowColumn(this.graph));
		//control.update();
	}

	/**
	 * @return
	 */
	public GLAdjacencyMatrix getGlmatrix() {
		return this.glmatrix;
	}

}
