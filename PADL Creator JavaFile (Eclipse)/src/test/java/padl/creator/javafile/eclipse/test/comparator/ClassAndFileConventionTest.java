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
package padl.creator.javafile.eclipse.test.comparator;

import java.io.File;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IParameter;

public class ClassAndFileConventionTest extends TestCase {
	public ClassAndFileConventionTest(final String aName) {
		super(aName);
	}

	public void testUseOfFileClassNameConvention() {

		final String javaFilesFolderPath = "../PADL Creator JavaFile (Eclipse)/target/test-classes//";
		final String classPathEntry = "";
		String rootPath = "../PADL Creator JavaFile (Eclipse)/target/test-classes//ptidej/example/FileAndClassNames/";

		String someFilesPaths[] = new File(
				"../PADL Creator JavaFile (Eclipse)/target/test-classes//ptidej/example/FileAndClassNames/")
				.list();
		for (int i = 0; i < someFilesPaths.length; i++) {
			someFilesPaths[i] = new StringBuffer().append(rootPath)
					.append(someFilesPaths[i]).toString();
		}

		// Model from source code

		final ICodeLevelModel padlModelFromJavaFiles = Utils
				.createLightJavaFilesPadlModel("", javaFilesFolderPath,
						classPathEntry, someFilesPaths);

		IInterface interface1 = (IInterface) padlModelFromJavaFiles
				.getTopLevelEntityFromID(
						"ptidej.example.FileAndClassNames.Element");
		IClass class1 = (IClass) padlModelFromJavaFiles.getTopLevelEntityFromID(
				"ptidej.example.FileAndClassNames.Document");
		IMethod method = (IMethod) class1
				.getConstituentFromName("removeComponent");
		IParameter param = (IParameter) method.getConstituentFromName("e");
		//		ProxyConsole
		//			.getInstance()
		//			.debugOutput()
		//			.println(param.getDisplayTypeName());

		Assert.assertNotNull("entity is null", interface1);
		Assert.assertEquals("ptidej.example.FileAndClassNames.Element",
				param.getDisplayTypeName());

	}

	public void testUseOfFileClassNameConvention2() {

		final String javaFilesFolderPath = "../PADL Creator JavaFile (Eclipse)/target/test-classes//";
		final String classPathEntry = "";
		String rootPath = new StringBuffer()

				.append("../PADL Creator JavaFile (Eclipse)/target/test-classes//ptidej/example/FileAndClassNames2/")
				.toString();

		String someFilesPaths[] = new File(
				"../PADL Creator JavaFile (Eclipse)/target/test-classes//ptidej/example/FileAndClassNames2/")
				.list();
		for (int i = 0; i < someFilesPaths.length; i++) {
			someFilesPaths[i] = new StringBuffer().append(rootPath)
					.append(someFilesPaths[i]).toString();
		}

		// Model from source code

		final ICodeLevelModel padlModelFromJavaFiles = Utils
				.createLightJavaFilesPadlModel("", javaFilesFolderPath,
						classPathEntry, someFilesPaths);

		IInterface interface1 = (IInterface) padlModelFromJavaFiles
				.getTopLevelEntityFromID(
						"ptidej.example.FileAndClassNames2.Element");
		IClass class1 = (IClass) padlModelFromJavaFiles.getTopLevelEntityFromID(
				"ptidej.example.FileAndClassNames2.Document");
		IMethod method = (IMethod) class1
				.getConstituentFromName("removeComponent");
		IParameter param = (IParameter) method.getConstituentFromName("e");
		//		ProxyConsole.getInstance().debugOutput()
		//				.println(param.getDisplayTypeName());

		Assert.assertNotNull("entity is not null", interface1);
		//the type is not equal to what is expected
		Assert.assertTrue(!("ptidej.example.FileAndClassNames.Element"
				.equals(param.getDisplayTypeName())));

	}

}
