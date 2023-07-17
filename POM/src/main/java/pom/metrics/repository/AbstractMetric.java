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
package pom.metrics.repository;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.util.Util;
import pom.metrics.IBinaryMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import pom.operators.Operators;
import pom.primitives.ClassPrimitives;
import pom.primitives.MethodPrimitives;
import pom.util.CacheManager;
import pom.util.NoSuchValueInCacheException;
import util.lang.ConcreteReceiverGuard;

/**
 * @author Yann
 * @author Foutse
 * 
 * DO NOT EVER NEVER EVER USE THIS CLASS TO IMPLEMENT
 * CONVENIENCE METHODS. USE THE PRIMITIVES IN pom.primitives.
 * IN OTHER WORD, DO NOT TOUCH THIS CLASS.
 */
abstract class AbstractMetric {
	private final String name;

	/**
	 * Cache manager.
	 */
	protected final CacheManager cacheManager;

	/**
	 * ClassPrimitives that allows to extract primitives from the model.
	 * (The model is the analysed program (or a set of classes) and described by
	 * the metamodel.)
	 */
	protected final ClassPrimitives classPrimitives;

	/**
	 * MethodPrimitives that calculates primitives for a specified method.
	 */
	protected final MethodPrimitives methodPrimitives;

	/**
	 * Operators. Useful for operations on sets
	 */
	protected final Operators operators;

	/**
	 * @author Yann
	 * @since 24/08/2005
	 * 
	 * A private constructor to make sure classes that extends this class can't
	 * use an empty constructor
	 * 
	 * @param anAbstractModel
	 */
	protected AbstractMetric() {
		ConcreteReceiverGuard
			.getInstance()
			.checkCallingClassName(
				"sun.reflect.NativeConstructorAccessorImpl",
				"Please do not instantiate metrics directly to allow efficient caching, use the methods of \"pom.metrics.MetricsRepository\" to obtain metric instances.");

		this.name = Util.computeSimpleName(this.getClass().getName());

		// Yann 2013/07/10: Dependencies!
		// I remove any dependence of the primitives on IAbstractModel,
		// to have a much cleaner API that allows me to create metrics in
		// the user-interface without problems and to call them on the
		// current models as need :-)
		this.operators = Operators.getInstance();
		this.classPrimitives = ClassPrimitives.getInstance();
		this.methodPrimitives = MethodPrimitives.getInstance();
		this.cacheManager = CacheManager.getInstance();
	}
	public final double compute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		try {
			return this.cacheManager.retrieveUnaryMetricValue(
				(IMetric) this,
				anEntity);
		}
		catch (final NoSuchValueInCacheException e) {
			double result = this.concretelyCompute(anAbstractModel, anEntity);
			this.cacheManager.cacheUnaryMetricValue(
				(IMetric) this,
				anEntity,
				result);
			return result;
		}
	}
	public final double compute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity,
		final IFirstClassEntity anotherEntity) {

		try {
			return this.cacheManager.retrieveBinaryMetricValue(
				(IBinaryMetric) this,
				anEntity,
				anotherEntity);
		}
		catch (final NoSuchValueInCacheException e) {
			double result =
				this
					.concretelyCompute(anAbstractModel, anEntity, anotherEntity);
			this.cacheManager.cacheBinaryMetricValue(
				(IBinaryMetric) this,
				anEntity,
				anotherEntity,
				result);
			return result;
		}
	}
	protected abstract double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity);

	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity,
		final IFirstClassEntity anotherEntity) {

		// Useless for unary metrics.
		return 0;
	}
	protected IBinaryMetric getBinaryMetricInstance(final String metricName) {
		return (IBinaryMetric) MetricsRepository.getInstance().getMetric(
			metricName);
	}
	public String getName() {
		return this.name;
	}
	protected IUnaryMetric getUnaryMetricInstance(final String aMetricName) {
		return (IUnaryMetric) MetricsRepository.getInstance().getMetric(
			aMetricName);
	}
	public boolean isSymmetrical() {
		return false;
	}
}
