//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer;

import java.util.Set;
import choco.ContradictionException;
import choco.model.IIntListener;
import choco.palm.PalmVarListener;
import choco.util.IntIterator;

public interface PalmIntVarListener extends PalmVarListener, IIntListener {

	/**
	 * Handles an inf bound restoration on the constraint <code>idx</code>
	 * @param idx Variable involved.
	 * @throws ContradictionException
	 */

	public void awakeOnRestoreInf(int idx) throws ContradictionException;

	/**
	 * Handles a sup bound restoration on the constraint <code>idx</code>
	 * @param idx Variable involved.
	 * @throws ContradictionException
	 */

	public void awakeOnRestoreSup(int idx) throws ContradictionException;

	/**
	 * Handles a val restoration on the constraint <code>idx</code>
	 * @param idx Variable involved.
	 * @throws ContradictionException
	 */

	public void awakeOnRestoreVal(int idx, int val)
			throws ContradictionException;

	/**
	 * Handles a val restoration on the constraint <code>idx</code>
	 * @param idx Variable involved.
	 * @throws ContradictionException
	 */

	public void awakeOnRestoreVal(int idx, IntIterator it)
			throws ContradictionException;

	/**
	 * When all variables are instantiated, explains why the wonstraint is false.
	 * @return A set of constraint justifying that the constraint is not satisfied.
	 */

	public Set whyIsFalse();

	/**
	 * When all variables are instantiated, explains why the wonstraint is true.
	 * @return A set of constraint justifying that the constraint is satisfied.
	 */

	public Set whyIsTrue();
}
