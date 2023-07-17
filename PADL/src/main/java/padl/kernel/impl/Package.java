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

import java.util.Iterator;
import java.util.List;
import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IFilter;
import padl.kernel.IPackage;
import padl.kernel.exception.ModelDeclarationException;
import padl.path.IConstants;
import padl.visitor.IVisitor;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2005/09/30
 */
class Package extends Constituent implements IPackage, IPrivateModelObservable {
	private static final long serialVersionUID = 2682383413411742951L;
	private AbstractGenericContainerOfConstituents container =
		new GenericContainerOfNaturallyOrderedConstituents(
			this,
			GenericContainerConstants.INITIAL_SIZE_PACKAGES);

	public Package(final char[] anID) {
		super(anID);
	}
	public void accept(final IVisitor visitor) {
		this.accept(visitor, "open");
		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			((IConstituent) iterator.next()).accept(visitor);
		}
		this.accept(visitor, "close");
	}
	public void addConstituent(final IConstituent aConstituent) {
		if (aConstituent instanceof IConstituentOfModel) {
			this.addConstituent((IConstituentOfModel) aConstituent);
		}
		else {
			throw new ModelDeclarationException(this.getClass().getName()
					+ " can only add IConstituentOfModel");
		}
	}
	public void addConstituent(IConstituentOfEntity aConstituent) {
		this.container.addConstituent(aConstituent);
	}
	public void addConstituent(final IConstituentOfModel aConstituent) {
		this.container.addConstituent(aConstituent);
	}
	public void addConstituent(final IPackage aPackage) {
		this.container.addConstituent(aPackage);
	}
	public void addModelListener(final IModelListener aModelListener) {
		this.container.addModelListener(aModelListener);
	}
	public void addModelListeners(final List someModelListeners) {
		this.container.addModelListeners(someModelListeners);
	}
	public boolean doesContainConstituentWithID(final char[] anID) {
		return this.container.doesContainConstituentWithID(anID);
	}
	public boolean doesContainConstituentWithName(final char[] aName) {
		return this.container.doesContainConstituentWithName(aName);
	}
	public void endCloneSession() {
		// The shallow copy must include a shallow copy of
		// member entities, because the clones of methods,
		// fields, and so on, depend on it!
		super.endCloneSession();

		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			constituent.endCloneSession();
		}
	}
	public void fireModelChange(final String anEventType, final IEvent anEvent) {
		this.container.fireModelChange(anEventType, anEvent);
	}
	public Iterator getConcurrentIteratorOnConstituents() {
		return this.container.getConcurrentIteratorOnConstituents();
	}
	public Iterator getConcurrentIteratorOnConstituents(final IFilter filter) {
		return this.container.getConcurrentIteratorOnConstituents();
	}
	public Iterator getConcurrentIteratorOnConstituents(
		final java.lang.Class aConstituentType) {

		return this.container
			.getConcurrentIteratorOnConstituents(aConstituentType);
	}
	public IConstituent getConstituentFromID(final char[] anID) {
		return this.container.getConstituentFromID(anID);
	}
	public IConstituent getConstituentFromID(final String anID) {
		return this.getConstituentFromID(anID.toCharArray());
	}
	public IConstituent getConstituentFromName(final char[] aName) {
		return this.container.getConstituentFromName(aName);
	}
	public IConstituent getConstituentFromName(final String aName) {
		return this.getConstituentFromName(aName.toCharArray());
	}
	public Iterator getIteratorOnConstituents() {
		return this.container.getIteratorOnConstituents();
	}
	public Iterator getIteratorOnConstituents(final IFilter aFilter) {
		return this.container.getIteratorOnConstituents(aFilter);
	}
	public Iterator getIteratorOnConstituents(java.lang.Class aConstituentType) {
		return this.container.getIteratorOnConstituents(aConstituentType);
	}
	public Iterator getIteratorOnModelListeners() {
		return this.container.getIteratorOnModelListeners();
	}
	public int getNumberOfConstituents() {
		return this.container.getNumberOfConstituents();
	}
	public int getNumberOfConstituents(final java.lang.Class aConstituentType) {
		return this.container.getNumberOfConstituents(aConstituentType);
	}
	protected char getPathSymbol() {
		return IConstants.PACKAGE_SYMBOL;
	}
	public void performCloneSession() {
		// The shallow copy must include a shallow copy of
		// member entities, because the clones of methods,
		// fields, and so on, depend on it!
		super.performCloneSession();

		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			constituent.performCloneSession();
		}
	}
	public void removeConstituentFromID(final char[] anID) {
		this.container.removeConstituentFromID(anID);
	}
	public void removeModelListener(final IModelListener aModelListener) {
		this.container.removeModelListener(aModelListener);
	}
	public void removeModelListeners(final List aListOfModelListeners) {
		this.container.removeModelListeners(aListOfModelListeners);
	}
	/**
	 * This method performs a shallow copy.
	 */
	public void startCloneSession() {
		// The shallow copy must include a shallow copy of
		// member entities, because the clones of methods,
		// fields, and so on, depend on it!
		super.startCloneSession();

		// Yann 2010/10/03: Objects!
		// The "container" is now an instance of a class
		// and must be assigned a new instance independently.
		// ((Package) this.getClone()).container.resetListOfConstituents();
		((Package) this.getClone()).container =
			new GenericContainerOfNaturallyOrderedConstituents(
				((Package) this.getClone()));

		// Yann 2015/09/01: Clone of listeners!
		// I don't forget to clone the listners too...
		// TODO To implement

		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			constituent.startCloneSession();
			((IPackage) this.getClone())
				.addConstituent((IConstituentOfModel) constituent.getClone());
		}
	}
	public String toString(final int tab) {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append("package");
		codeEq.append(' ');
		codeEq.append(this.getName());
		return codeEq.toString();
	}
}
