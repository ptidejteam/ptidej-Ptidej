/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization;

import infovis.column.IntColumn;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JComponent;
import javax.swing.Timer;


/**
 * Display excentric labels around items in a labeledComponent.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultExcentricLabels extends MouseAdapter
    implements ExcentricLabels, Comparator, MouseMotionListener {
    Timer              insideTimer;
    IntColumn          hits;
    Rectangle2D.Double cursorBounds;
    int                centerX;
    int                centerY;
    int                focusSize = 50;
    Point2D.Double[]   itemPosition;
    Point2D.Double[]   linkPosition;
    Point2D.Double[]   labelPosition;
    Point2D.Double[]   left;
    int                leftCount;
    Point2D.Double[]   right;
    int                rightCount;
    boolean            xStable;
    boolean            yStable;
    JComponent         component;
    LabeledComponent   labeledComponent;
    boolean            visible;
    int                gap = 5;
    int                maxLabels;
    int                labelCount;
    int                threshold = 20;
    boolean            opaque;
    Color               backgroundColor = Color.WHITE;
    Stroke             wideStroke = new BasicStroke(3);

    /**
     * Constructor for DefaultExcentricLabels.
     */
    public DefaultExcentricLabels() {
        this.cursorBounds = new Rectangle2D.Double(0, 0, this.focusSize, this.focusSize);

        this.insideTimer = new Timer(2000,
                                new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                }
            });
        this.insideTimer.setRepeats(false);
        setMaxLabels(20);
    }

    public void setVisualization(LabeledComponent labeledComponent) {
        this.labeledComponent = labeledComponent;
        JComponent c = (labeledComponent != null) ? labeledComponent.getComponent() : null;
        
        if (this.component == c)
            return;
        if (this.component != null) {
            this.component.removeMouseListener(this);
            this.component.removeMouseMotionListener(this);
        }
        this.component = c;
        if (this.component != null) {
            this.component.addMouseListener(this);
            this.component.addMouseMotionListener(this);
        }
    }

    public void paint(Graphics2D graphics, Rectangle2D bounds) {
        if (this.labeledComponent == null || !this.visible)
            return;
        FontMetrics fm = graphics.getFontMetrics();
        computeExcentricLabels(graphics, bounds);

        Line2D.Double line = new Line2D.Double();
        for (int i = 0; i < this.labelCount; i++) {
            String lab = this.labeledComponent.getLabelAt(this.hits.get(i));
            if (lab == null) {
                lab = "item" + this.hits.get(i);
            }
            
            Point2D.Double pos = this.labelPosition[i];
            if (this.opaque) {
                graphics.setColor(this.backgroundColor);
                Rectangle2D sb = fm.getStringBounds(lab, graphics);
                graphics.fillRect((int)(pos.x+sb.getX()-2), (int)(pos.y+sb.getY()-2),
                    (int)sb.getWidth()+4, (int)sb.getHeight()+4);
                graphics.setColor(this.labeledComponent.getColorAt(this.hits.get(i)));
                graphics.drawRect((int)(pos.x+sb.getX()-2), (int)(pos.y+sb.getY()-2),
                                    (int)sb.getWidth()+4, (int)sb.getHeight()+4);
            }
            graphics.setColor(Color.BLACK);
            graphics.drawString(lab, (int)(pos.x), (int)(pos.y));
            line.setLine(this.itemPosition[i], this.linkPosition[i]);
            graphics.setColor(this.backgroundColor);
            Stroke save = graphics.getStroke();
            graphics.setStroke(this.wideStroke);
            graphics.draw(line);
            graphics.setColor(Color.BLACK);
            graphics.setStroke(save);
            graphics.draw(line);
        }
        graphics.setColor(Color.RED);
        graphics.draw(this.cursorBounds);
    }

    protected void computeExcentricLabels(Graphics2D graphics,
                                          Rectangle2D bounds) {
        if (this.labeledComponent == null)
            return;

        this.cursorBounds.x = this.centerX - this.focusSize / 2;
        this.cursorBounds.y = this.centerY - this.focusSize / 2;

        if (this.hits == null)
            this.hits = new IntColumn("pickAll");

        this.hits = this.labeledComponent.pickAll(this.cursorBounds, bounds, this.hits);

        this.labelCount = Math.min(this.maxLabels, this.hits.getRowCount());
        if (this.labelCount != 0) {
            computeItemPositions(graphics, bounds);
            projectLeftRight(graphics, bounds);
        }
    }

    protected void computeItemPositions(Graphics2D graphics,
                                        Rectangle2D bounds) {
        Rectangle2D.Double inter = new Rectangle2D.Double();

        for (int i = 0; i < this.labelCount; i++) {
            Shape s = this.labeledComponent.getShapeAt(this.hits.get(i));
            if (s == null) {
                this.itemPosition[i].setLocation(0, 0);
            }
            else {
                Rectangle2D rect = s.getBounds2D();
                Rectangle2D.intersect(this.cursorBounds, rect, inter);
                this.itemPosition[i].setLocation(inter.getCenterX(), inter.getCenterY());
            }
        }
    }

    protected double comparableValueLeft(Point2D.Double pos) {
        if (this.yStable)
            return pos.y;
        else
            return Math.atan2(pos.y - this.centerY, this.centerX - pos.x);
    }

    protected double comparableValueRight(Point2D.Double pos) {
        if (this.yStable)
            return pos.getY();
        else
            return Math.atan2(pos.y - this.centerY, pos.x - this.centerX);
    }

    protected void projectLeftRight(Graphics2D graphics, Rectangle2D bounds) {
        int radius = this.focusSize / 2;
        int i;

        this.leftCount = 0;
        this.rightCount = 0;
        double      maxHeight = 0;
        FontMetrics fm = graphics.getFontMetrics();

        for (i = 0; i < this.labelCount; i++) {
            Point2D.Double itemPos = this.itemPosition[i];
            String         lab = this.labeledComponent.getLabelAt(this.hits.get(i));
            if (lab == null)
                lab = "item" + this.hits.get(i);
            Rectangle2D    sb = fm.getStringBounds(lab, graphics);
            Point2D.Double linkPos = this.linkPosition[i];
            Point2D.Double labelPos = this.labelPosition[i];

            maxHeight = Math.max(sb.getHeight(), maxHeight);
            if (this.itemPosition[i].getX() < this.centerX) {
                linkPos.y = comparableValueLeft(itemPos);
                if (this.xStable)
                    linkPos.x = itemPos.x - radius - this.gap;
                else
                    linkPos.x = this.centerX - radius - this.gap;
                labelPos.x = linkPos.x - sb.getWidth();
                this.left[this.leftCount++] = linkPos;
            } else {
                linkPos.y = comparableValueRight(itemPos);
                if (this.xStable)
                    linkPos.x = itemPos.x + radius + this.gap;
                else
                    linkPos.x = this.centerX + radius + this.gap;
                labelPos.x = linkPos.x;
                this.right[this.rightCount++] = linkPos;
            }
        }

        Arrays.sort(this.left, 0, this.leftCount, this);
        Arrays.sort(this.right, 0, this.rightCount, this);
        double yMidLeft = this.leftCount * maxHeight / 2;
        double yMidRight = this.rightCount * maxHeight / 2;
        int    ascent = fm.getAscent();

        for (i = 0; i < this.leftCount; i++) {
            Point2D.Double pos = this.left[i];
            pos.y = i * maxHeight + this.centerY - yMidLeft + ascent;
        }
        for (i = 0; i < this.rightCount; i++) {
            Point2D.Double pos = this.right[i];
            pos.y = i * maxHeight + this.centerY - yMidRight + ascent;
        }
        for (i = 0; i < this.linkPosition.length; i++) {
            this.labelPosition[i].y = this.linkPosition[i].y;
        }
    }

    /**
     * Returns the visible.
     * @return boolean
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Sets the visible.
     * @param visible The visible to set
     */
    public void setVisible(boolean visible) {
        if (this.visible != visible) {
            this.visible = visible;
            this.labeledComponent.repaint();
        }
    }

    /**
     * For sorting points vertically.
     *
     * @see java.util.Comparator#compare(Object, Object)
     */
    public int compare(Object o1, Object o2) {
        double d = ((Point2D.Double)o1).getY() - ((Point2D.Double)o2).getY();
        if (d < 0)
            return -1;
        else if (d == 0)
            return 0;
        else
            return 1;
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseEntered(MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {
        this.insideTimer.restart();
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseExited(MouseEvent)
     */
    public void mouseExited(MouseEvent e) {
        this.insideTimer.stop();
        setVisible(false);
    }

    /**
     * @see java.awt.event.MouseAdapter#mousePressed(MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        setVisible(false);
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
    }

    int dist2(int dx, int dy) {
        return dx * dx + dy * dy;
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        if (isVisible()) {
            if (dist2(this.centerX - e.getX(), this.centerY - e.getY()) > this.threshold * this.threshold) {
                setVisible(false);
                this.insideTimer.restart();
            }
            this.labeledComponent.repaint();
        }
        this.centerX = e.getX();
        this.centerY = e.getY();
    }

    /**
     * Returns the gap.
     * @return int
     */
    public int getGap() {
        return this.gap;
    }

    /**
     * Sets the gap.
     * @param gap The gap to set
     */
    public void setGap(int gap) {
        this.gap = gap;
    }

    /**
     * Returns the maxLabels.
     * @return int
     */
    public int getMaxLabels() {
        return this.maxLabels;
    }

    void allocatePoints(Point2D.Double[] array) {
        for (int i = 0; i < array.length; i++)
            array[i] = new Point2D.Double();
    }

    /**
     * Sets the maxLabels.
     * @param maxLabels The maxLabels to set
     */
    public void setMaxLabels(int maxLabels) {
        this.maxLabels = maxLabels;
        this.itemPosition = new Point2D.Double[maxLabels];
        allocatePoints(this.itemPosition);
        this.linkPosition = new Point2D.Double[maxLabels];
        allocatePoints(this.linkPosition);
        this.labelPosition = new Point2D.Double[maxLabels];
        allocatePoints(this.labelPosition);
        this.left = new Point2D.Double[maxLabels];
        this.right = new Point2D.Double[maxLabels];
    }

    /**
     * Returns the threshold.
     *
     * When the mouse moves a distance larger than this
     * threshold since the last event, excentric labels
     * are disabled.
     *
     * @return int
     */
    public int getThreshold() {
        return this.threshold;
    }

    /**
     * Sets the threshold.
     *
     * When the mouse moves a distance larger than the
     * specified threshold since the last event, excentric
     * labels are disabled.
     *
     * @param threshold The threshold to set
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    /**
     * Returns the focusSize.
     * @return int
     */
    public int getFocusSize() {
        return this.focusSize;
    }

    /**
     * Sets the focusSize.
     * @param focusSize The focusSize to set
     */
    public void setFocusSize(int focusSize) {
        this.focusSize = focusSize;
        this.cursorBounds = new Rectangle2D.Double(0, 0, focusSize, focusSize);
    }

    /**
     * Returns the backgroundColor.
     * @return Color
     */
    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * Returns the opaque.
     * @return boolean
     */
    public boolean isOpaque() {
        return this.opaque;
    }

    /**
     * Sets the backgroundColor.
     * @param backgroundColor The backgroundColor to set
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Sets the opaque.
     * @param opaque The opaque to set
     */
    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }

}
