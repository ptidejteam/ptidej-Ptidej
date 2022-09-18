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

import org.junit.Assert;


public class TestOO extends AspectJPrimitive {


	
	public TestOO(String aName) {
		super(aName);
	}
	
	public void testCreation() {
		//---------------------------------------
		Assert.assertEquals(
			"OO CLM Creation",
			true,
			(this.getCodeLevelModel() != null));
	}
	
	public void testConstituentCreation(){
		//On package exemple.figureElement.display
		Assert.assertTrue(
			"Test creation of exemple.figureElement.display.DisplayManager",
			(this.getCodeLevelModel().getTopLevelEntityFromID("exemple.figureElement.display.DisplayManager")) != null);
		Assert.assertTrue(
			"Test creation of exemple.figureElement.display.DisplayableFigure",
			(this.getCodeLevelModel().getTopLevelEntityFromID("exemple.figureElement.display.DisplayableFigure")) != null);
		
		//On package exemple.figureElement.core
		Assert.assertTrue(
			"Test creation of exemple.figureElement.core.Point",
			(this.getCodeLevelModel().getTopLevelEntityFromID("exemple.figureElement.core.Point")) != null);
		Assert.assertTrue(
			"Test creation of exemple.figureElement.core.Line",
			(this.getCodeLevelModel().getTopLevelEntityFromID("exemple.figureElement.core.Line")) != null);
		
		Assert.assertTrue(
			"Test creation of exemple.figureElement.core.FigureElement",
			(this.getCodeLevelModel().getTopLevelEntityFromID("exemple.figureElement.core.FigureElement")) != null);
		
		Assert.assertTrue(
			"Test creation of exemple.figureElement.core.Main",
			(this.getCodeLevelModel().getTopLevelEntityFromID("exemple.figureElement.core.Main")) != null);
		
		//Assert test
		Assert.assertTrue(
			"Test non creation of exemple.figureElement.display.NonExistent",
			(this.getCodeLevelModel().getTopLevelEntityFromID("exemple.figureElement.display.NonExistent")) == null);
		
	}
	
	
}
