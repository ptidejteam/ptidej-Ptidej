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

import ptidej.ui.IVisibility;
import ptidej.ui.primitive.IPrimitiveFactory;
import ptidej.ui.primitive.ISymbol;

public final class ContainerComposition extends Association {
	public ContainerComposition(
		final IPrimitiveFactory primitiveFactory,
		final String name,
		final int cardinality,
		final Entity origin,
		final Entity target) {

		super(primitiveFactory, name, cardinality, origin, target);
	}
	protected ISymbol getOriginSymbol() {
		return this.getPrimitiveFactory().createCompositionSymbol(
			this.findIntersectionPointWithOrigin(),
			this.getDimension(),
			this.getColor());
	}
	protected String getSymbol() {
		return "<#>->";
	}
	public int getVisibilityDisplay() {
		return IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS;
	}
	public int getVisibilityName() {
		return IVisibility.CONTAINER_COMPOSITION_NAMES;
	}
}
