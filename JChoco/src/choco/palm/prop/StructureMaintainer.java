//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: François Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm.prop;

import choco.Constraint;
import choco.integer.IntVar;
import choco.palm.integer.PalmIntVar;
import choco.palm.integer.PalmIntVarListener;
import choco.util.IntIterator;

/**
 * Created by IntelliJ IDEA.
 * User: rochart
 * Date: Jan 14, 2004
 * Time: 11:25:01 AM
 * To change this template use Options | File Templates.
 */
public final class StructureMaintainer {
	public static void updateDataStructures(
		final IntVar var,
		final int select,
		final int newValue,
		final int oldValue) {
		StructureMaintainer.updateDataStructuresOnVariable(
			var,
			select,
			newValue,
			oldValue);
		StructureMaintainer.updateDataStructuresOnConstraints(
			var,
			select,
			newValue,
			oldValue);
	}

	public static void updateDataStructuresOnConstraints(
		final IntVar var,
		final int select,
		final int newValue,
		final int oldValue) {
		final Constraint[] constraints = var.getConstraints();
		final Integer[] indices = var.getIndices();

		for (final IntIterator cit =
			((PalmIntVar) var).getEvent().getAllListenersButIterator(-1); cit
			.hasNext();) {
			final int index = cit.next();
			((PalmIntVarListener) constraints[index])
				.updateDataStructuresOnConstraint(
					indices[index].intValue(),
					select,
					newValue,
					oldValue);
		}
	}

	public static void updateDataStructuresOnRestore(
		final IntVar var,
		final int select,
		final int newValue,
		final int oldValue) {
		StructureMaintainer.updateDataStructuresOnRestoreVariable(
			var,
			select,
			newValue,
			oldValue);
		StructureMaintainer.updateDataStructuresOnRestoreConstraints(
			var,
			select,
			newValue,
			oldValue);
	}

	public static void updateDataStructuresOnRestoreConstraints(
		final IntVar var,
		final int select,
		final int newValue,
		final int oldValue) {
		//AbstractVar v = getModifiedVar();
		final Constraint[] constraints = var.getConstraints();
		final Integer[] indices = var.getIndices();

		for (final IntIterator cit =
			((PalmIntVar) var).getEvent().getAllListenersButIterator(-1); cit
			.hasNext();) {
			final int index = cit.next();
			((PalmIntVarListener) constraints[index])
				.updateDataStructuresOnRestoreConstraint(
					indices[index].intValue(),
					select,
					newValue,
					oldValue);
		}
	}

	public static void updateDataStructuresOnRestoreVariable(
		final IntVar var,
		final int select,
		final int newValue,
		final int oldValue) {
		// A redefinir si necessaire
	}

	public static void updateDataStructuresOnVariable(
		final IntVar var,
		final int select,
		final int newValue,
		final int oldValue) {
		// A redefinir si necessaire
	}
}
