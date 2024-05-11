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
package padl.analysis.repository.systematicuml;

import java.util.Iterator;
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
import padl.kernel.IFactory;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.IRelationship;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.motif.IDesignMotifModel;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/05/21
 */
public class SystematicUMLElementGenerator implements IWalker {
	private final IFactory factory;
	private IFirstClassEntity newEntity;
	private final IIdiomLevelModel newIdiomLevelModel;
	private IOperation newMethod;

	public SystematicUMLElementGenerator(
		final IFactory aFactory,
		final IIdiomLevelModel anIdiomLevelModel,
		final SystematicUMLStatistics aStatistics) {

		this.factory = aFactory;
		this.newIdiomLevelModel = anIdiomLevelModel;
	}

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
		return "SystematicUMLAnalisysElementGenerator";
	}
	public Object getResult() {
		return this.newIdiomLevelModel;
	}
	public void open(final IAbstractModel anAbstractModel) {
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);

		final Iterator inheritedEntities =
			aClass.getIteratorOnInheritedEntities();
		while (inheritedEntities.hasNext()) {
			this.newEntity
				.addInheritedEntity((IFirstClassEntity) this.newIdiomLevelModel
					.getConstituentFromID(((IFirstClassEntity) inheritedEntities
						.next()).getID()));
		}

		final Iterator implementedEntities =
			aClass.getIteratorOnImplementedInterfaces();
		while (implementedEntities.hasNext()) {
			((IClass) this.newEntity)
				.addImplementedInterface((IInterface) this.newIdiomLevelModel
					.getConstituentFromID(((IFirstClassEntity) implementedEntities
						.next()).getID()));
		}
	}
	public void open(final IConstructor aConstructor) {
		this.newMethod =
			this.factory.createConstructor(
				aConstructor.getID(),
				aConstructor.getName());
		this.newMethod.setVisibility(aConstructor.getVisibility());
		this.newEntity.addConstituent(this.newMethod);
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
		this.open((IMethod) aDelegatingMethod);
	}
	public void open(final IDesignMotifModel aPatternModel) {
	}
	private void open(final IFirstClassEntity anEntity) {
		this.newEntity =
			(IFirstClassEntity) this.newIdiomLevelModel
				.getConstituentFromID(anEntity.getID());
	}
	public void open(final IGetter aGetter) {
		this.open((IMethod) aGetter);
	}
	public void open(final IGhost aGhost) {
		this.open((IFirstClassEntity) aGhost);
	}
	public void open(final IInterface anInterface) {
		this.open((IFirstClassEntity) anInterface);

		final Iterator inheritedEntities =
			anInterface.getIteratorOnInheritedEntities();
		while (inheritedEntities.hasNext()) {
			this.newEntity
				.addInheritedEntity((IFirstClassEntity) this.newIdiomLevelModel
					.getConstituentFromID(((IFirstClassEntity) inheritedEntities
						.next()).getID()));
		}
	}
	public void open(final IMemberClass aMemberClass) {
		this.open((IClass) aMemberClass);
	}
	public void open(final IMemberGhost aMemberGhost) {
		this.open((IClass) aMemberGhost);
	}
	public void open(final IMemberInterface aMemberInterface) {
		this.open((IInterface) aMemberInterface);
	}
	public void open(final IMethod aMethod) {
		this.newMethod =
			this.factory.createMethod(aMethod.getID(), aMethod.getName());
		this.newMethod.setVisibility(aMethod.getVisibility());
		((IMethod) this.newMethod).setReturnType(aMethod.getReturnType());
		this.newEntity.addConstituent(this.newMethod);
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
		this.open((IMethod) aSetter);
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
	public void visit(final IAggregation anAggregation) {
		this.visit((IRelationship) anAggregation);
	}
	public void visit(final IAssociation anAssociation) {
		this.visit((IRelationship) anAssociation);
	}
	public void visit(final IComposition aComposition) {
		this.visit((IRelationship) aComposition);
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
		this.visit((IRelationship) aContainerAggregation);
	}
	public void visit(final IContainerComposition aContainerComposition) {
		this.visit((IRelationship) aContainerComposition);
	}
	public void visit(final ICreation aCreation) {
		this.visit((IRelationship) aCreation);
	}
	public void visit(final IField aField) {
		this.newEntity.addConstituent(this.factory.createField(
			aField.getID(),
			aField.getName(),
			aField.getType(),
			aField.getCardinality()));
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
		// The target entity can be null if this 
		// method invocation describes a field access
		if (aMethodInvocation.getTargetEntity() != null) {
			final IMethodInvocation methodInvocation =
				this.factory.createMethodInvocation(
					aMethodInvocation.getType(),
					aMethodInvocation.getCardinality(),
					aMethodInvocation.getVisibility(),
					(IFirstClassEntity) this.newIdiomLevelModel
						.getConstituentFromID(aMethodInvocation
							.getTargetEntity()
							.getID()));
			final Iterator iterator =
				aMethodInvocation.getIteratorOnCallingFields();
			while (iterator.hasNext()) {
				final IField field = (IField) iterator.next();
				methodInvocation.addCallingField(field);
			}
			// The called method can be null if this 
			// method invocation describes a field access
			if (aMethodInvocation.getCalledMethod() != null) {
				methodInvocation.setCalledMethod((IConstructor) this.newEntity
					.getConstituentFromID(aMethodInvocation
						.getCalledMethod()
						.getID()));
			}
			this.newMethod.addConstituent(methodInvocation);
		}
	}
	public void visit(final IParameter aParameter) {
		final IParameter newParameter =
			this.factory.createParameter(
				aParameter.getType(),
				aParameter.getName(),
				aParameter.getCardinality());
		// Yann 2004/08/07: Parameter cardinality!
		// I don't forget to set the parameter cardinality...
		newParameter.setCardinality(aParameter.getCardinality());
		this.newMethod.addConstituent(newParameter);
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	private void visit(final IRelationship aRelationship) {
		this.newEntity.addConstituent(this.factory
			.createAggregationRelationship(
				aRelationship.getName(),
				(IFirstClassEntity) this.newIdiomLevelModel
					.getConstituentFromID(aRelationship
						.getTargetEntity()
						.getID()),
				aRelationship.getCardinality()));
	}
	public void visit(final IUseRelationship aUse) {
		this.visit((IRelationship) aUse);
	}
}
