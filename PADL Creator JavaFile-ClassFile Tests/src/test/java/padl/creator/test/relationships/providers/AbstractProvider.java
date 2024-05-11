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
package padl.creator.test.relationships.providers;

import java.io.File;

abstract class AbstractProvider {
	private static final String BIN_FOLDER =
		"../PADL Creator JavaFile-ClassFile Tests/target/test-classes/";
	private static final String BYTE_CODE_EXTENSION = ".class";
	private static final String JAVA_CODE_EXTENSION = ".java";
	private static final String SRC_FOLDER =
		"../PADL Creator JavaFile-ClassFile Tests/src/test/java/";
	private static final String TEST_CLASS_PREFIX = "Test";

	public abstract String getHelperClassName();

	private String getPathTo(
		final String aTargetClassName,
		final String aFolder,
		final String aTestSuffix,
		final String anExtension) {

		final String testClassName =
			aFolder
					+ aTargetClassName.replaceAll(
						ITestProvider.PROVIDER_CLASS_PREFIX,
						aTestSuffix).replace('.', '/') + anExtension;
		final File pathTestClass = new File(testClassName);
		if (!pathTestClass.exists()) {
			throw new RuntimeException("Cannot find class: " + testClassName);
		}
		return pathTestClass.getAbsolutePath();
	}
	public String getPathToByteCodeOfHelperClass() {
		return this.getPathTo(
			this.getHelperClassName(),
			AbstractProvider.BIN_FOLDER,
			AbstractProvider.TEST_CLASS_PREFIX,
			AbstractProvider.BYTE_CODE_EXTENSION);
	}
	public String getPathToByteCodeOfTestClass() {
		return this.getPathTo(
			this.getTestFileName(),
			AbstractProvider.BIN_FOLDER,
			AbstractProvider.TEST_CLASS_PREFIX,
			AbstractProvider.BYTE_CODE_EXTENSION);
	}
	public String getPathToJavaCodeOfHelperClass() {
		return this.getPathTo(
			this.getHelperClassName(),
			AbstractProvider.SRC_FOLDER,
			ITestProvider.PROVIDER_CLASS_PREFIX,
			AbstractProvider.JAVA_CODE_EXTENSION);
	}
	public String getPathToJavaCodeOfTestClass() {
		return this.getPathTo(
			this.getTestFileName(),
			AbstractProvider.SRC_FOLDER,
			ITestProvider.PROVIDER_CLASS_PREFIX,
			AbstractProvider.JAVA_CODE_EXTENSION);
	}
	public String getTestClassName() {
		return this.getClass().getName().replaceAll(
			ITestProvider.PROVIDER_CLASS_PREFIX,
			AbstractProvider.TEST_CLASS_PREFIX);
	}
	private String getTestFileName() {
		return this.getClass().getName();
	}
}
