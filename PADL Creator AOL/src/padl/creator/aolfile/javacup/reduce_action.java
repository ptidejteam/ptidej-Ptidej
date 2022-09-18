package padl.creator.aolfile.javacup;

/** This class represents a reduce action within the parse table. 
 *  The action simply stores the production that it reduces with and 
 *  responds to queries about its type.
 *
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public class reduce_action extends parse_action {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** The production we reduce with. */
	protected production _reduce_with;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Simple constructor. 
	 * @param prod the production this action reduces with.
	 */
	public reduce_action(final production prod) throws internal_error {
		/* sanity check */
		if (prod == null) {
			throw new internal_error(
				"Attempt to create a reduce_action with a null production");
		}

		this._reduce_with = prod;
	}

	/** Generic equality test. */
	public boolean equals(final Object other) {
		if (other instanceof reduce_action) {
			return this.equals((reduce_action) other);
		}
		else {
			return false;
		}
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Equality test. */
	public boolean equals(final reduce_action other) {
		return other != null && other.reduce_with() == this.reduce_with();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Compute a hash code. */
	public int hashCode() {
		/* use the hash code of the production we are reducing with */
		return this.reduce_with().hashCode();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Quick access to type of action. */
	public int kind() {
		return parse_action.REDUCE;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The production we reduce with. */
	public production reduce_with() {
		return this._reduce_with;
	}

	/** Convert to string. */
	public String toString() {
		return "REDUCE(with prod " + this.reduce_with().index() + ")";
	}

	/*-----------------------------------------------------------*/

}
