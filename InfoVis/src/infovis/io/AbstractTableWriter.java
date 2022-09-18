/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.io;

import infovis.Column;
import infovis.Table;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Abstract base class for Table writers.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractTableWriter {
    protected Writer out;
    protected Table table;
    protected StringBuffer buffer;
    /** DefaultTable of column labels */
    private ArrayList columnLabels;

	
    /**
     * Constructor for AbstractTableWriter
     *
     * @param out the Writer
     * @param table the Table.
     */
    public AbstractTableWriter(Writer out, Table table) {
	this.out = out;
	this.table = table;
	this.buffer = new StringBuffer();
    }
	
	
    /**
     * Returns a type name from a class.
     *
     * @param c the Class
     * @return a type name from a class.
     */
    public static String namedType(Class c) {
	if (c == Integer.class)
	    return "Integer";
	else if (c == Float.class)
	    return "Real";
	else if (c == Long.class)
	    return "Long";
	else if (c == String.class)
	    return "String";
	else 
	    return null;
    }

    /**
     * Adds the name of a Column to write to the list of written column.
     *
     * @param name the Column name.
     */
    public void addColumnLabel(String name) {
	if (this.columnLabels == null) {
	    this.columnLabels = new ArrayList();
	}

	this.columnLabels.add(name);
    }
	
    /**
     * Adds the name of all the non-internal Columns to the list of
     * written columns.
     */
    public void addAllColumnLabels() {
	int col;
		
	for (col = 0; col < this.table.getColumnCount(); col++) {
	    Column c = this.table.getColumnAt(col);

	    if (c.isInternal()) {
		continue;
	    }

	    addColumnLabel(c.getName());
	}
    }

    /**
     * Returns the name of the column to write at a specified index.
     *
     * @param col the index.
     *
     * @return the name of the column to write at a specified index.
     */
    public String getColumnLabelAt(int col) {
	return (String)this.columnLabels.get(col);
    }
	
    /**
     * Returns the ArrayList of column names to write.
     * @return the ArrayList of column names to write.
     */
    public ArrayList getColumnLabels() {
	return this.columnLabels;
    }
	
    /**
     * Write one character in the output writer.
     * 
     * @param c the character.
     */
    public final void write(char c) throws IOException {
	this.out.write(c);
    }
	
    /**
     * Write a string in the output writer.
     * 
     * @param s the string.
     */
    public final void write(String s) throws IOException {
	if (s == null)
	    return;
	else if (s.equals("")) {
	    this.out.write("''");
	}
	else
	    this.out.write(s);
    }
	
    /**
     * Write the StringBuffer in the output writer and clears it.
     */
    public void writeBuffer() throws IOException {
	this.out.write(this.buffer.toString());
	this.buffer.setLength(0);
    }
	
    /**
     * Write a newline in the ouput writer.
     */
    public void writeln() throws IOException {
	writeBuffer();
	write('\n');
    }
	
    /**
     * Abstract method that performs the actual writing of data.
     */
    public abstract boolean write();
}
