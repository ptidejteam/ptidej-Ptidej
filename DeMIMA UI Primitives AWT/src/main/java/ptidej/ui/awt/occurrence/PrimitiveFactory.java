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
package ptidej.ui.awt.occurrence;

import java.awt.Dimension;
import java.awt.Point;
import ptidej.ui.RGB;
import ptidej.ui.kernel.ModelGraph;
import ptidej.ui.occurrence.IGroupOccurrenceModel;
import ptidej.ui.occurrence.IGroupOccurrenceTip;
import ptidej.ui.occurrence.IGroupRectangleButton;
import ptidej.ui.primitive.IPrimitiveFactory;

public final class PrimitiveFactory extends
		ptidej.ui.awt.primitive.PrimitiveFactory implements
		ptidej.ui.occurrence.IOccurrencePrimitiveFactory {

	public static IPrimitiveFactory getInstance() {
		if (ptidej.ui.awt.primitive.PrimitiveFactory.UniqueInstance == null) {
			ptidej.ui.awt.primitive.PrimitiveFactory.UniqueInstance =
				new PrimitiveFactory();
		}

		return ptidej.ui.awt.primitive.PrimitiveFactory.UniqueInstance;
	}
	public IGroupOccurrenceModel createGroupOccurrenceModel(
		final Point position,
		final ModelGraph pattern,
		final String tip,
		final RGB color) {

		return new GroupOccurrenceModel(this, position, pattern, tip, color);
	}
	public IGroupOccurrenceTip createGroupOccurrenceTip(
		final Point position,
		final String tip,
		final RGB color) {

		return new GroupOccurrenceTip(this, position, tip, color);
	}
	public IGroupRectangleButton createGroupRectangleButton(
		final int percentageOfGray,
		final Point position,
		final Dimension dimension,
		final RGB color) {

		return new GroupRectangleButton(
			this,
			percentageOfGray,
			position,
			dimension,
			color);
	}
}
