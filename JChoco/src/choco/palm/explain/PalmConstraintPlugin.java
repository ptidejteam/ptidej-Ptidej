//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.explain;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import choco.Constraint;
import choco.ConstraintCollection;
import choco.model.IConstraintPlugin;
import choco.palm.PalmConstraint;
import choco.palm.PalmProblem;

public class PalmConstraintPlugin implements IConstraintPlugin {

	/**
	 * Global time stamp for dating all constraint plugins when activating.
	 */

	private static int PALM_TIME_STAMP;

	/**
	 * Constraint associated with this plugin.
	 */

	private final Constraint touchedConstraint;

	/**
	 * Time stamp of this plugin (updated when the constraint is activated).
	 */

	private int timeStamp = 0;

	/**
	 * Weight of the constraint.
	 */

	private int weight = Integer.MAX_VALUE;

	/**
	 * States if the constraint is already connected to the involved variables.
	 */

	private boolean everConnected = false;

	/**
	 * States if the constraint is indirect, that is if it depends on other constraints.
	 */

	private boolean indirect = false;

	/**
	 * Explanations for indirect constraints. It contains all the constraints it depends on.
	 */

	//private Set addingExplanation;
	private BitSet addingExplanation;

	/**
	 * Depending constraints, that is constraints that depend on this one.
	 */

	private final BitSet dependingConstraints;

	/**
	 * a.k.a. the alpha set, this is the explanations of filtering decisions depending on this constraint.
	 */

	private final Set dependencyNet;

	/**
	 * List of controlling constraints (boolean constraints for instance).
	 */

	private final List controllingConstraints;

	/**
	 * Open slot for search use.
	 */

	private SearchInfo searchInfo;

	/**
	 * The index of the constraint in the problem
	 */
	private int constraintIdx = -1;

	/**
	 * Constructs a plugin for a constraint.
	 * @param constraint The constraint this plugin is created for. It should be a <code>PalmConstraint</code>.
	 */

	public PalmConstraintPlugin(final Constraint constraint) {
		this.touchedConstraint = constraint;
		this.dependencyNet = new HashSet();
		this.dependingConstraints = new BitSet();
		this.addingExplanation = new BitSet();
		this.controllingConstraints = new LinkedList();
	}

	/**
	 * Activates the current constraint.
	 */

	private void activate() {
		this.timeStamp = PalmConstraintPlugin.PALM_TIME_STAMP;
		PalmConstraintPlugin.PALM_TIME_STAMP++;
	}

	/**
	 * Reacts when this listener is reactivated.
	 */

	public void activateListener() {
		this.activate();
	}

	/**
	 * Adds a controlling constraint.
	 * @param father A new controlling constraint.
	 * @param index The index of this constraint in the controlling one.
	 */

	public void addControl(final Constraint father, final int index) {
		this.controllingConstraints
			.add(new PalmControlConstraint(father, index));
	}

	/**
	 * Adds the explain of a depending filtering decision.
	 * @param e The explain of the filtering decision.
	 */

	public void addDependency(final ConstraintCollection e) {
		this.dependencyNet.add(e);
	}

	/**
	 * Adds a depending constraint.
	 * @param constraint The constraint to add as depending.
	 */

	void addDependingConstraint(final Constraint constraint) {
		this.dependingConstraints.set(((PalmConstraintPlugin) constraint
			.getPlugIn()).constraintIdx);
		//this.dependingConstraints.add(constraint);
	}

	/**
	 * Reacts when this constraint is added as listener on each involved variable.
	 */

	public void addListener() {
		this.activate();
		this.everConnected = true;
	}

	/**
	 * Deactivates the current constraint.
	 */

	private void deactivate() {
		//IConstraint[] constraints = new IConstraint[dependingConstraints.size()];
		//constraints = (IConstraint[]) dependingConstraints.toArray(constraints);
		final BitSet bitset = (BitSet) this.dependingConstraints.clone();

		for (int i = bitset.nextSetBit(0); i >= 0; i = bitset.nextSetBit(i + 1)) {
			//for (int i = 0; i < constraints.length; i++) {
			final Constraint constraint =
				((PalmProblem) this.touchedConstraint.getProblem())
					.getConstraintNb(i);
			//PalmConstraintPlugin plugin = (PalmConstraintPlugin)constraints[i].getPlugIn();
			final PalmConstraintPlugin plugin =
				(PalmConstraintPlugin) constraint.getPlugIn();
			plugin.removeIndirectDependance();
			plugin.informControllersOfDeactivation();
			//constraints[i].setPassive();
			constraint.setPassive();
			plugin.setDirect();
		}

		if (this.dependingConstraints.cardinality() != 0) {
			Logger.getLogger("choco.palm").severe(
				"Depending Constraints size : "
						+ this.dependingConstraints.size());
			//Logger.getLogger("choco.palm").severe("First : " + dependingConstraints.iterator().next());
			Logger
				.getLogger("choco.palm")
				.severe(
					"***** In PalmConstraintPlugin.deactivate(), dependingConstraints should be empty !!!");
			Logger
				.getLogger("choco.palm")
				.severe(
					"***** Report bug and all possible information to jussien@emn.fr.");
		}
	}

	/**
	 * Reacts when this listener is deactivated.
	 */

	public void deactivateListener() {
		this.deactivate();
	}

	//TODO  
	public void deactivateListener(final int varIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return The associated number (determined when posting)
	 */

	public int getConstraintIdx() {
		return this.constraintIdx;
	}

	public SearchInfo getSearchInfo() {
		return this.searchInfo;
	}

	/**
	 * Time stamp of the last activation.
	 */

	public int getTimeStamp() {
		return this.timeStamp;
	}

	/**
	 * Gets the weight of the constraint.
	 * @return The weight contained in this plugin.
	 */

	public int getWeight() {
		return this.weight;
	}

	/**
	 * Informs all the constrolling constraints of modifications on this one.
	 */

	public void informControllersOfDeactivation() {
		for (int i = 0; i < this.controllingConstraints.size(); i++) {
			final PalmControlConstraint controler =
				(PalmControlConstraint) this.controllingConstraints.get(i);
			((PalmConstraint) controler.getConstraint())
				.takeIntoAccountStatusChange(controler.getIndex());
		}
	}

	/**
	 * Checks if this constraint is ever connected.
	 */

	public boolean isEverConnected() {
		return this.everConnected;
	}

	/**
	 * Checks if the constraint is indirect.
	 */

	public boolean isIndirect() {
		return this.indirect;
	}

	/**
	 * Removes the explain of a depending filtering decision.
	 * @param e The explain of the filtering decision.
	 */

	public void removeDependency(final ConstraintCollection e) {
		this.dependencyNet.remove(e);
	}

	/**
	 * Removes a depending constraint.
	 * @param constraint The constraint to remove from depending ones.
	 */

	void removeDependingConstraint(final Constraint constraint) {
		this.dependingConstraints.clear(((PalmConstraintPlugin) constraint
			.getPlugIn()).constraintIdx);
		//this.dependingConstraints.remove(constraint);
	}

	/**
	 * Removes indirect dependance of this constraint: it updates the depending constraints list of all the
	 * constraints this one depends on.
	 */

	public void removeIndirectDependance() {
		for (int i = this.addingExplanation.nextSetBit(0); i >= 0; i =
			this.addingExplanation.nextSetBit(i + 1)) {
			final Constraint constraint =
				((PalmProblem) this.touchedConstraint.getProblem())
					.getConstraintNb(i);
			((PalmConstraintPlugin) constraint.getPlugIn())
				.removeDependingConstraint(this.touchedConstraint);
		}
		//for (Iterator iterator = addingExplanation.iterator(); iterator.hasNext();) {
		//    IConstraint constraint = (IConstraint) iterator.next();
		//    ((PalmConstraintPlugin)constraint.getPlugIn()).removeDependingConstraint(this.touchedConstraint);
		//}
	}

	/**
	 * Computes the explain associated to this constraint: either this constraint itself if direct,
	 * or the <code>addingExplanation</code> if indirect.
	 * @param e The explain on which this explain should be merged.
	 */

	public void self_explain(final ConstraintCollection e) {
		if (this.indirect) {
			((Explanation) e).merge(this.addingExplanation);
		}
		else {
			e.add(this.touchedConstraint);
		}
	}

	/**
	 * Sets the constraint number during the post of teh variable.
	 * @param nb Number affected to the constraint.
	 */

	public void setConstraintIdx(final int nb) {
		this.constraintIdx = nb;
	}

	/**
	 * Sets the constraint direct.
	 */

	public void setDirect() {
		this.addingExplanation = null;
		this.indirect = false;
	}

	/**
	 * Sets the constraint indirect.
	 * @param e The explain containing all the constraint this one depends on.
	 */

	public void setIndirect(final Explanation e) {
		this.addingExplanation = e.toBitSet();
		this.indirect = true;
	}

	/**
	 * Sets indirect dependance of this constraint: it updates the depending constraints list of all the
	 * constraints this one depends on.
	 * @param e An explain containing all the constraint, this one should depend on.
	 */

	public void setIndirectDependance(final Explanation e) {
		for (final Iterator iterator = e.toSet().iterator(); iterator.hasNext();) {
			final Constraint constraint = (Constraint) iterator.next();
			((PalmConstraintPlugin) constraint.getPlugIn())
				.addDependingConstraint(this.touchedConstraint);
		}
	}

	public void setSearchInfo(final SearchInfo searchInfo) {
		this.searchInfo = searchInfo;
	}

	/**
	 * Sets the weight of the constraint.
	 * @param weight The new weight.
	 */

	public void setWeight(final int weight) {
		this.weight = weight;
	}

	/**
	 * Removes past effects of the constraint in order to remove it.
	 */

	public void undo() {
		for (final Iterator iterator = this.dependencyNet.iterator(); iterator
			.hasNext();) {
			final Explanation explanation = (Explanation) iterator.next();
			explanation.postUndoRemoval(this.touchedConstraint);
		}
		this.dependencyNet.clear();
	}
}
