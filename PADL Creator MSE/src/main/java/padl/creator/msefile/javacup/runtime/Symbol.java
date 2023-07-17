package padl.creator.msefile.javacup.runtime;

/**
 * Defines the Symbol class, which is used to represent all terminals
 * and nonterminals while parsing.  The lexer should pass CUP Symbols 
 * and CUP returns a Symbol.
 *
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */

/* ****************************************************************
  Class Symbol
  what the parser expects to receive from the lexer. 
  the token is identified as follows:
  sym:    the symbol type
  parse_state: the parse state.
  value:  is the lexical value of type Object
  left :  is the left position in the original input file
  right:  is the right position in the original input file
******************************************************************/

public class Symbol {

	/** The symbol number of the terminal or non terminal being represented */
	public int sym;

	/** The parse state to be recorded on the parse stack with this symbol.
	   *  This field is for the convenience of the parser and shouldn't be 
	   *  modified except by the parser. 
	   */
	public int parse_state;

	/** This allows us to catch some errors caused by scanners recycling
	   *  symbols.  For the use of the parser only. [CSA, 23-Jul-1999] */
	boolean used_by_parser = false;

	/*******************************
	  The data passed to parser
	 *******************************/

	public int left, right;

	public Object value;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/***********************************
	Constructor for no value or l,r
	***********************************/

	public Symbol(final int sym_num) {
		this(sym_num, -1);
		this.left = -1;
		this.right = -1;
		this.value = null;
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/***********************************
	Constructor to give a start state
	***********************************/
	Symbol(final int sym_num, final int state) {
		this.sym = sym_num;
		this.parse_state = state;
	}
	/*****************************
	Constructor for no value
	***************************/

	public Symbol(final int id, final int l, final int r) {
		this(id, l, r, null);
	}

	/*******************************
	  Constructor for l,r values
	 *******************************/

	public Symbol(final int id, final int l, final int r, final Object o) {
		this(id);
		this.left = l;
		this.right = r;
		this.value = o;
	}
	/*******************************
	Constructor for no l,r values
	********************************/

	public Symbol(final int id, final Object o) {
		this(id, -1, -1, o);
	}

	/*****************************
	  Printing this token out. (Override for pretty-print).
	  ****************************/
	public String toString() {
		return "#" + this.sym;
	}
}
