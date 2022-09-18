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

import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.RelaxedModelComparator;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;

public class FieldsAndReturnTypesTest extends TestCase {

	public FieldsAndReturnTypesTest(final String name) {
		super(name);

	}

	// test fields and return types in comparison with what Padl .class gives
	public void testFieldsAndReturnTypes() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/fieldsAndReturnTypes/FieldsAndReturnTypes.java" };
		final String classPathEntry = "";

		final String classFilesFolderPath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/fieldsAndReturnTypes/FieldsAndReturnTypes.class";
		final ICodeLevelModel padlModelFromJavaFiles =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		// Model from .class
		final ICodeLevelModel padlModelFromClassFiles =
			Utils.createLightJavaClassesPadlModel("", classFilesFolderPath);

		padlModelFromJavaFiles.walk(new RelaxedModelComparator(
			padlModelFromClassFiles));
	}
}
