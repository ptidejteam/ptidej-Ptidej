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
import padl.creator.javafile.eclipse.test.util.RelaxedModelComparatorReporter;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;

public class ModelsDifferencesReporter extends TestCase {
	public ModelsDifferencesReporter(final String aName) {
		super(aName);
	}
	public void testComparison() {
		/**
		 * Compare two models created source code or binary classes based on all
		 * the source The comparison is done by ModelComparator
		 * 
		 */
		System.out.println("Models differences reporter");
		final String javaFilesFolderPath = "../Java Parser/src/";
		final String classPathEntry = "";
		//	final String classFilesFolderPath = "../Java Parser/bin/";

		// Model from source code

		final ICodeLevelModel padlModelFromJavaFiles =
			Utils.createLightJavaFilesPadlModel(
				"",
				javaFilesFolderPath,
				classPathEntry);

		// Model from .class
		//	final ICodeLevelModel padlModelFromClassFiles =
		//		Utils.createLightJavaClassesPadlModel("", classFilesFolderPath);

		padlModelFromJavaFiles.walk(new RelaxedModelComparatorReporter(
			padlModelFromJavaFiles));
	}
}
