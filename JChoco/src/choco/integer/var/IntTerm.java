// **************************************************
// *                   J-CHOCO                      *
// *   Copyright (C) F. Laburthe, 1999-2003         *
// **************************************************
// *  an open-source Constraint Programming Kernel  *
// *     for Research and Education                 *
// **************************************************

/* File choco.search.IntTerm.java, last modified by Francois 21 sept. 2003 23:06:13 */
package choco.integer.var;

import choco.integer.IntExp;

/**
 * Implements linear terms: Sigma_i(a_i*X_i), where a_i are search coefficients,
 *  and X_i are search domain variable
 */
public class IntTerm implements IntExp {

	/**
	 * The coefficients
	 */
	protected final int[] coefficients;

	/**
	 * The variables
	 */
	protected final IntDomainVar[] variables;

	/**
	 * number of variables involved in the term
	 */
	protected int nbVars;

	/**
	 * the integer constant involved in the term
	 */
	protected int constant;

	/**
	 * Constructor
	 * @param capacity number of variables that will be involved in the term
	 */
	public IntTerm(final int capacity) {
		this.coefficients = new int[capacity];
		this.variables = new IntDomainVar[capacity];
		this.nbVars = capacity;
		this.constant = 0;
	}

	/**
	 * Constructor by copy
	 * @param t1 the IntTerm to be copied
	 */
	public IntTerm(final IntTerm t1) {
		final int capacity = t1.getSize();
		this.coefficients = new int[capacity];
		this.variables = new IntDomainVar[capacity];
		this.nbVars = capacity;
		for (int i = 0; i < capacity; i++) {
			this.coefficients[i] = t1.getCoefficient(i);
			this.variables[i] = t1.getVariable(i);
		}
		this.constant = t1.constant;
	}

	/** retrieve the i-th coefficient
	 * @param index the index of the variable/coefficient in the expression
	 * @return the coefficient
	 */
	public int getCoefficient(final int index) {
		return this.coefficients[index];
	}

	/** retrieve the array of coefficients
	 * @return the integer coefficients that are involved in the term
	 */
	public int[] getCoefficients() {
		return this.coefficients;
	}

	/**
	 * returns the integer constant involved in the linear term
	 * @return the value of the integer constant
	 */
	public int getConstant() {
		return this.constant;
	}

	/** returns the term capacity
	 * @return the capacity that has been reserved for storing coefficients and varibales
	 */
	public int getSize() {
		return this.nbVars;
	}

	/** retrieve the i-th variable
	 * @param index the index of the variable/coefficient in the expression
	 * @return the coefficient
	 */
	public IntDomainVar getVariable(final int index) {
		return this.variables[index];
	}

	/** retrieve the array of variables
	 * @return the variables that are involved in the term
	 */
	public IntDomainVar[] getVariables() {
		return this.variables;
	}

	/** sets the i-th coefficient
	 * @param index the index of the variable/coefficient in the expression
	 * @param coef the coefficient
	 */
	public void setCoefficient(final int index, final int coef) {
		this.coefficients[index] = coef;
	}

	/**
	* sets the integer constant involved in the linear term
	* @param constant the target value
	*/
	public void setConstant(final int constant) {
		this.constant = constant;
	}

	/** sets the i-th variable
	   * @param index the index of the variable/coefficient in the expression
	   * @param var the variable
	   */
	public void setVariable(final int index, final IntDomainVar var) {
		this.variables[index] = var;
	}
}
