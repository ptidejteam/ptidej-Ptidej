package fr.emn.oadymppac.utils;

/**
* @author fekete
*/
public class FloatStack {
	private float data[];
	private int top;

	public FloatStack() {
		this(10);
	}

	public FloatStack(final int initialSize) {
		this.top = 0;
		this.data = new float[initialSize];

	}

	public void clear() {
		this.reset();
	}

	public final float get(final int index) {
		return this.data[index];
	}

	public final float[] getData() {
		return this.data;
	}

	public final boolean isEmpty() {
		return this.top == 0;
	}

	public final float pop() {
		return this.data[--this.top];
	}

	public final void push(final float f) {
		if (this.top == this.data.length) {
			final float newdata[] = new float[this.top * 2];
			System.arraycopy(this.data, 0, newdata, 0, this.top);
			this.data = newdata;
		}
		this.data[this.top++] = f;
	}

	final void put(final int index, final float val) {
		this.data[index] = val;
	}

	public final void reset() {
		this.top = 0;
	}

	public final int size() {
		return this.top;
	}
	public void trim() {
		if (this.top == this.data.length) {
			return;
		}
		else {
			final float newdata[] = new float[this.top];
			System.arraycopy(this.data, 0, newdata, 0, this.top);
			this.data = newdata;
		}
	}
}
