/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.metadata;

import infovis.Table;
import infovis.column.CategoricalFormat;
import infovis.column.ColumnComparator;
import infovis.column.IntColumn;
import infovis.column.StringColumn;
import infovis.utils.Sort;

import java.text.ParseException;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Management of Metadata for <code>StringColumn</code>s.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class StringColumnMetadata implements Constants {
    /** Where sorted values are stored */
    public static final  String SORTED_VALUES = "SORTED_VALUE";
	
    public static int[] findSortedStringIndices(StringColumn column) {
	StringColumnSorter sorter =  (StringColumnSorter)column.getMetadata().get(SORTED_VALUES);
	if (sorter == null) {
	    sorter = new StringColumnSorter(column);
	    column.getMetadata().put(SORTED_VALUES, sorter);	
	}
	return sorter.getIndices();
    }
	
    /**
     * When sorted values of strings are used, keep the sorted list in
     * synch with the contents of the StringColumn, recumputing it
     * each time the column is changed.  Since this is expensive,
     * don't register this class until the column is almost finalized.
     */
    public static class StringColumnSorter extends ColumnComparator 
	implements ChangeListener {
	int[] indices;
		
	/**
	 * Construct a column sorted from StringColumn
	 */
	public StringColumnSorter(StringColumn c) {
	    super(c);
	    c.addChangeListener(this);
	    update();
	}
		
	public void update() {
	    this.indices = new int[this.column.getRowCount()];
	    for (int i = 0; i < this.indices.length; i++) {
		this.indices[i] = i;
	    }
	    Sort.sort(this.indices, this);
	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
	    update();
	}
	/**
	 * Returns the indices.
	 * @return int[]
	 */
	public int[] getIndices() {
	    return this.indices;
	}

    }

    /**
     * Returns a categorical column computed from the string column.
     * 
     * Maybe should it be synchronized with the StringColumn???
     */
    public static IntColumn findCategories(Table table, StringColumn column) {
	String catName = "#categoriesFor_"+column.getName();
        //TODO Create a LinkedColumn to manage the categories
	IntColumn categories = IntColumn.getColumn(table, catName);
		
	if (categories == null) {
	    categories = new IntColumn(catName, column.getRowCount());
	    categories.setFormat(new CategoricalFormat());
	    for (int i = 0; i < column.getRowCount(); i++) {
		try {
		    categories.addValue(column.get(i));
		}
		catch(ParseException e) {
		}
	    }
	}
	return categories;
    }
	
}
