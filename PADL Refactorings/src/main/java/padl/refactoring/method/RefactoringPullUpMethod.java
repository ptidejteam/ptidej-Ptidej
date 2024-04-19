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

import java.util.Iterator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.exception.ModelDeclarationException;

/**
 * @author Saliha Bouden
 * @since 2006/03/31
 * 
 */
public class RefactoringPullUpMethod extends RefactoringMethod {

	//	private IAbstractLevelModel abstractLevelModel;

	public RefactoringPullUpMethod(
		final IAbstractLevelModel anAbstractLevelModel) {
		//this.abstractLevelModel = anAbstractLevelModel;
		super(anAbstractLevelModel);
	}
	/**
	 * cette méthode retourne true si la méthode existe dans toutes les sous-classes
	 * 
	 */

	private void removeMethod(final Iterator list, final IMethod aMethod) {
		final Iterator iterator = list;
		while (iterator.hasNext()) {
			final IFirstClassEntity aClass =
				(IFirstClassEntity) iterator.next();
			final IMethod theMethod =
				this.getMethodToRefactor(
					aClass.getDisplayName(),
					aMethod.getDisplayName());
			if (theMethod != null) {
				aClass.removeConstituentFromID(theMethod.getID());
			}
		}
	}
	private IMethod getMethodToRefactorFromSubClass(
		final Iterator list,
		final String aMethodName) {

		final Iterator iterator = list;
		while (iterator.hasNext()) {
			final IClass aClass = (IClass) iterator.next();
			final IMethod aMethod =
				this.getMethodToRefactor(aClass.getDisplayName(), aMethodName);
			if (aMethod != null) {
				return aMethod;
			}

		}
		return null;
	}
	private IMethod checkPreConditionsOfPullUpMethod(
		final IClass superClass,
		final String methodName) {

		IMethod aMethod =
			this.getMethodToRefactorFromSubClass(
				superClass.getIteratorOnInheritingEntities(),
				methodName);
		if (aMethod != null) {
			if (!this.doesNewMethodExist(
				superClass.getDisplayID(),
				aMethod.getDisplayName())) {
				return aMethod;
			}
			throw new ModelDeclarationException(
				"Refactoring Pull Up Method  is impossible to apply because the name of the method"
						+ aMethod.getDisplayName()
						+ "  already exist in the superclass "
						+ superClass.getDisplayName());
		}
		return null;
	}

	public void pullUpMethod(String methodName, String superClassName) {
		final IClass superClass =
			(IClass) this.abstractLevelModel
				.getTopLevelEntityFromID(superClassName);
		//		IMethod aMethod = this.getMethodToRefactorFromSubClass(superClass
		//			.listOfInheritingEntities(), methodName);
		//		if (aMethod != null && !this.doesNewMethodExist(superClass.getName(), aMethod.getID()))
		IMethod aMethod =
			this.checkPreConditionsOfPullUpMethod(superClass, methodName);
		if (aMethod != null) {
			superClass.addConstituent(aMethod);

			this.removeMethod(
				superClass.getIteratorOnInheritingEntities(),
				aMethod);
		}
	}

	// TODO: In general, the "check..." methods should not throw exceptions
	// or return an IMethod, they better should return "true" or "false"?
	private IMethod checkPreConditionsOfPullUpMethodAcceptOverloading(
		final IClass superClass,
		final String methodName) {

		IMethod aMethod =
			this.getMethodToRefactorFromSubClass(
				superClass.getIteratorOnInheritingEntities(),
				methodName);
		if (aMethod != null) {
			final String aNewMethodID =
				this.createNewMethodSignature(aMethod, methodName);
			if (!this.doesNewMethodExistWithOverloading(
				superClass.getDisplayID(),
				aNewMethodID)) {
				return aMethod;
			}
			throw new ModelDeclarationException(
				"Refactoring Pull Up Method  is impossible to apply because the signature of the method"
						+ aMethod.getDisplayName()
						+ "  already exist in the superclass "
						+ superClass.getDisplayName());
		}
		return null;
	}

	public void pullUpMethodAcceptOverloading(
		final String methodName,
		final String superClassName) {

		final IClass superClass =
			(IClass) this.abstractLevelModel
				.getTopLevelEntityFromID(superClassName);
		IMethod aMethod =
			this.checkPreConditionsOfPullUpMethodAcceptOverloading(
				superClass,
				methodName);
		if (aMethod != null) {
			superClass.addConstituent(aMethod);

			this.removeMethod(
				superClass.getIteratorOnInheritingEntities(),
				aMethod);
			// TODO: RESTE A BIEN VÉRIFIER pourquoi la méthode existe tjs apres avoir supprimer cette méthode
			// On devrait regarder cela ensemble quand les autres "TODO" auront ete regles :-)
		}
	}
}
