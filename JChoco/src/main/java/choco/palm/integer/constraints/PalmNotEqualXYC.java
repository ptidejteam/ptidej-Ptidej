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
import java.util.logging.Logger;
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
 * User: grochart
 * Date: 26 ao?t 2003
 * Time: 10:57:55
 * To change this template use Options | File Templates.
 */
public class PalmNotEqualXYC extends AbstractPalmBinIntConstraint {
	protected final int cste;

	public PalmNotEqualXYC(final IntVar v0, final IntVar v1, final int cste) {
		this.v0 = v0;
		this.v1 = v1;
		this.cste = cste;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void awakeOnInf(final int idx) {
		if (idx == 0 && this.v0.getInf() == this.v0.getSup()) {
			final choco.palm.explain.Explanation expl =
				((PalmProblem) this.getProblem()).makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, expl);
			if (this.v1.getInf() == this.v0.getInf() - this.cste) {
				((PalmIntVar) this.v1).updateInf(this.v0.getInf() - this.cste
						+ 1, this.cIdx1, expl);
			}
			if (this.v1.getSup() == this.v0.getInf() - this.cste) {
				((PalmIntVar) this.v1).updateSup(this.v0.getInf() - this.cste
						- 1, this.cIdx1, (Explanation) expl.copy());
			}
		}
		else if (idx == 1 && this.v1.getInf() == this.v1.getSup()) {
			final choco.palm.explain.Explanation expl =
				((PalmProblem) this.getProblem()).makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v1).self_explain(PalmIntDomain.DOM, expl);
			if (this.v0.getInf() == this.v1.getInf() + this.cste) {
				((PalmIntVar) this.v0).updateInf(this.v1.getInf() + this.cste
						+ 1, this.cIdx0, expl);
			}
			if (this.v0.getSup() == this.v1.getInf() + this.cste) {
				((PalmIntVar) this.v0).updateSup(this.v1.getInf() + this.cste
						- 1, this.cIdx0, (Explanation) expl.copy());
			}
		}
	}

	public void awakeOnRem(final int idx, final int val) {
		if (idx == 0) {
			if (this.v0.getDomainSize() == 1) {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, expl);
				((PalmIntVar) this.v1).removeVal(
					((PalmIntVar) this.v0).getInf() - this.cste,
					this.cIdx1,
					expl);
			}
		}
		else {
			if (this.v1.getDomainSize() == 1) {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v1).self_explain(PalmIntDomain.DOM, expl);
				((PalmIntVar) this.v0).removeVal(
					((PalmIntVar) this.v1).getInf() + this.cste,
					this.cIdx0,
					expl);
			}
		}
	}

	public void awakeOnRestoreInf(final int idx) {
		this.awakeOnInf(idx);
	}

	public void awakeOnRestoreSup(final int idx) {
		this.awakeOnInf(idx);
	}

	public void awakeOnRestoreVal(final int idx, final int val) {
		this.awakeOnRem(idx, val);
	}

	public void awakeOnSup(final int idx) {
		this.awakeOnInf(idx);
	}

	public Boolean isEntailed() {
		if (this.v0.getSup() < this.v1.getInf() + this.cste
				|| this.v1.getSup() < this.v0.getInf() - this.cste) {
			return Boolean.TRUE;
		}
		else if (this.v0.getInf() == this.v0.getSup()
				&& this.v1.getInf() == this.v1.getSup()
				&& this.v0.getInf() == this.v1.getInf() + this.cste) {
			return Boolean.FALSE;
		}
		return null;
	}

	public boolean isSatisfied() {
		return true;
	}

	public void propagate() throws ContradictionException {
		if (this.v0.hasEnumeratedDomain() && this.v1.hasEnumeratedDomain()) {
			if (this.v0.getDomainSize() == 1) {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, expl);
				((PalmIntVar) this.v1).removeVal(
					((PalmIntVar) this.v0).getInf() - this.cste,
					this.cIdx1,
					expl);
			}
			if (this.v1.getDomainSize() == 1) {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v1).self_explain(PalmIntDomain.DOM, expl);
				((PalmIntVar) this.v0).removeVal(
					((PalmIntVar) this.v1).getInf() + this.cste,
					this.cIdx0,
					expl);
			}
		}
		else {
			if (this.v0.getInf() == this.v0.getSup()) {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, expl);
				if (this.v1.getInf() == this.v0.getInf() - this.cste) {
					((PalmIntVar) this.v1).updateInf(this.v0.getInf()
							- this.cste + 1, this.cIdx1, expl);
				}
				if (this.v1.getSup() == this.v0.getInf() - this.cste) {
					((PalmIntVar) this.v1).updateSup(
						this.v0.getInf() - this.cste - 1,
						this.cIdx1,
						(Explanation) expl.copy());
				}
			}
			else if (this.v1.getInf() == this.v1.getSup()) {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v1).self_explain(PalmIntDomain.DOM, expl);
				if (this.v0.getInf() == this.v1.getInf() + this.cste) {
					((PalmIntVar) this.v0).updateInf(this.v1.getInf()
							+ this.cste + 1, this.cIdx0, expl);
				}
				if (this.v0.getSup() == this.v1.getInf() + this.cste) {
					((PalmIntVar) this.v0).updateSup(
						this.v1.getInf() + this.cste - 1,
						this.cIdx0,
						(Explanation) expl.copy());
				}
			}
		}
	}

	public String toString() {
		return this.v0 + " !== " + this.v1 + " + " + this.cste;
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
