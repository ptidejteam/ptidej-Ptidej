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
package epi.test.helper;

import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFactory;
import padl.kernel.impl.Factory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/04/08
 */
public class CodeLevelModel {
	public static ICodeLevelModel getCodeLevelModel() {
		final IFactory factory = Factory.getInstance();
		final ICodeLevelModel codeLevelModel =
			factory.createCodeLevelModel("Test");
		final IClass classA =
			factory.createClass("A".toCharArray(), "A".toCharArray());
		final IClass classB =
			factory.createClass("B".toCharArray(), "B".toCharArray());
		final IClass classC =
			factory.createClass("C".toCharArray(), "C".toCharArray());
		final IClass classD =
			factory.createClass("D".toCharArray(), "D".toCharArray());
		final IClass classE =
			factory.createClass("E".toCharArray(), "E".toCharArray());
		final IClass classF =
			factory.createClass("F".toCharArray(), "F".toCharArray());
		final IClass classG =
			factory.createClass("G".toCharArray(), "G".toCharArray());
		final IClass classH =
			factory.createClass("H".toCharArray(), "H".toCharArray());

		classH.addConstituent(factory.createAssociationRelationship(
			"as1".toCharArray(),
			classG,
			1));
		classH.addConstituent(factory.createCreationRelationship(
			"cr1".toCharArray(),
			classD,
			1));
		classH.addConstituent(factory.createCreationRelationship(
			"cr2".toCharArray(),
			classE,
			1));
		classH.addConstituent(factory.createCreationRelationship(
			"cr3".toCharArray(),
			classF,
			1));
		classG.addConstituent(factory.createAggregationRelationship(
			"ag1".toCharArray(),
			classA,
			1));
		classB.addInheritedEntity(classA);
		classC.addInheritedEntity(classB);
		classD.addInheritedEntity(classC);
		classD.addConstituent(factory.createAssociationRelationship(
			"as2".toCharArray(),
			classC,
			1));
		classE.addInheritedEntity(classC);
		classF.addInheritedEntity(classC);
		classF.addConstituent(
		// TODO: containerComposition or Composition?!		
		//factory.createCompositionRelationship("co1", classB, 1));
			factory.createContainerCompositionRelationship(
				"co1".toCharArray(),
				classB,
				1));

		codeLevelModel.addConstituent(classA);
		codeLevelModel.addConstituent(classB);
		codeLevelModel.addConstituent(classC);
		codeLevelModel.addConstituent(classD);
		codeLevelModel.addConstituent(classE);
		codeLevelModel.addConstituent(classF);
		codeLevelModel.addConstituent(classG);
		codeLevelModel.addConstituent(classH);

		return codeLevelModel;
	}
}
