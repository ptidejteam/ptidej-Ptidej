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
package ptidej.ui.primitive.awt;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import ptidej.ui.RGB;
import ptidej.ui.primitive.IArrowSymbol;
import ptidej.ui.primitive.IButton;
import ptidej.ui.primitive.IDottedLine;
import ptidej.ui.primitive.IDottedTriangle;
import ptidej.ui.primitive.IDoubleSquareLine;
import ptidej.ui.primitive.IInheritanceSymbol;
import ptidej.ui.primitive.ILabel;
import ptidej.ui.primitive.ILine;
import ptidej.ui.primitive.IPlainSquareLine;
import ptidej.ui.primitive.IPlainTriangle;
import ptidej.ui.primitive.IPrimitiveFactory;
import ptidej.ui.primitive.IRectangle;

public class PrimitiveFactory implements IPrimitiveFactory {
	protected static PrimitiveFactory UniqueInstance;
	public static IPrimitiveFactory getInstance() {
		if (PrimitiveFactory.UniqueInstance == null) {
			PrimitiveFactory.UniqueInstance = new PrimitiveFactory();
		}

		return PrimitiveFactory.UniqueInstance;
	}

	private Graphics graphics;

	public final ptidej.ui.primitive.IAggregationSymbol createAggregationSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new AggregationSymbol(this, origin, dimension, color);
	}
	public final IArrowSymbol createArrowSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new ArrowSymbol(this, origin, dimension, color);
	}
	public final ptidej.ui.primitive.IAssociationSymbol createAssociationSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new AssociationSymbol(this, origin, dimension, color);
	}
	public final IButton createButton(
		final String label,
		final Point position,
		final boolean centerText,
		final RGB color) {

		return new Button(this, label, position, centerText, color);
	}
	public final IButton createButton(
		final String label,
		final Point position,
		final Dimension dimension,
		final boolean centerText,
		final RGB color) {

		return new Button(this, label, position, dimension, centerText, color);
	}
	public final ptidej.ui.primitive.ICompositionSymbol createCompositionSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new CompositionSymbol(this, origin, dimension, color);
	}
	/**
	 * @author Mohamed Kahla
	 * @since 17/05/2006
	 * @version 17052006H1102
	 * build The Dotted Double SquareLine used for 
	 * Implementations Links
	 */
	public final IDoubleSquareLine createDottedDoubleSquareLine(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new DottedDoubleSquareLine(this, origin, dimension, color);
	}
	public final IDottedLine createDottedLine(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new DottedLine(this, origin, dimension, color);
	}
	public final ptidej.ui.primitive.IDottedSquareLine createDottedSquareLine(
		final Point origin,
		final Dimension dimension,
		final RGB color) {
		// 
		return new DottedSquareLine(this, origin, dimension, color);
	}
	public final IDottedTriangle createDottedTriangle(
		final Point origin,
		final int direction,
		final RGB color) {

		return new DottedTriangle(this, origin, direction, color);
	}
	public IInheritanceSymbol createInheritanceSymbol(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new InheritanceSymbol(this, origin, dimension, color);
	}
	public final ILabel createLabel(
		final String label,
		final Point position,
		final Dimension dimension,
		final RGB color) {

		return new Label(this, label, position, dimension, color);
	}
	public final ILine createLine(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new Line(this, origin, dimension, color);
	}
	/**
	 * @author Mohamed Kahla
	 * @since 17/05/2006
	 * @version 17052006H1056
	 * build The Plain Double SquareLine used for 
	 * Specialisation Links
	 */
	public IDoubleSquareLine createPlainDoubleSquareLine(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new PlainDoubleSquareLine(this, origin, dimension, color);
	}
	public final IPlainSquareLine createPlainSquareLine(
		final Point origin,
		final Dimension dimension,
		final RGB color) {
		// 
		return new PlainSquareLine(this, origin, dimension, color);
	}
	public final IPlainTriangle createPlainTriangle(
		final Point origin,
		final int direction,
		final RGB color) {

		return new PlainTriangle(this, origin, direction, color);
	}
	public final IRectangle createRectangle(
		final Point origin,
		final Dimension dimension,
		final RGB color) {

		return new Rectangle(this, origin, dimension, color);
	}
	public final Graphics getGraphics() {
		return this.graphics;
	}
	public final void setGraphics(final Graphics graphics) {
		this.graphics = graphics;
	}
}
