package choco.real.var;

import choco.AbstractEntity;
import choco.ContradictionException;
import choco.Problem;
import choco.mem.Environment;
import choco.mem.StoredFloat;
import choco.prop.VarEvent;
import choco.real.RealInterval;
import choco.real.RealVar;

/**
 * An implmentation of real variable domains using two stored floats for storing bounds.
 */
public class RealDomainImpl extends AbstractEntity implements RealDomain {

	//public double width_zero = 1.e-8;
	//public double reduction_factor = 0.99;

	/**
	 * for the delta domain: current value of the inf (domain lower bound) when the bound started beeing propagated
	 * (just to check that it does not change during the propagation phase)
	 */
	protected double currentInfPropagated = Double.NEGATIVE_INFINITY;

	/**
	 * for the delta domain: current value of the sup (domain upper bound) when the bound started beeing propagated
	 * (just to check that it does not change during the propagation phase)
	 */
	protected double currentSupPropagated = Double.POSITIVE_INFINITY;

	protected StoredFloat inf;

	protected StoredFloat sup;

	protected RealVar variable;

	public RealDomainImpl(final RealVar v, final double a, final double b) {
		this.variable = v;
		this.problem = v.getProblem();
		final Environment env = this.problem.getEnvironment();
		this.inf = env.makeFloat(a);
		this.sup = env.makeFloat(b);
	}

	public void clearDeltaDomain() {
		this.currentInfPropagated = Double.NEGATIVE_INFINITY;
		this.currentSupPropagated = Double.POSITIVE_INFINITY;
	}

	public void freezeDeltaDomain() {
		this.currentInfPropagated = this.getInf();
		this.currentSupPropagated = this.getSup();
	}

	public double getInf() {
		return this.inf.get();
	}

	public Problem getProblem() {
		return this.problem;
	}

	public boolean getReleasedDeltaDomain() {
		return true;
	}

	public double getSup() {
		return this.sup.get();
	}

	public void intersect(final RealInterval interval)
			throws ContradictionException {
		if (interval.getInf() > this.getSup()
				|| interval.getSup() < this.getInf()) {
			throw new ContradictionException(this);
		}

		final double old_width = this.getSup() - this.getInf();
		final double new_width = interval.getSup() - interval.getInf();
		final boolean toAwake =
			this.variable.getProblem().getPrecision() / 100. <= new_width
					&& new_width < old_width
							* this.variable.getProblem().getReduction();

		if (interval.getInf() > this.getInf()) {
			if (toAwake) {
				this.problem.getChocEngine().postUpdateInf(
					this.variable,
					VarEvent.NOCAUSE);
			}
			this.inf.set(interval.getInf());
		}

		if (interval.getSup() < this.getSup()) {
			if (toAwake) {
				this.problem.getChocEngine().postUpdateSup(
					this.variable,
					VarEvent.NOCAUSE);
			}
			this.sup.set(interval.getSup());
		}
	}

	public String pretty() {
		// TODO
		return null;
	}

	public boolean releaseDeltaDomain() {
		final boolean noNewUpdate =
			this.getInf() == this.currentInfPropagated
					&& this.getSup() == this.currentSupPropagated;
		this.currentInfPropagated = Double.NEGATIVE_INFINITY;
		this.currentSupPropagated = Double.POSITIVE_INFINITY;
		return noNewUpdate;
	}

	public void silentlyAssign(final RealInterval i) {
		this.inf.set(i.getInf());
		this.sup.set(i.getSup());
	}
}
