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
package pom.test.classfile.specific;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.javafile.javac.JavaFileCreator;
import padl.generator.helper.ModelGenerator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.impl.Factory;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;

public class DITTest extends TestCase {
	protected static MetricsRepository MetricsRepository;
	private static ICodeLevelModel Model = null;
	protected static IIdiomLevelModel ModelJavaC = null;
	private static final String root =
		"../POM/target/test-classes/Metric Specific for Java/bin/pom/test/rsc/specific/testDIT/";
	private static final String rootJavaC =
			"../POM/target/test-classes/Metric Specific for Java/src/pom/test/rsc/specific/testDIT/";
	protected final String metricName = "DIT";
	protected final double expectedDITGhost = 4d;
	public DITTest(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
		if (DITTest.Model == null) {
			DITTest.Model =
				Factory.getInstance().createCodeLevelModel("Test.TestMetrics");
			DITTest.Model.create(new CompleteClassFileCreator(
				new String[] { root }));

			DITTest.MetricsRepository =
				pom.metrics.MetricsRepository.getInstance();
		}
		if (DITTest.ModelJavaC == null) {
			DITTest.ModelJavaC = ModelGenerator.generateModelFromJavaFilesDirectoryUsingJavaC("",
					new String[] {rootJavaC});
		}
	}
	public void testRefClass() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) DITTest.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestSingleClass");

		Assert.assertEquals(1d, ((IUnaryMetric) MetricsRepository
			.getMetric(metricName)).compute(DITTest.Model, firstClassEntity), 0d);
	}
	public void testRefInterface() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) DITTest.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestSingleInterface");

		Assert.assertEquals(1.0d, ((IUnaryMetric) MetricsRepository
			.getMetric(metricName)).compute(DITTest.Model, firstClassEntity), 0d);
	}
	public void testInheritanceOnClass() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) DITTest.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestAChild03");

		Assert.assertEquals(4d, ((IUnaryMetric) MetricsRepository
			.getMetric(metricName)).compute(DITTest.Model, firstClassEntity), 0d);
	}
	public void testInheritanceOnInterface01() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) DITTest.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestBChild03");

		Assert.assertEquals(4d, ((IUnaryMetric) MetricsRepository
			.getMetric(metricName)).compute(DITTest.Model, firstClassEntity), 0d);
	}
	public void testInheritanceOnInterface02() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) DITTest.Model
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestBChild11");

		Assert.assertEquals(2d, ((IUnaryMetric) MetricsRepository
			.getMetric(metricName)).compute(DITTest.Model, firstClassEntity), 0d);
	}
	public void testGhost() {
		final IFirstClassEntity firstClassEntity =
			(IFirstClassEntity) DITTest.ModelJavaC
				.getTopLevelEntityFromID("pom.test.rsc.specific.testDIT.TestGhostSwing");

		Assert.assertEquals(expectedDITGhost, ((IUnaryMetric) MetricsRepository
			.getMetric(metricName)).compute(DITTest.Model, firstClassEntity), 0d);
	}
}
