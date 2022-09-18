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
package padl.statement.kernel.impl;

import padl.statement.kernel.ISwitchInstruction;

class SwitchInstruction extends Conditional implements ISwitchInstruction {
	private static final long serialVersionUID = 7691868702663850443L;

	private final int numberOfCases;

	public SwitchInstruction(final char[] anExpression, final int aNumberOfCases) {
		super(anExpression);
		this.numberOfCases = aNumberOfCases;
	}
	public int getNumberOfCases() {
		return this.numberOfCases;
	}
}
