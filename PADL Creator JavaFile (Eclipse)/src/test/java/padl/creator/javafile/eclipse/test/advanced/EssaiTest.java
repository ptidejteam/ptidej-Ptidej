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
package padl.creator.javafile.eclipse.test.advanced;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;
import util.io.Files;

public class EssaiTest extends TestCase {

	public EssaiTest(final String name) {
		super(name);

	}

	public void testClassesDuplication() {
		final String sourcePath = "";
		final String[] javaFiles =
			new String[] {
					"../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/padl/example/eclipse/duplication/classes/in/A.java",
					"../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/padl/example/eclipse/duplication/classes/out/A.java", };
		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		int nbClasses = 0;
		try {
			nbClasses =
				model.getNumberOfTopLevelEntities(Class
					.forName("padl.kernel.impl.Class"));
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(1, nbClasses);
	}
}
