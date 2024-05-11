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
package ptidej.solver.java.constraint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import util.io.ProxyConsole;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmLargeIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.util.IntIterator;
import ptidej.solver.java.Constraint;
import ptidej.solver.java.Problem;
import ptidej.solver.java.Variable;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.approximation.IApproximations;
import ptidej.solver.java.domain.Entity;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayeal Skhiri
 */
public abstract class BinaryCounterConstraint extends
		AbstractPalmLargeIntConstraint implements Constraint {
	public static Set getTheList(Entity e, String fieldName) {
		//allReachableSuperEntities

		Class aClass = Entity.class;
		String methodeName = "geT" + fieldName;
		Method method = null;
		Set list = null;
		try {
			method = aClass.getMethod(methodeName, (Class[]) null);

			//										
		}
		catch (final Exception e1) {
			e1.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		try {
			list = (Set) method.invoke(e, (Object[]) null);
		}
		catch (final IllegalArgumentException e2) {
			e2.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalAccessException e2) {
			e2.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InvocationTargetException e2) {
			e2.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return list;

	}

	private String command = null;
	private String fieldName = null;

	public BinaryCounterConstraint(
		final int n,
		final String name,
		final String command,
		final Variable v0,
		final Variable v1,
		final PalmIntVar counter,
		final int weight) {

		super(n);
		this.getName();
		this.command = command;
		this.vars = new IntVar[] { v0, v1, counter };
	}
	public void awakeOnRem(int idx, int index_e) {
		this.propagate();
	}
	public void awakeOnRestoreVal(int idx, int val)
			throws ContradictionException {
		this.propagate();
	}
	public void doAwake() throws ContradictionException {
		this.propagate();
	}
	public IApproximations getApproximations() {
		return DefaultNoApproximations.getDefaultApproximations();
	}
	protected String getFieldName() {
		return this.fieldName;
	}
	public String getName() {
		return null;
	}
	public String getNextConstraint() {
		return null;
	}
	public Class getNextConstraintConstructor(String nextConstraint) {
		return null;
	}
	public String getSymbol() {
		return null;
	}
	public int getWeight() {
		return 0;
	}
	public String getXCommand() {
		return this.command;
	}
	public boolean isSatisfied() {
		return false;
	}

	public void propagate() {
		List allEntities = ((Problem) this.getProblem()).getAllEntities();

		//cleaning the  v0 domain
		IntIterator iterator0 = this.vars[0].getDomain().getIterator();
		while (iterator0.hasNext()) {
			int index_e0 = iterator0.next();
			boolean toBeRemoved = true;
			//we test whether the entity (index_e0) is present in the v1 domain

			Entity e0 = (Entity) allEntities.get(index_e0);
			//Set e0_superEntities = e0.getSuperEntities();
			Set theList = getTheList(e0, this.fieldName);

			Iterator itr = theList.iterator();
			while (itr.hasNext() && toBeRemoved) {
				//Entity supEntity = (Entity) itr.next();
				Entity entity = (Entity) itr.next();
				int index_entity = allEntities.indexOf(entity);

				if (this.vars[1].canBeInstantiatedTo(index_entity))
					toBeRemoved = false;

			}

			if (toBeRemoved) {
				choco.palm.explain.Explanation expl =
					((Problem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((Variable) this.vars[1]).self_explain(PalmIntDomain.DOM, expl);
				((Variable) this.vars[0]).removeVal(
					index_e0,
					this.cIndices[0],
					expl);
			}

		} //end of cleaning the  v0 domain
			//		cleaning the  v1 domain

		IntIterator iterator1 = this.vars[1].getDomain().getIterator();
		while (iterator1.hasNext()) {
			int index_e1 = iterator1.next();
			boolean toBeRemoved = true;

			//we test whether the entity (index_e1) is present in the v0 domain

			Entity e1 = (Entity) allEntities.get(index_e1);
			IntIterator itr0 = this.vars[0].getDomain().getIterator();
			while (itr0.hasNext() && toBeRemoved) {
				int index_e0 = itr0.next();
				Entity e0 = (Entity) allEntities.get(index_e0);
				Set theList = getTheList(e0, this.fieldName);

				if (theList.contains(e1))
					toBeRemoved = false;

			}

			if (toBeRemoved) {
				choco.palm.explain.Explanation expl =
					((Problem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((Variable) this.vars[0]).self_explain(PalmIntDomain.DOM, expl);
				((Variable) this.vars[1]).removeVal(
					index_e1,
					this.cIndices[1],
					expl);
			}

		} //end of cleaning the  v1 domain

	}
	protected void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	protected void setStrict(boolean flag) {

	}
	protected String setSymbol(String symbol) {
		return null;
	}
	public Set whyIsFalse() {
		return null;
	}
	public Set whyIsTrue() {
		return null;
	}
}
