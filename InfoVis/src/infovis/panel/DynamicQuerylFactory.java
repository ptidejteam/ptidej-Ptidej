/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import infovis.Column;
import infovis.column.NumberColumn;
import infovis.column.StringColumn;

import java.util.ArrayList;

/**
 * Creates a Dynamic Query from a Column.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DynamicQuerylFactory {
    static ArrayList creators = new ArrayList();
    static public final String QUERY_TYPE_DEFAULT = "default";
    static public final String QUERY_TYPE_SLIDER = "slider";
    static public final String QUERY_TYPE_TOGGLE = "toggle";
    static public final String QUERY_TYPE_RADIO = "radio";
    static public final String QUERY_TYPE_SEARCH = "search";

    /**
     * Constructor for DynamicQuerylFactory.
     */
    public DynamicQuerylFactory() {
    }

    static {
        add(new Creator() {
            public DynamicQuery create(Column c, String type) {
                if (type == QUERY_TYPE_SEARCH || c instanceof StringColumn) {
                    return new StringSearchDynamicQuery(c);
                }
                return null;
            }
        });
        add(new Creator() {
            public DynamicQuery create(Column c, String type) {
                if (c instanceof NumberColumn) {
                    NumberColumn number = (NumberColumn) c;
                    return new NumberColumnBoundedRangeModel(number);
                }
                return null;
            }
        });
    }

    /**
     * Creates a dynamic query from a column.
     *
     * @param c The column
     * @param type the default type of DynamicQuery.
     *
     * @return A Dynamic query or null.
     */
    public static DynamicQuery createDynamicQuery(
        Column c,
        String type) {
        DynamicQuery ret = null;
        for (int i = 0; i < creators.size(); i++) {
            Creator creator = (Creator) creators.get(i);
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
    public static DynamicQuery createDynamicQuery(Column c) {
        return createDynamicQuery(c, QUERY_TYPE_DEFAULT);
    }

    /**
     * Adds a default creator for a specific kind of column.
     *
     * @param c The creator
     */
    public static void add(Creator c) {
        creators.add(c);
    }

    /**
     * Creator interface for building a Dynamic Query from a column type.
     */
    public interface Creator {
        /**
         * Creates a Dynamic Query from a column.
         */
        public DynamicQuery create(Column c, String type);
    }
}
