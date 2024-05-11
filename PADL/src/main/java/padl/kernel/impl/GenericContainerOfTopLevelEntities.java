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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import padl.event.EntityEvent;
import padl.event.IModelListener;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.util.CharArrayComparator;
import padl.util.adapter.ModelListenerAdapter;

class GenericContainerOfTopLevelEntities implements Serializable {
	private static final long serialVersionUID = -714741910389826912L;

	private final class ModelListener extends ModelListenerAdapter
			implements Serializable {

		private static final long serialVersionUID = -3585462658647723807L;

		public void entityAdded(EntityEvent entityEvent) {
			GenericContainerOfTopLevelEntities.this.dirty = true;
		}

		public void entityRemoved(EntityEvent entityEvent) {
			GenericContainerOfTopLevelEntities.this.dirty = true;
		}
	}

	// Yann 2010/10/10: DB4O
	// Used to be:
	//	private final IAbstractModel containerAbstractModel;
	// I removed the final to make DB4O works...
	// TODO: Understand how to keep it final with DB4O!
	private IAbstractModel containerAbstractModel;

	private boolean dirty;
	private final ModelListener listenerAbstractModel = new ModelListener();

	// Yann 2010/10/10: DB4O
	// Used to be:
	//	private final Map mapOfIDsEntities = new TreeMap();
	// I removed the final to make DB4O works...
	// TODO: Understand how to keep it final with DB4O!
	private Map mapOfIDsEntities = new TreeMap(
			CharArrayComparator.getInstance());

	public GenericContainerOfTopLevelEntities(
			final IAbstractModel anAbstractModel) {

		this.containerAbstractModel = anAbstractModel;
		this.dirty = true;
	}

	public void addTopLevelEntity(
			final IConstituentOfModel aConstituentOfModel) {
		this.mapOfIDsEntities.put(aConstituentOfModel.getID(),
				aConstituentOfModel);
	}

	// Yann 2011/06/20: Top-level entities...
	// Now that the AbstractLevelModel uses a listener to update appropriately
	// this container when constituent are added/removed, I could surely use it
	// to remove the following three methods and use the added/removed methods
	// of the listener to do their jobs only when need, i.e., when adding a
	// non-empty package to the model! This new implementation should improved
	// performances...
	private void createMapOfIDsEntities() {
		// ProxyConsole.getInstance().debugOutput().print("(Re)Creating the maps of IDs and Entities");

		// Yann 2008/11/17: Walker!
		// I cannot use the visitor to count the number
		// of entities as new entity *not* taken into
		// account could be added to the model, for
		// example IEnum... I must compute the entities
		// recursively "by hand".
		// AbstractLevelModel.UniqueEntityFinder.reset();
		// AbstractLevelModel.UniqueEntityFinder.setID(anID);
		// this.walk(AbstractLevelModel.UniqueEntityFinder);
		// final IEntity entity =
		// (IEntity) AbstractLevelModel.UniqueEntityFinder.getResult();
		// if (entity != null) {
		// this.mapOfIDsEntities.put(anID, entity);
		// }
		this.mapOfIDsEntities.clear();

		final Iterator iterator = this.containerAbstractModel
				.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (constituent instanceof IPackage) {
				this.createMapOfIDsEntities((IPackage) constituent);
			}
			else if (IFirstClassEntity.class
					.isAssignableFrom(constituent.getClass())) {

				this.createMapOfIDsEntities((IFirstClassEntity) constituent);
			}
		}

		this.dirty = false;
	}

	private void createMapOfIDsEntities(
			final IFirstClassEntity aConstituentOfModel) {

		// Yann 2009/05/03: Comparable!
		// I must use String as keys because
		// char arrays are not Comparable...
		// TODO: Implement my own hashtable!
		// Yann 2010/06/20: Arrays!
		// The mapOfIDsEntities now implements a comparator
		// for char arrays to avoid unnecessary conversion.
		this.mapOfIDsEntities.put(aConstituentOfModel.getID(),
				aConstituentOfModel);
	}

	private void createMapOfIDsEntities(final IPackage aPackage) {
		final Iterator iterator = aPackage.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (constituent instanceof IPackage) {
				this.createMapOfIDsEntities((IPackage) constituent);
			}
			else if (IFirstClassEntity.class
					.isAssignableFrom(constituent.getClass())) {

				this.createMapOfIDsEntities((IFirstClassEntity) constituent);
			}
		}
	}

	public boolean doesContainTopLevelEntityWithID(char[] anID) {
		return this.getTopLevelEntityFromID(anID) != null;
	}

	public Iterator getIteratorOnTopLevelEntities() {
		if (this.dirty) {
			this.createMapOfIDsEntities();
		}

		// Yann 2008/11/05: Sort!
		// I make sure I sort the list of entities according to
		// their ID to preserve the behavior of other code that
		// assumes this order.
		final List listOfEntities = new ArrayList(
				this.mapOfIDsEntities.values());
		Collections.sort(listOfEntities, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				return CharArrayComparator.getInstance().compare(
						((IFirstClassEntity) o1).getID(),
						((IFirstClassEntity) o2).getID());
			}
		});
		return listOfEntities.iterator();
	}

	IModelListener getModelListener() {
		return this.listenerAbstractModel;
	}

	public int getNumberOfTopLevelEntities() {
		if (this.dirty) {
			this.createMapOfIDsEntities();
		}

		return this.mapOfIDsEntities.size();
	}

	public int getNumberOfTopLevelEntities(final java.lang.Class aClass) {
		if (this.dirty) {
			this.createMapOfIDsEntities();
		}

		int number = 0;
		final Iterator iterator = this.mapOfIDsEntities.values().iterator();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (aClass.isAssignableFrom(constituent.getClass())) {
				number++;
			}
		}
		return number;
	}

	public IFirstClassEntity getTopLevelEntityFromID(final char[] anID) {
		// Yann 2008/11/04: Global access!
		// The addition of packages means that we need a global
		// mechanism to access constituent wherever they are...
		if (this.dirty) {
			this.createMapOfIDsEntities();
		}

		return (IFirstClassEntity) this.mapOfIDsEntities.get(anID);
	}

	public void removeTopLevelEntityFromID(final char[] anID) {
		// Yann 2008/11/04: Global access!
		// The addition of packages means that we need a global
		// mechanism to access constituent wherever they are...

		// Yann 2011/06/20: Top-level entities...
		// If a user wants to remove a top-level entity by calling the
		// removeTopLevelEntityFromID(char[]) method on a model, then the
		// removeConstituentFromID(char[]) method is called, to also
		// remove the entity from the appropriate container, which then
		// notifies the GenericContainerOfTopLevelEntity, which finally
		// call again this very same method... Hence the need to test of
		// null entity: the entity has already been removed...
		// Yann 2013/05/22: Again...
		// I do not need to do that "manually" because it is taken care of by
		//	GenericContainerOfTopLevelEntities.TopLevelEntityManager
		// which is interesting use of the Observer design pattern!
		//	final IFirstClassEntity entity = this.getTopLevelEntityFromID(anID);
		//	if (entity != null) {
		//		if (!isMove) {
		//			try {
		//				final IContainer container =
		//					Finder.findContainer(
		//						entity.getDisplayPath(),
		//						this.containerAbstractModel);
		//				container.removeConstituentFromID(anID);
		//			}
		//			catch (final FormatException e) {
		//				e.printStackTrace(Output.getInstance().errorOutput());
		//			}
		//		}
		//	}
		this.mapOfIDsEntities.remove(anID);
	}
}
