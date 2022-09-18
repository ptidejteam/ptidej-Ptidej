/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization.treemap;

import infovis.Tree;
import infovis.column.NumberColumn;
import infovis.column.ObjectColumn;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;


/**
 * Base class for all Treemap algorithms.
 *
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 */
public abstract class Treemap {
    BorderDrawer border;
    Tree         tree;
    NumberColumn sizeColumn;
    ObjectColumn shapesColumn;

    /**
     * Creates a new Treemap object.
     */
    public Treemap() {
    }
    
    /**
     * Returns the name of the algorithm.
     * 
     * @return the name of the algorithm.
     */
    public abstract String getName();

    /**
     * Method start should be called before starting to draw shapes.
     */
    public void start() {
        this.shapesColumn.clear();
    }

    /**
     * Method start should be called before starting to draw shapes.
     */
    public void finish() {
    }

    /**
     * Method beginBox check whether the box should be drawn
     *
     * @param xmin the XMIN coordinate of the box to draw.
     * @param ymin the YMIN coordinate of the box to draw.
     * @param xmax the XMAX coordinate of the box to draw.
     * @param ymax the YMAX coordinate of the box to draw.
     * @param node the tree node to draw
     *
     * @return true if the treemap algorithm should continue to go
     *         deeper, false otherwise.
     */
    public boolean beginBox(float xmin, float ymin, float xmax, float ymax,
                            int node) {
        Float box = 
            this.shapesColumn.isValueUndefined(node) ? 
            null : (Rectangle2D.Float)this.shapesColumn.get(node);
        if (box == null)
            box = new Rectangle2D.Float(xmin, ymin, xmax - xmin, ymax - ymin);
        else {
            box.setRect(xmin, ymin, xmax - xmin, ymax - ymin);
        }
        this.shapesColumn.setExtend(node, box);
        return this.border.beginBorder(box, node);
    }

    /**
     * Method removeBorder only removes the border from the given
     * box.
     *
     * @param box the rectangle containing the box whose border
     *        should be drawn. It is modified by the method and will
     *        hold the remaining part of the box after the border is
     *        drawn.
     * @param node the tree node to draw.
     */
    public void removeBorder(Rectangle2D.Float box, int node) {
        if (this.border.beginBorder(box, node)) {
            this.border.remainingBox(box, node);
        }
    }

    /**
     * Returns the tree.
     *
     * @return Tree
     */
    public Tree getTree() {
        return this.tree;
    }

    /**
     * Returns the sizeColumn.
     *
     * @return FloatColumn
     */
    public NumberColumn getSizeColumn() {
        return this.sizeColumn;
    }

    /**
     * Sets the size Column.
     *
     * @param sizeColumn The size Column to set
     */
    public void setSizeColumn(NumberColumn sizeColumn) {
      this.sizeColumn = sizeColumn;
    }

    /**
     * Computes the treemap.
     *
     * @param tree the Tree.
     * @param sizeColumn the size Column.
     * @param bordee the BorderDrawer.
     * @param bounds the external bounds of the treemap.
     * @param root the root node to start with.
     *
     * @return the number of nodes constructed by the treemap.
     */
    public abstract int visit(Tree tree, NumberColumn sizeColumn,
                              BorderDrawer border, Rectangle2D bounds,
                              int root);

    /**
     * Returns the shapesColumn.
     * @return ObjectColumn
     */
    public ObjectColumn getShapesColumn() {
        return this.shapesColumn;
    }

    /**
     * Sets the shapesColumn.
     * @param shapesColumn The shapesColumn to set
     */
    public void setShapesColumn(ObjectColumn shapesColumn) {
        this.shapesColumn = shapesColumn;
    }

    /**
     * Returns the border.
     * @return BorderDrawer
     */
    public BorderDrawer getBorder() {
        return this.border;
    }

    /**
     * Sets the border.
     * @param border The border to set
     */
    public void setBorder(BorderDrawer border) {
        this.border = border;
    }
}
