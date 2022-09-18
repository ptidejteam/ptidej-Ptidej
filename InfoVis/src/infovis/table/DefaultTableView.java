/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.table;

import infovis.Table;
import infovis.TableView;
import infovis.utils.Permutation;
import infovis.utils.RowComparator;

/**
 * Class DefaultTableView
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultTableView extends TableProxy implements TableView {
    protected Permutation permutation;
    protected RowComparator comparator;
    
    public DefaultTableView(Table table) {
        super(table);
    }

    public Permutation getPermutation() {
        // TODO Auto-generated method stub
        return null;
    }

    public RowComparator getComparator() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean setComparator(RowComparator comp) {
        // TODO Auto-generated method stub
        return false;
    }

    public int getRowAtIndex(int index) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getRowIndex(int row) {
        // TODO Auto-generated method stub
        return 0;
    }

}
