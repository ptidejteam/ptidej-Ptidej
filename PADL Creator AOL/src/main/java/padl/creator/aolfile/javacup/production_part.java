package padl.creator.aolfile.javacup;

/** This class represents one part (either a symbol or an action) of a 
 *  production.  In this base class it contains only an optional label 
 *  string that the user can use to refer to the part within actions.<p>
 *
 *  This is an abstract class.
 *
 * @see     java_cup.production
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public abstract class production_part {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Optional label for referring to the part within an action (null for 
	 *  no label). 
	 */
	protected String _label;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Simple constructor. */
	public production_part(final String lab) {
		this._label = lab;
	}

	/** Generic equality comparison. */
	public boolean equals(final Object other) {
		if (!(other instanceof production_part)) {
			return false;
		}
		else {
			return this.equals((production_part) other);
		}
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Equality comparison. */
	public boolean equals(final production_part other) {
		if (other == null) {
			return false;
		}

		/* compare the labels */
		if (this.label() != null) {
			return this.label().equals(other.label());
		}
		else {
			return other.label() == null;
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce a hash code. */
	public int hashCode() {
		return this.label() == null ? 0 : this.label().hashCode();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Indicate if this is an action (rather than a symbol).  Here in the 
	 * base class, we don't this know yet, so its an abstract method.
	 */
	public abstract boolean is_action();

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Optional label for referring to the part within an action (null for 
	 *  no label). 
	 */
	public String label() {
		return this._label;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a string. */
	public String toString() {
		if (this.label() != null) {
			return this.label() + ":";
		}
		else {
			return " ";
		}
	}

	/*-----------------------------------------------------------*/

}
