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
package sad.codesmell.property.impl;

import java.util.Map;

import sad.codesmell.property.ICodeSmellProperty;

/**
 * Hold information about a metric
 *
 * @author Pierre Leduc
 * @version 1.0
 * @since 2006/05/29
 */
public class MetricProperty implements ICodeSmellProperty {
	final private String metricName;
	final private double metricValue;
	final private Map metricThreshold;
	final private double fuzziness;

	public MetricProperty(
		final String metricName,
		final double value,
		final Map threshold) {

		this.metricName = metricName;
		this.metricValue = value;
		this.metricThreshold = threshold;
		this.fuzziness = 100;
	}
	public MetricProperty(
		final String metricName,
		final double value,
		final Map threshold,
		final double fuzziness) {

		this.metricName = metricName;
		this.metricValue = value;
		this.metricThreshold = threshold;
		this.fuzziness = fuzziness;
	}
	public String getMetricName() {
		return this.metricName;
	}
	public Map getMetricThreshold() {
		return this.metricThreshold;
	}
	public double getMetricValue() {
		return this.metricValue;
	}
	public String toString(
		final int count,
		final int propertyCount,
		final String codesmellName) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(
			"\n"
				+ count
				+ "."
				+ (int) this.fuzziness
				+ "."
				+ codesmellName
				+ "."
				+ this.metricName
				+ "-"
				+ propertyCount
				+ " = "
				+ this.metricValue);
		if (this.metricThreshold != null) {
			buffer.append(
				"\n"
					+ count
					+ "."
					+ (int) this.fuzziness
					+ "."
					+ codesmellName
					+ "."
					+ this.metricName
					+ "_MaxBound-"
					+ propertyCount
					+ " = "
					+ this.metricThreshold);
		}
		return buffer.toString();
	}
}
