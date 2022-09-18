/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.table.visualization;

import infovis.column.ColumnFilter;
import infovis.panel.ControlPanel;

import javax.swing.JComponent;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ScatterPlotControlPanel extends ControlPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor for ScatterPlotControlPanel.
     * @param visualization
     */
    public ScatterPlotControlPanel(ScatterPlotVisualization visualization) {
        super(visualization);
    }

    /**
     * Constructor for ScatterPlotControlPanel.
     * @param visualization
     * @param filter
     */
    public ScatterPlotControlPanel(
        ScatterPlotVisualization visualization, ColumnFilter filter) {
        super(visualization, filter);
    }

    /**
     * Returns the ScatterPlotVisualization.
     * 
     * @return the ScatterPlotVisualization.
     */
    public ScatterPlotVisualization getScatterPlot() {
        return (ScatterPlotVisualization)getVisualization();
    }

    /**
     * @see infovis.panel.ControlPanel#createStdVisualPane()
     */
    protected JComponent createStdVisualPane() {
        return new ScatterPlotVisualPanel(getScatterPlot(), getFilter());
    }

}
