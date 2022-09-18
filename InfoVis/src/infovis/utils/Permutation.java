/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.utils;

import infovis.column.IntColumn;


/**
 * Maintain a permutation of indices.
 * 
 * <p>Maintain two tables called <code>direct</code> and <code>inverse</code>.
 * <p>The <code>direct</code> table usually is the result of a indirect sort
 * over a column.  It contains the index of the rows sorted in a specific
 * order.  For example, if a table containing {6, 7, 5} is sorted in
 * decreasing order, the direct table will contain {2, 0, 1}.
 * <p>The <code>inverse</code> table contains the index
 * of a given row.  With the previous example, it contains 
 * {1, 2, 0} meaning that the index or rows {0,1,2} are {1,2,0}.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class Permutation implements RowComparator {
    IntColumn direct;
    IntColumn inverse;

    /**
     * Constructor for Permutation.
     * 
     *
     */
    public Permutation(IntColumn direct, IntColumn inverse, int initialSize) {
        this.direct = direct;
        this.inverse = inverse;
        clear();
    }
    
    /**
     * Default constructor.
     */
    public Permutation() {
        this.direct = new IntColumn("#direct");
        this.inverse = new IntColumn("#inverse");
    }
    
    public void reserve(int size) {
        this.direct.ensureCapacity(size);
        this.inverse.ensureCapacity(size);
    }

    /**
     * DOCUMENT ME!
     */
    public void clear() {
        this.direct.clear();
        this.inverse.clear();
    }
    
    void fillPermutation(int size, RowComparator comp) {
        this.direct.clear();
        for (int i = 0; i < size; i++) {
            if (! comp.isValueUndefined(i)) {
                this.direct.add(i);
            }
            else {
                this.inverse.setExtend(i, -1);
            }
        }
    }
    
    void fillPermutation(int size, RowComparator comp, RowFilter filter) {
       this.direct.clear();
       this.inverse.clear();
       for (int i = 0; i < size; i++) {
           if ((comp != null && comp.isValueUndefined(i)) || filter.isFiltered(i)) {
               this.inverse.setExtend(i, -1);
           }
           else {
               this.direct.add(i);
           }
       }
   }

    /**
     * Returns the row column at a specified index.
     *
     * @param i the index
     *
     * @return the row column at a specified index.
     */
    public int getPermutation(int i) {
        return this.direct.get(i);
    }

    /**
     * Return the index of a specified row.
     *
     * @param i the row
     *
     * @return the index of a specified row.
     */
    public int getInverse(int i) {
        if (this.inverse.isValueUndefined(i))
            return -1;
        return this.inverse.get(i);
    }

    /**
     * Swaps two indices in the permutation table.
     *
     * @param i1 first index
     * @param i2 second index
     */
    public void swap(int i1, int i2) {
        int tmp = this.direct.get(i1);
        this.direct.set(i1, this.direct.get(i2));
        this.direct.set(i2, tmp);
    }

    void recomputeInverse() {
        this.inverse.clear();
        for (int i = 0; i < this.direct.getRowCount(); i++) {
            int index = this.direct.get(i);
            this.inverse.setExtend(index, i);
        }
    }

    public void sort(int size, RowComparator comp) {
        try {
            this.direct.disableNotify();
            this.inverse.disableNotify();
            fillPermutation(size, comp);
            this.direct.sort(comp);
            recomputeInverse();
        }
        finally {
            this.direct.enableNotify();
            this.inverse.enableNotify();
        }
    }
    
    public void sort(int size, RowComparator comp, RowFilter filter) {
        try {
            this.direct.disableNotify();
            this.inverse.disableNotify();
            fillPermutation(size, comp, filter);
            this.direct.sort(comp);
            recomputeInverse();
        }
        finally {
            this.direct.enableNotify();
            this.inverse.enableNotify();
        }
    }
//    
//    public void setPermutation(IntColumn perm) {
//        if (perm == direct)
//            return;
//        try {
//            direct.disableNotify();
//            inverse.disableNotify();
//            clear();
//            int size = perm.getRowCount();
//            for (int i = 0; i < size; i++) {
//                if (! perm.isValueUndefined(i))
//                    direct.add(perm.get(i));
//            }
//            recomputeInverse();
//        }
//        finally {
//            direct.enableNotify();
//            inverse.enableNotify();
//        }
//    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int size() {
        return this.direct.getRowCount();
    }

    /**
     * Returns the direct.
     * @return IntColumn
     */
    public IntColumn getDirect() {
        return this.direct;
    }

    /**
     * Returns the inverse.
     * @return IntColumn
     */
    public IntColumn getInverse() {
        return this.inverse;
    }

    /**
     * Sets the direct.
     * @param direct The direct to set
     */
    public void setDirect(IntColumn direct) {
        this.direct = direct;
        clear();
    }


    /**
     * Sets the inverse.
     * 
     * @param inverse The inverse to set
     */
    public void setInverse(IntColumn inverse) {
        this.inverse = inverse;
        recomputeInverse();
    }
    
    //  RowComparator Interface
     public int compare(int row1, int row2) {
         return getInverse(row1) - getInverse(row2);
     }
    
     public boolean isValueUndefined(int row) {
         return row < 0 || row >= this.inverse.getRowCount() || getInverse(row) == -1;
     }
    
}
