/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.visualization;

import infovis.Graph;
import infovis.panel.ControlPanel;
import infovis.panel.StdVisualPanel;

import javax.swing.JComponent;

/**
 * Class GraphControlPanel
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class GraphControlPanel extends ControlPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GraphControlPanel(GraphVisualization visualization) {
        super(visualization);
    }
    
    public GraphVisualization getGraphVisualization() {
        return (GraphVisualization)getVisualization();
    }
    
    public Graph getGraph() {
        return getGraphVisualization().getGraph();
    }

    protected JComponent createStdVisualPane() {
        StdVisualPanel panel = new StdVisualPanel(getVisualization(), getFilter());
        panel.addOrientationButtons();
        return panel;
    }
}
