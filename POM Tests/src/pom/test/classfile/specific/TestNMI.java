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
package pom.test.classfile.specific;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;

public class TestNMI extends TestCase {
	private static MetricsRepository metrics;
	private static ICodeLevelModel model = null;
	private static final String root =
		"../POM Tests/data/Metric Specific for Java/bin/pom/test/rsc/specific/testNMI/";

	public TestNMI(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (model == null) {
			model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			model.create(new CompleteClassFileCreator(new String[] { root }));

			metrics = MetricsRepository.getInstance();
		}
	}
	public void testDifMethods() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testNMI.TestAChild00");

		Assert.assertEquals(
			"Test about inheritance of differents methods",
			3d,
			((IUnaryMetric) metrics.getMetric("NMI")).compute(
				model,
				firstClassEntity),
			0d);
	}

	public void testSameMethods() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testNMI.TestBChild00");

		Assert.assertEquals(
			"Test about inheritance of the same methods",
			1d,
			((IUnaryMetric) metrics.getMetric("NMI")).compute(
				model,
				firstClassEntity),
			0d);
	}

	public void testDiffSignature() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testNMI.TestCChild00");

		Assert
			.assertEquals(
				"Test about inheritance of the same methods but differents signatures",
				3d,
				((IUnaryMetric) metrics.getMetric("NMI")).compute(
					model,
					firstClassEntity),
				0d);
	}

	public void testConstructor() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testNMI.TestDChild00");

		Assert.assertEquals(
			"Test about inheritance of the Constructor",
			0d,
			((IUnaryMetric) metrics.getMetric("NMI")).compute(
				model,
				firstClassEntity),
			0d);
	}
}
