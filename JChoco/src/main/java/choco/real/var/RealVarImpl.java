package choco.real.var;

import java.util.List;
import java.util.Set;
import choco.AbstractVar;
import choco.ContradictionException;
import choco.Problem;
import choco.real.RealInterval;
import choco.real.RealVar;

/**
 * An implementation of real variables using RealDomain domains.
 */
public class RealVarImpl extends AbstractVar implements RealVar {
	protected RealDomain domain;

	public RealVarImpl(
		final Problem pb,
		final String name,
		final double a,
		final double b) {
		this.problem = pb;
		this.name = name;
		this.domain = new RealDomainImpl(this, a, b);
		this.event = new RealVarEvent(this);
	}

	public Set collectVars(final Set s) {
		s.add(this);
		return s;
	}

	public void fail() throws ContradictionException {
		this.problem.getChocEngine().raiseContradiction(this);
	}

	public RealDomain getDomain() {
		return this.domain;
	}

	public double getInf() {
		return this.domain.getInf();
	}

	public double getSup() {
		return this.domain.getSup();
	}

	public void intersect(final RealInterval interval)
			throws ContradictionException {
		this.domain.intersect(interval);
	}

	public boolean isInstantiated() {
		return this.getInf() == this.getSup();
	}

	public boolean isolate(final RealVar var, final List wx, final List wox) {
		if (this == var) {
			return true;
		}
		else {
			return false;
		}
	}

	/*public int addConstraint(Constraint c, int varIdx, boolean active) { // TODO : a mettre dans AbstractVar !!
	  constraints.add(c);
	  indices.add(Integer.valueOf(varIdx));

	  int constraintIdx = constraints.size() - 1;

	  this.event.addListener(constraintIdx, active);

	  return constraintIdx;
	}

	public void deactivateConstraint(int constraintIdx) { // TODO : a mettre dans AbstractVar !!
	  this.event.deactivateListener(constraintIdx);
	}

	public void activateConstraint(int constraintIdx) { // TODO : a mettre dans AbstractVar !!
	  this.event.activateListener(constraintIdx);
	}  */

	public void project() {
	}

	public void silentlyAssign(final RealInterval i) {
		this.domain.silentlyAssign(i);
	}

	public List subExps(final List l) {
		l.add(this);
		return l;
	}

	public void tighten() {
	}

	public String toString() {
		return this.name + "[" + this.getInf() + "," + this.getSup() + "]";
	}
}
