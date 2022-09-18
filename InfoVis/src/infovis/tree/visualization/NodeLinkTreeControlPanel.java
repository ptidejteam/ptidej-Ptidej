/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization;

import infovis.column.ColumnFilter;

import javax.swing.JComponent;

/**
 * Class NodeLinkTreeControlPanel
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NodeLinkTreeControlPanel extends TreeControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeLinkTreeControlPanel(NodeLinkTreeVisualization visualization) {
        this(visualization, TreeStructrureFilter.sharedInstance());
    }
    
    NodeLinkTreeControlPanel(NodeLinkTreeVisualization visualization, ColumnFilter filter) {
        super(visualization, filter);
    }
    
    protected JComponent createStdVisualPane() {
        JComponent ret = new NodeLinkTreeVisualPanel(getVisualization(), getFilter());
        return ret;
    }
}
