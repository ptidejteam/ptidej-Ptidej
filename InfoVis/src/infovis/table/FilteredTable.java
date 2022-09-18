/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.table;

import infovis.Column;
import infovis.Table;
import infovis.column.ColumnFilter;
import infovis.column.ColumnNameComparator;
import infovis.column.filter.InternalFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Proxy Table filtering Columns.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class FilteredTable extends TableProxy implements TableModelListener {
    Column[] columns;
    ColumnFilter filter;
    Comparator comparator;
    ArrayList listenerList; // forward table structure changes insure we are in sync
    
    public FilteredTable(Table table, ColumnFilter filter, Comparator comp) {
        super(table);
        this.filter = filter;
        this.comparator = comp;
        this.listenerList = new ArrayList();
        table.addTableModelListener(this);
        update();
    }
    
    public FilteredTable(Table table, ColumnFilter filter) {
        this(table, filter, ColumnNameComparator.sharedInstance());
    }
    
    public FilteredTable(Table table) {
        this(table, InternalFilter.sharedInstance());
    }
    
    protected void update() {
        int count = super.getColumnCount();
        ArrayList cols = new ArrayList(count);
        for (int i = 0; i < count; i++) {
            Column c = super.getColumnAt(i);
            if (! isFiltered(c)) {
                cols.add(c);
            }
        }
        this.columns = new Column[cols.size()];
        cols.toArray(this.columns);
        if (this.comparator != null)
            Arrays.sort(this.columns, this.comparator);
    }
    
    public boolean isFiltered(Column c) {
        return this.filter != null && this.filter.filter(c);
    }
    
    /**
     * @see javax.swing.event.TableModelListener#tableChanged(TableModelEvent)
     */
    public void tableChanged(TableModelEvent e) {
        update();
        TableModelEvent mye = new TableModelEvent(this, e.getType());
        fireTableChanged(mye);
    }
    
    /**
     * @see infovis.Table#getColumn(String)
     */
    public Column getColumn(String name) {
        // pass internal name queries.
        if (name.length() > 0 && name.charAt(0)==INTERNAL_PREFIX)
            return super.getColumn(name);
        int index = indexOf(name);
        if (index == -1)
            return null;
        return getColumnAt(index);
    }
    
    /**
     * @see infovis.Table#getColumnCount()
     */
    public int getColumnCount() {
        return this.columns.length;
    }
    
    /**
     * Sorts the column according the the specified <code>Comparator</code>.
     * @param comp the Comparator.
     */
    public void sortColumns(Comparator comp) {
        this.comparator = comp;
        update();
    }

    /**
     * @see infovis.Table#getColumnAt(int)
     */
    public Column getColumnAt(int index) {
        if (index < 0 || index >= this.columns.length)
            return null;
        return this.columns[index];
    }


    /**
     * @see infovis.Table#indexOf(String)
     */
    public int indexOf(String name) {
        for (int i = 0; i < this.columns.length; i++) {
            if (this.columns[i].getName().equals(name))
                return i;
        }
        return -1;
    }
    
    /**
     * @see infovis.Table#indexOf(Column)
     */
    public int indexOf(Column column) {
        for (int i = 0; i < this.columns.length; i++) {
            if (this.columns[i] == column)
                return i;
        }
        return -1;
    }
    
    
    // TableModel implementation

    /**
     * @see javax.swing.table.TableModel#addTableModelListener(TableModelListener)
     */
    public void addTableModelListener(TableModelListener l) {
        this.listenerList.add(l);
    }

    /**
     * @see javax.swing.table.TableModel#removeTableModelListener(TableModelListener)
     */
    public void removeTableModelListener(TableModelListener l) {
        this.listenerList.remove(l);
    }
    
    /**
     * Forwards the given notification event to all
     * <code>TableModelListeners</code> that registered
     * themselves as listeners for this table model.
     *
     * @param e  the event to be forwarded
     */
    public void fireTableChanged(TableModelEvent e) {
        for (int i = this.listenerList.size() - 1; i >= 0; i--) {
            TableModelListener l = (TableModelListener)this.listenerList.get(i);
            l.tableChanged(e);
        }
    }
    /**
     * Returns the comparator.
     * @return Comparator
     */
    public Comparator getComparator() {
        return this.comparator;
    }

    /**
     * Returns the filter.
     * @return ColumnFilter
     */
    public ColumnFilter getFilter() {
        return this.filter;
    }

    /**
     * Sets the comparator.
     * @param comparator The comparator to set
     */
    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
        update();
    }

    /**
     * Sets the filter.
     * @param filter The filter to set
     */
    public void setFilter(ColumnFilter filter) {
        this.filter = filter;
        update();
    }

}

