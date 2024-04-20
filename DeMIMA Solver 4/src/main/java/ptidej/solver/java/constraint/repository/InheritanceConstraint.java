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

import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.IApproximations;
import ptidej.solver.java.constraint.AbstractInheritanceConstraint;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class InheritanceConstraint extends AbstractInheritanceConstraint {
	public InheritanceConstraint(
		final String name,
		final String commande,
		final Variable v0,
		final Variable v1,
		final int weight,
		final IApproximations approximations) {

		super(name, commande, v0, v1, weight, approximations);
		this.setSymbol("-|>- or =");
		this.setFieldName("superEntities");
	}

	//	public void propagate()
	//	{
	//		List allEntities = ((JPtidejProblem) this.getProblem()).getAllEntities();
	//
	//		//cleaning the  v0 domain
	//		IntIterator iterator0 = v0.getDomain().getIterator();
	//		while (iterator0.hasNext())
	//		{
	//			int index_e0 = iterator0.next();
	//			boolean toBeRemoved = true;
	//			//we test whether the entity (index_e0) is present in the v1 domain
	//			if (v1.canBeInstantiatedTo(index_e0))
	//				toBeRemoved = false;
	//			else //we test whether the entity (index_e0) has a super-entity present in the v1 domain
	//				{
	//				Entity e0 = (Entity) allEntities.get(index_e0);
	//				Set e0_superEntities = e0.getSuperEntities();
	//
	//				Iterator itr = e0_superEntities.iterator();
	//				while (itr.hasNext() && toBeRemoved)
	//				{
	//					Entity supEntity = (Entity) itr.next();
	//					int index_supEntity = allEntities.indexOf(supEntity);
	//
	//					if (v1.canBeInstantiatedTo(index_supEntity))
	//						toBeRemoved = false;
	//
	//				}
	//			} //end of else
	//
	//			if (toBeRemoved)
	//			{
	//				choco.palm.explain.Explanation expl = ((PalmProblem) this.getProblem()).makeExplanation();
	//				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
	//				((PalmIntVar) this.v1).self_explain(PalmIntDomain.DOM, expl);
	//				((JPtidejVar) this.v0).removeVal(index_e0, this.cIdx0, expl);
	//			}
	//
	//		} //end of cleaning the  v0 domain
	//
	//		//cleaning the  v1 domain
	//
	//		IntIterator iterator1 = v1.getDomain().getIterator();
	//		while (iterator1.hasNext())
	//		{
	//			int index_e1 = iterator1.next();
	//			boolean toBeRemoved = true;
	//
	//			//we test whether the entity (index_e1) is present in the v0 domain
	//			if (v0.canBeInstantiatedTo(index_e1))
	//				toBeRemoved = false;
	//			else //we test whether the entity (index_e1) is a super-entity of an entity in  v0 domain
	//				{
	//				Entity e1 = (Entity) allEntities.get(index_e1);
	//				IntIterator itr0 = v0.getDomain().getIterator();
	//				while (itr0.hasNext() && toBeRemoved)
	//				{
	//					int index_e0 = itr0.next();
	//					Entity e0 = (Entity) allEntities.get(index_e0);
	//
	//					//if (index_e0 != index_e1)
	//					{
	//						Set e0_superEntities = e0.getSuperEntities();
	//						if (e0_superEntities.contains(e1))
	//							toBeRemoved = false;
	//					}
	//
	//				}
	//			} //end of else
	//
	//			if (toBeRemoved)
	//			{
	//				choco.palm.explain.Explanation expl = ((PalmProblem) this.getProblem()).makeExplanation();
	//				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
	//				((PalmIntVar) this.v0).self_explain(PalmIntDomain.DOM, expl);
	//				((JPtidejVar) v1).removeVal(index_e1, this.cIdx1, expl);
	//			}
	//
	//		} //end of cleaning the  v0 domain
	//
	//	} //end of propagate()

	//	public void awakeOnRem(int idx, int index_e)
	//	{
	//		this.propagate();
	//	}
	//
	//	public void awakeOnRestoreVal(int idx, int val) throws ContradictionException
	//	{
	//		this.propagate();
	//	}
	//
	//	public void doAwake() throws ContradictionException
	//	{
	//		this.propagate();
	//
	//	}
}
