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

import padl.kernel.IFirstClassEntity;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 */
public final class Ghost extends Entity {
	public Ghost(
		final IPrimitiveFactory aPrimitiveFactory,
		final Builder aBuilder,
		final IFirstClassEntity anEntity) {

		super(aPrimitiveFactory, aBuilder, anEntity);
	}
	protected String getStereotype() {
		return "";
	}
}
