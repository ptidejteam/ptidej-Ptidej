// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IntVar.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:16 $
// Last Contributor:   $Author: guehene $

package choco.integer;

import choco.ContradictionException;
import choco.Problem;
import choco.Var;
import choco.integer.var.IntDomain;

/**
 * An interface for all implementations of search valued domain variables
 */
public interface IntVar extends Var, IntExp {

	/** <b>Public user API:</b>
	 *   static constants associated to the encoding of the variable domain
	 *   these constants are passed as parameters to the constructor of IntVars
	 *    LIST = a chained list of values
	 */
	public static int LIST = 0;

	/** <b>Public user API:</b>
	 *   static constants associated to the encoding of the variable domain
	 *   these constants are passed as parameters to the constructor of IntVars
	 *    BOUNDS = an interval (keeping the lower and upper bounds)
	 */
	public static int BOUNDS = 1;

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> testing whether two variables have intersecting domains.
	 *    @param x   the other variable
	 */

	public boolean canBeEqualTo(IntVar x);

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> testing whether one value among a list is present in
	 *    the domain.
	 *    @param sortedValList   the list of values. Must be sorted in increasing order.
	 *    @param nVals           the size of the list of values
	 */

	public boolean canBeInstantiatedIn(int[] sortedValList, int nVals);

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> testing whether a value is in the domain.
	 *    @param x   the tested value
	 */

	public boolean canBeInstantiatedTo(int x);

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> returns the object responsible for storing the enumeration of values in the domain
	 */

	public IntDomain getDomain();

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> retrieves the number of values in the domain.
	 */

	public int getDomainSize();

	/**
	 * Returns the lower bound of the variable domain (e.g. the smallest value that the variable can be assigned).
	 * @return the domain lower bound
	 */

	public int getInf();

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> retrieves the value immediately (but strictly) after
	 *    <i>i</i> in the domain
	 *    @param i   the pivot value. May or may not be in the domain
	 */

	public int getNextDomainValue(int i);

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> retrieves the value immediately (but strictly) before
	 *    <i>i</i> in the domain.
	 *    @param i   the pivot value. May or may not be in the domain
	 */

	public int getPrevDomainValue(int i);

	/**
	 * <b>Public user API:</b>
	 *    <i>Util :</i> retrieves the problem to which the variable belongs.
	 */

	public Problem getProblem();

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> retrieves a value drawn at random (uniform distribution)
	 *    from the domain.
	 */

	public int getRandomDomainValue();

	/**
	 * Returns the upper bound of the variable domain (e.g. the greatest value that the variable can be assigned).
	 * @return the domain upper bound
	 */

	public int getSup();

	/**
	 * Returns the value of the variable if instantiated.
	 * @return the value of the variable
	 */

	public int getValue();

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> whether an enumeration of values (in addition to the enclosing interval) is stored
	 */

	public boolean hasEnumeratedDomain();

	/**
	 *    <i>Propagation events</i> instantiating a variable
	 *  (ie: removing all other values from the domain)
	 * @param x the value of the variable
	 * @param idx the index of the constraint that generated the var
	 * @return a boolean indicating whether this method call added new information or not
	 */

	public boolean instantiate(int x, int idx) throws ContradictionException;

	/**
	 * <b>Public user API:</b>
	 *    <i>Domains :</i> testing whether the value of an instantiated variable
	 *    is equal to a specific value.
	 */
	public boolean isInstantiatedTo(int x);

	/**
	 *    <i>Propagation events</i> updating the domain of a variable
	 *    (by removing an interval, ie, a sequence of consecutive values)
	 * @param a the lower bound of the forbidden interval
	 * @param b the upper bound of the forbidden interval
	 * @param idx the index of the constraint that generated the var
	 * @return a boolean indicating whether this method call added new information or not
	 */

	public boolean removeInterval(int a, int b, int idx)
			throws ContradictionException;

	/**
	 *    <i>Propagation events</i> updating the domain of a variable (by removing a value)
	 * @param x the value that is not in the domain
	 * @param idx the index of the constraint that generated the var
	 * @return a boolean indicating whether this method call added new information or not
	 */

	public boolean removeVal(int x, int idx) throws ContradictionException;

	/**
	 * <b>Public user API:</b>
	 *    <i>Propagation events</i> removing a value from the domain of a variable.
	 *    @param x   the removed value
	 */

	public void remVal(int x) throws ContradictionException;

	/**
	 * <b>Public user API:</b>
	 *    <i>Propagation events</i> updating the upper bound of a variable
	 *   (ie: removing all value strictly above the new upper bound from the domain).
	 *    @param x   the new upper bound
	 */

	public void setMax(int x) throws ContradictionException;

	/**
	 * <b>Public user API:</b>
	 *    <i>Propagation events</i> updating the lower bound of a variable
	 *    (ie: removing all value strictly below the new lower bound from the domain).
	 *    @param x   the new lower bound
	 */

	public void setMin(int x) throws ContradictionException;

	/**
	 * <b>Public user API:</b>
	 *    <i>Propagation events</i> assigning a value to a variable
	 *    (ie: removing all other values from its domain).
	 *    @param x   the value that is assigned to the variable
	 */

	public void setVal(int x) throws ContradictionException;

	/**
	 * <i>Propagation events</i> updating the lower bound of a variable
	 * (ie: removing all value strictly below the new lower bound from the domain).
	 * @param x a lower bound of the domain (the new one, if better than the one currently stored)
	 * @param idx the index of the constraint that generated the var
	 * @return a boolean indicating whether this method call added new information or not
	 */

	public boolean updateInf(int x, int idx) throws ContradictionException;

	/**
	 *    <i>Propagation events</i> updating the upper bound of a variable
	 *   (ie: removing all value strictly above the new upper bound from the domain).
	 * @param x an upper bound of the domain (the new one, if better than the one currently stored)
	 * @param idx the index of the constraint that generated the var
	 * @return a boolean indicating whether this method call added new information or not
	 */

	public boolean updateSup(int x, int idx) throws ContradictionException;

	/**
	 * <b>Public user API:</b>
	 *    <i>Propagation events</i> wiping out the domain of the variable (removing all values)
	 *    and throwing a contradiction
	 */

	public void wipeOut() throws ContradictionException;

}
