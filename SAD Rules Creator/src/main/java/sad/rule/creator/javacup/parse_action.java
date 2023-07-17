package sad.rule.creator.javacup;

/** This class serves as the base class for entries in a parse action table.  
 *  Full entries will either be SHIFT(state_num), REDUCE(production), NONASSOC,
 *  or ERROR. Objects of this base class will default to ERROR, while
 *  the other three types will be represented by subclasses. 
 * 
 * @see     java_cup.reduce_action
 * @see     java_cup.shift_action
 * @version last updated: 7/2/96
 * @author  Frank Flannery
 */

public class parse_action {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Constant for action type -- error action. */
	public static final int ERROR = 0;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Static (Class) Variables ------------------*/
	/*-----------------------------------------------------------*/

	/** Constant for action type -- shift action. */
	public static final int SHIFT = 1;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Constants for action type -- reduce action. */
	public static final int REDUCE = 2;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Constants for action type -- reduce action. */
	public static final int NONASSOC = 3;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Simple constructor. */
	public parse_action() {
		/* nothing to do in the base class */
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

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Equality test. */
	public boolean equals(final parse_action other) {
		/* we match all error actions */
		return other != null && other.kind() == parse_action.ERROR;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Compute a hash code. */
	public int hashCode() {
		/* all objects of this class hash together */
		return 0xCafe123;
	}

	/** Quick access to the type -- base class defaults to error. */
	public int kind() {
		return parse_action.ERROR;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to string. */
	public String toString() {
		return "ERROR";
	}

	/*-----------------------------------------------------------*/
}
