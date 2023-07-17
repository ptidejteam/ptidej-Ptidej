package choco.palm.search.pathrepair;

import java.util.LinkedList;
import java.util.List;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.palm.prop.PalmEngine;
import choco.palm.search.PalmAssignVar;

/**
 * Created by IntelliJ IDEA.
 * User: Administrateur
 * Date: 15 janv. 2004
 * Time: 17:18:37
 * To change this template use Options | File Templates.
 */
public class PathRepairAssignVar extends PalmAssignVar {

	protected PalmIntVar variable;
	protected int value;

	/**
	*  Check if a decision is valid (according to the learner)
	*/
	public boolean checkAcceptable(final List csts) {
		return true;
	}

	/**
	 *  return the next possible decision (variable assignement) on the variable
	 * @param branchingItem
	 * @param previousBranch
	 */

	public Object getNextBranch(
		final Object branchingItem,
		final Object previousBranch) {
		final List list = new LinkedList();
		list.add(this.variable.getDecisionConstraint(this.value));
		this.value++;
		return list;
	}

	/**
	  *  check if the variable has been emptied
	  */

	public void learnFromRejection() {
		if (this.value == this.variable.getSup()) { // no more available value
			final Explanation expl =
				((PalmProblem) this.extender.getManager().getProblem())
					.makeExplanation();
			;
			this.variable.self_explain(PalmIntDomain.DOM, expl);
			((PalmEngine) this.extender
				.getManager()
				.getProblem()
				.getPropagationEngine()).raisePalmFakeContradiction(expl);
		}
	}

	public Object selectBranchingObject() {
		final IntVar var =
			(IntVar) ((PalmProblem) this.extender.getManager().getProblem())
				.getMinDomVar();
		if (var != null) {
			this.variable = (PalmIntVar) var;
			this.value = var.getInf();
		}
		return var;
	}

}
