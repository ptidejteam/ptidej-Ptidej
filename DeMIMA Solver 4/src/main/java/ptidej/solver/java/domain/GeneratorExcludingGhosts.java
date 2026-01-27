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
import padl.kernel.IPackageGhost;
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
public class GeneratorExcludingGhosts implements IWalker {
	private IAbstractModel abstractLevelModel;
	private Entity currentEntity;
	private List listOfKnownEntityNames = new ArrayList();
	private Map mapOfEntities = new HashMap();
	private Map mapOfFullyQualifiedNames = new HashMap();
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
		final Iterator itertor = this.mapOfEntities.keySet().iterator();
		while (itertor.hasNext()) {
			final String name = (String) itertor.next();
			if (!this.listOfKnownEntityNames.contains(name)) {
				final Entity entity = this.findEntity(name);
				// Yann 25/11/22: Target ghost entity
				// The entity could be null if it's a ghost,
				// because the model doesn't include ghosts.
				if (entity != null) {
					this.currentEntity.addUnknownEntity(entity);
				}
			}
		}
		this.listOfKnownEntityNames.clear();

		// Yann 2007/02/26: Stacking!
		// I do not forget to replace the current entity
		// but its predecessor on the stack when I pop
		// the stack of enclosing entities.
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
		// this.close((IFirstClassEntity) aGhost);
	}

	public void close(final IInterface anInterface) {
		this.close((IFirstClassEntity) anInterface);
	}

	public void close(final IMemberClass aMemberClass) {
		this.close((IFirstClassEntity) aMemberClass);
	}

	public void close(final IMemberGhost aMemberGhost) {
		// this.close((IFirstClassEntity) aMemberGhost);
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
	public void close(final IPackageGhost aPackageGhost) {
	}

	public void close(final ISetter aSetter) {
	}

	private Entity findEntity(final IFirstClassEntity anEntity) {
		// Yann 2006/08/11: Member entities...
		// If I cannot find an entity, then it 
		// must be that I am dealing with a
		// member entity.
		// Yann 2025/11/22: Ghosts!
		// The comment above is no longer true because of ghosts!
		String fullyQualifiedName = (String) this.mapOfFullyQualifiedNames
				.get(anEntity);
		if (fullyQualifiedName == null && !(anEntity instanceof IGhost)) {
			fullyQualifiedName = String.valueOf(Util.computeFullyQualifiedName(
					this.abstractLevelModel, anEntity));
			this.listOfKnownEntityNames.add(fullyQualifiedName);
		}

		return (Entity) this.mapOfEntities.get(fullyQualifiedName);
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
		return (Entity) this.mapOfEntities.get(anEntityName);
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
		final Iterator iterator = this.mapOfEntities.values().iterator();
		while (iterator.hasNext()) {
			listOfEntities.add(iterator.next());
		}
		return listOfEntities;
	}

	private void handleMemberEntities(final IFirstClassEntity anEntity,
			final String fullyQualifiedEnclosingName) {

		final Iterator iterator = anEntity
				.getIteratorOnConstituents(IFirstClassEntity.class);
		while (iterator.hasNext()) {
			final IFirstClassEntity memberEntity = (IFirstClassEntity) iterator
					.next();

			// Yann 25/11/22: Ghosts!
			if (!(memberEntity instanceof IGhost)) {
				final String name = fullyQualifiedEnclosingName + '.'
						+ memberEntity.getDisplayName();
				this.mapOfEntities.put(name,
						new Entity(name,
								memberEntity.isAbstract()
										|| (memberEntity instanceof IInterface),
								memberEntity instanceof IInterface,
								memberEntity instanceof IGhost));
				this.mapOfFullyQualifiedNames.put(memberEntity, name);
				this.handleMemberEntities(memberEntity, name);
			}
		}
	}

	public void open(final IAbstractModel anAbstractModel) {
		this.listOfKnownEntityNames.clear();
		this.mapOfEntities.clear();
		this.mapOfFullyQualifiedNames.clear();
		this.stackOfEnclosingEntities.clear();
		
		// First I build an empty shell for
		// all the entities in the model.
		// Yann 2005/10/07: Packages!
		// A model may now contain entities and packages.
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		this.abstractLevelModel = anAbstractModel;
		final Iterator iterator = anAbstractModel
				.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity = (IFirstClassEntity) iterator
					.next();
			// Yann 25/11/22: Ghosts!
			if (!(firstClassEntity instanceof IGhost)) {
				final String id = firstClassEntity.getDisplayID();

				this.mapOfEntities.put(id,
						new Entity(id, firstClassEntity.isAbstract()
								|| (firstClassEntity instanceof IInterface),
								firstClassEntity instanceof IInterface,
								firstClassEntity instanceof IGhost));
				this.mapOfFullyQualifiedNames.put(firstClassEntity, id);

				this.handleMemberEntities(firstClassEntity, id);
			}
		}
	}

	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);

		// Yann 2004/09/24: Interfaces!
		// I should not forget to add implemented interfaces
		// to the list of super-entities of an entity...
		final Iterator iterator = aClass.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			final Entity entity = this
					.findEntity((IFirstClassEntity) iterator.next());
			// Yann 25/11/22: Target ghost entity
			// The entity could be null if it's a ghost,
			// because the model doesn't include ghosts.
			if (entity != null) {
				this.currentEntity.addSuperEntity(entity);
			}
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
			final Entity entity = this
					.findEntity((IFirstClassEntity) iterator.next());
			// Yann 25/11/22: Target ghost entity
			// The entity could be null if it's a ghost,
			// because the model doesn't include ghosts.
			if (entity != null) {
				this.currentEntity.addSuperEntity(entity);
			}
		}
		this.stackOfEnclosingEntities.push(this.currentEntity);
	}

	public void open(final IGetter aGetter) {
		this.open((IMethod) aGetter);
	}

	public void open(final IGhost aGhost) {
		// this.open((IFirstClassEntity) aGhost);
	}

	public void open(final IInterface anInterface) {
		this.open((IFirstClassEntity) anInterface);
	}

	public void open(final IMemberClass aMemberClass) {
		this.open((IFirstClassEntity) aMemberClass);
	}

	public void open(final IMemberGhost aMemberGhost) {
		// this.open((IFirstClassEntity) aMemberGhost);
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
			// Yann 25/11/22: Target ghost entity
			// The entity could be null if it's a ghost,
			// because the model doesn't include ghosts.
			if (entity != null) {
				this.currentEntity.addKnownEntity(entity);
			}
		}

		this.currentEntity.addMethodName(aMethod.getDisplayName());
	}

	public void open(final IPackage aPackage) {
	}

	public void open(final IPackageDefault aPackage) {
	}

	public void open(IPackageGhost aPackageGhost) {

	}

	public void open(final ISetter aSetter) {
		this.open((IMethod) aSetter);
	}

	public void reset() {
		this.mapOfFullyQualifiedNames.clear();
		this.mapOfEntities.clear();
		this.stackOfEnclosingEntities.clear();
	}

	public final void unknownConstituentHandler(final String aCalledMethodName,
			final IConstituent aConstituent) {

		ProxyConsole.getInstance().debugOutput()
				.print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput()
				.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole.getInstance().debugOutput()
				.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	public void visit(final IAggregation anAggregation) {
		final Entity entity = this.findEntity(anAggregation.getTargetEntity());
		// Yann 25/11/22: Target ghost entity
		// The entity could be null if it's a ghost,
		// because the model doesn't include ghosts.
		if (entity != null) {
			this.currentEntity.addAggregatedEntity(entity);
		}
	}

	public void visit(final IAssociation anAssociation) {
		final Entity entity = this.findEntity(anAssociation.getTargetEntity());
		// Yann 25/11/22: Target ghost entity
		// The entity could be null if it's a ghost,
		// because the model doesn't include ghosts.
		if (entity != null) {
			this.currentEntity.addAssociatedEntity(entity);
		}
	}

	public void visit(final IComposition aComposition) {
		final Entity entity = this.findEntity(aComposition.getTargetEntity());
		// Yann 25/11/22: Target ghost entity
		// The entity could be null if it's a ghost,
		// because the model doesn't include ghosts.
		if (entity != null) {
			this.currentEntity.addComposedEntity(entity);
		}
	}

	public void visit(final IContainerAggregation aContainerAggregation) {
		final Entity entity = this
				.findEntity(aContainerAggregation.getTargetEntity());
		// Yann 25/11/22: Target ghost entity
		// The entity could be null if it's a ghost,
		// because the model doesn't include ghosts.
		if (entity != null) {
			this.currentEntity.addContainerAggregatedEntity(entity);
		}
	}

	public void visit(final IContainerComposition aContainerComposition) {
		final Entity entity = this
				.findEntity(aContainerComposition.getTargetEntity());
		// Yann 25/11/22: Target ghost entity
		// The entity could be null if it's a ghost,
		// because the model doesn't include ghosts.
		if (entity != null) {
			this.currentEntity.addContainerComposedEntity(entity);
		}
	}

	public void visit(final ICreation aCreation) {
		final Entity entity = this.findEntity(aCreation.getTargetEntity());
		// Yann 25/11/22: Target ghost entity
		// The entity could be null if it's a ghost,
		// because the model doesn't include ghosts.
		if (entity != null) {
			this.currentEntity.addCreatedEntity(entity);
		}
	}

	public void visit(final IField aField) {
		// Yann 2004/05/16: Target entity.
		// The target entity may be "void", "boolean"...
		final char[] type = aField.getType();
		final String displayType = aField.getDisplayTypeName();
		if (!Util.isPrimtiveType(type)) {
			final Entity entity = this.findEntity(displayType);
			// Yann 25/11/22: Target ghost entity
			// The entity could be null if it's a ghost,
			// because the model doesn't include ghosts.
			if (entity != null) {
				this.currentEntity.addKnownEntity(entity);
			}
		}
	}

	public void visit(final IMethodInvocation aMethodInvocation) {
		// Yann 2004/05/16: Target entity.
		// How is it possible that a target entity could be null?
		// Yann 2025/11/22: Ghost target entity
		// The model doesn't include Ghosts, so I skip them.
		final IFirstClassEntity targetEntity = aMethodInvocation
				.getTargetEntity();
		if (targetEntity != null) {
			final Entity entity = this.findEntity(targetEntity);
			// Yann 25/11/22: Target ghost entity
			// The entity could be null if it's a ghost,
			// because the model doesn't include ghosts.
			if (entity != null) {
				this.currentEntity.addKnownEntity(entity);
			}
		}
	}

	public void visit(final IParameter aParameter) {
		// Yann 2004/05/16: Target entity.
		// The target entity may be "void", "boolean"...
		final char[] type = aParameter.getTypeName();
		final String displayType = aParameter.getType().getDisplayID();
		if (!Util.isPrimtiveType(type)) {
			final Entity entity = this.findEntity(displayType);
			// Yann 25/11/22: Target ghost entity
			// The entity could be null if it's a ghost,
			// because the model doesn't include ghosts.
			if (entity != null) {
				this.currentEntity.addKnownEntity(entity);
			}
		}
	}

	public void visit(final IPrimitiveEntity aPrimitiveEntity) {
		// Do nothing for uninteresting primitive types.
	}

	public void visit(final IUseRelationship aUse) {
		final Entity entity = this.findEntity(aUse.getTargetEntity());
		// Yann 25/11/22: Target ghost entity
		// The entity could be null if it's a ghost,
		// because the model doesn't include ghosts.
		if (entity != null) {
			this.currentEntity.addKnownEntity(entity);
		}
	}
}
