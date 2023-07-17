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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import choco.Constraint;
import choco.ConstraintCollection;
import choco.Problem;
import choco.palm.PalmProblem;
import choco.palm.integer.PalmIntVar;
import choco.palm.integer.explain.DecSupExplanation;
import choco.palm.integer.explain.IncInfExplanation;
import choco.palm.integer.explain.RemovalExplanation;

/**
 * Generic implementation of explanations. It is used by filtering algorithms before specializing them
 * for specific use (bound modification, value removal, contradiction...).
 */

public class GenericExplanation implements Explanation {
	private static int EXPLANATION_TIMESTAMP = 0;

	public static void reinitTimestamp() {
		GenericExplanation.EXPLANATION_TIMESTAMP = 0;
	}

	protected int timeStamp;

	/**
	 * Set of all the constraint in the explain.
	 */

	protected BitSet explanation;

	/**
	 * The current problem.
	 */

	protected PalmProblem pb;

	/**
	 * Initializes the explain set.
	 */

	public GenericExplanation(final Problem pb) {
		//this.explain = new HashSet();
		this.explanation = new BitSet();
		this.pb = (PalmProblem) pb;
		this.timeStamp = GenericExplanation.EXPLANATION_TIMESTAMP++;
	}

	/**
	 * Adds a new constraint in the explain.
	 * @param constraint The constraint that should be added to the explain.
	 * It must be a <code>PalmConstraint</code>.
	 */

	public void add(final Constraint constraint) {
		//this.explain.add(constraint);
		this.explanation.set(((PalmConstraintPlugin) constraint.getPlugIn())
			.getConstraintIdx());
	}

	public void addAll(final Collection collection) {
		for (final Iterator iterator = collection.iterator(); iterator
			.hasNext();) {
			final Constraint c = (Constraint) iterator.next();
			this.explanation.set(((PalmConstraintPlugin) c.getPlugIn())
				.getConstraintIdx());
		}
	}

	/**
	 * Updates dependencies.
	 */

	public void addDependencies() {
		//this.addDependencies(this.toSet());
		for (int i = this.explanation.nextSetBit(0); i >= 0; i =
			this.explanation.nextSetBit(i + 1)) {
			((PalmConstraintPlugin) this.pb.getConstraintNb(i).getPlugIn())
				.addDependency(this);
		}
	}

	/**
	 * Deletes all indirect constraints.
	 */

	public void clear() {
		final BitSet cp = (BitSet) this.explanation.clone();

		for (int i = cp.nextSetBit(0); i >= 0; i = cp.nextSetBit(i + 1)) {
			if (((PalmConstraintPlugin) this.pb.getConstraintNb(i).getPlugIn())
				.isIndirect()) {
				this.explanation.clear(i);
			}
		}
		/*Set constraints = this.toSet(); // Copie des contraintes

		for (Iterator iterator = constraints.iterator(); iterator.hasNext();) {
		    IConstraint constraint = (IConstraint) iterator.next();
		    if (((PalmConstraintPlugin)constraint.getPlugIn()).isIndirect()) {
		        this.delete(constraint);
		    }
		} */
	}

	/**
	 * Checks if the explain contains a constraint.
	 * @param constraint The constraint to search.
	 */

	public boolean contains(final Constraint constraint) {
		//return (this.explain.contains(constraint));
		return this.explanation.get(((PalmConstraintPlugin) constraint
			.getPlugIn()).getConstraintIdx());
	}

	/**
	 * Checks if another explain is included in this one.
	 * @param expl The explain that is tested to be included.
	 */

	public boolean containsAll(final ConstraintCollection expl) {
		final GenericExplanation exp = (GenericExplanation) expl;
		for (int i = exp.explanation.nextSetBit(0); i >= 0; i =
			exp.explanation.nextSetBit(i + 1)) {
			if (!this.explanation.get(i)) {
				return false;
			}
		}
		// TODO : gerer le cas ou ce n'estpas une GenericExplanation... avec un set
		//for (Iterator iterator = expl.toSet().iterator(); iterator.hasNext();) {
		//    IConstraint constraint = (IConstraint) iterator.next();
		//    if (!this.contains(constraint)) return false;
		//}
		return true;
	}

	/**
	 * Clones the explain as a new one.
	 */

	public ConstraintCollection copy() {
		final GenericExplanation expl = new GenericExplanation(this.pb);
		expl.merge(this);
		return expl;
	}

	/**
	 * Deletes a constraint from the explain.
	 * @param constraint The constraint that must be removed.
	 */

	public void delete(final Constraint constraint) {
		//this.explain.remove(constraint);
		this.explanation.clear(((PalmConstraintPlugin) constraint.getPlugIn())
			.getConstraintIdx());
	}

	/**
	 * Clears the constraint set.
	 */

	public void empties() {
		//this.explain.clear();
		this.explanation.clear();
	}

	public int hashCode() {
		return this.timeStamp;
	}

	/**
	 * Checks if the explain is empty (that is wether the size of the set is null).
	 */

	public boolean isEmpty() {
		//return (this.explain.size() == 0);
		return this.explanation.cardinality() == 0;
	}

	/**
	 * Checks if the explain is valid, that is wether all the constraint are active.
	 */

	public boolean isValid() {
		for (int i = this.explanation.nextSetBit(0); i >= 0; i =
			this.explanation.nextSetBit(i + 1)) {
			if (!this.pb.getConstraintNb(i).isActive()) {
				return false;
			}
		}
		//for (Iterator iterator = explain.iterator(); iterator.hasNext();) {
		//    IConstraint constraint = (IConstraint) iterator.next();
		//    if (!constraint.isActive()) return false;
		//}
		return true;
	}

	/**
	 * Makes a DecSupExplanation from the current explain by adding dependencies.
	 * @param sup The previous value of the bound.
	 * @param var The involved variable.
	 */

	public DecSupExplanation makeDecSupExplanation(
		final int sup,
		final PalmIntVar var) {
		Logger
			.getLogger("choco.palm")
			.fine("Make Sup Explanation for : " + var);
		final DecSupExplanation expl =
			new DecSupExplanation(this.pb, this.explanation, sup, var);
		expl.addDependencies();
		return expl;
	}

	/**
	 * Makes an IncInfExplanation from the current explain by adding dependencies.
	 * @param inf The previous value of the bound.
	 * @param var The involved variable.
	 */

	public IncInfExplanation makeIncInfExplanation(
		final int inf,
		final PalmIntVar var) {
		Logger
			.getLogger("choco.palm")
			.fine("Make Inf Explanation for : " + var);
		final IncInfExplanation expl =
			new IncInfExplanation(this.pb, this.explanation, inf, var);
		expl.addDependencies();
		return expl;
	}

	/**
	 * Makes a RemovalExplanation from the current explain by adding dependencies.
	 * @param value The removed value of the domain.
	 * @param var The involved variable.
	 */

	public RemovalExplanation makeRemovalExplanation(
		final int value,
		final PalmIntVar var) {
		final RemovalExplanation expl =
			new RemovalExplanation(this.pb, this.explanation, value, var);
		expl.addDependencies();
		return expl;
	}

	/**
	 * Adds several constraints.
	 * @param set The set of constraints
	 */

	//public void addAll(Set set) {
	//    for (Iterator iterator = set.iterator(); iterator.hasNext();) {
	//        IConstraint constraint = (IConstraint) iterator.next();
	//        this.explanationB.set(((PalmConstraintPlugin)constraint.getPlugIn()).getConstraintIdx());
	//    }
	//this.explain.addAll(set);
	//}

	public void merge(final BitSet set) {
		this.explanation.or(set);
	}

	/**
	 * Merges an explain in the current one.
	 * @param explanation The explain with the constraints to add.
	 */

	public void merge(final ConstraintCollection explanation) {
		//this.explain.addAll(explain.toSet());
		this.explanation.or(((GenericExplanation) explanation).explanation);
		// TODO : prevoir le cas ou ce n'est pas vrai : utilisation de addAll(Set) !
	}

	/**
	 * Posts a restoration prop.
	 * @param constraint
	 */

	public void postUndoRemoval(final Constraint constraint) {
		Logger.getLogger("choco.palm").warning(
			"GenericExplanation.postUndoRemoval should not be used !!");
	}

	///**
	// * Update dependencies.
	// * @param constSet The set of constraint that the dependencies should be updated.
	// */

	/*private void addDependencies(Set constSet) {
	for (Iterator iterator = constSet.iterator(); iterator.hasNext();) {
	IConstraint constraint = (IConstraint) iterator.next();
	((PalmConstraintPlugin)constraint.getPlugIn()).addDependency(this);
	}
	} */

	/**
	 * Removes all dependencies except for one constraint.
	 * @param removed
	 */

	public void removeDependencies(final Constraint removed) {
		for (int i = this.explanation.nextSetBit(0); i >= 0; i =
			this.explanation.nextSetBit(i + 1)) {
			final Constraint constraint = this.pb.getConstraintNb(i);
			if (constraint != removed) {
				((PalmConstraintPlugin) constraint.getPlugIn())
					.removeDependency(this);
			}
		}
		/*for (Iterator iterator = this.toSet().iterator(); iterator.hasNext();) {
		    IConstraint constraint = (IConstraint) iterator.next();
		    if (constraint != removed) {
		        ((PalmConstraintPlugin)constraint.getPlugIn()).removeDependency(this);
		    }
		}*/
	}

	/**
	 * Get the size of the bitSet
	 * @return the size of the explanation
	 */

	public int size() {
		return this.explanation.cardinality();
	}

	/**
	 * Copies the explain set and returns the new bitset.
	 * @return The explain as a BitSet.
	 */

	public BitSet toBitSet() {
		return (BitSet) this.explanation.clone();
	}

	/**
	 * Creates a set with all the constraints in the explain.
	 * @return The explain as a set.
	 */

	public Set toSet() {
		final Set ret = new HashSet();
		for (int i = this.explanation.nextSetBit(0); i >= 0; i =
			this.explanation.nextSetBit(i + 1)) {
			ret.add(this.pb.getConstraintNb(i));
		}
		//ret.addAll(explain);
		return ret;
	}

	/**
	 * Pretty print of the explain.
	 */

	public String toString() {
		final StringBuffer str = new StringBuffer();
		str.append("{");

		for (int i = this.explanation.nextSetBit(0); i >= 0; i =
			this.explanation.nextSetBit(i + 1)) {
			str.append(this.pb.getConstraintNb(i));
			if (this.explanation.nextSetBit(i + 1) >= 0) {
				str.append(", ");
			}
		}
		/*for (Iterator iterator = explain.iterator(); iterator.hasNext();) {
		    str.append(iterator.next());
		    if (iterator.hasNext()) str.append(", ");
		} */

		str.append("}");
		return str.toString();
	}
}
