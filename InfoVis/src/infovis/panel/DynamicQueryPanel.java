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
import infovis.column.FilterColumn;
import infovis.metadata.ValueCategory;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * Panel managing the Dynamic Query controls on the visualized table.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DynamicQueryPanel extends AbstractControlPanel
    implements TableModelListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Table        table;
    HashMap      columnDynamicQuery = new HashMap();
    FilterColumn filterColumn;
    ColumnFilter filter;
    JButton      hideFilteredButton;
    JButton      hideSelectedButton;
    JButton      showAllButton;

    /**
     * Constructor for DynamicQueryPanel.
     *
     * @param table the table.
     */
    public DynamicQueryPanel(Visualization vis, Table table) {
        this(vis, table, FilterColumn.findColumn(table, Table.FILTER_COLUMN));
    }

    /**
     * Creates a new DynamicQueryPanel object.
     *
     * @param table the Table to filter.
     * @param filterColumn the FilterColumn.
     */
    public DynamicQueryPanel(Visualization vis, Table table, FilterColumn filterColumn) {
        this(vis, table, filterColumn, null);
    }

    /**
     * Creates a new DynamicQueryPanel object.
     *
     * @param table the Table to filter.
     * @param filterColumn the FilterColumn.
     * @param filter the ColumnFilter.
     */
    public DynamicQueryPanel(Visualization vis,
                             Table table,
                             FilterColumn filterColumn,
                             ColumnFilter filter) {
        super(vis);
        this.table = table;
        this.filterColumn = filterColumn;
        this.filter = filter;
        Box hbox = new Box(BoxLayout.X_AXIS);
        
        this.hideFilteredButton = new JButton("Hide Filtered");
        this.hideFilteredButton.addActionListener(this);
        hbox.add(this.hideFilteredButton);
        this.hideSelectedButton = new JButton("Hide Selected");
        this.hideSelectedButton.addActionListener(this);
        hbox.add(this.hideSelectedButton);
        this.showAllButton = new JButton("Show All");
        this.showAllButton.addActionListener(this);
        hbox.add(this.showAllButton);
                
        add(hbox);
        addMissingControls();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.hideFilteredButton) {
            getVisualization().hideFilteredRows();
        }
        else if (e.getSource() == this.hideSelectedButton) {
            getVisualization().hideSelectedRows();
        }
        else if (e.getSource() == this.showAllButton) {
            getVisualization().showAllRows();
        }
        else
            super.actionPerformed(e);
    }



    /**
     * Creates a default control for the specified column.
     *
     * @param c the column.
     */
    public void createDefaultControl(Column c) {
        int category = ValueCategory.findValueCategory(c);

        if (category != ValueCategory.TYPE_CATEGORIAL) {
            DynamicQuery dq = DynamicQuerylFactory.createDynamicQuery(c);
            if (dq == null)
                return;
            JComponent rs = dq.getComponent();
            setMaximumSize(rs);
            setTitleBorder(rs, c.getName());
            rs.setEnabled(true);
            add(rs);
            this.columnDynamicQuery.put(c, dq);
            dq.setFilterColumn(this.filterColumn);
        }
    }

    /**
     * Removes the control associated with a specified column.
     *
     * @param c the column.
     */
    public void removeControl(Column c) {
        DynamicQuery dq = (DynamicQuery)this.columnDynamicQuery.remove(c);
        if (dq != null) {
            remove(dq.getComponent());
            dq.setFilterColumn(null);
            dq = null; // should dispose it.
        }
    }
    
    public boolean filter(Column c) {
        if (this.filter != null)
            return this.filter.filter(c);
        return false;
    }

    /**
     * Adds Dynamic Query controls for all columns in the table unless one already
     * exists.
     */
    public void addMissingControls() {
        Set knownColumn = new TreeSet(this.columnDynamicQuery.keySet());
        for (int col = 0; col < this.table.getColumnCount(); col++) {
            Column c = this.table.getColumnAt(col);
            if (filter(c))
                continue;
            if (!knownColumn.remove(c)) {
                createDefaultControl(c);
            }
        }
        for (Iterator iter = knownColumn.iterator(); iter.hasNext();) {
            Column c = (Column)iter.next();
            removeControl(c);
        }
    }

    /**
     * @see javax.swing.event.TableModelListener#tableChanged(TableModelEvent)
     */
    public void tableChanged(TableModelEvent e) {
        addMissingControls();
    }

    // Filter management
    public int columnOffset(Column column) {
        int offset = this.table.indexOf(column);
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("column is not in table");
        }
        return offset;
    }

    /**
     * Returns the filterColumn.
     * @return FilterColumn
     */
    public FilterColumn getFilterColumn() {
        return this.filterColumn;
    }

    /**
     * Sets the filterColumn.
     * @param filterColumn The filterColumn to set
     */
    public void setFilterColumn(FilterColumn filterColumn) {
        this.filterColumn = filterColumn;
    }

    /**
     * Returns the dynamic query associated with a specified column.
     *
     * @param c the column.
     *
     * @return the dynamic query associated with a specified column or null if
     *  no dynamic query exist for this column.
     */
    public DynamicQuery getColumnDynamicQuery(Column c) {
        return (DynamicQuery)this.columnDynamicQuery.get(c);
    }
    /**
     * Returns the filter.
     * @return ColumnFilter
     */
    public ColumnFilter getFilter() {
        return this.filter;
    }

    /**
     * Sets the filter.
     * @param filter The filter to set
     */
    public void setFilter(ColumnFilter filter) {
        this.filter = filter;
        addMissingControls();
    }

}
