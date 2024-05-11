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
import padl.refactoring.exception.ModelDeclarationException;

/**
 * @author Saliha Bouden
 * @since 2006/03/31
 * 
 */
public class RefactoringRenameMethod extends RefactoringMethod {

	public RefactoringRenameMethod(IAbstractLevelModel anAbstractLevelModel) {
		super(anAbstractLevelModel);
	}

	public void renameMethod(final IMethod method, final String newName) {
		method.setDisplayName(newName);
	}

	/**
	 * Cette methode permet de verifier les pres conditions de renommer une
	 * methode dans sa classe de definition avec la surcharge de methodes
	 * 
	 * @throws ModelDeclarationException
	 */
	private List checkPreConditionsOfRenameMethodAcceptOverloading(
		final IClass aClass,
		final String aNewMethod) {

		final List list = new ArrayList();
		List listOfClasses = new ArrayList();
		list.add(aClass);
		listOfClasses =
			this.doesNewMethodExistWithOverloading(list.iterator(), aNewMethod);

		return listOfClasses;
	}

	/**
	 * Cette methode permet de renommer une methode dans sa classe de definition
	 * sans la surcharge de methodes
	 */
	public void renameMethod(
		final String aClassName,
		final String aMethodName,
		final String aNewName) throws ModelDeclarationException {

		final IClass aClass =
			(IClass) this.abstractLevelModel
				.getTopLevelEntityFromID(aClassName);
		final IMethod theMethod =
			this.getMethodToRefactor(aClass.getDisplayName(), aMethodName);
		if (theMethod != null) {
			final List listOfClasses =
				this.checkPreConditionsOfRenameMethod(aClass, aNewName);
			if (!listOfClasses.isEmpty()) {
				this.renameMethod(theMethod, aNewName);
			}

			else {
				throw new ModelDeclarationException(
					"Refactoring Rename Method is impossible to apply because the method "
							+ aNewName + " already exist in  "
							+ aClass.getDisplayName()
							+ ". Please change the name of method.");
			}
		}
	}

	/**
	 * Cette methode permet de verifier les pres conditions de renommer une
	 * methode dans sa classe de definition avec la surcharge de methodes
	 */
	private List checkPreConditionsOfRenameMethod(
		final IClass aClass,
		final String aNewName) {

		List listOfClasses = new ArrayList();
		final List list = new ArrayList();
		list.add(aClass);
		listOfClasses = this.doesNewMethodExist(list.iterator(), aNewName);

		return listOfClasses;
	}

	/**
	 * Cette methode permet de renommer une methode dans sa classe de definition
	 * en acceptant la surcharge de methodes
	 */
	public void renameMethodAcceptOverloading(
		final String aClassName,
		final String aMethodName,
		final String aNewName) throws ModelDeclarationException {

		final IClass aClass =
			(IClass) this.abstractLevelModel
				.getTopLevelEntityFromID(aClassName);
		final IMethod theMethod =
			this.getMethodToRefactor(aClass.getDisplayName(), aMethodName);
		if (theMethod != null) {
			final String newMethod =
				this.createNewMethodSignature(theMethod, aNewName);
			final List listOfClasses =
				this.checkPreConditionsOfRenameMethodAcceptOverloading(
					aClass,
					newMethod);
			if (!listOfClasses.isEmpty()) {
				this.renameMethod(theMethod, aNewName);
			}
			else {
				throw new ModelDeclarationException(
					"Refactoring Rename Method Accept Overloading is impossible to apply because the method "
							+ aNewName
							+ " already exist in  "
							+ aClass.getDisplayName()
							+ ". Please change the name of method.");
			}
		}
	}

	/**
	 * Cette methode permet de renommer une methode et de la propager e la
	 * hierarchie d'heritage sans la surcharge de methodes
	 */
	private void renameMethod(
		final List list,
		final String methodName,
		final String newMethod) {

		final Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();

			final IMethod theMethod =
				this.getMethodToRefactor(
					firstClassEntity.getDisplayName(),
					methodName);
			if (theMethod != null) {
				this.renameMethod(theMethod, newMethod);

				// final List listOfInheritedEnties = entity
				// .listOfInheritedEntitie();
				// this.renameMethod(listOfInheritedEntitie, methodName,
				// newMethod);
				//
				// final List listOfInheritingEntitie = entity
				// .listOfInheritingEntitie();
				// this.renameMethod(listOfInheritingEntitie, methodName,
				// newMethod);
			}
		}
	}

	private void renameMethodFromInterface(
		final List list,
		final String methodName,
		final String newMethod) {

		final Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			final IFirstClassEntity anInterface =
				(IFirstClassEntity) iterator.next();
			final IMethod theMethod =
				this.getMethodToRefactor(
					anInterface.getDisplayName(),
					methodName);
			if (theMethod != null) {
				this.renameMethod(theMethod, newMethod);

				// final List listOfInheritingEntities = entity
				// .listOfInheritingEntities();
				// this.renameMethod(listOfInheritingEntities, methodName,
				// newMethod);
				//
				// final List listOfInheritingEntitie = entity
				// .listOfInheritingEntities();
				// this.renameMethod(listOfInheritingEntities, methodName,
				// newMethod);
			}
		}
	}

	/**
	 * Cette methode permet de renommer une methode et de la propager e la
	 * hierarchie d'heritage sans la surcharge de methodes
	 * 
	 * @throws ModelDeclarationException
	 */
	public void renameMethodWithPropagationToHierarchy(
		final String className,
		final String methodName,
		final String newName) throws ModelDeclarationException {
		final IClass aClass =
			(IClass) this.abstractLevelModel.getTopLevelEntityFromID(className);
		final IMethod theMethod =
			this.getMethodToRefactor(aClass.getDisplayName(), methodName);
		if (theMethod != null) {
			final List listOfClasses =
				this.checkPreConditionsOfRenameMethod(aClass, newName);
			if (!listOfClasses.isEmpty()) {
				this.renameMethod(listOfClasses, methodName, newName);
			}
			else {
				throw new ModelDeclarationException(
					"Refactoring Rename Method With Propagation To Hierarchy is impossible to apply because the name of the method "
							+ newName
							+ "  already exist in hierarchy of  "
							+ aClass.getDisplayName()
							+ " please change the name of method.");
			}
		}
	}

	/**
	 * Cette methode permet de renommer une methode et de la propager e la
	 * hierarchie d'heritage en acceptant la surcharge de methodes
	 * 
	 * @throws ModelDeclarationException
	 */
	// TODO: Maybe rename in "renameMethodWithPropagationToHierarchyAndOverloadedMethods"?
	public void renameMethodWithPropagationToHierarchyAcceptOverloading(
		final String className,
		final String methodName,
		final String newName) throws ModelDeclarationException {

		List listOfClasses = new ArrayList();
		final IClass aClass =
			(IClass) this.abstractLevelModel.getTopLevelEntityFromID(className);

		final IMethod theMethod =
			this.getMethodToRefactor(aClass.getDisplayName(), methodName);
		if (theMethod != null) {
			final String newMethodSignature =
				this.createNewMethodSignature(theMethod, newName);
			listOfClasses =
				this.checkPreConditionsOfRenameMethodAcceptOverloading(
					aClass,
					newMethodSignature);
			if (!listOfClasses.isEmpty()) {

				this.renameMethod(listOfClasses, methodName, newName);
			}
			else {
				throw new ModelDeclarationException(
					"Refactoring Rename Method With Propagation To Hierarchy Accept Overloading is impossible to apply because the method  "
							+ newName
							+ "  already exist with similar signature in hierarchy of "
							+ aClass.getDisplayName()
							+ ". Please change the name of method.");

			}
		}
	}

	private List checkPreConditionsOfRenameMethodToInterface(
		final IClass aClass,
		final String aNewName) {

		List listOfInterfaces = new ArrayList();
		listOfInterfaces =
			this.doesNewMethodExistInHierarchyofInterface(
				aClass.getIteratorOnImplementedInterfaces(),
				aNewName);

		return listOfInterfaces;
	}

	/**
	 * Cette methode permet de renommer une methode et de la propager aux
	 * interfaces sans surcharge de methodes
	 */
	public void renameMethodWithPropagationToInterface(
		final List list,
		final String methodName,
		final String newMethod) {

		final Iterator iterator = list.iterator();
		while (iterator.hasNext()) {

			final IInterface anInterface = (IInterface) iterator.next();
			final IMethod theMethod =
				this.getMethodToRefactor(
					anInterface.getDisplayName(),
					methodName);
			if (theMethod != null) {
				this.renameMethod(theMethod, newMethod);

				// final List listOfImplementedEntities = aClass
				// .listOfImplementedEntities();
				// renameMethodWithPropagationToInterface(
				// listOfImplementedEntities, methodName, newMethod);
				// }
				// }
			}
		}
	}

	public void renameMethodWithPropagationToInterface(
		final String className,
		final String methodName,
		final String newMethod) throws ModelDeclarationException {

		final IClass aClass =
			(IClass) this.abstractLevelModel.getTopLevelEntityFromID(className);

		final List listOfClasses =
			this.checkPreConditionsOfRenameMethod(aClass, newMethod);
		if (!listOfClasses.isEmpty()) {
			final List listOfInterfaces =
				this.checkPreConditionsOfRenameMethodToInterface(
					aClass,
					newMethod);

			if (!listOfInterfaces.isEmpty()) {

				this.renameMethod(listOfClasses, methodName, newMethod);
				this.renameMethodFromInterface(
					listOfInterfaces,
					methodName,
					newMethod);
			}
			else {
				throw new ModelDeclarationException(
					"Refactoring Rename Method With Propagation To Interface  is impossible to apply because the method "
							+ newMethod
							+ "  already exist in hierarchy of  "
							+ aClass.getDisplayName()
							+ " please change the name of method.");
			}
		}
		else {
			throw new ModelDeclarationException(
				"Refactoring Rename Method With Propagation To Interface  is impossible to apply because the method "
						+ newMethod
						+ "  already exist in hierarchy of  "
						+ aClass.getDisplayName()
						+ " please change the name of method.");
		}
	}

	private List checkPreConditionsOfRenameMethodToInterfaceAcceptOverloading(
		final IClass aClass,
		final String aNewMethodSignature) {

		List listOfInterfaces = new ArrayList();
		listOfInterfaces =
			this.doesNewMethodExistInHierarchyofInterfaceWithOverloading(
				aClass.getIteratorOnImplementedInterfaces(),
				aNewMethodSignature);

		return listOfInterfaces;
	}

	/**
	 * Cette methode permet de renommer une methode et de la propager aux
	 * interfaces en acceptant la surcharge de methodes
	 */
	public void renameMethodWithPropagationToInterfaceAcceptOverloading(

	final List list, final String methodName, final String newMethod) {

		final Iterator iterator = list.iterator();
		while (iterator.hasNext()) {

			final IInterface anInterface = (IInterface) iterator.next();
			final IMethod theMethod =
				this.getMethodToRefactor(
					anInterface.getDisplayName(),
					methodName);
			if (theMethod != null) {
				this.renameMethod(theMethod, newMethod);

				// final List listOfImplementedEntities = anInterface.
				// listOfInheritingEntitiess();
				// renameMethodWithPropagationToInterface(
				// listOfImplementedEntities, methodName, newMethod);
				// }
				// }
			}
		}
	}

	public void renameMethodWithPropagationToInterfaceAcceptOverloading(
		final String className,
		final String methodName,
		final String newName) throws ModelDeclarationException {

		final IClass aClass =
			(IClass) this.abstractLevelModel.getTopLevelEntityFromID(className);
		final IMethod theMethod =
			this.getMethodToRefactor(aClass.getDisplayName(), methodName);
		if (theMethod != null) {
			final String newMethodSignature =
				this.createNewMethodSignature(theMethod, newName);
			final List listOfClasses =
				this.checkPreConditionsOfRenameMethodAcceptOverloading(
					aClass,
					newMethodSignature);
			if (!listOfClasses.isEmpty()) {
				final List listOfInterfaces =
					this
						.checkPreConditionsOfRenameMethodToInterfaceAcceptOverloading(
							aClass,
							newMethodSignature);
				if (!listOfInterfaces.isEmpty()) {
					this.renameMethod(listOfClasses, methodName, newName);
					this.renameMethodFromInterface(
						listOfInterfaces,
						methodName,
						newName);
				}
				else {
					throw new ModelDeclarationException(
						"Refactoring Rename Method With Propagation To Interface Accept Overloading is impossible to apply because the method "
								+ newName
								+ " already exist with similar signature in hierarchy of "
								+ aClass.getDisplayName()
								+ " please change the name of method.");
				}
			}
			else {
				throw new ModelDeclarationException(
					"Refactoring Rename Method With Propagation To Interface Accept Overloading is impossible to apply because the method "
							+ newName
							+ "  already exist with similar signature in hierarchy of "
							+ aClass.getDisplayName()
							+ " please change the name of method.");
			}
		}
	}
}
