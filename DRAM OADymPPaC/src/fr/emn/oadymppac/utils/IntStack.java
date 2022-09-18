package fr.emn.oadymppac.utils;

/**
* @author fekete
*/
public class IntStack {
	private int data[];
	private int top;

	public IntStack() {
		this(10);
	}

	public IntStack(final int initialSize) {
		this.top = 0;
		this.data = new int[initialSize];

	}

	public void clear() {
		this.reset();
	}

	public final int get(final int index) {
		return this.data[index];
	}

	public final int[] getData() {
		return this.data;
	}

	public final boolean isEmpty() {
		return this.top == 0;
	}

	public final int pop() {
		return this.data[--this.top];
	}

	public final void push(final int f) {
		if (this.top == this.data.length) {
			final int newdata[] = new int[this.top * 2];
			System.arraycopy(this.data, 0, newdata, 0, this.top);
			this.data = newdata;
		}
		this.data[this.top++] = f;
	}

	final void put(final int index, final int val) {
		this.data[index] = val;
	}
	public final void reset() {
		this.top = 0;
	}
	public final int size() {
		return this.top;
	}
}
