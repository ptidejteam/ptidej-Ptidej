/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.metadata;

import infovis.Column;
import infovis.column.BooleanColumn;
import infovis.column.CategoricalFormat;
import infovis.column.NumberColumn;


/**
 * Qualify what the column values expresses.
 *
 * <p>A Column can contain values of five categories: <ol>
 * <li>Ordered: there is a natural ordering among the value, like
 * integers <li>Nominal: all the values are labels.  <li>Categorical:
 * values are category names.  <li>Differential: there is a nominal
 * value and the values are compared to it, like percentage of votes
 * around 50%.<li>Explicit: values are color encoded as ALPHA,RED,GREEN,BLUE  </ol> </p>
 *
 * <p>The representation of value should take the category into
 * account.  For example, coloring should be different: ordered values
 * are usually depicted with intensity, nominal and categorical value
 * with different hues, differential may use black for the the nominal
 * value and to two other colors for above and below.  </p>
 *
 * <p>Controls for dynamic queries should also take the category into
 * account.  Range Sliders can be used with ordered value,
 * differential and nominal values if that makes sense (an order can
 * usually be derived from a nominal value, like lexicographical
 * order, but this is somewhat artificial).  For categorical value,
 * radio shapes or other buttons can be used.</p>
 *
 * <p>When nothing is known about a column, we assign it a category
 * depending on its type.</p>
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class ValueCategory implements Constants {
    /**
     * Key for the metadata
     */
    public static final String VALUE_CATEGORY_TYPE = "VALUE_TYPE";

    /** Values are ordered */
    public static final String VALUE_CATEGORY_TYPE_ORDERED = "ordered";

    /** Value are nominal */
    public static final String VALUE_CATEGORY_TYPE_NOMINAL = "nominal";

    /** Values are categorical */
    public static final String VALUE_CATEGORY_TYPE_CATEGORICAL = "categorical";

    /** Values are differential (for example, around 0) */
    public static final String VALUE_CATEGORY_TYPE_DIFFERENTIAL = "differential";
    
    /** Values are explicit color values */
    public static final String VALUE_CATEGORY_TYPE_EXPLICIT = "explicit";
	
    /** integer constant for unkown type */
    public static final int	   TYPE_UNKOWN = -1;
	
    /** integer constant for ordered type */
    public static final int	   TYPE_ORDERED = 0;
	
    /** integer constant for nominal type */
    public static final int	   TYPE_NOMINAL = 1;
	
    /** integer constant for categorical type */
    public static final int	   TYPE_CATEGORIAL = 2;
	
    /** integer constant for differential type */
    public static final int	   TYPE_DIFFERENTIAL = 3;
    
    /** integer constant for explicit type */
    public static final int      TYPE_EXPLICIT = 4;
	
    /** String names of integer categories. */
    public static final String[] CATEGORY_NAME = {
	VALUE_CATEGORY_TYPE_ORDERED,
	VALUE_CATEGORY_TYPE_NOMINAL,
	VALUE_CATEGORY_TYPE_CATEGORICAL,
	VALUE_CATEGORY_TYPE_DIFFERENTIAL,
        VALUE_CATEGORY_TYPE_EXPLICIT
    };

    /**
     * When the VALUE_TYPE is differential, the reference value can be
     * stored as the value of this metadata property.
     */
    public static final String VALUE_CATEGORY_TYPE_DIFFERENTIAL_REFERENCE = "VALUE_TYPE_DIFFERENTIAL_REFERENCE";

    /**
     * Returns the category string associated with the specified column
     * or null.
     *
     * @param c the column,
     *
     * @return the category string associated with the specified
     * column or null if no category is associated with it.
     */
    public static String getValueCategoryString(Column c) {
	return (String)c.getMetadata().get(VALUE_CATEGORY_TYPE);
    }

    /**
     * Returns the category constant associated with the specified
     * column.
     *
     * @param c the column.
     *
     * @return the category constant associated with the specified
     * column or TYPE_UNKNOWN if no category is associated with it.
     */
    public static int getValueCategory(Column c) {
	String catString = getValueCategoryString(c);
	return categoryValue(catString);
    }

    public static void setValueCategory(Column c, int cat) {
        c.getMetadata().put(VALUE_CATEGORY_TYPE, CATEGORY_NAME[cat]);
    }
    /**
     * Returns a category constant associated with the specified
     * column, guessing one is none is associated with the column.
     *
     * @param c the column
     *
     * @return a category constant associated with the specified
     * column, guessing one is none is associated with the column.
     */
    public static int findValueCategory(Column c) {
	int cat = getValueCategory(c);
	if (cat == TYPE_UNKOWN) {
	    if (c.getFormat() instanceof CategoricalFormat)
		cat = TYPE_CATEGORIAL;
	    if (c instanceof NumberColumn)
		cat = TYPE_ORDERED;
	    else if (c instanceof BooleanColumn)
		cat = TYPE_CATEGORIAL;
	    else {
		cat = TYPE_NOMINAL;
	    }
	    c.getMetadata().put(VALUE_CATEGORY_TYPE, CATEGORY_NAME[cat]);
	}
	return cat;
    }

    public static String categoryName(int cat) {
	return CATEGORY_NAME[cat];
    }
	
    public static int categoryValue(String catString) {
	if (catString == null)
	    return TYPE_UNKOWN;
	else if (catString.equals(VALUE_CATEGORY_TYPE_ORDERED))
	    return TYPE_ORDERED;
	else if (catString.equals(VALUE_CATEGORY_TYPE_NOMINAL))
	    return TYPE_NOMINAL;
	else if (catString.equals(VALUE_CATEGORY_TYPE_CATEGORICAL))
	    return TYPE_CATEGORIAL;
	else if (catString.equals(VALUE_CATEGORY_TYPE_DIFFERENTIAL))
	    return TYPE_DIFFERENTIAL;
        else if (catString.equals(VALUE_CATEGORY_TYPE_EXPLICIT))
            return TYPE_EXPLICIT;
	return TYPE_UNKOWN;
    }
}
