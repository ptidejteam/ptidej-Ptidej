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
import infovis.graph.visualization.NodeLinkGraphVisualization;
import infovis.io.AbstractReader;
import infovis.panel.ControlPanel;

import javax.swing.JFrame;


/**
 * Example of graph visualization with an adjacency matrix.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NodeLinkGraphExample {
    /**
     * Create a Treemap visualization of a specified Tree in a specified JFrame.
     *
     * @param frame the JFrame
     * @param t the Tree.
     */
    public static void create(JFrame frame, Graph g) {
        NodeLinkGraphVisualization visualization = new NodeLinkGraphVisualization(g);
        ControlPanel control = (ControlPanel)visualization.createDefaultControls();

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
    public static StringColumn getStringColumn(Table t, int index) {
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
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Syntax: NodeLinkGraphExample <name>");
            System.exit(1);
        }
        Graph          g = new DefaultGraph();
        AbstractReader reader = GraphReaderFactory.createReader(args[0], g);

        if (reader != null && reader.load()) {
            JFrame frame = new JFrame("NodeLinkGraphExample");
            create(frame, g);
            frame.setVisible(true);
            frame.pack();
        } else {
            System.err.println("cannot load " + args[0]);
        }
    }
}