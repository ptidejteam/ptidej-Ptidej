package infovis.column;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Map;


/**
 * Format for storing categorical string data in an IntColumn.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class CategoricalFormat extends Format {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Map categories;
    Map inverse;

    /**
     * Constructor for CategoricalFormat.
     */
    public CategoricalFormat() {
	this.categories = new HashMap();
	this.inverse = new HashMap();
    }

    /**
     * Associate a category name with a value.
     *
     * @param name the category name
     * @param value the integer value.
     */
    public void putCategory(String name, int value) {
	Integer i = Integer.valueOf(value);
	this.categories.put(name, i);
	this.inverse.put(i, name);
    }

    /**
     * Get the category associated with a name.
     *
     * @param name the name.
     * @return the category or -1 if not defined.
     */
    public int getCategory(String name) {
	Integer i = (Integer)this.categories.get(name);
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
	Integer i = (Integer)this.categories.get(name);
	if (i == null) {
	    int v = this.categories.size();

	    putCategory(name, v);
	    return v;
	}
	return i.intValue();
    }

    /**
     * Returns the category name given its index.
     *
     * @param index the category index
     * @return the category name.
     */
    public String indexCategory(int index) {
	Integer i = Integer.valueOf(index);
	return (String)this.inverse.get(i);
    }

    /**
     * Creates a new index associated with the given name.
     */
    public int addCategory(String name) {
	int v = this.categories.size();
	putCategory(name, v);
	return v;
    }

    /**
     * Clears the association maps.
     */
    public void clear() {
	this.categories.clear();
	this.inverse.clear();
    }

    /**
     * @see java.text.Format#format(Object, StringBuffer, FieldPosition)
     */
    public StringBuffer format(Object obj, StringBuffer toAppendTo,
			       FieldPosition pos) {
	if (!(obj instanceof Integer))
	    return null;
	pos.setBeginIndex(toAppendTo.length());
	toAppendTo.append(this.inverse.get((Integer)obj));
	pos.setEndIndex(toAppendTo.length());
	return toAppendTo;
    }

    /**
     * Select valid characters for categories.
     *
     * @param c a character
     *
     * @return <code>true</code> if the character is a separator.
     */
    public boolean isSeparator(char c) {
	return !Character.isJavaIdentifierPart(c);
    }

    /**
     * @see java.text.Format#parseObject(String, ParsePosition)
     */
    public Object parseObject(String source, ParsePosition pos) {
	int index = pos.getIndex();
	int last;

	for (last = pos.getIndex(); last != source.length(); last++) {
	    if (isSeparator(source.charAt(last))) {
		break;
	    }
	}
	pos.setIndex(last);
	String  catName = source.substring(index, last);
	Integer cat = (Integer)this.categories.get(catName);
	if (cat == null) {
	    addCategory(catName);
	    cat = (Integer)this.categories.get(catName);
	}
	return cat;
    }

    /**
     * Returns the categories map.
     * @return the categories map.
     */
    public Map getCategories() {
	return this.categories;
    }
}
