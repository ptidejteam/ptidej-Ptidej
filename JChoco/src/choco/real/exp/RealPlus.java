package choco.real.exp;

import choco.ContradictionException;
import choco.Problem;
import choco.real.RealExp;
import choco.real.RealInterval;
import choco.real.RealMath;

/**
 * An expression modelling a substraction.
 */
public class RealPlus extends AbstractRealBinTerm {
	public RealPlus(final Problem pb, final RealExp exp1, final RealExp exp2) {
		super(pb, exp1, exp2);
	}

	public void project() throws ContradictionException {
		this.exp1.intersect(RealMath.sub(this, this.exp2));
		this.exp2.intersect(RealMath.sub(this, this.exp1));
	}

	public void tighten() {
		final RealInterval res = RealMath.add(this.exp1, this.exp2);
		this.inf.set(res.getInf());
		this.sup.set(res.getSup());
	}
}
