package choco.integer.var;

import choco.mem.Environment;
import choco.mem.StoredBitSet;
import choco.mem.StoredInt;
import choco.util.IntIterator;

/**
 * Created by IntelliJ IDEA.
 * User: hcambaza
 * Date: 8 déc. 2003
 * Time: 16:14:56
 * To change this template use Options | File Templates.
 */
public class BitSetIntDomain extends AbstractIntDomain {

	protected class DeltaIntDomainIterator implements IntIterator {
		protected BitSetIntDomain domain;
		protected int currentIndex = -1;

		private DeltaIntDomainIterator(final BitSetIntDomain dom) {
			this.domain = dom;
			this.currentIndex = -1;
		}

		public boolean hasNext() {
			if (this.currentIndex == -1) {
				return BitSetIntDomain.this.firstIndexBeingPropagated != -1;
			}
			else {
				return BitSetIntDomain.this.chain[this.currentIndex] != -1;
			}
		}

		public int next() {
			if (this.currentIndex == -1) {
				this.currentIndex =
					BitSetIntDomain.this.firstIndexBeingPropagated;
			}
			else {
				this.currentIndex =
					BitSetIntDomain.this.chain[this.currentIndex];
			}
			return this.currentIndex + BitSetIntDomain.this.offset;
		}

		public void remove() {
			if (this.currentIndex == -1) {
				throw new IllegalStateException();
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
	}

	/**
	 * The offset, that is the minimal value of the domain (stored at index 0).
	 * Thus the entry at index i corresponds to x=i+offset).
	 */

	protected final int offset;

	/**
	 * Number of present values.
	 */

	protected StoredInt size;

	/**
	 * A bit set indicating for each value whether it is present or not
	 */

	protected StoredBitSet contents;

	/**
	 * the initial size of the domain (never increases)
	 */
	private final int capacity;

	/**
	 * A chained list implementing two subsets of values:
	 *   - the removed values waiting to be propagated
	 *   - the removed values being propagated
	 *  (each element points to the index of the enxt element)
	 *  -1 for the last element
	 */
	protected int[] chain;

	/**
	 * start of the chain for the values waiting to be propagated
	 * -1 for empty chains
	 */
	protected int firstIndexToBePropagated;

	/**
	 * start of the chain for the values being propagated
	 * -1 for empty chains
	 */
	protected int firstIndexBeingPropagated;

	/**
	 * Constructs a new domain for the specified variable and bounds.
	 * @param v The involved variable.
	 * @param a Minimal value.
	 * @param b Maximal value.
	 */

	public BitSetIntDomain(
		final choco.integer.var.IntDomainVar v,
		final int a,
		final int b) {
		this.variable = v;
		this.problem = v.getProblem();
		final Environment env = this.problem.getEnvironment();
		this.capacity = b - a + 1; // number of entries
		this.offset = a;
		this.size = (StoredInt) env.makeInt(this.capacity);
		this.contents = env.makeBitSet(this.capacity);
		for (int i = 0; i < this.capacity; i++) { // TODO : could be improved...
			this.contents.set(i);
		}
		this.chain = new int[this.capacity];
		this.firstIndexToBePropagated = -1;
		this.firstIndexBeingPropagated = -1;
	}

	/**
	 * cleans the data structure implementing the delta domain
	 */
	public void clearDeltaDomain() {
		this.firstIndexBeingPropagated = -1;
		this.firstIndexToBePropagated = -1;
	}

	/**
	 * Checks if the value is present.
	 * @param x The value to check.
	 */

	public boolean contains(final int x) {
		final int i = x - this.offset;
		return this.contents.get(i);
	}

	/**
	 * The delta domain container is "frozen" (it can no longer accept new value removals)
	 * so that this set of values can be iterated as such
	 */
	public void freezeDeltaDomain() {
		// freeze all data associated to bounds for the the event
		super.freezeDeltaDomain();
		// if the delta domain is already being iterated, it cannot be frozen
		if (this.firstIndexBeingPropagated != -1) {
		}//throw new IllegalStateException();
		else {
			// the set of values waiting to be propagated is now "frozen" as such,
			// so that those value removals can be iterated and propagated
			this.firstIndexBeingPropagated = this.firstIndexToBePropagated;
			// the container (link list) for values waiting to be propagated is reinitialized to an empty set
			this.firstIndexToBePropagated = -1;
		}
	}

	public IntIterator getDeltaIterator() {
		return new DeltaIntDomainIterator(this);
	}

	/**
	 * Returns the minimal present value.
	 */
	public int getInf() {
		return this.contents.nextSetBit(0) + this.offset;
	}

	/**
	 * Returns the value following <code>x</code>
	 */

	public int getNextValue(final int x) {
		final int i = x - this.offset;
		final int next = this.contents.nextSetBit(i + 1) + this.offset;

		//	System.out.print("Constraint: ");
		//	System.out.print(this.hashCode());
		//	System.out.print(" x=");
		//	System.out.print(x);
		//	System.out.print(", next=");
		//	System.out.println(next);

		return next;
	}

	/**
	 * Returns the value preceding <code>x</code>
	 */

	public int getPrevValue(final int x) {
		final int i = x - this.offset;
		return this.contents.prevSetBit(i - 1) + this.offset;
	}

	/**
	 * Returns a value randomly choosed in the domain.
	 *
	 * Not implemented yet.
	 */

	public int getRandomValue() {
		throw new UnsupportedOperationException();
		// TODO
	}

	public boolean getReleasedDeltaDomain() {
		return this.firstIndexBeingPropagated == -1
				&& this.firstIndexToBePropagated == -1;
	}

	/**
	 * Returns the current size of the domain.
	 */

	public int getSize() {
		return this.size.get();
	}

	/**
	 * Returns the maximal present value.
	 */

	public int getSup() {
		return this.contents.prevSetBit(this.capacity - 1) + this.offset;
	}

	/**
	 * Checks if the value has a following value.
	 */

	public boolean hasNextValue(final int x) {
		final int i = x - this.offset;
		return this.contents.nextSetBit(i + 1) != -1;
	}

	/**
	 * Checks if the value has a preceding value.
	 */

	public boolean hasPrevValue(final int x) {
		final int i = x - this.offset;
		return this.contents.prevSetBit(i - 1) != -1;
	}

	public boolean isEnumerated() {
		return true;
	}

	public String pretty() {
		final StringBuffer buf = new StringBuffer("{");
		int count = 0;
		for (final IntIterator it = this.getIterator(); it.hasNext()
				&& count < 5;) {
			final int val = it.next();
			count++;
			if (count > 1) {
				buf.append(", ");
			}
			buf.append(val);
		}
		if (this.getSize() > 5) {
			buf.append("..., ");
			buf.append(this.getSup());
		}
		buf.append("}");
		return buf.toString();
	}

	/**
	 * after an iteration over the delta domain, the delta domain is reopened again.
	 * @return true iff the delta domain is reopened empty (no updates have been made to the domain
	 * while it was frozen, false iff the delta domain is reopened with pending value removals (updates
	 * were made to the domain, while the delta domain was frozen).
	 */
	public boolean releaseDeltaDomain() {
		// release all data associated to bounds for the the event
		super.releaseDeltaDomain();
		// special case: the set of removals was not being iterated (because the variable was instantiated, or a bound was updated)
		if (this.firstIndexBeingPropagated == -1) {
			// remove all values that are waiting to be iterated
			this.firstIndexToBePropagated = -1;
			// return true because the event has been "flushed" (nothing more is awaiting)
			return true;
		}
		else { // standard case: the set of removals was being iterated
				// empty the set of values that were being propagated
			this.firstIndexBeingPropagated = -1;
			// if more values are waiting to be propagated, return true
			return this.firstIndexToBePropagated == -1;
		}
	}

	/**
	 * Removes a value.
	 */

	public boolean remove(final int x) {
		final int i = x - this.offset;
		if (this.contents.get(i)) {
			this.removeIndex(i);
			return true;
		}
		else {
			return false;
		}
	}

	protected void removeIndex(final int i) {
		if (i == this.firstIndexToBePropagated) {
			System.out.println("BIZARRE !!!!!!!!!!!!");
		}
		this.contents.clear(i);
		this.chain[i] = this.firstIndexToBePropagated;
		this.firstIndexToBePropagated = i;
		if (this.contents.get(i)) {
			System.out.println("etrange etrange");
		}
		this.size.add(-1);
	}

	/**
	 * Removes all the value but the specified one.
	 */

	public void restrict(final int x) {
		final int xi = x - this.offset;
		for (int i = this.contents.nextSetBit(0); i >= 0; i =
			this.contents.nextSetBit(i + 1)) {
			if (i != xi) {
				this.contents.clear(i);
			}
		}
		this.size.set(1);
	}

	/**
	 * Sets a new minimal value.
	 * @param x New bound value.
	 */

	public int updateInf(final int x) {
		final int newi = x - this.offset; // index of the new lower bound
		for (int i = this.contents.nextSetBit(0); i < newi; i =
			this.contents.nextSetBit(i + 1)) {
			if (!this.contents.get(i)) {
				System.out.println("Mega bizarre");
			}
			this.removeIndex(i);
		}
		return this.contents.nextSetBit(x - this.offset) + this.offset;
	}

	/**
	 * Sets a new maximal value.
	 * @param x New bound value.
	 */

	public int updateSup(final int x) {
		final int newi = x - this.offset; // index of the new lower bound
		for (int i = this.contents.prevSetBit(this.capacity - 1); i > newi; i =
			this.contents.prevSetBit(i - 1)) {
			if (!this.contents.get(i)) {
				System.out.println("Mega bizarre");
			}
			this.removeIndex(i);
		}
		return this.contents.prevSetBit(x - this.offset) + this.offset;
	}

}
