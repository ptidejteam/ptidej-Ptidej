/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
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

public class TestComposite extends TestCase {
	private static IDesignMotifModel CompositePattern;
	private static IDesignMotifModel SerialisedCompositePattern;
	private static String SerialisedFileName;

	public TestComposite(String name) {
		super(name);
	}
	protected void setUp() {
		if (TestComposite.CompositePattern == null) {
			TestComposite.CompositePattern = new Composite();
			TestComposite.SerialisedFileName =
				JOSSerialiser.getInstance().serialiseWithAutomaticNaming(
					TestComposite.CompositePattern,
					"../PADL Serialiser JOS Tests/rsc/");
			TestComposite.SerialisedCompositePattern =
				(IDesignMotifModel) JOSSerialiser.getInstance().deserialise(
					TestComposite.SerialisedFileName);
		}
	}
	protected void tearDown() {
		final File serialisedFile = new File(TestComposite.SerialisedFileName);
		serialisedFile.delete();

		final File serialisedHelperFile =
			new File(TestComposite.SerialisedFileName
					+ TransientFieldManager.METHOD_INVOCATION_EXTENSION);
		serialisedHelperFile.delete();
	}
	public void testNames() {
		Assert.assertEquals(
			TestComposite.CompositePattern.getName(),
			TestComposite.SerialisedCompositePattern.getName());
	}
	public void testComparator() {
		TestComposite.CompositePattern.walk(new ModelComparator(
			TestComposite.SerialisedCompositePattern));
	}
}
