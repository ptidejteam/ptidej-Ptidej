package padl.creator.aolfile.javacup;

/** The "core" of an LR item.  This includes a production and the position
 *  of a marker (the "dot") within the production.  Typically item cores 
 *  are written using a production with an embedded "dot" to indicate their 
 *  position.  For example: <pre>
 *     A ::= B * C d E
 *  </pre>
 *  This represents a point in a parse where the parser is trying to match
 *  the given production, and has succeeded in matching everything before the 
 *  "dot" (and hence is expecting to see the symbols after the dot next).  See 
 *  lalr_item, lalr_item_set, and lalr_start for full details on the meaning 
 *  and use of items.
 *
 * @see     java_cup.lalr_item
 * @see     java_cup.lalr_item_set
 * @see     java_cup.lalr_state
 * @version last updated: 11/25/95
 * @author  Scott Hudson
*/

public class lr_item_core {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** The production for the item. */
	protected production _the_production;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The position of the "dot" -- this indicates the part of the production 
	 *  that the marker is before, so 0 indicates a dot at the beginning of 
	 *  the RHS.
	 */
	protected int _dot_pos;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Cache of the hash code. */
	protected int _core_hash_cache;

	/** Cache of symbol after the dot. */
	protected symbol _symbol_after_dot = null;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Constructor for dot at start of right hand side. 
	 * @param prod production this item uses.
	 */
	public lr_item_core(final production prod) throws internal_error {
		this(prod, 0);
	}

	/** Full constructor.
	 * @param prod production this item uses.
	 * @param pos  position of the "dot" within the item.
	 */
	public lr_item_core(final production prod, final int pos)
			throws internal_error {
		final symbol after_dot = null;
		production_part part;

		if (prod == null) {
			throw new internal_error(
				"Attempt to create an lr_item_core with a null production");
		}

		this._the_production = prod;

		if (pos < 0 || pos > this._the_production.rhs_length()) {
			throw new internal_error(
				"Attempt to create an lr_item_core with a bad dot position");
		}

		this._dot_pos = pos;

		/* compute and cache hash code now */
		this._core_hash_cache = 13 * this._the_production.hashCode() + pos;

		/* cache the symbol after the dot */
		if (this._dot_pos < this._the_production.rhs_length()) {
			part = this._the_production.rhs(this._dot_pos);
			if (!part.is_action()) {
				this._symbol_after_dot = ((symbol_part) part).the_symbol();
			}
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Equality comparison for the core only.  This is separate out because we 
	 *  need separate access in a super class. 
	 */
	public boolean core_equals(final lr_item_core other) {
		return other != null
				&& this._the_production.equals(other._the_production)
				&& this._dot_pos == other._dot_pos;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Hash code for the core (separated so we keep non overridden version). */
	public int core_hashCode() {
		return this._core_hash_cache;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Is the dot at the end of the production? */
	public boolean dot_at_end() {
		return this._dot_pos >= this._the_production.rhs_length();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if we have a dot before a non terminal, and if so which one 
	 *  (return null or the non terminal). 
	 */
	public non_terminal dot_before_nt() {
		symbol sym;

		/* get the symbol after the dot */
		sym = this.symbol_after_dot();

		/* if it exists and is a non terminal, return it */
		if (sym != null && sym.is_non_term()) {
			return (non_terminal) sym;
		}
		else {
			return null;
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The position of the "dot" -- this indicates the part of the production 
	 *  that the marker is before, so 0 indicates a dot at the beginning of 
	 *  the RHS.
	 */
	public int dot_pos() {
		return this._dot_pos;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Equality comparison. */
	public boolean equals(final lr_item_core other) {
		return this.core_equals(other);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Generic equality comparison. */
	public boolean equals(final Object other) {
		if (!(other instanceof lr_item_core)) {
			return false;
		}
		else {
			return this.equals((lr_item_core) other);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Hash code for the item. */
	public int hashCode() {
		return this._core_hash_cache;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Return the hash code that object would have provided for us so we have 
	 *  a (nearly) unique id for debugging.
	 */
	protected int obj_hash() {
		return super.hashCode();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce a new lr_item_core that results from shifting the dot one 
	 *  position to the right. 
	 */
	public lr_item_core shift_core() throws internal_error {
		if (this.dot_at_end()) {
			throw new internal_error(
				"Attempt to shift past end of an lr_item_core");
		}

		return new lr_item_core(this._the_production, this._dot_pos + 1);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Return the symbol after the dot.  If there is no symbol after the dot
	 *  we return null. */
	public symbol symbol_after_dot() {
		/* use the cached symbol */
		return this._symbol_after_dot;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The production for the item. */
	public production the_production() {
		return this._the_production;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a string (separated out from toString() so we can call it
	 *  from subclass that overrides toString()).
	 */
	public String to_simple_string() throws internal_error {
		String result;
		production_part part;

		if (this._the_production.lhs() != null
				&& this._the_production.lhs().the_symbol() != null
				&& this._the_production.lhs().the_symbol().name() != null) {
			result = this._the_production.lhs().the_symbol().name();
		}
		else {
			result = "$$NULL$$";
		}

		result += " ::= ";

		for (int i = 0; i < this._the_production.rhs_length(); i++) {
			/* do we need the dot before this one? */
			if (i == this._dot_pos) {
				result += "(*) ";
			}

			/* print the name of the part */
			if (this._the_production.rhs(i) == null) {
				result += "$$NULL$$ ";
			}
			else {
				part = this._the_production.rhs(i);
				if (part == null) {
					result += "$$NULL$$ ";
				}
				else if (part.is_action()) {
					result += "{ACTION} ";
				}
				else if (((symbol_part) part).the_symbol() != null
						&& ((symbol_part) part).the_symbol().name() != null) {
					result += ((symbol_part) part).the_symbol().name() + " ";
				}
				else {
					result += "$$NULL$$ ";
				}
			}
		}

		/* put the dot after if needed */
		if (this._dot_pos == this._the_production.rhs_length()) {
			result += "(*) ";
		}

		return result;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a string */
	public String toString() {
		/* can't throw here since super class doesn't, so we crash instead */
		try {
			return this.to_simple_string();
		}
		catch (final internal_error e) {
			e.crash();
			return null;
		}
	}

	/*-----------------------------------------------------------*/

}
