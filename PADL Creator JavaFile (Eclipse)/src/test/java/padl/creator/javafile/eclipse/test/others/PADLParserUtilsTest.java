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
package padl.creator.javafile.eclipse.test.others;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

public class PADLParserUtilsTest extends TestCase {

	public PADLParserUtilsTest(final String name) {
		super(name);

	}

	/**
	 * to handle the null exception found by Sam with the project restlet
	 */
	// public void testGetTypeName(){
	// ProxyConsole.getInstance().debugOutput().println("testGetTypeName");
	// final String sourcePath =
	// "./rsc/sam test data/restlet/";
	// /*final String[] javaFiles =
	// new String[] {
	// "../PADL Creator JavaFile (Eclipse) Tests/src/padl/example/annotator/"
	// };*/
	// final String classPathEntry = "";
	// IIdiomLevelModel javaIdiomModel =
	// Utils.createCompleteJavaFilesPadlIdiomModel(
	// "",
	// sourcePath,
	// classPathEntry
	// );
	// ProxyConsole.getInstance().debugOutput().println("getNumberOfTopLevelEntities"+javaIdiomModel.getNumberOfTopLevelEntities());
	// Assert.assertNotNull(javaIdiomModel);
	// }

	/**
	 * to handle the null exception found by Sam with the project restlet
	 */
	public void testGetTypeName() {
		ProxyConsole.getInstance().debugOutput().println("testGetTypeName");
		final String sourcePath = "../PADL Creator JavaFile (Eclipse) Tests/rsc/sam test data/restlet/";
		final ICodeLevelModel model =
			Factory.getInstance().createCodeLevelModel("");

		try {
			model.create(new CompleteJavaFileCreator(sourcePath, ""));

			ProxyConsole
				.getInstance()
				.debugOutput()
				.println(
					"getNumberOfTopLevelEntities"
							+ model.getNumberOfTopLevelEntities());
			Assert.assertNotNull(model);

		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

	}

}
