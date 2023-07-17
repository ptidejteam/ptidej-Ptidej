package choco.palm.integer.constraints;

import java.util.Set;
import java.util.logging.Logger;
import choco.ConstraintCollection;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmTernIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.util.IntIterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 30 janv. 2004
 * Time: 11:04:19
 * To change this template use Options | File Templates.
 */
public class PalmElt2D extends AbstractPalmTernIntConstraint {

	/** uses the cste slot: l[i + cste] = x
	*  (ex: cste = 1 allows to use and index from 0 to length(l) - 1
	*/

	protected int[][] lvals;
	int dim1;
	int dim2;
	/**
	 * 2D Element constraint
	 * @param v0 index1
	 * @param v1 index2
	 * @param v2 valeur
	 * @param lvals
	 */
	// On ne peut plus avoir d'offset
	public PalmElt2D(
		final IntVar v0,
		final IntVar v1,
		final IntVar v2,
		final int[][] lvals,
		final int dim1,
		final int dim2) {
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.lvals = lvals;
		this.dim1 = dim1;
		this.dim2 = dim2;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void awakeOnInf(final int idx) {
		if (idx <= 2) {
			this.updateValueFromIndex();
		}
		else {
			this.updateIndexFromValue();
		}
	}

	public void awakeOnRem(final int idx, final int val) {
		if (idx <= 2) {
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
			0,
			this.cIdx0,
			(Explanation) expl.copy());
		((PalmIntVar) this.v0).updateSup(
			this.dim1 - 1,
			this.cIdx0,
			(Explanation) expl.copy());
		((PalmIntVar) this.v1).updateInf(
			0,
			this.cIdx0,
			(Explanation) expl.copy());
		((PalmIntVar) this.v1).updateSup(
			this.dim2 - 1,
			this.cIdx1,
			(Explanation) expl.copy());
		if (idx <= 2) {
			this.updateIndexFromValue(); // on l'appelle à l'envers pour voir les valeurs qu'on peut remettre
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
		if (idx <= 2) {
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

	public boolean testValueVarV0(final int idx) {
		boolean ret = false;
		final IntIterator domIt = this.v1.getDomain().getIterator();
		while (!ret & domIt.hasNext()) {
			ret = this.v2.canBeInstantiatedTo(this.lvals[idx][domIt.next()]);
		}
		return ret;
	}

	public boolean testValueVarV1(final int idx) {
		boolean ret = false;
		final IntIterator domIt = this.v0.getDomain().getIterator();
		while (!ret & domIt.hasNext()) {
			ret = this.v2.canBeInstantiatedTo(this.lvals[domIt.next()][idx]);
		}
		return ret;
	}

	public void updateIndexFromValue() {
		final PalmProblem pb = (PalmProblem) this.getProblem();
		final ConstraintCollection e = pb.makeExplanation();
		((PalmConstraintPlugin) this.hook).self_explain(e);
		((PalmIntVar) this.v2).self_explain(PalmIntDomain.DOM, e);
		int minFeasibleIndex1 = this.v0.getInf(), minFeasibleIndex2 =
			this.v1.getInf();
		int maxFeasibleIndex1 = this.v0.getSup(), maxFeasibleIndex2 =
			this.v1.getSup();
		int thecause1 = 0, thecause2 = 0;
		if (this.v0.getSup() > this.dim1 - 1) {
			maxFeasibleIndex1 = this.dim1 - 1;
		}
		if (this.v1.getSup() > this.dim2 - 1) {
			maxFeasibleIndex2 = this.dim2 - 1;
		}
		if (this.v2.hasEnumeratedDomain()) {
			thecause1 = this.cIdx0;
		}
		if (this.v2.hasEnumeratedDomain()) {
			thecause2 = this.cIdx1;
		}
		// update index1
		final ConstraintCollection e1 = pb.makeExplanation();
		e1.merge(e);
		((PalmIntVar) this.v1).self_explain(PalmIntDomain.DOM, e1);
		while (minFeasibleIndex1 < this.dim1 - 1
				& this.v0.canBeInstantiatedTo(minFeasibleIndex1)
				& !this.testValueVarV0(minFeasibleIndex1)) {
			minFeasibleIndex1++;
		}
		((PalmIntVar) this.v0).updateInf(
			minFeasibleIndex1,
			thecause1,
			(Explanation) e1.copy());
		while (maxFeasibleIndex1 > 0
				& this.v0.canBeInstantiatedTo(maxFeasibleIndex1)
				& !this.testValueVarV0(maxFeasibleIndex1)) {
			maxFeasibleIndex1--;
		}
		((PalmIntVar) this.v0).updateSup(
			maxFeasibleIndex1,
			thecause1,
			(Explanation) e1.copy());
		if (this.v0.hasEnumeratedDomain()) {
			for (int i = minFeasibleIndex1 + 1; i < maxFeasibleIndex1; i++) {
				if (this.v0.canBeInstantiatedTo(i) & !this.testValueVarV0(i)) {
					final Explanation expl = pb.makeExplanation();
					((PalmConstraintPlugin) this.hook).self_explain(expl);
					final IntIterator domIt = this.v1.getDomain().getIterator();
					while (domIt.hasNext()) {
						((PalmIntVar) this.v2).self_explain(
							PalmIntDomain.VAL,
							this.lvals[i][domIt.next()],
							expl);
					}
					((PalmIntVar) this.v1)
						.self_explain(PalmIntDomain.DOM, expl);
					((PalmIntVar) this.v0).removeVal(i, thecause1, expl);
				}
			}
		}
		// update index2
		final ConstraintCollection e2 = pb.makeExplanation();
		e2.merge(e);
		((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, e2);
		while (minFeasibleIndex2 < this.dim2 - 1
				& this.v1.canBeInstantiatedTo(minFeasibleIndex2)
				& !this.testValueVarV1(minFeasibleIndex2)) {
			minFeasibleIndex2++;
		}
		((PalmIntVar) this.v1).updateInf(
			minFeasibleIndex2,
			thecause2,
			(Explanation) e2.copy());
		while (maxFeasibleIndex1 > 0
				& this.v1.canBeInstantiatedTo(maxFeasibleIndex2)
				& !this.testValueVarV1(maxFeasibleIndex2)) {
			maxFeasibleIndex2--;
		}
		((PalmIntVar) this.v1).updateSup(
			maxFeasibleIndex2,
			thecause2,
			(Explanation) e2.copy());
		if (this.v1.hasEnumeratedDomain()) {
			for (int i = minFeasibleIndex2 + 1; i < maxFeasibleIndex2; i++) {
				if (this.v1.canBeInstantiatedTo(i) & !this.testValueVarV1(i)) {
					final Explanation expl = pb.makeExplanation();
					((PalmConstraintPlugin) this.hook).self_explain(expl);
					final IntIterator domIt = this.v0.getDomain().getIterator();
					while (domIt.hasNext()) {
						((PalmIntVar) this.v2).self_explain(
							PalmIntDomain.VAL,
							this.lvals[domIt.next()][i],
							expl);
					}
					((PalmIntVar) this.v0)
						.self_explain(PalmIntDomain.DOM, expl);
					((PalmIntVar) this.v1).removeVal(i, thecause2, expl);
				}
			}
		}

	}

	public void updateValueFromIndex() {
		int minVal = Integer.MAX_VALUE, maxVal = Integer.MIN_VALUE, val, k = 0, l =
			0;
		boolean found = false;
		final ConstraintCollection e =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmConstraintPlugin) this.hook).self_explain(e);
		((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, e);
		((PalmIntVar) this.v1).self_explain(PalmIntDomain.DOM, e);
		int[] idx1 = ((PalmIntVar) this.v0).getAllValues();
		int[] idx2 = ((PalmIntVar) this.v1).getAllValues();
		for (int i = 0; i < idx1.length; i++) {
			for (int j = 0; j < idx2.length; j++) {
				val = this.lvals[idx1[i]][idx2[j]];
				if (minVal > val) {
					minVal = val;
				}
				if (maxVal < val) {
					maxVal = val;
				}
			}
		}
		((PalmIntVar) this.v2).updateSup(
			maxVal,
			this.cIdx2,
			(Explanation) e.copy());
		((PalmIntVar) this.v2).updateInf(
			minVal,
			this.cIdx2,
			(Explanation) e.copy());

		//values = ((PalmIntVar) this.v0).getAllValues();
		final int[] values = ((PalmIntVar) this.v2).getAllValues(); // TODO : remplacer par des itérateurs
		idx1 = ((PalmIntVar) this.v0).getAllValues();
		idx2 = ((PalmIntVar) this.v1).getAllValues();
		// propagate on holes
		if (this.v2.hasEnumeratedDomain()) {
			for (int i = 0; i < values.length; i++) { // on parcourt la valeur
				while (!found & k < idx1.length) {
					while (!found & l < idx2.length) {
						if (this.lvals[idx1[k]][idx2[l]] == values[i]) {
							found = true;
						}
						l++;
					}
					l = 0;
					k++;
				}
				if (!found) {
					((PalmIntVar) this.v2).removeVal(
						values[i],
						this.cIdx2,
						(Explanation) e.copy());
				}
				found = false;
				l = 0;
				k = 0;
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
