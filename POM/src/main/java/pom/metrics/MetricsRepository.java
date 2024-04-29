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
package pom.metrics;

import java.util.ArrayList;
import java.util.List;

import com.ibm.toad.cfparse.ClassFile;

import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;

public class MetricsRepository implements IRepository {
	private static MetricsRepository UniqueInstance;

	public static MetricsRepository getInstance() {
		// Yann 2013/07/03: Singleton per model
		// It is a Singleton per model, probably could
		// keep trace of the model so as not to create
		// again and again the metrics when calling the
		// repository on one model, then another, then 
		// one...
		// Yann 2013/07/10: Bis repetita
		// Better keep the model separate!
		if (MetricsRepository.UniqueInstance == null) {
			MetricsRepository.UniqueInstance = new MetricsRepository();
		}
		return MetricsRepository.UniqueInstance;
	}

	private final IMetric[] allMetrics;
	private final IBinaryMetric[] binaryMetrics;
	private final IUnaryMetric[] unaryMetrics;

	// @Note David: Added model independent metrics
	private final IDependencyIndependentMetric[] dependencyIndependentMetrics;

	private MetricsRepository() {
		final ClassFile[] classFiles;
		try {
			classFiles = SubtypeLoader.loadSubtypesFromStream(
					"pom.metrics.IMetric", FileRepositoryFactory.getInstance()
							.getFileRepository(this).getFiles(),
					"pom.metrics.repository", ".class");
		}
		catch (final FileAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			this.allMetrics = new IMetric[0];
			this.binaryMetrics = new IBinaryMetric[0];
			this.unaryMetrics = new IUnaryMetric[0];
			this.dependencyIndependentMetrics = new IDependencyIndependentMetric[0];
			return;
		}

		final List listOfAllMetrics = new ArrayList();
		final List listOfUnaryMetrics = new ArrayList();
		final List listOfBinaryMetrics = new ArrayList();

		// David: Added independent metrics
		final List listOfIndependentMetrics = new ArrayList();

		for (int i = 0; i < classFiles.length; i++) {
			try {
				final Class metricClass = Class
						.forName(classFiles[i].getName());
				final IMetric metric = (IMetric) metricClass.newInstance();

				listOfAllMetrics.add(metric);
				if (IUnaryMetric.class.isAssignableFrom(metricClass)) {
					listOfUnaryMetrics.add(metric);
				}
				// Aminata 17/05/2011
				// Some are IUnaryMetric and also IBinaryMetric
				// so I removed this else  
				//	else if (IBinaryMetric.class.isAssignableFrom(metricClass)) {
				if (IBinaryMetric.class.isAssignableFrom(metricClass)) {
					listOfBinaryMetrics.add(metric);
				}

				// David: Added IIndependentMetrics
				if (IDependencyIndependentMetric.class
						.isAssignableFrom(metricClass)) {
					listOfIndependentMetrics.add(metric);
				}

			}
			catch (final ClassNotFoundException cnfe) {
			}
			catch (final IllegalArgumentException iae) {
				iae.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final SecurityException se) {
				se.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final InstantiationException ie) {
				ie.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IllegalAccessException iae) {
				iae.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}

		this.allMetrics = new IMetric[listOfAllMetrics.size()];
		listOfAllMetrics.toArray(this.allMetrics);
		this.binaryMetrics = new IBinaryMetric[listOfBinaryMetrics.size()];
		listOfBinaryMetrics.toArray(this.binaryMetrics);
		this.unaryMetrics = new IUnaryMetric[listOfUnaryMetrics.size()];
		listOfUnaryMetrics.toArray(this.unaryMetrics);

		this.dependencyIndependentMetrics = new IDependencyIndependentMetric[listOfIndependentMetrics
				.size()];
		listOfIndependentMetrics.toArray(this.dependencyIndependentMetrics);
	}

	//	public double compute(
	//		final String aMetricName,
	//		final IFirstClassEntity anEntity) {
	//		final IMetric metric = this.getMetric(aMetricName);
	//
	//		if (metric instanceof IUnaryMetric) {
	//			return ((IUnaryMetric) metric).compute(anEntity);
	//		}
	//
	//		return -1;
	//	}
	//	public double compute(
	//		final String aMetricName,
	//		final IFirstClassEntity anEntityA,
	//		final IFirstClassEntity anEntityB) {
	//
	//		final IMetric metric = this.getMetric(aMetricName);
	//
	//		if (metric instanceof IBinaryMetric) {
	//			return ((IBinaryMetric) metric).compute(anEntityA, anEntityB);
	//		}
	//
	//		return -1;
	//	}
	public IBinaryMetric[] getBinaryMetrics() {
		return this.binaryMetrics;
	}

	// TODO For symmetry with other Repository, could we do without this method?
	public IMetric getMetric(final String aMetricName) {
		for (int i = 0; i < this.allMetrics.length; i++) {
			final IMetric metric = this.allMetrics[i];
			final String metricName = metric.getName();
			if (metricName.equals(aMetricName)) {
				return metric;
			}
		}
		return null;
	}

	public IMetric[] getMetrics() {
		return this.allMetrics;
	}

	public IUnaryMetric[] getUnaryMetrics() {
		return this.unaryMetrics;
	}

	public IDependencyIndependentMetric[] getIndependentMetrics() {
		return this.dependencyIndependentMetrics;
	}
}
