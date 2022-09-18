package fr.emn.oadymppac.demos;

import java.net.MalformedURLException;
import java.net.URL;
import fr.emn.oadymppac.graph.ConstraintGraphLoader;
import fr.emn.oadymppac.graph.EdgeWithHistoryGetter;
import fr.emn.oadymppac.graph.TxtGraphLoader;
import fr.emn.oadymppac.graph.aggregation.GraphWithHistoryGetter;
import fr.emn.oadymppac.graph.clustering.AbstractMatrixClusterizer;

/*
 * Created on 8 juil. 2003
 */

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.3 $
 *
 * A demo visualisation of static graphs using an adjacency matrix.
 */
public class DemoStaticGLAdjacencyMatrix extends DemoGLAdjacencyMatrix {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4849706773927637495L;
	public static void main(final String[] args) {
		boolean directed = false;
		boolean struct = false;
		String structureType = null;
		int mode = -1;

		if (args.length < 2) {
			System.err.println("Syntax : source directed");
			System.err.println("source is the source file");
			System.err.println("directed maybe true or false");
			System.exit(1);
		}
		else {
			if (args.length >= 2) {
				directed = Boolean.valueOf(args[1]).booleanValue();
			}

			if (args.length >= 3) {
				if (args[2].equals("cc")) {
					mode = ConstraintGraphLoader.CONSCONS;
				}
				else if (args[2].equals("vv")) {
					mode = ConstraintGraphLoader.VARVAR;
				}
				else if (args[2].equals("cv")) {
					mode = ConstraintGraphLoader.CONSVAR;
				}
			}
			if (args.length >= 4) {
				if (args[3].equals("struct")) {
					struct = true;
					if (args.length >= 5) {
						if (args[4].equals("onevar")
								|| args[4].equals("allvars")) {
							structureType = args[4];
						}
					}
				}
			}

		}

		final DemoStaticGLAdjacencyMatrix frame =
			new DemoStaticGLAdjacencyMatrix(
				directed,
				mode,
				struct,
				structureType);
		frame.loadGraph(args[0]);

	}
	boolean directed = false;
	boolean struct = false;
	String structureType = null;

	int mode = -1;

	DemoStaticGLAdjacencyMatrix(
		final boolean directed,
		final int mode,
		final boolean structure,
		final String structureType) {
		super(directed);
		this.directed = directed;
		this.mode = mode;
		this.struct = structure;
		this.structureType = structureType;

	}

	public void loadGraph(final String filename) {
		if (DemoGLAdjacencyMatrix.isGraph(filename)) {
			final ConstraintGraphLoader gl = new ConstraintGraphLoader();
			gl.setMode(this.mode);
			gl.setSeeStructure(this.struct);
			if (this.structureType != null) {
				gl.setStructureType(this.structureType);
			}
			// TODO : add these methods to the generic interface GraphLoader
			gl.setMaxModel(this.control.getIntensityModel());
			gl.setVisibleEdgeModel(this.control.getVisibleEdgesModel());
			gl.addTimeListener(this.control);
			gl.setIntervalList(this.control.getIntervalList());
			try {
				gl.load(new URL("file:" + filename), this.graph);
			}
			catch (final MalformedURLException e) {
				e.printStackTrace();
			}

			this.control.setExternalNames(gl.getExternalNames());

			final EdgeWithHistoryGetter w =
				new EdgeWithHistoryGetter(this.control.getHistoryModel());
			this.glmatrix.setWeightGetter(w);
			this.setGraphGetter(new GraphWithHistoryGetter(
				this.treeModel1,
				this.treeModel2,
				this.graph));
		}
		else {
			final TxtGraphLoader gl = new TxtGraphLoader();
			gl.setMaxModel(this.control.getIntensityModel());
			gl.setVisibleEdgeModel(this.control.getVisibleEdgesModel());
			gl.addTimeListener(this.control);
			gl.setIntervalList(this.control.getIntervalList());

			try {
				gl.load(new URL("file:" + filename), this.graph);
			}
			catch (final MalformedURLException e) {
				e.printStackTrace();
			}

			final EdgeWithHistoryGetter w =
				new EdgeWithHistoryGetter(this.control.getHistoryModel());
			System.out.println("dans EdgeWithHistoryGetter");
			this.glmatrix.setWeightGetter(w);
			this.setGraphGetter(new GraphWithHistoryGetter(
				this.treeModel1,
				this.treeModel2,
				this.graph));

			//			RandomGraphLoader gl = new RandomGraphLoader();
			//			control.setExternalNames(new HashMap()); // replace with a real map
			//
			//			try {
			//				maxWeight = gl.load(new URL("file:" + filename), graph);
			//			} catch (MalformedURLException e) {
			//				e.printStackTrace();
			//			}
			//			final WeightedEdgeWeight w = new WeightedEdgeWeight(20);
			//
			//			glmatrix.setWeightGetter(w);
			//			setGraphGetter(
			//				new DefaultGraphGetter(treeModel1, treeModel2, graph));
		}

		this.glmatrix.getRow().populateMatrix();
		this.glmatrix.getColumn().populateMatrix();
		((AbstractMatrixClusterizer) this.glmatrix.getClusterizer())
			.extractNodes(this.glmatrix.getColumn(), this.hjtb.getTreeModel());
		((AbstractMatrixClusterizer) this.glmatrix.getClusterizer())
			.extractNodes(this.glmatrix.getColumn(), this.vjtb.getTreeModel());

		this.control.register(this.getGraphGetter());

		this.mainSplitPane.setResizeWeight(1);

		this.pack();
		this.setVisible(true);
	}
}
