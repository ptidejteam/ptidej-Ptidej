/*
 * $Id: JTickBars.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.JComponent;

/**
 * The <code>JTickBars</code> component display a list of bar objects
 * over time.   It can be used to monitor memory use over time or
 * other kind of evolving value.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.2 $
 */
public class JTickBars extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5525809966691440674L;

	/**
	 * The label
	 */
	String label;

	/**
	 * The list of tick bars.
	 */
	LinkedList tickBars = new LinkedList();
	/**
	 * Maximum number of tick bars kept in memory.
	 */
	int maxHistory;

	/**
	 * Default constructor
	 *
	 * @param label The displayed label
	 */
	public JTickBars(final String label) {
		this(label, 1000);
	}

	/**
	 * Constructor with a limit on the number of bars kept in history.
	 *
	 * @param label The displayed label
	 * @param max maximum number of tick bars kept in memory.
	 */
	public JTickBars(final String label, final int max) {
		this.label = label;
		this.maxHistory = max;
	}

	public void add(final double min, final double max) {
		this.add(new DefaultTickBar(min, max));
	}

	public synchronized void add(final TickBar b) {
		this.tickBars.add(b);
		this.clearHistory(this.maxHistory);
	}

	public void clearHistory(final int size) {
		while (this.tickBars.size() > size) {
			this.tickBars.removeFirst();
		}
	}

	/**
	 * Get the value of label.
	 * @return value of label.
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Get the value of maxHistory.
	 * @return value of maxHistory.
	 */
	public int getMaxHistory() {
		return this.maxHistory;
	}
	public synchronized void paintComponent(final Graphics g) {
		super.paintComponent(g);
		final int w = this.getWidth();
		int h = this.getHeight();
		int y = 0;

		if (this.label != null) {
			g.setFont(this.getFont());
			g.setColor(this.getForeground());
			final FontMetrics fm = g.getFontMetrics();
			g.drawString(this.label, 0, fm.getAscent());
			y = fm.getHeight();
			h -= y;
		}

		if (this.tickBars.size() == 0) {
			return;
		}
		final int first =
			this.tickBars.size() > w ? w - this.tickBars.size() : 0;

		ListIterator iter = this.tickBars.listIterator(first);

		TickBar bar = (TickBar) iter.next(); // size != 0
		double min = bar.min();
		double max = bar.max();
		while (iter.hasNext()) {
			bar = (TickBar) iter.next();
			min = Math.min(bar.min(), min);
			max = Math.max(bar.max(), max);
		}

		final double scale = min == max ? 0 : h / (max - min);
		iter = this.tickBars.listIterator(first);
		int x = 0;
		g.setColor(this.getForeground());
		while (iter.hasNext()) {
			bar = (TickBar) iter.next();
			bar.paint(g, x, y, h, min, scale);
			x++;
		}
	}

	/**
	 * Set the value of label.
	 * @param v  Value to assign to label.
	 */
	public void setLabel(final String v) {
		this.label = v;
	}

	/**
	 * Set the value of maxHistory.
	 * @param v  Value to assign to maxHistory.
	 */
	public void setMaxHistory(final int v) {
		this.maxHistory = v;
	}
}
