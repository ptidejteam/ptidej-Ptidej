/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Implements a Swing-based Range slider, which allows the user to
 * enter a range-based value.
 *
 * @author Ben B. Bederson, Jon Meyer and Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DoubleRangeSlider extends JComponent
    implements MouseListener, MouseMotionListener, ChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int PICK_WIDTH = 6;
    static int		   SZ = 6; // Size of the cutout corner on the range bar.

    // Event handling
    static final int PICK_NONE = 0;

    // Event handling
    static final int PICK_MIN = 1;

    // Event handling
    static final int PICK_MAX = 2;

    // Event handling
    static final int		  PICK_MID = 4;
    private DoubleBoundedRangeModel model;
    private boolean			  enabled = false;

    // PAINT METHOD
    int[] xPts = new int[7];

    // PAINT METHOD
    int[] yPts = new int[7];
    int   pick;
    double   pickOffset;
    double   mouseX;
    double scaleRatio = 1;

    // PUBLIC API

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
    public DoubleRangeSlider(double minimum, double maximum, double lowValue, double highValue) {
	this(new DefaultDoubleBoundedRangeModel(lowValue, highValue - lowValue,
						minimum, maximum));
    }

    /**
     * Creates a new RangeSlider object.
     *
     * @param model the DoubleBoundedRangeModel
     */
    public DoubleRangeSlider(DoubleBoundedRangeModel model) {
	this.model = model;
	model.addChangeListener(this);
	addMouseListener(this);
	addMouseMotionListener(this);
    }

    /**
     * Returns the current "low" value shown by the range slider's
     * bar. The low value meets the constraint minimum  &lt;=
     * lowValue  &lt;= highValue  &lt;= maximum.
     *
     * @return the current "low" value shown by the range slider's bar.
     */
    public double getLowValue() {
	return this.model.getValue();
    }

    /**
     * Returns the current "high" value shown by the range slider's
     * bar. The high value meets the constraint minimum  &lt;=
     * lowValue  &lt;= highValue  &lt;= maximum.
     *
     * @return the current "high" value shown by the range slider's
     * bar.
     */
    public double getHighValue() {
	return this.model.getValue() + this.model.getExtent();
    }

    /**
     * Returns the minimum possible value for either the low value or
     * the high value.
     *
     * @return the minimum possible value for either the low value or
     *         the high value.
     */
    public double getMinimum() {
	return this.model.getMinimum();
    }

    /**
     * Returns the maximum possible value for either the low value or
     * the high value.
     *
     * @return the maximum possible value for either the low value or
     *         the high value.
     */
    public double getMaximum() {
	return this.model.getMaximum();
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
    public boolean contains(double v) {
	return (v >= getLowValue() && v <= getHighValue());
    }

    /**
     * Sets the low value shown by this range slider. This causes the
     * range slider to be repainted and a RangeEvent to be fired.
     *
     * @param lowValue the low value shown by this range slider
     */
    public void setLowValue(double lowValue) {
	if ((lowValue + this.model.getExtent()) > getMaximum()) {
	    this.model.setExtent(getMaximum() - lowValue);
	    this.model.setValue(lowValue);
	} else {
	    double high = getHighValue();
	    this.model.setValue(lowValue);
	    setHighValue(high);
	}
    }

    /**
     * Sets the high value shown by this range slider. This causes
     * the range slider to be repainted and a RangeEvent to be
     * fired.
     *
     * @param highValue the high value shown by this range slider
     */
    public void setHighValue(double highValue) {
	this.model.setExtent(highValue - getLowValue());
    }

    /**
     * Sets the minimum value of the doubleBoundedRangeModel.
     *
     * @param min the minimum value.
     */
    public void setMinimum(double min) {
	this.model.setMinimum(min);
    }

    /**
     * Sets the maximum value of the doubleBoundedRangeModel.
     *
     * @param max the maximum value.
     */
    public void setMaximum(double max) {
	this.model.setMaximum(max);
    }

    Rectangle getInBounds() {
	Dimension sz = getSize();
	Insets    insets = getInsets();
	return new Rectangle(insets.left, insets.top,
			     sz.width - insets.left - insets.right,
			     sz.height - insets.top - insets.bottom);
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics g) {
	g.setColor(Color.lightGray);

	//Dimension sz = getSize();
	Rectangle rect = getInBounds();

	g.fill3DRect(rect.x, rect.y, rect.width, rect.height, false);

	int minX = toScreenX(getLowValue());
	int maxX = toScreenX(getHighValue());

	if ((maxX - minX) > 10) {
	    this.xPts[0] = minX;
	    this.yPts[0] = rect.y + SZ;
	    this.xPts[1] = minX + SZ;
	    this.yPts[1] = rect.y;
	    this.xPts[2] = maxX;
	    this.yPts[2] = rect.y;
	    this.xPts[3] = maxX;
	    this.yPts[3] = rect.y + rect.height - SZ;
	    this.xPts[4] = maxX - SZ;
	    this.yPts[4] = rect.y + rect.height - 3;
	    this.xPts[5] = minX;
	    this.yPts[5] = rect.y + rect.height - 3;
	    this.xPts[6] = minX;
	    this.yPts[6] = rect.y + SZ;
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

	    if ((maxX - minX) > 12) {
		// Draw the little dot pattern
		for (int y = rect.y + 3; y < (rect.y + rect.height - 3);
		     y += 3) {
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
		g.drawLine(minX + 10, rect.y + 2, minX + 10,
			   rect.y + rect.height - 2);
		g.drawLine(maxX - 11, rect.y + 2, maxX - 11,
			   rect.y + rect.height - 2);
	    } else {
		// Too small to draw the dot pattern - just draw a line down the center
		g.setColor(Color.gray);
		g.drawLine((minX + maxX) / 3, rect.y + 2, (minX + maxX) / 3,
			   rect.y + rect.height - 2);

		g.drawLine((2 * (minX + maxX)) / 3, rect.y + 2,
			   (2 * (minX + maxX)) / 3, rect.y + rect.height - 2);
	    }
	} else {
	    // For very small ranges we just draw a tiny 3D rect
	    if (this.enabled == true) {
		g.setColor(Color.lightGray);
	    } else {
		g.setColor(Color.darkGray);
	    }

	    int w = maxX - minX;

	    if (w < 10) {
		w = 10;
	    }

	    g.fill3DRect(minX, rect.y, w, rect.y + rect.height, true);
	    g.setColor(Color.gray);
	    g.drawLine((minX + maxX) / 3, rect.y + 2, (minX + maxX) / 3,
		       rect.y + rect.height - 2);
	    g.drawLine((2 * (minX + maxX)) / 3, rect.y + 2,
		       (2 * (minX + maxX)) / 3, rect.y + rect.height - 2);
	}
	setToolTipText("RangeSlider");
    }

    // Converts from screen coordinates to a range value.
    private double toLocalX(double x) {
	Dimension sz = getSize();
	double    xScale = (sz.width - 3) / (getMaximum() - getMinimum());

	return (x / xScale) + getMinimum();
    }

    // Converts from a range value to screen coordinates.
    private int toScreenX(double x) {
	Dimension sz = getSize();
	double    xScale = (sz.width - 3) / (getMaximum() - getMinimum());

	return (int)((x - getMinimum()) * xScale);
    }

    private int pickHandle(double x) {
	double minX = toScreenX(getLowValue());
	int maxX = toScreenX(getHighValue());

	int pick = 0;

	if (Math.abs(x - minX) < PICK_WIDTH) {
	    pick |= PICK_MIN;

	    //System.out.println("MIN");
	}

	if (Math.abs(x - maxX) < PICK_WIDTH) {
	    pick |= PICK_MAX;

	    //System.out.println("MAX");
	}

	if ((pick == 0) && (x > minX) && (x < maxX)) {
	    pick = PICK_MID;

	    //System.out.println("MID");
	}

	return pick;
    }

    private void offset(double dx) {
	this.model.setValue(getLowValue() + dx);
    }

    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
	if (this.enabled == false) {
	    return;
	}

	this.pick = pickHandle(e.getX());
	this.pickOffset = e.getX() - toScreenX(getLowValue());
	this.mouseX = e.getX();
	this.model.setValueIsAdjusting(true);
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
	if (this.enabled == false) {
	    return;
	}
	int dist = 0;
	double xpos = e.getX();
		
	if (e.getY() < 0)
	    dist = -e.getY();
	else if (e.getY() > getSize().height) {
	    dist = e.getY() - getSize().height;
	}
	if (dist != 0) {
	    xpos = this.mouseX + (xpos - this.mouseX) / (1+dist);
	}
	this.mouseX = xpos;
		
	double x = toLocalX(xpos);

	if (x < getMinimum()) {
	    x = getMinimum();
	}

	if (x > getMaximum()) {
	    x = getMaximum();
	}

	if (this.pick == (PICK_MIN | PICK_MAX)) {
	    if ((xpos - this.mouseX) > 2) {
		this.pick = PICK_MAX;
	    } else if ((xpos - this.mouseX) < -2) {
		this.pick = PICK_MIN;
	    } else {
		return;
	    }
	}

	switch (this.pick) {
	case PICK_MIN:
	    if (x < getHighValue()) {
		setLowValue(x);
	    }
	    break;
	case PICK_MAX:
	    if (x > getLowValue()) {
		setHighValue(x);
	    }
	    break;
	case PICK_MID:
	    double dx = toLocalX(xpos - this.pickOffset) - getLowValue();
	    if ((dx < 0) && ((getLowValue() + dx) < getMinimum())) {
		dx = getMinimum() - getLowValue();
	    }

	    if ((dx > 0) && ((getHighValue() + dx) > getMaximum())) {
		dx = getMaximum() - getHighValue();
	    }

	    if (dx != 0) {
		offset(dx);
	    }

	    break;
	}
    }

    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
	this.model.setValueIsAdjusting(false);
    }

    private void setCurs(int c) {
	Cursor cursor = Cursor.getPredefinedCursor(c);
    
        Component frame = this;
        while (frame != null && ! (frame instanceof Window)) {
            frame = frame.getParent();
        }
        if (frame == null)
            return;

	if (frame.getCursor() != cursor) {
	    frame.setCursor(cursor);
	}
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
	if (this.enabled == false) {
	    return;
	}

	switch (pickHandle(e.getX())) {
	case PICK_MIN:
	case PICK_MIN | PICK_MAX:
	    setCurs(Cursor.W_RESIZE_CURSOR);
	    break;
	case PICK_MAX:
	    setCurs(Cursor.E_RESIZE_CURSOR);
	    break;
	case PICK_MID:
	    setCurs(Cursor.MOVE_CURSOR);
	    break;
	case PICK_NONE:
	    setCurs(Cursor.DEFAULT_CURSOR);
	    break;
	}
    }

    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            this.model.setValue(this.model.getMinimum());
            this.model.setExtent(this.model.getMaximum()-this.model.getMinimum());
            repaint();
        }
    }

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        setCurs(Cursor.DEFAULT_CURSOR);
    }

    /**
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
	return new Dimension(300, 20);
    }

    /**
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    public void setEnabled(boolean v) {
	this.enabled = v;
	repaint();
    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
	repaint();
    }

    /**
     * Returns the doubleBoundedRangeModel.
     * @return BoundedRangeModel
     */
    public DoubleBoundedRangeModel getModel() {
	return this.model;
    }

    /**
     * Sets the doubleBoundedRangeModel.
     * @param model The doubleBoundedRangeModel to set
     */
    public void setModel(DoubleBoundedRangeModel model) {
	this.model = model;
	repaint();
    }
	
    /**
     * @see javax.swing.JComponent#getToolTipText(MouseEvent)
     */
    public String getToolTipText(MouseEvent event) {
	return 
	    "["+getMinimum()
	    +" ["+getLowValue()
	    +":"+getHighValue()
	    +"] "+getMaximum()
	    +"]";
    }

    /**
     * Test code.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
	JFrame	    f = new JFrame();
	RangeSlider slider = new RangeSlider(10, 123, 23, 30);
	slider.setEnabled(true);
	f.getContentPane().add(slider);
	f.pack();
	f.setVisible(true);
    }
}
