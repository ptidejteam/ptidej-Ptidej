//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer.constraints;

import java.util.Set;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.integer.constraints.AbstractIntConstraint;
import choco.integer.constraints.IntLinComb;
import choco.palm.PalmConstraint;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.palm.integer.PalmIntVarListener;
import choco.palm.prop.PalmEngine;
import choco.util.Arithm;
import choco.util.IntIterator;

/**
 * Created by IntelliJ IDEA.
 * User: rochart
 * Date: Jan 13, 2004
 * Time: 7:58:45 AM
 * To change this template use Options | File Templates.
 */
public class PalmIntLinComb extends IntLinComb implements PalmIntVarListener,
		PalmConstraint {
	public PalmIntLinComb(
		final IntVar[] vars,
		final int[] coeffs,
		final int c,
		final int linOperator) {
		super(vars, coeffs, c, linOperator);
		this.hook = new PalmConstraintPlugin(this);
	}

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		this.propagate();
	}

	public void awakeOnRestoreInf(final int idx) throws ContradictionException {
		this.propagate();
	}

	public void awakeOnRestoreSup(final int idx) throws ContradictionException {
		this.propagate();
	}

	public void awakeOnRestoreVal(final int idx, final int val)
			throws ContradictionException {
		// should not be called !! this.propagate();
	}

	public void awakeOnRestoreVal(final int idx, final IntIterator it)
			throws ContradictionException {
		this.propagate();
	}

	private Explanation explainVariablesLB() {
		final Explanation expl =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmConstraintPlugin) this.hook).self_explain(expl);
		for (int i = 0; i < this.nbPosVars; i++) {
			((PalmIntVar) this.vars[i]).self_explain(PalmIntDomain.INF, expl);
		}
		for (int i = this.nbPosVars; i < this.getNbVars(); i++) {
			((PalmIntVar) this.vars[i]).self_explain(PalmIntDomain.SUP, expl);
		}
		return expl;
	}

	private Explanation explainVariablesUB() {
		final Explanation expl =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmConstraintPlugin) this.hook).self_explain(expl);
		for (int i = 0; i < this.nbPosVars; i++) {
			((PalmIntVar) this.vars[i]).self_explain(PalmIntDomain.SUP, expl);
		}
		for (int i = this.nbPosVars; i < this.getNbVars(); i++) {
			((PalmIntVar) this.vars[i]).self_explain(PalmIntDomain.INF, expl);
		}
		return expl;
	}

	protected boolean propagateNewLowerBound(final int mylb)
			throws ContradictionException {
		final Explanation expl = this.explainVariablesLB();
		boolean anyChange = false;
		final int nbVars = this.getNbVars();
		if (mylb > 0) {
			AbstractIntConstraint.logger.finer("lb = " + mylb + " > 0 => fail");
			((PalmEngine) this.getProblem().getChocEngine())
				.raisePalmFakeContradiction(expl);
		}
		int i;
		for (i = 0; i < this.nbPosVars; i++) {
			final int newSupi =
				Arithm.divFloor(-mylb, this.coeffs[i]) + this.vars[i].getInf();
			if (((PalmIntVar) this.vars[i]).updateSup(
				newSupi,
				this.cIndices[i],
				expl)) {
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
			if (((PalmIntVar) this.vars[i]).updateInf(
				newInfi,
				this.cIndices[i],
				expl)) {
				AbstractIntConstraint.logger.finer("INF("
						+ this.vars[i].toString() + ") >= " + mylb + "/"
						+ -this.coeffs[i] + " + " + this.vars[i].getSup()
						+ " = " + newInfi);
				anyChange = true;
			}
		}
		return anyChange;
	}

	protected boolean propagateNewUpperBound(final int myub)
			throws ContradictionException {
		final Explanation expl = this.explainVariablesUB();
		boolean anyChange = false;
		final int nbVars = this.getNbVars();
		if (myub < 0) {
			AbstractIntConstraint.logger.finer("ub = " + myub + " < 0 => fail");
			((PalmEngine) this.getProblem().getChocEngine())
				.raisePalmFakeContradiction(expl);
		}
		int i;
		for (i = 0; i < this.nbPosVars; i++) {
			final int newInfi =
				Arithm.divCeil(-myub, this.coeffs[i]) + this.vars[i].getSup();
			if (((PalmIntVar) this.vars[i]).updateInf(
				newInfi,
				this.cIndices[i],
				expl)) {
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
			if (((PalmIntVar) this.vars[i]).updateSup(
				newSupi,
				this.cIndices[i],
				expl)) {
				AbstractIntConstraint.logger.finer("SUP("
						+ this.vars[i].toString() + ") <= " + myub + "/"
						+ -this.coeffs[i] + " + " + this.vars[i].getInf()
						+ " = " + newSupi);
				anyChange = true;
			}
		}
		return anyChange;
	}

	public void takeIntoAccountStatusChange(final int index) {
	}

	public void updateDataStructuresOnConstraint(
		final int idx,
		final int select,
		final int newValue,
		final int oldValue) {
	}

	public void updateDataStructuresOnRestoreConstraint(
		final int idx,
		final int select,
		final int newValue,
		final int oldValue) {
	}

	public Set whyIsFalse() {
		return null;
	}

	public Set whyIsTrue() {
		return null;
	}

}
