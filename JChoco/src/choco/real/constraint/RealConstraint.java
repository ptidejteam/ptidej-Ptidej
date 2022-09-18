package choco.real.constraint;

import choco.Constraint;
import choco.real.RealVar;

/**
 * An interface for float constraints.
 */
public interface RealConstraint extends Constraint, RealListener {
	public RealVar getRealVar(int i);
	public int getRealVarNb();
}
