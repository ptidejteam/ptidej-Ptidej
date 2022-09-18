/*
 * $Id: GlassPane.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;
import javax.swing.JComponent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Mohammad Ghoniem
 * @version $Revision : $
 */

public class GlassPane extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7880136765565155927L;

	Point origin;

	Container container;

	Vector positions;

	public GlassPane(final Container pane) {
		this.container = pane;
		this.setVisible(true);
		this.origin = new Point();
		this.positions = new Vector();
	}

	public Point getOrigin() {
		return this.origin;
	}

	public Vector getPositions() {
		return this.positions;
	}

	/**
	 * Draws a line between the picked object and all its related
	 * representations indicated in the <code>Vector positions</code>.
	 */
	public void paint(final Graphics g) {
		g.setColor(Color.black);
		Point current;
		for (int i = 0; i < this.positions.size(); i++) {
			current = (Point) this.positions.elementAt(i);
			g.drawLine(
				(int) this.origin.getX(),
				(int) this.origin.getY(),
				(int) current.getX(),
				(int) current.getY());
		}
	}

	public void setOrigin(final int x, final int y) {
		this.origin.x = x;
		this.origin.y = y;
	}

	public void setOrigin(final Point p) {
		this.origin = p;
	}

	public void setPositions(final Vector v) {
		this.positions = v;
	}
}