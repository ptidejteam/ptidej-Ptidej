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
package ptidej.solver.java.helper;

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
import padl.motif.IDesignMotifModel;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/21
 */
public class ConstituentCounterVisitor implements IWalker {
	private int numberOfAggregations;
	private int numberOfAssociations;
	private int numberOfClasses;
	private int numberOfImplementationRelationships;
	private int numberOfInheritanceRelationships;
	private int numberOfMethods;

	public void close(final IAbstractModel anAbstractModel) {
	}
	public void close(final IClass aClass) {
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	public void close(final IDesignMotifModel aPatternModel) {
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost aGhost) {
	}
	public void close(final IInterface anInterface) {
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
		return "Data Counter";
	}
	public Object getResult() {
		final StringBuffer buffer = new StringBuffer();
		//	buffer.append("\nNumber of classes: ");
		//	buffer.append(this.numberOfClasses);
		//	buffer.append("\nNumber of methods: ");
		//	buffer.append(this.numberOfMethods);
		//	buffer.append("\nNumber of associations: ");
		//	buffer.append(this.numberOfAssociations);
		//	buffer.append("\nNumber of aggregations: ");
		//	buffer.append(this.numberOfAggregations);
		//	buffer.append("\nNumber of inheritance: ");
		//	buffer.append(
		//		this.numberOfImplementationRelationships
		//			+ this.numberOfInheritanceRelationships);

		// Yann 2007/04/18: TSE'07
		// Output for building the table for TSE.
		buffer.append("& ");
		buffer.append(this.numberOfClasses);
		buffer.append(" & ");
		buffer.append(this.numberOfMethods);
		buffer.append(" & ");
		buffer.append(this.numberOfAssociations);
		buffer.append(" & ");
		buffer.append(this.numberOfAggregations);
		buffer.append(" & ");
		buffer.append(this.numberOfImplementationRelationships
				+ this.numberOfInheritanceRelationships);
		buffer.append(" \\\\");

		return buffer.toString();
	}
	public void open(final IAbstractModel anAbstractModel) {
	}
	public void open(final IClass aClass) {
		this.numberOfClasses++;

		this.numberOfImplementationRelationships +=
			aClass.getNumberOfImplementedInterfaces();
		this.numberOfInheritanceRelationships +=
			aClass.getNumberOfInheritedEntities();
	}
	public void open(final IConstructor aConstructor) {
		this.numberOfMethods++;
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
		this.numberOfMethods++;
	}
	public void open(final IDesignMotifModel aPatternModel) {
	}
	public void open(final IGetter aGetter) {
		this.numberOfMethods++;
	}
	public void open(final IGhost aGhost) {
	}
	public void open(final IInterface anInterface) {
		this.numberOfClasses++;

		this.numberOfInheritanceRelationships +=
			anInterface.getNumberOfInheritedEntities();
	}
	public void open(final IMemberClass aMemberClass) {
		this.open((IClass) aMemberClass);
	}
	public void open(final IMemberGhost aMemberGhost) {
	}
	public void open(final IMemberInterface aMemberInterface) {
		this.open((IInterface) aMemberInterface);
	}
	public void open(final IMethod aMethod) {
		this.numberOfMethods++;
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
		this.numberOfMethods++;
	}
	public void reset() {
		this.numberOfAggregations = 0;
		this.numberOfAssociations = 0;
		this.numberOfClasses = 0;
		this.numberOfImplementationRelationships = 0;
		this.numberOfInheritanceRelationships = 0;
		this.numberOfMethods = 0;
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
	public void visit(final IAggregation anAggregation) {
		this.numberOfAggregations++;
	}
	public void visit(final IAssociation anAssociation) {
		this.numberOfAssociations++;
	}
	public void visit(final IComposition aComposition) {
		this.numberOfAggregations++;
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
		this.numberOfAggregations++;
	}
	public void visit(final IContainerComposition aContainerComposition) {
		this.numberOfAggregations++;
	}
	public void visit(final ICreation aCreation) {
	}
	public void visit(final IField aField) {
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
	}
	public void visit(final IParameter aParameter) {
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship aUse) {
		this.numberOfAssociations++;
	}
}
