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
package ptidej.ui;

import java.awt.Dimension;
import java.awt.Point;

import ptidej.ui.event.MouseEvent;

public interface IDrawable {
	void build();
	Dimension getDimension();
	String getName();
	Point getPosition();
	void paint(final int xOffset, final int yOffset);
	boolean processMouseEvent(final MouseEvent aMouseEvent);
	String toString();
}
