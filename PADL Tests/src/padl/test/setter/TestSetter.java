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
package padl.test.setter;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/11/28
 */
public class TestSetter extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	public TestSetter(final String name) {
		super(name);
	}
	public void setUp() {
		if (TestSetter.IdiomLevelModel == null) {
			try {
				final char[] entityName = "A".toCharArray();
				final IFirstClassEntity anEntity =
					Factory.getInstance().createClass(entityName, entityName);

				final IMethod aGetter =
					Factory.getInstance().createMethod(
						"get".toCharArray(),
						"get".toCharArray());
				aGetter.setReturnType(entityName);

				final IParameter aParameter =
					Factory.getInstance().createParameter(
						anEntity,
						"a".toCharArray(),
						Constants.CARDINALITY_ONE);
				final IMethod aSetter =
					Factory.getInstance().createMethod(
						"set".toCharArray(),
						"set".toCharArray());
				aSetter.addConstituent(aParameter);

				anEntity.addConstituent(aGetter);
				anEntity.addConstituent(aSetter);

				final IPackage aPackage =
					Factory.getInstance().createPackage("p".toCharArray());
				aPackage.addConstituent(anEntity);

				final ICodeLevelModel aCodeLevelModel =
					Factory.getInstance().createCodeLevelModel("Model");
				aCodeLevelModel.addConstituent(aPackage);

				TestSetter.IdiomLevelModel =
					(IIdiomLevelModel) new AACRelationshipsAnalysis()
						.invoke(aCodeLevelModel);
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	public void testClass() {
		Assert.assertEquals(
			"Class A exists",
			1,
			TestSetter.IdiomLevelModel.getNumberOfTopLevelEntities());
	}
	public void testMethods() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestSetter.IdiomLevelModel
				.getTopLevelEntityFromID("A");
		Assert.assertEquals(
			"Getter and setter exist",
			2,
			firstClassEntity.getNumberOfConstituents());
	}
	public void testParameters() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) TestSetter.IdiomLevelModel
				.getTopLevelEntityFromID("A");
		final IMethod setter =
			(IMethod) firstClassEntity.getConstituentFromID("set");
		Assert.assertEquals(
			"Parameter exists",
			1,
			setter.getNumberOfConstituents());
	}
}
