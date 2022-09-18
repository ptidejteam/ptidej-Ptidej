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
import infovis.io.AbstractTableReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


/**
 * Read an Excel CSV format into a table
 * 
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 */
public class CSVTableReader extends AbstractTableReader {
    /** Field separator. */
    protected char separator;
    /** Number of lines to skip at the beginning of the file. */
    protected int skipLines;
    /** True if first line is labels. */
    protected boolean labelLinePresent;
    /** True if types are declared. */
    protected boolean typeLinePresent;
    /** DefaultTable of column labels */
    protected ArrayList labels = new ArrayList();
    /** True is considering quotes */
    protected boolean consideringQuotes = true;

    /**
     * Constructor for CSVTableReader.
     * 
     * @param in
     * @param table
     */
    public CSVTableReader(BufferedReader in, Table table) {
	this(in, "CVS", table);
	setCommentChar('#');
    }
	
    protected CSVTableReader(BufferedReader in, String name, Table table) {
	super(in, name, table);
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
     * Read the next field, storing it in buffer and returns false if it ends
     * the line.
     * 
     * @return true if it ends the line.
     * 
     * @throws IOException pass exception from BufferedReader
     */
    public boolean nextField(int col) throws IOException {
	int c = read();

	if (this.consideringQuotes && col == 0 && c == '#') {
	    do {
		skipToEol();
		c = read();
	    }
	    while (c == '#');
	}
	if (this.consideringQuotes && c == '"') {
	    return readQuoted('"');
	}
	else if (this.consideringQuotes && c == '\'') {
	    return readQuoted('\'');
	}

	this.buffer.setLength(0);

	while ((c != this.separator) && (c != '\n') && (c != -1)) {
	    this.buffer.append((char)c);
	    c = read();
	}
	int len = this.buffer.length();
	if (len > 0 && this.buffer.charAt(len-1) == '\r')
	    this.buffer.setLength(len-1);

	return (c == '\n') || (c == -1);
    }

    /**
     * Returns a default field name for a specified index.
     * 
     * @param index the index.
     * 
     * @return a default field name for a specified index.
     */
    public String defaultFieldName(int index) {
	this.buffer.setLength(0);

	for (; index >= 0; index = (index / 26) - 1) {
	    this.buffer.insert(0, (char)((char)(index % 26) + 'A'));
	}

	return getField();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param index DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public String getLabelAt(int index) {
	while (this.labels.size() <= index) {
	    this.labels.add(defaultFieldName(this.labels.size()));
	}

	return (String)this.labels.get(index);
    }
    
    public void addLabel(String name) {
        this.labels.add(name);
    }
	
    /**
     * Returns the column at the specified index in the label table.
     * 
     * @param index the index
     * 
     * @return the column at the specified index in the label table
     * 		or null if the index is invalid or no column of that name exist.
     */
    public Column getColumnAt(int index) {
	String label = getLabelAt(index);
	if (label == null)
	    return null;
	return this.table.getColumn(label);
    }

	
    protected void disableNotify() {
	for (int i = 0; i < this.labels.size(); i++) {
	    Column c = getColumnAt(i);
	    if (c != null)
		c.disableNotify();
	}
    }
	
    protected void enableNotify() {
	for (int i = 0; i < this.labels.size(); i++) {
	    Column c = getColumnAt(i);
	    if (c != null)
		c.enableNotify();
	}
    }

    /**
     * DOCUMENT ME!
     * 
     * @param column DOCUMENT ME!
     * @param field DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @throws ParseException DOCUMENT ME!
     */
    public boolean addField(int column, String field) throws ParseException {
	String label = getLabelAt(column);
	Column col = this.table.getColumn(label);

	if (col == null) {
	    Class type = guessFieldType(field);
	    col = createColumn(type, label);

	    if (col == null) {
		throw new ParseException(
					 "couldn't guess the type of field at column " + 
					 column, 0);
	    }

	    this.table.addColumn(col);
	}

	col.addValueOrNull(field);

	return true;
    }

    /**
     * @see infovis.io.AbstractTableReader#load()
     */
    public boolean load() {
	try {
	    for (int i = 0; i < this.skipLines; i++)
		skipToEol();

	    readLabels();
	    readTypes();

	    readLines();
	}
	catch (ParseException e) {
	    return false;
	}
	catch (IOException e) {
	    return false;
	}
	finally {
	    try {
		this.in.close();
	    }
	    catch(IOException e) {
	    }
	}
			
	return true;
    }

    protected void readLines() throws IOException, ParseException {
	try {
	    disableNotify();
	    while (!isEof()) {
		int column = 0;
			
		for (boolean eol = nextField(column); true; eol = nextField(column)) {
		    if (eol && isEof())
			break;
		    addField(column, getField());
		    column++;
		    if (eol)
			break;
		}
	    }
	}
	finally {
	    enableNotify();
	}
    }

    protected void readTypes() throws IOException, ParseException {
	if (this.typeLinePresent) {
	    int column = 0;
	    for (boolean eol = nextField(column); true; eol = nextField(column)) {
		String typeName = getField();
		if (typeName.length() != 0) {
		    String label = getLabelAt(column);
		    Column col = createColumn(typeNamed(typeName), label);
		    if (col == null) {
			throw new ParseException(
						 "couldn't understand the type "+typeName+" of field at column " + 
						 column, 0);
		    }
		    this.table.addColumn(col);
		    column++;
		}
		if (eol)
		    break;
	    }
	}
    }

    protected void readLabels() throws IOException {
	
	if (this.labelLinePresent) {
	    int column = 0;
	    for (boolean eol = nextField(column); true; eol = nextField(column)) {
		String label = getField();
		if (label.length() != 0) {
		    addLabel(label);
		    column++;
		}
		if (eol)
		    break;
	    }
	}
    }
	
    public static boolean load(File file, Table t) {
	try {
	    CSVTableReader loader =
		new CSVTableReader(new BufferedReader(new FileReader(file)), t);
	    return loader.load();
	}
	catch (FileNotFoundException e) {
	    return false;
	}
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
    
    /**
     * Returns the consideringQuotes.
     * @return boolean
     */
    public boolean isConsideringQuotes() {
        return this.consideringQuotes;
    }

    /**
     * Sets the consideringQuotes.
     * @param consideringQuotes The consideringQuotes to set
     */
    public void setConsideringQuotes(boolean consideringQuotes) {
        this.consideringQuotes = consideringQuotes;
    }

}
