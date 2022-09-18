/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
import infovis.Graph;
import infovis.Table;
import infovis.column.StringColumn;
import infovis.graph.DefaultGraph;
import infovis.graph.io.GraphReaderFactory;
import infovis.graph.visualization.MatrixVisualization;
import infovis.io.AbstractReader;
import infovis.panel.ControlPanel;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import javax.swing.JFrame;

/**
 * Example of graph visualization with an adjacency matrix.
 * Modified by Yann-Gaël Guéhéneuc.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.2 $
 */
public class MatrixExample {
	/**
	 * Create a Treemap visualization of a specified Tree in a specified JFrame.
	 *
	 * @param frame the JFrame
	 * @param t the Tree.
	 */
	public static void create(final JFrame frame, final Graph g) {
		final MatrixVisualization visualization = new MatrixVisualization(g);

		visualization.setVertexLabelColumn(
			getStringColumn(g.getEdgeTable(), 0));
		final ControlPanel control =
			(ControlPanel) visualization.createDefaultControls();

		frame.getContentPane().add(control);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Return the nth <code>StringColumn</code> from the specified table.
	 *
	 * @param t the table.
	 * @param index the index
	 *
	 * @return the nth <code>StringColumn</code> from the specified table.
	 */
	public static StringColumn getStringColumn(final Table t, int index) {
		StringColumn ret = null;
		for (int i = 0; i < t.getColumnCount(); i++) {
			ret = StringColumn.getColumn(t, i);
			if (ret != null && !ret.isInternal() && index-- == 0)
				return ret;
		}
		return null;
	}

	/**
	 * Main program.
	 *
	 * @param args args.
	 */
	public static void main(final String[] args) {
		final String file;
		if (args.length != 1) {
			final FileDialog fileDialog = new FileDialog(new Frame());
			fileDialog.setVisible(true);
			file =
				fileDialog.getDirectory()
					+ File.separatorChar
					+ fileDialog.getFile();
		}
		else {
			file = args[0];
		}
		final Graph g = new DefaultGraph();
		final AbstractReader reader =
			GraphReaderFactory.createReader(file, g);

		if (reader != null && reader.load()) {
			JFrame frame = new JFrame("MatrixExample");
			create(frame, g);
			frame.setVisible(true);
			frame.pack();
		}
		else {
			System.err.println("cannot load " + args[0]);
		}
	}
}
