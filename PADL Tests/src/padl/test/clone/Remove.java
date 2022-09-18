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
package padl.test.clone;

import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.impl.Factory;

public final class Remove extends TestCase {
	private static IIdiomLevelModel OriginalIdiomLevelModel;

	public Remove(String name) {
		super(name);
	}
	protected void setUp() throws UnsupportedSourceModelException {
		final char[] entityName = "A".toCharArray();
		final IFirstClassEntity entity =
			Factory.getInstance().createClass(entityName, entityName);

		final IMethod aGetter =
			Factory.getInstance().createMethod(
				"get".toCharArray(),
				"get".toCharArray());
		aGetter.setReturnType(entityName);
		// aGetter.addConstituent(aParameter1);

		final IParameter aParameter2 =
			Factory.getInstance().createParameter(
				entity,
				"a".toCharArray(),
				Constants.CARDINALITY_ONE);
		final IMethod aSetter =
			Factory.getInstance().createMethod(
				"set".toCharArray(),
				"set".toCharArray());
		aSetter.addConstituent(aParameter2);

		final IField aField =
			Factory.getInstance().createField(
				"a".toCharArray(),
				"a".toCharArray(),
				entityName,
				2);
		aField.setPrivate(true);

		entity.addConstituent(aGetter);
		entity.addConstituent(aSetter);
		entity.addConstituent(aField);

		final IPackage aPackage =
			Factory.getInstance().createPackage("p".toCharArray());
		aPackage.addConstituent(entity);

		final ICodeLevelModel aCodeLevelModel =
			Factory.getInstance().createCodeLevelModel("Model");
		aCodeLevelModel.addConstituent(aPackage);

		Remove.OriginalIdiomLevelModel =
			(IIdiomLevelModel) new AACRelationshipsAnalysis()
				.invoke(aCodeLevelModel);
	}
	public void test1() {
		final Iterator iteratorOnTopLevelEntities =
			Remove.OriginalIdiomLevelModel.getIteratorOnTopLevelEntities();
		while (iteratorOnTopLevelEntities.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iteratorOnTopLevelEntities.next();
			Remove.OriginalIdiomLevelModel
				.removeTopLevelEntityFromID(firstClassEntity.getID());
		}

		final Iterator iteratorOnConstituents =
			Remove.OriginalIdiomLevelModel
				.getConcurrentIteratorOnConstituents(IPackage.class);
		while (iteratorOnConstituents.hasNext()) {
			final IPackage packaje = (IPackage) iteratorOnConstituents.next();
			Assert.assertEquals(0, packaje.getNumberOfConstituents());
		}
	}
}
