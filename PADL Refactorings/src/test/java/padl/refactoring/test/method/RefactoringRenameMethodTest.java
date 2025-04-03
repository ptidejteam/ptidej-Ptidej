/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga> l Gu> h> neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga> l Gu> h> neuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.refactoring.test.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.impl.Factory;
import padl.refactoring.exception.ModelDeclarationException;
import padl.refactoring.method.IClassComparator;
import padl.refactoring.method.RefactoringRenameMethod;

/**
 * @author Saliha Bouden
 * @since 2006/03/31
 * 
 * To test, use the path="rsc/applications/QuickUML2001.jar"";
 */
public final class RefactoringRenameMethodTest extends TestCase {

	private static IIdiomLevelModel IdiomLevelModel;

	private static final String path =
		"../PADL Refactorings/target/test-classes/QuickUML2001.jar";

	public RefactoringRenameMethodTest(String name) {
		super(name);
	}

	// construire le mod> le
	protected void setUp() throws Exception {
		// if (RefactoringRenameMethodTest.IdiomLevelModel == null) {
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(path);
		codeLevelModel.create(new CompleteClassFileCreator(
			new String[] { path },
			true));
		RefactoringRenameMethodTest.IdiomLevelModel =
			(IIdiomLevelModel) new AACRelationshipsAnalysis()
				.invoke(codeLevelModel);
	}

	// }

	/**
	 * 
	 */
	public void testRenameMethod() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"acme.GifEncoder",
				"encodeStart");
		if (aMethod != null) {
			System.out.println("Before Refactoring Rename Method");
			IClass aClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"acme.GifEncoder",
					"encodeStart");
			System.out.println("-----> " + aClass.getDisplayName());
			System.out.println("-----> " + aMethod.getDisplayName());
			try {
				refactoringMethod.renameMethod(
					"acme.GifEncoder",
					"encodeStart",
					"newName");

				System.out.println("After Refactoring Rename Method");
				final IMethod method =
					refactoringMethod.getMethodToRefactor(
						"acme.GifEncoder",
						"newName");
				if (aMethod != null) {
					System.out.println("-----> " + aClass.getDisplayName());
					System.out.println("-----> " + method.getDisplayName());
					Assert.assertEquals(
						"Name of the new method renamed",
						"newName",
						aMethod.getName());
				}
			}
			catch (ModelDeclarationException e) {

				e.printStackTrace();
			}
		}
	}

	public void testRenameMethod2() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"acme.GifEncoder",
				"char_init");
		if (aMethod != null) {
			System.out.println("Before Refactoring Rename Method");
			IClass aClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"acme.GifEncoder",
					"char_init");
			System.out.println("-----> " + aClass.getDisplayName());
			System.out.println("-----> " + aMethod.getDisplayName());
			try {
				refactoringMethod.renameMethod(
					"acme.GifEncoder",
					"char_init",
					"BumpPixel");
				final IMethod method =
					refactoringMethod.getMethodToRefactor(
						"acme.GifEncoder",
						"BumpPixel");
				if (aMethod != null) {
					System.out.println("After Refactoring Rename Method");
					System.out.println("-----> " + aClass.getDisplayName());
					System.out.println("-----> " + method.getDisplayName());
					Assert.assertEquals(
						"Name of the new method renamed",
						"BumpPixel",
						aMethod.getName());
				}
			}
			catch (ModelDeclarationException e) {
				e.printStackTrace();
			}
		}
	}

	public void testRenameMethodAcceptOverloading() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"acme.GifEncoder",
				"char_init");
		if (aMethod != null) {
			System.out
				.println("Before Refactoring Rename Method Accept Overloading");
			IClass aClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"acme.GifEncoder",
					"char_init");
			System.out.println("-----> " + aClass.getDisplayName());
			System.out.println("-----> " + aMethod.getDisplayName());
			try {
				refactoringMethod.renameMethodAcceptOverloading(
					"acme.GifEncoder",
					"char_init",
					"BumpPixel");
				System.out
					.println("After Refactoring Rename Method Accept Overloading");
				System.out.println("-----> " + aClass.getDisplayName());
				System.out.println("-----> " + aMethod.getDisplayName());
				Assert.assertEquals(
					"Name of the new method renamed",
					"BumpPixel",
					aMethod.getName());
			}
			catch (ModelDeclarationException e) {

				e.printStackTrace();
			}
		}
	}

	public void testRenameMethodWithPropagationToHierarchy() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"acme.GifEncoder",
				"encodeStart");
		if (aMethod != null) {
			System.out
				.println("Before Refactoring Rename Method With Propagation To Hierarchy");
			IClass aClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"acme.GifEncoder",
					"encodeStart");
			final List list = new ArrayList();

			list.add(aClass);
			final List listOfClassesOfHierarchy =
				refactoringMethod.getListOfClassesOfHierarchy(
					list.iterator(),
					new IClassComparator() {
						public boolean check(final String anID) {
							// return refactoringMethod.doesNewMethodExist(
							// anID,
							// null);
							// return true;
							return false;
						}
					});
			final Iterator iterator = listOfClassesOfHierarchy.iterator();
			while (iterator.hasNext()) {
				final IFirstClassEntity theEntity =
					(IFirstClassEntity) iterator.next();
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						theEntity.getDisplayName(),
						"encodeStart");
				if (theMethod != null) {
					System.out.println("-----> " + theEntity.getDisplayName());
					System.out.println("-----> " + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the method ",
						"encodeStart",
						theMethod.getName());
				}
			}
			try {
				refactoringMethod.renameMethodWithPropagationToHierarchy(
					"acme.GifEncoder",
					"encodeStart",
					"newName");
				System.out
					.println("After Refactoring Rename Method With Propagation To Hierarchy");
				final Iterator iter = listOfClassesOfHierarchy.iterator();
				while (iter.hasNext()) {
					final IFirstClassEntity theEntity =
						(IFirstClassEntity) iter.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"newName");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the new method renamed",
							"newName",
							theMethod.getName());
					}
				}
			}
			catch (ModelDeclarationException e) {

				e.printStackTrace();
			}
		}
	}

	public void testRenameMethodWithPropagationToHierarchy2() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"acme.GifEncoder",
				"encodeStart");
		if (aMethod != null) {
			System.out
				.println("Before Refactoring Rename Method With Propagation To Hierarchy");
			IClass aClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"acme.GifEncoder",
					"encodeStart");
			final List list = new ArrayList();

			list.add(aClass);
			final List listOfClassesOfHierarchy =
				refactoringMethod.getListOfClassesOfHierarchy(
					list.iterator(),
					new IClassComparator() {
						public boolean check(final String anID) {
							// return refactoringMethod.doesNewMethodExist(
							// anID,
							// null);
							// return true;
							return false;
						}
					});
			final Iterator iterator = listOfClassesOfHierarchy.iterator();
			while (iterator.hasNext()) {
				final IFirstClassEntity theEntity =
					(IFirstClassEntity) iterator.next();
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						theEntity.getDisplayName(),
						"encodeStart");
				if (theMethod != null) {
					System.out.println("-----> " + theEntity.getDisplayName());
					System.out.println("-----> " + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the method ",
						"encodeStart",
						theMethod.getName());
				}

			}
			try {
				refactoringMethod.renameMethodWithPropagationToHierarchy(
					"acme.GifEncoder",
					"encodeStart",
					"BumpPixel");
				System.out
					.println("After Refactoring Rename Method With Propagation To Hierarchy");
				final Iterator iter = listOfClassesOfHierarchy.iterator();
				while (iter.hasNext()) {
					final IFirstClassEntity theEntity =
						(IFirstClassEntity) iter.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"BumpPixel");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the new method renamed",
							"BumpPixel",
							theMethod.getName());
					}
				}
			}
			catch (ModelDeclarationException e) {

				e.printStackTrace();
			}
		}
	}

	public void testrenameMethodWithPropagationToHierarchyAcceptOverloading() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"acme.GifEncoder",
				"encodeStart");
		if (aMethod != null) {
			System.out
				.println("Before Refactoring Rename Method With Propagation To Hierarchy Accept Overloading");
			final IMethod theMeth =
				refactoringMethod.getMethodToRefactor(
					"acme.GifEncoder",
					"char_init");
			if (theMeth != null) {
				IClass aClass =
					refactoringMethod.getClassContainsMethodToRefactor(
						"acme.GifEncoder",
						"encodeStart");
				final List list = new ArrayList();
				list.add(aClass);
				//				
				final List listOfClassesOfHierarchy =
					refactoringMethod.getListOfClassesOfHierarchy(
						list.iterator(),
						new IClassComparator() {
							public boolean check(final String anID) {
								// return
								// refactoringMethod.doesNewMethodExist(
								// anID,
								// null);
								return false;
							}
						});
				final Iterator iterator = listOfClassesOfHierarchy.iterator();
				while (iterator.hasNext()) {
					final IFirstClassEntity theEntity =
						(IFirstClassEntity) iterator.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"encodeStart");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the  method ",
							"encodeStart",
							theMethod.getName());
					}
				}
				try {
					refactoringMethod
						.renameMethodWithPropagation(
							"acme.GifEncoder",
							"encodeStart",
							"char_init");
					System.out
						.println("After Refactoring Rename Method WithPropagation To Hierarchy Accept Overloading");
					final Iterator iter = listOfClassesOfHierarchy.iterator();
					while (iter.hasNext()) {
						final IFirstClassEntity theEntity =
							(IFirstClassEntity) iter.next();
						final IMethod theMethod =
							refactoringMethod.getMethodToRefactor(
								theEntity.getDisplayName(),
								"char_init");
						if (theMethod != null) {
							System.out.println("-----> "
									+ theEntity.getDisplayName());
							System.out.println("-----> "
									+ theMethod.getDisplayName());
							Assert.assertEquals(
								"Name of the new method renamed",
								"char_init",
								theMethod.getName());
						}
					}
				}
				catch (ModelDeclarationException e) {

					e.printStackTrace();
				}
			}
		}
	}

	public void testrenameMethodWithPropagationToInterface() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"diagram.AbstractDiagramModel",
				"addDiagramDataListener");
		if (aMethod != null) {
			System.out
				.println("Before Refactoring Rename Method With Propagation To Interface");
			IClass aClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"diagram.AbstractDiagramModel",
					"addDiagramDataListener");
			final List list = new ArrayList();
			list.add(aClass);
			final List listOfClassesOfHierarchy =
				refactoringMethod.getListOfClassesOfHierarchy(
					list.iterator(),
					new IClassComparator() {
						public boolean check(final String anID) {
							// return true;
							// return refactoringMethod.doesNewMethodExist(
							// anID,
							// null);
							return false;
						}
					});
			final Iterator iterator = listOfClassesOfHierarchy.iterator();
			while (iterator.hasNext()) {
				final IFirstClassEntity theEntity =
					(IFirstClassEntity) iterator.next();
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						theEntity.getDisplayName(),
						"addDiagramDataListener");
				if (theMethod != null) {
					System.out.println("-----> " + theEntity.getDisplayName());
					System.out.println("-----> " + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new method renamed",
						"addDiagramDataListener",
						theMethod.getName());
				}

			}
			final List listOfInterfaces =
				refactoringMethod.getlistOfInterfaces(
					aClass.getIteratorOnImplementedInterfaces(),
					new IClassComparator() {
						public boolean check(final String anID) {
							// return true;
							// return
							// refactoringMethod.doesNewMethodExist(
							// anID,
							// null);
							return false;

						}

					});
			final Iterator itera = listOfInterfaces.iterator();
			while (itera.hasNext()) {
				final IFirstClassEntity theEntity =
					(IFirstClassEntity) itera.next();
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						theEntity.getDisplayName(),
						"addDiagramDataListener");
				if (theMethod != null) {
					System.out.println("-----> " + theEntity.getDisplayName());
					System.out.println("-----> " + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new method renamed",
						"addDiagramDataListener",
						theMethod.getName());
				}
			}

			try {
				refactoringMethod.renameMethodWithPropagationToInterface(
					"diagram.AbstractDiagramModel",
					"addDiagramDataListener",
					"newName");
				System.out
					.println("After Refactoring Rename Method With Propagation To Interface");
				final Iterator iter = listOfClassesOfHierarchy.iterator();
				while (iter.hasNext()) {
					final IFirstClassEntity theEntity =
						(IFirstClassEntity) iter.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"newName");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the new method renamed",
							"newName",
							theMethod.getName());
					}
				}
				final Iterator iter1 = listOfInterfaces.iterator();
				while (iter1.hasNext()) {
					final IFirstClassEntity theEntity =
						(IFirstClassEntity) iter1.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"newName");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the new method renamed",
							"newName",
							theMethod.getName());
					}
				}
			}
			catch (ModelDeclarationException e) {

				e.printStackTrace();
			}
		}
	}

	public void testrenameMethodWithPropagationToInterface2() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"diagram.AbstractDiagramModel",
				"addDiagramDataListener");
		if (aMethod != null) {
			System.out
				.println("Before Refactoring Rename Method With Propagation To Interface");
			IClass aClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"diagram.AbstractDiagramModel",
					"addDiagramDataListener");
			final List list = new ArrayList();
			list.add(aClass);

			final List listOfClassesOfHierarchy =
				refactoringMethod.getListOfClassesOfHierarchy(
					list.iterator(),
					new IClassComparator() {
						public boolean check(final String anID) {
							// return refactoringMethod.doesNewMethodExist(
							// anID,
							// null);
							return false;
						}
					});
			final Iterator iterator = listOfClassesOfHierarchy.iterator();
			while (iterator.hasNext()) {
				final IFirstClassEntity theEntity =
					(IFirstClassEntity) iterator.next();
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						theEntity.getDisplayName(),
						"addDiagramDataListener");
				if (theMethod != null) {
					System.out.println("-----> " + theEntity.getDisplayName());
					System.out.println("-----> " + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new method renamed",
						"addDiagramDataListener",
						theMethod.getName());
				}

			}

			final List listOfInterfaces =
				refactoringMethod.getlistOfInterfaces(
					aClass.getIteratorOnImplementedInterfaces(),
					new IClassComparator() {
						public boolean check(final String anID) {
							// return true;
							// return refactoringMethod
							// .doesNewMethodExist(anID, null);
							return false;
						}

					});
			final Iterator itera = listOfInterfaces.iterator();
			while (itera.hasNext()) {
				final IFirstClassEntity theEntity =
					(IFirstClassEntity) itera.next();
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						theEntity.getDisplayName(),
						"addDiagramDataListener");
				if (theMethod != null) {
					System.out.println("-----> " + theEntity.getDisplayName());
					System.out.println("-----> " + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new method renamed",
						"addDiagramDataListener",
						theMethod.getName());
				}
			}

			try {
				refactoringMethod.renameMethodWithPropagationToInterface(
					"diagram.AbstractDiagramModel",
					"addDiagramDataListener",
					"add");
				System.out
					.println("After Refactoring Rename Method With Propagation To Interface");

				final Iterator iter = listOfClassesOfHierarchy.iterator();
				while (iter.hasNext()) {
					final IFirstClassEntity theEntity =
						(IFirstClassEntity) iter.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"add");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the new method renamed",
							"add",
							theMethod.getName());
					}
				}
				final Iterator iter1 = listOfInterfaces.iterator();
				while (iter1.hasNext()) {
					final IFirstClassEntity theEntity =
						(IFirstClassEntity) iter1.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"add");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the new method renamed",
							"add",
							theMethod.getName());
					}
				}
			}
			catch (ModelDeclarationException e) {

				e.printStackTrace();
			}
		}
	}

	public void testrenameMethodWithPropagationToInterfaceAcceptOverloading() {

		final RefactoringRenameMethod refactoringMethod =
			new RefactoringRenameMethod(
				RefactoringRenameMethodTest.IdiomLevelModel);
		final IMethod aMethod =
			refactoringMethod.getMethodToRefactor(
				"diagram.AbstractDiagramModel",
				"addDiagramDataListener");
		if (aMethod != null) {
			System.out
				.println("Before Refactoring Rename Method With Propagation To Interface Accept Overloading");
			IClass aClass =
				refactoringMethod.getClassContainsMethodToRefactor(
					"diagram.AbstractDiagramModel",
					"addDiagramDataListener");
			final List list = new ArrayList();
			list.add(aClass);
			final List listOfClassesOfHierarchy =
				refactoringMethod.getListOfClassesOfHierarchy(
					list.iterator(),
					new IClassComparator() {
						public boolean check(final String anID) {
							// return true;
							// return refactoringMethod.doesNewMethodExist(anID,
							// null);
							return false;

						}

					});
			final Iterator iterator = listOfClassesOfHierarchy.iterator();
			while (iterator.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) iterator.next();
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						firstClassEntity.getDisplayName(),
						"addDiagramDataListener");
				if (theMethod != null) {
					System.out.println("-----> "
							+ firstClassEntity.getDisplayName());
					System.out.println("-----> " + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new method renamed",
						"addDiagramDataListener",
						theMethod.getName());
				}

			}

			final List listOfInterfaces =
				refactoringMethod.getlistOfInterfaces(
					aClass.getIteratorOnImplementedInterfaces(),
					new IClassComparator() {
						public boolean check(final String anID) {
							// return true;
							// return refactoringMethod
							// .doesNewMethodExist(anID, null);
							return false;

						}

					});
			final Iterator itera = listOfInterfaces.iterator();
			while (itera.hasNext()) {
				final IFirstClassEntity theEntity =
					(IFirstClassEntity) itera.next();
				final IMethod theMethod =
					refactoringMethod.getMethodToRefactor(
						theEntity.getDisplayName(),
						"addDiagramDataListener");
				if (theMethod != null) {
					System.out.println("-----> " + theEntity.getDisplayName());
					System.out.println("-----> " + theMethod.getDisplayName());
					Assert.assertEquals(
						"Name of the new method renamed",
						"addDiagramDataListener",
						theMethod.getName());
				}
			}
			try {
				refactoringMethod
					.renameMethodWithPropagationToInterfaceAcceptOverloading(
						"diagram.AbstractDiagramModel",
						"addDiagramDataListener",
						"add");
				System.out
					.println("After Refactoring Rename Method With Propagation To Interface Accept Overloading");
				final Iterator iter = listOfClassesOfHierarchy.iterator();
				while (iter.hasNext()) {
					final IFirstClassEntity theEntity =
						(IFirstClassEntity) iter.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"add");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the new method renamed",
							"add",
							theMethod.getName());
					}
				}
				final Iterator iter1 = listOfInterfaces.iterator();
				while (iter1.hasNext()) {
					final IInterface theEntity = (IInterface) iter1.next();
					final IMethod theMethod =
						refactoringMethod.getMethodToRefactor(
							theEntity.getDisplayName(),
							"add");
					if (theMethod != null) {
						System.out.println("-----> "
								+ theEntity.getDisplayName());
						System.out.println("-----> "
								+ theMethod.getDisplayName());
						Assert.assertEquals(
							"Name of the new method renamed",
							"add",
							theMethod.getName());
					}
				}
			}
			catch (ModelDeclarationException e) {
				e.printStackTrace();
			}
		}
	}

	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new RefactoringRenameMethodTest("testRenameMethod"));
		suite.addTest(new RefactoringRenameMethodTest(
			"testRenameMethodAcceptOverloading"));
		suite.addTest(new RefactoringRenameMethodTest("testRenameMethod2"));
		suite.addTest(new RefactoringRenameMethodTest(
			"testRenameMethodWithPropagationToHierarchy"));
		suite.addTest(new RefactoringRenameMethodTest(
			"testrenameMethodWithPropagationToHierarchyAcceptOverloading"));
		suite.addTest(new RefactoringRenameMethodTest(
			"testRenameMethodWithPropagationToHierarchy2"));
		suite.addTest(new RefactoringRenameMethodTest(
			"testrenameMethodWithPropagationToInterface"));
		suite.addTest(new RefactoringRenameMethodTest(
			"testrenameMethodWithPropagationToInterfaceAcceptOverloading"));
		suite.addTest(new RefactoringRenameMethodTest(
			"testrenameMethodWithPropagationToInterface2"));

		return suite;
	}
}
