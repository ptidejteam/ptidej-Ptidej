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
package padl.visitor.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * See chapter 5 of
 *     David N. Card, Robert L. Glass ; <I>Measuring Software
 *     Design Quality<I> ; Prentice Hall, 1990.
 *
 * To handle hierarchy in the structural complexity calculus,
 * I add the hierarchy level to the St. As for fanout, all
 * descendants of a given module (class in our case) are
 * connected to each otherby their common parent. Thus, there
 * are hierarchy-squared possible connections. (cf. p48)
 */

/* Right now, the same weight is given to inheritance links and
 * fanout, but this potentially lead to the case where the
 * complexity value is lower for a "god" class than for a neat
 * hierarchy of classes. This should be further examined and
 * the weight of the inheritance link should be reduced.
 */

public final class MetricsComplexityCalculator implements IWalker {
	private static class ValuesForModule {
		private int fanout;
		private int ioVariables;
		private final char[] moduleName;

		public ValuesForModule(final char[] moduleName) {
			this.moduleName = moduleName;
		}
		public void addFanout(final int fanout) {
			this.fanout += fanout;
		}
		public void addIOVariables(final int ioVariables) {
			this.ioVariables += ioVariables;
		}
		public double getDataComplexity() {
			return ((double) this.ioVariables) / (this.fanout + 1);
		}
		//	public char[] getModuleName() {
		//		return this.moduleName;
		//	}
		public double getStructuralComplexity() {
			return Math.pow(this.fanout, 2);
		}
		//	public void setFanout(final int fanout) {
		//		this.fanout = fanout;
		//	}
		//	public void setIOVariables(final int ioVariables) {
		//		this.ioVariables = ioVariables;
		//	}
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			buffer.append(this.moduleName);
			buffer.append(":\n\tFanout = ");
			buffer.append(this.fanout);
			buffer.append(", IO variables = ");
			buffer.append(this.ioVariables);
			buffer.append(",\n\tSt = ");
			buffer.append(this.getStructuralComplexity());
			buffer.append(", Dt = ");
			buffer.append(this.getDataComplexity());
			return buffer.toString();
		}
	}

	private final List valuesForAllModules = new ArrayList();
	private ValuesForModule valuesForCurrentModule;
	public void close(final IAbstractModel p) {
		final double S = this.computeStructuralComplexity();
		final double D = this.computeDataComplexity();

		for (int i = 0; i < this.valuesForAllModules.size(); i++) {
			ProxyConsole
				.getInstance()
				.normalOutput()
				.println(this.valuesForAllModules.get(i));
		}
		ProxyConsole.getInstance().normalOutput().print("C = S + D = ");
		ProxyConsole.getInstance().normalOutput().print(S);
		ProxyConsole.getInstance().normalOutput().print(" + ");
		ProxyConsole.getInstance().normalOutput().print(D);
		ProxyConsole.getInstance().normalOutput().print(" = ");
		ProxyConsole.getInstance().normalOutput().println(S + D);
		ProxyConsole.getInstance().normalOutput().println("----");
	}

	public void close(final IClass p) {
		int hierarchyNestingLevel = this.computeDeeperHierarchyNestingLevel(p);
		this.valuesForCurrentModule.addFanout(hierarchyNestingLevel);

		this.close((IFirstClassEntity) p);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	private void close(final IFirstClassEntity pEntity) {
		this.valuesForAllModules.add(this.valuesForCurrentModule);
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost p) {
		this.valuesForCurrentModule.addFanout(1);

		this.close((IFirstClassEntity) p);
	}
	public void close(final IInterface p) {
		int hierarchyNestingLevel = this.computeDeeperHierarchyNestingLevel(p);
		this.valuesForCurrentModule.addFanout(hierarchyNestingLevel);

		this.close((IFirstClassEntity) p);
	}
	public void close(final IMemberClass aMemberClass) {
	}
	public void close(final IMemberGhost aMemberGhost) {
	}
	public void close(final IMemberInterface aMemberInterface) {
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage aPackage) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final ISetter aSetter) {
	}
	private double computeDataComplexity() {
		int n = this.valuesForAllModules.size();
		double sum = 0.0;

		for (int i = 0; i < n; i++) {
			sum +=
				((ValuesForModule) this.valuesForAllModules.get(i))
					.getDataComplexity();
		}

		return sum / n;
	}
	private int computeDeeperHierarchyNestingLevel(final IClass pClass) {
		int nestingLevel = 1;

		Iterator iterator = pClass.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			nestingLevel +=
				this
					.computeDeeperHierarchyNestingLevel((IFirstClassEntity) iterator
						.next());
		}

		iterator = pClass.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			nestingLevel +=
				this
					.computeDeeperHierarchyNestingLevel((IFirstClassEntity) iterator
						.next());
		}

		return nestingLevel;
	}
	private int computeDeeperHierarchyNestingLevel(
		final IFirstClassEntity pEntity) {
		if (pEntity instanceof IClass) {
			return this.computeDeeperHierarchyNestingLevel((IClass) pEntity);
		}
		else if (pEntity instanceof IInterface) {
			return this
				.computeDeeperHierarchyNestingLevel((IInterface) pEntity);
		}

		return 1;
	}
	private int computeDeeperHierarchyNestingLevel(final IInterface pInterface) {
		int nestingLevel = 1;

		final Iterator iterator = pInterface.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			nestingLevel +=
				this.computeDeeperHierarchyNestingLevel((IInterface) iterator
					.next());
		}

		return nestingLevel;
	}
	private double computeStructuralComplexity() {
		final int n = this.valuesForAllModules.size();
		double sum = 0.0;

		for (int i = 0; i < n; i++) {
			sum +=
				((ValuesForModule) this.valuesForAllModules.get(i))
					.getStructuralComplexity();
		}

		return sum / n;
	}
	public String getName() {
		return "Complexity metric";
	}
	public Object getResult() {
		final double S = this.computeStructuralComplexity();
		final double D = this.computeDataComplexity();

		return Double.valueOf(S + D);
	}
	public void open(final IAbstractModel p) {
		this.reset();
	}
	public void open(final IClass p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IConstructor p) {
	}
	public void open(final IDelegatingMethod p) {
		this.valuesForCurrentModule.addFanout(1);
	}
	private void open(final IFirstClassEntity anEntity) {
		this.valuesForCurrentModule = new ValuesForModule(anEntity.getID());
	}
	public void open(final IGetter p) {
		this.valuesForCurrentModule.addFanout(1);
	}
	public void open(final IGhost p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IInterface p) {
		this.open((IFirstClassEntity) p);
	}
	public void open(final IMemberClass aMemberClass) {
	}
	public void open(final IMemberGhost aMemberGhost) {
	}
	public void open(final IMemberInterface aMemberInterface) {
	}
	public void open(final IMethod p) {
		if (!Arrays.equals(p.getReturnType(), "void".toCharArray())) {
			this.valuesForCurrentModule.addIOVariables(1);
		}
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter p) {
		this.valuesForCurrentModule.addFanout(1);
	}
	public void reset() {
		this.valuesForAllModules.clear();
	}
	public final void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(this.getClass().getName());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}
	public void visit(final IAggregation p) {
	}
	public void visit(final IAssociation p) {
		this.valuesForCurrentModule.addFanout(1);
	}
	public void visit(final IComposition p) {
	}
	public void visit(final IContainerAggregation p) {
	}
	public void visit(final IContainerComposition p) {
	}
	public void visit(final ICreation p) {
		this.valuesForCurrentModule.addFanout(1);
	}
	public void visit(final IField p) {
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter p) {
		this.valuesForCurrentModule.addIOVariables(1);
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship p) {
		this.valuesForCurrentModule.addFanout(1);
	}

	public void traverse(Iterator iterator) {
		throw new NotImplementedException();
	}
}
