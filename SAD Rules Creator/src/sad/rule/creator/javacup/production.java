package sad.rule.creator.javacup;

import java.util.Enumeration;
import java.util.Hashtable;

/** This class represents a production in the grammar.  It contains
 *  a LHS non terminal, and an array of RHS symbols.  As various 
 *  transformations are done on the RHS of the production, it may shrink.
 *  As a result a separate length is always maintained to indicate how much
 *  of the RHS array is still valid.<p>
 * 
 *  I addition to construction and manipulation operations, productions provide
 *  methods for factoring out actions (see  remove_embedded_actions()), for
 *  computing the nullability of the production (i.e., can it derive the empty
 *  string, see check_nullable()), and operations for computing its first
 *  set (i.e., the set of terminals that could appear at the beginning of some
 *  string derived from the production, see check_first_set()).
 * 
 * @see     java_cup.production_part
 * @see     java_cup.symbol_part
 * @see     java_cup.action_part
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */

public class production {

	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Table of all productions.  Elements are stored using their index as 
	 *  the key.
	 */
	protected static Hashtable _all = new Hashtable();

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Static counter for assigning unique index numbers. */
	protected static int next_index;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Access to all productions. */
	public static Enumeration all() {
		return production._all.elements();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Lookup a production by index. */
	public static production find(final int indx) {
		return (production) production._all.get(Integer.valueOf(indx));
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/*-----------------------------------------------------------*/
	/*--- (Access to) Static (Class) Variables ------------------*/
	/*-----------------------------------------------------------*/

	/** Determine if a character can be in a label id. 
	 * @param c the character in question.
	 */
	protected static boolean is_id_char(final char c) {
		return production.is_id_start(c) || c >= '0' && c <= '9';
	}

	/** Determine if a given character can be a label id starter. 
	 * @param c the character in question. 
	 */
	protected static boolean is_id_start(final char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_';

		//later need to handle non-8-bit chars here
	}

	/** Total number of productions. */
	public static int number() {
		return production._all.size();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The left hand side non-terminal. */
	protected symbol_part _lhs;

	/** The precedence of the rule */
	protected int _rhs_prec = -1;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	protected int _rhs_assoc = -1;

	/** A collection of parts for the right hand side. */
	protected production_part _rhs[];

	/** How much of the right hand side array we are presently using. */
	protected int _rhs_length;
	/** An action_part containing code for the action to be performed when we 
	 *  reduce with this production. 
	 */
	protected action_part _action;

	/** Index number of the production. */
	protected int _index;
	/** Count of number of reductions using this production. */
	protected int _num_reductions = 0;

	/** Is the nullability of the production known or unknown? */
	protected boolean _nullable_known = false;
	/** Nullability of the production (can it derive the empty string). */
	protected boolean _nullable = false;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** First set of the production.  This is the set of terminals that 
	 *  could appear at the front of some string derived from this production.
	 */
	protected terminal_set _first_set = new terminal_set();

	/** Constructor with no action string. */
	public production(
		final non_terminal lhs_sym,
		final production_part rhs_parts[],
		final int rhs_l) throws internal_error {
		this(lhs_sym, rhs_parts, rhs_l, null);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/* Constructor w/ no action string and contextual precedence
	 defined */
	public production(
		final non_terminal lhs_sym,
		final production_part rhs_parts[],
		final int rhs_l,
		final int prec_num,
		final int prec_side) throws internal_error {
		this(lhs_sym, rhs_parts, rhs_l, null);
		/* set the precedence */
		this.set_precedence_num(prec_num);
		this.set_precedence_side(prec_side);
	}

	/** Full constructor.  This constructor accepts a LHS non terminal,
	 *  an array of RHS parts (including terminals, non terminals, and 
	 *  actions), and a string for a final reduce action.   It does several
	 *  manipulations in the process of  creating a production object.
	 *  After some validity checking it translates labels that appear in
	 *  actions into code for accessing objects on the runtime parse stack.
	 *  It them merges adjacent actions if they appear and moves any trailing
	 *  action into the final reduce actions string.  Next it removes any
	 *  embedded actions by factoring them out with new action productions.  
	 *  Finally it assigns a unique index to the production.<p>
	 *
	 *  Factoring out of actions is accomplished by creating new "hidden"
	 *  non terminals.  For example if the production was originally: <pre>
	 *    A ::= B {action} C D
	 *  </pre>
	 *  then it is factored into two productions:<pre>
	 *    A ::= B X C D
	 *    X ::= {action}
	 *  </pre> 
	 *  (where X is a unique new non terminal).  This has the effect of placing
	 *  all actions at the end where they can be handled as part of a reduce by
	 *  the parser.
	 */
	public production(
		final non_terminal lhs_sym,
		final production_part rhs_parts[],
		final int rhs_l,
		String action_str) throws internal_error {
		int i;
		action_part tail_action;
		String declare_str;
		int rightlen = rhs_l;

		/* remember the length */
		if (rhs_l >= 0) {
			this._rhs_length = rhs_l;
		}
		else if (rhs_parts != null) {
			this._rhs_length = rhs_parts.length;
		}
		else {
			this._rhs_length = 0;
		}

		/* make sure we have a valid left-hand-side */
		if (lhs_sym == null) {
			throw new internal_error(
				"Attempt to construct a production with a null LHS");
		}

		/* I'm not translating labels anymore, I'm adding code to declare
		 labels as valid variables.  This way, the users code string is
		 untouched 
		 6/96 frankf */

		/* check if the last part of the right hand side is an action.  If
		 it is, it won't be on the stack, so we don't want to count it 
		 in the rightlen.  Then when we search down the stack for a
		 Symbol, we don't try to search past action */

		if (rhs_l > 0) {
			if (rhs_parts[rhs_l - 1].is_action()) {
				rightlen = rhs_l - 1;
			}
			else {
				rightlen = rhs_l;
			}
		}

		/* get the generated declaration code for the necessary labels. */
		declare_str = this.declare_labels(rhs_parts, rightlen, action_str);

		if (action_str == null) {
			action_str = declare_str;
		}
		else {
			action_str = declare_str + action_str;
		}

		/* count use of lhs */
		lhs_sym.note_use();

		/* create the part for left-hand-side */
		this._lhs = new symbol_part(lhs_sym);

		/* merge adjacent actions (if any) */
		this._rhs_length =
			this.merge_adjacent_actions(rhs_parts, this._rhs_length);

		/* strip off any trailing action */
		tail_action = this.strip_trailing_action(rhs_parts, this._rhs_length);
		if (tail_action != null) {
			this._rhs_length--;
		}

		/* Why does this run through the right hand side happen
		 over and over?  here a quick combination of two 
		 prior runs plus one I wanted of my own
		 frankf 6/25/96 */
		/* allocate and copy over the right-hand-side */
		/* count use of each rhs symbol */
		this._rhs = new production_part[this._rhs_length];
		for (i = 0; i < this._rhs_length; i++) {
			this._rhs[i] = rhs_parts[i];
			if (!this._rhs[i].is_action()) {
				((symbol_part) this._rhs[i]).the_symbol().note_use();
				if (((symbol_part) this._rhs[i]).the_symbol() instanceof terminal) {
					this._rhs_prec =
						((terminal) ((symbol_part) this._rhs[i]).the_symbol())
							.precedence_num();
					this._rhs_assoc =
						((terminal) ((symbol_part) this._rhs[i]).the_symbol())
							.precedence_side();
				}
			}
		}

		/*now action string is really declaration string, so put it in front!
		 6/14/96 frankf */
		if (action_str == null) {
			action_str = "";
		}
		if (tail_action != null && tail_action.code_string() != null) {
			action_str = action_str + "\t\t" + tail_action.code_string();
		}

		/* stash the action */
		this._action = new action_part(action_str);

		/* rewrite production to remove any embedded actions */
		this.remove_embedded_actions();

		/* assign an index */
		this._index = production.next_index++;

		/* put us in the global collection of productions */
		production._all.put(Integer.valueOf(this._index), this);

		/* put us in the production list of the lhs non terminal */
		lhs_sym.add_production(this);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/* Constructor with precedence and associativity of production
	 contextually define */
	public production(
		final non_terminal lhs_sym,
		final production_part rhs_parts[],
		final int rhs_l,
		final String action_str,
		final int prec_num,
		final int prec_side) throws internal_error {
		this(lhs_sym, rhs_parts, rhs_l, action_str);

		/* set the precedence */
		this.set_precedence_num(prec_num);
		this.set_precedence_side(prec_side);
	}

	/** An action_part containing code for the action to be performed when we 
	 *  reduce with this production. 
	 */
	public action_part action() {
		return this._action;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Update (and return) the first set based on current NT firsts. 
	 *  This assumes that nullability has already been computed for all non 
	 *  terminals and productions. 
	 */
	public terminal_set check_first_set() throws internal_error {
		int part;
		symbol sym;

		/* walk down the right hand side till we get past all nullables */
		for (part = 0; part < this.rhs_length(); part++) {
			/* only look at non-actions */
			if (!this.rhs(part).is_action()) {
				sym = ((symbol_part) this.rhs(part)).the_symbol();

				/* is it a non-terminal?*/
				if (sym.is_non_term()) {
					/* add in current firsts from that NT */
					this._first_set.add(((non_terminal) sym).first_set());

					/* if its not nullable, we are done */
					if (!((non_terminal) sym).nullable()) {
						break;
					}
				}
				else {
					/* its a terminal -- add that to the set */
					this._first_set.add((terminal) sym);

					/* we are done */
					break;
				}
			}
		}

		/* return our updated first set */
		return this.first_set();
	}

	/** Check to see if the production (now) appears to be nullable.
	 *  A production is nullable if its RHS could derive the empty string.
	 *  This results when the RHS is empty or contains only non terminals
	 *  which themselves are nullable.
	 */
	public boolean check_nullable() throws internal_error {
		production_part part;
		symbol sym;
		int pos;

		/* if we already know bail out early */
		if (this.nullable_known()) {
			return this.nullable();
		}

		/* if we have a zero size RHS we are directly nullable */
		if (this.rhs_length() == 0) {
			/* stash and return the result */
			return this.set_nullable(true);
		}

		/* otherwise we need to test all of our parts */
		for (pos = 0; pos < this.rhs_length(); pos++) {
			part = this.rhs(pos);

			/* only look at non-actions */
			if (!part.is_action()) {
				sym = ((symbol_part) part).the_symbol();

				/* if its a terminal we are definitely not nullable */
				if (!sym.is_non_term()) {
					return this.set_nullable(false);
				}
				else if (!((non_terminal) sym).nullable()) {
					/* this one not (yet) nullable, so we aren't */
					return false;
				}
			}
		}

		/* if we make it here all parts are nullable */
		return this.set_nullable(true);
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Declare label names as valid variables within the action string
	 * @param rhs          array of RHS parts.
	 * @param rhs_len      how much of rhs to consider valid.
	 * @param final_action the final action string of the production. 
	 * @param lhs_type     the object type associated with the LHS symbol.
	 */
	protected String declare_labels(
		final production_part rhs[],
		final int rhs_len,
		final String final_action) {
		String declaration = "";

		symbol_part part;
		int pos;

		/* walk down the parts and extract the labels */
		for (pos = 0; pos < rhs_len; pos++) {
			if (!rhs[pos].is_action()) {
				part = (symbol_part) rhs[pos];

				/* if it has a label, make declaration! */
				if (part.label() != null) {
					declaration =
						declaration
								+ this.make_declaration(part.label(), part
									.the_symbol()
									.stack_type(), rhs_len - pos - 1);
				}
			}
		}
		return declaration;
	}

	/** Generic equality comparison. */
	public boolean equals(final Object other) {
		if (!(other instanceof production)) {
			return false;
		}
		else {
			return this.equals((production) other);
		}
	}

	/** Equality comparison. */
	public boolean equals(final production other) {
		if (other == null) {
			return false;
		}
		return other._index == this._index;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** First set of the production.  This is the set of terminals that 
	 *  could appear at the front of some string derived from this production.
	 */
	public terminal_set first_set() {
		return this._first_set;
	}

	/** Produce a hash code. */
	public int hashCode() {
		/* just use a simple function of the index */
		return this._index * 13;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Index number of the production. */
	public int index() {
		return this._index;
	}

	/** The left hand side non-terminal. */
	public symbol_part lhs() {
		return this._lhs;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Return label declaration code
	 * @param labelname    the label name
	 * @param stack_type   the stack type of label?
	 * @author frankf
	 */
	protected String make_declaration(
		final String labelname,
		final String stack_type,
		final int offset) {
		String ret;

		/* Put in the left/right value labels */
		if (emit.lr_values()) {
			ret =
				"\t\tint " + labelname + "left = ((java_cup.runtime.Symbol)"
						+ emit.pre("stack") + ".elementAt(" + emit.pre("top")
						+ "-" + offset + ")).left;\n" + "\t\tint " + labelname
						+ "right = ((java_cup.runtime.Symbol)"
						+ emit.pre("stack") + ".elementAt(" + emit.pre("top")
						+ "-" + offset + ")).right;\n";
		}
		else {
			ret = "";
		}

		/* otherwise, just declare label. */
		return ret + "\t\t" + stack_type + " " + labelname + " = ("
				+ stack_type + ")((" + "java_cup.runtime.Symbol) "
				+ emit.pre("stack") + ".elementAt(" + emit.pre("top") + "-"
				+ offset + ")).value;\n";

	}
	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Helper routine to merge adjacent actions in a set of RHS parts 
	 * @param rhs_parts array of RHS parts.
	 * @param len       amount of that array that is valid.
	 * @return          remaining valid length.
	 */
	protected int merge_adjacent_actions(
		final production_part rhs_parts[],
		final int len) {
		int from_loc, to_loc, merge_cnt;

		/* bail out early if we have no work to do */
		if (rhs_parts == null || len == 0) {
			return 0;
		}

		merge_cnt = 0;
		to_loc = -1;
		for (from_loc = 0; from_loc < len; from_loc++) {
			/* do we go in the current position or one further */
			if (to_loc < 0 || !rhs_parts[to_loc].is_action()
					|| !rhs_parts[from_loc].is_action()) {
				/* next one */
				to_loc++;

				/* clear the way for it */
				if (to_loc != from_loc) {
					rhs_parts[to_loc] = null;
				}
			}

			/* if this is not trivial? */
			if (to_loc != from_loc) {
				/* do we merge or copy? */
				if (rhs_parts[to_loc] != null && rhs_parts[to_loc].is_action()
						&& rhs_parts[from_loc].is_action()) {
					/* merge */
					rhs_parts[to_loc] =
						new action_part(
							((action_part) rhs_parts[to_loc]).code_string()
									+ ((action_part) rhs_parts[from_loc])
										.code_string());
					merge_cnt++;
				}
				else {
					/* copy */
					rhs_parts[to_loc] = rhs_parts[from_loc];
				}
			}
		}

		/* return the used length */
		return len - merge_cnt;
	}

	/*-----------------------------------------------------------*/
	/*--- Static Methods ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Increment the count of reductions with this non-terminal */
	public void note_reduction_use() {
		this._num_reductions++;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Nullability of the production (can it derive the empty string). */
	public boolean nullable() {
		return this._nullable;
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Is the nullability of the production known or unknown? */
	public boolean nullable_known() {
		return this._nullable_known;
	}

	/** Count of number of reductions using this production. */
	public int num_reductions() {
		return this._num_reductions;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Access to the precedence of the rule */
	public int precedence_num() {
		return this._rhs_prec;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	public int precedence_side() {
		return this._rhs_assoc;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Remove all embedded actions from a production by factoring them 
	 *  out into individual action production using new non terminals.
	 *  if the original production was:  <pre>
	 *    A ::= B {action1} C {action2} D 
	 *  </pre>
	 *  then it will be factored into: <pre>
	 *    A ::= B NT$1 C NT$2 D
	 *    NT$1 ::= {action1}
	 *    NT$2 ::= {action2}
	 *  </pre>
	 *  where NT$1 and NT$2 are new system created non terminals.
	 */

	/* the declarations added to the parent production are also passed along,
	 as they should be perfectly valid in this code string, since it
	 was originally a code string in the parent, not on its own.
	 frank 6/20/96 */
	protected void remove_embedded_actions(

	) throws internal_error {
		non_terminal new_nt;
		String declare_str;

		/* walk over the production and process each action */
		for (int act_loc = 0; act_loc < this.rhs_length(); act_loc++) {
			if (this.rhs(act_loc).is_action()) {

				declare_str = this.declare_labels(this._rhs, act_loc, "");
				/* create a new non terminal for the action production */
				new_nt = non_terminal.create_new();
				new_nt.is_embedded_action = true; /* 24-Mar-1998, CSA */

				new action_production(this, new_nt, null, 0, declare_str
						+ ((action_part) this.rhs(act_loc)).code_string());

				/* replace the action with the generated non terminal */
				this._rhs[act_loc] = new symbol_part(new_nt);
			}
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Access to the collection of parts for the right hand side. */
	public production_part rhs(final int indx) throws internal_error {
		if (indx >= 0 && indx < this._rhs_length) {
			return this._rhs[indx];
		}
		else {
			throw new internal_error(
				"Index out of range for right hand side of production");
		}
	}

	/** How much of the right hand side array we are presently using. */
	public int rhs_length() {
		return this._rhs_length;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** set (and return) nullability */
	boolean set_nullable(final boolean v) {
		this._nullable_known = true;
		this._nullable = v;
		return v;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Setting the precedence of a rule */
	public void set_precedence_num(final int prec_num) {
		this._rhs_prec = prec_num;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	public void set_precedence_side(final int prec_side) {
		this._rhs_assoc = prec_side;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Helper routine to strip a trailing action off rhs and return it
	 * @param rhs_parts array of RHS parts.
	 * @param len       how many of those are valid.
	 * @return          the removed action part.
	 */
	protected action_part strip_trailing_action(
		final production_part rhs_parts[],
		final int len) {
		action_part result;

		/* bail out early if we have nothing to do */
		if (rhs_parts == null || len == 0) {
			return null;
		}

		/* see if we have a trailing action */
		if (rhs_parts[len - 1].is_action()) {
			/* snip it out and return it */
			result = (action_part) rhs_parts[len - 1];
			rhs_parts[len - 1] = null;
			return result;
		}
		else {
			return null;
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a simpler string. */
	public String to_simple_string() throws internal_error {
		String result;

		result =
			this.lhs() != null ? this.lhs().the_symbol().name() : "NULL_LHS";
		result += " ::= ";
		for (int i = 0; i < this.rhs_length(); i++) {
			if (!this.rhs(i).is_action()) {
				result += ((symbol_part) this.rhs(i)).the_symbol().name() + " ";
			}
		}

		return result;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Convert to a string. */
	public String toString() {
		String result;

		/* catch any internal errors */
		try {
			result = "production [" + this.index() + "]: ";
			result +=
				this.lhs() != null ? this.lhs().toString() : "$$NULL-LHS$$";
			result += " :: = ";
			for (int i = 0; i < this.rhs_length(); i++) {
				result += this.rhs(i) + " ";
			}
			result += ";";
			if (this.action() != null && this.action().code_string() != null) {
				result += " {" + this.action().code_string() + "}";
			}

			if (this.nullable_known()) {
				if (this.nullable()) {
					result += "[NULLABLE]";
				}
				else {
					result += "[NOT NULLABLE]";
				}
			}
		}
		catch (final internal_error e) {
			/* crash on internal error since we can't throw it from here (because
			 superclass does not throw anything. */
			e.crash();
			result = null;
		}

		return result;
	}

	/*-----------------------------------------------------------*/

}
