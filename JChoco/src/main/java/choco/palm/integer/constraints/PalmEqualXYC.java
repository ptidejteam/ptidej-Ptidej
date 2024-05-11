//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer.constraints;

import java.util.Set;
import java.util.logging.Logger;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmBinIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;

/**
 * Created by IntelliJ IDEA.
 * User: grochart
 * Date: 26 ao?t 2003
 * Time: 17:10:01
 * To change this template use Options | File Templates.
 */
public class PalmEqualXYC extends AbstractPalmBinIntConstraint {
	protected final int cste;

	public PalmEqualXYC(final IntVar v0, final IntVar v1, final int cste) {
		this.v0 = v0;
		this.v1 = v1;
		this.cste = cste;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void awakeOnInf(final int idx) {
		final PalmProblem pb = (PalmProblem) this.getProblem();
		if (idx == 0) {
			final choco.palm.explain.Explanation expl = pb.makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).self_explain(PalmIntDomain.INF, expl);
			((PalmIntVar) this.v1).updateInf(
				this.v0.getInf() - this.cste,
				this.cIdx1,
				expl);
		}
		else {
			final choco.palm.explain.Explanation expl = pb.makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v1).self_explain(PalmIntDomain.INF, expl);
			((PalmIntVar) this.v0).updateInf(
				this.v1.getInf() + this.cste,
				this.cIdx0,
				expl);
		}
	}

	public void awakeOnRem(final int idx, final int value) {
		final PalmProblem pb = (PalmProblem) this.getProblem();
		if (idx == 0) {
			if (this.v1.canBeInstantiatedTo(value - this.cste)) {
				final choco.palm.explain.Explanation expl =
					pb.makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v0).self_explain(
					PalmIntDomain.VAL,
					value,
					expl);
				((PalmIntVar) this.v1).removeVal(
					value - this.cste,
					this.cIdx1,
					expl);
			}
		}
		else {
			if (this.v0.canBeInstantiatedTo(value + this.cste)) {
				final choco.palm.explain.Explanation expl =
					pb.makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v1).self_explain(
					PalmIntDomain.VAL,
					value,
					expl);
				((PalmIntVar) this.v0).removeVal(
					value + this.cste,
					this.cIdx0,
					expl);
			}
		}
	}

	public void awakeOnRestoreInf(final int idx) {
		if (idx == 0) {
			this.awakeOnInf(1);
		}
		else {
			this.awakeOnInf(0);
		}
	}

	public void awakeOnRestoreSup(final int idx) {
		if (idx == 0) {
			this.awakeOnSup(1);
		}
		else {
			this.awakeOnSup(0);
		}
	}

	public void awakeOnRestoreVal(final int idx, final int value) {
		final PalmProblem pb = (PalmProblem) this.getProblem();
		if (idx == 0) {
			if (!this.v1.canBeInstantiatedTo(value - this.cste)) {
				final choco.palm.explain.Explanation expl =
					pb.makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v1).self_explain(PalmIntDomain.VAL, value
						- this.cste, expl);
				((PalmIntVar) this.v0).removeVal(value, this.cIdx0, expl);
			}
		}
		else {
			if (!this.v0.canBeInstantiatedTo(value + this.cste)) {
				final choco.palm.explain.Explanation expl =
					pb.makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v0).self_explain(PalmIntDomain.VAL, value
						+ this.cste, expl);
				((PalmIntVar) this.v1).removeVal(value, this.cIdx1, expl);
			}
		}
	}

	public void awakeOnSup(final int idx) {
		final PalmProblem pb = (PalmProblem) this.getProblem();
		if (idx == 0) {
			final choco.palm.explain.Explanation expl = pb.makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).self_explain(PalmIntDomain.SUP, expl);
			((PalmIntVar) this.v1).updateSup(
				this.v0.getSup() - this.cste,
				this.cIdx1,
				expl);
		}
		else {
			final choco.palm.explain.Explanation expl = pb.makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v1).self_explain(PalmIntDomain.SUP, expl);
			((PalmIntVar) this.v0).updateSup(
				this.v1.getSup() + this.cste,
				this.cIdx0,
				expl);
		}
	}

	public Boolean isEntailed() {
		if (this.v0.getSup() < this.v1.getInf() + this.cste
				|| this.v0.getInf() > this.v1.getSup() + this.cste) {
			return Boolean.FALSE;
		}
		else if (this.v0.getInf() == this.v0.getSup()
				&& this.v1.getInf() == this.v1.getSup()
				&& this.v0.getInf() == this.v1.getInf() + this.cste) {
			return Boolean.TRUE;
		}
		return null;
	}

	public boolean isSatisfied() {
		return true;
	}

	public void propagate() {
		final PalmProblem pb = (PalmProblem) this.getProblem();
		if (((PalmIntVar) this.v0).hasEnumeratedDomain()) {
			int[] values = ((PalmIntVar) this.v0).getAllValues();
			for (int i = 0; i < values.length; i++) {
				final int value = values[i];
				if (!this.v1.canBeInstantiatedTo(value - this.cste)) {
					final choco.palm.explain.Explanation expl =
						pb.makeExplanation();
					((PalmConstraintPlugin) this.getPlugIn())
						.self_explain(expl);
					((PalmIntVar) this.v1).self_explain(
						PalmIntDomain.VAL,
						value - this.cste,
						expl);
					((PalmIntVar) this.v0).removeVal(value, this.cIdx0, expl);
				}
			}
			values = ((PalmIntVar) this.v1).getAllValues();
			for (int i = 0; i < values.length; i++) {
				final int value = values[i];
				if (!this.v0.canBeInstantiatedTo(value + this.cste)) {
					final choco.palm.explain.Explanation expl =
						pb.makeExplanation();
					((PalmConstraintPlugin) this.getPlugIn())
						.self_explain(expl);
					((PalmIntVar) this.v0).self_explain(
						PalmIntDomain.VAL,
						value + this.cste,
						expl);
					((PalmIntVar) this.v1).removeVal(value, this.cIdx1, expl);
				}
			}
		}
		else {
			choco.palm.explain.Explanation expl = pb.makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v1).self_explain(PalmIntDomain.INF, expl);
			((PalmIntVar) this.v0).updateInf(
				this.v1.getInf() + this.cste,
				this.cIdx0,
				expl);

			expl = pb.makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v1).self_explain(PalmIntDomain.SUP, expl);
			((PalmIntVar) this.v0).updateSup(
				this.v1.getSup() + this.cste,
				this.cIdx0,
				expl);

			expl = pb.makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).self_explain(PalmIntDomain.INF, expl);
			((PalmIntVar) this.v1).updateInf(
				this.v0.getInf() - this.cste,
				this.cIdx1,
				expl);

			expl = pb.makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).self_explain(PalmIntDomain.SUP, expl);
			((PalmIntVar) this.v1).updateSup(
				this.v0.getSup() - this.cste,
				this.cIdx1,
				expl);
		}
	}

	public String toString() {
		return this.v0 + " == " + this.v1 + " + " + this.cste;
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
