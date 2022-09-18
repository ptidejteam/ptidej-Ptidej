/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.visualization;

import infovis.panel.ControlPanel;

import javax.swing.JComponent;

/**
 * Class NodeLinkGraphControlPanel
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NodeLinkGraphControlPanel extends ControlPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NodeLinkGraphControlPanel(NodeLinkGraphVisualization visualization) {
        super(visualization);
    }
    
    public NodeLinkGraphVisualization getNodeLinkVisualization() {
        return (NodeLinkGraphVisualization)getVisualization();
    }
    
    protected JComponent createStdVisualPane() {
        NodeLinkGraphVisualPanel visualPanel =
            new NodeLinkGraphVisualPanel(getVisualization(), getFilter());
        return visualPanel;
    }
    
}
