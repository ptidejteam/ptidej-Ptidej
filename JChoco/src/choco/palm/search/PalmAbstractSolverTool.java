//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.search;

public abstract class PalmAbstractSolverTool {
	protected PalmSolver manager;

	public PalmSolver getManager() {
		return this.manager;
	}

	public void setManager(final PalmSolver manager) {
		this.manager = manager;
	}
}
