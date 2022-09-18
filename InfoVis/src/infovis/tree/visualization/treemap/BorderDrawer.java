/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License
 * a copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.tree.visualization.treemap;

import infovis.Tree;
import infovis.tree.visualization.TreemapVisualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;



/**
 * Class for drawing Treemap borders.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class BorderDrawer {
    /** DOCUMENT ME! */
    protected Tree              tree;
    /** DOCUMENT ME! */
    protected float             left;
    /** DOCUMENT ME! */
    protected float             right;
    /** DOCUMENT ME! */
    protected float             top;
    /** DOCUMENT ME! */
    protected float             bottom;
    /** DOCUMENT ME! */
    protected Rectangle2D.Float rect;

    /**
     * Create a new BorderDrawer object.
     *
     * @param tree DOCUMENT ME!
     */
    public BorderDrawer(Tree tree) {
        this(tree, 3, 3, 3, 3);
    }

    /**
     * Create a new BorderDrawer object.
     *
     * @param tree DOCUMENT ME!
     * @param left DOCUMENT ME!
     * @param right DOCUMENT ME!
     * @param top DOCUMENT ME!
     * @param bottom DOCUMENT ME!
     */
    public BorderDrawer(Tree tree, float left, float right, float top,
                        float bottom) {
        this.tree = tree;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    /**
     * DOCUMENT ME!
     *
     * @param box DOCUMENT ME!
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean beginBorder(Rectangle2D.Float box, int node) {
        return box.width > (getLeft(node) + getRight(node) + 1) &&
               box.height > (getTop(node) + getBottom(node) + 1);
    }

    /**
     * DOCUMENT ME!
     *
     * @param box DOCUMENT ME!
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean leftBorder(Rectangle2D.Float box, int node) {
        box.width -= getLeft(node);
        box.x += getLeft(node);
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param box DOCUMENT ME!
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean rightBorder(Rectangle2D.Float box, int node) {
        box.width -= getRight(node);
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param box DOCUMENT ME!
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean topBorder(Rectangle2D.Float box, int node) {
        box.height -= getTop(node);
        box.y += getTop(node);
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param box DOCUMENT ME!
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean bottomBorder(Rectangle2D.Float box, int node) {
        box.height -= getBottom(node);
        return true;
    }

    /**
     * DOCUMENT ME!
     *
     * @param box DOCUMENT ME!
     * @param node DOCUMENT ME!
     */
    public void remainingBox(Rectangle2D.Float box, int node) {
        if (beginBorder(box, node)) {
            box.width -= getLeft(node) + getRight(node);
            box.x += getLeft(node);
            box.height -= getTop(node) + getBottom(node);
            box.y += getTop(node);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param graphics DOCUMENT ME!
     * @param visualization DOCUMENT ME!
     * @param box DOCUMENT ME!
     * @param node DOCUMENT ME!
     */
    public void paint(Graphics2D graphics,
                      TreemapVisualization visualization,
                      Rectangle2D.Float box, int node) {
        if (this.tree.isLeaf(node)) {
            visualization.paintShape(graphics, node);
        } else {
            paintBorder(graphics, visualization, box, node);
        }
        //visualization.paintOutline(graphics, node, box);
    }

    /**
     * Paint the border of a rectangle.
     *
     * @param graphics DOCUMENT ME!
     * @param visualization DOCUMENT ME!
     * @param box DOCUMENT ME!
     * @param node DOCUMENT ME!
     */
    public void paintBorder(Graphics2D graphics,
                            TreemapVisualization visualization,
                            Rectangle2D.Float box, int node) {
        graphics.setColor(Color.GRAY);
        Rectangle2D.Float rect = getRect();
        float             left = getLeft(node);
        float             right = getRight(node);
        float             top = getTop(node);
        float             bottom = getBottom(node);

        if (top > 0) {
            rect.setRect(box.x, box.y, box.width, top);
            graphics.fill(visualization.transformShape(rect));
        }
        if (left > 0) {
            rect.setRect(box.x, box.y + top,
                         left, box.height - top - bottom);
            graphics.fill(visualization.transformShape(rect));
        }
        if (right > 0) {
            rect.setRect(box.x + box.width - right, box.y + top,
                         right, box.height - top - bottom);
            graphics.fill(visualization.transformShape(rect));
        }
        if (bottom > 0) {
            rect.setRect(box.x, box.y + box.height - bottom,
                         box.width, bottom);
            graphics.fill(visualization.transformShape(rect));
        }
        visualization.paintOutline(graphics, node, visualization.transformShape(box));
    }

    /**
     * Returns the rect.
     *
     * @return Rectangle2D.Float
     */
    protected Rectangle2D.Float getRect() {
        if (this.rect == null)
            this.rect = new Rectangle2D.Float();
        return this.rect;
    }

    /**
     * Returns the bottom.
     *
     * @param node DOCUMENT ME!
     *
     * @return float
     */
    public float getBottom(int node) {
        return this.bottom;
    }

    /**
     * Returns the left.
     *
     * @param node DOCUMENT ME!
     *
     * @return float
     */
    public float getLeft(int node) {
        return this.left;
    }

    /**
     * Returns the right.
     *
     * @param node DOCUMENT ME!
     *
     * @return float
     */
    public float getRight(int node) {
        return this.right;
    }

    /**
     * Returns the top.
     *
     * @param node DOCUMENT ME!
     *
     * @return float
     */
    public float getTop(int node) {
        return this.top;
    }

    /**
     * Sets the bottom.
     *
     * @param bottom The bottom to set
     */
    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    /**
     * Sets the left.
     *
     * @param left The left to set
     */
    public void setLeft(float left) {
        this.left = left;
    }

    /**
     * Sets the right.
     *
     * @param right The right to set
     */
    public void setRight(float right) {
        this.right = right;
    }

    /**
     * Sets the top.
     *
     * @param top The top to set
     */
    public void setTop(float top) {
        this.top = top;
    }
}
