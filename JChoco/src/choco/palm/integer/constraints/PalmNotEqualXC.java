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
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmUnIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;

/**
 * Created by IntelliJ IDEA.
 * User: grochart
 * Date: 26 ao?t 2003
 * Time: 09:18:02
 * To change this template use Options | File Templates.
 */
public class PalmNotEqualXC extends AbstractPalmUnIntConstraint {
	protected final int cste;

	public PalmNotEqualXC(final IntVar v0, final int cste) {
		this.v0 = v0;
		this.cste = cste;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void awakeOnInf(final int idx) {
		if (this.v0.getInf() == this.cste) {
			final choco.palm.explain.Explanation expl =
				((PalmProblem) this.getProblem()).makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).updateInf(this.cste + 1, this.cIdx0, expl);
		}
	}

	public void awakeOnRem() {
	}

	public void awakeOnRestoreInf(final int idx) {
		this.awakeOnInf(idx);
	}

	public void awakeOnRestoreSup(final int idx) {
		this.awakeOnSup(idx);
	}

	public void awakeOnRestoreVal(final int idx, final int val) {
		if (this.cste == val) {
			final choco.palm.explain.Explanation expl =
				((PalmProblem) this.getProblem()).makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).removeVal(val, this.cIdx0, expl);
		}
	}

	public void awakeOnSup(final int idx) {
		if (this.v0.getSup() == this.cste) {
			final choco.palm.explain.Explanation expl =
				((PalmProblem) this.getProblem()).makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).updateInf(this.cste - 1, this.cIdx0, expl);
		}
	}

	public Boolean isEntailed() {
		if (this.v0.isInstantiatedTo(this.cste)) {
			return Boolean.FALSE;
		}
		else if (!this.v0.canBeInstantiatedTo(this.cste)) {
			return Boolean.TRUE;
		}
		return null;
	}

	public boolean isSatisfied() {
		return true;
	}

	public void propagate() {
		if (this.v0.hasEnumeratedDomain()) {
			final choco.palm.explain.Explanation expl =
				((PalmProblem) this.getProblem()).makeExplanation();
			((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
			((PalmIntVar) this.v0).removeVal(this.cste, this.cIdx0, expl);
		}
		else {
			if (this.v0.getInf() == this.cste) {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v0).updateInf(
					this.cste + 1,
					this.cIdx0,
					expl);
			}
			if (this.v0.getSup() == this.cste) {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v0).updateSup(
					this.cste - 1,
					this.cIdx0,
					expl);
			}
		}
	}

	public String toString() {
		return this.v0 + " !== " + this.cste;
	}

	public Set whyIsFalse() {
		final choco.palm.explain.Explanation expl =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, expl);
		return expl.toSet();
	}

	public Set whyIsTrue() {
		final choco.palm.explain.Explanation expl =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmIntVar) this.v0).self_explain(PalmIntDomain.VAL, this.cste, expl);
		return expl.toSet();
	}
}
