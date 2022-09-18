/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
import infovis.Tree;
import infovis.Visualization;
import infovis.column.StringColumn;
import infovis.io.AbstractReader;
import infovis.panel.ControlPanel;
import infovis.tree.DefaultTree;
import infovis.tree.io.TreeReaderFactory;
import infovis.tree.visualization.TreemapVisualization;
import infovis.tree.visualization.treemap.Squarified;

import javax.swing.JFrame;


/**
 * Example of Treemap visualization.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreemapExample {
    /**
     * Create a Treemap visualization of a specified Tree in a specified JFrame.
     *
     * @param frame the JFrame
     * @param t the Tree.
     */
    public static void create(JFrame frame, Tree t) {
        TreemapVisualization visualization = new TreemapVisualization(t,
                                                                      null,
                                                                      new Squarified());

        visualization.setVisualColumn(Visualization.VISUAL_LABEL, getStringColumn(t, 0));
        ControlPanel control = (ControlPanel)visualization.createDefaultControls();

        frame.getContentPane().add(control);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * DOCUMENT ME!
     *
     * @param t DOCUMENT ME!
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static StringColumn getStringColumn(Tree t, int index) {
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
            System.err.println("Syntax: TreemapExample <name>");
            System.exit(1);
        }
        Tree           t = new DefaultTree();
        AbstractReader reader = TreeReaderFactory.createReader(args[0], t);

        if (reader != null && reader.load()) {
            JFrame frame = new JFrame("TreemapExample");
            create(frame, t);
            frame.setVisible(true);
            frame.pack();
        } else {
            System.err.println("cannot load " + args[0]);
        }
    }
}
