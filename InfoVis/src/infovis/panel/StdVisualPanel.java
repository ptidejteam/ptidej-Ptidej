/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import infovis.Column;
import infovis.Table;
import infovis.Visualization;
import infovis.column.ColumnFilter;
import infovis.column.NumberColumn;
import infovis.column.filter.ComposeOrFilter;
import infovis.column.filter.InternalFilter;
import infovis.column.filter.NotNumberFilter;
import infovis.panel.color.ColorPanel;
import infovis.visualization.Orientable;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;


/**
 * Control panel for standard visual compoents such as size and label.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class StdVisualPanel extends AbstractControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ColumnFilter            filter;
    protected Table                   table;
    protected ColorPanel              colorPanel;
    protected JCheckBox               smooth;
    protected FilteredColumnListModel sizeModel;
    protected JComboBox               sizeCombo;
    protected FilteredColumnListModel labelModel;
    protected JCheckBox               labelItems;
    protected JCheckBox               displayStatistics;
    protected JComboBox               labelCombo;
    protected FilteredColumnListModel sortModel;
    protected JComboBox               sortCombo;
    protected ButtonGroup orientationGroup;
    protected Box orientation;
    protected JRadioButton orientationNorth;
    protected JRadioButton orientationSouth;
    protected JRadioButton orientationEast;
    protected JRadioButton orientationWest;

    /**
     * Constructor for StdVisualPanel.
     */
    public StdVisualPanel(Visualization visualization, ColumnFilter filter) {
        super(visualization);
        this.filter = filter;
        this.table = getVisualization().getTable();
        createAll();
    }

    protected void createAll() {
        addColor(getVisualization());
        addSize(getVisualization());
        addLabel(getVisualization());
        addSort(getVisualization());
    }

    protected void addSort(Visualization visualization) {
        
        this.sortModel = new FilteredColumnListModel(this.table, this.filter);
        this.sortCombo = createJCombo(this.sortModel, null, "Sort by");
    }

    protected void addLabel(Visualization visualization) {        
        this.labelModel = new FilteredColumnListModel(this.table, this.filter);
        this.labelCombo = createJCombo(this.labelModel,
            visualization.getVisualColumn(Visualization.VISUAL_LABEL),
                                  "Label by");
        
        Box checkBox = new Box(BoxLayout.X_AXIS);                          
        this.labelItems = new JCheckBox("Label all items");
        this.labelItems.setSelected(visualization.isShowingLabel());
        this.labelItems.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                getVisualization().setShowingLabel(StdVisualPanel.this.labelItems.isSelected());
            }
        });
        checkBox.add(this.labelItems);
        
        this.displayStatistics = new JCheckBox("Display stats");
        this.displayStatistics.setSelected(visualization.isDisplayingStatistics());
        this.displayStatistics.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                getVisualization().setDisplayingStatistics(StdVisualPanel.this.displayStatistics.isSelected());
            }
        });
        checkBox.add(this.displayStatistics);
        add(checkBox);
    }

    protected void addSize(Visualization visualization) {
        this.sizeModel = new FilteredColumnListModel(this.table, this.filter);
        this.sizeModel.setFilter(new ComposeOrFilter(InternalFilter.sharedInstance(),
                                                NotNumberFilter.sharedInstance()));
        this.sizeModel.setNullAdded(true);
        this.sizeCombo = createJCombo(this.sizeModel,
            visualization.getVisualColumn(Visualization.VISUAL_SIZE),
                                 "Size by");
    }

    protected void addColor(Visualization visualization) {
        
        this.colorPanel = new ColorPanel(visualization, Visualization.VISUAL_COLOR, "Color by");
        add(this.colorPanel);

        this.smooth = new JCheckBox("Smooth");
        
        this.smooth.setSelected(visualization.isSmooth());
        this.smooth.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                getVisualization().setSmooth(StdVisualPanel.this.smooth.isSelected());
            }
        });
        add(this.smooth);
    }

    protected JComboBox createJCombo(FilteredColumnListModel model, Column c,
                                     String label) {
        model.setNullAdded(true);
        JComboBox combo = new JComboBox(model);
        model.setSelectedItem(c);
        combo.setRenderer(new ColumnListCellRenderer());
        return addJCombo(label, combo);
    }

    protected JComboBox addJCombo(
        String label,
        JComboBox combo) {
        setTitleBorder(combo, label);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                                           (int)combo.getPreferredSize()
                                                     .getHeight()));
        add(combo);
        combo.getModel().addListDataListener(this);
        return combo;
    }
    
    public void addOrientationButtons() {
        this.orientationGroup = new ButtonGroup();
        this.orientation = new Box(BoxLayout.X_AXIS);
        setTitleBorder(this.orientation, "Orientation");
        
        this.orientationNorth = new JRadioButton("North");
        this.orientationGroup.add(this.orientationNorth);
        this.orientationNorth.setSelected(
            getVisualization().getOrientation()==Orientable.ORIENTATION_NORTH);
        this.orientationNorth.addActionListener(this);
        this.orientation.add(this.orientationNorth);
        
        this.orientationSouth = new JRadioButton("South");
        this.orientationGroup.add(this.orientationSouth);
        this.orientationSouth.setSelected(
            getVisualization().getOrientation()==Orientable.ORIENTATION_SOUTH);
        this.orientationSouth.addActionListener(this);
        this.orientation.add(this.orientationSouth);
        
        this.orientationEast = new JRadioButton("East");
        this.orientationGroup.add(this.orientationEast);
        this.orientationNorth.setSelected(
            getVisualization().getOrientation()==Orientable.ORIENTATION_EAST);
        this.orientationEast.addActionListener(this);
        this.orientation.add(this.orientationEast);
        
        this.orientationWest = new JRadioButton("West");
        this.orientationGroup.add(this.orientationWest);
        this.orientationNorth.setSelected(
            getVisualization().getOrientation()==Orientable.ORIENTATION_WEST);
        this.orientationWest.addActionListener(this);
        this.orientation.add(this.orientationWest);
        
        add(this.orientation);
    }
    

    /**
     * @see javax.swing.event.ListDataListener#contentsChanged(ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e) {
        if (e.getSource() == this.sizeModel) {
            NumberColumn col = (NumberColumn)this.sizeCombo.getSelectedItem();
            getVisualization().setVisualColumn(Visualization.VISUAL_SIZE, col);
        }
        else if (e.getSource() == this.labelModel) {
            getVisualization().setVisualColumn(Visualization.VISUAL_LABEL, (Column)this.labelCombo.getSelectedItem());
        }
        else if (e.getSource() == this.sortModel) {
            getVisualization().setVisualColumn(Visualization.VISUAL_SORT, (Column)this.sortCombo.getSelectedItem());
        }
        super.contentsChanged(e);
    }
    
    public void actionPerformed(ActionEvent e) {    
        if (e.getSource() == this.orientationNorth) {
            getVisualization().setOrientation(Orientable.ORIENTATION_NORTH);
            getVisualization().repaint();
        }
        else if (e.getSource() == this.orientationSouth) {
            getVisualization().setOrientation(Orientable.ORIENTATION_SOUTH);
            getVisualization().repaint();
        }
        else if (e.getSource() == this.orientationEast) {
            getVisualization().setOrientation(Orientable.ORIENTATION_EAST);
            getVisualization().repaint();
        }
        else if (e.getSource() == this.orientationWest) {
            getVisualization().setOrientation(Orientable.ORIENTATION_WEST);
            getVisualization().repaint();
        }
        else
            super.actionPerformed(e);
    }
    
    /**
     * Returns the labelCombo.
     * @return JComboBox
     */
    public JComboBox getLabelCombo() {
        return this.labelCombo;
    }

    /**
     * Returns the labelModel.
     * @return FilteredColumnListModel
     */
    public FilteredColumnListModel getLabelModel() {
        return this.labelModel;
    }

    /**
     * Returns the sizeCombo.
     * @return JComboBox
     */
    public JComboBox getSizeCombo() {
        return this.sizeCombo;
    }

    /**
     * Returns the sizeModel.
     * @return FilteredColumnListModel
     */
    public FilteredColumnListModel getSizeModel() {
        return this.sizeModel;
    }

    /**
     * Returns the table.
     * @return Table
     */
    public Table getTable() {
        return this.table;
    }
}
