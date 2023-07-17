package choco.real.constraint;

import choco.Constraint;
import choco.integer.IntVar;
import choco.model.IIntListener;
import choco.real.RealVar;

/**
 * An interface for mixed constraint : interger and flot variables.
 */
public interface MixedConstraint extends Constraint, RealListener, IIntListener {
	public IntVar getIntVar(int i);
	public int getIntVarNb();
	public RealVar getRealVar(int i);
	public int getRealVarNb();
}
