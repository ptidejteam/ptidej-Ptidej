package choco.real.exp;

import java.util.List;
import java.util.Set;
import choco.ContradictionException;
import choco.real.RealExp;
import choco.real.RealInterval;
import choco.real.RealVar;

/**
 * A constant real interval.
 */
public class RealIntervalConstant implements RealExp {
	protected final double inf;
	protected final double sup;

	public RealIntervalConstant(final double inf, final double sup) {
		this.inf = inf;
		this.sup = sup;
		//this.problem = pb;
	}

	public Set collectVars(final Set s) {
		return s;
	}

	public double getInf() {
		return this.inf;
	}

	public double getSup() {
		return this.sup;
	}

	public void intersect(final RealInterval interval)
			throws ContradictionException {
	}

	public boolean isolate(final RealVar var, final List wx, final List wox) {
		return false;
	}

	public String pretty() {
		return this.toString();
	}

	public void project() {
	}

	public List subExps(final List l) {
		l.add(this);
		return l;
	}

	public void tighten() {
	}

	public String toString() {
		return "[" + this.inf + "," + this.sup + "]";
	}
}
