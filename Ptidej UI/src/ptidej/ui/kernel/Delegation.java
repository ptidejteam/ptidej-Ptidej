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

import padl.kernel.IDelegatingMethod;
import ptidej.ui.IVisibility;
import ptidej.ui.primitive.IPrimitive;
import ptidej.ui.primitive.IPrimitiveFactory;
import ptidej.ui.primitive.ISymbol;

public final class Delegation extends Relationship {
	private IDelegatingMethod pDelegation;

	public Delegation(
		IPrimitiveFactory primitiveFactory,
		Entity origin,
		Entity target,
		IDelegatingMethod pDelegation) {

		super(primitiveFactory, 0, origin, target);
		this.pDelegation = pDelegation;
	}
	protected IPrimitive getLine() {
		return this.getPrimitiveFactory().createDottedLine(
			this.getPosition(),
			this.getDimension(),
			this.getColor());
	}
	protected ISymbol getOriginSymbol() {
		return null;
	}
	protected String getSymbol() {
		return "-()->" + String.valueOf(this.pDelegation.getName());
	}
	protected ISymbol getTargetSymbol() {
		return this.getPrimitiveFactory().createArrowSymbol(
			this.findIntersectionPointWithTarget(),
			this.getDimension(),
			this.getColor());
	}
	public int getVisibilityDisplay() {
		return IVisibility.DELEGATION_DISPLAY_ELEMENTS;
	}
	public int getVisibilityName() {
		return IVisibility.DELEGATION_NAMES;
	}
}
