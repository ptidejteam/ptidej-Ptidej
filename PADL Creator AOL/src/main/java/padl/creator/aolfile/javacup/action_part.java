package padl.creator.aolfile.javacup;

/** 
 * This class represents a part of a production which contains an
 * action.  These are eventually eliminated from productions and converted
 * to trailing actions by factoring out with a production that derives the
 * empty string (and ends with this action).
 *
 * @see java_cup.production
 * @version last update: 11/25/95
 * @author Scott Hudson
 */

public class action_part extends production_part {

	/*-----------------------------------------------------------*/
	/*--- Constructors ------------------------------------------*/
	/*-----------------------------------------------------------*/

	/** String containing code for the action in question. */
	protected String _code_string;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Simple constructor. 
	 * @param code_str string containing the actual user code.
	 */
	public action_part(final String code_str) {
		super(/* never have a label on code */null);
		this._code_string = code_str;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** String containing code for the action in question. */
	public String code_string() {
		return this._code_string;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Equality comparison for properly typed object. */
	public boolean equals(final action_part other) {
		/* compare the strings */
		return other != null && super.equals(other)
				&& other.code_string().equals(this.code_string());
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Generic equality comparison. */
	public boolean equals(final Object other) {
		if (!(other instanceof action_part)) {
			return false;
		}
		else {
			return this.equals((action_part) other);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce a hash code. */
	public int hashCode() {
		return super.hashCode()
				^ (this.code_string() == null ? 0 : this
					.code_string()
					.hashCode());
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Override to report this object as an action. */
	public boolean is_action() {
		return true;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Set the code string. */
	public void set_code_string(final String new_str) {
		this._code_string = new_str;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a string.  */
	public String toString() {
		return super.toString() + "{" + this.code_string() + "}";
	}

	/*-----------------------------------------------------------*/
}
