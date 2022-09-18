/* 
 * RangeSlider.java : code for a double-sided range slider. Based on code
 * by Ben Bederson and Jon Meyer:
 * Copyright (C) 2000 by Ben Bederson, Maryland, USA
 * All rights reserved.
 *
 * RangeSlider handles int values from a BoundedRangeModel
 *
 */
package fr.emn.oadymppac.widgets;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Implements a Swing-based Range slider, which allows the user to
 * enter a range-based value.
 */
public class RangeSlider extends JComponent implements MouseListener,
		MouseMotionListener, ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4415485783466635814L;
	private static int PICK_WIDTH = 6;
	//	private static int pickWidth = RangeSlider.PICK_WIDTH;
	static int SZ = 6; // Size of the cutout corner on the range bar.

	// Event handling
	static final int PICK_NONE = 0;

	// Event handling
	static final int PICK_MIN = 1;

	// Event handling
	static final int PICK_MAX = 2;

	// Event handling
	static final int PICK_MID = 4;
	/**
	 * DOCUMENT ME!
	 * 
	 * @param args DOCUMENT ME!
	 */
	public static void main(final String[] args) {
		final JFrame f = new JFrame();
		final RangeSlider slider = new RangeSlider(10, 123, 23, 30);
		slider.setEnabled(true);
		f.getContentPane().add(slider);
		f.pack();
		f.setVisible(true);
	}
	private BoundedRangeModel model;

	private boolean enabled = false;

	// PAINT METHOD
	int[] xPts = new int[7];
	// PAINT METHOD
	int[] yPts = new int[7];
	int pick;
	int pickOffset;

	// PUBLIC API

	int mouseX;

	public RangeSlider(final BoundedRangeModel model) {
		this.model = model;
		model.addChangeListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * Constructs a new range slider.
	 * 
	 * @param minimum - the minimum value of the range.
	 * @param maximum - the maximum value of the range.
	 * @param lowValue - the current low value shown by the range
	 *        slider's bar.
	 * @param highValue - the current high value shown by the range
	 *        slider's bar.
	 */
	public RangeSlider(
		final int minimum,
		final int maximum,
		final int lowValue,
		final int highValue) {
		this(new DefaultBoundedRangeModel(
			lowValue,
			highValue - lowValue,
			minimum,
			maximum));

	}

	/**
	 * Returns true if the specified value is within the range
	 * indicated by this range slider. i.e. lowValue 1 &lt;= v &lt;=
	 * highValue.
	 * 
	 * @param v value
	 * 
	 * @return true if the specified value is within the range
	 *         indicated by this range slider.
	 */
	public boolean contains(final int v) {
		return v >= this.getLowValue() && v <= this.getHighValue();
	}

	/**
	 * Returns the current "high" value shown by the range slider's
	 * bar. The high value meets the constraint minimum  &lt;=
	 * lowValue  &lt;= highValue  &lt;= maximum.
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getHighValue() {
		return this.model.getValue() + this.model.getExtent();
	}

	Rectangle getInBounds() {
		final Dimension sz = this.getSize();
		final Insets insets = this.getInsets();
		return new Rectangle(insets.left, insets.top, sz.width - insets.left
				- insets.right, sz.height - insets.top - insets.bottom);
	}

	/**
	 * Returns the current "low" value shown by the range slider's
	 * bar. The low value meets the constraint minimum  &lt;=
	 * lowValue  &lt;= highValue  &lt;= maximum.
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getLowValue() {
		return this.model.getValue();
	}

	/**
	 * Returns the maximum possible value for either the low value or
	 * the high value.
	 * 
	 * @return the maximum possible value for either the low value or
	 *         the high value.
	 */
	public int getMaximum() {
		return this.model.getMaximum();
	}

	/**
	 * Returns the minimum possible value for either the low value or
	 * the high value.
	 * 
	 * @return the minimum possible value for either the low value or
	 *         the high value.
	 */
	public int getMinimum() {
		return this.model.getMinimum();
	}

	/**
	 * Returns the model.
	 * @return BoundedRangeModel
	 */
	public BoundedRangeModel getModel() {
		return this.model;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public Dimension getPreferredSize() {
		return new Dimension(300, 20);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param e DOCUMENT ME!
	 */
	public void mouseClicked(final MouseEvent e) {
	}
	/**
	 * DOCUMENT ME!
	 * 
	 * @param e DOCUMENT ME!
	 */
	public void mouseDragged(final MouseEvent e) {
		if (this.enabled == false) {
			return;
		}

		int x = this.toLocalX(e.getX());

		if (x < this.getMinimum()) {
			x = this.getMinimum();
		}

		if (x > this.getMaximum()) {
			x = this.getMaximum();
		}

		if (this.pick == (RangeSlider.PICK_MIN | RangeSlider.PICK_MAX)) {
			if (e.getX() - this.mouseX > 2) {
				this.pick = RangeSlider.PICK_MAX;
			}
			else if (e.getX() - this.mouseX < -2) {
				this.pick = RangeSlider.PICK_MIN;
			}
			else {
				return;
			}
		}

		switch (this.pick) {
			case PICK_MIN :

				if (x < this.getHighValue()) {
					this.setLowValue(x);
				}

				break;

			case PICK_MAX :

				if (x > this.getLowValue()) {
					this.setHighValue(x);
				}

				break;

			case PICK_MID :

				int dx =
					this.toLocalX(e.getX() - this.pickOffset)
							- this.getLowValue();

				if (dx < 0 && this.getLowValue() + dx < this.getMinimum()) {
					dx = this.getMinimum() - this.getLowValue();
				}

				if (dx > 0 && this.getHighValue() + dx > this.getMaximum()) {
					dx = this.getMaximum() - this.getHighValue();
				}

				if (dx != 0) {
					this.offset(dx);
				}

				break;
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param e DOCUMENT ME!
	 */
	public void mouseEntered(final MouseEvent e) {
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param e DOCUMENT ME!
	 */
	public void mouseExited(final MouseEvent e) {
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param e DOCUMENT ME!
	 */
	public void mouseMoved(final MouseEvent e) {
		if (this.enabled == false) {
			return;
		}

		switch (this.pickHandle(e.getX())) {
			case PICK_MIN :
			case RangeSlider.PICK_MIN | RangeSlider.PICK_MAX :
				this.setCurs(Cursor.W_RESIZE_CURSOR);

				break;

			case PICK_MAX :
				this.setCurs(Cursor.E_RESIZE_CURSOR);

				break;

			case PICK_MID :
				this.setCurs(Cursor.MOVE_CURSOR);

				break;

			case PICK_NONE :
				this.setCurs(Cursor.DEFAULT_CURSOR);

				break;
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param e DOCUMENT ME!
	 */
	public void mousePressed(final MouseEvent e) {
		if (this.enabled == false) {
			return;
		}

		this.pick = this.pickHandle(e.getX());
		this.pickOffset = e.getX() - this.toScreenX(this.getLowValue());
		this.mouseX = e.getX();
		this.model.setValueIsAdjusting(true);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param e DOCUMENT ME!
	 */
	public void mouseReleased(final MouseEvent e) {
		this.model.setValueIsAdjusting(false);
	}

	private void offset(final int dx) {
		this.model.setValue(this.getLowValue() + dx);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param g DOCUMENT ME!
	 */
	public void paintComponent(final Graphics g) {
		g.setColor(Color.lightGray);

		this.getSize();
		final Rectangle rect = this.getInBounds();

		g.fill3DRect(rect.x, rect.y, rect.width, rect.height, false);

		final int minX = this.toScreenX(this.getLowValue());
		final int maxX = this.toScreenX(this.getHighValue());

		if (maxX - minX > 10) {
			this.xPts[0] = minX;
			this.yPts[0] = rect.y + RangeSlider.SZ;
			this.xPts[1] = minX + RangeSlider.SZ;
			this.yPts[1] = rect.y;
			this.xPts[2] = maxX;
			this.yPts[2] = rect.y;
			this.xPts[3] = maxX;
			this.yPts[3] = rect.y + rect.height - RangeSlider.SZ;
			this.xPts[4] = maxX - RangeSlider.SZ;
			this.yPts[4] = rect.y + rect.height - 3;
			this.xPts[5] = minX;
			this.yPts[5] = rect.y + rect.height - 3;
			this.xPts[6] = minX;
			this.yPts[6] = rect.y + RangeSlider.SZ;
			g.setColor(Color.darkGray);
			g.drawPolygon(this.xPts, this.yPts, 7);

			if (this.enabled == true) {
				g.setColor(Color.lightGray);
			}

			g.fillPolygon(this.xPts, this.yPts, 7);

			g.setColor(Color.white);
			g.drawLine(this.xPts[0], this.yPts[0], this.xPts[1], this.yPts[1]);
			g.drawLine(this.xPts[1], this.yPts[1], this.xPts[2], this.yPts[2]);
			g.drawLine(this.xPts[5], this.yPts[5], this.xPts[6], this.yPts[6]);

			if (maxX - minX > 12) {
				// Draw the little dot pattern
				for (int y = rect.y + 3; y < rect.y + rect.height - 3; y += 3) {
					g.setColor(Color.lightGray);
					g.fillRect(minX + 2, y + 2, 1, 1);
					g.fillRect(minX + 5, y, 1, 1);
					g.fillRect(minX + 8, y - 2, 1, 1);

					g.fillRect(maxX - 3, y, 1, 1);
					g.fillRect(maxX - 6, y + 2, 1, 1);
					g.fillRect(maxX - 9, y + 4, 1, 1);

					g.setColor(Color.darkGray);
					g.fillRect(minX + 2, y + 3, 1, 1);
					g.fillRect(minX + 5, y + 1, 1, 1);
					g.fillRect(minX + 8, y - 1, 1, 1);

					g.fillRect(maxX - 3, y + 1, 1, 1);
					g.fillRect(maxX - 6, y + 3, 1, 1);
					g.fillRect(maxX - 9, y + 5, 1, 1);
				}

				g.setColor(Color.gray);
				g.drawLine(minX + 10, rect.y + 2, minX + 10, rect.y
						+ rect.height - 2);
				g.drawLine(maxX - 11, rect.y + 2, maxX - 11, rect.y
						+ rect.height - 2);
				//	RangeSlider.pickWidth = RangeSlider.PICK_WIDTH;
			}
			else {
				// Too small to draw the dot pattern - just draw a line down the center
				g.setColor(Color.gray);
				g.drawLine(
					(minX + maxX) / 3,
					rect.y + 2,
					(minX + maxX) / 3,
					rect.y + rect.height - 2);

				g.drawLine(
					2 * (minX + maxX) / 3,
					rect.y + 2,
					2 * (minX + maxX) / 3,
					rect.y + rect.height - 2);
				//	RangeSlider.pickWidth = ((maxX - minX) / 3);
			}
		}
		else {
			// For very small ranges we just draw a tiny 3D rect
			if (this.enabled == true) {
				g.setColor(Color.lightGray);
			}
			else {
				g.setColor(Color.darkGray);
			}

			int w = maxX - minX;

			if (w < 10) {
				w = 10;
			}

			g.fill3DRect(minX, rect.y, w, rect.y + rect.height, true);
			g.setColor(Color.gray);
			g.drawLine((minX + maxX) / 3, rect.y + 2, (minX + maxX) / 3, rect.y
					+ rect.height - 2);
			g.drawLine(
				2 * (minX + maxX) / 3,
				rect.y + 2,
				2 * (minX + maxX) / 3,
				rect.y + rect.height - 2);
			//	RangeSlider.pickWidth = ((maxX - minX) / 3);
		}
	}

	private int pickHandle(final int x) {
		final int minX = this.toScreenX(this.getLowValue());
		final int maxX = this.toScreenX(this.getHighValue());

		int pick = 0;

		if (Math.abs(x - minX) < RangeSlider.PICK_WIDTH) {
			pick |= RangeSlider.PICK_MIN;

			//System.out.println("MIN");
		}

		if (Math.abs(x - maxX) < RangeSlider.PICK_WIDTH) {
			pick |= RangeSlider.PICK_MAX;

			//System.out.println("MAX");
		}

		if (pick == 0 && x > minX && x < maxX) {
			pick = RangeSlider.PICK_MID;

			//System.out.println("MID");
		}

		return pick;
	}

	private void setCurs(final int c) {
		final Cursor cursor = Cursor.getPredefinedCursor(c);

		if (this.getCursor() != cursor) {
			this.setCursor(cursor);
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param v DOCUMENT ME!
	 */
	public void setEnabled(final boolean v) {
		this.enabled = v;
		this.repaint();
	}

	/**
	 * Sets the high value shown by this range slider. This causes
	 * the range slider to be repainted and a RangeEvent to be
	 * fired.
	 * 
	 * @param highValue the high value shown by this range slider
	 */
	public void setHighValue(final int highValue) {
		this.model.setExtent(highValue - this.getLowValue());
	}

	/**
	 * Sets the low value shown by this range slider. This causes the
	 * range slider to be repainted and a RangeEvent to be fired.
	 * 
	 * @param lowValue the low value shown by this range slider
	 */
	public void setLowValue(final int lowValue) {
		if (lowValue + this.model.getExtent() > this.getMaximum()) {
			this.model.setExtent(this.getMaximum() - lowValue);
			this.model.setValue(lowValue);
		}
		else {
			final int high = this.getHighValue();
			this.model.setValue(lowValue);
			this.setHighValue(high);
		}
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param max DOCUMENT ME!
	 */
	public void setMaximum(final int max) {
		this.model.setMaximum(max);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param min DOCUMENT ME!
	 */
	public void setMinimum(final int min) {
		this.model.setMinimum(min);
	}

	/**
	 * Sets the model.
	 * @param model The model to set
	 */
	public void setModel(final BoundedRangeModel model) {
		this.model = model;
		this.repaint();
	}

	/**
	 * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
	 */
	public void stateChanged(final ChangeEvent e) {
		this.repaint();
	}
	// Converts from screen coordinates to a range value.
	private int toLocalX(final int x) {
		final Dimension sz = this.getSize();
		final double xScale =
			(sz.width - 3) / (double) (this.getMaximum() - this.getMinimum());

		return (int) (x / xScale + this.getMinimum());
	}

	// Converts from a range value to screen coordinates.
	private int toScreenX(final int x) {
		final Dimension sz = this.getSize();
		final double xScale =
			(sz.width - 3) / (double) (this.getMaximum() - this.getMinimum());

		return (int) ((x - this.getMinimum()) * xScale);
	}

}