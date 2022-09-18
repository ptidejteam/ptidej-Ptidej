/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License
 * a copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.table.visualization;

import infovis.column.ColumnFilter;
import infovis.column.NumberColumn;
import infovis.column.filter.ComposeOrFilter;
import infovis.column.filter.InternalFilter;
import infovis.column.filter.NotNumberFilter;
import infovis.panel.FilteredColumnListModel;
import infovis.panel.StdVisualPanel;

import javax.swing.JComboBox;
import javax.swing.event.ListDataEvent;


/**
 * DOCUMENT ME!
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ScatterPlotVisualPanel extends StdVisualPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected FilteredColumnListModel xAxisModel;
    protected JComboBox               xAxisCombo;
    protected FilteredColumnListModel yAxisModel;
    protected JComboBox               yAxisCombo;

    /**
     * Constructor for ScatterPlotVisualPanel.
     *
     * @param visualization
     * @param filter
     */
    public ScatterPlotVisualPanel(ScatterPlotVisualization visualization,
                                  ColumnFilter filter) {
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
     * @see infovis.panel.StdVisualPanel#createAll(Visualization)
     */
    protected void createAll() {
        super.createAll();
        addXAxis();
        addYAxis();
    }

    protected void addXAxis() {
        this.xAxisModel = new FilteredColumnListModel(this.table, this.filter);
        this.xAxisModel.setFilter(new ComposeOrFilter(InternalFilter.sharedInstance(),
                                                 NotNumberFilter.sharedInstance()));
        this.xAxisModel.setNullAdded(true);
        this.xAxisCombo = createJCombo(this.xAxisModel,
                                  getScatterPlot().getXAxisColumn(),
                                  "X Axis by");
    }

    protected void addYAxis() {
        this.yAxisModel = new FilteredColumnListModel(this.table, this.filter);
        this.yAxisModel.setFilter(new ComposeOrFilter(InternalFilter.sharedInstance(),
                                                 NotNumberFilter.sharedInstance()));
        this.yAxisCombo = createJCombo(this.yAxisModel,
                                  getScatterPlot().getYAxisColumn(),
                                  "Y Axis by");
    }
    
    /**
     * @see infovis.panel.StdVisualPanel#contentsChanged(ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e) {
        if (e.getSource() == this.xAxisModel) {
            getScatterPlot().setXAxisColumn((NumberColumn)this.xAxisCombo.getSelectedItem());
        }
        else if (e.getSource() == this.yAxisModel) {
            getScatterPlot().setYAxisColumn((NumberColumn)this.yAxisCombo.getSelectedItem());
        }
        else
            super.contentsChanged(e);
    }

}
