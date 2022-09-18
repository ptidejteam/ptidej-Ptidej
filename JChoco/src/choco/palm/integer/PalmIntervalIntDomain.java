//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;
import choco.Constraint;
import choco.ConstraintCollection;
import choco.integer.var.IntDomainVar;
import choco.integer.var.IntervalIntDomain;
import choco.mem.IStateInt;
import choco.palm.PalmProblem;
import choco.palm.integer.constraints.PalmAssignment;
import choco.palm.integer.constraints.PalmNotEqualXC;
import choco.palm.integer.explain.DecSupExplanation;
import choco.palm.integer.explain.IncInfExplanation;
import choco.palm.prop.PalmEngine;
import choco.palm.prop.StructureMaintainer;
import choco.prop.VarEvent;

public class PalmIntervalIntDomain extends IntervalIntDomain implements
		PalmIntDomain {
	/**
	 * A stack of explanations for lower bound modifications.
	 */

	protected final LinkedList explanationOnInf;

	/**
	 * A stack of explanations for upper bound modifications.
	 */

	protected final LinkedList explanationOnSup;

	/**
	 * Decision constraints on the variable for branching purpose.
	 */

	protected final Hashtable decisionConstraints;

	/**
	 * Negation of decision constraints on the variable for branching purpose.
	 */

	protected final Hashtable negDecisionConstraints;

	/**
	 * Original lower bound.
	 */

	protected final int originalInf;

	/**
	 * Original upper bound.
	 */

	protected final int originalSup;

	/**
	 * Builds a interval domain for the specified variable.
	 * @param v Involved variable.
	 * @param a Lower bound.
	 * @param b Upper bound.
	 */

	public PalmIntervalIntDomain(final IntDomainVar v, final int a, final int b) {
		super(v, a, b);
		final PalmProblem pb = (PalmProblem) this.getProblem();
		this.explanationOnInf = new LinkedList();
		this.explanationOnSup = new LinkedList();
		this.explanationOnInf.add(pb.makeExplanation().makeIncInfExplanation(
			this.getInf(),
			(PalmIntVar) this.variable));
		this.explanationOnSup.add(pb.makeExplanation().makeDecSupExplanation(
			this.getSup(),
			(PalmIntVar) this.variable));
		this.decisionConstraints = new Hashtable();
		this.negDecisionConstraints = new Hashtable();
		this.originalInf = a;
		this.originalSup = b;
	}

	/**
	 * Returns all the value currently in the domain.
	 */

	public int[] getAllValues() {
		//if (bucket != null) {
		//    return bucket.domainSet();
		//} else {
		final int[] ret = new int[this.getSup() - this.getInf() + 1];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = this.getInf() + i;
		}
		return ret;
		//}
	}

	/**
	 * Returns the decision constraint assigning the domain to the specified value. The constraint is created if
	 * it is not yet created.
	 */

	public Constraint getDecisionConstraint(final int val) {
		Constraint cons =
			(Constraint) this.decisionConstraints.get(Integer.valueOf(val
					- this.getOriginalInf()));
		if (cons != null) {
			return cons;
		}
		else {
			cons = new PalmAssignment(this.variable, val);
			this.decisionConstraints.put(
				Integer.valueOf(val - this.getOriginalInf()),
				cons);
			this.negDecisionConstraints.put(
				Integer.valueOf(val - this.getOriginalInf()),
				new PalmNotEqualXC(this.variable, val));
			return cons;
		}
	}

	/**
	 * Returns the negated decision constraint.
	 */

	public Constraint getNegDecisionConstraint(final int val) {
		return (Constraint) this.negDecisionConstraints.get(Integer.valueOf(val
				- this.getOriginalInf()));
	}

	/**
	 * Returns the original lower bound.
	 */

	public int getOriginalInf() {
		return this.originalInf;
	}

	/**
	 * Returns the original upper bound.
	 */

	public int getOriginalSup() {
		return this.originalSup;
	}

	/**
	 * Removes a value and posts the event.
	 */

	public boolean removeVal(
		final int value,
		final int idx,
		final choco.palm.explain.Explanation e) {
		if (value == this.getInf()) {
			return this.updateInf(value + 1, idx, e);
		}
		else if (value == this.getSup()) {
			return this.updateSup(value - 1, idx, e);
		}
		return false;
	}

	/**
	 * When a lower bound is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnInf() {
		boolean keep = true;
		for (final ListIterator iterator = this.explanationOnInf.listIterator(); iterator
			.hasNext();) {
			final IncInfExplanation expl = (IncInfExplanation) iterator.next();
			if (expl.getPreviousValue() >= this.getInf()) {
				if (expl.getPreviousValue() == this.getOriginalInf() && keep) {
					keep = false;
				}
				else {
					iterator.remove();
				}
			}
		}
	}

	/**
	 * When an upper bound is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnSup() {
		boolean keep = true;
		for (final ListIterator iterator = this.explanationOnSup.listIterator(); iterator
			.hasNext();) {
			final DecSupExplanation expl = (DecSupExplanation) iterator.next();
			if (expl.getPreviousValue() <= this.getSup()) {
				if (expl.getPreviousValue() == this.getOriginalSup() && keep) {
					keep = false;
				}
				else {
					iterator.remove();
				}
			}
		}
	}

	/**
	 * When a value is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnVal(final int val) {
	}

	/**
	 * Restores a lower bound and posts the event.
	 */

	public void restoreInf(final int newValue) {
		if (this.getInf() > newValue) {
			final int oldValue = this.getInf();
			this.inf.set(newValue);
			if (this.getInf() != this.getSup()) {
				this.variable.value.set(IStateInt.UNKNOWN_INT);
			}
			else {
				this.variable.value.set(this.getInf());
			}
			StructureMaintainer.updateDataStructuresOnRestore(
				this.variable,
				PalmIntDomain.INF,
				newValue,
				oldValue);
			//((PalmIntVar) this.variable).updateDataStructuresOnRestore(PalmIntVar.INF, newValue, oldValue);
			((PalmEngine) this.getProblem().getPropagationEngine())
				.postRestoreInf((PalmIntVar) this.variable);
		}
	}

	/**
	 * Restores an upper bound and posts the event.
	 */

	public void restoreSup(final int newValue) {
		if (this.getSup() < newValue) {
			final int oldValue = this.getSup();
			this.sup.set(newValue);
			if (this.getInf() != this.getSup()) {
				this.variable.value.set(IStateInt.UNKNOWN_INT);
			}
			else {
				this.variable.value.set(this.getInf());
			}
			StructureMaintainer.updateDataStructuresOnRestore(
				this.variable,
				PalmIntDomain.SUP,
				newValue,
				oldValue);
			//((PalmIntVar) this.variable).updateDataStructuresOnRestore(PalmIntVar.SUP, newValue, oldValue);
			((PalmEngine) this.getProblem().getPropagationEngine())
				.postRestoreSup((PalmIntVar) this.variable);
		}
	}

	/**
	 * Restores a value and posts the event. Not supported for such a domain.
	 */

	public void restoreVal(final int val) {
		System.err
			.println("restoreVal should not be called on a IntervalIntdomain !");
	}

	/**
	 * Allows to get an explanation for the domain or a bound of the variable. This explanation is merge to the
	 * explanation in parameter.
	 * @param select Should be <code>PalmIntDomain.INF</code>, <code>PalmIntDomain.SUP</code>, or <code>PalmIntDomain.DOM</code>
	 */

	public void self_explain(final int select, final ConstraintCollection expl) {
		switch (select) {
			case DOM :
				this.self_explain(PalmIntDomain.INF, expl);
				this.self_explain(PalmIntDomain.SUP, expl);
				break;
			case INF :
				expl.merge((choco.ConstraintCollection) this.explanationOnInf
					.getLast());
				break;
			case SUP :
				expl.merge((choco.ConstraintCollection) this.explanationOnSup
					.getLast());
				break;
			default :
				Logger
					.getLogger("choco.palm")
					.warning(
						"PaLM: VAL needs another parameter in self_explain (IntVar)");
		}
	}

	/**
	 * Allows to get an explanation for a value removal from the variable. This explanation is merge to the
	 * explanation in parameter.
	 * @param select Should be <code>PalmIntDomain.VAL</code>
	 */

	public void self_explain(
		final int select,
		final int x,
		final ConstraintCollection expl) {
		if (select == PalmIntDomain.VAL) {
			// TODO : on ne peut pas prendre une explication plus precise ?
			if (x < this.getInf()) {
				expl.merge((choco.ConstraintCollection) this.explanationOnInf
					.getLast());
			}
			else if (x > this.getSup()) {
				expl.merge((choco.ConstraintCollection) this.explanationOnSup
					.getLast());
			}
		}
		else {
			Logger
				.getLogger("choco.palm")
				.warning(
					"PaLM: INF, SUP or DOM do not need a supplementary parameter in self_explain (IntVar)");
		}
	}

	private boolean updateInf(
		final int x,
		final choco.palm.explain.Explanation e) {
		if (x > this.getInf()) {
			final int oldValue = this.getInf();
			((PalmIntVar) this.variable).self_explain(PalmIntDomain.INF, e);
			this.explanationOnInf.add(e.makeIncInfExplanation(
				this.getInf(),
				(PalmIntVar) this.variable));
			this.updateInf(x);
			if (this.inf.get() == this.sup.get()) {
				this.variable.value.set(this.getInf());
			}
			StructureMaintainer.updateDataStructures(
				this.variable,
				PalmIntDomain.INF,
				x,
				oldValue);
			//((PalmIntVar) this.variable).updateDataStructures(PalmIntVar.INF, x, oldValue);
			return true;
		}
		return false;
	}

	/**
	 * Updates the lower bound and posts the event.
	 */

	public boolean updateInf(
		final int x,
		final int idx,
		final choco.palm.explain.Explanation e) {
		if (this.updateInf(x, e)) {
			int cause = VarEvent.NOCAUSE;
			if (x == this.getInf()) {
				cause = idx;
			}
			((PalmEngine) this.getProblem().getPropagationEngine())
				.postUpdateInf(this.variable, cause);
			if (x > this.getSup()) {
				this.variable.value.set(IStateInt.UNKNOWN_INT);
				((PalmEngine) this.getProblem().getPropagationEngine())
					.raisePalmContradiction((PalmIntVar) this.variable);
			}
			return true;
		}
		return false;
	}

	private boolean updateSup(
		final int x,
		final choco.palm.explain.Explanation e) {
		if (x < this.getSup()) {
			final int oldValue = this.getSup();
			((PalmIntVar) this.variable).self_explain(PalmIntDomain.SUP, e);
			this.explanationOnSup.add(e.makeDecSupExplanation(
				this.getSup(),
				(PalmIntVar) this.variable));
			this.updateSup(x);
			if (this.inf.get() == this.sup.get()) {
				this.variable.value.set(this.getInf());
			}
			StructureMaintainer.updateDataStructures(
				this.variable,
				PalmIntDomain.SUP,
				x,
				oldValue);
			//((PalmIntVar) this.variable).updateDataStructures(PalmIntVar.SUP, x, oldValue);
			return true;
		}
		return false;
	}

	/**
	 * Updates the upper bound and posts the event.
	 */

	public boolean updateSup(
		final int x,
		final int idx,
		final choco.palm.explain.Explanation e) {
		if (this.updateSup(x, e)) {
			int cause = VarEvent.NOCAUSE;
			if (x == this.getSup()) {
				cause = idx;
			}
			((PalmEngine) this.getProblem().getPropagationEngine())
				.postUpdateSup(this.variable, cause);
			if (x < this.getInf()) {
				this.variable.value.set(IStateInt.UNKNOWN_INT);
				((PalmEngine) this.getProblem().getPropagationEngine())
					.raisePalmContradiction((PalmIntVar) this.variable);
			}
			return true;
		}
		return false;
	}
}
