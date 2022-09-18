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
package ptidej.viewer.extension.repository;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import padl.visitor.IWalker;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.extension.IViewerExtension;
import salvo.jesus.graph.DirectedGraphImpl;
import util.help.IHelpURL;
import util.io.ProxyConsole;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.graph.GraphLoader;
import fr.emn.oadymppac.graph.NamedGraphDelegator;
import fr.emn.oadymppac.graph.RandomGraphLoader;
import fr.emn.oadymppac.graph.WeightedEdgeWeight;
import fr.emn.oadymppac.widgets.AdjacencyMatrixControlPanel;
import fr.emn.oadymppac.widgets.GLAdjacencyMatrix;
import fr.emn.oadymppac.widgets.JClusterManipulator;

public final class OADymPPaC extends JFrame implements IViewerExtension,
		IHelpURL {

	private static final long serialVersionUID = 1L;
	public OADymPPaC() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(final WindowEvent e) {
				OADymPPaC.this.setVisible(false);
			}
			public void windowClosing(final WindowEvent e) {
				this.windowClosed(e);
			}
		});
	}
	private void createFrame(final IRepresentation aRepresentation) {
		this.setVisible(false);
		this.setTitle(this.getName());
		this.getContentPane().removeAll();

		final NamedGraphDelegator graph =
			new NamedGraphDelegator(new DirectedGraphImpl());

		final IWalker walker = new OADymPPaCGenerator();
		aRepresentation.getSourceModel().walk(walker);

		final String path =
			System.getProperty("user.dir") + File.separatorChar + "Temp.graph";
		try {
			// TODO Use ProxyDisk
			final FileWriter writer = new FileWriter(path);
			writer.write(walker.getResult().toString());
			writer.close();

			final GraphLoader gl = new RandomGraphLoader();
			gl.load(new URL("file:" + path), graph);
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final AdjacencyMatrixRowColumn row =
			new AdjacencyMatrixRowColumn(graph);
		row.populateMatrix();

		final AdjacencyMatrixRowColumn column =
			new AdjacencyMatrixRowColumn(graph);
		column.populateMatrix();

		final GLAdjacencyMatrix glmatrix =
			new GLAdjacencyMatrix(row, column, 600, 800);
		glmatrix.setWeightGetter(new WeightedEdgeWeight(20));

		final AdjacencyMatrixControlPanel control =
			new AdjacencyMatrixControlPanel(
				glmatrix,
				new JClusterManipulator(),
				new JClusterManipulator());
		glmatrix.setVisibleEdges(control.getVisibleEdgesModel());

		final JSplitPane mainSplitPane =
			new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.getContentPane().add(mainSplitPane);
		mainSplitPane.setLeftComponent(glmatrix);
		mainSplitPane.setContinuousLayout(false);
		mainSplitPane.setResizeWeight(0.8);
		mainSplitPane.setRightComponent(control);
		mainSplitPane.setDividerLocation(600);
		mainSplitPane.setResizeWeight(1);
		this.pack();
		this.setVisible(true);
	}
	public String getHelpURL() {
		return "http://contraintes.inria.fr/OADymPPaC/";
	}
	public String getName() {
		return "Adjacency matrix (OADymPPaC)";
	}
	public void invoke(final IRepresentation aRepresentation) {
		this.createFrame(aRepresentation);
	}
	public void sourceModelAvailable(SourceAndGraphModelEvent aViewerEvent) {
		if (this.isVisible()) {
			this.createFrame(aViewerEvent.getRepresentation());
		}
	}
	public void sourceModelChanged(final SourceAndGraphModelEvent aViewerEvent) {
		this.sourceModelAvailable(aViewerEvent);
	}
	public void sourceModelUnavailable() {
		// Nothing to do...
	}
}
