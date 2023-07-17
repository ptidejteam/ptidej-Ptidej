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

import padl.kernel.IFactory;
import padl.kernel.IStatement;
import padl.kernel.impl.Factory;
import padl.kernel.impl.Statement;
import padl.statement.kernel.IIfInstruction;
import padl.statement.kernel.IStatementFactory;
import padl.statement.kernel.ISwitchInstruction;

/**
 * @author Yousra Tagmouti
 * @author Yann-Gaël Guéhéneuc
 */
public class StatementFactory extends Factory implements IStatementFactory {
	private static final long serialVersionUID = -7857386029686909308L;

	private static IFactory UniqueInstance;
	public static IFactory getInstance() {
		if (StatementFactory.UniqueInstance == null) {
			StatementFactory.UniqueInstance = new StatementFactory();
		}
		return StatementFactory.UniqueInstance;
	}

	private StatementFactory() {
	}
	public IIfInstruction createIfInstruction(final char[] anExpression) {
		return new IfInstruction(anExpression);
	}
	public IStatement createStatement(final char[] aName) {
		return new Statement(aName);
	}
	public ISwitchInstruction createSwitchInstruction(
		final char[] anExpression,
		final int aNumberOfCases) {

		return new SwitchInstruction(anExpression, aNumberOfCases);
	}
}
