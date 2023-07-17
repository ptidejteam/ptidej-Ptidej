package padl.creator.aolfile.javacup;

import java.util.Enumeration;
import java.util.Hashtable;

/** This class represents a non-terminal symbol in the grammar.  Each
 *  non terminal has a textual name, an index, and a string which indicates
 *  the type of object it will be implemented with at runtime (i.e. the class
 *  of object that will be pushed on the parse stack to represent it). 
 *
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */

@SuppressWarnings("rawtypes")
public class non_terminal extends symbol {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Table of all non-terminals -- elements are stored using name strings 
	 *  as the key 
	 */
	protected static Hashtable _all = new Hashtable();

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Table of all non terminals indexed by their index number. */
	protected static Hashtable _all_by_index = new Hashtable();

	/*-----------------------------------------------------------*/
	/*--- (Access to) Static (Class) Variables ------------------*/
	/*-----------------------------------------------------------*/

	/** Static counter to assign unique indexes. */
	protected static int next_index = 0;

	/** Static counter for creating unique non-terminal names */
	static protected int next_nt = 0;

	/** special non-terminal for start symbol */
	public static final non_terminal START_nt = new non_terminal("$START");

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Access to all non-terminals. */
	public static Enumeration all() {
		return non_terminal._all.elements();
	}

	/** Compute first sets for all non-terminals.  This assumes nullability has
	 *  already computed.
	 */
	public static void compute_first_sets() throws internal_error {
		boolean change = true;
		Enumeration n;
		Enumeration p;
		non_terminal nt;
		production prod;
		terminal_set prod_first;

		/* repeat this process until we have no change */
		while (change) {
			/* look for a new change */
			change = false;

			/* consider each non-terminal */
			for (n = non_terminal.all(); n.hasMoreElements();) {
				nt = (non_terminal) n.nextElement();

				/* consider every production of that non terminal */
				for (p = nt.productions(); p.hasMoreElements();) {
					prod = (production) p.nextElement();

					/* get the updated first of that production */
					prod_first = prod.check_first_set();

					/* if this going to add anything, add it */
					if (!prod_first.is_subset_of(nt._first_set)) {
						change = true;
						nt._first_set.add(prod_first);
					}
				}
			}
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Compute nullability of all non-terminals. */
	public static void compute_nullability() throws internal_error {
		boolean change = true;
		non_terminal nt;
		Enumeration e;
		production prod;

		/* repeat this process until there is no change */
		while (change) {
			/* look for a new change */
			change = false;

			/* consider each non-terminal */
			for (e = non_terminal.all(); e.hasMoreElements();) {
				nt = (non_terminal) e.nextElement();

				/* only look at things that aren't already marked nullable */
				if (!nt.nullable()) {
					if (nt.looks_nullable()) {
						nt._nullable = true;
						change = true;
					}
				}
			}
		}

		/* do one last pass over the productions to finalize all of them */
		for (e = production.all(); e.hasMoreElements();) {
			prod = (production) e.nextElement();
			prod.set_nullable(prod.check_nullable());
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** static routine for creating a new uniquely named hidden non-terminal */
	static non_terminal create_new() throws internal_error {
		return non_terminal.create_new(null);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Method for creating a new uniquely named hidden non-terminal using 
	 *  the given string as a base for the name (or "NT$" if null is passed).
	 * @param prefix base name to construct unique name from. 
	 */
	static non_terminal create_new(String prefix) throws internal_error {
		if (prefix == null) {
			prefix = "NT$";
		}
		return new non_terminal(prefix + non_terminal.next_nt++);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Lookup a non terminal by index. */
	public static non_terminal find(final int indx) {
		final Integer the_indx = Integer.valueOf(indx);

		return (non_terminal) non_terminal._all_by_index.get(the_indx);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** lookup a non terminal by name string */
	public static non_terminal find(final String with_name) {
		if (with_name == null) {
			return null;
		}
		else {
			return (non_terminal) non_terminal._all.get(with_name);
		}
	}

	/*-----------------------------------------------------------*/
	/*--- Static Methods ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Total number of non-terminals. */
	public static int number() {
		return non_terminal._all.size();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** flag non-terminals created to embed action productions */
	public boolean is_embedded_action = false; /* added 24-Mar-1998, CSA */

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Table of all productions with this non terminal on the LHS. */
	protected Hashtable _productions = new Hashtable(11);

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Nullability of this non terminal. */
	protected boolean _nullable;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** First set for this non-terminal. */
	protected terminal_set _first_set = new terminal_set();

	/** Constructor with default type. 
	 * @param nm  the name of the non terminal.
	 */
	public non_terminal(final String nm) {
		this(nm, null);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Full constructor.
	 * @param nm  the name of the non terminal.
	 * @param tp  the type string for the non terminal.
	 */
	@SuppressWarnings("unchecked")
	public non_terminal(final String nm, final String tp) {
		/* super class does most of the work */
		super(nm, tp);

		/* add to set of all non terminals and check for duplicates */
		final Object conflict = non_terminal._all.put(nm, this);
		if (conflict != null) {
			// can't throw an exception here because these are used in static
			// initializers, so we crash instead
			// was: 
			// throw new internal_error("Duplicate non-terminal ("+nm+") created");
			new internal_error("Duplicate non-terminal (" + nm + ") created")
				.crash();
		}

		/* assign a unique index */
		this._index = non_terminal.next_index++;

		/* add to by_index set */
		non_terminal._all_by_index.put(Integer.valueOf(this._index), this);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Add a production to our set of productions. */
	@SuppressWarnings("unchecked")
	public void add_production(final production prod) throws internal_error {
		/* catch improper productions */
		if (prod == null || prod.lhs() == null
				|| prod.lhs().the_symbol() != this) {
			throw new internal_error(
				"Attempt to add invalid production to non terminal production table");
		}

		/* add it to the table, keyed with itself */
		this._productions.put(prod, prod);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** First set for this non-terminal. */
	public terminal_set first_set() {
		return this._first_set;
	}

	/** Indicate that this symbol is a non-terminal. */
	public boolean is_non_term() {
		return true;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Test to see if this non terminal currently looks nullable. */
	protected boolean looks_nullable() throws internal_error {
		/* look and see if any of the productions now look nullable */
		for (final Enumeration e = this.productions(); e.hasMoreElements();) {
			/* if the production can go to empty, we are nullable */
			if (((production) e.nextElement()).check_nullable()) {
				return true;
			}
		}

		/* none of the productions can go to empty, so we are not nullable */
		return false;
	}

	/** Nullability of this non terminal. */
	public boolean nullable() {
		return this._nullable;
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Total number of productions with this non terminal on the LHS. */
	public int num_productions() {
		return this._productions.size();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Access to productions with this non terminal on the LHS. */
	public Enumeration productions() {
		return this._productions.elements();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** convert to string */
	public String toString() {
		return super.toString() + "[" + this.index() + "]"
				+ (this.nullable() ? "*" : "");
	}

	/*-----------------------------------------------------------*/
}
