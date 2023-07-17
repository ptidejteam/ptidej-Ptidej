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
package pom.test.classfile.general;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;

/**
 * @author Yann
 * @since  2013/04/21
 */
public final class TestNMIandNMOwithPattern4J extends TestCase {
	private static MetricsRepository MetricsRepository;
	private static ICodeLevelModel Model = null;
	private static final String ROOT = "../POM Tests/rsc/pattern4.jar";

	public TestNMIandNMOwithPattern4J(final String aName) {
		super(aName);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (Model == null) {
			MetricsRepository = pom.metrics.MetricsRepository.getInstance();
			Model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			Model.create(new CompleteClassFileCreator(new String[] { ROOT }));
		}
	}
	public void testNMOforPatternDescriptor() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("gr.uom.java.pattern.PatternDescriptor");
		Assert.assertEquals(
			"Computing NMO on gr.uom.java.pattern.PatternDescriptor",
			0d,
			((IUnaryMetric) MetricsRepository.getMetric("NMO")).compute(
				Model,
				firstClassEntity),
			0);
	}
	public void testNMIforPatternDescriptor() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) Model
				.getTopLevelEntityFromID("gr.uom.java.pattern.PatternDescriptor");
		Assert.assertEquals(
			"Computing NMI on gr.uom.java.pattern.PatternDescriptor",
			69d,
			((IUnaryMetric) MetricsRepository.getMetric("NMI")).compute(
				Model,
				firstClassEntity),
			0);
	}
}
