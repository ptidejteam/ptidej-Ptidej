/*******************************************************************************
 * Copyright (c) 2001-2015 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.creator.xmiclassdiagram.test;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.xmiclassdiagram.XMICreator;
import padl.kernel.IAssociation;
import padl.kernel.IComposition;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IUseRelationship;
import padl.motif.kernel.IDesignLevelModel;
import padl.motif.kernel.impl.MotifFactory;

public class ClassDiagram1 extends TestCase {
	private IDesignLevelModel model;

	protected void setUp() throws Exception {
		super.setUp();

		this.model =
			((MotifFactory) MotifFactory.getInstance())
				.createDesignLevelModel(new char[0]);
		this.model.create(new XMICreator(
			"../PADL Creator XMI/target/test-classes/ClassDiagram1.xmi"));
	}

	public void testAssociation() {
		final IFirstClassEntity gamePresentation =
			this.model.getTopLevelEntityFromID("GamePresentation");
		final IAssociation association =
			(IAssociation) gamePresentation
				.getConstituentFromID("UMLAssociation.201_>PADL<_1");
		Assert.assertNotNull(association);
		Assert.assertEquals("GameInterface", association
			.getTargetEntity()
			.getDisplayName());
	}
	public void testComposition() {
		final IFirstClassEntity party =
			this.model.getTopLevelEntityFromID("Party");
		final IFirstClassEntity character =
			this.model.getTopLevelEntityFromID("Character");
		final IComposition composition =
			(IComposition) party
				.getConstituentFromID("UMLAssociation.188_>PADL<_2");
		Assert.assertNotNull(composition);
		Assert.assertEquals(character, composition.getTargetEntity());
	}
	public void testInheritance() {
		final IFirstClassEntity gamePresentation =
			this.model.getTopLevelEntityFromID("GamePresentation");
		final IFirstClassEntity guiPresentation =
			this.model.getTopLevelEntityFromID("GUIPresentation");
		Assert.assertNotNull(guiPresentation
			.getInheritedEntityFromID(gamePresentation.getID()));
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(19, this.model.getNumberOfTopLevelEntities());
	}
	public void testUseRelationship() {
		final IFirstClassEntity dbManager =
			this.model.getTopLevelEntityFromID("DBManager");
		final IFirstClassEntity iObserver =
			this.model.getTopLevelEntityFromID("i_observer");
		final IUseRelationship useRelationship =
			(IUseRelationship) dbManager
				.getConstituentFromID("UMLDependency.207_>PADL<_1");
		Assert.assertNotNull(useRelationship);
		Assert.assertEquals(iObserver, useRelationship.getTargetEntity());
	}
}
