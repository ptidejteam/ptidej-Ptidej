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
package padl.cpp.kernel.impl;

import padl.cpp.kernel.IGlobalFunctionGhost;

class GlobalFunctionGhost extends GlobalFunction implements
		IGlobalFunctionGhost {

	private static final long serialVersionUID = -3190748719695315022L;

	public GlobalFunctionGhost(char[] anID, char[] aName) {
		super(anID, aName);

		// Yann 2014/04/17: Ghost functions are ghosts...
		// ... but also functions, so I must set their
		// lines of code to the empty array to make sure
		// metrics and others work.
		this.setCodeLines(new String[0]);
	}
}
