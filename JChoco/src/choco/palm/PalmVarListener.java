//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm;

import choco.model.IVarListener;

public interface PalmVarListener extends IVarListener {

	/**
	 * Method called when one of the variables is modified.
	 * @param idx Index of the variable.
	 * @param select Type of modification (<code>PalmIntVar.INF</code>, <code>PalmIntVar.SUP</code>,
	 * <code>PalmIntVar.VAL</code>).
	 * @param newValue New value (or modified value).
	 * @param oldValue Old value (or 0 if this a value removal).
	 */

	public void updateDataStructuresOnConstraint(
		int idx,
		int select,
		int newValue,
		int oldValue);

	/**
	 * Method called when one of the variables has a restored value.
	 * @param idx Index of the variable.
	 * @param select Type of modification.
	 * @param newValue New value (or modified value).
	 * @param oldValue Old value (or 0 if this a value removal).
	 */

	public void updateDataStructuresOnRestoreConstraint(
		int idx,
		int select,
		int newValue,
		int oldValue);
}
