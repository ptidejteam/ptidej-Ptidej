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
import infovis.panel.ControlPanel;
import infovis.tree.DefaultTree;
import infovis.tree.io.TreeReaderFactory;
import infovis.tree.visualization.IcicleTreeVisualization;

import javax.swing.JFrame;


/**
 * Example of Node Link diagram visualization.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class IcicleTreeExample {
    /**
     * Create a Treemap visualization of a specified Tree in a specified JFrame.
     *
     * @param frame the JFrame
     * @param t the Tree.
     */
    public static void create(JFrame frame, Tree t) {
        IcicleTreeVisualization visualization = new EditableIcicleTree(t);

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
            System.err.println("Syntax: IcicleTreeExample <name>");
            System.exit(1);
        }
        Tree           t = new DefaultTree();
        if (TreeReaderFactory.readTree(args[0], t)) {
            JFrame frame = new JFrame("IcicleTreeExample");
            create(frame, t);
            frame.setVisible(true);
            frame.pack();
        } else {
            System.err.println("cannot load " + args[0]);
        }
    }
}
