//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm;

public interface PalmConstraint extends choco.Constraint, PalmVarListener {

	/**
	 * Informs constraints that one of their children has a modified status (due to value restoration).
	 */

	public void takeIntoAccountStatusChange(int index);

	//   /**
	//    * Checks if the constraint is already satisfied (<code>true</code>) or not (<code>false</code>) w.r.t.
	//    * the current state os the search. If the current state does not allow to determine it, the
	//    * <code>null</code> value is returned.
	//    */

	//    public Boolean askIfEntailed();
}
