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
package pom.test.cppfile.general;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import pom.metrics.MetricsRepository;

/**
 * @author Yann
 * @since  2014/04/19
 */
public class QMOODMetricsTest extends TestCase {
	private static IFirstClassEntity Entity;
	private static IIdiomLevelModel IdiomLevelModel;
	private static MetricsRepository Repository;

	public QMOODMetricsTest(final String aName) {
		super(aName);
	}

	// TODO Add this whole test back
	public void testDummy() {
		Assert.assertTrue(true);
	}
	/*
	protected void setUp() throws Exception {
		super.setUp();
	
		if (TestQMOODMetrics.IdiomLevelModel == null) {
			TestQMOODMetrics.IdiomLevelModel =
				ModelGenerator.generateModelFromCppFilesUsingEclipse(
					"LOG4C++ v0.2.7",
					"../POM/target/test-classes/log4cpp-0.2.7/");
			TestQMOODMetrics.Repository = MetricsRepository.getInstance();
			TestQMOODMetrics.Entity =
				(IFirstClassEntity) TestQMOODMetrics.IdiomLevelModel
					.getTopLevelEntityFromID("log4cpp.Appender");
		}
	}
	
	public void testAID() {
		Assert.assertEquals(
			"AID",
			0d,
			((IUnaryMetric) TestQMOODMetrics.Repository.getMetric("AID"))
				.compute(
					TestQMOODMetrics.IdiomLevelModel,
					TestQMOODMetrics.Entity),
			0);
	}
	public void testCLD() {
		Assert.assertEquals(
			"CLD",
			3d,
			((IUnaryMetric) TestQMOODMetrics.Repository.getMetric("CLD"))
				.compute(
					TestQMOODMetrics.IdiomLevelModel,
					TestQMOODMetrics.Entity),
			0);
	}
	public void testDIT() {
		Assert.assertEquals(
			"DIT",
			0d,
			((IUnaryMetric) TestQMOODMetrics.Repository.getMetric("DIT"))
				.compute(
					TestQMOODMetrics.IdiomLevelModel,
					TestQMOODMetrics.Entity),
			0);
	}
	public void testNMA() {
		Assert.assertEquals(
			"NMA",
			20d,
			((IUnaryMetric) TestQMOODMetrics.Repository.getMetric("NMA"))
				.compute(
					TestQMOODMetrics.IdiomLevelModel,
					TestQMOODMetrics.Entity),
			0);
	}
	public void testNMI() {
		Assert.assertEquals(
			"NMI",
			0d,
			((IUnaryMetric) TestQMOODMetrics.Repository.getMetric("NMI"))
				.compute(
					TestQMOODMetrics.IdiomLevelModel,
					TestQMOODMetrics.Entity),
			0);
	}
	public void testNMO() {
		Assert.assertEquals(
			"NMO",
			0d,
			((IUnaryMetric) TestQMOODMetrics.Repository.getMetric("NMO"))
				.compute(
					TestQMOODMetrics.IdiomLevelModel,
					TestQMOODMetrics.Entity),
			0);
	}
	public void testSIX() {
		Assert.assertEquals(
			"SIX",
			0.0d,
			((IUnaryMetric) TestQMOODMetrics.Repository.getMetric("SIX"))
				.compute(
					TestQMOODMetrics.IdiomLevelModel,
					TestQMOODMetrics.Entity),
			0d);
	}
	*/
}
