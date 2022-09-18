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
package ptidej.ui.kernel;

import padl.cpp.kernel.IGlobalFunction;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Yann
 * @since  2013/05/23
 */

// Ward 2004/08/19: Hierarchy level
// For now GlobalField is created at the same level as Class.
// So, it has all the inheritance properties.
// Implementation to be checked ...
public final class GlobalFunction extends Entity {
	public GlobalFunction(
		final IPrimitiveFactory aPrimitiveFactory,
		final Builder aBuilder,
		final IGlobalFunction aGlobalFunction) {

		super(aPrimitiveFactory, aBuilder, aGlobalFunction);
	}
	protected String getStereotype() {
		return "<<global function>>\n";
	}
}
