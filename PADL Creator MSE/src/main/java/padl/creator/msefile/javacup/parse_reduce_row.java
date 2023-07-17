package padl.creator.msefile.javacup;

/** This class represents one row (corresponding to one machine state) of the 
 *  reduce-goto parse table. 
 */
public class parse_reduce_row {
	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Number of columns (non terminals) in every row. */
	protected static int _size = 0;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Static (Class) Variables ------------------*/
	/*-----------------------------------------------------------*/

	/** Number of columns (non terminals) in every row. */
	public static int size() {
		return parse_reduce_row._size;
	}

	/** Actual entries for the row. */
	public lalr_state under_non_term[];

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Simple constructor. Note: this should not be used until the number
	 *  of terminals in the grammar has been established.
	 */
	public parse_reduce_row() {
		/* make sure the size is set */
		if (parse_reduce_row._size <= 0) {
			parse_reduce_row._size = non_terminal.number();
		}

		/* allocate the array */
		this.under_non_term = new lalr_state[parse_reduce_row.size()];
	}
}
