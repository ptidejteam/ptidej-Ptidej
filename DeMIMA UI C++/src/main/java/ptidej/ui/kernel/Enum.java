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

import padl.cpp.kernel.IEnum;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

public final class Enum extends Entity {
	public Enum(
		final IPrimitiveFactory aPrimitiveFactory,
		final Builder aBuilder,
		final IEnum anEnum) {

		super(aPrimitiveFactory, aBuilder, anEnum);
	}
	protected String getStereotype() {
		return "<<enum>>\n";
	}
}
