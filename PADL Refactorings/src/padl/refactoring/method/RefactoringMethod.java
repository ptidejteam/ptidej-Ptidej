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
package padl.refactoring.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IParameter;
import padl.kernel.impl.Factory;

// TODO: Should this class be the superclass of all refactorings or a utility
// class whose (then static methods) are called when appropriate? BTW, Pierre
// may need some of your methods :-)

// TODO: Add "final" keyword when appropriate in all classes.

// TODO: Apply source formatter "CTRL + SHIFT + F"

// TODO: Clean up code, i.e., remove commented code that is not useful anymore.

/**
 * @author Saliha Bouden
 * @since 2006/03/31
 * 
 */
public class RefactoringMethod {

	protected IAbstractLevelModel abstractLevelModel;

	public RefactoringMethod(final IAbstractLevelModel anAbstractLevelModel) {
		this.abstractLevelModel = anAbstractLevelModel;
	}

	public List getListOfClassesOfHierarchy(
		final Iterator list,
		final IClassComparator aComparator) {

		final List listOfClasses = new ArrayList();
		return getListOfClassesOfHierarchy(listOfClasses, list, aComparator);
	}

	private List getListOfClassesOfHierarchy(
		final List visitedList,
		final Iterator list,
		final IClassComparator aComparator) {

		final Iterator iterator = list;
		//		final List resultsList = new ArrayList();
		//		resultsList = visitedList;
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();

			if (firstClassEntity instanceof IClass) {
				if (aComparator.check(firstClassEntity.getDisplayName())) {
					visitedList.clear();
					return visitedList;
				}
				//				if (!resultsList.contains(entity)) {
				//					resultsList.add(entity);
				//				}	
				if (!this.doesClassExistInList(visitedList, firstClassEntity)) {
					//if (!visitedList.contains(entity)) {
					visitedList.add(firstClassEntity);

					final Iterator listOfInheritedEntities =
						firstClassEntity.getIteratorOnInheritedEntities();
					//			   	list.addAll(listOfInheritedEntities);
					//				visitedList.addAll(this.getListOfClassesOfHierarchy(visitedList,
					//						listOfInheritedEntities, aComparator));
					this.getListOfClassesOfHierarchy(
						visitedList,
						listOfInheritedEntities,
						aComparator);

					final Iterator listOfInheritingEntities =
						firstClassEntity.getIteratorOnInheritingEntities();
					//				list.add(listOfInheritingEntities);
					//				visitedList.addAll(this.getListOfClassesOfHierarchy(visitedList,
					//						listOfInheritingEntities, aComparator));
					this.getListOfClassesOfHierarchy(
						visitedList,
						listOfInheritingEntities,
						aComparator);
				}

			}
		}

		return visitedList;
	}

	public List getlistOfInterfaces(
		final Iterator list,
		final IClassComparator aComparator) {

		List listOfInterfaces = new ArrayList();
		// TODO: Add "this." when appropriate.
		return getlistOfInterfaces(listOfInterfaces, list, aComparator);
	}

	private List getlistOfInterfaces(
		final List visitedList,
		final Iterator list,
		final IClassComparator aComparator) {

		final Iterator iterator = list;
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();

			if (firstClassEntity instanceof IInterface) {
				if (aComparator.check(firstClassEntity.getDisplayName())) {
					visitedList.clear();
					return visitedList;
				}
				//				if (!this.doesClassExistInListOfinterface(
				//					visitedList,
				//					(IInterface) entity)) {
				if (!visitedList.contains(firstClassEntity)) {
					visitedList.add(firstClassEntity);

					final Iterator listOfImplementedEntities =
						firstClassEntity.getIteratorOnInheritedEntities();
					this.getlistOfInterfaces(
						visitedList,
						listOfImplementedEntities,
						aComparator);

				}
			}
		}
		return visitedList;
	}

	//private boolean doesMethodInvoquedInClass(
	protected boolean doesMethodInvoquedInClass(
		String theMethodID,
		IClass sourceClass) {
		final Iterator iteratorOnMethods =
			sourceClass.getIteratorOnConstituents(IMethod.class);
		while (iteratorOnMethods.hasNext()) {
			final IMethod method = (IMethod) iteratorOnMethods.next();
			if (!method.getID().equals(theMethodID)) {
				final Iterator iteratorOnInvocations =
					method.getIteratorOnConstituents(IMethodInvocation.class);
				while (iteratorOnInvocations.hasNext()) {
					final IMethodInvocation methodInvocation =
						(IMethodInvocation) iteratorOnInvocations.next();
					if (methodInvocation != null) {
						if (methodInvocation.equals(theMethodID)) {
							//							return true;

							//					if (methodInvocation.getCalledMethod().equals(theMethodID)){
							//						return true;
							//    RESTE A BIEN VÉRIFIER
							//if (methodInvocation.getCalledMethod().equals(theMethodID)){

							return true;

						}
					}
				}
			}
		}
		return false;
	}

	// private IMethod getMethodToRefactor(
	public IMethod getMethodToRefactor(
		final String className,
		final String nameMethod) {
		final IFirstClassEntity aClass =
			(IFirstClassEntity) this.abstractLevelModel
				.getTopLevelEntityFromID(className);
		final Iterator iterator =
			aClass.getConcurrentIteratorOnConstituents(IMethod.class);
		while (iterator.hasNext()) {
			IMethod aMethod = (IMethod) iterator.next();

			if (aMethod.getName().equals(nameMethod)) {
				return aMethod;
			}
		}
		return null;
	}

	public String createNewMethodSignature(
		final IMethod theMethod,
		final String newName) {

		IMethod method =
			Factory.getInstance().createMethod(
				newName.toCharArray(),
				newName.toCharArray());

		final Iterator iteratorOnParameters =
			theMethod.getIteratorOnConstituents(IParameter.class);
		while (iteratorOnParameters.hasNext()) {
			final IParameter oldParameter =
				(IParameter) iteratorOnParameters.next();
			final IParameter newParameter =
				Factory.getInstance().createParameter(
					oldParameter.getType(),
					oldParameter.getName(),
					oldParameter.getCardinality());
			method.addConstituent(newParameter);

			method.setVisibility(theMethod.getVisibility());
		}
		return method.getDisplayID();
	}

	public IClass getClassContainsMethodToRefactor(
		final String className,
		final String nameMethod) {

		final IClass aClass =
			(IClass) this.abstractLevelModel.getTopLevelEntityFromID(className);

		final Iterator iterator =
			aClass.getConcurrentIteratorOnConstituents(IMethod.class);
		while (iterator.hasNext()) {
			IMethod aMethod = (IMethod) iterator.next();

			if (aMethod.getName().equals(nameMethod))
				return aClass;

		}
		return null;
	}

	/**
	 * Cette méthode permet de vérifier que newName n'existe pas dans une classe
	 * en supportant la surcharge des méthodes
	 */
	public boolean doesNewMethodExistWithOverloading(
		String anID,
		final String newMethod) {

		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) this.abstractLevelModel
				.getTopLevelEntityFromID(anID);
		final Iterator iterator =
			firstClassEntity.getIteratorOnConstituents(IMethod.class);
		while (iterator.hasNext()) {

			IMethod aMethod = (IMethod) iterator.next();
			if (aMethod.getID().equals(newMethod)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Cette méthode permet de vérifier que newName n'existe pas dans une classe
	 * sans supporter la surcharge des méthodes
	 */

	// private boolean doesNewMethodExist(
	protected boolean doesNewMethodExist(final String anID, final String newName) {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) this.abstractLevelModel
				.getTopLevelEntityFromID(anID);
		final Iterator iterator =
			firstClassEntity.getIteratorOnConstituents(IMethod.class);
		while (iterator.hasNext()) {

			IMethod aMethod = (IMethod) iterator.next();
			if (aMethod.getName().equals(newName))
				return true;
		}
		return false;
	}

	private boolean doesClassExistInList(
		List list,
		IFirstClassEntity firstClassEntity) {
		final Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			final IClass theEntity = (IClass) iterator.next();
			{
				if (theEntity.getName().equals(firstClassEntity.getName())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * cette méthode vérifie que le nouveau nom newName n'existe pas dans la
	 * liste des classes de l'hiérarchie d'héritage passée en paramètre. Elle
	 * retourne true si newName existe déjà et n'accepte pas la surcharge des
	 * méthodes
	 */

	// private boolean doesNewMethodExist(
	protected List doesNewMethodExist(final Iterator list, final String newName) {
		final List listOfClassesOfHierarchy =
			this.getListOfClassesOfHierarchy(list, new IClassComparator() {
				public boolean check(final String anID) {
					return RefactoringMethod.this.doesNewMethodExist(
						anID,
						newName);
				}
			});
		// return !listOfClassesOfHierarchy.isEmpty();
		return listOfClassesOfHierarchy;
	}

	// private boolean doesNewMethodExistInHierarchyofInterface(
	protected List doesNewMethodExistInHierarchyofInterface(
		final Iterator list,
		final String newMethod) {

		final List listOfInterfaces =
			this.getlistOfInterfaces(list, new IClassComparator() {
				public boolean check(final String anID) {
					return RefactoringMethod.this.doesNewMethodExist(
						anID,
						newMethod);
				}
			});

		// return !listOfInterfaces.isEmpty();
		return listOfInterfaces;
	}

	protected List doesNewMethodExistInHierarchyofInterfaceWithOverloading(
		final Iterator list,
		final String aNewMethodSignature) {

		final List listOfInterfaces =
			this.getlistOfInterfaces(list, new IClassComparator() {
				public boolean check(final String anID) {
					return RefactoringMethod.this
						.doesNewMethodExistWithOverloading(
							anID,
							aNewMethodSignature);
				}
			});

		// return !listOfInterfaces.isEmpty();
		return listOfInterfaces;
	}

	/**
	 * cette méthode vérifie que le nouveau nom newName n'existe pas dans la
	 * liste des classes de l'hiérarchie d'héritage passée en paramètre. Elle
	 * retourne true si newName existe déjà et accepte la surcharge des méthodes
	 */

	// private List doesNewMethodExistWithOverloading(
	protected List doesNewMethodExistWithOverloading(
		final Iterator list,
		final String newMethodSignature) {
		final List listOfClassesOfHierarchy =
			this.getListOfClassesOfHierarchy(list, new IClassComparator() {
				public boolean check(final String anID) {
					return RefactoringMethod.this
						.doesNewMethodExistWithOverloading(
							anID,
							newMethodSignature);
				}
			});
		// return !listOfClassesOfHierarchy.isEmpty();
		return listOfClassesOfHierarchy;
	}

}
