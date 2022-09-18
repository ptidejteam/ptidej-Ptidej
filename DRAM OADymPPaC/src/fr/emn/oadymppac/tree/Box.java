/*
 * $Id: Box.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

public class Box {
	public float xmin;
	public float ymin;
	public float xmax;
	public float ymax;

	Box() {
	}
	Box(final Box other) {
		this.xmin = other.xmin;
		this.ymin = other.ymin;
		this.xmax = other.xmax;
		this.ymax = other.ymax;
	}
	Box(final float xmin, final float ymin, final float xmax, final float ymax) {
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
	}

	float getHeight() {
		return this.ymax - this.ymin;
	}
	float getWidth() {
		return this.xmax - this.xmin;
	}
}
