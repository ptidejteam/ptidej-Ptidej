package choco.mem;

/**
 * Created by IntelliJ IDEA.
 * User: hcambaza
 * Date: 8 déc. 2003
 * Time: 15:04:29
 * To change this template use Options | File Templates.
 */
public class StoredBitSetOLD {
	/**
	 * returns the index of the word in the "bits" array
	 */
	private static int largeIndex(final int bitIndex) {
		return bitIndex >> 5;
	}

	/**
	 * returns the index within the right word
	 */
	private static int smallIndex(final int index) {
		return index & 31; // equivalent to (index % 32);
	}

	/**
	 * Counts the number of clear bits starting from the heaviest (leftmost) one
	 * assumes val contains some bits that are set.
	 * @param val the integer, taken as a 32-bit set
	 * @return the index of the leftmost bit that is set
	 */
	public static int startingZeroCnt(int val) {
		final int mask = 1 << 31;
		int offset = 0;
		while ((val & mask) == 0) {
			offset++;
			val <<= 1;
		}
		return offset;
	}

	/**
	 * Counts the number of clear bits starting from the lightest (rightmost) one
	 * assumes val contains some bits that are set.
	 * @param val the integer, taken as a 32-bit set
	 * @return the index of the rightmost bit that is set
	 */
	public static int trailingZeroCnt(int val) {
		int offset = 0;
		while ((val & 1) == 0) {
			offset++;
			val >>= 1;
		}
		return offset;
	}

	private final StoredIntVector representedBy;

	// note: shifting by 5 bits amounts to dividing by 32
	public StoredBitSetOLD(final Environment env, final int initialSize) {
		this.representedBy =
			new StoredIntVector(
				env,
				StoredBitSetOLD.largeIndex(initialSize) + 1,
				0);
	}

	public void clear(final int bitIndex) {
		System.out.print("BitSet: ");
		System.out.print(this.hashCode());
		System.out.print(", set(");
		System.out.print(bitIndex);
		System.out.println(");");

		final int lidx = StoredBitSetOLD.largeIndex(bitIndex);
		final int sidx = StoredBitSetOLD.smallIndex(bitIndex);
		final int mask = 1 << sidx;
		final int word = this.representedBy.get(lidx);
		if ((word & mask) != 0) {
			// the bit was already set.....
			this.representedBy.set(lidx, word & ~mask);
		}
	}

	public boolean get(final int bitIndex) {
		final int lidx = StoredBitSetOLD.largeIndex(bitIndex);
		final int sidx = StoredBitSetOLD.smallIndex(bitIndex);
		final int mask = 1 << sidx;
		final int word = this.representedBy.get(lidx);

		System.out.print("BitSet: ");
		System.out.print(this.hashCode());
		System.out.print(", get(");
		System.out.print(bitIndex);
		System.out.print(") -> ");
		System.out.print((word & mask) != 0);
		System.out.println(";");

		return (word & mask) != 0;
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
	// TODO: write the test file + prevSetBit + nextClearBit
	public int nextSetBit(final int fromIndex) {
		if (fromIndex < 0) {
			throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
		}
		else if (fromIndex > this.representedBy.size() * 32) {
			throw new IndexOutOfBoundsException("fromIndex > 32*size: "
					+ fromIndex);
		}

		int lidx = StoredBitSetOLD.largeIndex(fromIndex);
		int sidx = StoredBitSetOLD.smallIndex(fromIndex);
		// int mask = 1 << sidx;
		int word = this.representedBy.get(lidx);

		word = word & 0xffffffff >> sidx << sidx;
		// 1...(21 set bits)...1000 with sidx=3

		while (word == 0 && lidx < this.representedBy.size() - 1) { // <fla>: added -1
			word = this.representedBy.get(++lidx);
		}
		if (word == 0) { // the bitset contains no entry after fromIndex
			return -1;
		}
		else {
			sidx = StoredBitSetOLD.trailingZeroCnt(word);
		}
		return lidx * 32 + sidx;
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
		else if (fromIndex > this.representedBy.size() * 32) {
			throw new IndexOutOfBoundsException("fromIndex > 32*size: "
					+ fromIndex);
		}

		int lidx = StoredBitSetOLD.largeIndex(fromIndex);
		int sidx = StoredBitSetOLD.smallIndex(fromIndex);
		//    int mask = (((0xffffffff) << (31 - sidx)) >> (31 - sidx));  // 0001...(21 set bits)...1 with sidx=3
		//    int mask = ~(((0xffffffff) >> (sidx + 1)) << (sidx + 1));  // 0...(28 clear bits)...01111 with sidx=3
		final int mask =
			sidx == 31 ? 0xffffffff : ~(0xffffffff >> sidx + 1 << sidx + 1);
		// 0...(28 clear bits)...01111 with sidx=3

		int word = this.representedBy.get(lidx);
		word = word & mask;

		while (word == 0 && lidx > 0) {
			word = this.representedBy.get(--lidx);
		}
		if (word == 0) { // the bitset contains no entry before fromIndex
			return -1;
		}
		else {
			sidx = 31 - StoredBitSetOLD.startingZeroCnt(word);
		}
		return lidx * 32 + sidx;
	}

	public void set(final int bitIndex) {
		System.out.print("BitSet: ");
		System.out.print(this.hashCode());
		System.out.print(", set(");
		System.out.print(bitIndex);
		System.out.println(");");

		final int lidx = StoredBitSetOLD.largeIndex(bitIndex);
		final int sidx = StoredBitSetOLD.smallIndex(bitIndex);
		final int mask = 1 << sidx;
		final int word = this.representedBy.get(lidx);
		if ((word & mask) == 0) {
			// the bit was not already set.....
			this.representedBy.set(lidx, word | mask);
		}
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
