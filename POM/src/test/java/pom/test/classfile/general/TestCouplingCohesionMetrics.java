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
package pom.test.classfile.general;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Factory;
import pom.metrics.IBinaryMetric;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;

/**
 * @author Farouk Zaidi
 */
public final class TestCouplingCohesionMetrics extends TestCase {
	private static MetricsRepository MetricsRepository;
	private static ICodeLevelModel ModelForCouplingCohesion = null;
	private static final String ROOT = "../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/";

	public TestCouplingCohesionMetrics(final String aName) {
		super(aName);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (ModelForCouplingCohesion == null) {
			ModelForCouplingCohesion = Factory.getInstance()
					.createCodeLevelModel("Test.TestMetrics");
			ModelForCouplingCohesion.create(
					new CompleteClassFileCreator(new String[] { ROOT }));
			MetricsRepository = pom.metrics.MetricsRepository.getInstance();
		}
	}

	public void testACAIC() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.G2");
		Assert.assertEquals("Computing ACAIC on G2", 7d,
				((IUnaryMetric) MetricsRepository.getMetric("ACAIC")).compute(
						ModelForCouplingCohesion, firstClassEntity),
				0);
	}

	public void testACMIC() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.F2");
		Assert.assertEquals("Computing ACMIC on F2", 12d,
				((IUnaryMetric) MetricsRepository.getMetric("ACMIC")).compute(
						ModelForCouplingCohesion, firstClassEntity),
				0);
	}

	public void testCBO() {
		final IFirstClassEntity classA = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.A");
		final IFirstClassEntity classB = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.B");
		Assert.assertEquals("CBO", 6d,
				((IBinaryMetric) MetricsRepository.getMetric("CBO"))
						.compute(ModelForCouplingCohesion, classA, classB),
				0);
	}
	/*
		public void testCBOUnit() {
			final IEntity entity = (IEntity) modelForCouplingCohesion
				.getAnyEntityFromID("pom.test.rsc.A");
			Assert.assertEquals(
				"Computing unitary CBO for the class pom.test.rsc.A",
				4d,
				metrics.compute("CBO", entity),
				0);
		}*/

	public void testCohesionAttributes() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.H");
		Assert.assertEquals(
				"Computing Cohesion attributes for the class pom.test.rsc.H",
				0.2d,
				((IUnaryMetric) MetricsRepository
						.getMetric("cohesionAttributes")).compute(
								ModelForCouplingCohesion, firstClassEntity),
				0);
	}

	public void testConnectivity() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.H");
		Assert.assertEquals(
				"Computing connectivity for the class pom.test.rsc.H", 1d,
				((IUnaryMetric) MetricsRepository.getMetric("connectivity"))
						.compute(ModelForCouplingCohesion, firstClassEntity),
				0);
	}

	public void testDCAEC() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.C2");
		Assert.assertEquals("Computing DCAEC on C2", 9d,
				((IUnaryMetric) MetricsRepository.getMetric("DCAEC")).compute(
						ModelForCouplingCohesion, firstClassEntity),
				0);
	}

	public void testDCMEC() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.E2");
		Assert.assertEquals("Computing DCMEC on E2", 7d,
				((IUnaryMetric) MetricsRepository.getMetric("DCMEC")).compute(
						ModelForCouplingCohesion, firstClassEntity),
				0);
	}

	public void testICH() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.H");

		//Farouk : May 11, 2004 - The method ICHMethod became private, so it is not possible to invoke it.
		/*		String[] methodNames =
		 {
		 "<init>()",
		 "computeResult(int, java.lang.String, java.lang.Integer)",
		 "computeSum()",
		 "displaySum(int, boolean)",
		 "computeLine(int, int, int, java.lang.String)",
		 "toString()",
		 "fooNothing()" };
		 
		 int[] expectedIchMethodValues = { 0, 0, 18, 2, 0, 15, 0 };
		 for (int i = 0; i < expectedIchMethodValues.length; i++) {
		 final IMethod method =
		 (IMethod) entity.getConstituentFromID(methodNames[i]);
		 Assert.assertEquals(
		 "Computing ICH for the method " + methodNames[i],
		 expectedIchMethodValues[i],
		 metrics.ICHmethod(method, entity));
		 }
		 */
		Assert.assertEquals("Computing ICH for the class pom.test.rsc.H", 35d,
				((IUnaryMetric) MetricsRepository.getMetric("ICHClass"))
						.compute(ModelForCouplingCohesion, firstClassEntity),
				0);

	}

	public void testLCOM2() {
		final String id = "MethodDump";
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc." + id);
		final double lcom = ((IUnaryMetric) MetricsRepository
				.getMetric("LCOM2"))
				.compute(ModelForCouplingCohesion, firstClassEntity);
		Assert.assertEquals("LCOM2", 0d, lcom, 0);
	}

	public void testLCOM5() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.H");
		Assert.assertEquals("Computing LCOM5 for the class pom.test.rsc.H",
				0.9d, ((IUnaryMetric) MetricsRepository.getMetric("LCOM5"))
						.compute(ModelForCouplingCohesion, firstClassEntity),
				0.003);
	}

	// Farouk : May 11, 2004 - Now for WMC, we consider that a method can start with 1 by default, even if the body is empty. 
	public void testWMCforAClass() {
		final IFirstClassEntity firstClassEntity = (IFirstClassEntity) ModelForCouplingCohesion
				.getTopLevelEntityFromID("pom.test.rsc.A");
		final double wmc = ((IUnaryMetric) MetricsRepository.getMetric("WMC"))
				.compute(ModelForCouplingCohesion, firstClassEntity);
		Assert.assertEquals("Computing of the weight for an entity:", 36d, wmc,
				0);
	}
}
