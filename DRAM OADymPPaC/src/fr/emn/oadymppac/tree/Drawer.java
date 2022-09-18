/*
 * $Id: Drawer.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

public interface Drawer {
	public boolean beginBox(
		float xmin,
		float ymin,
		float xmax,
		float ymax,
		int node,
		int depth);
	public Box drawBorder(
		float xmin,
		float ymin,
		float xmax,
		float ymax,
		int node,
		int depth,
		Box box);

	public void drawBox(
		float xmin,
		float ymin,
		float xmax,
		float ymax,
		int node,
		int depth);
	public void endBox(
		float xmin,
		float ymin,
		float xmax,
		float ymax,
		int node,
		int depth);
	public void finish();
	public Box removeBorder(
		float xmin,
		float ymin,
		float xmax,
		float ymax,
		int node,
		int depth,
		Box box);
	public void start();
}
