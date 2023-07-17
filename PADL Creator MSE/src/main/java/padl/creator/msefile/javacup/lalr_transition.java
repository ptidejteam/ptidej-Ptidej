package padl.creator.msefile.javacup;

/** This class represents a transition in an LALR viable prefix recognition 
 *  machine.  Transitions can be under terminals for non-terminals.  They are
 *  internally linked together into singly linked lists containing all the 
 *  transitions out of a single state via the _next field.
 *
 * @see     java_cup.lalr_state
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 *
 */
public class lalr_transition {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** The symbol we make the transition on. */
	protected symbol _on_symbol;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The state we transition to. */
	protected lalr_state _to_state;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Next transition in linked list of transitions out of a state */
	protected lalr_transition _next;

	/** Constructor with null next. 
	 * @param on_sym  symbol we are transitioning on.
	 * @param to_st   state we transition to.
	 */
	public lalr_transition(final symbol on_sym, final lalr_state to_st)
			throws internal_error {
		this(on_sym, to_st, null);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Full constructor.
	 * @param on_sym  symbol we are transitioning on.
	 * @param to_st   state we transition to.
	 * @param nxt     next transition in linked list.
	 */
	public lalr_transition(
		final symbol on_sym,
		final lalr_state to_st,
		final lalr_transition nxt) throws internal_error {
		/* sanity checks */
		if (on_sym == null) {
			throw new internal_error(
				"Attempt to create transition on null symbol");
		}
		if (to_st == null) {
			throw new internal_error(
				"Attempt to create transition to null state");
		}

		/* initialize */
		this._on_symbol = on_sym;
		this._to_state = to_st;
		this._next = nxt;
	}

	/** Next transition in linked list of transitions out of a state */
	public lalr_transition next() {
		return this._next;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The symbol we make the transition on. */
	public symbol on_symbol() {
		return this._on_symbol;
	}

	/** The state we transition to. */
	public lalr_state to_state() {
		return this._to_state;
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Convert to a string. */
	public String toString() {
		String result;

		result = "transition on " + this.on_symbol().name() + " to state [";
		result += this._to_state.index();
		result += "]";

		return result;
	}

	/*-----------------------------------------------------------*/
}
