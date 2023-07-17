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
import java.awt.Toolkit;

public interface Constants {
	// TODO: The rounding from float to int makes necessary to use
	// this constant: improve the calculations to remove it.
	int FACTOR = 100000;
	int ROUNDING = 2;
	int DOT_LENGTH = 3;
	int TAB_LENGTH = 4;

	Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

	Point NULL_POSITION = new Point(0, 0);
	Point MAX_POSITION = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

	Dimension NULL_DIMENSION = new Dimension(0, 0);
	Dimension MAX_DIMENSION =
		new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

	Point DEFAULT_POSITION = new Point(10, 10);
	Dimension DEFAULT_DIMENSION = new Dimension(10, 10);

	RGB BACKGROUND_COLOR = new RGB(255, 255, 255);
	RGB FOREGROUND_COLOR = new RGB(0, 0, 0);

	Dimension CANVAS_INNER_GAP = new Dimension(10, 10);
	Dimension GAP_BETWEEN_ENTITIES = new Dimension(25, 25);

	int INHERITANCE_SYMBOL_GAP = 8;
	Dimension INHERITANCE_SYMBOL_DIMENSION = new Dimension(10, 10);
	Dimension AAC_SYMBOL_DIMENSION = new Dimension(8, 16);
	Dimension ARROW_SYMBOL_DIMENSION = new Dimension(8, 16);

	RGB IMPLEMENTATION_ELEMENT_DISPLAY_COLOR = new RGB(127, 127, 127);
	RGB SPECIALIZATION_ELEMENT_DISPLAY_COLOR = new RGB(0, 0, 0);
	RGB GHOST_ENTITY_DISPLAY_COLOR = new RGB(192, 192, 192);

	Dimension SOLUTION_HANDLE_DIMENSION = new Dimension(15, 15);
	Dimension SOLUTION_FRAME_GAP = new Dimension(5, 5);
	Dimension TIP_GAP = new Dimension(10, 7);
	RGB TIP_BACKGROUND_COLOR = new RGB(255, 255, 215);
	double MINIMUM_PERCENTAGE = 15 / 100d;

	int FONT_DESCENT = 4;
	int FONT_HEIGHT = 11;
	int FONT_WIDTH = 6;
	int FONT_HEIGHT_SWT_SHIFT = 2;
}
