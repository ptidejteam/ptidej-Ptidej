/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.utils;


/**
 * Dynamic table of ints
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class IntVector {
    private int[] data;
    private int   top;

    /**
     * Creates a new IntVector object.
     */
    public IntVector() {
        this(10);
    }

    /**
     * Creates a new IntVector object.
     *
     * @param initialSize DOCUMENT ME!
     */
    public IntVector(int initialSize) {
        this.top = 0;
        this.data = new int[initialSize];
    }

    /**
     * Clears the vector.
     */
    public void clear() {
        reset();
    }

    /**
     * 
     * Returns <tt>true</tt> if this vector contains the specified element.
     
     *
     * @param o element whose presence in this vector is to be tested.
     * @return <tt>true</tt> if this vector contains the specified element.
     */
    public boolean contains(int o) {
    	for (int i = 0; i < this.top; i++) {
    	    if (this.data[i] == o)
    	    	return true;
    	}
    	return false;
    }

    public void reserve(int size) {
	if (size >= this.data.length) {
	    int newtop = Math.max(this.top*2, size);
            int[] newdata = new int[newtop];
            System.arraycopy(this.data, 0, newdata, 0, this.top);
            this.data = newdata;
        }
    }
    /**
     * Adds a specified element to the vector.
     *
     * @param f the element.
     */
    public final void push(int f) {
        reserve(this.top+1);
        this.data[this.top++] = f;
    }

    /**
     * Adds a specified element to the vector.
     *
     * @param f the element.
     */
    public final void add(int f) {
        push(f);
    }

    /**
     * Returns the last element.
     *
     * @return int the last element.
     */
    public final int top() {
        return this.data[this.top - 1];
    }
    
    public final void setTop(int value) {
        this.data[this.top-1] = value;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public final int pop() {
        return this.data[--this.top];
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public final int size() {
        return this.top;
    }
    
    public final void resize(int size) {
    	reserve(size);
    	this.top = size;
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public final int get(int index) {
        return this.data[index];
    }

    public final void put(int index, int val) {
        this.data[index] = val;
    }
    
    public final void set(int index, int val) {
        this.data[index] = val;
    }

    /**
     * DOCUMENT ME!
     */
    public final void reset() {
        this.top = 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public final int[] getData() {
        return this.data;
    }
    
    public final int[] toArray() {
    	return toArray(null);
    }
    
    public final int[] toArray(int[] a) {
        if (a == null)
            a = new int[this.top];
    	System.arraycopy(this.data, 0, a, 0, this.top);
    	return a;
    }

    public int indexOf(int o) {
    	for (int i = 0; i < this.top; i++) {
    	    if (this.data[i] == 0)
    	        return i;
    	}
    	return -1;
    }

    public final boolean removeAt(int o) {
    	int i = indexOf(0);
    	if (i == -1)
    	    return false;
    	System.arraycopy(this.data, i, this.data, i-1, this.top-i-1);
    	this.top--;
    	return true;
    }
    
    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public final boolean isEmpty() {
        return this.top == 0;
    }
}
