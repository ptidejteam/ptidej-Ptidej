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
import infovis.utils.RowIterator;
import infovis.utils.TableIterator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


/**
 * Concrete Table.
 *
 * Implements all the methods of <code>Table</code> managing a
 * ArrayList of columns.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultTable implements Table {
    ArrayList column;
    String    name;
    ArrayList listenerList;
    Map       metadata;
    Map       clientPropery;

    /**
     * Creates a new DefaultTable object.
     */
    public DefaultTable() {
        this.column = new ArrayList();
        this.listenerList = new ArrayList();
    }

    // interface Metadata
    /**
     * Returns the clientPropery.
     * @return Map the clientPropery map.
     */
    public Map getClientPropery() {
        if (this.clientPropery == null) {
            this.clientPropery = new HashMap();
        }
        return this.clientPropery;
    }

    /**
     * Returns the metadata.
     * @return Map the metadata map.
     */
    public Map getMetadata() {
        if (this.metadata == null) {
            this.metadata = new HashMap();
        }
        return this.metadata;
    }

    // interface Table

    /**
     * @see infovis.Table#getName()
     */
    public String getName() {
        return this.name;
    }

    /**
     * @see infovis.Table#setName(String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @see Table#getColumnCount()
     */
    public int getColumnCount() {
        return this.column.size();
    }

    /**
     * @see Table#clear()
     */
    public void clear() {
        this.column.clear();
        fireTableStructureChanged();
    }


    /**
     * @see Table#addColumn(Column)
     */
    public void addColumn(Column c) {
        this.column.add(c);
        fireTableStructureChanged();
    }

    /**
     * @see Table#getColumnAt(int)
     */
    public Column getColumnAt(int index) {
        if (index < 0 || index >= getColumnCount())
            return null;
        return (Column)this.column.get(index);
    }

    /**
     * @see Table#setColumnAt(int,Column)
     */
    public void setColumnAt(int i, Column c) {
        this.column.set(i, c);
        fireTableStructureChanged();
    }

    /**
     * @see Table#indexOf(String)
     */
    public int indexOf(String name) {
        for (int i = 0; i < getColumnCount(); i++) {
            Column col = getColumnAt(i);
            if (col.getName().equals(name))
                return i;
        }
        return -1;
    }

    /**
     * @see infovis.Table#indexOf(Column)
     */
    public int indexOf(Column column) {
        for (int i = 0; i < getColumnCount(); i++) {
            Column col = getColumnAt(i);
            if (col == column)
                return i;
        }
        return -1;
    }

    /**
     * @see Table#getColumn(String)
     */
    public Column getColumn(String name) {
        for (int i = 0; i < getColumnCount(); i++) {
            Column col = getColumnAt(i);
            if (col.getName().equals(name))
                return col;
        }
        return null;
    }

    /**
     * @see Table#remove(Column)
     */
    public boolean remove(Column c) {
        if (this.column.remove(c)) {
            fireTableStructureChanged();
            return true;
        }
        return false;
    }
    /**
     * @see infovis.Table#iterator()
     */
    public RowIterator iterator() {
        return new TableIterator(0, getRowCount());
    }

    /**
     * @see infovis.Table#getTable()
     */
    public Table getTable() {
        return this;
    }
    
    /**
     * @see infovis.Table#isRowValid(int)
     */
    public boolean isRowValid(int row) {
        return row >= 0 && row < getRowCount();
    }


    // interface TableModel

    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex) {
        return getColumnAt(columnIndex).getName();
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        int max = 0;
        for (int index = 0; index < getColumnCount(); index++) {
            int r = getColumnAt(index).getRowCount();
            if (r > max)
                max = r;
        }

        return max;
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getColumnAt(columnIndex).getValueAt(rowIndex);
    }

    /**
     *  <code>TableModel</code> method for editable tables.
     *
     *  @param  aValue   value to assign to cell
     *  @param  rowPosition   row of cell
     *  @param  columnPosition  column of cell
     *
     * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            getColumnAt(columnIndex).setValueAt(rowIndex, (String)aValue);
        } catch (ParseException e) {
        }
    }

    /**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(int columnIndex) {
        return getColumnAt(columnIndex).getClass();
    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
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

    // Implementation specific methods

    /**
     * Test if the column is internal, i.e. the first character of its name is a '#'.
     *
     * @param col the column.
     *
     * @return <code>true</code>
     *  if the column is internal, i.e. the first character of its name is a '#'.
     */
    public static boolean isColumnInternal(Column col) {
        return col.getName().charAt(0) == INTERNAL_PREFIX;
    }

    // TableModel implementation


    /**
     * Notifies all listeners that all cell values in the table's
     * rows may have changed. The number of rows may also have changed
     * and the <code>JTable</code> should redraw the
     * table from scratch. The structure of the table (as in the order of the
     * columns) is assumed to be the same.
     *
     * @see TableModelEvent
     * @see javax.swing.event.EventListenerList
     * @see javax.swing.JTable#tableChanged(TableModelEvent)
     */
    public void fireTableDataChanged() {
        fireTableChanged(new TableModelEvent(this));
    }

    /**
     * Notifies all listeners that the table's structure has changed.
     * The number of columns in the table, and the names and types of
     * the new columns may be different from the previous state.
     * If the <code>JTable</code> receives this event and its
     * <code>autoCreateColumnsFromModel</code>
     * flag is set it discards any table columns that it had and reallocates
     * default columns in the order they appear in the model. This is the
     * same as calling <code>setModel(TableModel)</code> on the
     * <code>JTable</code>.
     *
     * @see TableModelEvent
     * @see javax.swing.event.EventListenerList
     */
    public void fireTableStructureChanged() {
        fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    /**
     * Forwards the given notification event to all
     * <code>TableModelListeners</code> that registered
     * themselves as listeners for this table model.
     *
     * @param e  the event to be forwarded
     *
     * @see #addTableModelListener
     * @see TableModelEvent
     * @see javax.swing.event.EventListenerList
     */
    public void fireTableChanged(TableModelEvent e) {
        for (int i = this.listenerList.size() - 1; i >= 0; i--) {
            TableModelListener l = (TableModelListener)this.listenerList.get(i);
            l.tableChanged(e);
        }
    }
}
