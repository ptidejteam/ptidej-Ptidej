/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization;

import infovis.Tree;
import infovis.column.ColumnFilter;
import infovis.panel.ControlPanel;
import infovis.panel.DynamicQueryPanel;

import javax.swing.JComponent;


/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreeControlPanel extends ControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected DynamicQueryPanel treeQueryPanel;

    /**
     * Constructor for TreeControlPanel.
     * 
     * @param visualization the visualization.
     */
    public TreeControlPanel(TreeVisualization visualization) {
        this(visualization, TreeStructrureFilter.sharedInstance());
    }
    
    /**
     * Constructor for TreeControlPanel.
     * 
     * @param visualization the visualization.
     * @param filter the ColumnFilter.
     */
    public TreeControlPanel(TreeVisualization visualization, ColumnFilter filter) {
        super(visualization, filter);
    }
    
    /**
     * Returns the managed tree visualization.
     * 
     * @return the managed tree visualization.
     */
    public TreeVisualization getTreeVisualization() {
        return (TreeVisualization)getVisualization();
    }

    /**
     * Returns the managed tree.
     *
     * @return the managed tree.
     */
    public Tree getTree() {
        return getTreeVisualization().getTree();
    }

    /**
     * @see infovis.panel.ControlPanel#createStdVisualPane()
     */
    protected JComponent createStdVisualPane() {
        JComponent ret = new TreeVisualPanel(getVisualization(), getFilter());
        return ret;
    }

}
