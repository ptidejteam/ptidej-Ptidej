package choco.real;

import choco.Var;
import choco.real.var.RealDomain;

/**
 * An interface for a real variable : an expression and a variable with a domain.
 */
public interface RealVar extends Var, RealExp {
	public RealDomain getDomain();

	/**
	 * Modifies bounds silently (does not propagate modifications). This is usefull for box cosistency.
	 * @param i
	 */
	void silentlyAssign(RealInterval i);
}
