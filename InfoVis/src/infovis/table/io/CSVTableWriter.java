/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.table.io;

import infovis.Column;
import infovis.Table;
import infovis.io.AbstractTableWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;


/**
 * Writer in Excel CVS format for tables.
 * 
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 */
public class CSVTableWriter extends AbstractTableWriter {
    char separator;
    /** True if first line is labels. */
    boolean labelLinePresent;
    /** True if types are declared. */
    boolean typeLinePresent;

    /**
     * Constructor for CSVTableWriter.
     * 
     * @param out
     * @param table
     */
    public CSVTableWriter(Writer out, Table table) {
	super(out, table);
	this.separator = '\t';
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean write() {
	int col;

	ArrayList labels = getColumnLabels();
	if (labels == null) {
	    addAllColumnLabels();
	    labels = getColumnLabels();
	}

	try {
	    if (this.labelLinePresent) {
		for (col = 0; col < labels.size(); col++) {
		    if (col != 0)
			write(this.separator);
		    write(getColumnLabelAt(col));
					
		}
		write('\n');
	    }
	    if (this.typeLinePresent) {
		for (col = 0; col < labels.size(); col++) {
		    if (col != 0)
			write(this.separator);
		    Column c = this.table.getColumn(getColumnLabelAt(col));
		    write(namedType(c.getValueClass()));
		}
		write('\n');
	    }
			
	    int nrow = this.table.getColumnCount();
	    for (int row = 0; row < nrow; row++) {
		for (col = 0; col < labels.size(); col++) {
		    if (col != 0)
			write(this.separator);
		    Column c = this.table.getColumn(getColumnLabelAt(col));
		    write(c.getValueAt(row));
		}
		write('\n');
	    }
	    this.out.flush();
	}
	catch (IOException e) {
	    return false;
	}
		
	return true;
    }

    /**
     * Returns the separator.
     * 
     * @return char
     */
    public char getSeparator() {
	return this.separator;
    }

    /**
     * Sets the separator.
     * 
     * @param separator The separator to set
     */
    public void setSeparator(char separator) {
	this.separator = separator;
    }
    /**
     * Returns the labelLinePresent.
     * @return boolean
     */
    public boolean isLabelLinePresent() {
	return this.labelLinePresent;
    }

    /**
     * Returns the typeLinePresent.
     * @return boolean
     */
    public boolean isTypeLinePresent() {
	return this.typeLinePresent;
    }

    /**
     * Sets the labelLinePresent.
     * @param labelLinePresent The labelLinePresent to set
     */
    public void setLabelLinePresent(boolean labelLinePresent) {
	this.labelLinePresent = labelLinePresent;
    }

    /**
     * Sets the typeLinePresent.
     * @param typeLinePresent The typeLinePresent to set
     */
    public void setTypeLinePresent(boolean typeLinePresent) {
	this.typeLinePresent = typeLinePresent;
    }

}
