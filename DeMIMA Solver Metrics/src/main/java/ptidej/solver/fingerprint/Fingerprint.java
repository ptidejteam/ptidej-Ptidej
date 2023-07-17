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
package ptidej.solver.fingerprint;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import util.multilingual.MultilingualManager;

/**
 * @author Jean-Yves Guyomarc'h
 * @since  2004/10/14
 */
public class Fingerprint {
	// OPERATORS
	public static final int GT = 0;
	public static final int LT = 1;
	public static final int EQ = 2;
	public static final int GEQ = 3;
	public static final int LEQ = 4;

	private final String metricName;
	private final int operator;
	private final double value;

	public Fingerprint(
		final String metricName,
		final int operator,
		final double value) {

		this.metricName = metricName;
		this.operator = operator;
		this.value = value;
	}
	public boolean compute(final double result) {
		if (this.operator == Fingerprint.GT) {
			return result > this.value;
		}
		else if (this.operator == Fingerprint.LT) {
			return result < this.value;
		}
		else if (this.operator == Fingerprint.EQ) {
			return result == this.value;
		}
		else if (this.operator == Fingerprint.GEQ) {
			return result >= this.value;
		}
		else if (this.operator == Fingerprint.LEQ) {
			return result <= this.value;
		}
		throw new RuntimeException(new IllegalArgumentException(
			MultilingualManager.getString(
				"IAException_UNKNOWN_OP",
				Fingerprint.class)));
	}
	public boolean compute(
		final MetricsRepository metricRepository,
		final IAbstractLevelModel anAbstractLevelModel,
		final IFirstClassEntity firstClassEntity) {

		return this.compute(((IUnaryMetric) metricRepository
			.getMetric(this.metricName)).compute(
			anAbstractLevelModel,
			firstClassEntity));
	}
	public String getMetricsName() {
		return this.metricName;
	}
	public int getOperator() {
		return this.operator;
	}
	public double getValue() {
		return this.value;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer(this.metricName);

		if (this.operator == Fingerprint.GT) {
			buffer.append(" > ");
		}
		else if (this.operator == Fingerprint.LT) {
			buffer.append(" < ");
		}
		else if (this.operator == Fingerprint.EQ) {
			buffer.append(" == ");
		}
		else if (this.operator == Fingerprint.GEQ) {
			buffer.append(" >= ");
		}
		else if (this.operator == Fingerprint.LEQ) {
			buffer.append(" <= ");
		}
		buffer.append(this.value);

		return buffer.toString();
	}
}
