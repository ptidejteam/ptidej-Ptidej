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
package padl.kernel.impl;

import padl.kernel.IStatement;
import padl.path.IConstants;

/**
 * @author tagmouty
 * @author Yann
 */
public class Statement extends Constituent implements IStatement {
	private static final long serialVersionUID = 6348055409249029367L;

	public Statement(char[] anID) {
		super(anID);
	}
	protected char getPathSymbol() {
		return IConstants.STATEMENT_SYMBOL;
	}
}
