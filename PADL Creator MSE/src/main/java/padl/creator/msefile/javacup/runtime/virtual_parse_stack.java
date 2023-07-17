package padl.creator.msefile.javacup.runtime;

import java.util.Stack;

/** This class implements a temporary or "virtual" parse stack that 
 *  replaces the top portion of the actual parse stack (the part that 
 *  has been changed by some set of operations) while maintaining its
 *  original contents.  This data structure is used when the parse needs 
 *  to "parse ahead" to determine if a given error recovery attempt will 
 *  allow the parse to continue far enough to consider it successful.  Once 
 *  success or failure of parse ahead is determined the system then 
 *  reverts to the original parse stack (which has not actually been 
 *  modified).  Since parse ahead does not execute actions, only parse
 *  state is maintained on the virtual stack, not full Symbol objects.
 *
 * @see     java_cup.runtime.lr_parser
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */

public class virtual_parse_stack {
	/*-----------------------------------------------------------*/
	/*--- Constructor(s) ----------------------------------------*/
	/*-----------------------------------------------------------*/

	/** The real stack that we shadow.  This is accessed when we move off
	 *  the bottom of the virtual portion of the stack, but is always left
	 *  unmodified.
	 */
	protected Stack real_stack;

	/*-----------------------------------------------------------*/
	/*--- (Access to) Instance Variables ------------------------*/
	/*-----------------------------------------------------------*/

	/** Top of stack indicator for where we leave off in the real stack.
	 *  This is measured from top of stack, so 0 would indicate that no
	 *  elements have been "moved" from the real to virtual stack. 
	 */
	protected int real_next;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** The virtual top portion of the stack.  This stack contains Integer
	 *  objects with state numbers.  This stack shadows the top portion
	 *  of the real stack within the area that has been modified (via operations
	 *  on the virtual stack).  When this portion of the stack becomes empty we 
	 *  transfer elements from the underlying stack onto this stack. 
	 */
	protected Stack vstack;

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Constructor to build a virtual stack out of a real stack. */
	public virtual_parse_stack(final Stack shadowing_stack)
			throws java.lang.Exception {
		/* sanity check */
		if (shadowing_stack == null) {
			throw new Exception(
				"Internal parser error: attempt to create null virtual stack");
		}

		/* set up our internals */
		this.real_stack = shadowing_stack;
		this.vstack = new Stack();
		this.real_next = 0;

		/* get one element onto the virtual portion of the stack */
		this.get_from_real();
	}

	/*-----------------------------------------------------------*/
	/*--- General Methods ---------------------------------------*/
	/*-----------------------------------------------------------*/

	/** Indicate whether the stack is empty. */
	public boolean empty() {
		/* if vstack is empty then we were unable to transfer onto it and 
		the whole thing is empty. */
		return this.vstack.empty();
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Transfer an element from the real to the virtual stack.  This assumes 
	 *  that the virtual stack is currently empty.  
	 */
	protected void get_from_real() {
		Symbol stack_sym;

		/* don't transfer if the real stack is empty */
		if (this.real_next >= this.real_stack.size()) {
			return;
		}

		/* get a copy of the first Symbol we have not transfered */
		stack_sym =
			(Symbol) this.real_stack.elementAt(this.real_stack.size() - 1
					- this.real_next);

		/* record the transfer */
		this.real_next++;

		/* put the state number from the Symbol onto the virtual stack */
		this.vstack.push(Integer.valueOf(stack_sym.parse_state));
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Pop the stack. */
	public void pop() throws java.lang.Exception {
		if (this.vstack.empty()) {
			throw new Exception(
				"Internal parser error: pop from empty virtual stack");
		}

		/* pop it */
		this.vstack.pop();

		/* if we are now empty transfer an element (if there is one) */
		if (this.vstack.empty()) {
			this.get_from_real();
		}
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Push a state number onto the stack. */
	public void push(final int state_num) {
		this.vstack.push(Integer.valueOf(state_num));
	}

	/*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

	/** Return value on the top of the stack (without popping it). */
	public int top() throws java.lang.Exception {
		if (this.vstack.empty()) {
			throw new Exception(
				"Internal parser error: top() called on empty virtual stack");
		}

		return ((Integer) this.vstack.peek()).intValue();
	}

	/*-----------------------------------------------------------*/

}
