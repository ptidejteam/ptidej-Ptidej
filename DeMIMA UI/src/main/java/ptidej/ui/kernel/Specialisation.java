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

import ptidej.ui.primitive.IDoubleSquareLine;
import ptidej.ui.primitive.IPrimitive;
import ptidej.ui.primitive.IPrimitiveFactory;

public final class Specialisation extends AbstractInheritance {
	public Specialisation(
		final IPrimitiveFactory primitiveFactory,
		final Entity origin,
		final Entity target) {

		super(primitiveFactory, origin, target);
	}
	protected IPrimitive getLine() {
		final IPrimitive line =
			this.getPrimitiveFactory().createPlainDoubleSquareLine(
				this.getPosition(),
				this.dimensionWithoutIntermediaryPoints,
				this.getColor());

		((IDoubleSquareLine) line).setEdgeList(this.intermediaryPoints);
		((IDoubleSquareLine) line).setSplitter(this.splitter);

		return line;
	}
}
