/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.io;

import infovis.Column;
import infovis.column.FloatColumn;
import infovis.column.IntColumn;
import infovis.column.LongColumn;
import infovis.column.StringColumn;
import infovis.column.UTCDateFormat;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base class for all the readers.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractReader {
    protected BufferedReader in;
    private String name;
	
    /**
     * Constructor for AbstractReader
     *
     * @param in the <code>BufferedReader</code> for input.
     * @param name the resource name.
     */
    public AbstractReader(BufferedReader in, String name) {
	this.in = in;
	this.name = name;
    }
    /**
     * Returns the input <code>BufferedReader</code>.
     * @return the input <code>BufferedReader</code>.
     */
    public BufferedReader getIn() {
	return this.in;
    }

    /**
     * Returns the resource name.
     * @return the resource name.
     */
    public String getName() {
	return this.name;
    }

    /**
     * Sets the <code>BufferedReader</code>
     * @param in the <code>BufferedReader</code>
     */
    public void setIn(BufferedReader in) {
	this.in = in;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
	this.name = name;
    }
    /**
     * Guess the field class from a value.
     * 
     * @param field the value.
     * 
     * @return A field class.
     */
    public static Class guessFieldType(String field) {
	if ((field == null) || (field.length() == 0)) {
	    return String.class;
	}

	char c = field.charAt(0);

	if (Character.isDigit(c) || 
	    c == '-' ||
	    c == '+' ||
	    (c == '.' && Character.isDigit(field.charAt(1)))) {
            SimpleDateFormat date = new UTCDateFormat();
            try {
                date.parse(field);
                return Date.class;
            }
            catch(ParseException e)  {
            }
            
            try {
                Float.parseFloat(field);                
                return Float.class; 
            }
            catch(NumberFormatException e) {
            }
	}

	return String.class;
    }
	
    /**
     * Returns the class of the specified type name.
     * 
     * @param name the type name.
     * 
     * @return A field class.
     */
    public static Class typeNamed(String name) {
	if (name.equalsIgnoreCase("integer") || name.equalsIgnoreCase("int"))
	    return Integer.class;
	if (name.equalsIgnoreCase("long"))
	    return Long.class;
	else if (name.equalsIgnoreCase("string"))
	    return String.class;
        else if (name.equalsIgnoreCase("date"))
            return Date.class;
	else if (name.equalsIgnoreCase("real") || name.equalsIgnoreCase("float"))
	    return Float.class;
	return String.class;
    }

    /**
     * Creates of a column of the specified class.
     * 
     * @param type the class name.
     * @param label the name of the column.
     * 
     * @return a column of the specified class and name.
     */
    public static Column createColumn(Class type, String label) {
	Column col = null;

	if (type == String.class) {
	    col = new StringColumn(label);
	}
	else if (type == Integer.class) {
	    col = new IntColumn(label);
	}
	else if (type == Float.class) {
	    col = new FloatColumn(label);
	}
	else if (type == Long.class) {
	    col = new LongColumn(label);
	}
        else if (type == Date.class) {
            col = new LongColumn(label);
            col.setFormat(new UTCDateFormat());
        }

	return col;
    }

    /**
     * Main method for loading the file.
     *
     * The loading may fail at any point, leaving the table in an
     * indefinite state if the methods returns false.
     *
     * @return true if the file has been loaded without error,
     * false otherwise.
     */
    public abstract boolean load() throws WrongFormatException;

}
