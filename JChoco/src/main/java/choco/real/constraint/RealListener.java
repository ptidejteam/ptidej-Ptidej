package choco.real.constraint;

import choco.ContradictionException;
import choco.model.IVarListener;

/**
 * An interface for real event listener (like real constraints for instance).
 */
public interface RealListener extends IVarListener {
	/**
	 * Default propagation on improved lower bound: propagation on domain revision.
	 */

	public void awakeOnInf(int idx) throws ContradictionException;

	/**
	 * Default propagation on improved upper bound: propagation on domain revision.
	 */

	public void awakeOnSup(int idx) throws ContradictionException;
}
