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
package ptidej.solver.test.data.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import padl.event.IEvent;
import padl.event.IModelListener;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IFactory;
import padl.kernel.IFilter;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.kernel.exception.ModelDeclarationException;
import padl.util.Util;
import padl.visitor.IGenerator;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

public abstract class TestAbstractLevelModel implements IAbstractLevelModel {
	private static final char[] Message = "SourceAdapter".toCharArray();
	private static final long serialVersionUID = -786934717930224538L;

	private IFactory factory;
	private final List listOfConstituents = new ArrayList();
	private final Map mapOfIDsEntities = new HashMap();

	public void addConstituent(final IConstituent aConstituent) {
		if (aConstituent instanceof IPackage) {
			this.addConstituents((IPackage) aConstituent);
		}
		else {
			throw new ModelDeclarationException(
				"Cannot add an entity to a model, only packages!");
		}
	}
	public void addConstituent(final IConstituentOfModel constituent) {
	}
	public void addConstituents(final IPackage aPackage) {
		this.listOfConstituents.add(aPackage);
	}
	public void addModelListener(final IModelListener aModelListener) {
	}
	public void addModelListeners(final List someModelListeners) {
	}
	public Object clone() {
		return null;
	}
	public void copyIn(final IAbstractModel aDestinationModel) {
	}
	private void createMapOfIDsEntities() {
		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			this.createMapOfIDsEntities((IPackage) iterator.next());
		}
	}
	private void createMapOfIDsEntities(final IPackage aPackage) {
		final Iterator iterator = aPackage.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (constituent instanceof IPackage) {
				this.createMapOfIDsEntities((IPackage) constituent);
			}
			else if (constituent instanceof IFirstClassEntity) {
				this.mapOfIDsEntities.put(constituent.getID(), constituent);
			}
		}
	}
	public boolean doesContainConstituentWithID(final char[] anID) {
		return false;
	}
	public boolean doesContainConstituentWithID(final String anID) {
		return false;
	}
	public boolean doesContainConstituentWithName(final char[] name) {
		return false;
	}
	public boolean doesContainConstituentWithName(final String aName) {
		return false;
	}
	public boolean doesContainTopLevelEntityWithID(final char[] anID) {
		return false;
	}
	public void fireModelChange(final String anEventType, final IEvent anEvent) {
	}
	public String generate(final IGenerator aBuilder) {
		aBuilder.open(this);

		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			((IConstituent) iterator.next()).accept(aBuilder);
		}

		aBuilder.close(this);
		return aBuilder.getCode();
	}
	public Iterator getConcurrentIteratorOnConstituents() {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		// (The name of the method is deliberate for consistency
		// and also as a joke :-).)
		final IConstituent[] array =
			new IConstituent[this.listOfConstituents.size()];
		this.listOfConstituents.toArray(array);
		return Arrays.asList(array).iterator();
	}
	public Iterator getConcurrentIteratorOnConstituents(final IFilter filter) {
		return null;
	}
	public Iterator getConcurrentIteratorOnConstituents(
		final java.lang.Class aConstituentType) {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		// (The name of the method is deliberate for consistency
		// and also as a joke :-).)
		final IConstituent[] array =
			new IConstituent[this.listOfConstituents.size()];
		this.listOfConstituents.toArray(array);
		return Util.getTypedConstituentsIterator(
			array,
			this.listOfConstituents.size(),
			aConstituentType);
	}
	public IConstituent getConstituentFromID(final char[] anID) {
		return null;
	}
	public IConstituent getConstituentFromID(final String anID) {
		final Iterator iterator = this.listOfConstituents.iterator();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (constituent.getID().equals(anID)) {
				return constituent;
			}
		}
		return null;
	}
	public IConstituent getConstituentFromName(final char[] name) {
		return null;
	}
	public IConstituent getConstituentFromName(final String aName) {
		final Iterator iterator = this.listOfConstituents.iterator();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (constituent.getName().equals(aName)) {
				return constituent;
			}
		}
		return null;
	}
	public String getDisplayName() {
		return null;
	}
	public String getDisplayPath() {
		return null;
	}
	public IFactory getFactory() {
		return this.factory;
	}
	public Iterator getIteratorOnConstituents() {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		return this.listOfConstituents.iterator();
	}
	public Iterator getIteratorOnConstituents(final IFilter filter) {
		return null;
	}
	public Iterator getIteratorOnConstituents(
		final java.lang.Class aConstituentType) {

		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		// TODO: Implement my own "smart" iterator which
		// could be tightly linked with the AbstractContainer
		// class to prevent too many memory allocation (Singleton).
		final IConstituent[] array =
			new IConstituent[this.listOfConstituents.size()];
		this.listOfConstituents.toArray(array);
		return Util.getTypedConstituentsIterator(
			array,
			this.listOfConstituents.size(),
			aConstituentType);
	}
	public Iterator getIteratorOnModelListeners() {
		return null;
	}
	public Iterator getIteratorOnTopLevelEntities() {
		// System.out.println("Iterating on entities");

		// Yann 2008/11/17: Walker!
		// I cannot use the visitor to count the number
		// of entities as new entity *not* taken into
		// account could be added to the model, for
		// example IEnum... I must compute the entities
		// recursively "by hand".
		//	AbstractLevelModel.UniqueEntityLister.reset();
		//	this.walk(AbstractLevelModel.UniqueEntityLister);
		//	final List listOfEntities =
		//		(List) AbstractLevelModel.UniqueEntityLister.getResult();
		this.createMapOfIDsEntities();
		final List listOfEntities =
			new ArrayList(this.mapOfIDsEntities.values());

		// Yann 2008/11/05: Sort!
		// I make sure I sort the list of entities according to
		// their ID to preserve the behavior of other code that
		// assumes this order.
		Collections.sort(listOfEntities, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				return ((IFirstClassEntity) o1).getDisplayID().compareTo(
					((IFirstClassEntity) o2).getDisplayID());
			}
		});
		return listOfEntities.iterator();
	}
	public char[] getName() {
		return TestAbstractLevelModel.Message;
	}
	public int getNumberOfConstituents() {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		return this.listOfConstituents.size();
	}
	public int getNumberOfConstituents(final java.lang.Class aConstituentType) {
		// Yann 2005/10/12: Iterator!
		// I replace the list with an iterator, but this
		// is a major bottleneck in some specific case!
		throw new RuntimeException(
			"Method int getNumberOfConsituents(final java.lang.Class) is not implemented.");
	}
	public int getNumberOfTopLevelEntities() {
		// Yann 2008/11/17: Walker!
		// I cannot use the visitor to count the number
		// of entities as new entity *not* taken into
		// account could be added to the model, for
		// example IEnum... I must compute the entities
		// recursively "by hand".
		//	AbstractLevelModel.UniqueEntityLister.reset();
		//	this.walk(AbstractLevelModel.UniqueEntityLister);
		//	return ((List) AbstractLevelModel.UniqueEntityLister.getResult())
		//		.size();

		this.createMapOfIDsEntities();
		return this.mapOfIDsEntities.size();
	}
	public int getNumberOfTopLevelEntities(final Class aConstituentType) {
		return -1;
	}
	public char[] getPath() {
		return new char[0];
	}
	public IFirstClassEntity getTopLevelEntityFromID(final char[] anID) {
		return null;
	}
	public IFirstClassEntity getTopLevelEntityFromID(final String anID) {
		// Yann 2008/11/04: Global access!
		// The addition of packages means that we need a global
		// mechanism to access constituent wherever they are...
		// TODO: Move this mechanism in AbstractContainer and make sure that the map is updated when an entity is removed!
		if (!this.mapOfIDsEntities.containsKey(anID)) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("Searching for " + anID);
			// Yann 2008/11/17: Walker!
			// I cannot use the visitor to count the number
			// of entities as new entity *not* taken into
			// account could be added to the model, for
			// example IEnum... I must compute the entities
			// recursively "by hand".
			//	AbstractLevelModel.UniqueEntityFinder.reset();
			//	AbstractLevelModel.UniqueEntityFinder.setID(anID);
			//	this.walk(AbstractLevelModel.UniqueEntityFinder);
			//	final IEntity entity =
			//		(IEntity) AbstractLevelModel.UniqueEntityFinder.getResult();
			//	if (entity != null) {
			//		this.mapOfIDsEntities.put(anID, entity);
			//	}

			this.createMapOfIDsEntities();
		}
		return (IFirstClassEntity) this.mapOfIDsEntities.get(anID);
	}
	public void moveIn(final IAbstractLevelModel aDestinationModel) {
		// TODO To implement
	}
	public void moveIn(final IAbstractModel aDestinationModel) {
	}
	public void removeConstituentFromID(final char[] anID) {
	}
	//	public List listOfActors() {
	//		return this.listOfActors;
	//	}
	public void removeConstituentFromID(final String anID) {
	}
	public void removeModelListener(final IModelListener aModelListener) {
	}
	public void removeModelListeners(final List modelListeners) {
	}
	public void removeTopLevelEntityFromID(final char[] anID) {
	}
	public void removeTopLevelEntityFromID(final String anID) {
		// Yann 2008/11/04: Global access!
		// The addition of packages means that we need a global
		// mechanism to access constituent wherever they are...
		// TODO: Move this mechanism in AbstractContainer and make sure that the map is updated when an entity is removed!
		if (!this.mapOfIDsEntities.containsKey(anID)) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println("Searching for " + anID);
			// Yann 2008/11/17: Walker!
			// I cannot use the visitor to count the number
			// of entities as new entity *not* taken into
			// account could be added to the model, for
			// example IEnum... I must compute the entities
			// recursively "by hand".
			//	AbstractLevelModel.UniqueEntityFinder.reset();
			//	AbstractLevelModel.UniqueEntityFinder.setID(anID);
			//	this.walk(AbstractLevelModel.UniqueEntityFinder);
			//	final IEntity entity =
			//		(IEntity) AbstractLevelModel.UniqueEntityFinder.getResult();
			//	if (entity != null) {
			//		this.mapOfIDsEntities.put(anID, entity);
			//	}

			this.createMapOfIDsEntities();
		}

		this.removeConstituentFromID(anID);
		this.mapOfIDsEntities.remove(anID);
	}
	public void setFactory(final IFactory aFactory) {
		this.factory = aFactory;
	}
	public Object walk(final IWalker aWalker) {
		aWalker.open(this);

		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			((IConstituent) iterator.next()).accept(aWalker);
		}

		aWalker.close(this);
		return aWalker.getResult();
	}
}
