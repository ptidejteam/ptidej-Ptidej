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
package ptidej.solver.constraint.repository;

import ptidej.solver.Variable;
import ptidej.solver.approximation.IApproximations;
import ptidej.solver.constraint.AbstractInheritanceConstraint;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class StrictInheritanceConstraint
	extends AbstractInheritanceConstraint {
	public StrictInheritanceConstraint(
		final String name,
		final String commande,
		final Variable v0,
		final Variable v1,
		final int weight,
		final IApproximations approximations) {

		super(name, commande, v0, v1, weight, approximations);
		this.setSymbol("-|>-");
		this.setStrict(true);
		this.setFieldName("superEntities");
	}

	//	public void awakeOnRem(int idx, int index_e) {
	//		List allEntities = ((Problem) this.getProblem()).getAllEntities();
	//		Entity remEntity = (Entity) allEntities.get(index_e);
	//		if (idx == 0) {
	//			Set remEntity_superEntities = remEntity.getSuperEntities();
	//			Iterator itr = remEntity_superEntities.iterator();
	//
	//			while (itr.hasNext()) {
	//				//I check the super entities of the removed entity from V0
	//				//if one (Ei) of these super entities is not a super entity 
	//				//of one of te remaining entity
	//				//I remove this entity (Ei) from V1
	//				Entity sup_remEntity = (Entity) itr.next();
	//				int index_sup_remEntity = allEntities.indexOf(sup_remEntity);
	//				boolean existAsSupEntity = false;
	//
	//				IntIterator iterator0 = v0.getDomain().getIterator();
	//				while (iterator0.hasNext()) {
	//					int index_e0 = iterator0.next();
	//					Entity e0 = (Entity) allEntities.get(index_e0);
	//					Set e0_superEntities = e0.getSuperEntities();
	//					if (e0_superEntities.contains(sup_remEntity))
	//						existAsSupEntity = true;
	//
	//				}
	//				if (!existAsSupEntity) {
	//					if (v1.canBeInstantiatedTo(index_sup_remEntity)) {
	//						choco.palm.explain.Explanation expl =
	//							((PalmProblem) this.getProblem())
	//								.makeExplanation();
	//						(
	//							(PalmConstraintPlugin) this
	//								.getPlugIn())
	//								.self_explain(
	//							expl);
	//						((PalmIntVar) this.v0).self_explain(
	//							PalmIntDomain.DOM,
	//							expl);
	//						((Variable) v1).removeVal(
	//							index_sup_remEntity,
	//							this.cIdx1,
	//							expl);
	//
	//					}
	//				}
	//			}
	//		}
	//		else {
	//			//a bug was fixed!!
	//			//I check if the removed entity in V1 is the only 
	//			//super entity of an entity Ei from V0, I removed this entity Ei
	//			//from V0 
	//			IntIterator iterator0 = v0.getDomain().getIterator();
	//			while (iterator0.hasNext()) {
	//				int index_e0 = iterator0.next();
	//				Entity e0 = (Entity) allEntities.get(index_e0);
	//				Set e0_superEntities = e0.getSuperEntities();
	//
	//				if (e0_superEntities.contains(remEntity)) {
	//					e0_superEntities.remove(remEntity);
	//					boolean existAnOtherSupEntity = false;
	//
	//					Iterator itr = e0_superEntities.iterator();
	//					while (itr.hasNext()) {
	//						Entity e0_supEntity = (Entity) itr.next();
	//						int index_e0_supEntity =
	//							allEntities.indexOf(e0_supEntity);
	//
	//						if (v1.canBeInstantiatedTo(index_e0_supEntity))
	//							existAnOtherSupEntity = true;
	//					}
	//
	//					if (!existAnOtherSupEntity) {
	//						choco.palm.explain.Explanation expl =
	//							((PalmProblem) this.getProblem())
	//								.makeExplanation();
	//						(
	//							(PalmConstraintPlugin) this
	//								.getPlugIn())
	//								.self_explain(
	//							expl);
	//						((PalmIntVar) this.v1).self_explain(
	//							PalmIntDomain.VAL,
	//							index_e0, // TODO: Is it the right value?
	//							expl);
	//						((Variable) v0).removeVal(index_e0, this.cIdx0, expl);
	//					}
	//
	//				}
	//
	//			}
	//		}
	//	}
}
