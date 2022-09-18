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
package sad.rule.creator.model.impl;

import sad.rule.creator.model.IMetric;

/**
 * @author Pierre Leduc
 * @modified Naouel 2007/10/18 : add comparison operators
 */
public class Metric extends Attribute implements IMetric {
	private int value_ordi;
	private double value;
	private int comparisonOperator;
	private double fuzziness;

	private final int operator;
	private final IMetric operand1;
	private final IMetric operand2;

	public Metric(
		final String aName,
		final double aValue,
		final double fuzziness) {
		super(aName);
		this.value = aValue;
		this.value_ordi = 0;
		this.fuzziness = fuzziness;

		this.operator = -1;
		this.operand1 = null;
		this.operand2 = null;
	}

	public Metric(
		final String aName,
		final double aValue,
		final int aComparisonOp,
		final double fuzziness) {
		super(aName);
		this.value = aValue;
		this.comparisonOperator = aComparisonOp;
		this.value_ordi = 0;
		this.fuzziness = fuzziness;

		this.operator = -1;
		this.operand1 = null;
		this.operand2 = null;
	}

	public Metric(
		final String aName,
		final int aValue_ordi,
		final double fuzziness) {
		super(aName);
		this.value_ordi = aValue_ordi;
		this.value = 0;
		this.fuzziness = fuzziness;

		this.operator = -1;
		this.operand1 = null;
		this.operand2 = null;
	}

	public Metric(
		final String aName,
		final int anOperator,
		final IMetric anOperand1,
		final IMetric anOperand2) {
		super(aName);
		this.value_ordi = 0;
		this.value = 0;
		this.fuzziness = 0;

		this.operator = anOperator;
		this.operand1 = anOperand1;
		this.operand2 = anOperand2;
	}

	public int getComparisonOperator() {
		return this.comparisonOperator;
	}

	public double getFuzziness() {
		return this.fuzziness;
	}

	public IMetric getMetric1() {
		return this.operand1;
	}

	public IMetric getMetric2() {
		return this.operand2;
	}

	public double getNumericValue() {
		return this.value;
	}

	public int getOperator() {
		return this.operator;
	}

	public int getOrdinalValue() {
		return this.value_ordi;
	}

	public void setComparisonOperator(final int value) {
		this.comparisonOperator = value;
	}

	public void setFuzziness(final double value) {
		this.fuzziness = value;
	}

	public void setNumericValue(final double value) {
		this.value = value;
	}

	public void setOrdinalValue(final int value) {
		this.value_ordi = value;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.getClass().getName());
		buffer.append("\nName: ");
		buffer.append(this.getID());
		buffer.append("\nOrdinal value: ");
		buffer.append(this.getOrdinalValue());
		buffer.append("\nValue: ");
		buffer.append(this.getNumericValue());
		buffer.append("\nComparisonOperator: ");
		buffer.append(this.getComparisonOperator());
		buffer.append("\nFuzziness: ");
		buffer.append(this.getNumericValue());

		return buffer.toString();
	}
}
