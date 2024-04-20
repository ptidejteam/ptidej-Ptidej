/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package padl.serialiser.test;

import java.io.File;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.motif.IDesignMotifModel;
import padl.motif.repository.Composite;
import padl.serialiser.JOSSerialiser;
import padl.serialiser.util.TransientFieldManager;
import padl.test.helper.ModelComparator;

public class CompositeTest extends TestCase {
	private static IDesignMotifModel CompositePattern;
	private static IDesignMotifModel SerialisedCompositePattern;
	private static String SerialisedFileName;

	public CompositeTest(String name) {
		super(name);
	}

	protected void setUp() {
		if (CompositeTest.CompositePattern == null) {
			CompositeTest.CompositePattern = new Composite();
			CompositeTest.SerialisedFileName = JOSSerialiser.getInstance()
					.serialiseWithAutomaticNaming(
							CompositeTest.CompositePattern,
							"../PADL Serialiser JOS Tests/target/test-classes/");
			CompositeTest.SerialisedCompositePattern = (IDesignMotifModel) JOSSerialiser
					.getInstance()
					.deserialise(CompositeTest.SerialisedFileName);
		}
	}

	protected void tearDown() {
		final File serialisedFile = new File(CompositeTest.SerialisedFileName);
		serialisedFile.delete();

		final File serialisedHelperFile = new File(
				CompositeTest.SerialisedFileName
						+ TransientFieldManager.METHOD_INVOCATION_EXTENSION);
		serialisedHelperFile.delete();
	}

	public void testNames() {
		Assert.assertEquals(CompositeTest.CompositePattern.getName(),
				CompositeTest.SerialisedCompositePattern.getName());
	}

	public void testComparator() {
		CompositeTest.CompositePattern.walk(
				new ModelComparator(CompositeTest.SerialisedCompositePattern));
	}
}
