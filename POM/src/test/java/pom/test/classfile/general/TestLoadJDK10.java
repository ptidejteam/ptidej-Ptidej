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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import pom.metrics.IBinaryMetric;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import util.io.Files;

/**
 * @author zaidifar
 * 
 * We consider the package java.lang. The arborescence seems to be good for doing tests.
 * We consider some classes on the middle of the hierarchy. We are using JDK1.2.2. However,
 * a representation of the tree is available at : 
 * http://java.sun.com/j2se/1.3/docs/api/java/lang/package-tree.html
 * 
 */
public final class TestLoadJDK10 extends TestCase {
	private static String root = "../POM/target/test-classes/jdk102.jar";

	public TestLoadJDK10(String aName) {
		super(aName);
	}
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testTimeExecution() {
		long start = System.currentTimeMillis();

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("test JDK102");
		final ModelStatistics modelStatistics = new ModelStatistics();
		codeLevelModel.addModelListener(modelStatistics);
		try {
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { root }));
		}
		catch (CreationException ce) {
			ce.printStackTrace(System.err);
		}
		System.out.println(modelStatistics);

		// Exculding the IGhost entities
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final List listOfEntities = new ArrayList();
		final Iterator iterator =
			codeLevelModel.getIteratorOnConstituents(IFirstClassEntity.class);
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			if (!(firstClassEntity instanceof IGhost)) {
				listOfEntities.add(firstClassEntity);
			}
		}
		final IFirstClassEntity[] entities =
			new IFirstClassEntity[listOfEntities.size()];
		listOfEntities.toArray(entities);

		final MetricsRepository metricRepository =
			MetricsRepository.getInstance();
		System.out.println("Computing unary metrics...");
		this.computeUnaryMetrics(codeLevelModel, entities, metricRepository);
		System.out.println("Computing binary metrics...");
		this.computeBinaryMetrics(codeLevelModel, entities, metricRepository);

		long end = System.currentTimeMillis();
		long time = (end - start) / 1000;
		System.out.println("Computation time:" + time);
	}

	public void computeUnaryMetrics(
		final IAbstractLevelModel anAbstractLevelModel,
		final IFirstClassEntity[] someEntities,
		final MetricsRepository metricRepository) {

		IFirstClassEntity entityA;
		int count = 0;
		final IUnaryMetric[] metrics = metricRepository.getUnaryMetrics();

		for (int i = 0; i < someEntities.length - 1; i++) {
			for (int j = i + 1; j < someEntities.length; j++) {
				entityA = someEntities[i];

				for (int k = 0; k < metrics.length; k++) {
					final IUnaryMetric binaryMetric = metrics[k];
					binaryMetric.compute(anAbstractLevelModel, entityA);
					System.out.println("1 - " + count++);
				}
			}
		}
	}
	public void computeBinaryMetrics(
		final IAbstractLevelModel anAbstractLevelModel,
		final IFirstClassEntity[] someEntities,
		final MetricsRepository metricRepository) {

		IFirstClassEntity entityA;
		IFirstClassEntity entityB;
		int count = 0;
		final IBinaryMetric[] metrics = metricRepository.getBinaryMetrics();

		for (int i = 0; i < someEntities.length - 1; i++) {
			for (int j = i + 1; j < someEntities.length; j++) {
				entityA = someEntities[i];
				entityB = someEntities[j];

				for (int k = 0; k < metrics.length; k++) {
					final IBinaryMetric binaryMetric = metrics[k];
					binaryMetric
						.compute(anAbstractLevelModel, entityA, entityB);
					System.out.println("2 - " + count++);
				}
			}
		}
	}
}
