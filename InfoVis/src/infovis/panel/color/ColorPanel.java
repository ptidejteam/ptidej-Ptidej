/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel.color;

import infovis.Column;
import infovis.Table;
import infovis.Visualization;
import infovis.panel.ColumnListCellRenderer;
import infovis.panel.FilteredColumnListModel;
import infovis.visualization.ColorVisualization;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;


/**
 * Panel for managing the Color and ColorVisualization associated with
 * a BasicVisualization.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ColorPanel extends Box implements ListDataListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Visualization                  visualization;
    String                         visualColor;
    Table                          table;
    FilteredColumnListModel        model;
    JComboBox                      colorCombo;
    JPanel                         controlHolder;
    ColorVisualizationControlPanel control;

    /**
     * Creates a new ColorPanel object.
     *
     * @param visualization the Visualization.
     */
    public ColorPanel(Visualization visualization, String visualColor, String label) {
        super(BoxLayout.Y_AXIS);
        this.visualization = visualization;
        this.visualColor = visualColor;
        this.table = visualization.getTable();
        this.model = new FilteredColumnListModel(this.table);
        this.model.setNullAdded(true);
        this.colorCombo = new JComboBox(this.model);
        this.model.setSelectedItem(visualization.getVisualColumn(visualColor));
        this.colorCombo.setRenderer(new ColumnListCellRenderer());
        this.colorCombo.setBorder(BorderFactory.createTitledBorder(label));
        this.colorCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                                                (int)this.colorCombo.getPreferredSize()
                                                               .getHeight()));
        add(this.colorCombo);
        this.model.addListDataListener(this);

        this.controlHolder = new JPanel();
        this.controlHolder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        this.control = 
            ColorVisualizationControlPanelFactory.createControlPanel(
                visualization, visualColor);
        if (this.control != null)
            this.controlHolder.add(this.control);
        add(this.controlHolder);
    }

    public void contentsChanged(ListDataEvent e) {
        Column col = (Column)this.colorCombo.getSelectedItem();
        this.visualization.setVisualColumn(Visualization.VISUAL_COLOR, col);
        ColorVisualization vis = this.visualization.getColorVisualization(this.visualColor);
        if (this.control == null || vis != this.control.getColorVisualization()) {
            if (this.control != null)
                this.controlHolder.remove(this.control);
            this.control =
                ColorVisualizationControlPanelFactory.createControlPanel(this.visualization, this.visualColor);
            if (this.control != null)
                this.controlHolder.add(this.control);
        }
    }

    public void intervalAdded(ListDataEvent e) {
    }

    public void intervalRemoved(ListDataEvent e) {
    }
}
