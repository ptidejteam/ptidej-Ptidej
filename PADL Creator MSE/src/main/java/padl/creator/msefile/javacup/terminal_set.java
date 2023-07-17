package padl.creator.msefile.javacup;

import java.util.BitSet;

/** A set of terminals implemented as a bitset. 
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public class terminal_set {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Constant for the empty set. */
	public static final terminal_set EMPTY = new terminal_set();

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Bitset to implement the actual set. */
	protected BitSet _elements;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Static (Class) Variables ------------------*/
	/*-----------------------------------------------------------*/

	/** Constructor for an empty set. */
	public terminal_set() {
		/* allocate the bitset at what is probably the right size */
		this._elements = new BitSet(terminal.number());
	}

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Constructor for cloning from another set. 
	 * @param other the set we are cloning from.
	 */
	public terminal_set(final terminal_set other) throws internal_error {
		this.not_null(other);
		this._elements = (BitSet) other._elements.clone();
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Add a single terminal to the set.  
	 * @param sym the terminal being added.
	 * @return true if this changes the set.
	 */
	public boolean add(final terminal sym) throws internal_error {
		boolean result;

		this.not_null(sym);

		/* see if we already have this */
		result = this._elements.get(sym.index());

		/* if not we add it */
		if (!result) {
			this._elements.set(sym.index());
		}

		return result;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Add (union) in a complete set.  
	 * @param other the set being added.
	 * @return true if this changes the set.
	 */
	public boolean add(final terminal_set other) throws internal_error {
		this.not_null(other);

		/* make a copy */
		final BitSet copy = (BitSet) this._elements.clone();

		/* or in the other set */
		this._elements.or(other._elements);

		/* changed if we are not the same as the copy */
		return !this._elements.equals(copy);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Given its index determine if the set contains a particular terminal. 
	 * @param indx the index of the terminal in question.
	 */
	public boolean contains(final int indx) {
		return this._elements.get(indx);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if the set contains a particular terminal. 
	 * @param sym the terminal symbol we are looking for.
	 */
	public boolean contains(final terminal sym) throws internal_error {
		this.not_null(sym);
		return this._elements.get(sym.index());
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if the set is empty. */
	public boolean empty() {
		return this.equals(terminal_set.EMPTY);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Generic equality comparison. */
	public boolean equals(final Object other) {
		if (!(other instanceof terminal_set)) {
			return false;
		}
		else {
			return this.equals((terminal_set) other);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Equality comparison. */
	public boolean equals(final terminal_set other) {
		if (other == null) {
			return false;
		}
		else {
			return this._elements.equals(other._elements);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if this set intersects another.
	 * @param other the other set in question.
	 */
	public boolean intersects(final terminal_set other) throws internal_error {
		this.not_null(other);

		/* make a copy of the other set */
		final BitSet copy = (BitSet) other._elements.clone();

		/* xor out our values */
		copy.xor(this._elements);

		/* see if its different */
		return !copy.equals(other._elements);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if this set is an (improper) subset of another.
	 * @param other the set we are testing against.
	 */
	public boolean is_subset_of(final terminal_set other) throws internal_error {
		this.not_null(other);

		/* make a copy of the other set */
		final BitSet copy_other = (BitSet) other._elements.clone();

		/* and or in */
		copy_other.or(this._elements);

		/* if it hasn't changed, we were a subset */
		return copy_other.equals(other._elements);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if this set is an (improper) superset of another.
	 * @param other the set we are testing against.
	 */
	public boolean is_superset_of(final terminal_set other)
			throws internal_error {
		this.not_null(other);
		return other.is_subset_of(this);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Helper function to test for a null object and throw an exception if
	 *  one is found. 
	 * @param obj the object we are testing.
	 */
	protected void not_null(final Object obj) throws internal_error {
		if (obj == null) {
			throw new internal_error("Null object used in set operation");
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Remove a terminal if it is in the set.
	 * @param sym the terminal being removed.
	 */
	public void remove(final terminal sym) throws internal_error {
		this.not_null(sym);
		this._elements.clear(sym.index());
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to string. */
	public String toString() {
		String result;
		boolean comma_flag;

		result = "{";
		comma_flag = false;
		for (int t = 0; t < terminal.number(); t++) {
			if (this._elements.get(t)) {
				if (comma_flag) {
					result += ", ";
				}
				else {
					comma_flag = true;
				}

				result += terminal.find(t).name();
			}
		}
		result += "}";

		return result;
	}

	/*-----------------------------------------------------------*/

}
