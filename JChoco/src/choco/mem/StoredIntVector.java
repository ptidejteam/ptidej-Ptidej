// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: StoredIntVector.java,v $
// Version:            $Revision: 1.4 $
// Last Modification:  $Date: 2005/12/30 05:02:42 $
// Last Contributor:   $Author: guehene $

package choco.mem;

import java.util.logging.Logger;
import org.junit.Assert;

/**
 * Implements a backtrackable search vector.
 *
 * Cette classe permet de stocker facilment des entiers dans un tableau
 * backtrackable d'entiers.
 *
 */
public final class StoredIntVector implements IStateIntVector {
	/**
	 * Reference to an object for logging trace statements related memory & backtrack (using the java.util.logging package)
	 */

	private static Logger logger = Logger.getLogger("choco.mem");

	/**
	 * Minimal capacity of a vector
	 */
	public static final int MIN_CAPACITY = 8;

	/**
	 * Contains the elements of the vector.
	 */

	private int[] elementData;

	/**
	 * Contains time stamps for all entries (the world index of the last update for each entry)
	 */

	private int[] worldStamps;

	/**
	 * A backtrackable search with the size of the vector.
	 */

	private int size;

	/**
	 * The current environment.
	 */

	private final Environment environment;

	/**
	 * The history of all the backtrackable search vectors.
	 */

	private final StoredIntVectorTrail trail;

	/**
	 * Constructs an empty stored search vector.
	 * @param env The current environment.
	 */

	public StoredIntVector(final Environment env) {
		this(env, 0, 0);
	}

	/**
	 * Constructs a stored search vector with an initial size, and initial values.
	 * @param env The current environment.
	 * @param initialSize  The initial size.
	 * @param initialValue The initial common value.
	 */

	public StoredIntVector(
		final Environment env,
		final int initialSize,
		final int initialValue) {
		int initialCapacity = StoredIntVector.MIN_CAPACITY;
		final int w = env.currentWorld;

		if (initialCapacity < initialSize) {
			initialCapacity = initialSize;
		}

		this.environment = env;
		this.elementData = new int[initialCapacity];
		this.worldStamps = new int[initialCapacity];
		for (int i = 0; i < initialSize; i++) {
			this.elementData[i] = initialValue;
			this.worldStamps[i] = w;
		}
		this.size = initialSize;

		this.trail =
			(StoredIntVectorTrail) this.environment
				.getTrail(Environment.INT_VECTOR_TRAIL);
	}

	public StoredIntVector(final int[] entries) {
		// TODO
		throw new UnsupportedOperationException();
	}

	/**
	 * Sets an element without storing the previous value.
	 */

	int _set(final int index, final int val, final int stamp) {
		final int oldval = this.elementData[index];
		this.elementData[index] = val;
		this.worldStamps[index] = stamp;
		return oldval;
	}

	/**
	 * Adds a new search at the end of the vector.
	 * @param i The search to add.
	 */

	public void add(final int i) {
		//	System.out.print("Vector: ");
		//	System.out.print(this.hashCode());
		//	System.out.print(", add(");
		//	System.out.print(i);
		//	System.out.println(");");

		final int newsize = this.size + 1;
		this.ensureCapacity(newsize);
		this.size = newsize;
		this.elementData[newsize - 1] = i;
		this.worldStamps[newsize - 1] = this.environment.currentWorld;
	}

	/*    public Object[] toArray() {
	        // TODO : voir ci c'est utile
	        return new Object[0];
	    }*/

	/**
	 * Checks if the capacity is great enough, else the capacity
	 * is extended.
	 * @param minCapacity the necessary capacity.
	 */

	public void ensureCapacity(final int minCapacity) {
		final int oldCapacity = this.elementData.length;
		if (minCapacity > oldCapacity) {
			final int[] oldData = this.elementData;
			final int[] oldStamps = this.worldStamps;
			int newCapacity = oldCapacity * 3 / 2 + 1;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			this.elementData = new int[newCapacity];
			this.worldStamps = new int[newCapacity];
			System.arraycopy(oldData, 0, this.elementData, 0, this.size);
			System.arraycopy(oldStamps, 0, this.worldStamps, 0, this.size);
		}
	}

	/**
	 * Returns the <code>index</code>th element of the vector.
	 */

	public int get(final int index) {
		if (index < this.size && index >= 0) {
			//	System.out.print("Vector: ");
			//	System.out.print(this.hashCode());
			//	System.out.print(", get(");
			//	System.out.print(index);
			//	System.out.print(") -> ");
			//	System.out.print(this.elementData[index]);
			//	System.out.println(';');

			return this.elementData[index];
		}
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
				+ this.size);
	}

	/**
	 * Checks if the vector is empty.
	 */

	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * removes the search at the end of the vector.
	 * does nothing when called on an empty vector
	 */

	public void removeLast() {
		final int newsize = this.size - 1;
		if (newsize >= 0) {
			this.size = newsize;
		}
	}

	/**
	 * Assigns a new value <code>val</code> to the element <code>index</code>.
	 */

	public int set(final int index, final int val) {
		if (index < this.size && index >= 0) {
			Assert.assertTrue(this.worldStamps[index] <= this.environment.currentWorld);
			final int oldValue = this.elementData[index];
			if (val != oldValue) {
				final int oldStamp = this.worldStamps[index];
				StoredIntVector.logger.finest("W:"
						+ this.environment.currentWorld + "@" + index + "ts:"
						+ this.worldStamps[index]);
				if (oldStamp < this.environment.currentWorld) {
					this.trail.savePreviousState(
						this,
						index,
						oldValue,
						oldStamp);
					this.worldStamps[index] = this.environment.currentWorld;
				}
				this.elementData[index] = val;

				//	System.out.print("Vector: ");
				//	System.out.print(this.hashCode());
				//	System.out.print(", set(");
				//	System.out.print(index);
				//	System.out.print(", ");
				//	System.out.print(val);
				//	System.out.println(");");
			}
			return oldValue;
		}
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: "
				+ this.size);
	}

	/**
	 * Returns the current size of the stored search vector.
	 */

	public int size() {
		return this.size;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < this.size; i++) {
			buffer.append(this.elementData[i]);
			if (i < this.size) {
				buffer.append(", ");
			}
		}
		return buffer.toString();
	}
}
