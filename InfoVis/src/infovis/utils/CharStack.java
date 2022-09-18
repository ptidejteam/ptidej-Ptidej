/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.utils;

/**
 * Dynamic table of chars
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class CharStack {
    private char data[];
    private int top;

    public CharStack() {
	this(10);
    }

    public CharStack(int initialSize) {
	this.top = 0;
	this.data = new char[initialSize];

    }
	
    public void clear() {
	reset();
    }

    public final void push(char f) {
	if (this.top == this.data.length) {
	    char newdata[] = new char[this.top * 2];
	    System.arraycopy(this.data, 0, newdata, 0, this.top);
	    this.data = newdata;
	}
	this.data[this.top++] = f;
    }
	
    public final char top() {
	return this.data[this.top-1];
    }


    public final char pop() {
	return this.data[--this.top];
    }

    public final int size() {
	return this.top;
    }

    public final char get(int index) {
	return this.data[index];
    }

    final void put(int index, char val) {
	this.data[index] = val;
    }

    public final void reset() {
	this.top = 0;
    }
    public final char[] getData() {
	return this.data;
    }
    public final boolean isEmpty() {
	return this.top == 0;
    }
}

