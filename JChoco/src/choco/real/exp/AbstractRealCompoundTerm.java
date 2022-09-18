package choco.real.exp;

import choco.AbstractEntity;
import choco.ContradictionException;
import choco.Problem;
import choco.mem.Environment;
import choco.mem.StoredFloat;
import choco.real.RealExp;
import choco.real.RealInterval;

/**
 * A compound expression depending on other terms.
 */
public abstract class AbstractRealCompoundTerm extends AbstractEntity implements
		RealExp {
	protected StoredFloat inf;
	protected StoredFloat sup;

	public AbstractRealCompoundTerm(final Problem pb) {
		this.problem = pb;
		final Environment env = pb.getEnvironment();
		this.inf = env.makeFloat(Double.NEGATIVE_INFINITY);
		this.sup = env.makeFloat(Double.POSITIVE_INFINITY);
	}

	public double getInf() {
		return this.inf.get();
	}

	public double getSup() {
		return this.sup.get();
	}

	public void intersect(final RealInterval interval)
			throws ContradictionException {
		if (interval.getInf() > this.inf.get()) {
			this.inf.set(interval.getInf());
		}
		if (interval.getSup() < this.sup.get()) {
			this.sup.set(interval.getSup());
		}
		if (this.inf.get() > this.sup.get()) {
			this.problem.getPropagationEngine().raiseContradiction();
		}
	}

	public String toString() {
		return "[" + this.inf.get() + "," + this.sup.get() + "]";
	}
}
