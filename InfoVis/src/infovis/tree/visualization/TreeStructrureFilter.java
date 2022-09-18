/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization;

import infovis.Column;
import infovis.Tree;
import infovis.column.ColumnFilter;


/**
 * Filter out non tree-structural columns.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreeStructrureFilter implements ColumnFilter {
    static TreeStructrureFilter sharedInstance = new TreeStructrureFilter();

    /**
     * @see infovis.column.ColumnFilter#filter(Column)
     */
    public boolean filter(Column column) {
        String name = column.getName();
        return !(name.equals(Tree.DEGREE_COLUMN) ||
               name.equals(Tree.DEPTH_COLUMN) || !column.isInternal());
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static TreeStructrureFilter sharedInstance() {
        return sharedInstance;
    }
}
