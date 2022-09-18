/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.utils;

/**
 * Dynamic table of floats.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class FloatStack {
    private float data[];
    private int top;

    public FloatStack() {
	this(10);
    }

    public FloatStack(int initialSize) {
	this.top = 0;
	this.data = new float[initialSize];

    }
	
    public void clear() {
	reset();
    }

    public final void push(float f) {
	if (this.top == this.data.length) {
	    float newdata[] = new float[this.top * 2];
	    System.arraycopy(this.data, 0, newdata, 0, this.top);
	    this.data = newdata;
	}
	this.data[this.top++] = f;
    }
	
    public final float top() {
	return this.data[this.top-1];
    }

    public final float pop() {
	return this.data[--this.top];
    }

    public final int size() {
	return this.top;
    }

    public final float get(int index) {
	return this.data[index];
    }

    final void put(int index, float val) {
	this.data[index] = val;
    }

    public final void reset() {
	this.top = 0;
    }
    public final float[] getData() {
	return this.data;
    }
    public final boolean isEmpty() {
	return this.top == 0;
    }
}
