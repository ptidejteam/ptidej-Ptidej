/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.visualization;

import infovis.Column;
import infovis.Visualization;
import infovis.column.ColumnFilter;
import infovis.panel.FilteredColumnListModel;
import infovis.panel.StdVisualPanel;

import javax.swing.JComboBox;
import javax.swing.event.ListDataEvent;

/**
 * Panel for Visual Controls on Treemaps.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class MatrixVisualPanel extends StdVisualPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected FilteredColumnListModel vertexLabelModel;
    protected JComboBox               vertexLabelCombo;
    protected FilteredColumnListModel sortRowModel;
    protected JComboBox               sortRowCombo;
    protected FilteredColumnListModel sortColumnModel;
    protected JComboBox               sortColumnCombo;

    public MatrixVisualization getMatrix() {
        return (MatrixVisualization)getVisualization();
    }

    /**
     * Constructor for MatrixVisualPanel.
     * @param visualization
     */
    public MatrixVisualPanel(MatrixVisualization visualization, ColumnFilter filter) {
        super(visualization, filter);
    }
    
    /**
     * @see infovis.panel.StdVisualPanel#createAll(Visualization)
     */
    protected void createAll() {
        addColor(getVisualization());
        addLabel(getVisualization());
        addVertexLabel(getVisualization());
        addSortRow(getVisualization());
        addSortColumn(getVisualization());
    }
    
    /**
     * Adds a JComboBox to change the column used for labeling vertices.
     *
     * param visualization the Visualization.
     */
    protected void addVertexLabel(Visualization visualization) {        
        MatrixVisualization matrix = getMatrix();
        this.vertexLabelModel = new FilteredColumnListModel(matrix.getGraph().getVertexTable(), this.filter);
        this.vertexLabelCombo = createJCombo(this.vertexLabelModel, matrix.getVertexLabelColumn(),
                                  "Label Vertex by");
        
    }

    /**
     * Adds a JComboBox to change the column used for sorting rows.
     *
     * param visualization the Visualization.
     */
    protected void addSortRow(Visualization visualization) {       
        MatrixVisualization matrix = getMatrix();
        this.sortRowModel = new FilteredColumnListModel(matrix.getGraph().getVertexTable(), this.filter);
        this.sortRowCombo = createJCombo(this.sortRowModel, null, "Sort Row by");
    }
    
    /**
     * Adds a JComboBox to change the column used for sorting columns.
     *
     * param visualization the Visualization.
     */
    protected void addSortColumn(Visualization visualization) {       
        MatrixVisualization matrix = getMatrix();
        this.sortColumnModel = new FilteredColumnListModel(matrix.getGraph().getVertexTable(), this.filter);
        this.sortColumnCombo = createJCombo(this.sortColumnModel, null, "Sort Column by");
    }
    
    /**
     * @see infovis.panel.StdVisualPanel#contentsChanged(ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e) {
        if (e.getSource() == null)
            return;
        if (e.getSource() == this.vertexLabelModel) {
            Column col = (Column)this.vertexLabelCombo.getSelectedItem();
            getMatrix().setVertexLabelColumn(col);
        }
        else if (e.getSource() == this.sortRowModel) {
            Column col = (Column)this.sortRowCombo.getSelectedItem();
            getMatrix().setRowSortColumn(col);
        }
        else if (e.getSource() == this.sortColumnModel) {
            Column col = (Column)this.sortColumnCombo.getSelectedItem();
            getMatrix().setColumnSortColumn(col);
        }
        else
            super.contentsChanged(e);
    }

}
