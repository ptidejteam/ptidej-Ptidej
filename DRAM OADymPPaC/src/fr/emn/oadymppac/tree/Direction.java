/*
 * $Id: Direction.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */

package fr.emn.oadymppac.tree;

public class Direction {
	static public final short LEFT_TO_RIGHT = 0;
	static public final short RIGHT_TO_LEFT = 1;
	static public final short TOP_TO_BOTTOM = 2;
	static public final short BOTTOM_TO_TOP = 3;

	static public short flip(final short d) {
		switch (d) {
			case LEFT_TO_RIGHT :
				return Direction.TOP_TO_BOTTOM;
			case RIGHT_TO_LEFT :
				return Direction.BOTTOM_TO_TOP;
			case TOP_TO_BOTTOM :
				return Direction.LEFT_TO_RIGHT;
			case BOTTOM_TO_TOP :
				return Direction.RIGHT_TO_LEFT;
		}
		return Direction.LEFT_TO_RIGHT;
	}
}
