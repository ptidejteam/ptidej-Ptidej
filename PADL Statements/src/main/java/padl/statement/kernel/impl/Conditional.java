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

import padl.kernel.IMethod;
import padl.kernel.exception.ModelDeclarationException;
import padl.statement.kernel.IConditional;

abstract class Conditional extends Statement implements IConditional {
	private static final long serialVersionUID = 2905665981477351455L;

	private IMethod condition;
	public Conditional(final char[] anID) {
		super(anID);
	}
	public void setCondition(final IMethod aBooleanMethod) {
		if (aBooleanMethod.getReturnType().equals("boolean")) {
			this.condition = aBooleanMethod;
		}
		else {
			throw new ModelDeclarationException(
				"Condition method must return a boolean value");
		}
	}
	public IMethod getCondition() {
		return this.condition;
	}
}
