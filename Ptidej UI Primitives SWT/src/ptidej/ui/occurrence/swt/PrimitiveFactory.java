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
package ptidej.ui.occurrence.swt;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import ptidej.ui.RGB;
import ptidej.ui.kernel.ModelGraph;

public final class PrimitiveFactory extends
		ptidej.ui.primitive.swt.PrimitiveFactory implements
		ptidej.ui.occurrence.IOccurrencePrimitiveFactory {

	private static Map UniqueInstance = new HashMap(1);

	public static ptidej.ui.primitive.IPrimitiveFactory getInstance(
		final Device device,
		final GC graphics) {

		if (PrimitiveFactory.UniqueInstance.get(graphics) == null) {
			final PrimitiveFactory primitiveFactory = new PrimitiveFactory();
			primitiveFactory.setDevice(device);
			primitiveFactory.setGraphics(graphics);
			PrimitiveFactory.UniqueInstance.put(graphics, primitiveFactory);
		}

		return (ptidej.ui.primitive.IPrimitiveFactory) PrimitiveFactory.UniqueInstance
			.get(graphics);
	}

	public ptidej.ui.occurrence.IGroupOccurrenceModel createGroupOccurrenceModel(
		final Point position,
		final ModelGraph pattern,
		final String tip,
		final RGB color) {

		return new GroupOccurrenceModel(
			this.getDevice(),
			this.getGraphics(),
			position,
			pattern,
			tip,
			color);
	}
	public ptidej.ui.occurrence.IGroupOccurrenceTip createGroupOccurrenceTip(
		final Point position,
		final String tip,
		final RGB color) {

		return new GroupOccurrenceTip(
			this.getDevice(),
			this.getGraphics(),
			position,
			tip,
			color);
	}
	public ptidej.ui.occurrence.IGroupRectangleButton createGroupRectangleButton(
		final int percentageOfGray,
		final Point position,
		final Dimension dimension,
		final RGB color) {

		return new GroupRectangleButton(
			this.getDevice(),
			this.getGraphics(),
			percentageOfGray,
			position,
			dimension,
			color);
	}
}
