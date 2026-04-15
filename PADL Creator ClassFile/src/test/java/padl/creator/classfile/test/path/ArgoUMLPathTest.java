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
package padl.creator.classfile.test.path;

import org.junit.Assert;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.exception.CreationException;
import padl.path.Finder;
import padl.path.FormatException;
import padl.path.IConstants;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/08
 */
public class ArgoUMLPathTest extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static ICodeLevelModel Model = null;

	public ArgoUMLPathTest(final String aName) {
		super(aName);
	}
	protected void setUp() throws CreationException {
		if (ArgoUMLPathTest.Model == null) {
			ArgoUMLPathTest.Model =
				ClassFilePrimitive.getFactory().createCodeLevelModel("ArgoUML");
			ArgoUMLPathTest.Model.create(
				new CompleteClassFileCreator(
					new String[] {
							"../PADL Creator ClassFile/target/test-classes/ArgoUML-0.15.6.jar" }));

			ArgoUMLPathTest.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(ArgoUMLPathTest.Model);
		}
	}
	public void testPath1() {
		for (int i = 0; i < ArgoUMLPathTest.FirstClassEntities.length; i++) {
			Assert.assertEquals(
				ArgoUMLPathTest.FirstClassEntities[i].getDisplayID(),
				ArgoUMLPathTest.FirstClassEntities[i]
					.getDisplayPath()
					.substring(
						ArgoUMLPathTest.FirstClassEntities[i]
							.getDisplayPath()
							.lastIndexOf(IConstants.ENTITY_SYMBOL) + 1));
		}
	}
	public void testPath2() {
		try {
			Finder.findContainer("/ArgoUML", ArgoUMLPathTest.Model);
			Assert.fail();
		}
		catch (final FormatException e) {
		}
	}
	public void testPath3() {
		try {
			Assert.assertNull(
				Finder.find("/ArgoUML|Dummy", ArgoUMLPathTest.Model));
			Assert.fail("Dummy should not exist!");
		}
		catch (final FormatException e) {
		}
	}
	public void testPath4() {
		try {
			Assert.assertNotNull(
				Finder.find(
					"/ArgoUML|org|argouml|pattern|cognitive|critics|org.argouml.pattern.cognitive.critics.CrConsiderSingleton",
					ArgoUMLPathTest.Model));
		}
		catch (final FormatException e) {
			Assert.fail(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	public void testPath5() {
		try {
			Assert.assertNotNull(
				Finder.find(
					"/ArgoUML|org|argouml|ui|org.argouml.ui.ActionExportXMI",
					ArgoUMLPathTest.Model));
		}
		catch (final FormatException e) {
			Assert.fail(e.getMessage());
		}
	}
	public void testPath6() {
		try {
			Assert.assertNotNull(
				Finder.find(
					"/ArgoUML|org|argouml|ui|org.argouml.ui.ActionExportXMI|actionPerformed(java.awt.event.ActionEvent)",
					ArgoUMLPathTest.Model));
		}
		catch (final FormatException e) {
			Assert.fail(e.getMessage());
		}
	}
	public void testPath7() {
		try {
			Assert.assertNotNull(
				Finder.find(
					"/ArgoUML|org|argouml|ui|org.argouml.ui.DnDNavigatorTree$ArgoDropTargetListener",
					ArgoUMLPathTest.Model));
		}
		catch (final FormatException e) {
			Assert.fail(e.getMessage());
		}
	}
	public void testPath8() {
		try {
			Assert.assertNotNull(
				Finder.find(
					"/ArgoUML|org|argouml|ui|org.argouml.ui.DnDNavigatorTree$ArgoDropTargetListener|isValidDropTarget(javax.swing.tree.TreePath, javax.swing.tree.TreePath)",
					ArgoUMLPathTest.Model));
		}
		catch (final FormatException e) {
			Assert.fail(e.getMessage());
		}
	}
}
