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
/**
 * 
 */
package ptidej.ui.awt.primitive;

import java.awt.Dimension;
import java.awt.Point;
import ptidej.ui.RGB;
import ptidej.ui.kernel.IntermediaryPoint;
import ptidej.ui.primitive.IDoubleSquareLine;

/**
 * @author Mohamed Kahla
 * @date 	16-05-2006
 */
public abstract class DoubleSquareLine extends SquareLine implements
		IDoubleSquareLine {

	protected IntermediaryPoint[] intermediaryPoints;

	protected DoubleSquareLine(
		final PrimitiveFactory primitiveFactory,
		final Point origin,
		final Dimension dimension,
		final RGB color) {
		super(primitiveFactory, origin, dimension, color);
	}
	// 06-07-2006
	public final void setEdgeList(
		final IntermediaryPoint[] someIntermediaryPoints) {

		this.intermediaryPoints = someIntermediaryPoints;
	}
	// 18-05-2006
	public final void setSplitter(final int split) {
		//	this.splitter = split;
	}
}
