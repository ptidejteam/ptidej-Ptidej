package padl.creator.aolfile.javacup;

import java.util.Stack;

/** This class represents an LALR item. Each LALR item consists of 
 *  a production, a "dot" at a position within that production, and
 *  a set of lookahead symbols (terminal).  (The first two of these parts
 *  are provide by the super class).  An item is designed to represent a 
 *  configuration that the parser may be in.  For example, an item of the 
 *  form: <pre>
 *    [A ::= B * C d E  , {a,b,c}]
 *  </pre>
 *  indicates that the parser is in the middle of parsing the production <pre>
 *    A ::= B C d E
 *  </pre>
 *  that B has already been parsed, and that we will expect to see a lookahead 
 *  of either a, b, or c once the complete RHS of this production has been 
 *  found.<p>
 *
 *  Items may initially be missing some items from their lookahead sets.  
 *  Links are maintained from each item to the set of items that would need 
 *  to be updated if symbols are added to its lookahead set.  During 
 *  "lookahead propagation", we add symbols to various lookahead sets and 
 *  propagate these changes across these dependency links as needed. 
 *  
 * @see     java_cup.lalr_item_set
 * @see     java_cup.lalr_state
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
@SuppressWarnings("rawtypes")
public class lalr_item extends lr_item_core {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** The lookahead symbols of the item. */
	protected terminal_set _lookahead;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Links to items that the lookahead needs to be propagated to. */
	protected Stack _propagate_items;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Flag to indicate that this item needs to propagate its lookahead 
	 *  (whether it has changed or not). 
	 */
	protected boolean needs_propagation;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Constructor with default position and empty lookahead set. 
	 * @param prod the production for the item.
	 */
	public lalr_item(final production prod) throws internal_error {
		this(prod, 0, new terminal_set());
	}

	/** Full constructor. 
	 * @param prod the production for the item.
	 * @param pos  the position of the "dot" within the production.
	 * @param look the set of lookahead symbols.
	 */
	public lalr_item(
		final production prod,
		final int pos,
		final terminal_set look) throws internal_error {
		super(prod, pos);
		this._lookahead = look;
		this._propagate_items = new Stack();
		this.needs_propagation = true;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Constructor with default position (dot at start). 
	 * @param prod the production for the item.
	 * @param look the set of lookahead symbols.
	 */
	public lalr_item(final production prod, final terminal_set look)
			throws internal_error {
		this(prod, 0, look);
	}

	/** Add a new item to the set of items we propagate to. */
	@SuppressWarnings("unchecked")
	public void add_propagate(final lalr_item prop_to) {
		this._propagate_items.push(prop_to);
		this.needs_propagation = true;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Calculate lookahead representing symbols that could appear after the
	 *   symbol that the dot is currently in front of.  Note: this routine must
	 *   not be invoked before first sets and nullability has been calculated
	 *   for all non terminals. 
	 */
	public terminal_set calc_lookahead(final terminal_set lookahead_after)
			throws internal_error {
		terminal_set result;
		int pos;
		production_part part;
		symbol sym;

		/* sanity check */
		if (this.dot_at_end()) {
			throw new internal_error(
				"Attempt to calculate a lookahead set with a completed item");
		}

		/* start with an empty result */
		result = new terminal_set();

		/* consider all nullable symbols after the one to the right of the dot */
		for (pos = this.dot_pos() + 1; pos < this.the_production().rhs_length(); pos++) {
			part = this.the_production().rhs(pos);

			/* consider what kind of production part it is -- skip actions */
			if (!part.is_action()) {
				sym = ((symbol_part) part).the_symbol();

				/* if its a terminal add it in and we are done */
				if (!sym.is_non_term()) {
					result.add((terminal) sym);
					return result;
				}
				else {
					/* otherwise add in first set of the non terminal */
					result.add(((non_terminal) sym).first_set());

					/* if its nullable we continue adding, if not, we are done */
					if (!((non_terminal) sym).nullable()) {
						return result;
					}
				}
			}
		}

		/* if we get here everything past the dot was nullable 
		   we add in the lookahead for after the production and we are done */
		result.add(lookahead_after);
		return result;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Equality comparison -- here we only require the cores to be equal since
	 *   we need to do sets of items based only on core equality (ignoring 
	 *   lookahead sets). 
	 */
	public boolean equals(final lalr_item other) {
		if (other == null) {
			return false;
		}
		return super.equals(other);
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Generic equality comparison. */
	public boolean equals(final Object other) {
		if (!(other instanceof lalr_item)) {
			return false;
		}
		else {
			return this.equals((lalr_item) other);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Return a hash code -- here we only hash the core since we only test core
	 *  matching in LALR items. 
	 */
	public int hashCode() {
		return super.hashCode();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The lookahead symbols of the item. */
	public terminal_set lookahead() {
		return this._lookahead;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Determine if everything from the symbol one beyond the dot all the 
	 *  way to the  end of the right hand side is nullable.  This would indicate
	 *  that the lookahead of this item must be included in the lookaheads of
	 *  all items produced as a closure of this item.  Note: this routine should 
	 *  not be invoked until after first sets and nullability have been 
	 *  calculated for all non terminals. 
	 */
	public boolean lookahead_visible() throws internal_error {
		production_part part;
		symbol sym;

		/* if the dot is at the end, we have a problem, but the cleanest thing
		to do is just return true. */
		if (this.dot_at_end()) {
			return true;
		}

		/* walk down the rhs and bail if we get a non-nullable symbol */
		for (int pos = this.dot_pos() + 1; pos < this
			.the_production()
			.rhs_length(); pos++) {
			part = this.the_production().rhs(pos);

			/* skip actions */
			if (!part.is_action()) {
				sym = ((symbol_part) part).the_symbol();

				/* if its a terminal we fail */
				if (!sym.is_non_term()) {
					return false;
				}

				/* if its not nullable we fail */
				if (!((non_terminal) sym).nullable()) {
					return false;
				}
			}
		}

		/* if we get here its all nullable */
		return true;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Links to items that the lookahead needs to be propagated to */
	public Stack propagate_items() {
		return this._propagate_items;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Propagate incoming lookaheads through this item to others need to 
	 *  be changed.
	 * @params incoming symbols to potentially be added to lookahead of this item.
	 */
	public void propagate_lookaheads(final terminal_set incoming)
			throws internal_error {
		boolean change = false;

		/* if we don't need to propagate, then bail out now */
		if (!this.needs_propagation && (incoming == null || incoming.empty())) {
			return;
		}

		/* if we have null incoming, treat as an empty set */
		if (incoming != null) {
			/* add the incoming to the lookahead of this item */
			change = this.lookahead().add(incoming);
		}

		/* if we changed or need it anyway, propagate across our links */
		if (change || this.needs_propagation) {
			/* don't need to propagate again */
			this.needs_propagation = false;

			/* propagate our lookahead into each item we are linked to */
			for (int i = 0; i < this.propagate_items().size(); i++) {
				((lalr_item) this.propagate_items().elementAt(i))
					.propagate_lookaheads(this.lookahead());
			}
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce the new lalr_item that results from shifting the dot one position
	 *  to the right. 
	 */
	public lalr_item shift() throws internal_error {
		lalr_item result;

		/* can't shift if we have dot already at the end */
		if (this.dot_at_end()) {
			throw new internal_error(
				"Attempt to shift past end of an lalr_item");
		}

		/* create the new item w/ the dot shifted by one */
		result =
			new lalr_item(
				this.the_production(),
				this.dot_pos() + 1,
				new terminal_set(this.lookahead()));

		/* change in our lookahead needs to be propagated to this item */
		this.add_propagate(result);

		return result;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to string. */
	public String toString() {
		String result = "";

		// additional output for debugging:
		// result += "(" + obj_hash() + ")"; 
		result += "[";
		result += super.toString();
		result += ", ";
		if (this.lookahead() != null) {
			result += "{";
			for (int t = 0; t < terminal.number(); t++) {
				if (this.lookahead().contains(t)) {
					result += terminal.find(t).name() + " ";
				}
			}
			result += "}";
		}
		else {
			result += "NULL LOOKAHEAD!!";
		}
		result += "]";

		// additional output for debugging:
		// result += " -> ";
		// for (int i = 0; i<propagate_items().size(); i++)
		//   result+=((lalr_item)(propagate_items().elementAt(i))).obj_hash()+" ";
		//
		// if (needs_propagation) result += " NP";

		return result;
	}
	/*-----------------------------------------------------------*/
}
