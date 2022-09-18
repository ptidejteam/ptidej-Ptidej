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
package pom.util;

import java.util.HashMap;
import java.util.Map;
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IBinaryMetric;
import pom.metrics.IMetric;

public class CacheManager {
	private static CacheManager UniqueInstance;
	public static CacheManager getInstance() {
		if (CacheManager.UniqueInstance == null) {
			CacheManager.UniqueInstance = new CacheManager();
		}
		return CacheManager.UniqueInstance;
	}

	private final Map cache;
	private CacheManager() {
		this.cache = new HashMap();
	}
	public void cacheBinaryMetricValue(
		final IBinaryMetric aMetric,
		final IFirstClassEntity anEntity,
		final IFirstClassEntity anotherEntity,
		final double aValue) {

		if (aMetric.isSymmetrical()) {
			if (!this.isBinaryMetricValueInCache(
				aMetric,
				anotherEntity,
				anEntity)) {

				this.cache.put(
					this.getBinaryMetricKey(aMetric, anEntity, anotherEntity),
					Double.valueOf(aValue));
			}
		}
		else {
			//	this.cache.put(this.getBinaryMetricKey(
			//		aMetric,
			//		anEntity,
			//		anotherEntity), Double.valueOf(aValue));
		}
	}
	public void cachePrimitiveResult(
		final String aMethodName,
		final IConstituent aConstituent,
		final IConstituent anotherConstituent,
		final Object aValue) {

		//	this.cache.put(this.getPrimitiveKey(
		//		aMethodName,
		//		aConstituent,
		//		anotherConstituent), aValue);
	}
	public void cacheUnaryMetricValue(
		final IMetric aMetric,
		final IFirstClassEntity anEntity,
		final double aValue) {

		this.cache.put(this.getUnaryMetricKey(aMetric, anEntity), Double.valueOf(
			aValue));
	}
	private String getBinaryMetricKey(
		final IBinaryMetric aMetric,
		final IFirstClassEntity anEntity,
		final IFirstClassEntity anotherEntity) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(aMetric.getClass().getName());
		buffer.append(anEntity.getID());
		buffer.append(anotherEntity.getID());
		return buffer.toString();
	}
	private String getPrimitiveKey(
		final String aMethodName,
		final IConstituent aConstituent,
		final IConstituent anotherConstituent) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(aMethodName);
		buffer.append(aConstituent.getID());
		buffer.append(anotherConstituent.getID());
		return buffer.toString();
	}
	private String getUnaryMetricKey(
		final IMetric aMetric,
		final IFirstClassEntity anEntity) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(aMetric.getClass().getName());
		buffer.append(anEntity.getID());
		return buffer.toString();
	}
	//	public boolean isAsymetricalBinaryMetricValueInCache(
	//		final Class aMetric,
	//		final IEntity anEntity,
	//		final IEntity anotherEntity) {
	//
	//		// TODO: It would be better if the client of the 
	//		// cache did not have to know whether the metric 
	//		// is symmetrical or not using reflection to test 
	//		// isSymmetrical()
	//		
	//		return this.cache.containsKey(this.getBinaryMetricKey(
	//			aMetric,
	//			anEntity,
	//			anotherEntity));
	//	}
	public boolean isBinaryMetricValueInCache(
		final IBinaryMetric aMetric,
		final IFirstClassEntity anEntity,
		final IFirstClassEntity anotherEntity) {

		if (aMetric.isSymmetrical()) {
			return this.cache.containsKey(this.getBinaryMetricKey(
				aMetric,
				anEntity,
				anotherEntity))
					|| this.cache.containsKey(this.getBinaryMetricKey(
						aMetric,
						anotherEntity,
						anEntity));
		}
		else {
			return this.cache.containsKey(this.getBinaryMetricKey(
				aMetric,
				anEntity,
				anotherEntity));
		}
	}
	public boolean isUnaryMetricValueInCache(
		final IMetric aMetric,
		final IFirstClassEntity anEntity) {

		return this.cache
			.containsKey(this.getUnaryMetricKey(aMetric, anEntity));
	}
	public double retrieveBinaryMetricValue(
		final IBinaryMetric aMetric,
		final IFirstClassEntity anEntity,
		final IFirstClassEntity anotherEntity) {

		if (aMetric.isSymmetrical()) {
			try {
				return this.retrieveMetricValueFromCache(this
					.getBinaryMetricKey(aMetric, anEntity, anotherEntity));
			}
			catch (final NoSuchValueInCacheException e) {
				return this.retrieveMetricValueFromCache(this
					.getBinaryMetricKey(aMetric, anotherEntity, anEntity));
			}
		}
		else {
			return this.retrieveMetricValueFromCache(this.getBinaryMetricKey(
				aMetric,
				anEntity,
				anotherEntity));
		}
	}
	private double retrieveMetricValueFromCache(final String aKey) {
		final Double value = (Double) this.cache.get(aKey);
		if (value != null) {
			return value.doubleValue();
		}
		else {
			throw new NoSuchValueInCacheException(aKey);
		}
	}
	public Object retrievePrimitiveResult(
		final String aMethodName,
		final IConstituent aConstituent,
		final IConstituent anotherConstituent) {

		return this.retrievePrimitiveResultFromCache(this.getPrimitiveKey(
			aMethodName,
			aConstituent,
			anotherConstituent));
	}
	private Object retrievePrimitiveResultFromCache(final String aKey) {
		final Object value = this.cache.get(aKey);
		if (value != null) {
			return value;
		}
		else {
			throw new NoSuchValueInCacheException(aKey);
		}
	}
	public double retrieveUnaryMetricValue(
		final IMetric aMetric,
		final IFirstClassEntity anEntity) {

		return this.retrieveMetricValueFromCache(this.getUnaryMetricKey(
			aMetric,
			anEntity));
	}
}
