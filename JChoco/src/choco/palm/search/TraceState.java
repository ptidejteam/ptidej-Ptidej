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

import choco.Constraint;

/**
 * Created by IntelliJ IDEA.
 * User: grochart
 * Date: 7 janv. 2004
 * Time: 15:43:05
 * To change this template use Options | File Templates.
 */
public class TraceState extends PalmState {

	public TraceState(final choco.palm.explain.Explanation expl) {
		super(expl);
	}

	public void addDecision(final Constraint constraint) {
		System.out.println("Constraint " + constraint + " added.");
		super.addDecision(constraint);
	}

	public void removeDecision(final Constraint constraint) {
		System.out.println("Constraint " + constraint + " removed.");
		super.removeDecision(constraint);
	}
}
