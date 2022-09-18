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
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmBinIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;

public class PalmGreaterOrEqualXYC extends AbstractPalmBinIntConstraint {
	protected final int cste;

	public PalmGreaterOrEqualXYC(
		final IntVar v0,
		final IntVar v1,
		final int cste) {
		this.v0 = v0;
		this.v1 = v1;
		this.cste = cste;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void awakeOnInf(final int idx) {
		if (idx == 1 && this.v1.getInf() + this.cste > this.v0.getInf()) {
			if (this.v0.hasEnumeratedDomain()) {
				//if (v1.getInf() + this.cste > v0.getInf()) {
				final int[] values = ((PalmIntVar) this.v0).getAllValues();
				final choco.ConstraintCollection expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				int index = 0;
				int min =
					((PalmIntDomain) this.v1.getDomain()).getOriginalInf();
				while (index < values.length
						&& values[index] < this.v1.getInf() + this.cste) {
					for (int i = min; i <= values[index] - this.cste; i++) {
						((PalmIntVar) this.v1).self_explain(
							PalmIntDomain.VAL,
							i,
							expl);
					}
					((PalmIntVar) this.v0).removeVal(
						values[index],
						this.cIdx0,
						(Explanation) expl.copy());
					min = values[index] + 1 - this.cste;
					index++;
				}
				//}
			}
			else {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v1).self_explain(PalmIntDomain.INF, expl);
				((PalmIntVar) this.v0).updateInf(
					this.v1.getInf() + this.cste,
					this.cIdx0,
					expl);
			}
		}
	}

	public void awakeOnRem(final int idx, final int v) {
		if (idx == 0) {
			this.awakeOnSup(0);
		}
		else {
			this.awakeOnInf(1);
		}
		/*
		// A priori ca marche, mais a surveiller :)

		if (idx == 1) {
		  if (v1.getInf() + this.cste > v0.getInf()) {
		    int[] values = ((PalmIntVar) v0).getAllValues();
		    choco.palm.explain.Explanation expl = new choco.palm.explain.GenericExplanation(this.getProblem());
		    ((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
		    int id = 0;
		    int min = ((PalmIntDomain) v1.getDomain()).getOriginalInf();
		    while (id < values.length && values[id] - this.cste < v1.getInf()) {
		      for (int i = min; i <= values[id] - this.cste; i++)
		        ((PalmIntVar) this.v1).self_explain(PalmIntDomain.VAL, i, expl);
		      ((PalmIntVar) this.v0).removeVal(values[id], this.cIdx0, expl.copy());
		      min = values[id] + 1 - this.cste;
		      id++;
		    }
		  }
		} else {
		  if (v1.getSup() + this.cste > v0.getSup()) {
		    int[] values = ((PalmIntVar) v1).getAllValues();
		    choco.palm.explain.Explanation expl = new choco.palm.explain.GenericExplanation(this.getProblem());
		    ((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
		    int id = values.length - 1;
		    int max = ((PalmIntDomain) v0.getDomain()).getOriginalSup();
		    while (id >= 0 && values[id] + this.cste > v0.getSup()) {
		      for (int i = max; i >= values[id] + this.cste; i--)
		        ((PalmIntVar) this.v0).self_explain(PalmIntDomain.VAL, i, expl);
		      ((PalmIntVar) this.v1).removeVal(values[id], this.cIdx1, expl.copy());
		      max = values[id] - 1 + this.cste;
		      id--;
		    }
		  }
		} */
	}

	public void awakeOnRestoreInf(final int idx) {
		if (idx == 0) {
			this.awakeOnInf(1);
		}
	}

	public void awakeOnRestoreSup(final int idx) {
		if (idx == 1) {
			this.awakeOnSup(0);
		}
	}

	public void awakeOnRestoreVal(final int idx, final int val) {
		if (idx == 1) {
			this.awakeOnSup(0);
		}
		else {
			this.awakeOnInf(1);
		}
		// A priori ca marche, mais a surveiller :)
		/*if (idx == 1) {
		  if (v1.getInf() + this.cste > val) {
		    choco.palm.explain.Explanation expl = new choco.palm.explain.GenericExplanation(this.getProblem());
		    ((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
		    for (int i = ((PalmIntDomain) v1.getDomain()).getOriginalInf(); i <= val - this.cste; i++)
		      ((PalmIntVar) this.v1).self_explain(PalmIntDomain.VAL, i, expl);
		    ((PalmIntVar) this.v0).removeVal(val, this.cIdx0, expl);
		  }
		} else {
		  if (val + this.cste > v0.getSup()) {
		    choco.palm.explain.Explanation expl = new choco.palm.explain.GenericExplanation(this.getProblem());
		    ((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
		    for (int i = ((PalmIntDomain) v0.getDomain()).getOriginalSup(); i >= val + this.cste; i--)
		      ((PalmIntVar) this.v0).self_explain(PalmIntDomain.VAL, i, expl);
		    ((PalmIntVar) this.v1).removeVal(val, this.cIdx1, expl);
		  }
		} */
	}

	public void awakeOnSup(final int idx) {
		if (idx == 0 && this.v1.getSup() + this.cste > this.v0.getSup()) {
			if (this.v1.hasEnumeratedDomain()) {
				//if (v1.getSup() + this.cste > v0.getSup()) {
				final int[] values = ((PalmIntVar) this.v1).getAllValues();
				final choco.ConstraintCollection expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				int index = values.length - 1;
				int max =
					((PalmIntDomain) this.v0.getDomain()).getOriginalSup();
				while (index >= 0
						&& values[index] + this.cste > this.v0.getSup()) {
					for (int i = max; i >= values[index] + this.cste; i--) {
						((PalmIntVar) this.v0).self_explain(
							PalmIntDomain.VAL,
							i,
							expl);
					}
					((PalmIntVar) this.v1).removeVal(
						values[index],
						this.cIdx1,
						(Explanation) expl.copy());
					max = values[index] - 1 + this.cste;
					index--;
				}
				//}
			}
			else {
				final choco.palm.explain.Explanation expl =
					((PalmProblem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((PalmIntVar) this.v0).self_explain(PalmIntDomain.SUP, expl);
				((PalmIntVar) this.v1).updateSup(
					this.v0.getSup() - this.cste,
					this.cIdx1,
					expl);
			}
		}
	}

	public Boolean isEntailed() {
		if (this.v0.getSup() < this.v1.getInf() + this.cste) {
			return Boolean.FALSE;
		}
		if (this.v0.getInf() >= this.v1.getSup() + this.cste) {
			return Boolean.TRUE;
		}
		return null;
	}

	public boolean isSatisfied() {
		return this.v0.getValue() >= this.v1.getSup() + this.cste;
	}

	public void propagate() {
		this.awakeOnInf(1);
		this.awakeOnSup(0);
		/*if (this.v0.hasEnumeratedDomain()) {

		  if (v1.getInf() + this.cste > v0.getInf()) {
		    int[] values = ((PalmIntVar) v0).getAllValues();
		    choco.palm.explain.Explanation expl = new choco.palm.explain.GenericExplanation(this.getProblem());
		    ((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
		    int index = 0;
		    int min = ((PalmIntDomain) v1.getDomain()).getOriginalInf();
		    while (index < values.length && values[index] < v1.getInf() + this.cste) {
		      for (int i = min; i <= values[index] - this.cste; i++)
		        ((PalmIntVar) this.v1).self_explain(PalmIntDomain.VAL, i, expl);
		      ((PalmIntVar) this.v0).removeVal(values[index], this.cIdx0, expl.copy());
		      min = values[index] + 1 - this.cste;
		      index++;
		    }
		  }
		} else {
		  this.awakeOnInf(1);
		}

		if (this.v1.hasEnumeratedDomain()) {
		  if (v1.getSup() + this.cste > v0.getSup()) {
		    int[] values = ((PalmIntVar) v1).getAllValues();
		    choco.palm.explain.Explanation expl = new choco.palm.explain.GenericExplanation(this.getProblem());
		    ((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
		    int index = values.length - 1;
		    int max = ((PalmIntDomain) v0.getDomain()).getOriginalSup();
		    while (index >= 0 && values[index] + this.cste > v0.getSup()) {
		      for (int i = max; i >= values[index] + this.cste; i--)
		        ((PalmIntVar) this.v0).self_explain(PalmIntDomain.VAL, i, expl);
		      ((PalmIntVar) this.v1).removeVal(values[index], this.cIdx1, expl.copy());
		      max = values[index] - 1 + this.cste;
		      index--;
		    }
		  }
		} else {
		  this.awakeOnSup(0);
		} */
	}

	public String toString() {
		return this.v0 + " >= " + this.v1 + " + " + this.cste;
	}

	public Set whyIsFalse() {
		final choco.palm.explain.Explanation expl =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmIntVar) this.v0).self_explain(PalmIntDomain.SUP, expl);
		((PalmIntVar) this.v1).self_explain(PalmIntDomain.INF, expl);
		return expl.toSet();
	}

	public Set whyIsTrue() {
		final choco.palm.explain.Explanation expl =
			((PalmProblem) this.getProblem()).makeExplanation();
		((PalmIntVar) this.v0).self_explain(PalmIntDomain.INF, expl);
		((PalmIntVar) this.v1).self_explain(PalmIntDomain.SUP, expl);
		return expl.toSet();
	}
}
