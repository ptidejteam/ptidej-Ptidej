/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.utils;

/**
 * Iterator over permuted rows.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class PermutedIterator implements RowIterator {
    Permutation permutation;
    int index;
    
    public PermutedIterator(int index, Permutation permutation) {
        this.permutation = permutation;
        this.index = index;
    }

    public PermutedIterator(Permutation permutation) {
        this(0, permutation);
    }
    
    public boolean hasNext() {
        return this.index < this.permutation.size();
    }

    public Object next() {
        return Integer.valueOf(nextRow());
    }

    public void remove() {
    }

    public int nextRow() {
        return this.permutation.getPermutation(this.index++);
    }

    public int peekRow() {
        return this.permutation.getPermutation(this.index);
    }
    
    /**
     * @see infovis.utils.RowIterator#copy()
     */
    public RowIterator copy() {
        return new PermutedIterator(this.index, this.permutation);
    }


}