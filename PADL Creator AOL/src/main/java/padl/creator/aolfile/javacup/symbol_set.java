package padl.creator.aolfile.javacup;

import java.util.Enumeration;
import java.util.Hashtable;

/** This class represents a set of symbols and provides a series of 
 *  set operations to manipulate them.
 *
 * @see     java_cup.symbol
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
@SuppressWarnings("rawtypes")
public class symbol_set {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** A hash table to hold the set. Symbols are keyed using their name string. 
	 */
	protected Hashtable _all = new Hashtable(11);

	/** Constructor for an empty set. */
	public symbol_set() {
	}

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Constructor for cloning from another set. 
	 * @param other the set we are cloning from.
	 */
	public symbol_set(final symbol_set other) throws internal_error {
		this.not_null(other);
		this._all = (Hashtable) other._all.clone();
	}

	/** Add a single symbol to the set.  
	 * @param sym the symbol we are adding.
	 * @return true if this changes the set.
	 */
	@SuppressWarnings("unchecked")
	public boolean add(final symbol sym) throws internal_error {
		Object previous;

		this.not_null(sym);

		/* put the object in */
		previous = this._all.put(sym.name(), sym);

		/* if we had a previous, this is no change */
		return previous == null;
	}

	/** Add (union) in a complete set.  
	 * @param other the set we are adding in.
	 * @return true if this changes the set. 
	 */
	public boolean add(final symbol_set other) throws internal_error {
		boolean result = false;

		this.not_null(other);

		/* walk down the other set and do the adds individually */
		for (final Enumeration e = other.all(); e.hasMoreElements();) {
			result = this.add((symbol) e.nextElement()) || result;
		}

		return result;
	}

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Access to all elements of the set. */
	public Enumeration all() {
		return this._all.elements();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if the set contains a particular symbol. 
	 * @param sym the symbol we are looking for.
	 */
	public boolean contains(final symbol sym) {
		return this._all.containsKey(sym.name());
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Generic equality comparison. */
	public boolean equals(final Object other) {
		if (!(other instanceof symbol_set)) {
			return false;
		}
		else {
			return this.equals((symbol_set) other);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Equality comparison. */
	public boolean equals(final symbol_set other) {
		if (other == null || other.size() != this.size()) {
			return false;
		}

		/* once we know they are the same size, then improper subset does test */
		try {
			return this.is_subset_of(other);
		}
		catch (final internal_error e) {
			/* can't throw the error (because super class doesn't), so we crash */
			e.crash();
			return false;
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Compute a hash code. */
	public int hashCode() {
		int result = 0;
		int cnt;
		Enumeration e;

		/* hash together codes from at most first 5 elements */
		for (e = this.all(), cnt = 0; e.hasMoreElements() && cnt < 5; cnt++) {
			result ^= ((symbol) e.nextElement()).hashCode();
		}

		return result;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if this set is an (improper) subset of another. 
	 * @param other the set we are testing against.
	 */
	public boolean is_subset_of(final symbol_set other) throws internal_error {
		this.not_null(other);

		/* walk down our set and make sure every element is in the other */
		for (final Enumeration e = this.all(); e.hasMoreElements();) {
			if (!other.contains((symbol) e.nextElement())) {
				return false;
			}
		}

		/* they were all there */
		return true;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if this set is an (improper) superset of another. 
	 * @param other the set we are are testing against.
	 */
	public boolean is_superset_of(final symbol_set other) throws internal_error {
		this.not_null(other);
		return other.is_subset_of(this);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Helper function to test for a null object and throw an exception
	 *  if one is found.
	 * @param obj the object we are testing.
	 */
	protected void not_null(final Object obj) throws internal_error {
		if (obj == null) {
			throw new internal_error("Null object used in set operation");
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Remove a single symbol if it is in the set. 
	 * @param sym the symbol we are removing.
	 */
	public void remove(final symbol sym) throws internal_error {
		this.not_null(sym);
		this._all.remove(sym.name());
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Remove (set subtract) a complete set. 
	 * @param other the set we are removing.
	 */
	public void remove(final symbol_set other) throws internal_error {
		this.not_null(other);

		/* walk down the other set and do the removes individually */
		for (final Enumeration e = other.all(); e.hasMoreElements();) {
			this.remove((symbol) e.nextElement());
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** size of the set */
	public int size() {
		return this._all.size();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a string. */
	public String toString() {
		String result;
		boolean comma_flag;

		result = "{";
		comma_flag = false;
		for (final Enumeration e = this.all(); e.hasMoreElements();) {
			if (comma_flag) {
				result += ", ";
			}
			else {
				comma_flag = true;
			}

			result += ((symbol) e.nextElement()).name();
		}
		result += "}";

		return result;
	}

	/*-----------------------------------------------------------*/

}
