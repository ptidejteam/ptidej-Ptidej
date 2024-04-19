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
package padl.pagerank.test;

import java.io.StringWriter;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.pagerank.helper.PageRankCallerWithNoParameters;
import padl.pagerank.utils.InputDataGeneratorWith9RelationsForCPP;

public class TestCPP extends TestCase {
	private static InputDataGeneratorWith9RelationsForCPP Generator;
	public TestCPP(final String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();

		if (TestCPP.Generator == null) {
			TestCPP.Generator =
				new InputDataGeneratorWith9RelationsForCPP(false, true);
			final StringWriter writer = new StringWriter();
			PageRankCallerWithNoParameters.callForSomeCPPFiles(
				"Simple",
				"../PADL Creator C++ (Eclipse) Tests/data/Simple/",
				TestCPP.Generator,
				writer);
			writer.close();
		}
	}
	public void testType4() {
		// Called methods
		Assert.assertEquals(4, TestCPP.Generator
			.getRelationsType4CalledMethods()
			.size());
	}
	public void testType5() {
		// Field accesses
		Assert.assertEquals(0, TestCPP.Generator
			.getRelationsType5FieldAccesses()
			.size());
	}
	public void testType6() {
		// Types of fields
		Assert.assertEquals(0, TestCPP.Generator
			.getRelationsType6TypesOfFields()
			.size());
	}
	public void testType7() {
		// Return types of methods
		Assert.assertEquals(0, TestCPP.Generator
			.getRelationsType7ReturnTypesOfMethods()
			.size());
	}
	public void testType8() {
		// Parameter types of methods
		Assert.assertEquals(7, TestCPP.Generator
			.getRelationsType8ParameterTypesOfMethods()
			.size());
	}
}
