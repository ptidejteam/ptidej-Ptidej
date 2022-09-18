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
package padl.generator.helper.eclipse.snaphots;

import java.io.File;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.generator.helper.utils.FilesUtils;

public class FilesUtilsTest extends TestCase {

	public FilesUtilsTest(final String name) {
		super(name);
	}

	public void testCountJavaFiles() {
		String pathDir = "./rsc/project1/";
		long expectedNumber = 19;
		long actualNumber = FilesUtils.countJavaFiles(new File(pathDir));

		Assert.assertEquals(expectedNumber, actualNumber);
	}

	public void testCleanDir() {
		String pathDir = "./rsc/project2/";
		long expectedNumber = 19;
		FilesUtils.filterDir(new File(pathDir));
		long actualNumber = FilesUtils.countJavaFiles(new File(pathDir));

		Assert.assertEquals(expectedNumber, actualNumber);
	}

}
