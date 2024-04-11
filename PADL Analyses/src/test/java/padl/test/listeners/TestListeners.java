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
package padl.test.listeners;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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
import padl.kernel.impl.AbstractGenericContainerOfConstituents;
import padl.kernel.impl.Factory;
import padl.kernel.impl.FirstClassEntity;
import padl.test.helper.Reflector;
import padl.util.ModelStatistics;
import util.io.ProxyConsole;

public class TestListeners extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;

	public TestListeners(final String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		if (TestListeners.IdiomLevelModel == null) {
			try {
				final char[] entityZName = "Z".toCharArray();
				final IFirstClassEntity anZEntity = Factory.getInstance()
						.createClass(entityZName, entityZName);

				final IMethod aMethod = Factory.getInstance()
						.createMethod("foo".toCharArray(), "foo".toCharArray());
				aMethod.setReturnType(entityZName);
				anZEntity.addConstituent(aMethod);

				final char[] entityAName = "A".toCharArray();
				final IFirstClassEntity anAEntity = Factory.getInstance()
						.createClass(entityAName, entityAName);

				final IMethod aGetter = Factory.getInstance()
						.createMethod("get".toCharArray(), "get".toCharArray());
				aGetter.setReturnType(entityAName);

				final IParameter aParameter = Factory.getInstance()
						.createParameter(anAEntity, "a".toCharArray(),
								Constants.CARDINALITY_ONE);
				final IMethod aSetter = Factory.getInstance()
						.createMethod("set".toCharArray(), "set".toCharArray());
				aSetter.addConstituent(aParameter);

				anAEntity.addConstituent(aGetter);
				anAEntity.addConstituent(aSetter);

				final IPackage aPackage = Factory.getInstance()
						.createPackage("p".toCharArray());
				aPackage.addConstituent(anZEntity);
				aPackage.addConstituent(anAEntity);

				final ICodeLevelModel aCodeLevelModel = Factory.getInstance()
						.createCodeLevelModel("Model");
				aCodeLevelModel.addConstituent(aPackage);

				TestListeners.IdiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
						.invoke(aCodeLevelModel);
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}

	public void testAddListenerModel() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {

		TestListeners.IdiomLevelModel.addModelListener(new ModelStatistics());

		final IFirstClassEntity anA = TestListeners.IdiomLevelModel
				.getTopLevelEntityFromID("A".toCharArray());
		final AbstractGenericContainerOfConstituents container = (AbstractGenericContainerOfConstituents) Reflector
				.getObjectFromReflectedField(FirstClassEntity.class,
						"container", anA);
		final Object genericObservable = Reflector.getObjectFromReflectedField(
				AbstractGenericContainerOfConstituents.class, "observable",
				container);
		final Method getModelListeners = genericObservable.getClass()
				.getDeclaredMethod("getModelListeners", new Class[0]);
		getModelListeners.setAccessible(true);
		final List listOfListeners = (List) getModelListeners
				.invoke(genericObservable, new Object[0]);

		Assert.assertEquals(2, listOfListeners.size());
	}

	public void testAddListenerEntity()
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		TestListeners.IdiomLevelModel.addModelListener(new ModelStatistics());

		final IFirstClassEntity anA = TestListeners.IdiomLevelModel
				.getTopLevelEntityFromID("A".toCharArray());
		final AbstractGenericContainerOfConstituents container = (AbstractGenericContainerOfConstituents) Reflector
				.getObjectFromReflectedField(FirstClassEntity.class,
						"container", anA);
		final Object genericObservable = Reflector.getObjectFromReflectedField(
				AbstractGenericContainerOfConstituents.class, "observable",
				container);
		final Method getModelListeners = genericObservable.getClass()
				.getDeclaredMethod("getModelListeners", new Class[0]);
		getModelListeners.setAccessible(true);
		final List listOfListeners = (List) getModelListeners
				.invoke(genericObservable, new Object[0]);

		Assert.assertEquals(3, listOfListeners.size());
	}
}
