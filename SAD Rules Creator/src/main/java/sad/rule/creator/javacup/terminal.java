package sad.rule.creator.javacup;

import java.util.Enumeration;
import java.util.Hashtable;

/** This class represents a terminal symbol in the grammar.  Each terminal 
 *  has a textual name, an index, and a string which indicates the type of 
 *  object it will be implemented with at runtime (i.e. the class of object 
 *  that will be returned by the scanner and pushed on the parse stack to 
 *  represent it). 
 *
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */
public class terminal extends symbol {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Access to all terminals. */
	public static Enumeration all() {
		return terminal._all.elements();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	private int _precedence_num;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	private int _precedence_side;

	/*-----------------------------------------------------------*/
	/*-------------------  Class Variables  ---------------------*/
	/*-----------------------------------------------------------*/

	/** Table of all terminals.  Elements are stored using name strings as 
	 *  the key 
	 */
	protected static Hashtable _all = new Hashtable();
	/** Table of all terminals indexed by their index number. */
	protected static Hashtable _all_by_index = new Hashtable();

	/*-----------------------------------------------------------*/
	/*--- (Access to) Static (Class) Variables ------------------*/
	/*-----------------------------------------------------------*/

	/** Static counter to assign unique index. */
	protected static int next_index = 0;

	/** Special terminal for end of input. */
	public static final terminal EOF = new terminal("EOF");

	/** special terminal used for error recovery */
	public static final terminal error = new terminal("error");

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Lookup a terminal by index. */
	public static terminal find(final int indx) {
		final Integer the_indx = Integer.valueOf(indx);

		return (terminal) terminal._all_by_index.get(the_indx);
	}

	/** Lookup a terminal by name string. */
	public static terminal find(final String with_name) {
		if (with_name == null) {
			return null;
		}
		else {
			return (terminal) terminal._all.get(with_name);
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Total number of terminals. */
	public static int number() {
		return terminal._all.size();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Constructor with default type. 
	 * @param nm the name of the terminal.
	 */
	public terminal(final String nm) {
		this(nm, null);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Constructor for non-precedented terminal
	 */

	public terminal(final String nm, final String tp) {
		this(nm, tp, assoc.no_prec, -1);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Full constructor.
	 * @param nm the name of the terminal.
	 * @param tp the type of the terminal.
	 */
	public terminal(
		final String nm,
		final String tp,
		final int precedence_side,
		final int precedence_num) {
		/* superclass does most of the work */
		super(nm, tp);

		/* add to set of all terminals and check for duplicates */
		final Object conflict = terminal._all.put(nm, this);
		if (conflict != null) {
			// can't throw an execption here because this is used in static 
			// initializers, so we do a crash instead
			// was:
			// throw new internal_error("Duplicate terminal (" + nm + ") created");
			new internal_error("Duplicate terminal (" + nm + ") created")
				.crash();
		}

		/* assign a unique index */
		this._index = terminal.next_index++;

		/* set the precedence */
		this._precedence_num = precedence_num;
		this._precedence_side = precedence_side;

		/* add to by_index set */
		terminal._all_by_index.put(Integer.valueOf(this._index), this);
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Report this symbol as not being a non-terminal. */
	public boolean is_non_term() {
		return false;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** get the precedence of a terminal */
	public int precedence_num() {
		return this._precedence_num;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	public int precedence_side() {
		return this._precedence_side;
	}
	/** set the precedence of a terminal */
	public void set_precedence(final int p, final int new_prec) {
		this._precedence_side = p;
		this._precedence_num = new_prec;
	}

	/** Convert to a string. */
	public String toString() {
		return super.toString() + "[" + this.index() + "]";
	}

	/*-----------------------------------------------------------*/

}
