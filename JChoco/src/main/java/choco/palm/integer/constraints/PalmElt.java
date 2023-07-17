package choco.palm.integer.constraints;

import java.util.Set;
import java.util.logging.Logger;
import choco.ConstraintCollection;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmBinIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;

/**
 * Created by IntelliJ IDEA.
 * User: Hadrien
 * Date: 18 janv. 2004
 * Time: 19:22:28
 * To change this template use Options | File Templates.
 */
public class PalmElt extends AbstractPalmBinIntConstraint {

	/** uses the cste slot: l[i + cste] = x
	 *  (ex: cste = 1 allows to use and index from 0 to length(l) - 1
	 */
	protected final int cste;
	protected int[] lvals;

	/**
	 * Element constraint
	 * (accessing the ith element in a list of values, where i is a variable)
	 * the slot v0 represents the index and the slot v1 represents the value
	 * propagation with complete arc consistency from values to indices (v1 to v0)
	 * propagation with interval approximation from indices to values (v0 to v1)
	 * @param v0
	 * @param v1
	 * @param cste
	 * @param lvals
	 */
	public PalmElt(
		final IntVar v0,
		final IntVar v1,
		final int cste,
		final int[] lvals) {
		this.v0 = v0;
		this.v1 = v1;
		this.cste = cste;
		this.lvals = lvals;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void awakeOnInf(final int idx) {
		if (idx == 0) {
			this.updateValueFromIndex();
		}
		else {
			this.updateIndexFromValue();
		}
	}

	public void awakeOnRem(final int idx, final int val) {
		if (idx == 0) {
			this.updateValueFromIndex();
		}
		else {
			this.updateIndexFromValue();
		}
	}

	public void awakeOnRestore(final int idx) {
		final ConstraintCollection expl =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmConstraintPlugin) this.hook).self_explain(expl);
		((PalmIntVar) this.v0).updateInf(
			this.cste,
			this.cIdx0,
			(Explanation) expl.copy());
		((PalmIntVar) this.v0).updateSup(
			this.lvals.length - 1,
			this.cIdx0,
			(Explanation) expl.copy());
		if (idx == 0) {
			this.updateIndexFromValue();
		}
		else {
			this.updateValueFromIndex();
		}
	}

	public void awakeOnRestoreInf(final int idx) {
		this.awakeOnRestore(idx);
	}

	public void awakeOnRestoreSup(final int idx) {
		this.awakeOnRestore(idx);
	}

	public void awakeOnRestoreVal(final int idx, final int val) {
		this.awakeOnRestore(idx);
	}

	public void awakeOnSup(final int idx) {
		if (idx == 0) {
			this.updateValueFromIndex();
		}
		else {
			this.updateIndexFromValue();
		}
	}

	public Boolean isEntailed() {
		Logger.getLogger("choco.palm").warning(
			"Not Yet implemented : NotEqual.isEntailed");
		return null;
	}

	public boolean isSatisfied() {
		return true;
	}

	public void propagate() throws ContradictionException {
		this.updateIndexFromValue();
		this.updateValueFromIndex();
	}

	public void updateIndexFromValue() {
		final ConstraintCollection e =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmIntVar) this.v1).self_explain(PalmIntDomain.DOM, e);
		((PalmConstraintPlugin) this.hook).self_explain(e);
		int minFeasibleIndex = this.v0.getInf(), maxFeasibleIndex =
			this.v0.getSup(), thecause = -1;
		if (this.v0.getInf() < this.cste) {
			minFeasibleIndex = this.cste;
		}
		if (this.v0.getSup() > this.lvals.length - 1) {
			maxFeasibleIndex = this.lvals.length - 1;
		}
		if (this.v1.hasEnumeratedDomain()) {
			thecause = this.cIdx0;
		}
		while (minFeasibleIndex < this.lvals.length - 1
				& this.v0.canBeInstantiatedTo(minFeasibleIndex)
				& !this.v1.canBeInstantiatedTo(this.lvals[minFeasibleIndex
						+ this.cste])) {
			minFeasibleIndex++;
		}
		((PalmIntVar) this.v0).updateInf(
			minFeasibleIndex,
			thecause,
			(Explanation) e.copy());
		while (maxFeasibleIndex > 0
				& this.v0.canBeInstantiatedTo(maxFeasibleIndex)
				& !this.v1.canBeInstantiatedTo(this.lvals[maxFeasibleIndex
						+ this.cste])) {
			maxFeasibleIndex--;
		}
		((PalmIntVar) this.v0).updateSup(
			maxFeasibleIndex,
			thecause,
			(Explanation) e.copy());
		if (this.v0.hasEnumeratedDomain()) {
			for (int i = minFeasibleIndex + 1; i < maxFeasibleIndex; i++) {
				if (this.v0.canBeInstantiatedTo(i)
						& !this.v1
							.canBeInstantiatedTo(this.lvals[i + this.cste])) {
					final Explanation expl =
						((PalmProblem) this.getProblem()).makeExplanation();
					((PalmConstraintPlugin) this.hook).self_explain(expl);
					((PalmIntVar) this.v1).self_explain(
						PalmIntDomain.VAL,
						this.lvals[i + this.cste],
						expl);
					((PalmIntVar) this.v0).removeVal(i, thecause, expl);
				}
			}
		}
	}

	public void updateValueFromIndex() {
		int minVal = Integer.MAX_VALUE, maxVal = Integer.MIN_VALUE, val, j = 0;
		boolean found = false;
		final ConstraintCollection e =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmConstraintPlugin) this.hook).self_explain(e);
		((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, e);
		int[] values = ((PalmIntVar) this.v0).getAllValues();
		for (int i = 0; i < values.length; i++) {
			val = this.lvals[this.cste + values[i]];
			if (minVal > val) {
				minVal = val;
			}
			if (maxVal < val) {
				maxVal = val;
			}
		}
		((PalmIntVar) this.v1).updateSup(
			maxVal,
			this.cIdx1,
			(Explanation) e.copy());
		((PalmIntVar) this.v1).updateInf(
			minVal,
			this.cIdx1,
			(Explanation) e.copy());

		values = ((PalmIntVar) this.v0).getAllValues();
		final int[] values2 = ((PalmIntVar) this.v1).getAllValues(); // TODO : remplacer par des itérateurs
		// propagate on holes
		if (this.v1.hasEnumeratedDomain()) {
			for (int i = 0; i < values2.length; i++) {
				while (!found & j < values.length) {
					if (this.lvals[values[j] + this.cste] == values2[i]) {
						found = true;
					}
					j++;
				}
				if (!found) {
					((PalmIntVar) this.v1).removeVal(
						values2[i],
						this.cIdx1,
						(Explanation) e.copy());
				}
				found = false;
				j = 0;
			}
		}
	}

	public Set whyIsFalse() {
		Logger.getLogger("choco.palm").warning(
			"Not Yet implemented : NotEqual.whyIsFalse");
		return null;
	}

	public Set whyIsTrue() {
		Logger.getLogger("choco.palm").warning(
			"Not Yet implemented : NotEqual.whyIsTrue");
		return null;
	}
}
