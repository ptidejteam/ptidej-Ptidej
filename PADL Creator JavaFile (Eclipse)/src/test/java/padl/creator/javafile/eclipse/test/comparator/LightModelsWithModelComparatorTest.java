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

import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.RelaxedModelComparator;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;
import util.io.Files;

public class LightModelsWithModelComparatorTest extends TestCase {
	public LightModelsWithModelComparatorTest(final String aName) {
		super(aName);
	}

	/**
	 * Compare two models created source code or binary classes based on all the
	 * source The comparison is done by ModelComparator
	 * 
	 */

	/*public void testModelsFromAllSrceComparison() {

		final String javaFilesFolderPath = "../Java Parser/src/main/java/";
		final String classPathEntry = "";
		final String classFilesFolderPath = "../Java Parser/target/classes/";

		// Model from source code

		final ICodeLevelModel padlModelFromJavaFiles = Utils
				.createJavaFilesPadlModel("", javaFilesFolderPath,
						classPathEntry);

		// Model from .class
		final ICodeLevelModel padlModelFromClassFiles = Utils
				.createJavaClassesPadlModel("", classFilesFolderPath);

		padlModelFromJavaFiles.walk(new RelaxedModelComparator(
				padlModelFromClassFiles));

		padlModelFromClassFiles.walk(new RelaxedModelComparator(
				padlModelFromClassFiles));

		padlModelFromJavaFiles.walk(new RelaxedModelComparator(
				padlModelFromJavaFiles));

	}*/

	/**
	 * Compare two models created source code and binary classes based on one
	 * class The comparison is done by ModelComparator
	 */

	public void testModelsFromOneFileComparison() {

		final String javaFilesFolderPath = "../Java Parser/src/main/java/";
		final String classPathEntry = "";
		final String[] javaFilesList =
			new String[] { "../Java Parser/src/parser/input/SourceInputsHolder.java" };
		final String classFilesFolderPath =
				"../Java Parser/target/classes/parser/input/SourceInputsHolder.class";

		// Model from source code

		final ICodeLevelModel padlModelFromJavaFiles =
			Utils.createLightJavaFilesPadlModel(
				"",
				javaFilesFolderPath,
				classPathEntry,
				javaFilesList);

		// Model from .class
		final ICodeLevelModel padlModelFromClassFiles =
			Utils.createLightJavaClassesPadlModel("", classFilesFolderPath);

		padlModelFromJavaFiles.walk(new RelaxedModelComparator(
			padlModelFromClassFiles));

	}

	/**
	 * Compare two models created source code and binary classes based on one
	 * class The comparison is done by ModelComparator
	 */

	public void testModelsFromOneFileComparison2() {
		final String javaFilesFolderPath =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/";
		final String classPathEntry = "";
		final String[] javaFilesList =
			new String[] { "../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/padl/example/interfaceComparator/IConstituent.java" };
		final String classFilesFolderPath =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/padl/example/interfaceComparator/IConstituent.class";

		// Model from source code

		final ICodeLevelModel padlModelFromJavaFiles =
			Utils.createLightJavaFilesPadlModel(
				"",
				javaFilesFolderPath,
				classPathEntry,
				javaFilesList);

		// Model from .class
		final ICodeLevelModel padlModelFromClassFiles =
			Utils.createLightJavaClassesPadlModel("", classFilesFolderPath);

		padlModelFromJavaFiles.walk(new RelaxedModelComparator(
			padlModelFromJavaFiles));
		padlModelFromClassFiles.walk(new RelaxedModelComparator(
			padlModelFromClassFiles));
		padlModelFromJavaFiles.walk(new RelaxedModelComparator(
			padlModelFromClassFiles));
	}

}
