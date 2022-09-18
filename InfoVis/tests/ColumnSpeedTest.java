/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License a
 * copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
import infovis.Column;
import infovis.column.FilterColumn;
import infovis.column.IntColumn;
import infovis.panel.DynamicQuery;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;


/**
 * DOCUMENT ME!
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ColumnSpeedTest implements DynamicQuery {
    IntColumn      intColumn;
    ArrayList      objectColumn;
    int            size;
    FilterColumn   filter;

    /**
     * Constructor for ColumnSpeedTest.
     *
     * @param size DOCUMENT ME!
     */
    public ColumnSpeedTest(int size) {
        this.size = size;
        this.intColumn = new IntColumn("intColumn");
        this.objectColumn = new ArrayList();
        this.filter = new FilterColumn("#filter");
        setFilterColumn(this.filter);
    }

    /**
     * DOCUMENT ME!
     */
    public void doTest() {
        long time = System.currentTimeMillis();
        allocateColumn();
        System.out.println("Allocation of a " + this.size + " elements column: " +
                           (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        allocateArray();
        System.out.println("Allocation of a " + this.size + " elements array: " +
                           (System.currentTimeMillis() - time));

        Filter noopFilter = new NoopFilter();
        time = System.currentTimeMillis();
        testColumn(noopFilter);
        System.out.println("Testing of the " + this.size + " elements column: " +
                           (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        testArray(noopFilter);
        System.out.println("Testing of the " + this.size + " elements array: " +
                           (System.currentTimeMillis() - time));

        permuteArray();
        time = System.currentTimeMillis();
        testArray(noopFilter);
        System.out.println("Testing of the " + this.size +
                           " permuted elements array: " +
                           (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        apply();
        System.out.println("Filtering " + this.size +
                           " elements column: " +
                           (System.currentTimeMillis() - time));
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Column getColumn() {
        return this.intColumn;
    }
    
    public FilterColumn getFilterColumn() {
        return this.filter;
    }
    
    public void setFilterColumn(FilterColumn filter) {
        if (this.filter != null) {
            this.filter.removeDynamicQuery(this);
        }
        this.filter = filter;
        if (this.filter != null) {
            this.filter.addDynamicQuery(this);      
        }
    }



    /**
     * DOCUMENT ME!
     *
     * @param row DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isFiltered(int row) {
        return this.intColumn.get(row) < this.size / 2;
    }

    /**
     * DOCUMENT ME!
     */
    public void apply() {
        if (this.filter != null)
            this.filter.applyDynamicQuery(this, this.intColumn.iterator());
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public JComponent getComponent() {
        return null;
    }

    void swapArray(int i, int j) {
        Object o = this.objectColumn.get(i);
        this.objectColumn.set(i, this.objectColumn.get(j));
        this.objectColumn.set(j, o);
    }

    void permuteArray() {
        Random random = new Random();
        for (int i = this.size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swapArray(i, j);
        }
    }

    void allocateColumn() {
        this.intColumn.clear();
        for (int i = 0; i < this.size; i++)
            this.intColumn.setExtend(i, i);
    }

    void allocateArray() {
        this.objectColumn.clear();
        for (int i = 0; i < this.size; i++)
            this.objectColumn.add(Integer.valueOf(i));
    }

    /**
     * DOCUMENT ME!
     *
     * @param filter DOCUMENT ME!
     */
    public void testColumn(Filter filter) {
        for (int i = 0; i < this.size; i++)
            filter.filter(this.intColumn.get(i));
    }

    /**
     * DOCUMENT ME!
     *
     * @param filter DOCUMENT ME!
     */
    public void testArray(Filter filter) {
        for (int i = 0; i < this.size; i++)
            filter.filter(((Integer)this.objectColumn.get(i)).intValue());
    }

    interface Filter {
        boolean filter(int value);
    }

    static class NoopFilter implements Filter {
        public boolean filter(int value) {
            return true;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param args DOCUMENT ME!
     */
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        ColumnSpeedTest test = new ColumnSpeedTest(size);
        
        for (int i = 0; i < 10; i++) {
            System.gc();
            test.doTest();
        }
    }
}
