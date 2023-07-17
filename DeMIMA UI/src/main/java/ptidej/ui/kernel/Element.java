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

import java.awt.Dimension;
import java.awt.Point;
import ptidej.ui.primitive.IPrimitiveFactory;

public abstract class Element extends Constituent {
	public Element(final IPrimitiveFactory primitiveFactory) {
		super(primitiveFactory);
	}
	protected void setDimensionSpecifics(final Dimension dimension) {
		// Nothing to do.
	}
	protected void setPositionSpecifics(final Point position) {
		// Nothing to do.
	}
	public final void setSelectedSpecifics(final boolean isSelected) {
		// Nothing to do.
	}
	protected void setVisibleElementsSpecifics(final int visibility) {
		// Nothing to do.
	}
}
