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
package ptidej.solver.constraint;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import ptidej.solver.Constraint;
import ptidej.solver.Domain;
import ptidej.solver.Problem;
import ptidej.solver.Variable;
import ptidej.solver.approximation.IApproximations;
import ptidej.solver.domain.Entity;
import util.io.ProxyConsole;
import choco.ContradictionException;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmBinIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.util.IntIterator;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public abstract class BinaryConstraint extends AbstractPalmBinIntConstraint
		implements Constraint {

	private static TIntObjectMap EntityListCache = new TIntObjectHashMap();
	public static TIntSet getEntityList(
		final Entity anEntity,
		final String aFieldName,
		final boolean isNegativeConstraint,
		final List aListOfAllEntities) {

		// Yann 2007/11/22: Cache!
		// I assume that the list do not change as the
		// solver runs, so instead of polling the lists
		// on the fly at each call, I put them in a cache.
		final int key = anEntity.getName().hashCode() + aFieldName.hashCode();
		if (!BinaryConstraint.EntityListCache.containsKey(key)) {
			final Class aClass = Entity.class;
			final String methodeName =
				"get" + aFieldName.substring(0, 1).toUpperCase()
						+ aFieldName.substring(1);

			Method method = null;
			Set list = null;

			try {
				method = aClass.getMethod(methodeName, (Class[]) null);
			}
			catch (final Exception e1) {
				e1.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}

			try {
				list = (Set) method.invoke(anEntity, (Object[]) null);
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

			// Yann 2007/02/26: Negation!
			// I know handle directly negative constraint.
			// But this implementation is highly inefficient.
			if (isNegativeConstraint) {
				final List allEntities = aListOfAllEntities;
				final Set substractionSet = new HashSet(allEntities);
				substractionSet.removeAll(list);
				list = substractionSet;
			}

			final TIntSet listOfIndices = new TIntHashSet(list.size());
			final Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				final Entity entity = (Entity) iterator.next();
				listOfIndices.add(aListOfAllEntities.indexOf(entity));
			}

			BinaryConstraint.EntityListCache.put(key, listOfIndices);
		}

		final TIntSet list =
			(TIntSet) BinaryConstraint.EntityListCache.get(key);
		return list;
	}
	public static void resetEntityList() {
		BinaryConstraint.EntityListCache.clear();
	}

	private IApproximations approximations;
	private String command;
	private String fieldName;
	private boolean isNegative;
	private boolean isStrict;
	private String[] listOfNextOrder;
	private String name;
	private String symbol;

	public BinaryConstraint(
		final String name,
		final String command,
		final Variable v0,
		final Variable v1,
		final int weight,
		final IApproximations approximations) {

		this.approximations = approximations;
		this.name = name;
		this.command = command;
		this.v0 = v0;
		this.v1 = v1;
		this.hook = new PalmConstraintPlugin(this);
		((PalmConstraintPlugin) this.hook).setWeight(weight);
		this.setListOfNextOrder(approximations.getApproximations());
	}
	public void awakeOnRem(final int idx, final int index_e) {
		this.propagate();
	}
	public void awakeOnRestoreVal(final int idx, final int val)
			throws ContradictionException {

		this.propagate();
	}
	public void doAwake() throws ContradictionException {
		this.propagate();
	}
	public IApproximations getApproximations() {
		return this.approximations;
	}
	protected String getFieldName() {
		return this.fieldName;
	}
	public String getName() {
		return this.name;
	}
	public String getNextConstraint() {
		int size = this.listOfNextOrder.length;
		boolean matchName = false;
		for (int i = 0; i < size; ++i) {
			if (this.getClass().getName().equals(this.listOfNextOrder[i])) {
				matchName = true;
				if (i < (size - 1)) {
					return this.listOfNextOrder[i + 1];
				}
			}
		}
		// Yann 2014/04/01: Fishy...
		// If there are approximations but none match the current
		// constraint it is possible that either the approximations
		// do not apply to this constraint (wrong constraint definition)
		// or that the name of the constraints in the definition are
		// misspelled or missing...
		if (size > 0 && !matchName) {
			throw new IllegalStateException(
				"Approximations exist but first approximation does not match current constraint");
		}
		return null;
	}
	public Class getNextConstraintConstructor(final String nextConstraint) {
		boolean isContained = false;
		for (int i = 0; i < this.listOfNextOrder.length; ++i) {
			if (this.listOfNextOrder[i].equals(nextConstraint)) {
				isContained = true;
				break;
			}
		}
		if (isContained) {
			try {
				return Class.forName(nextConstraint);
			}
			catch (final ClassNotFoundException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		return null;
	}
	public String getSymbol() {
		return this.symbol;
	}
	public int getWeight() {
		return ((PalmConstraintPlugin) this.hook).getWeight();
	}
	public String getXCommand() {
		return this.command;
	}
	public boolean isNegative() {
		return this.isNegative;
	}
	public boolean isSatisfied() {
		return false;
	}
	public void propagate() {
		final List allEntities = ((Problem) this.getProblem()).getAllEntities();
		final Domain v0Domain = (Domain) this.v0.getDomain();
		final Domain v1Domain = (Domain) this.v1.getDomain();
		final TIntSet v1ValuesWithSupportInV0 = new TIntHashSet();

		final IntIterator iterator0 = v0Domain.getIterator();
		while (iterator0.hasNext()) {
			final int index_e0 = iterator0.next();

			// Yann 2004/10/16: Fingerprints
			// It is possible when defining domain using fingerprints
			// that the domain of a variable be empty. This seems to
			// upset the combinatorial automatic solver which produces
			// temporary variable with INF and SUP equal to -1, which leads
			// an offset of -1 in the BitSetIntDomain which then returns -1.
			if (index_e0 > -1) {
				boolean toBeRemoved = true;
				final Entity e0 = (Entity) allEntities.get(index_e0);
				final TIntSet list =
					BinaryConstraint.getEntityList(
						e0,
						this.fieldName,
						this.isNegative(),
						((Problem) this.getProblem()).getAllEntities());

				// We test whether the entity (index_e0) is present in the v1 domain
				if (!this.isStrict && this.v1.canBeInstantiatedTo(index_e0)) {
					list.add(index_e0);
					toBeRemoved = false;
				}

				// Yann 2007/11/24: Iteration!
				// A simple test (!) shows that most of the time,
				// the size of the domain of v1 is smaller than
				// the size of the list of super-entities...
				// therefore it is better to iterate on the domain
				// of v1 rather than on the list!
				// Yann 2010/07/20: Performance!
				// I do not distinguish the two cases anymore
				// because I want to unify the treatment of the
				// domain v1 to avoid, possibly, iterating twice
				// over v1: once to find support for values in v0
				// second to remove values with no support in v1.
				// I would like to do it all at once!
				//	if (v1Domain.getSize() <= list.size()) {
				final IntIterator iterator = v1Domain.getIterator();
				// Yann 2007/11/25: Short cut!
				// I cannot short cut the while loop with
				// "&& toBeRemoved" anymore because I must
				// build the "allSuperEntities" set properly.
				while (iterator.hasNext()) {
					final int index_e1 = iterator.next();

					// We test whether the entity (index_e0) has a 
					// super-entity present in the v1 domain
					if (list.contains(index_e1)) {
						v1ValuesWithSupportInV0.add(index_e1);
						toBeRemoved = false;
					}
					else {
						// Yann 2014/05/22: Infinite loop:
						//	iterator0.hasNext() == true
						// BUT
						//	iterator0.next() == -1
						// break;
					}
				}

				// We test whether the entity (index_e0) is present in the v1 domain
				if (!this.isStrict && this.v1.canBeInstantiatedTo(index_e0)) {
					list.remove(index_e0);
					toBeRemoved = false;
				}

				if (toBeRemoved) {
					choco.palm.explain.Explanation expl =
						((Problem) this.getProblem()).makeExplanation();
					((PalmConstraintPlugin) this.getPlugIn())
						.self_explain(expl);
					((Variable) this.v1).self_explain(PalmIntDomain.DOM, expl);
					((Variable) this.v0).removeVal(index_e0, this.cIdx0, expl);
				}
			}
			else {
				// Yann 2014/05/22: Infinite loop:
				//	iterator0.hasNext() == true
				// BUT
				//	iterator0.next() == -1
				// break;
			}
		}

		final IntIterator iterator1 = v1Domain.getIterator();
		while (iterator1.hasNext()) {
			final int index_e1 = iterator1.next();

			if (!v1ValuesWithSupportInV0.contains(index_e1)) {
				choco.palm.explain.Explanation expl =
					((Problem) this.getProblem()).makeExplanation();
				((PalmConstraintPlugin) this.getPlugIn()).self_explain(expl);
				((Variable) this.v0).self_explain(PalmIntDomain.DOM, expl);
				((Variable) this.v1).removeVal(index_e1, this.cIdx1, expl);
			}
		}
	}
	protected void setFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}
	private void setListOfNextOrder(final String[] listOfNextOrder) {
		this.listOfNextOrder = listOfNextOrder;
	}
	protected void setNegative(final boolean aBoolean) {
		this.isNegative = aBoolean;
	}
	protected void setStrict(final boolean aBoolean) {
		this.isStrict = aBoolean;
	}
	protected String setSymbol(final String symbol) {
		return this.symbol = symbol;
	}
	public Set whyIsFalse() {
		return null;
	}
	public Set whyIsTrue() {
		return null;
	}
}
