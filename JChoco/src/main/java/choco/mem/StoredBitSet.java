package choco.mem;

import java.util.BitSet;

/**
 * 
 * @author Yann
 * @since 2010/07/20
 *
 */
public class StoredBitSet {
	//	/**
	//	 * Counts the number of clear bits starting from the heaviest (leftmost) one
	//	 * assumes val contains some bits that are set.
	//	 * @param val the integer, taken as a 32-bit set
	//	 * @return the index of the leftmost bit that is set
	//	 */
	//	public static int startingZeroCnt(int val) {
	//		throw new RuntimeException("startingZeroCnt(" + val + ')');
	//	}

	//	/**
	//	 * Counts the number of clear bits starting from the lightest (rightmost) one
	//	 * assumes val contains some bits that are set.
	//	 * @param val the integer, taken as a 32-bit set
	//	 * @return the index of the rightmost bit that is set
	//	 */
	//	public static int trailingZeroCnt(int val) {
	//		throw new RuntimeException("trailingZeroCnt(" + val + ')');
	//	}

	private final int initialSize;
	private final BitSet bitSet;
	private final BitSet reversedBitSet;

	public StoredBitSet(final Environment env, final int initialSize) {
		this.initialSize = initialSize;
		this.bitSet = new BitSet(this.initialSize);
		this.reversedBitSet = new BitSet(this.initialSize);
	}

	public void clear(final int bitIndex) {
		//	System.out.print("BitSet: ");
		//	System.out.print(this.hashCode());
		//	System.out.print(", set(");
		//	System.out.print(bitIndex);
		//	System.out.println(");");

		this.bitSet.clear(bitIndex);
		this.reversedBitSet.clear(this.initialSize - 1 - bitIndex);
	}
	public boolean get(final int bitIndex) {
		//	System.out.print("BitSet: ");
		//	System.out.print(this.hashCode());
		//	System.out.print(", get(");
		//	System.out.print(bitIndex);
		//	System.out.print(") -> ");
		//	System.out.print(this.bitSet.get(bitIndex));
		//	System.out.println(";");

		return this.bitSet.get(bitIndex);
	}

	/**
	 * Returns the index of the first bit that is set to <code>true</code>
	 * that occurs on or after the specified starting index. If no such
	 * bit exists then -1 is returned.
	 *
	 * To iterate over the <code>true</code> bits in a <code>BitSet</code>,
	 * use the following loop:
	 *
	 * for(int i=bs.nextSetBit(0); i>=0; i=bs.nextSetBit(i+1)) {
	 *     // operate on index i here
	 * }
	 *
	 * @param   fromIndex the index to start checking from (inclusive).
	 * @return  the index of the next set bit.
	 * @throws  IndexOutOfBoundsException if the specified index is negative.
	 * @since   1.4
	 */
	public int nextSetBit(final int fromIndex) {
		if (fromIndex < 0) {
			throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
		}
		else if (fromIndex > this.initialSize * 32) {
			throw new IndexOutOfBoundsException("fromIndex > 32*size: "
					+ fromIndex);
		}

		return this.bitSet.nextSetBit(fromIndex);
	}

	/**
	 * Returns the index of the first bit that is set to <code>true</code>
	 * that occurs on or before the specified starting index. If no such
	 * bit exists then -1 is returned.
	 *
	 * @param   fromIndex the index to start checking from (inclusive).
	 * @return  the index of the previous set bit.
	 * @throws  IndexOutOfBoundsException if the specified index is negative or too large
	 */
	public int prevSetBit(final int fromIndex) {
		if (fromIndex < 0) {
			throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
		}
		else if (fromIndex > this.initialSize * 32) {
			throw new IndexOutOfBoundsException("fromIndex > 32*size: "
					+ fromIndex);
		}

		final int index =
			this.initialSize
					- 1
					- this.reversedBitSet.nextSetBit(this.initialSize - 1
							- fromIndex);

		return index;
	}

	public void set(final int bitIndex) {
		//	System.out.print("BitSet: ");
		//	System.out.print(this.hashCode());
		//	System.out.print(", set(");
		//	System.out.print(bitIndex);
		//	System.out.println(");");

		this.bitSet.flip(bitIndex);
		this.reversedBitSet.flip(this.initialSize - 1 - bitIndex);
	}

	public void set(final int index, final boolean value) {
		if (value) {
			this.set(index);
		}
		else {
			this.clear(index);
		}
	}
}
