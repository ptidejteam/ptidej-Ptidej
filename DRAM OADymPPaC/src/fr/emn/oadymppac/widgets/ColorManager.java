/*
 * $Id: ColorManager.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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
package fr.emn.oadymppac.widgets;

import java.awt.Color;
import fr.emn.oadymppac.Identifiable;
import fr.emn.oadymppac.Solver;

public class ColorManager {
	Solver solver;

	Color colors[];

	//public static final String PROPERTY_NAME = "varColorManager";

	protected ColorManager() {
	}

	protected ColorManager(final Solver solver) {
		this(solver, 6);
	}

	protected ColorManager(final Solver solver, final int max_colors) {
		this.solver = solver;
		this.allocColors(max_colors);
	}

	public void allocColors(final int max_colors) {
		this.colors = new Color[max_colors];
		for (int i = 0; i < max_colors; i++) {
			this.colors[i] = Color.getHSBColor((float) i / max_colors, 1, 1);
		}
	}

	public Color getColor(final Identifiable i) {
		return this.colors[i.getId() % this.colors.length];
	}

	public Color getColor(final int i) {
		return this.colors[i % this.colors.length];
	}

	public Color getColorByClass(final Object o) {
		int h = o.getClass().hashCode();
		h /= 4;
		return this.colors[h % this.colors.length];
	}
}
