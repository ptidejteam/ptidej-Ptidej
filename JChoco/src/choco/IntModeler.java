// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.IntModeler.java, last modified by Francois 21 sept. 2003 11:18:46 */
package choco;

import choco.integer.IntVar;

/** An interface for all methods related modelling constraint programs over search domains
 *
 */
public interface IntModeler {

	/** Creates a constraint by stating that a term is equal than a constant
	 * @param x the first search expression
	 * @param y the second search expression
	 * @return the linear equality constraint
	 */
	public Constraint eq(choco.integer.IntExp x, choco.integer.IntExp y);

	/** Creates a constraint by stating that a term is equal than a constant
	 * @param x the search expression
	 * @param c the search constant
	 * @return the linear equality constraint
	 */
	public Constraint eq(choco.integer.IntExp x, int c);

	/** Creates a constraint by stating that a term is equal than a constant
	 * @param x the search expression
	 * @param c the search constant
	 * @return the linear equality constraint
	 */
	public Constraint eq(int c, choco.integer.IntExp x);

	/** Creates a constraint by stating that a variable is greater or equal than a constant
	 * @param x the first search expression
	 * @param y the second search expression
	 * @return the linear equality constraint
	 */
	public Constraint geq(choco.integer.IntExp x, choco.integer.IntExp y);

	/** Creates a constraint by stating that a variable is greater or equal than a constant
	 * @param x the search expression
	 * @param c the search constant
	 * @return the linear equality constraint
	 */
	public Constraint geq(choco.integer.IntExp x, int c);

	/** Creates a constraint by stating that a variable is greater or equal than a constant
	 * @param x the search expression
	 * @param c the search constant
	 * @return the linear equality constraint
	 */
	public Constraint geq(int c, choco.integer.IntExp x);

	/** Creates a constraint by stating that a variable is strictly than a constant
	 * @param x the first search expression
	 * @param y the second search expression
	 * @return the linear equality constraint
	 */
	public Constraint gt(choco.integer.IntExp x, choco.integer.IntExp y);

	/** Creates a constraint by stating that a variable is strictly than a constant
	 * @param x the search expression
	 * @param c the search constant
	 * @return the linear equality constraint
	 */
	public Constraint gt(choco.integer.IntExp x, int c);

	/** Creates a constraint by stating that a variable is strictly than a constant
	 * @param x the search expression
	 * @param c the search constant
	 * @return the linear equality constraint
	 */
	public Constraint gt(int c, choco.integer.IntExp x);

	/** Creates a constraint by stating that an integer expression is less or equal than another one
	 * @param x the first search expression
	 * @param y the second search expression
	 * @return the linear equality constraint
	 */
	public Constraint leq(choco.integer.IntExp x, choco.integer.IntExp y);

	/** Creates a constraint by stating that integer expression is less or equal than a constant
	 * @param x the search expression
	 * @param c the search constant
	 * @return the linear equality constraint
	 */
	public Constraint leq(choco.integer.IntExp x, int c);

	/** Creates a constraint by stating that a constant is less or equal than an integer expression
	 * @param x the search expression
	 * @param c the search constant
	 * @return the linear equality constraint
	 */
	public Constraint leq(int c, choco.integer.IntExp x);

	/** Creates a constraint by stating that an integer expression is strictly less than another one
	* @param x the first search expression
	* @param y the second search expression
	* @return the linear equality constraint
	*/
	public Constraint lt(choco.integer.IntExp x, choco.integer.IntExp y);

	/** Creates a constraint by stating that integer expression is strictly less than a constant
	   * @param x the search expression
	   * @param c the search constant
	   * @return the linear equality constraint
	   */
	public Constraint lt(choco.integer.IntExp x, int c);

	/** Creates a constraint by stating that a constant is strictly less than an integer expression
	* @param x the search expression
	* @param c the search constant
	* @return the linear equality constraint
	*/
	public Constraint lt(int c, choco.integer.IntExp x);

	/** Creates a new search variable with an interval domain
	 * @param name the name of the variable
	 * @param min minimal allowed value (included in the domain)
	 * @param max maximal allowed value (included in the domain)
	 * @return the variable
	 */
	public IntVar makeBoundIntVar(String name, int min, int max);

	/**
	 * Creates a one dimensional array of integer variables
	 * @param name the name of the array (a prefix shared by all individual IntVars)
	 * @param dim  the number of entries
	 * @param min the minimal domain value for all variables in the array
	 * @param max the maximal domain value for all variables in the array
	 */
	public IntVar[] makeBoundIntVarArray(String name, int dim, int min, int max);

	/**
	 * Creates a one dimensional array of integer variables
	 * @param name the name of the array (a prefix shared by all individual IntVars)
	 * @param dim1  the number of entries for the first index
	 * @param dim2  the number of entries for the second index
	 * @param min the minimal domain value for all variables in the array
	 * @param max the maximal domain value for all variables in the array
	 */
	public IntVar[][] makeBoundIntVarArray(
		String name,
		int dim1,
		int dim2,
		int min,
		int max);

	/** Creates a new search variable with an enumerated domain
	 * @param name the name of the variable
	 * @param min minimal allowed value (included in the domain)
	 * @param max maximal allowed value (included in the domain)
	 * @return the variable
	 */
	public IntVar makeEnumIntVar(String name, int min, int max);

	/** Subtracting two search expressions one from another
	 * @param t1 first expression
	 * @param t2 second expression
	 * @return the resulting expression (a fresh one)
	 */
	public choco.integer.IntExp minus(
		choco.integer.IntExp t1,
		choco.integer.IntExp t2);

	/** Subtracting an search constant from an expression
	 * @param t1 the expression
	 * @param c the search constant
	 * @return the resulting expression (a fresh one)
	 */
	public choco.integer.IntExp minus(choco.integer.IntExp t1, int c);

	/** Subtracting an expression from an search
	 * @param t1 the expression
	 * @param c the search constant
	 * @return the resulting expression (a fresh one)
	 */
	public choco.integer.IntExp minus(int c, choco.integer.IntExp t1);

	/** Creates a simple linear term from one coefficient and one variable
	 * @param a the coefficient
	 * @param x the variable
	 * @return the term
	 */
	public choco.integer.IntExp mult(int a, choco.integer.IntExp x);

	/** Creates a constraint by stating that two term are different
	 * @param x the first variable
	 * @param y the second variale
	 * @return the linear disequality constraint
	 */
	public Constraint neq(choco.integer.IntExp x, choco.integer.IntExp y);

	/** Creates a constraint by stating that a term is not equal than a constant
	   * @param x the search expression
	   * @param c the search constant
	   * @return the linear disequality constraint
	   */
	public Constraint neq(choco.integer.IntExp x, int c);

	/** Creates a constraint by stating that a term is not equal than a constant
	   * @param x the search expression
	   * @param c the search constant
	   * @return the linear disequality constraint
	   */
	public Constraint neq(int c, choco.integer.IntExp x);

	/** Adding two search expressions one to another
	 * @param t1 first expression
	 * @param t2 second expression
	 * @return the resulting expression (a fresh one)
	 */
	public choco.integer.IntExp plus(
		choco.integer.IntExp t1,
		choco.integer.IntExp t2);

	/** Adding an search constant to an expression
	 * @param t1 the expression
	 * @param c the search constant
	 * @return the resulting expression (a fresh one)
	 */
	public choco.integer.IntExp plus(choco.integer.IntExp t1, int c);

	/** Adding an search constant to an expression
	 * @param t1 the expression
	 * @param c the search constant
	 * @return the resulting expression (a fresh one)
	 */
	public choco.integer.IntExp plus(int c, choco.integer.IntExp t1);

	/** Building a term from a scalar product of coefficients and variables
	 * @param lc the array of coefficients
	 * @param lv the array of variables
	 * @return the term
	 */
	public choco.integer.IntExp scalar(choco.integer.IntVar[] lv, int[] lc);

	/** Building a term from a scalar product of coefficients and variables
	 * @param lc the array of coefficients
	 * @param lv the array of variables
	 * @return the term
	 */
	public choco.integer.IntExp scalar(int[] lc, choco.integer.IntVar[] lv);

	/**
	 * sets the optimization mode to maximization and sets the objective function
	 * @param obj the variable to be maximized
	 */
	public void setMaximizationObjective(choco.integer.IntVar obj);

	/**
	 * sets the optimization mode to minimization and sets the objective function
	 * @param obj the variable to be minimized
	 */
	public void setMinimizationObjective(choco.integer.IntVar obj);

	/** Building a term from a sum of integer expressions
	 * @param lv the array of integer expressions
	 * @return the term
	 */
	public choco.integer.IntExp sum(choco.integer.IntExp[] lv);
}
