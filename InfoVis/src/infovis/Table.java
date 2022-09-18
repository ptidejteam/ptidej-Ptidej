/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis;

import infovis.utils.RowIterator;

import javax.swing.table.TableModel;


/**
 * The <code>Table</code> is the base class of all infovis dataset
 * containers.
 *
 * <p>An abstract table manages a list of columns.  Each column is an
 * indexed container of homogeneous type such as integers (int),
 * floats or Objects.  Each column has also a name.  By convention,
 * when the name of a column starts with a '#' character (sharp sign),
 * the column is considered "internal", i.e not user supplied.
 * Internal columns are used to manage trees and graphs using a table
 * structure, where the internal structure of the tree or the graph
 * should not be confused with user supplied attributes.</p>
 *
 * <p>Tables, like columns, also manage metadata and client data.
 * Metadata are useful to store information about the nature of the
 * table that could be saved along with the data, i.e.  a textual
 * description of the origin of the data.  Client data is useful to
 * store run-time information associated with the table.</p>
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface Table extends Metadata, TableModel {
    /**
     * NIL value for a table index.
     */
    public static final int NIL = -1;
	
    /**
     * Prefixes the name of columns used for internal purposes.
     */
    public static final char INTERNAL_PREFIX = '#';
	
    /**
     * Name of the BooleanColumn managing the selection, if one exists.
     */
    public static final String SELECTION_COLUMN = "#selection";

    /**
     * Name of the LongColumn managing the dynamic filtering, if one exists.
     */	
    public static final String FILTER_COLUMN = "#filter";

    /**
     * Returns the Table name.
     * @return the Table name.
     */
    public String getName();

    /**
     * Sets the Table name.
     * @param name The Table name to set
     */
    public void setName(String name);
    
    /**
     * Returns the number of columns of the table.
     *
     * @return the number of columns of the table.
     */
    public int getColumnCount();
    
    /**
     * Clears the table.
     */
    public void clear();

    /**
     * Adds a column.
     *
     * @param c the column.
     */
    public void addColumn(Column c);

    /**
     * Returns the column at a specified index.
     *
     * @param index the index.
     *
     * @return the column at a specified index
     * or null if the index is out of range.
     */
    public Column getColumnAt(int index);

    /**
     * Replaces the column at a specified index.
     *
     * @param i the index.
     * @param c the column.
     */
    public void setColumnAt(int i, Column c);

    /**
     * Returns the index of a column of a specified name.
     *
     * @param name the name.
     *
     * @return the index of a column of a specified name
     * 	or -1 if no such column exist.
     */
    public int indexOf(String name);
	
    /**
     * Returns the index of a specified column
     *
     * @param name the column.
     *
     * @return the index of a specified column
     * 	or -1 if the column is not in the table..
     */
    public int indexOf(Column column);

    /**
     * Returns the column of a specified name.
     *
     * @param name the name.
     *
     * @return the column of a specified name.
     */
    public Column getColumn(String name);

    /**
     * Removes a column from the table.
     *
     * @param c the column.
     *
     * @return <code>true</code> if the column has been removed.
     */
    public boolean remove(Column c);

    /**
     * Returns the number of rows in the table.
     * 
     * @return the number of rows in the table.
     */
    public int getRowCount();

    /**
     * Returns an iterator over the columns of this table.
     *
     * @return an iterator over the columns of this table.
     */
    public RowIterator iterator();
    
    /**
     * Returns the real table for Proxies and this for a concrete table.
     */
    public Table getTable();
    
    /**
    * Checks whether a specified row is valid.
    *
    * @param row the row.
    * 
    * @return <code>true</code> if it is.
    */
   public boolean isRowValid(int row);
}
