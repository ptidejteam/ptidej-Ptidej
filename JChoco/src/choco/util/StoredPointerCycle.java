package choco.util;

import java.util.logging.Logger;
import choco.mem.Environment;
import choco.mem.StoredIntVector;

// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: StoredPointerCycle.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

/**
 * A data structure implementing a subset of a given set, by means of a cyclic chain of pointers
 * (grossly specking, each entry contains the index of the next entry in the cycle).
 * This data structure is convenient for iterating a subset of objets (chain of pointers), starting
 * from any object (cyclic iteration).
 * The data structure is based on backtrackable vectors (@link{StoredIntVector})
 * It is also robust in the sense that objects can be removed from the cycle while the cycle is being iterated
 */
public class StoredPointerCycle {
	class CyclicIterator implements IntIterator {
		/**
		 * the variable over whose constraint cycle we are iterating
		 */
		private final StoredIntVector itnext;

		/**
		 *  current index of the iteration (the one just returned at the last call to next())
		 */
		private int k;

		/**
		 *  the index where the iteration started when the iterator was created
		 */
		private int endMarker;

		/**
		 * marker: true when the iteration is from the first index to the vector end (first half of the iteration)
		 *         false when the itereation is from the vector start to the first index, excluded (second half of the iteration)
		 */
		private boolean didCircleArnoundTheEnd;

		/**
		 * constructor
		 */
		public CyclicIterator(
			final StoredPointerCycle pCycle,
			final int avoidIndex) {
			this.itnext = pCycle.next;
			this.didCircleArnoundTheEnd = false;
			final int n = pCycle.size();
			if (n > 0) {
				if (avoidIndex == -1) {
					this.k = n - 1;
					this.endMarker = n;
				}
				else {
					this.k = avoidIndex;
					this.endMarker = avoidIndex;
				}
			}
			else {
				this.k = -1;
				this.endMarker = -1;
			}
		}

		public boolean hasNext() {
			final int nextk = this.itnext.get(this.k);
			if (!this.didCircleArnoundTheEnd) {
				return nextk > this.k || nextk > -1 && nextk < this.endMarker;
			}
			else {
				return nextk > this.k && nextk < this.endMarker;
			}
		}

		public int next() {
			int prevk = this.k;
			this.k = this.itnext.get(this.k);
			if (!this.didCircleArnoundTheEnd && this.k <= prevk) {
				prevk = -1; // useless
				this.didCircleArnoundTheEnd = true;
			}
			return this.k;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	class EmptyIterator implements IntIterator {
		public boolean hasNext() {
			return false;
		}

		public int next() {
			return 0;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * wildcard: all entries of the vector contain valid indices (i.e. values from 0 to n-1, n being the vector size)
	 * or the constant INVALID_INDEX
	 */
	public final static int INVALID_INDEX = -1;

	/**
	 * The vectors of entries
	 */

	protected StoredIntVector next;

	/**
	 * Reference to the root Logger, an object for logging trace statements related to propagation events (using the java.util.logging package)
	 */

	protected static Logger logger = Logger
		.getLogger("choco.util.StoredPointerCycle");

	public StoredPointerCycle(final Environment env) {
		this.next = env.makeIntVector();
	}

	/**
	 * Adds an entry to the static collection, at a given index
	 * @param inCycle Specifies if the new entry is in the cycle
	 */
	public void add(final int idx, final boolean inCycle) { // ex connectEvent
		if (idx >= 1) {
			final int j = this.next.get(idx - 1);
			int k = idx - 1;
			if (j == -1 && inCycle) {
				this.next.add(idx);
			}
			else {
				this.next.add(j);
			}
			if (inCycle) {
				while (k >= 0 && k >= j && this.next.get(k) == j) {
					this.next.set(k, idx);
					k--;
				}
			}
		}
		else {
			if (inCycle) {
				this.next.add(0);
			}
			else {
				this.next.add(-1);
			}
		}
	}

	public IntIterator getCycleButIterator(final int avoidIndex) {
		final int n = this.size();
		if (n > 0) {
			return new CyclicIterator(this, avoidIndex);
		}
		else {
			return new EmptyIterator();
		}
	}

	/**
	 * Tests if a given index is in the cycle or not
	 */

	public boolean isInCycle(final int idx) {
		final int n = this.next.size();
		final int k = idx == 0 ? n - 1 : idx - 1;
		return n != 0 && this.next.get(k) == idx;
	}

	/**
	 * Sets the index into the cycle
	 */

	public void setInCycle(final int i) { // ex disconnectEvent
		final int n = this.next.size();
		final int nextIdx = this.next.get(i);

		if (nextIdx == -1) { // No listener
			for (int j = 0; j < n; j++) {
				this.next.set(j, i);
			}
		}
		else {
			int k = i == 0 ? n - 1 : i - 1;
			boolean needToContinue = true;

			while (needToContinue) {
				if (this.next.get(k) == nextIdx) {
					this.next.set(k, i);
					needToContinue = k != nextIdx;
				}
				else {
					needToContinue = false;
				}
				k = k == 0 ? n - 1 : k - 1;
			}
		}
	}

	/**
	 * Sets the index into the cycle
	 */
	public void setOutOfCycle(final int i) { // ex reconnectEvent
		final int n = this.next.size();

		if (this.next.get(i) == -1) { // Already deactivated
		}
		else if (this.next.get(i) == i) { // This is the only one
			for (int j = 0; j < n; j++) {
				this.next.set(j, -1);
			}
		}
		else {
			final int j = this.next.get(i);
			int k = i == 0 ? n - 1 : i - 1;
			while (this.next.get(k) == i) {
				this.next.set(k, j);
				k = k == 0 ? n - 1 : k - 1;
			}
		}
	}

	public int size() {
		return this.next.size();
	}

}

/*    public void propagateEvent(EventQueue q) throws ContradictionException {

    // first, mark event
    VarEventQueue queue = (VarEventQueue) q;
    queue.setPopping(true);

    AbstractVar v =    logger.finer("propagate " + this.toString());
 getModifiedVar();
    this.freeze();

    int n = v.getNbConstraints();
    if (n > 0) {
      StoredIntVector constList = getNextConst();
      int prevk = cause;
      int k = (cause == NOCAUSE) ? constList.get(n - 1) : constList.get(cause);

      if ((k >= 0) && (k != cause)) {
        Constraint[] constraints = getModifiedVar().getConstraints();
        Integer[] indices = getModifiedVar().getIndices();

        while (k > prevk) {
          constraints[k].propagateEvent(indices[k].intValue(), this);
          prevk = k;
          k = constList.get(k);
        }

        prevk = -1; // 0 est authorise !!! => il faut mettre -1

        while ((k > prevk) && (k < cause)) {
          constraints[k].propagateEvent(indices[k].intValue(), this);
          prevk = k;
          k = constList.get(k);
        }
      }
    }
    // last, release event
    this.release();
    queue.setPopping(false);
  }
  */

