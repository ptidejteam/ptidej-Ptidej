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
package pom.test.classfile.general;

import java.io.StringWriter;
import java.io.Writer;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;
import pom.metrics.MetricsRepository;
import pom.metrics.repository.USELESS;
import util.io.Files;
import util.io.ProxyConsole;

/**
 * @author Foutse Khomh
 *  since   2009-03-04
 *  
 */
public class TestMetricRepository extends TestCase {
	public TestMetricRepository(String aName) {
		super(aName);
	}

	private static ICodeLevelModel Model = null;
	private static final String Root = "../POM Tests/data/java.lang.jar";
	private static MetricsRepository Metrics;

	protected void setUp() throws Exception {
		super.setUp();
		if (TestMetricRepository.Model == null) {
			TestMetricRepository.Model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			TestMetricRepository.Model.create(new CompleteClassFileCreator(
				new String[] { TestMetricRepository.Root }));
			TestMetricRepository.Metrics = MetricsRepository.getInstance();
		}
	}
	public void testUnarymetricList() {
		Assert.assertNotNull(TestMetricRepository.Metrics.getUnaryMetrics());
	}
	public void testRuntimeDeprecation() {
		final StringWriter writer = new StringWriter();
		final Writer oldOut = ProxyConsole.getInstance().normalOutput();
		ProxyConsole.getInstance().setNormalOutput(writer);
		final double result =
			new USELESS().compute(
				TestMetricRepository.Model,
				TestMetricRepository.Model
					.getTopLevelEntityFromID("java.lang.Object"));
		ProxyConsole.getInstance().setNormalOutput(oldOut);
		Assert.assertEquals((double) 1, result, 0d);
		Assert
			.assertEquals(
				"util.lang.ConcreteReceiverGuard reports a runtime deprecation: calling method \"pom.metrics.repository.USELESS.<init>()\" from class \"pom.test.classfile.general.TestMetricRepository\"\r\nPlease do not instantiate metrics directly to allow efficient caching, use the methods of \"pom.metrics.MetricsRepository\" to obtain metric instances.\r\n",
				writer.toString());
	}
}
