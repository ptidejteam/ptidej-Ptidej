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
package ptidej.ui.primitive;

import java.awt.Dimension;
import java.awt.Point;
import ptidej.ui.RGB;

public interface IPrimitiveFactory {
	IAggregationSymbol createAggregationSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	IArrowSymbol createArrowSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	IAssociationSymbol createAssociationSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	IButton createButton(
		final String label,
		final Point position,
		final boolean centerText,
		final RGB color);
	IButton createButton(
		final String label,
		final Point position,
		final Dimension dimension,
		final boolean centerText,
		final RGB color);
	ICompositionSymbol createCompositionSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	// Mohamed Kahla 06/05/17
	IDoubleSquareLine createDottedDoubleSquareLine(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	IDottedLine createDottedLine(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	IDottedSquareLine createDottedSquareLine(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	IDottedTriangle createDottedTriangle(
		final Point origin,
		final int direction,
		final RGB color);
	IInheritanceSymbol createInheritanceSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	ILabel createLabel(
		final String label,
		final Point position,
		final Dimension dimension,
		final RGB color);
	ILine createLine(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	// Mohamed Kahla 06/05/17
	IDoubleSquareLine createPlainDoubleSquareLine(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	IPlainSquareLine createPlainSquareLine(
		final Point origin,
		final Dimension dimension,
		final RGB color);
	IPlainTriangle createPlainTriangle(
		final Point origin,
		final int direction,
		final RGB color);
	IRectangle createRectangle(
		final Point origin,
		final Dimension dimension,
		final RGB color);
}
