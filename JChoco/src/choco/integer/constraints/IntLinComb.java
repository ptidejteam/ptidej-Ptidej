// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IntLinComb.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import org.junit.Assert;
import choco.AbstractConstraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.IntExp;
import choco.util.Arithm;

// import choco.search.AbstractLargeIntConstraint;

/**
 * Implements a constraint Sigma (ai Xi) <=/>=/= C, with Xi variables, ai and C search constants.
 */
public class IntLinComb extends AbstractLargeIntConstraint {

	/**
	 * Constant, to be assigned to <code>op</code>, representing linear equalities
	 */
	public static final int EQ = 0;

	/**
	 * Constant, to be assigned to <code>op</code>, representing linear inequalities
	 */
	public static final int GEQ = 1;

	/**
	 * Constant, to be assigned to <code>op</code>, representing linear disequalities
	 */
	public static final int NEQ = 2;

	/**
	 * rewriting utility: remove all null coefficients
	 * @param lcoeffs
	 * @return
	 */
	// TODO: could be improved to remove duplicates (variables that would appear twice in the linear combination)
	private static int countNonNull(final int[] lcoeffs) {
		// first, count the actual number of non-null coefficients
		int nbNonNull = 0;
		for (int i = 0; i < lcoeffs.length; i++) {
			if (lcoeffs[i] != 0) {
				nbNonNull++;
			}
		}
		return nbNonNull;
	}

	/**
	 * Field representing the type of linear constraint (equality, inequality, disequality)
	 */
	protected final int op;

	/**
	 * Field representing the type of linear constraint (equality, inequality, disequality)
	 */
	protected final int[] coeffs;

	/**
	 * Field representing the number of variables with positive coeffficients in the linear combination
	 * (all positive coefficients are given before negative ones)
	 */
	protected final int nbPosVars;

	/**
	 * The search constant of the constraint
	 */
	protected final int cste;

	/**
	 * Constructs the constraint with the specified variables and constant.
	 *
	 */
	public IntLinComb(
		final choco.integer.IntVar[] lvars,
		final int[] lcoeffs,
		final int c,
		final int linOperator) {
		// create the appropriate data structure
		super(IntLinComb.countNonNull(lcoeffs));

		final int nbVars = this.getNbVars();
		int i = 0;
		int j = 0;
		this.coeffs = new int[nbVars];
		// fill it up with the coefficients and variables in the right order
		for (i = 0; i < nbVars; i++) {
			if (lcoeffs[i] > 0) {
				this.vars[j] = lvars[i];
				this.coeffs[j] = lcoeffs[i];
				j++;
			}
		}
		this.nbPosVars = j;

		for (i = 0; i < nbVars; i++) {
			if (lcoeffs[i] < 0) {
				this.vars[j] = lvars[i];
				this.coeffs[j] = lcoeffs[i];
				j++;
			}
		}
		this.op = linOperator;
		this.cste = c;
	}

	public void awakeOnInf(final int idx) throws ContradictionException {
		if (idx < this.nbPosVars) {
			this.filter(true, 1);
		}
		else {
			this.filter(false, 1);
		}
	}

	public void awakeOnInst(final int idx) throws ContradictionException {
		this.propagate();
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		;
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		if (idx < this.nbPosVars) {
			this.filter(false, 1);
		}
		else {
			this.filter(true, 1);
		}
	}

	public void awakeOnVar(final int idx) throws ContradictionException {
	}

	/**
	 * computes a lower bound estimate of a linear combination of variables
	 */
	protected int computeLowerBound() {
		int s = this.cste;
		final int nbVars = this.getNbVars();
		int i;
		for (i = 0; i < this.nbPosVars; i++) {
			s += this.vars[i].getInf() * this.coeffs[i];
		}
		for (i = this.nbPosVars; i < nbVars; i++) {
			s += this.vars[i].getSup() * this.coeffs[i];
		}
		AbstractIntConstraint.logger.finest("lb for " + this.toString() + ": "
				+ s);
		return s;
	}

	/**
	 * computes an upper bound estimate of a linear combination of variables
	 */
	protected int computeUpperBound() {
		int s = this.cste;
		final int nbVars = this.getNbVars();
		int i;
		for (i = 0; i < this.nbPosVars; i++) {
			s += this.vars[i].getSup() * this.coeffs[i];
		}
		for (i = this.nbPosVars; i < nbVars; i++) {
			s += this.vars[i].getInf() * this.coeffs[i];
		}
		AbstractIntConstraint.logger.finest("ub for " + this.toString() + ": "
				+ s);
		return s;
	}

	// Note: additional propagation pass are sometimes useful:
	// For instance : 3*X[0.3] + Y[1.10] = 10
	//                Y >= 2 causes X < 3 -> updateSup(X,2)
	//                and this very var (the new sup of X) causes (Y >= 4).
	//                this induced var (Y>=4) could not be infered at first (with only Y>=2)
	//
	/**
	 *  a strategy for chaotic iteration with two rules (LB and UB propagation)
	 *  the fix point is reached individually for each rule in one function call
	 *  but this call may break the stability condition for the other rule (in which case
	 *  the second rule infers new information from the fresh inferences made by the first rule).
	 *  The algorithm oscilates between both rules until a global fix point is reached.
	 *
	 * @param startWithLB whether LB must be the first rule applied
	 * @param minNbRules minimum number of rules required to reach fiux point.
	 */
	protected void filter(final boolean startWithLB, final int minNbRules)
			throws ContradictionException {
		boolean lastRuleEffective = true; // whether the last rule indeed perform some reductions
		int nbr = 0; // number of rules applied
		boolean nextRuleIsLB = startWithLB; // whether the next rule that should be filtered is LB (or UB)
		while (lastRuleEffective || nbr < minNbRules) {
			if (nextRuleIsLB) {
				AbstractIntConstraint.logger.finer("-- LB propagation for "
						+ this.toString());
				lastRuleEffective = this.filterOnImprovedLowerBound();
			}
			else {
				AbstractIntConstraint.logger.finer("-- UB propagation for "
						+ this.toString());
				lastRuleEffective = this.filterOnImprovedUpperBound();
			}
			nextRuleIsLB = !nextRuleIsLB;
			nbr++;
		}
	}

	protected boolean filterOnImprovedLowerBound()
			throws ContradictionException {
		if (this.op == IntLinComb.EQ) {
			return this.propagateNewLowerBound(this.computeLowerBound());
		}
		else if (this.op == IntLinComb.GEQ) {
			return false; // nothing to propagate => nothing was inferred, so return false
		}
		else {
			Assert.assertTrue(this.op == IntLinComb.NEQ);
			final int mylb = this.computeLowerBound();
			if (mylb == 0) { // propagate the constraint sigma(ai) + c >= 1
				AbstractIntConstraint.logger.finer("propagate > 0 for "
						+ this.toString());
				return this
					.propagateNewUpperBound(this.computeUpperBound() - 1); // TODO I have doubts.....
			}
			else {
				return false;
			}
		}
	}

	protected boolean filterOnImprovedUpperBound()
			throws ContradictionException {
		final int myub = this.computeUpperBound();
		if (this.op == IntLinComb.EQ) {
			return this.propagateNewUpperBound(myub);
		}
		else if (this.op == IntLinComb.GEQ) {
			return this.propagateNewUpperBound(myub);
		}
		else {
			Assert.assertTrue(this.op == IntLinComb.NEQ);
			if (myub == 0) { // propagate the constraint sigma(ai) + c <= -1
				AbstractIntConstraint.logger.finer("propagate < 0 for "
						+ this.toString());
				return this
					.propagateNewLowerBound(this.computeLowerBound() + 1); // TODO I have doubts.....
			}
			else {
				return false;
			}
		}
	}

	/**
	   * tests if the constraint is consistent with respect to the current state of domains
	   * @return true iff the constraint is bound consistent (weaker than arc consistent)
	   */
	protected boolean hasConsistentLowerBound() {
		final int lb = this.computeLowerBound();
		final int nbVars = this.getNbVars();

		if (lb > 0) {
			return false;
		}
		else {
			for (int i = 0; i < this.nbPosVars; i++) {
				final int newSupi =
					Arithm.divFloor(-lb, this.coeffs[i])
							+ this.vars[i].getInf();
				if (this.vars[i].getSup() < newSupi) {
					return false;
				}
			}
			for (int i = this.nbPosVars; i < nbVars; i++) {
				final int newInfi =
					Arithm.divCeil(lb, -this.coeffs[i]) + this.vars[i].getSup();
				if (this.vars[i].getInf() > newInfi) {
					return false;
				}
			}
			return true;
		}
	}

	protected boolean hasConsistentUpperBound() {
		final int ub = this.computeUpperBound();
		final int nbVars = this.getNbVars();

		if (ub < 0) {
			return false;
		}
		else {
			for (int i = 0; i < this.nbPosVars; i++) {
				final int newInfi =
					Arithm.divCeil(-ub, this.coeffs[i]) + this.vars[i].getSup();
				if (this.vars[i].getInf() > newInfi) {
					return false;
				}
			}
			for (int i = this.nbPosVars; i < nbVars; i++) {
				final int newSupi =
					Arithm.divFloor(ub, -this.coeffs[i])
							+ this.vars[i].getInf();
				if (this.vars[i].getSup() < newSupi) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * tests if the constraint is consistent with respect to the current state of domains
	 * @return true iff the constraint is bound consistent (weaker than arc consistent)
	 */
	public boolean isConsistent() {
		this.computeUpperBound();
		this.computeLowerBound();
		this.getNbVars();

		if (this.op == IntLinComb.EQ) {
			return this.hasConsistentLowerBound()
					&& this.hasConsistentUpperBound();
		}
		else if (this.op == IntLinComb.GEQ) {
			return this.hasConsistentUpperBound();
		}
		return true;
	}

	public Boolean isEntailed() {
		if (this.op == IntLinComb.EQ) {
			final int a = this.computeLowerBound();
			final int b = this.computeUpperBound();
			if (b < 0 || a > 0) {
				return Boolean.FALSE;
			}
			else if (a == 0 && b == 0) {
				return Boolean.TRUE;
			}
			else {
				return null;
			}
		}
		else if (this.op == IntLinComb.GEQ) {
			if (this.computeUpperBound() < 0) {
				return Boolean.FALSE;
			}
			else if (this.computeLowerBound() >= 0) {
				return Boolean.TRUE;
			}
			else {
				return null;
			}
		}
		else {
			Assert.assertTrue(this.op == IntLinComb.NEQ);
			final int a = this.computeLowerBound();
			if (a > 0) {
				return Boolean.TRUE;
			}
			else {
				final int b = this.computeUpperBound();
				if (b < 0) {
					return Boolean.TRUE;
				}
				else if (b == 0 && a == 0) {
					return Boolean.FALSE;
				}
				else {
					return null;
				}
			}
		}
	}

	public boolean isSatisfied() {
		int s = this.cste;
		final int nbVars = this.getNbVars();
		int i;
		for (i = 0; i < nbVars; i++) {
			s += this.vars[i].getValue() * this.coeffs[i];
		}
		if (this.op == IntLinComb.EQ) {
			return s == 0;
		}
		else if (this.op == IntLinComb.GEQ) {
			return s >= 0;
		}
		else {
			Assert.assertTrue(this.op == IntLinComb.NEQ);
			return s != 0;
		}
	}

	public AbstractConstraint opposite() {
		final Problem pb = this.getProblem();
		final IntExp term = pb.scalar(this.coeffs, this.vars);
		if (this.op == IntLinComb.EQ) {
			return (AbstractConstraint) pb.neq(term, this.cste);
		}
		else if (this.op == IntLinComb.NEQ) {
			return (AbstractConstraint) pb.eq(term, this.cste);
		}
		else if (this.op == IntLinComb.GEQ) {
			return (AbstractConstraint) pb.lt(term, this.cste);
		}
		else {
			return null;
		}
	}

	public void propagate() throws ContradictionException {
		this.filter(true, 2);
	}

	/**
	 * propagates the constraint sigma(ai Xi) + c <= 0 where mylb = sigma(ai inf(Xi)) + c
	 *  note: this does not reach saturation (fix point),
	 *    but returns a boolean indicating whether it infered new information or not.
	 */
	protected boolean propagateNewLowerBound(final int mylb)
			throws ContradictionException {
		boolean anyChange = false;
		final int nbVars = this.getNbVars();
		if (mylb > 0) {
			AbstractIntConstraint.logger.finer("lb = " + mylb + " > 0 => fail");
			this.fail();
		}
		int i;
		for (i = 0; i < this.nbPosVars; i++) {
			final int newSupi =
				Arithm.divFloor(-mylb, this.coeffs[i]) + this.vars[i].getInf();
			if (this.vars[i].updateSup(newSupi, this.cIndices[i])) {
				AbstractIntConstraint.logger.finer("SUP("
						+ this.vars[i].toString() + ") <= " + -mylb + "/"
						+ this.coeffs[i] + " + " + this.vars[i].getInf()
						+ " = " + newSupi);
				anyChange = true;
			}
		}
		for (i = this.nbPosVars; i < nbVars; i++) {
			final int newInfi =
				Arithm.divCeil(mylb, -this.coeffs[i]) + this.vars[i].getSup();
			if (this.vars[i].updateInf(newInfi, this.cIndices[i])) {
				AbstractIntConstraint.logger.finer("INF("
						+ this.vars[i].toString() + ") >= " + mylb + "/"
						+ -this.coeffs[i] + " + " + this.vars[i].getSup()
						+ " = " + newInfi);
				anyChange = true;
			}
		}
		return anyChange;
	}

	/**
	 * propagates the constraint sigma(ai Xi) + c <= 0 where myub = sigma(ai sup(Xi)) + c
	 *  note: this does not reach saturation (fix point),
	 *    but returns a boolean indicating whether it infered new information or not.
	 */
	protected boolean propagateNewUpperBound(final int myub)
			throws ContradictionException {
		boolean anyChange = false;
		final int nbVars = this.getNbVars();
		if (myub < 0) {
			AbstractIntConstraint.logger.finer("ub = " + myub + " < 0 => fail");
			this.fail();
		}
		int i;
		for (i = 0; i < this.nbPosVars; i++) {
			final int newInfi =
				Arithm.divCeil(-myub, this.coeffs[i]) + this.vars[i].getSup();
			if (this.vars[i].updateInf(newInfi, this.cIndices[i])) {
				AbstractIntConstraint.logger.finer("INF("
						+ this.vars[i].toString() + ") >= " + -myub + "/"
						+ this.coeffs[i] + " + " + this.vars[i].getSup()
						+ " = " + newInfi);
				anyChange = true;
			}
		}
		for (i = this.nbPosVars; i < nbVars; i++) {
			final int newSupi =
				Arithm.divFloor(myub, -this.coeffs[i]) + this.vars[i].getInf();
			if (this.vars[i].updateSup(newSupi, this.cIndices[i])) {
				AbstractIntConstraint.logger.finer("SUP("
						+ this.vars[i].toString() + ") <= " + myub + "/"
						+ -this.coeffs[i] + " + " + this.vars[i].getInf()
						+ " = " + newSupi);
				anyChange = true;
			}
		}
		return anyChange;
	}

}
