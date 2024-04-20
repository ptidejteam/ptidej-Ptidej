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
package ptidej.solver.java.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
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
import padl.util.Util;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/05/16
 */
public class Generator implements IWalker {
	private IAbstractModel abstractLevelModel;
	private Map cachedFullyQualifiedNames = new HashMap();
	private Entity currentEntity;
	private Map listOfEntities = new HashMap();
	private List listOfKnownEntityNames = new ArrayList();
	// Yann 2006/08/11: Member entities...
	// I need to store a stack of enclosing entities
	// if I am dealing with a member entity.
	private Stack stackOfEnclosingEntities = new Stack();

	public void close(final IAbstractModel anAbstractModel) {
	}
	public void close(final IClass aClass) {
		this.close((IFirstClassEntity) aClass);
	}
	public void close(final IConstructor aConstructor) {
	}
	public void close(final IDelegatingMethod aDelegatingMethod) {
	}
	protected void close(final IFirstClassEntity anEntity) {
		final Iterator itertor = this.listOfEntities.keySet().iterator();
		while (itertor.hasNext()) {
			final String name = (String) itertor.next();
			if (!this.listOfKnownEntityNames.contains(name)) {
				this.currentEntity.addUnknownEntity(this.findEntity(name));
			}
		}
		this.listOfKnownEntityNames.clear();

		// Yann 2007/02/26: Stacking!
		// I do not forget to replace the current entity
		// but its predecessor on the stack when I pop
		// the stack of enclosing entities.
		// I pop twice because on top of the stack is the
		// actual current entity and I want to change for
		// its predecessor.
		this.stackOfEnclosingEntities.pop();
		if (this.stackOfEnclosingEntities.size() > 0) {
			this.currentEntity = (Entity) this.stackOfEnclosingEntities.peek();
		}
		else {
			this.currentEntity = null;
		}
	}
	public void close(final IGetter aGetter) {
	}
	public void close(final IGhost aGhost) {
		this.close((IFirstClassEntity) aGhost);
	}
	public void close(final IInterface anInterface) {
		this.close((IFirstClassEntity) anInterface);
	}
	public void close(final IMemberClass aMemberClass) {
		this.close((IFirstClassEntity) aMemberClass);
	}
	public void close(final IMemberGhost aMemberGhost) {
		this.close((IFirstClassEntity) aMemberGhost);
	}
	public void close(final IMemberInterface aMemberInterface) {
		this.close((IFirstClassEntity) aMemberInterface);
	}
	public void close(final IMethod aMethod) {
	}
	public void close(final IPackage aPackage) {
	}
	public void close(final IPackageDefault aPackage) {
	}
	public void close(final ISetter aSetter) {
	}
	private Entity findEntity(final IFirstClassEntity anEntity) {
		// Yann 2006/08/11: Member entities...
		// If I cannot find an entity, then it 
		// must be that I am dealing with a
		// member entity.
		String fullyQualifiedName =
			(String) this.cachedFullyQualifiedNames.get(anEntity);
		if (fullyQualifiedName == null) {
			fullyQualifiedName =
				String.valueOf(Util.computeFullyQualifiedName(
					this.abstractLevelModel,
					anEntity));
		}

		this.listOfKnownEntityNames.add(fullyQualifiedName);
		return (Entity) this.listOfEntities.get(fullyQualifiedName);
	}
	private Entity findEntity(final String anEntityName) {
		this.listOfKnownEntityNames.add(anEntityName);

		// TODO: The following test should not exist!
		// It exists only because fields, return types,
		// and parameters may be "foo$bar"!
		// Moreover, the return could return null,
		// which is not a problem much but is still not
		// very clean, because the member entity has
		// not been created...
		//	if (Util.isMemberEntity(anEntityName)) {
		//		return (Entity) this.listOfEntities.get(
		//			anEntityName.replace('$', '.'));
		//	}
		//	else {
		return (Entity) this.listOfEntities.get(anEntityName);
		//	}
	}
	public String getName() {
		return "JPtidejSolver domain";
	}
	/**
	 * Returns an instance of class List
	 * containing instances of class Entity.
	 */
	public Object getResult() {
		final List listOfEntities = new ArrayList();
		final Iterator iterator = this.listOfEntities.values().iterator();
		while (iterator.hasNext()) {
			listOfEntities.add(iterator.next());
		}
		return listOfEntities;
	}
	private void handleMemberEntities(
		final IFirstClassEntity anEntity,
		final String fullyQualifiedEnclosingName) {

		final Iterator iterator =
			anEntity.getIteratorOnConstituents(IFirstClassEntity.class);
		while (iterator.hasNext()) {
			final IFirstClassEntity memberEntity =
				(IFirstClassEntity) iterator.next();
			final String name =
				fullyQualifiedEnclosingName + '.'
						+ memberEntity.getDisplayName();

			this.listOfEntities.put(
				name,
				new Entity(
					name,
					memberEntity.isAbstract()
							|| (memberEntity instanceof IInterface),
					memberEntity instanceof IInterface,
					memberEntity instanceof IGhost));
			this.cachedFullyQualifiedNames.put(memberEntity, name);
			this.handleMemberEntities(memberEntity, name);
		}
	}
	public void open(final IAbstractModel anAbstractModel) {
		// First I build an empty shell for
		// all the entities in the model.
		// Yann 2005/10/07: Packages!
		// A model may now contain entities and packages.
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		this.abstractLevelModel = anAbstractModel;
		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			final String id = firstClassEntity.getDisplayID();

			this.listOfEntities.put(
				id,
				new Entity(
					id,
					firstClassEntity.isAbstract()
							|| (firstClassEntity instanceof IInterface),
					firstClassEntity instanceof IInterface,
					firstClassEntity instanceof IGhost));
			this.cachedFullyQualifiedNames.put(firstClassEntity, id);

			this.handleMemberEntities(firstClassEntity, id);
		}
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);

		// Yann 2004/09/24: Interfaces!
		// I should not forget to add implemented interfaces
		// to the list of super-entities of an entity...
		final Iterator iterator = aClass.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			this.currentEntity.addSuperEntity(this
				.findEntity((IFirstClassEntity) iterator.next()));
		}
	}
	public void open(final IConstructor aConstructor) {
	}
	public void open(final IDelegatingMethod aDelegatingMethod) {
		this.open((IMethod) aDelegatingMethod);
	}
	protected void open(final IFirstClassEntity anEntity) {
		this.currentEntity = this.findEntity(anEntity);
		final Iterator iterator = anEntity.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			this.currentEntity.addSuperEntity(this
				.findEntity((IFirstClassEntity) iterator.next()));
		}
		this.stackOfEnclosingEntities.push(this.currentEntity);
	}
	public void open(final IGetter aGetter) {
		this.open((IMethod) aGetter);
	}
	public void open(final IGhost aGhost) {
		this.open((IFirstClassEntity) aGhost);
	}
	public void open(final IInterface anInterface) {
		this.open((IFirstClassEntity) anInterface);
	}
	public void open(final IMemberClass aMemberClass) {
		this.open((IFirstClassEntity) aMemberClass);
	}
	public void open(final IMemberGhost aMemberGhost) {
		this.open((IFirstClassEntity) aMemberGhost);
	}
	public void open(final IMemberInterface aMemberInterface) {
		this.open((IFirstClassEntity) aMemberInterface);
	}
	public void open(final IMethod aMethod) {
		// Yann 2004/05/16: Target entity.
		// The target entity may be "void", "boolean"...
		final char[] type = aMethod.getReturnType();
		final String displayType = aMethod.getDisplayReturnType();
		if (!Util.isPrimtiveType(type)) {
			final Entity entity = this.findEntity(displayType);
			this.currentEntity.addKnownEntity(entity);
		}

		this.currentEntity.addMethodName(aMethod.getDisplayName());
	}
	public void open(final IPackage aPackage) {
	}
	public void open(final IPackageDefault aPackage) {
	}
	public void open(final ISetter aSetter) {
		this.open((IMethod) aSetter);
	}
	public void reset() {
		this.cachedFullyQualifiedNames.clear();
		this.listOfEntities.clear();
		this.stackOfEnclosingEntities.clear();
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
		this.currentEntity.addAggregatedEntity(this.findEntity(anAggregation
			.getTargetEntity()));
	}
	public void visit(final IAssociation anAssociation) {
		this.currentEntity.addAssociatedEntity(this.findEntity(anAssociation
			.getTargetEntity()));
	}
	public void visit(final IComposition aComposition) {
		this.currentEntity.addComposedEntity(this.findEntity(aComposition
			.getTargetEntity()));
	}
	public void visit(final IContainerAggregation aContainerAggregation) {
		this.currentEntity.addContainerAggregatedEntity(this
			.findEntity(aContainerAggregation.getTargetEntity()));
	}
	public void visit(final IContainerComposition aContainerComposition) {
		this.currentEntity.addContainerComposedEntity(this
			.findEntity(aContainerComposition.getTargetEntity()));
	}
	public void visit(final ICreation aCreation) {
		this.currentEntity.addCreatedEntity(this.findEntity(aCreation
			.getTargetEntity()));
	}
	public void visit(final IField aField) {
		// Yann 2004/05/16: Target entity.
		// The target entity may be "void", "boolean"...
		final char[] type = aField.getType();
		final String displayType = aField.getDisplayTypeName();
		if (!Util.isPrimtiveType(type)) {
			final Entity entity = this.findEntity(displayType);
			this.currentEntity.addKnownEntity(entity);
		}
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
		// Yann 2004/05/16: Target entity.
		// How is it possible that a target entity could be null?
		if (aMethodInvocation.getTargetEntity() != null) {
			this.currentEntity.addKnownEntity(this.findEntity(aMethodInvocation
				.getTargetEntity()));
		}
	}
	public void visit(final IParameter aParameter) {
		// Yann 2004/05/16: Target entity.
		// The target entity may be "void", "boolean"...
		final char[] type = aParameter.getTypeName();
		final String displayType = aParameter.getType().getDisplayID();
		if (!Util.isPrimtiveType(type)) {
			final Entity entity = this.findEntity(displayType);
			this.currentEntity.addKnownEntity(entity);
		}
	}
	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}
	public void visit(final IUseRelationship aUse) {
		this.currentEntity.addKnownEntity(this.findEntity(aUse
			.getTargetEntity()));
	}
}
