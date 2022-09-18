package infovis.column;

import infovis.Column;
import infovis.Table;
import infovis.metadata.ValueCategory;

import java.text.ParseException;
import java.util.Map;
import java.util.TreeMap;


/**
 * Specialization of an IntColumn storing categorical values.
 *
 * @deprecated This class is redundant with an <code>IntColumn</code> with a
 * <code>CategoricalFormat</code>.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class CategoricalColumn extends IntColumn {
    protected Map categoryMap;

    /**
     * Constructor for CategoricalColumn.
     * @param name
     */
    public CategoricalColumn(String name) {
	this(name, 10, null);
    }

    /**
     * Constructor for CategoricalColumn.
     *
     * @param name the Column name.
     * @param reserve the initial reserved size.
     * @param map the initial category map.
     */
    public CategoricalColumn(String name, int reserve, Map map) {
	super(name, reserve);
	if (map == null) {
	    map = new TreeMap();
	}
	this.categoryMap = map;
	getMetadata().put(ValueCategory.VALUE_CATEGORY_TYPE, 
			  ValueCategory.VALUE_CATEGORY_TYPE_CATEGORICAL);
    }

    /**
     * @see infovis.column.ArrayColumn#clear()
     */
    public void clear() {
	super.clear();
	this.categoryMap.clear();
    }

    /**
     * Get the category associated with a name.
     * 
     * @param name the name.
     * @return the category or -1 if not defined.
     */
    public int getCategory(String name) {
	Integer i = (Integer)this.categoryMap.get(name);
	if (i == null)
	    return -1;
	return i.intValue();
    }

    /**
     * Find a category associated with a name, creating it
     * if it doesn't exist yet.
     * 
     * @param name the category name
     * @return the integer value associated with the name.
     */
    public int findCategory(String name) {
	Integer i = (Integer)this.categoryMap.get(name);
	if (i == null) {
	    i = Integer.valueOf(this.categoryMap.size());
	    this.categoryMap.put(name, i);
	}
	return i.intValue();
    }

    /**
     * Replaces the element at the specified position in this column with the
     * specified element.
     * 
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * 
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *         (index &lt; 0 || index &gt;= getRowCount()).
     */
    public final void set(int index, String element) {
	set(index, findCategory(element));
    }

    /**
     * Replaces the element at the specified position in this column with the
     * specified element, growing the column if necessary.
     * 
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * 
     * @throws ArrayIndexOutOfBoundsException if the index is out of range
     *         (index &lt; 0 ).
     */
    public final void setExtend(int index, String element) {
	setExtend(index, findCategory(element));
    }

    /**
     * Adds an element to the column.
     * 
     * @param element the element.
     */
    public final void add(String element) {
	add(findCategory(element));
    }

    /**
     * Parse a string and return the value for the column.
     *
     * @param v the string representation of the value
     *
     * @return the value
     *
     * @throws ParseException if the value cannot be parsed
     */
    public int parse(String v) throws ParseException {
	if (v == null)
	    return 0;
	if (getFormat() == null) {
	    return findCategory(v);
	}
	return super.parse(v);
    }

    /**
     * Returns the string representation of a value according to the current format.
     *
     * @param v the value
     *
     * @return the string representation.
     */
    public String format(int v) {
	if (getFormat() == null) {
	    return (String)this.categoryMap.get(Integer.valueOf(v));
	}

	return super.format(v);
    }

    /**
     * Returns a column as a <code>IntColumn</code> from an
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param index index in the <code>DefaultTable</code>
     *
     * @return a <code>IntColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>IntColumn</code>.
     */
    public static IntColumn getColumn(Table t, int index) {
	Column c = t.getColumnAt(index);

	if (c instanceof CategoricalColumn) {
	    return (CategoricalColumn)c;
	} else {
	    return null;
	}
    }

    /**
     * Returns a column as a <code>IntColumn</code> from a table,
     * creating it if needed.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a column as a <code>IntColumn</code> from a table,
     */
    public static IntColumn findColumn(Table t, String name) {
	Column c = t.getColumn(name);
	if (c == null) {
	    c = new CategoricalColumn(name);
	    t.addColumn(c);
	}
	return (CategoricalColumn)c;
    }
}
