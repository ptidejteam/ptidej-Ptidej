/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License
 * a copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.table;

import infovis.Column;
import infovis.Table;
import infovis.utils.RowIterator;

import java.util.Map;

import javax.swing.event.TableModelListener;


/**
 * A Table Proxy implements an <code>Table</code> by forwarding all
 * the methods to an internal <code>Table</code>.
 * 
 * <p>This class is useful to
 * implements higher level containers out of a standard
 * <code>DefaultTable</code>.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TableProxy implements Table {
    protected Table table;

    /**
     * Creates a new TableProxy object.
     *
     * @param table The underlying table.
     *
     * @see infovis.Table#getTable()
     */
    public TableProxy(Table table) {
        this.table = table;
    }

    /**
     * @see infovis.Metadata#getClientPropery()
     */
    public Map getClientPropery() {
        return this.table.getClientPropery();
    }

    /**
     * @see infovis.Metadata#getMetadata()
     */
    public Map getMetadata() {
        return this.table.getMetadata();
    }

    /**
     * @see infovis.Table#getName()
     */
    public String getName() {
        return this.table.getName();
    }

    /**
     * @see infovis.Table#setName(String)
     */
    public void setName(String name) {
        this.table.setName(name);
    }

    /**
     * @see infovis.Table#getColumnCount()
     */
    public int getColumnCount() {
        return this.table.getColumnCount();
    }

    /**
     * @see infovis.Table#clear()
     */
    public void clear() {
        this.table.clear();
    }

    /**
     * @see infovis.Table#addColumn(Column)
     */
    public void addColumn(Column c) {
        this.table.addColumn(c);
    }

    /**
     * @see infovis.Table#getColumnAt(int)
     */
    public Column getColumnAt(int index) {
        return this.table.getColumnAt(index);
    }

    /**
     * @see infovis.Table#setColumnAt(int, Column)
     */
    public void setColumnAt(int i, Column c) {
        this.table.setColumnAt(i, c);
    }

    /**
     * @see infovis.Table#indexOf(String)
     */
    public int indexOf(String name) {
        return this.table.indexOf(name);
    }

    /**
     * @see infovis.Table#indexOf(Column)
     */
    public int indexOf(Column column) {
        return this.table.indexOf(column);
    }

    /**
     * @see infovis.Table#getColumn(String)
     */
    public Column getColumn(String name) {
        return this.table.getColumn(name);
    }

    /**
     * @see infovis.Table#remove(Column)
     */
    public boolean remove(Column c) {
        return this.table.remove(c);
    }

    /**
     * @see infovis.Table#iterator()
     */
    public RowIterator iterator() {
        return this.table.iterator();
    }

    /**
     * Returns the table.
     *
     * @return Table
     */
    public Table getTable() {
        return this.table.getTable();
    }
    
    /**
     * @see infovis.Table#isRowValid(int)
     */
    public boolean isRowValid(int row) {
        return this.table.isRowValid(row);
    }

    // interface TableModel
    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex) {
        return this.table.getColumnName(columnIndex);
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return this.table.getRowCount();
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.table.getValueAt(rowIndex, columnIndex);
    }

    /**
     * @see javax.swing.table.TableModel#setValueAt(Object, int, int)
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        this.table.setValueAt(aValue, rowIndex, columnIndex);
    }

    /**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     */
    public Class getColumnClass(int columnIndex) {
        return this.table.getColumnClass(columnIndex);
    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return this.table.isCellEditable(rowIndex, columnIndex);
    }

    /**
     * @see javax.swing.table.TableModel#addTableModelListener(TableModelListener)
     */
    public void addTableModelListener(TableModelListener l) {
        this.table.addTableModelListener(l);
    }

    /**
     * @see javax.swing.table.TableModel#removeTableModelListener(TableModelListener)
     */
    public void removeTableModelListener(TableModelListener l) {
        this.table.removeTableModelListener(l);
    }
}
