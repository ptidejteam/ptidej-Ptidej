/*
 * $Id: ColorDrawer.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import java.awt.Color;

public class ColorDrawer {
	static final Color colors[] = { Color.black, Color.red, Color.green,
			Color.blue, Color.cyan, Color.magenta, Color.yellow, Color.gray };
	Color getColor(
		final float xmin,
		final float ymin,
		final float xmax,
		final float ymax,
		final int node,
		final int depth) {
		return ColorDrawer.colors[depth % ColorDrawer.colors.length];
	}
}
