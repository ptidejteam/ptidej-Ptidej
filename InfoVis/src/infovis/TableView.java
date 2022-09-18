/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis;

import infovis.utils.Permutation;
import infovis.utils.RowComparator;
import infovis.utils.RowIterator;

/**
 * Class TableView
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface TableView extends Table {
    public Permutation getPermutation();
    public RowComparator getComparator();
    public boolean setComparator(RowComparator comp);
    public int getRowAtIndex(int index);
    public int getRowIndex(int row);
    public RowIterator iterator();
}
