/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.java.constraint.repository;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.PalmIntDomain;
import choco.util.IntIterator;
import ptidej.solver.java.Problem;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.IApproximations;
import ptidej.solver.java.constraint.BinaryConstraint;
import ptidej.solver.java.domain.Entity;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayeal Skhiri
 */
public class ListPropertyTypeConstraint extends BinaryConstraint {
	public ListPropertyTypeConstraint(final String name, final String commande,
			final Variable v0, final Variable v1, final int weight,
			final IApproximations approximations) {

		super(name, commande, v0, v1, weight, approximations);
	}

	public void propagate() {
		List allEntities = ((Problem) this.getProblem()).getAllEntities();

		//cleaning the  v0 domain
		IntIterator iterator0 = this.v0.getDomain().getIterator();
		while (iterator0.hasNext()) {
			int index_e0 = iterator0.next();
			Entity e0 = (Entity) allEntities.get(index_e0);

			final Set theList = BinaryConstraint.getEntityList(e0,
					this.getFieldName(), this.isNegative(),
					((Problem) this.getProblem()).getAllEntities());

			if (theList.size() == 0) {
				choco.palm.explain.Explanation expl = ((Problem) this
						.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((Variable) this.v0).self_explain(PalmIntDomain.VAL, index_e0,
						expl);
				((Variable) this.v0).removeVal(index_e0, this.cIdx0, expl);

			}

		} //end of cleaning the  v0 domain

		//cleaning the  v1 domain

		final IntIterator iterator1 = this.v1.getDomain().getIterator();
		String fieldName = this.getFieldName();
		while (iterator1.hasNext()) {
			int index_e1 = iterator1.next();
			boolean toBeRemoved = true;

			allEntities.get(index_e1);
			final IntIterator itr0 = this.v0.getDomain().getIterator();
			while (itr0.hasNext() && toBeRemoved) {
				final int index_e0 = itr0.next();
				final Entity e0 = (Entity) allEntities.get(index_e0);

				final Set theList = BinaryConstraint.getEntityList(e0,
						fieldName, this.isNegative(),
						((Problem) this.getProblem()).getAllEntities());
				//TODO : TheList should be a list of lists.

				Iterator iter2 = theList.iterator();
				while (iter2.hasNext()) {
					iter2.next();

					// TODO: Implement the right code!
					//	if (list.contains(e1)) {
					//		toBeRemoved = false;
					//		break;
					//	}
				}
			}

			if (toBeRemoved) {
				choco.palm.explain.Explanation expl = ((Problem) this
						.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((Variable) this.v0).self_explain(PalmIntDomain.DOM, expl);
				((Variable) this.v1).removeVal(index_e1, this.cIdx1, expl);
			}

		} //end of cleaning the  v0 domain

	} //end propagate()

	//-------------------------------------------------------------------------------------------------------------------
	public void awakeOnRem(int idx, int index_e) {
		List allEntities = ((Problem) this.getProblem()).getAllEntities();
		allEntities.get(index_e);
		String fieldName = this.getFieldName();
		if (idx == 0) {
			final Entity e = (Entity) allEntities.get(index_e);
			final Set theList = BinaryConstraint.getEntityList(e, fieldName,
					this.isNegative(),
					((Problem) this.getProblem()).getAllEntities());

			final Iterator itr = theList.iterator();
			while (itr.hasNext()) {
				itr.next();

				// TODO: Implement the right code!
				//	Iterator itr2 = componentsList.iterator();
				//	while (itr2.hasNext()) {
				//		Entity ent = (Entity) itr2.next();
				//		int index_ent = allEntities.indexOf(ent);
				//
				//		final IntIterator itr0 = this.v0.getDomain().getIterator();
				//		while (itr0.hasNext()) {
				//			final int index_e0 = itr0.next();
				//			if (index_e0 != index_e) {
				//				final Entity e0 =
				//					(Entity) allEntities.get(index_e0);
				//				final TIntSet list =
				//					BinConstraint.getEntityList(
				//						e0,
				//						fieldName,
				//						this.isNegative(),
				//						((Problem) this.getProblem())
				//							.getAllEntities());
				//
				//				final TIntIterator itr3 = list.iterator();
				//				while (itr3.hasNext()) {
				//					final int otherComponentsList = itr3.next();
				//					if (otherComponentsList.contains(ent))
				//						toBeRemoved = false;
				//				}
				//			}
				//		}
				//		if (toBeRemoved) {
				//			if (this.v1.canBeInstantiatedTo(index_ent)) {
				//				choco.palm.explain.Explanation expl =
				//					((Problem) this.getProblem()).makeExplanation();
				//				((PalmConstraintPlugin) this.getPlugIn())
				//					.self_explain(expl);
				//				((Variable) this.v0).self_explain(
				//					PalmIntDomain.VAL,
				//					index_e,
				//					expl);
				//				((Variable) this.v1).removeVal(
				//					index_ent,
				//					this.cIdx1,
				//					expl);
				//
				//			}
				//		}
				//	}
			}
		}
		else //if (idx == 1)
		{
			//bug was fixed!!
			//I check if the removed entity in V1 is the only 
			//super entity of an entity Ei from V0, I removed this entity Ei
			//from V0 
			IntIterator iterator0 = this.v0.getDomain().getIterator();
			while (iterator0.hasNext()) {
				final int index_e0 = iterator0.next();
				final Entity e0 = (Entity) allEntities.get(index_e0);

				boolean toBeRemoved = true;
				final Set theList = BinaryConstraint.getEntityList(e0,
						fieldName, this.isNegative(),
						((Problem) this.getProblem()).getAllEntities());

				final Iterator itr = theList.iterator();
				while (itr.hasNext() && toBeRemoved) {

					itr.next();
				}
				if (toBeRemoved) {
					choco.palm.explain.Explanation expl = ((Problem) this
							.getProblem()).makeExplanation();
					((PalmConstraintPlugin) this.getPlugIn())
							.self_explain(expl);
					((Variable) this.v1).self_explain(PalmIntDomain.VAL,
							index_e, expl);
					((Variable) this.v0).removeVal(index_e0, this.cIdx0, expl);
				}
			}

		}

	}
}
