package choco.real.exp;

import choco.ContradictionException;
import choco.Problem;
import choco.real.RealExp;
import choco.real.RealInterval;
import choco.real.RealMath;

/**
 * An expression modelling a multiplication.
 */
public class RealMult extends AbstractRealBinTerm {
	public RealMult(final Problem pb, final RealExp exp1, final RealExp exp2) {
		super(pb, exp1, exp2);
	}

	public void project() throws ContradictionException {
		RealInterval res = RealMath.odiv_wrt(this, this.exp2, this.exp1);
		if (res.getInf() > res.getSup()) {
			this.problem.getPropagationEngine().raiseContradiction();
		}
		this.exp1.intersect(res);

		res = RealMath.odiv_wrt(this, this.exp1, this.exp2);
		if (res.getInf() > res.getSup()) {
			this.problem.getPropagationEngine().raiseContradiction();
		}
		this.exp2.intersect(res);
	}

	public void tighten() {
		final RealInterval res = RealMath.mul(this.exp1, this.exp2);
		this.inf.set(res.getInf());
		this.sup.set(res.getSup());
	}
}
