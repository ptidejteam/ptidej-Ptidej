/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization.color;

import infovis.Column;
import infovis.column.IntColumn;
import infovis.column.NumberColumn;
import infovis.metadata.ValueCategory;
import infovis.visualization.ColorVisualization;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Factory for Color Visualizations.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class ColorVisualizationFactory extends ValueCategory {
    static LinkedList creators = new LinkedList();
    static public final String COLOR_VISUALIZATION_DEFAULT = "default";
    static public final String COLOR_VISUALIZATION_ORDERED =
	VALUE_CATEGORY_TYPE_ORDERED;
    static public final String COLOR_VISUALIZATION_NOMINAL =
	VALUE_CATEGORY_TYPE_NOMINAL;
    static public final String COLOR_VISUALIZATION_CATEGORICAL =
	VALUE_CATEGORY_TYPE_CATEGORICAL;
    static public final String COLOR_VISUALIZATION_DIFFERENTIAL =
	VALUE_CATEGORY_TYPE_DIFFERENTIAL;
    static public final String COLOR_VISUALIZATION_EXPLICIT = "explicit";

    static {
	add(new DefaultCreator());
    }
    /**
     * Creates a dynamic query from a column.
     *
     * @param c The column
     * @param type the default type of ColorVisualization.
     *
     * @return A Dynamic query or null.
     */
    public static ColorVisualization createColorVisualization(Column c, String type) {
	ColorVisualization ret = null;
	for (Iterator iter = creators.iterator(); iter.hasNext(); ) {
	    Creator creator = (Creator)iter.next();
	    ret = creator.create(c, type);
	    if (ret != null)
		break;
	}
	return ret;
    }
	
    /**
     * Creates a dynamic query of default type from a column.
     *
     * @param c The column
     *
     * @return A Dynamic query or null.
     */
    public static ColorVisualization createColorVisualization(Column c) {
	return createColorVisualization(c, COLOR_VISUALIZATION_DEFAULT);
    }

    /**
     * Adds a default creator for a specific kind of column.
     *
     * @param c The creator
     */
    public static void add(Creator c) {
	creators.addFirst(c);
    }

    /**
     * Constructor for ColorVisualizationFactory.
     */
    public ColorVisualizationFactory() {
	super();
    }

    public interface Creator {
	ColorVisualization create(Column col, String type);
    }
	
    public static class DefaultCreator implements Creator {
	public ColorVisualization create(Column col, String type) {
	    int cat;
	    if (type == null || type.equals(COLOR_VISUALIZATION_DEFAULT)) {
		cat = findValueCategory(col);
	    }
	    else {
		cat = categoryValue(type);
	    }
			
	    switch(cat) {
	    case TYPE_ORDERED: {
		if (col instanceof NumberColumn) {
		    NumberColumn column = (NumberColumn) col;
		    return new OrderedColor(column);
		}
		break;
	    }
	    case TYPE_NOMINAL:
                return new NominalColor(col);
	    case TYPE_CATEGORIAL:
		if (col instanceof IntColumn) {
		    IntColumn column = (IntColumn) col;
		    return new CategoricalColor(column);
		}
		break;
	    case TYPE_DIFFERENTIAL:
				
		break;
            case TYPE_EXPLICIT:
                if (col instanceof IntColumn) {
                    return new ExplicitColor((IntColumn)col);
                }
	    }
	    return null;
	}

    }
}
