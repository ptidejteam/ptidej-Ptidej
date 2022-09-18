//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.integer;

import choco.Constraint;
import choco.ConstraintCollection;
import choco.palm.explain.Explanation;

/**
 * Created by IntelliJ IDEA.
 * User: grochart
 * Date: 7 janv. 2004
 * Time: 13:50:57
 * To change this template use Options | File Templates.
 */
public interface PalmIntDomain {
	int DOM = 0;
	int INF = 1;
	int SUP = 2;
	int VAL = 3;

	/**
	 * Returns all the value currently in the domain.
	 */

	public int[] getAllValues();

	/**
	 * Returns the decision constraint assigning the domain to the specified value. The constraint is created if
	 * it is not yet created.
	 */

	public Constraint getDecisionConstraint(int val);

	/**
	 * Returns the negated decision constraint.
	 */

	public Constraint getNegDecisionConstraint(int val);

	/**
	 * Returns the original lower bound.
	 */

	public int getOriginalInf();

	/**
	 * Returns the original upper bound.
	 */

	public int getOriginalSup();

	/**
	 * Removes a value and posts the event.
	 */

	public boolean removeVal(int value, int idx, Explanation e);

	/**
	 * When a lower bound is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnInf();

	/**
	 * When an upper bound is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnSup();

	/**
	 * When a value is restored, it deletes the explanation associated to the value removal.
	 */

	public void resetExplanationOnVal(int val);

	/**
	 * Restores a lower bound and posts the event. Not supported for such a domain.
	 */

	public void restoreInf(int newValue);

	/**
	 * Restores an upper bound and posts the event. Not supported for such a domain.
	 */

	public void restoreSup(int newValue);

	/**
	 * Restores a value and posts the event.
	 */

	public void restoreVal(int val);

	/**
	 * Allows to get an explanation for the domain or a bound of the variable. This explanation is merge to the
	 * explanation in parameter.
	 * @param select Should be <code>PalmIntDomain.INF</code>, <code>PalmIntDomain.SUP</code>, or <code>PalmIntDomain.DOM</code>
	 */

	public void self_explain(int select, ConstraintCollection expl);

	/**
	 * Allows to get an explanation for a value removal from the variable. This explanation is merge to the
	 * explanation in parameter.
	 * @param select Should be <code>PalmIntDomain.VAL</code>
	 */

	public void self_explain(int select, int x, ConstraintCollection expl);

	/**
	 * Updates the lower bound and posts the event.
	 */

	public boolean updateInf(int x, int idx, Explanation e);

	/**
	 * Updates the upper bound and posts the event.
	 */

	public boolean updateSup(int x, int idx, Explanation e);
}
