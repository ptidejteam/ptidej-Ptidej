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

import sad.rule.creator.model.IConstituent;
import sad.rule.creator.model.IOperator;

/**
 * @author Pierre Leduc
 */
public class Operator extends Constituent implements IOperator {
	private final int operatorType;
	private IConstituent operand1;
	private IConstituent operand2;

	public Operator(
		final String anID,
		final int anOperatorType,
		final IConstituent anOperand1,
		final IConstituent anOperand2) {
		super(anID);
		this.operatorType = anOperatorType;
		this.operand1 = anOperand1;
		this.operand2 = anOperand2;
	}

	/**
	 * @return Returns the operand 1.
	 */
	public IConstituent getOperand1() {
		return this.operand1;
	}

	/**
	 * @return Returns the operand 2.
	 */
	public IConstituent getOperand2() {
		return this.operand2;
	}

	/**
	 * @return Returns the operator type.
	 */
	public int getOperatorType() {
		return this.operatorType;
	}

	public void setOperand1(final IConstituent aConstituent) {
		this.operand1 = aConstituent;
	}

	public void setOperand2(final IConstituent aConstituent) {
		this.operand2 = aConstituent;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.getClass().getName());
		buffer.append("\nName: ");
		buffer.append(this.getID());
		buffer.append("\nOperator type: ");
		buffer.append(this.getOperatorType());
		buffer.append("\nOperand #1: \n");
		buffer.append(this.getOperand1().getID());
		buffer.append("\n\nOperand #2: \n");
		buffer.append(this.getOperand2().getID());

		return buffer.toString();
	}
}
