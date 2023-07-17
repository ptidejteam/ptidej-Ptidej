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
package padl.cpp.kernel.impl;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import padl.cpp.kernel.IUnion;
import padl.kernel.IElement;
import padl.kernel.IEntityMarker;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.FirstClassEntity;

class Union extends FirstClassEntity implements IEntityMarker, IUnion {
	private static final long serialVersionUID = -4945913029224539105L;

	private boolean forceAbstract = false;

	public Union(final char[] anID) {
		super(anID);
	}
	//	public Union(final String anID, final IEntity inheritedEntity) {
	//		super(anID);
	//		try {
	//			this.addInheritedEntity(inheritedEntity);
	//		}
	//		catch (final ModelDeclarationException e) {
	//			// No error can occur
	//		}
	//	}
	// Yann 2002/07/29: Thought...
	// I think the following method is not consistent with the idea of
	// vetoable property. I guess a better implementation would be for
	// the PClass class to register itself as a listener on the list
	// of inherited entities and to veto any addition to the list when
	// the list already contains one entity.
	//	public void addInherits(final Entity aPEntity)
	//		 {
	//
	//		if (this.listOfInheritedEntities().size() > 0) {
	//			StringBuffer buffer = new StringBuffer();
	//			buffer.append("Only single inheritance supported for class\n");
	//			buffer.append(this);
	//			buffer.append(" -|>- ");
	//			buffer.append(this.listOfInheritedEntities().(0));
	//			buffer.append("\nCannot attach new super-class\n");
	//			buffer.append(aPEntity);
	//			throw new ModelDeclarationException(buffer.toString());
	//		}
	//
	//		super.addInherits(aPEntity);
	//	}
	//	public void addImplementedEntity(final IEntity anEntity)
	//		 {
	//
	//		if (this.listOfSuperInterfaces.contains(anEntity)) {
	//			throw new ModelDeclarationException(
	//				anEntity.getID()
	//					+ " is already implemented by "
	//					+ this.getID());
	//		}
	//
	//		this.listOfSuperInterfaces.add(anEntity);
	//		((Entity) anEntity).addInheritingEntity(this);
	//	}
	//	public void assumeAllInterfaces() {
	//		try {
	//			final Iterator iterator = this.listOfSuperInterfaces.iterator();
	//			while (iterator.hasNext()) {
	//				this.assumeInterface((Interface) iterator.next());
	//			}
	//		}
	//		catch (final ModelDeclarationException e) {
	//			// No ModelDeclarationException can occur.
	//		}
	//	}
	//	public void assumeInterface(final IInterface anInterface)
	//		 {
	//
	//		if (!this.listOfSuperInterfaces.contains(anInterface)) {
	//			throw new ModelDeclarationException(
	//				anInterface.getID()
	//					+ " is not implemented by "
	//					+ this.getID());
	//		}
	//
	//		final Iterator iterator = anInterface.listOfConstituents().iterator();
	//		while (iterator.hasNext()) {
	//			try {
	//				final Element orgElement = (Element) iterator.next();
	//				orgElement.startCloneSession();
	//				final Element dupElement = (Element) orgElement.getClone();
	//				orgElement.endCloneSession();
	//				dupElement.setAbstract(false);
	//				dupElement.attachTo(orgElement);
	//				this.addConstituent(dupElement);
	//			}
	//			catch (final ModelDeclarationException e) {
	//				// In case of duplicated element.
	//			}
	//		}
	//	}
	//	public IEntity getImplementedEntity(final String anEntityName) {
	//		final Iterator iterator = this.listOfImplementedEntities().iterator();
	//		while (iterator.hasNext()) {
	//			final Entity implementedEntity = (Entity) iterator.next();
	//			if (implementedEntity.getName().equals(anEntityName)) {
	//				return implementedEntity;
	//			}
	//		}
	//
	//		return null;
	//	}
	public boolean isForceAbstract() {
		return this.forceAbstract;
	}
	//	public void performCloneSession() {
	//		super.performCloneSession();
	//
	//		// Duplicate implementation hierarchy.
	//		final Union clonedPUnion = (Union) this.getClone();
	//		clonedPUnion.listOfSuperInterfaces =
	//			new ArrayList(this.listOfSuperInterfaces.size());
	//		final Iterator iterator = this.listOfSuperInterfaces.iterator();
	//		while (iterator.hasNext()) {
	//			final Entity currentInterface = (Entity) iterator.next();
	//			// Yann: The followind lines are not needed anymore?
	//			// if (currentPInterface.isCloned()) {
	//			// tmpObject.removeShouldImplement(currentPInterface);
	//			// try {
	//			// Yann 2001/07/31: Hack!
	//			// The following test is only needed when cloning
	//			// a subList of the padlModel.
	//			// A better and *cleaner* algorithm must be
	//			// implemented eventually.
	//			if (currentInterface.getClone() != null) {
	//				clonedPUnion.listOfSuperInterfaces.add(
	//					(Entity) currentInterface.getClone());
	//			}
	//		}
	//	}
	//	public void removeImplementedEntity(final IEntity anEntity) {
	//		this.listOfSuperInterfaces.remove(anEntity);
	//	}
	public void setAbstract(final boolean aBoolean) {
		this.forceAbstract = aBoolean;
		super.setAbstract(aBoolean);
	}
	public void setVisibility(final int visibility) {
		super.setVisibility(this.isForceAbstract() ? visibility
				| Modifier.ABSTRACT : visibility);
	}
	public String toString() {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString());
		codeEq.append(" Union ");
		codeEq.append(this.getName());
		final Iterator<?> iterator = this.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			codeEq.append(" extends ");
			while (iterator.hasNext()) {
				codeEq.append(((IFirstClassEntity) iterator.next()).getName());
				if (iterator.hasNext()) {
					codeEq.append(", ");
				}
			}
		}
		return codeEq.toString();
	}
	public void attachTo(final IElement anElement) {
	}
	public void detach() {
	}
	public IElement getAttachedElement() {
		return null;
	}
}
