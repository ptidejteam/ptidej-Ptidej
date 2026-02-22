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
package padl.kernel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.ibm.toad.cfparse.utils.Access;

import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.exception.ModelDeclarationException;
import util.multilingual.MultilingualManager;

class AbstractClass extends FirstClassEntity {
	private static final long serialVersionUID = 5923727557416846348L;
	private boolean forceAbstract = false;
	private List listOfImplementedInterfaces = new ArrayList();

	public AbstractClass(final char[] anID, final char[] aName) {
		super(anID);
		this.setName(aName);
	}
	// Yann 2004/08/15: Specialisation and implementation.
	// I want to unify the specialisation and implementation relationships
	// with other relationships. So, I don't treat as a special case the
	// inherited entity for classes.
	//	public Class(final String anID, final IEntity inheritedEntity) {
	//		super(anID);
	//		try {
	//			this.addInheritedConstituent(inheritedEntity);
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
	public void addImplementedInterface(final IInterface anInterface) {
		if (this.listOfImplementedInterfaces.contains(anInterface)) {
			throw new ModelDeclarationException(
				MultilingualManager.getString(
					"ALREADY_IMPL",
					IClass.class,
					new Object[] { anInterface.getDisplayID(),
							this.getDisplayID() }));
		}

		this.listOfImplementedInterfaces.add(anInterface);
		//	this.addConstituent(
		//		new Implementation("I" + anEntity.getConstituentID(), anEntity));

		((AbstractInterface) anInterface)
			.addImplementingClass((IClass) this);
		//	anEntity.addConstituent(
		//		new Generalisation("G" + this.getConstituentID(), this));
	}
	public void assumeAllInterfaces() {
		final Iterator iterator = this.listOfImplementedInterfaces.iterator();
		while (iterator.hasNext()) {
			this.assumeInterface((Interface) iterator.next());
		}
	}
	public void assumeInterface(final IInterface anInterface) {
		if (!this.listOfImplementedInterfaces.contains(anInterface)) {
			throw new ModelDeclarationException(MultilingualManager.getString(
				"NOT_IMPL",
				IClass.class,
				new Object[] { anInterface.getID(), this.getID() }));
		}

		final Iterator iterator = anInterface.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final Element orgElement = (Element) iterator.next();
			orgElement.startCloneSession();
			final Element dupElement = (Element) orgElement.getClone();
			orgElement.endCloneSession();
			dupElement.setAbstract(false);
			dupElement.attachTo(orgElement);
			this.addConstituent(dupElement);
		}
	}
	public IInterface getImplementedInterface(final char[] anEntityName) {
		final Iterator iterator = this.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			final IInterface implementedEntity =
				(IInterface) iterator.next();
			if (Arrays.equals(implementedEntity.getName(), anEntityName)) {
				return implementedEntity;
			}
		}

		return null;
	}
	public Iterator getIteratorOnImplementedInterfaces() {
		return this.listOfImplementedInterfaces.iterator();
	}
	public boolean isForceAbstract() {
		return this.forceAbstract;
	}
	public void performCloneSession() {
		super.performCloneSession();

		// Duplicate implementation hierarchy.
		final AbstractClass clonedPClass = (AbstractClass) this.getClone();
		clonedPClass.listOfImplementedInterfaces =
			new ArrayList(this.listOfImplementedInterfaces.size());
		final Iterator iterator = this.listOfImplementedInterfaces.iterator();
		while (iterator.hasNext()) {
			final IFirstClassEntity currentInterface =
				(IFirstClassEntity) iterator.next();
			// Yann: The followind lines are not needed anymore?
			// if (currentPInterface.isCloned()) {
			// tmpObject.removeShouldImplement(currentPInterface);
			// try {
			// Yann 2001/07/31: Hack!
			// The following test is only needed when cloning
			// a subList of the model.
			// A better and *cleaner* algorithm must be
			// implemented eventually.
			if (currentInterface.getClone() != null) {
				clonedPClass.listOfImplementedInterfaces
					.add((IFirstClassEntity) currentInterface.getClone());
			}
		}
	}
	public void removeImplementedInterface(final IInterface anInterface) {
		this.listOfImplementedInterfaces.remove(anInterface);

		// Here, we know for sure that anInterface is  
		// actually an instance of Interface, or do we?
		((AbstractInterface) anInterface)
			.removeImplementingClass((IClass) this);
	}
	public void setAbstract(final boolean aBoolean) {
		this.forceAbstract = aBoolean;
		super.setAbstract(aBoolean);
	}
	public void setVisibility(final int visibility) {
		super
			.setVisibility(isForceAbstract() ? (visibility | Access.ACC_ABSTRACT)
					: visibility);
	}
	public String toString() {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString());
		codeEq.append(" class ");
		codeEq.append(this.getName());
		Iterator iterator = this.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			codeEq.append(" extends ");
			while (iterator.hasNext()) {
				codeEq
					.append(((IFirstClassEntity) (iterator.next())).getName());
				if (iterator.hasNext()) {
					codeEq.append(", ");
				}
			}
		}
		iterator = this.getIteratorOnImplementedInterfaces();
		if (iterator.hasNext()) {
			codeEq.append(" implements ");
			while (iterator.hasNext()) {
				codeEq
					.append(((IFirstClassEntity) (iterator.next())).getName());
				if (iterator.hasNext()) {
					codeEq.append(", ");
				}
			}
		}
		return codeEq.toString();
	}
	public int getNumberOfImplementedInterfaces() {
		return this.listOfImplementedInterfaces.size();
	}
	protected String constituentToString() {
		return super.toString();
	}
}
