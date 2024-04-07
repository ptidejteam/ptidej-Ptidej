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
package padl.creator.aspectj.test;

import java.io.File;
import java.util.HashMap;

import org.junit.Assert;

import padl.creator.aspectjlst.AspectCreator;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.kernel.exception.CreationException;

public class TestAO extends AspectJPrimitive {
	private HashMap importCreation = null;

	private final String lstAO = "../PADL Creator AspectJ/target/test-classes/FigureElement/srcAO/files.lst";

	public TestAO(String aName) {
		super(aName);
	}

	public String getLstPath() {
		return this.lstAO;
	}

	public void setUp() throws CreationException {
		super.setUp();

		// Add AO model

		final AspectCreator ac = new AspectCreator(new String[] { this.lstAO });
		this.getCodeLevelModel().create(ac);
		this.importCreation = ac.getImportMap();
	}

	public void testConstituentCreation() {
		//On package exemple.figureElement.aspect
		Assert.assertTrue("Test creation of exemple.figureElement.aspect.DisplayAspect",
				((IPackage) this.getCodeLevelModel().getConstituentFromID(AspectCreator.ASPECT_PACKAGE_ID))
						.getConstituentFromID("exemple.figureElement.aspect.DisplayAspect") != null);

		//False test
		Assert.assertTrue("Test non creation of exemple.figureElement.aspect.NonExistentAspect",
				((IPackage) this.getCodeLevelModel().getConstituentFromID(AspectCreator.ASPECT_PACKAGE_ID))
						.getConstituentFromID("exemple.figureElement.aspect.NonExistentAspect") == null);

	}

	public void testImportCreation() {
		//		import exemple.figureElement.core.Point;
		//		import exemple.figureElement.core.Line;
		//		import exemple.figureElement.core.FigureElement;
		//		import exemple.figureElement.display.DisplayManager;
		//		import exemple.figureElement.display.DisplayableFigure;
		Assert.assertTrue("Test creation of import exemple.figureElement.core.Point",
				(this.importCreation.get("Point") != null));

		Assert.assertTrue("Test mapping of import exemple.figureElement.core.Point",
				(((IFirstClassEntity) (this.importCreation.get("Point"))).getDisplayID()
						.equals("exemple.figureElement.core.Point")));

		Assert.assertTrue("Test creation of import exemple.figureElement.core.Line",
				(this.importCreation.get("Line") != null));

		Assert.assertTrue("Test mapping of import exemple.figureElement.core.Line",
				(((IFirstClassEntity) (this.importCreation.get("Line"))).getDisplayID()
						.equals("exemple.figureElement.core.Line")));

		Assert.assertTrue("Test creation of import exemple.figureElement.core.FigureElement",
				(this.importCreation.get("FigureElement") != null));

		Assert.assertTrue("Test mapping of import exemple.figureElement.core.FigureElement",
				(((IFirstClassEntity) (this.importCreation.get("FigureElement"))).getDisplayID()
						.equals("exemple.figureElement.core.FigureElement")));

		Assert.assertTrue("Test creation of import exemple.figureElement.display.DisplayManager",
				(this.importCreation.get("DisplayManager") != null));

		Assert.assertTrue("Test mapping of import exemple.figureElement.display.DisplayManager",
				(((IFirstClassEntity) (this.importCreation.get("DisplayManager"))).getDisplayID()
						.equals("exemple.figureElement.display.DisplayManager")));

		Assert.assertTrue("Test creation of import exemple.figureElement.display.DisplayableFigure",
				(this.importCreation.get("DisplayableFigure") != null));

		Assert.assertTrue("Test mapping of import exemple.figureElement.display.DisplayableFigure",
				(((IFirstClassEntity) (this.importCreation.get("DisplayableFigure"))).getDisplayID()
						.equals("exemple.figureElement.display.DisplayableFigure")));

		Assert.assertTrue("Test creation of import java.awt.Color", (this.importCreation.get("Color") != null));

		Assert.assertTrue("Test mapping of import java.awt.Color",
				(((IFirstClassEntity) (this.importCreation.get("Color"))).getDisplayID().equals("java.awt.Color")));

	}

	public void testLstExist() {
		File lst = new File(this.getLstPath());
		Assert.assertEquals("Existence of the lst file", true, lst.exists());
	}
}
