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
import padl.kernel.impl.DepthFirstTraverser;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

import java.util.Iterator;

public final class MetricsNOCCalculator implements IWalker {
	private final DepthFirstTraverser traverser = new DepthFirstTraverser();
	private int numberOfAbstractModel;
	private int numberOfGhost;
	private int numberOfInterface;
	private int numberOfPatternModel;
	private int numberOfSubclasses;

	public void close(final IAbstractModel p) {
		this.numberOfAbstractModel++;
	}
	public void close(final IClass p) {
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println(
				"NOC --> number of imediate subclasses = "
						+ this.numberOfSubclasses);
		ProxyConsole
			.getInstance()
			.normalOutput()
			.println(
				"AbstractModel :" + (this.numberOfAbstractModel + 1)
						+ "Ghost :" + this.numberOfGhost + "Interface :"
						+ this.numberOfInterface + "AbstractModel :"
						+ this.numberOfAbstractModel + "PatternModel  :"
						+ this.numberOfPatternModel);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost p) {
		this.numberOfGhost++;
	}
	public void close(final IInterface p) {
		this.numberOfInterface++;
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
	public String getName() {
		return "NOC metric";
	}
	public Object getResult() {
		return null;
	}
	public void open(final IAbstractModel p) {
	}
	public void open(final IClass p) {
		this.numberOfSubclasses = 0;
	}
	public void open(final IConstructor p) {
	}
	public void open(final IDelegatingMethod p) {
	}
	public void open(final IGetter p) {
	}
	public void open(final IGhost p) {
	}
	public void open(final IInterface p) {
	}
	public void open(final IMemberClass aMemberClass) {
	}
	public void open(final IMemberGhost aMemberGhost) {
	}
	public void open(final IMemberInterface aMemberInterface) {
	}
	public void open(final IMethod p) {
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter p) {
	}
	public void reset() {
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
		this.numberOfSubclasses++;
	}
	public void visit(final IComposition p) {
	}
	public void visit(final IContainerAggregation p) {
	}
	public void visit(final IContainerComposition p) {
	}
	public void visit(final ICreation p) {
	}
	public void visit(final IField p) {
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter p) {
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship p) {
	}

	public void traverse(Iterator iterator) {

		this.traverser.traverse(this,
				iterator,
				null);
	}
}
