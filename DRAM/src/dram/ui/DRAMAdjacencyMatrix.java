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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import salvo.jesus.graph.Vertex;
import dram.core.ClassGrouping;
import dram.core.GraphLoader;
import dram.core.GroupingByClass;
import dram.utils.FileChange;
import dram.utils.salvo.MyDirectedGraphImpl;
import dram.utils.salvo.MyGraphImpl;
import fr.emn.oadymppac.graph.ConstraintGraphLoader;
import fr.emn.oadymppac.graph.EdgeWithHistoryGetter;
import fr.emn.oadymppac.graph.NamedGraphDelegator;
import fr.emn.oadymppac.graph.aggregation.GraphWithHistoryGetter;
import fr.emn.oadymppac.graph.clustering.AbstractMatrixClusterizer;
import fr.emn.oadymppac.utils.TraceFileFilter;

/*
 * Created on 8 juil. 2003
 */

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.11 $
 *
 * A demo visualisation of static graphs using an adjacency matrix.
 */
public class DRAMAdjacencyMatrix extends DRAMGLAdjacencyMatrix {
	private static final long serialVersionUID = 1235946235731665579L;

	public static final String tempFile = "C:\\DRAMTemp.txt";
	public static final String sortFile = "C:\\DRAMSort.txt";

	private String namefile;
	private String namefileChange;
	private Vector vectorClassName = new Vector();
	boolean directed = false;
	//boolean struct = false;
	boolean struct = true;
	//String structureType = null;
	String structureType = "onevar";
	//int mode = -1;
	int mode = ConstraintGraphLoader.CONSCONS;

	DRAMAdjacencyMatrix(
		boolean directed,
		int mode,
		boolean structure,
		String structureType) {
		super(directed);
		this.directed = directed;
		this.mode = mode;
		this.struct = structure;
		this.structureType = structureType;

	}

	DRAMAdjacencyMatrix(boolean directed) {
		super(directed);
		this.directed = directed;
	}

	public void loadGraph(String filename) {
		setGraph((this.directed == true) ? new NamedGraphDelegator(
			new MyDirectedGraphImpl()) : new NamedGraphDelegator(
			new MyGraphImpl()));

		if (isGraph(filename)) {
			ConstraintGraphLoader gl = new ConstraintGraphLoader();
			gl.setMode(this.mode);
			gl.setSeeStructure(this.struct);
			if (this.structureType != null)
				gl.setStructureType(this.structureType);
			// TODO : add these methods to the generic interface GraphLoader
			gl.setMaxModel(this.control.getIntensityModel());
			gl.setVisibleEdgeModel(this.control.getVisibleEdgesModel());
			gl.addTimeListener(this.control);
			gl.setIntervalList(this.control.getIntervalList());
			try {
				gl.load(new URL("file:" + filename), this.graph);
			}
			catch (MalformedURLException e) {
				e.printStackTrace();
			}

			this.control.setExternalNames(gl.getExternalNames());

			EdgeWithHistoryGetter w =
				new EdgeWithHistoryGetter(this.control.getHistoryModel());
			//MyEdgeWithHistoryGetter w =
			//	new MyEdgeWithHistoryGetter(control.getHistoryModel());
			this.glmatrix.setWeightGetter(w);
			setGraphGetter(new GraphWithHistoryGetter(
				this.treeModel1,
				this.treeModel2,
				this.graph));
		}
		else {
			GraphLoader gl = new GraphLoader();
			gl.setMaxModel(this.control.getIntensityModel());
			gl.setVisibleEdgeModel(this.control.getVisibleEdgesModel());
			gl.addTimeListener(this.control);
			gl.setIntervalList(this.control.getIntervalList());

			try {
				gl.load(new URL("file:" + filename), this.graph);
			}
			catch (MalformedURLException e) {
				e.printStackTrace();
			}

			EdgeWithHistoryGetter w =
				new EdgeWithHistoryGetter(this.control.getHistoryModel());
			//MyEdgeWithHistoryGetter w = new MyEdgeWithHistoryGetter(control
			//		.getHistoryModel());
			//System.out.println("dans EdgeWithHistoryGetter");
			this.glmatrix.setWeightGetter(w);
			setGraphGetter(new GraphWithHistoryGetter(
				this.treeModel1,
				this.treeModel2,
				this.graph));

		}

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

		this.glmatrix.getRow().populateMatrix();
		this.glmatrix.getColumn().populateMatrix();
		((AbstractMatrixClusterizer) this.glmatrix.getClusterizer())
			.extractNodes(this.glmatrix.getColumn(), this.hjtb.getTreeModel());
		((AbstractMatrixClusterizer) this.glmatrix.getClusterizer())
			.extractNodes(this.glmatrix.getColumn(), this.vjtb.getTreeModel());

		this.control.register(getGraphGetter());

		this.mainSplitPane.setResizeWeight(1);

		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		boolean directed = true;

		//		if (args.length < 2) {
		//			System.err.println("Syntax : source directed");
		//			System.err.println("source is the source file");
		//			System.err.println("directed maybe true or false");
		//			System.exit(1);
		//		} else {
		//			if (args.length >= 2)
		//				directed = (Boolean.valueOf(args[1])).booleanValue();
		//			
		//			if (args.length >= 3) {
		//				if (args[2].equals("cc"))
		//					mode = ConstraintGraphLoader.CONSCONS;
		//				else if (args[2].equals("vv"))
		//					mode = ConstraintGraphLoader.VARVAR;
		//				else if (args[2].equals("cv"))
		//					mode = ConstraintGraphLoader.CONSVAR;
		//			}
		//			if (args.length >= 4) {
		//				if (args[3].equals("struct")) {
		//					struct = true;
		//					if (args.length >= 5) {
		//						if (args[4].equals("onevar")
		//							|| args[4].equals("allvars"))
		//							structureType = args[4];
		//					}
		//				}
		//			}
		//
		//		}
		//
		//		final DRAMAdjacencyMatrix frame =
		//			new DRAMAdjacencyMatrix(
		//				directed,
		//				mode,
		//				struct,
		//				structureType);
		final DRAMAdjacencyMatrix frame = new DRAMAdjacencyMatrix(directed);
		frame.addMenuBar();
		frame.pack();
		frame.setVisible(true);
		//frame.loadGraph(args[0]);
	}
	public void addMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		//System.out.println("choixss " + namefile);
		JMenuItem openItem = new JMenuItem("Open...");
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DRAMAdjacencyMatrix.this.vectorClassName.removeAllElements();
				final JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System
					.getProperty("user.dir")
						+ System.getProperty("file.separator") + "traces"));
				TraceFileFilter filter = new TraceFileFilter();
				chooser.setFileFilter(filter);
				int returnVal =
					chooser.showOpenDialog(DRAMAdjacencyMatrix.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					DRAMAdjacencyMatrix.this.namefile =
						chooser.getSelectedFile().getAbsolutePath();
					FileChange fileChange =
						new FileChange(DRAMAdjacencyMatrix.this.namefile);
					//					DRAMAdjacencyMatrix.this.loadGraph(chooser
					//									.getSelectedFile().getAbsolutePath());
					DRAMAdjacencyMatrix.this.namefileChange =
						fileChange.getfileName();
					DRAMAdjacencyMatrix.this
						.loadGraph(DRAMAdjacencyMatrix.this.namefileChange);
				}
			}
		});

		JMenuItem groupItem = new JMenuItem("Grouping");
		groupItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Iterator it;
				Vector vectorVertices = new Vector();
				System.out.println("nombre de vertex  ");
				for (it = getGraph().getVerticesIterator(); it.hasNext();) {
					Vertex v = (Vertex) it.next();
					vectorVertices.add(v.toString());
					System.out.println(v.toString());
				}
				System.out.println("nombre de vertex  " + vectorVertices.size());

				Collections.sort(vectorVertices);
				DialogGrouping dialogGrouping =
					new DialogGrouping(
						vectorVertices,
						DRAMAdjacencyMatrix.this,
						DRAMAdjacencyMatrix.this.vectorClassName);

				DRAMAdjacencyMatrix.this.vectorClassName =
					dialogGrouping.checkBoxListener.getvectorChoices();
				//Collections.sort(vectorClassName);
				//				for (int i = 0; i < vectorClassName.size(); i++) {
				//					System.out.print(" vecteur  " + vectorClassName.elementAt(i));
				//				}
				//				
				//				System.out.println("\n");
				//				System.out.println("namefile " + namefile);
				//ClassGrouping classGrouping = new ClassGrouping(namefile,
				//		vectorClassName);
				ClassGrouping classGrouping =
					new ClassGrouping(
						DRAMAdjacencyMatrix.this.namefileChange,
						DRAMAdjacencyMatrix.this.vectorClassName);

				//				System.out.println("choix classGrouping "
				//						+ classGrouping.returnFilename());
				DRAMAdjacencyMatrix.this.loadGraph(classGrouping
					.returnFilename());
			}
		});
		JMenuItem groupItemClass = new JMenuItem("Grouping by class");
		groupItemClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Iterator it;
				Vector vectorVertices = new Vector();
				for (it = getGraph().getVerticesIterator(); it.hasNext();) {
					Vertex v = (Vertex) it.next();
					vectorVertices.add(v.toString());
				}
				System.out.println("nombre de vertex  " + vectorVertices.size());

				Collections.sort(vectorVertices);
				DialogGrouping dialogGrouping =
					new DialogGrouping(
						vectorVertices,
						DRAMAdjacencyMatrix.this,
						DRAMAdjacencyMatrix.this.vectorClassName);

				DRAMAdjacencyMatrix.this.vectorClassName =
					dialogGrouping.checkBoxListener.getvectorChoices();
				String nameofclass =
					(String) DRAMAdjacencyMatrix.this.vectorClassName
						.elementAt(0);

				GroupingByClass groupingByClass =
					new GroupingByClass(
						DRAMAdjacencyMatrix.this.namefile,
						nameofclass);
				DRAMAdjacencyMatrix.this.loadGraph(groupingByClass
					.returnFilename());
			}
		});

		JMenuItem groupItemMethod = new JMenuItem("Grouping by method");
		groupItemMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("exiting application");
				System.exit(0);
			}
		});

		fileMenu.add(openItem);
		fileMenu.add(groupItem);
		fileMenu.add(groupItemClass);
		fileMenu.add(groupItemMethod);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);

		setJMenuBar(menuBar);
	}
}
