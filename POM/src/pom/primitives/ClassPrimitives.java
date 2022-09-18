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
package pom.primitives;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IElement;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IMethod;
import padl.kernel.IOperation;
import padl.util.Util;
import pom.operators.Operators;

/**
 * @author Farouk ZAIDI
 * @since 2004/02/01
 * 
 * Note: Monday January 26, 13h30 Considering Yann's idea, it is better to use a
 * recursive algorithm than a visitor of undirect children of parents.
 */

public class ClassPrimitives extends Primitives {
	private static ClassPrimitives UniqueInstance;
	public static ClassPrimitives getInstance() {
		if (ClassPrimitives.UniqueInstance == null) {
			ClassPrimitives.UniqueInstance = new ClassPrimitives();
		}
		return ClassPrimitives.UniqueInstance;
	}

	private Operators operators = Operators.getInstance();

	/**
	 * Constructor Initializes the instance and handles an instance of the model
	 * 
	 * @param introspector
	 */
	private ClassPrimitives() {
		super();
	}

	/**
	 * Returns the methods of the ancestor entities considering an entity.
	 * Methods that are equal (same signature, actorID or one overloads another)
	 * are considered like one method only. (This method corresponds to
	 * Methods(Ancestors(E)))
	 * 
	 * @param firstClassEntity
	 * @return the methods of the ancestor entities considering the entity
	 */
	private final List ancestorsMethods(final IFirstClassEntity firstClassEntity) {
		final List methodAncestors = new ArrayList();
		final List ancestors =
			new ArrayList(this.listOfAncestors(firstClassEntity));

		for (final Iterator iterAncestorEntity = ancestors.iterator(); iterAncestorEntity
			.hasNext();) {

			final IFirstClassEntity nextAncestor =
				(IFirstClassEntity) iterAncestorEntity.next();

			for (final Iterator iteratorOnElements =
				nextAncestor.getIteratorOnConstituents(); iteratorOnElements
				.hasNext();) {

				final IElement element = (IElement) iteratorOnElements.next();
				if (element instanceof IMethod
						&& !methodAncestors.contains(element)) {

					final IMethod method = (IMethod) element;
					if (!method.isPrivate())
						// February 17, 2004 - 11h28: Farouk->I take account of
						// private methods, but inherited methods must be
						// protected or public.
						methodAncestors.add(method);
				}
			}
		}

		return methodAncestors;
	}
	/**
	 * @param firstClassEntity TODO
	 * @author Karim DHAMBRI
	 * @since 2005/??/??
	 */
	public String extractPackageName(final IFirstClassEntity firstClassEntity) {
		String name = firstClassEntity.getDisplayName();
		int lastPointIndex = name.lastIndexOf(".");
		if (lastPointIndex == -1) {
			return "";
		}
		return name.substring(0, lastPointIndex);
	}

	//	public final Iterator getIteratorOnTopLevelEntities() {
	//		return this.abstractLevelModel.getIteratorOnTopLevelEntities();
	//
	//		// final String key = "allEntities"+this.getIdiomLevelModel().getName();
	//		// List tmpLst = (List) cachedLists.get(key);
	//		// if (tmpLst != null)
	//		// return tmpLst;
	//		//		
	//		// final List entities = new ArrayList();
	//		// for (final Iterator iterEntity =
	//		// this.getIdiomLevelModel().listOfConstituents().iterator();
	//		// iterEntity.hasNext();) {
	//		// Object o = (Object) iterEntity.next();
	//		// if(o instanceof IEntity)
	//		// entities.add(o);
	//		// }
	//		// this.put(key, entities);
	//		// return entities;
	//	}

	/**
	 * Returns the number of children of the entity
	 * 
	 * @param firstClassEntity
	 * @return the number of children of the entity
	 */
	public final int getNumberOfChildren(
		final IFirstClassEntity firstClassEntity) {

		return firstClassEntity.getNumberOfInheritingEntities();
	}
	/**
	 * Returns a new list containing the parents of the entity. The list
	 * contains the interfaces and the super classes.
	 * 
	 * @param firstClassEntity
	 * @return the list of the interfaces and the super classes
	 */
	public final List listOfAllDirectParents(
		final IFirstClassEntity firstClassEntity) {
		final List parents = new ArrayList();

		Iterator iterator = firstClassEntity.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity inheritedEntity =
				(IFirstClassEntity) iterator.next();
			parents.add(inheritedEntity);
		}
		if (firstClassEntity instanceof IClass) {
			iterator =
				((IClass) firstClassEntity)
					.getIteratorOnImplementedInterfaces();
			while (iterator.hasNext()) {
				final IFirstClassEntity inheritedEntity =
					(IFirstClassEntity) iterator.next();
				parents.add(inheritedEntity);
			}
		}

		return parents;
	}

	/**
	 * Returns all the methods (M) of an entity (including herited methods from
	 * ancestors of the entity). Methods that are equal (same signature, actorID
	 * or one overloads another) are considered like one method only.
	 * 
	 * @param firstClassEntity
	 * @return all the methods that an entity can call
	 */
	public final List listOfAllMethods(final IFirstClassEntity firstClassEntity) {
		final List listOfMethods = new ArrayList();
		final List listOfEntities = new ArrayList();

		/* Init the list of all the considered entities. */
		listOfEntities.add(firstClassEntity);
		listOfEntities.addAll(this.listOfAncestors(firstClassEntity));

		// For every entity...
		final Iterator iteratorOnEntities = listOfEntities.iterator();
		while (iteratorOnEntities.hasNext()) {
			final IFirstClassEntity nextEntity =
				(IFirstClassEntity) iteratorOnEntities.next();

			// ... For every element of the entity...
			final Iterator iteratorOnElements =
				nextEntity.getIteratorOnConstituents();
			while (iteratorOnElements.hasNext()) {
				final IConstituent constituent =
					(IConstituent) iteratorOnElements.next();

				if (constituent instanceof IMethod) {
					final IMethod method = (IMethod) constituent;
					// Yann 2009/05/13: How did it work before?
					// We must check if a method with a same signature does
					// not already exist or we have twice overloaded methods
					// or the concrete and the abstract method! The equals()
					// must take care of that.
					if (!(method.isPrivate() && (firstClassEntity != nextEntity))
							&& !listOfMethods.contains(method)) {
						// Feb 17,2004: Farouk -> We don't care about
						// private methods that do not belong to the entity
						listOfMethods.add(method);
					}
				}
			}
		}
		return listOfMethods;
	}

	/**
	 * Returns the ancestors of the entity
	 * 
	 * Due to the meta model, it is important to ignore ghosts. Also, in the
	 * JAVA specification, the Object class inherit from nothing. The meta-model
	 * constructs a ghost with no name. So, if the entity is a ghost or is the
	 * Object class, an empty list is returned.
	 * 
	 * @param firstClassEntity
	 * @return the ancestors of the entity
	 */
	public final List listOfAncestors(final IFirstClassEntity firstClassEntity) {
		// Special case where the entity is a ghost or java.lang.Object.
		if (firstClassEntity instanceof IGhost
				|| Util.isObjectModelRoot(firstClassEntity.getID())) {
			// TODO: Replace java.lang.Object test
			// if(entity instanceof IGhost || entity.isAboveInHierarchy())
			return new ArrayList();
		}

		// We initialize the set.
		final Set aListAncestors = new HashSet();

		// We get every super-interface and every super-class.
		Iterator iterator = firstClassEntity.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity inheritedEntity =
				(IFirstClassEntity) iterator.next();
			aListAncestors.add(inheritedEntity);
		}
		if (firstClassEntity instanceof IClass) {
			iterator =
				((IClass) firstClassEntity)
					.getIteratorOnImplementedInterfaces();
			while (iterator.hasNext()) {
				final IFirstClassEntity inheritedEntity =
					(IFirstClassEntity) iterator.next();
				aListAncestors.add(inheritedEntity);
			}
		}

		final Set copyOfListAncestors = new HashSet(aListAncestors);
		final Iterator iterParent = copyOfListAncestors.iterator();
		while (iterParent.hasNext()) {
			final IFirstClassEntity aParent =
				(IFirstClassEntity) iterParent.next();
			aListAncestors.addAll(this.listOfAncestors(aParent));
		}

		final List resultList = new ArrayList(aListAncestors);
		return resultList;
	}

	/**
	 * Returns the methods defined directly in the class body (This method
	 * corresponds to Methods(E))
	 * 
	 * @param firstClassEntity
	 * @return the methods defined directly in the class body
	 */
	public final List listOfDeclaredMethods(
		final IFirstClassEntity firstClassEntity) {
		final List results = new ArrayList();
		final Iterator iterator =
			firstClassEntity.getIteratorOnConstituents(IOperation.class);
		while (iterator.hasNext()) {
			final IOperation method = (IOperation) iterator.next();
			results.add(method);
		}
		return results;
	}

	//	/**
	//	 * Returns a new list containing the classes
	//	 * 
	//	 * @param entity
	//	 * @return a new list containing the super classes
	//	 */
	//	public final List listOfSuperClasses(final IEntity entity) {
	//		return new ArrayList(entity.listOfInheritedEntities());
	//	}

	//	/**
	//	 * Returns a new list containing the interfaces If the entity is not a
	//	 * class, the returned list is empty.
	//	 * 
	//	 * @param entity
	//	 * @return a new list containing the interfaces
	//	 */
	//	public final List listOfSuperInterfaces(final IEntity entity) {
	//		if (entity instanceof IClass) {
	//			return new ArrayList(((IClass) entity).listOfImplementedEntities());
	//		}
	//
	//		return new ArrayList();
	//	}

	/**
	 * Returns a new list containing the descendents of the entity
	 * 
	 * @param firstClassEntity
	 * @return the list of descendents
	 */
	public final List listOfDescendents(final IFirstClassEntity firstClassEntity) {
		if (firstClassEntity instanceof IGhost) {
			return new ArrayList();
		}
		final List setOfDescendents = new ArrayList();
		final Iterator childrenOfEntity =
			firstClassEntity.getIteratorOnInheritingEntities();

		while (childrenOfEntity.hasNext()) {
			final IFirstClassEntity child =
				(IFirstClassEntity) childrenOfEntity.next();
			setOfDescendents.add(child);
			setOfDescendents.addAll(this.listOfDescendents(child));
		}

		return setOfDescendents;
	}

	/**
	 * Returns the attributes defined directly in the class body
	 * 
	 * @param firstClassEntity
	 * @return the attributes defined directly in the class body
	 */
	public final List listOfImplementedFields(
		final IFirstClassEntity firstClassEntity) {
		final List results = new ArrayList();
		final Iterator iterator =
			firstClassEntity.getIteratorOnConstituents(IField.class);
		while (iterator.hasNext()) {
			final IField field = (IField) iterator.next();
			results.add(field);
		}
		return results;
	}

	/**
	 * Returns the declared methods (Md) of an entity: inherited methods that
	 * are not overloaded and abstract methods
	 * 
	 * @param firstClassEntity
	 * @return
	 */
	public final List listOfInheritedAndAbstractMethods(
		final IFirstClassEntity firstClassEntity) {
		final List results = new ArrayList();
		final List listOfMethods = this.listOfDeclaredMethods(firstClassEntity);
		final Iterator iteratorMethod = listOfMethods.iterator();
		while (iteratorMethod.hasNext()) {
			final IOperation nextMethod = (IOperation) iteratorMethod.next();
			if (nextMethod.isAbstract()) {
				results.add(nextMethod);
			}
		}
		results.addAll(this.listOfInheritedMethods(firstClassEntity));
		return results;
	}

	public final List listOfInheritedAndImplantedFields(
		IFirstClassEntity firstClassEntity) {
		final List entityAncestors = this.listOfAncestors(firstClassEntity);
		entityAncestors.add(firstClassEntity);
		final List fields = new ArrayList();
		final Iterator iterEntity = entityAncestors.iterator();
		while (iterEntity.hasNext()) {
			final IFirstClassEntity anEntity =
				(IFirstClassEntity) iterEntity.next();
			fields.addAll(this.listOfImplementedFields(anEntity));
		}
		return fields;
	}
	/**
	 * Returns the inherited methods of an entity
	 * 
	 * @param firstClassEntity
	 * @return the inherited methods of an entity
	 */
	public final Collection listOfInheritedMethods(
		final IFirstClassEntity firstClassEntity) {
		final Collection list =
			this.operators.difference(
				ancestorsMethods(firstClassEntity),
				listOfDeclaredMethods(firstClassEntity));
		return list;
	}
	/**
	 * Returns the new methods of an entity
	 * 
	 * @param firstClassEntity
	 * @return the new methods of an entity
	 */
	public final Collection listOfNewMethods(
		final IFirstClassEntity firstClassEntity) {
		return this.operators.difference(
			this.listOfDeclaredMethods(firstClassEntity),
			this.ancestorsMethods(firstClassEntity));
	}
	/**
	 * Returns the methods of the ancestors considering an entity
	 * 
	 * @param entity
	 * @return the methods of the ancestors considering an entity
	 */
	/*
	 * private List methodAncestors(IEntity entity) { List
	 * listMethodsOfAncestors = new ArrayList(); List listAncestorsOfEntity =
	 * this.ancestor(entity);
	 * 
	 * for (final Iterator iteratorAncestor = listAncestorsOfEntity.iterator();
	 * iteratorAncestor.hasNext();) { IEntity nextAncestor = (IEntity)
	 * iteratorAncestor.next();
	 * listMethodsOfAncestors.addAll(allMethods(nextAncestor)); } return
	 * listMethodsOfAncestors; }
	 */
	/**
	 * Returns the implemented methods (Mi) of an entity: Mi(entity)=
	 * M(entity)-Md(entity) Methods are non-inherited, non-abstract and
	 * overloaded some inherited methods
	 * 
	 * @param firstClassEntity
	 * @return the implemented methods of an entity
	 */
	public final Collection listOfOverriddenAndConcreteMethods(
		final IFirstClassEntity firstClassEntity) {
		final Collection list =
			this.operators.difference(
				this.listOfAllMethods(firstClassEntity),
				this.listOfInheritedAndAbstractMethods(firstClassEntity));
		return list;
	}

	/**
	 * Returns the overloaded methods of an entity
	 * 
	 * @param firstClassEntity
	 * @return the overloaded methods of an entity
	 */
	public final Collection listOfOverriddenMethods(
		final IFirstClassEntity firstClassEntity) {
		return this.operators.intersection(
			listOfDeclaredMethods(firstClassEntity),
			ancestorsMethods(firstClassEntity));
	}

	public final int getNumberOfParents(final IFirstClassEntity firstClassEntity) {
		return firstClassEntity.getNumberOfInheritedEntities();
	}
}
