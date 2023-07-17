package sad.rule.creator.javacup;

/** This class represents a shift action within the parse table. 
 *  The action simply stores the state that it shifts to and responds 
 *  to queries about its type.
 *
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public class shift_action extends parse_action {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** The state we shift to. */
	protected lalr_state _shift_to;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Simple constructor. 
	 * @param shft_to the state that this action shifts to.
	 */
	public shift_action(final lalr_state shft_to) throws internal_error {
		/* sanity check */
		if (shft_to == null) {
			throw new internal_error(
				"Attempt to create a shift_action to a null state");
		}

		this._shift_to = shft_to;
	}

	/** Generic equality test. */
	public boolean equals(final Object other) {
		if (other instanceof shift_action) {
			return this.equals((shift_action) other);
		}
		else {
			return false;
		}
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Equality test. */
	public boolean equals(final shift_action other) {
		return other != null && other.shift_to() == this.shift_to();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Compute a hash code. */
	public int hashCode() {
		/* use the hash code of the state we are shifting to */
		return this.shift_to().hashCode();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Quick access to type of action. */
	public int kind() {
		return parse_action.SHIFT;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The state we shift to. */
	public lalr_state shift_to() {
		return this._shift_to;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a string. */
	public String toString() {
		return "SHIFT(to state " + this.shift_to().index() + ")";
	}

	/*-----------------------------------------------------------*/

}
