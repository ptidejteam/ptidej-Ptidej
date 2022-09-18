package padl.creator.aolfile.javacup;

/** This class represents a shift/reduce nonassociative error within the 
 *  parse table.  If action_table element is assign to type
 *  nonassoc_action, it cannot be changed, and signifies that there 
 *  is a conflict between shifting and reducing a production and a
 *  terminal that shouldn't be next to each other.
 *
 * @version last updated: 7/2/96
 * @author  Frank Flannery
 */
public class nonassoc_action extends parse_action {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Simple constructor. 
	 */
	public nonassoc_action() throws internal_error {
		/* don't need to set anything, since it signifies error */
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Generic equality test. */
	public boolean equals(final Object other) {
		if (other instanceof parse_action) {
			return this.equals((parse_action) other);
		}
		else {
			return false;
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Equality test. */
	public boolean equals(final parse_action other) {
		return other != null && other.kind() == parse_action.NONASSOC;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Compute a hash code. */
	public int hashCode() {
		/* all objects of this class hash together */
		return 0xCafe321;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Quick access to type of action. */
	public int kind() {
		return parse_action.NONASSOC;
	}

	/** Convert to string. */
	public String toString() {
		return "NONASSOC";
	}

	/*-----------------------------------------------------------*/

}
