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
package padl.creator.javafile.eclipse.test.basic;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IInterface;
import util.io.ProxyConsole;

public class InterfaceTest extends TestCase {
	public InterfaceTest(final String aName) {
		super(aName);
	}

	public void testInterface1() {
		final String classpath = "";

		final String sourcePath = "../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/";
		final String[] javaFiles = new String[] {
				"../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/padl/example/interfaz/" };

		final ICodeLevelModel model = Utils.createLightJavaFilesPadlModel("",
				sourcePath, classpath, javaFiles);

		final IInterface interfaz = (IInterface) model
				.getTopLevelEntityFromID("padl.example.interfaz.MyInterface");
		Assert.assertNotNull(interfaz);

		final IInterface interfaz1 = (IInterface) model
				.getTopLevelEntityFromID("padl.example.interfaz.MyInterface1");
		Assert.assertNull(interfaz1);

		final int expectedNumber = 2;
		final int actualNumber = interfaz.getNumberOfInheritedEntities();

		Assert.assertEquals(expectedNumber, actualNumber);

	}

	//not contain constructor
	public void testInterfaceNoDefaultConstructor() {
		final String classpath = "";

		final String sourcePath = "../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/";
		final String[] javaFiles = new String[] {
				"../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/padl/example/interfaz/" };

		final ICodeLevelModel model = Utils.createLightJavaFilesPadlModel("",
				sourcePath, classpath, javaFiles);

		final IInterface interfaz = (IInterface) model
				.getTopLevelEntityFromID("padl.example.interfaz.MyInterface");
		Assert.assertNotNull(interfaz);

		try {
			Assert.assertEquals(0,
					interfaz.getNumberOfConstituents(
							Class.forName("padl.kernel.impl.Constructor"))
							- interfaz.getNumberOfConstituents(
									Class.forName("padl.kernel.impl.Method")));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

	}
}
