/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization;

import infovis.Visualization;
import infovis.column.ColumnFilter;
import infovis.panel.StdVisualPanel;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreeVisualPanel extends StdVisualPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor for TreeVisualPanel.
     * @param visualization
     * @param filter
     */
    public TreeVisualPanel(Visualization visualization, ColumnFilter filter) {
        super(visualization, filter);
    }
    
    public TreeVisualization getTreeVisualization() {
        return (TreeVisualization)getVisualization();
    }
    
    protected void createAll() {
        super.createAll();
        addOrientationButtons();
    }

}
