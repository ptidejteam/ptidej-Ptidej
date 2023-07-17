package padl.creator.msefile.javacup;

/** This class represents a part of a production which is a symbol (terminal
 *  or non terminal).  This simply maintains a reference to the symbol in 
 *  question.
 *
 * @see     java_cup.production
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public class symbol_part extends production_part {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** The symbol that this part is made up of. */
	protected symbol _the_symbol;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Constructor with no label. 
	 * @param sym the symbol that this part is made up of.
	 */
	public symbol_part(final symbol sym) throws internal_error {
		this(sym, null);
	}

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Full constructor. 
	 * @param sym the symbol that this part is made up of.
	 * @param lab an optional label string for the part.
	 */
	public symbol_part(final symbol sym, final String lab)
			throws internal_error {
		super(lab);

		if (sym == null) {
			throw new internal_error(
				"Attempt to construct a symbol_part with a null symbol");
		}
		this._the_symbol = sym;
	}

	/** Generic equality comparison. */
	public boolean equals(final Object other) {
		if (!(other instanceof symbol_part)) {
			return false;
		}
		else {
			return this.equals((symbol_part) other);
		}
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Equality comparison. */
	public boolean equals(final symbol_part other) {
		return other != null && super.equals(other)
				&& this.the_symbol().equals(other.the_symbol());
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Produce a hash code. */
	public int hashCode() {
		return super.hashCode()
				^ (this.the_symbol() == null ? 0 : this.the_symbol().hashCode());
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Respond that we are not an action part. */
	public boolean is_action() {
		return false;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The symbol that this part is made up of. */
	public symbol the_symbol() {
		return this._the_symbol;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a string. */
	public String toString() {
		if (this.the_symbol() != null) {
			return super.toString() + this.the_symbol();
		}
		else {
			return super.toString() + "$$MISSING-SYMBOL$$";
		}
	}

	/*-----------------------------------------------------------*/

}
