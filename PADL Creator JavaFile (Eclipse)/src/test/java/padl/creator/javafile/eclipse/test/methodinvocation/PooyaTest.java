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
package padl.creator.javafile.eclipse.test.methodinvocation;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;

public class PooyaTest extends TestCase {
	public PooyaTest(final String name) {
		super(name);
	}
	public void test1() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes/Pooya's/";
		final String classPathEntry = "";

		final ICodeLevelModel codeLevelModel = Utils
			.createCompleteJavaFilesPadlModel("", sourcePath, classPathEntry);

		final IFirstClassEntity entity = codeLevelModel
			.getTopLevelEntityFromID("java.util.Mazaheri".toString());
		Assert.assertNotNull(entity);

		final IMethod method =
			(IMethod) entity.getConstituentFromID("MazMaz(int, int)");
		Assert.assertNotNull(method);

		Assert.assertEquals(method.getNumberOfConstituents(), 2);
	}
}
