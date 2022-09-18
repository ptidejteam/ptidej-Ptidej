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
import infovis.column.BooleanColumn;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;


/**
 * Table Model for displaying the details of the selected rows of a
 * visualization.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DetailTable implements TableModel, TableModelListener,
                                    ChangeListener {
    protected Table table;
    BooleanColumn   selection;
    ArrayList       listenerList;

    /**
     * Constructor for DetailTable.
     */
    public DetailTable(Table table, BooleanColumn selection) {
        this.table = table;
        this.selection = selection;
    }

    /**
     * @see javax.swing.table.TableModel#addTableModelListener(TableModelListener)
     */
    public void addTableModelListener(TableModelListener l) {
        if (this.listenerList == null) {
            this.listenerList = new ArrayList();
            this.table.addTableModelListener(this);
            this.selection.addChangeListener(this);
        }
        this.listenerList.add(l);
    }

    /**
     * @see javax.swing.table.TableModel#removeTableModelListener(TableModelListener)
     */
    public void removeTableModelListener(TableModelListener l) {
        this.listenerList.remove(l);
        if (this.listenerList.size() == 0) {
            this.listenerList = null;
            this.selection.removeChangeListener(this);
            this.table.removeTableModelListener(this);
        }
    }

    /**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(int columnIndex) {
        return String.class;
    }

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return 1 + this.selection.getSelectedCount();
    }

    /**
     * DOCUMENT ME!
     *
     * @param columnPosition DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getRowAt(int columnIndex) {
        if (columnIndex == 0)
            return -1;
        columnIndex--;
        for (int index = this.selection.getMinSelectionIndex();
                 index <= this.selection.getMaxSelectionIndex(); index++) {
            if (this.selection.isSelectedIndex(index) && columnIndex-- == 0) {
                return index;
            }
        }
        return -1;
    }

    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex) {
        if (columnIndex == 0)
            return "Attribute";

        int index = getRowAt(columnIndex);
        if (index == -1)
            return null;
        return "Row " + index;
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return this.table.getColumnCount();
    }

    public Column getColumnAt(int index) {
        return this.table.getColumnAt(index);
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            return getColumnAt(rowIndex).getName();
        }
        int index = getRowAt(columnIndex);
        if (index != -1)
            return getColumnAt(rowIndex).getValueAt(index);
        return null;
    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        // The selection has changed.
        fireTableStructureChanged();
    }

    /**
     * @see javax.swing.event.TableModelListener#tableChanged(TableModelEvent)
     */
    public void tableChanged(TableModelEvent e) {
        // The underlying Table has changed.
        fireTableStructureChanged();
    }

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
     * default columns in the order they appear in the sizeModel. This is the
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
     * themselves as listeners for this table sizeModel.
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
