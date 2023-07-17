package choco.palm.search.pathrepair;

import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import choco.Constraint;
import choco.ConstraintCollection;
import choco.ContradictionException;
import choco.palm.PalmProblem;
import choco.palm.explain.Explanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.constraints.PalmAssignment;
import choco.palm.prop.PalmEngine;
import choco.palm.search.PalmLearn;
import choco.palm.search.PalmSolver;

public class PathRepairLearn extends PalmLearn {

	protected int maxMoves = 1500;
	protected LinkedList explanations;
	protected int maxSize = 7;

	public PathRepairLearn(
		final int lSize,
		final int nMoves,
		final PalmSolver man) {
		this.maxMoves = nMoves;
		this.maxSize = lSize;
		this.explanations = new LinkedList();
		this.setManager(man);
	}

	/**
	 *  Update the tabou list of nogood
	 * @param nogood
	 */
	public void addForbiddenSituation(final ConstraintCollection nogood) {
		if (this.explanations.size() == this.maxSize) {
			this.explanations.removeLast(); // Il n'y a pas besoin de faire un reset des serachInfo ici ?
		}
		this.explanations.addFirst(nogood);
	}

	public void assertValidSearchInfo(final Explanation expl) {
		final Iterator it = expl.toSet().iterator();
		for (; it.hasNext();) {
			final Constraint ct = (Constraint) it.next();
			if (((PalmConstraintPlugin) ct.getPlugIn()).getWeight() == 0) {
				final PathRepairSearchInfo sInfo =
					(PathRepairSearchInfo) ((PalmConstraintPlugin) ct
						.getPlugIn()).getSearchInfo();
				Assert.assertTrue(sInfo != null);
			}
		}
	}

	/**
	 * Check if the set of decision is acceptable by the solver
	 * Note : should be called : checkAcceptableExtension
	 * @param constraints
	 * @return
	 */
	public boolean checkAcceptable(final List constraints) {
		final PalmSolver man = this.getManager();
		final PalmProblem pb = (PalmProblem) man.getProblem();
		final ConstraintCollection situation = pb.makeExplanation();
		final Iterator it = constraints.iterator();
		for (; it.hasNext();) {
			final Constraint ct = (Constraint) it.next();
			if (((PalmConstraintPlugin) ct.getPlugIn()).isEverConnected()) {
				situation.add(ct);
			}
		}

		situation.merge(man.getState().getPath());
		return !this.isForbidden(situation);
	}

	public boolean checkAcceptableRelaxation(final Constraint ctout) {
		final PalmSolver man = this.getManager();
		final PalmProblem pb = (PalmProblem) man.getProblem();
		final Constraint ctin = ((PalmAssignment) ctout).negate();
		final ConstraintCollection situation = pb.makeExplanation();

		situation.merge(man.getState().getPath());
		situation.delete(ctout);
		//Iterator it = man.getState().getPath().toSet().iterator();
		//for (; it.hasNext();) {
		//  Constraint ct = (Constraint)it.next();
		//  if (ct != ctout) situation.add(ct);
		//}
		if (((PalmConstraintPlugin) ctin.getPlugIn()).isEverConnected()) {
			situation.add(ctin);
		}
		return !this.isForbidden(situation);
	}

	public void debugDecisionPath() {
		final Iterator it =
			this.getManager().getState().getPath().toSet().iterator();
		for (; it.hasNext();) {
			final Constraint ct = (Constraint) it.next();
			System.out.print(" - " + ct);
		}
		System.out.println();
	}

	public void debugMemory() {
		System.out.println("-----------");
		System.out.print("Chemin de decision :");
		this.debugDecisionPath();
		System.out.println("Memoire de DR :");
		final Iterator it = this.explanations.listIterator();
		for (; it.hasNext();) {
			this.debugNogood((Explanation) it.next());
			System.out.println();
		}
		System.out.println("-----------");
	}

	public void debugNogood(final Explanation nogood) {
		final Set no = nogood.toSet();
		final Iterator it = no.iterator();
		while (it.hasNext()) {
			final Constraint ct = (Constraint) it.next();
			System.out.print(""
					+ ct
					+ " : "
					+ ((PathRepairSearchInfo) ((PalmConstraintPlugin) ct
						.getPlugIn()).getSearchInfo()).getWeigth() + " ");
		}
	}

	/**
	 * maintain the searchInfo parameter on each constraint concerned by the conflict
	 * @param expl
	 */
	public void informConstraintsInExplanation(final Explanation expl) {
		if (!expl.isEmpty()) {
			final PalmProblem pb = (PalmProblem) this.getManager().getProblem();
			final float sCoef = 1 / (float) expl.size();
			//Iterator it = expl.toSet().iterator();
			final BitSet bset = expl.toBitSet(); // iterate on the bitset and avoid HashSet !!!!!!
			for (int i = bset.nextSetBit(0); i >= 0; i = bset.nextSetBit(i + 1)) {
				final Constraint ct = pb.getConstraintNb(i);
				PathRepairSearchInfo sInfo =
					(PathRepairSearchInfo) ((PalmConstraintPlugin) ct
						.getPlugIn()).getSearchInfo();
				if (sInfo == null) { // TODO : faire ca autrement
					sInfo = new PathRepairSearchInfo();
					((PalmConstraintPlugin) ct.getPlugIn())
						.setSearchInfo(sInfo);
				}
				sInfo.add(sCoef);
			}
		}
	}

	/**
	 * Check if the tabou list contains the given nogood
	 * @param nogood
	 */
	public boolean isForbidden(final ConstraintCollection nogood) {
		final Iterator it = this.explanations.listIterator();
		for (; it.hasNext();) {
			if (((Explanation) nogood).containsAll((Explanation) it.next())) {
				return true;
			}
		}
		return false;
	}

	public void learnFromContradiction(final Explanation expl)
			throws ContradictionException {
		final PalmSolver man = this.getManager();
		final PalmProblem pb = (PalmProblem) man.getProblem();
		if (man.getRuntimeStatistics(PalmProblem.RLX) > this.maxMoves) {
			((PalmEngine) pb.getPropagationEngine()).raiseSystemContradiction();
		}
		final BitSet bset = expl.toBitSet(); // iterate on the bitset and avoid HashSet !!!!!!
		final Explanation nogood = pb.makeExplanation(); // create the nogood associated to the conflict
		for (int i = bset.nextSetBit(0); i >= 0; i = bset.nextSetBit(i + 1)) {
			final Constraint ct = pb.getConstraintNb(i);
			if (((PalmConstraintPlugin) ct.getPlugIn()).getWeight() == 0) {
				nogood.add(ct);
			}
		}
		this.addForbiddenSituation(nogood); // add it in the tabou list
		this.informConstraintsInExplanation(nogood);
		//System.out.print("Nogood obtenu : ");
		//debugNogood(nogood);
		//System.out.println();
	}

	public void learnFromRemoval(final Constraint ct) {
		final PathRepairSearchInfo sInfo =
			(PathRepairSearchInfo) ((PalmConstraintPlugin) ct.getPlugIn())
				.getSearchInfo();
		//assert(sInfo != null);
		sInfo.set(0);
	}
}
