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
import infovis.utils.RowIterator;

import java.awt.geom.Rectangle2D;

/**
 * Slice and Dice Algorithm.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class SliceAndDice extends Treemap {
    public static final short DIRECTION_LEFT_TO_RIGHT = 0;
    public static final short DIRECTION_TOP_TO_BOTTOM = 1;
    protected short initialDirection;

    /**
     * Constructor for SliceAndDice.
     */
    public SliceAndDice() {
    }
    
    public String getName() {
        return "Slice & Dice";
    }
	
    protected short flip(short direction) {
	return (short)(1 - direction);
    }
	
    protected int visit(short direction,
			float xmin, float ymin, float xmax, float ymax,
			int node) {
	if (! beginBox(xmin, ymin, xmax, ymax, node))
	    return 0;
	int ret = 1;
	Rectangle2D.Float box = new Rectangle2D.Float(xmin, ymin, xmax-xmin, ymax-ymin);
	removeBorder(box, node);
	if (! this.tree.isLeaf(node)) {
	    float tw = this.sizeColumn.getFloatAt(node);
	    if (direction == DIRECTION_LEFT_TO_RIGHT) {
		float w = box.width;
		float x = box.x;
		for (RowIterator it = this.tree.childrenIterator(node); it.hasNext(); ) {
                    int child = it.nextRow();
		    float nw = w * this.sizeColumn.getFloatAt(child) / tw;
		    ret += visit(flip(direction),
				 x, box.y, x+nw, box.y+box.height,
				 child);
		    x += nw;
		}
	    }
	    else {
		float h = box.height;
		float y = box.y;
		for (RowIterator it = this.tree.childrenIterator(node); it.hasNext(); ) {
                    int child = it.nextRow();
		    float nh = h * this.sizeColumn.getFloatAt(child) / tw;
		    ret += visit(flip(direction),
				 box.x, y, box.x+box.width, y+nh, 
				 child);
		    y += nh;
		}
	    }
	}
	return ret;
    }

    /**
     * Returns the initialDirection.
     * @return short
     */
    public short getInitialDirection() {
	return this.initialDirection;
    }

    /**
     * Sets the initialDirection.
     * @param initialDirection The initialDirection to set
     */
    public void setInitialDirection(short initialDirection) {
	this.initialDirection = initialDirection;
    }

    /**
     * @see infovis.tree.visualization.treemap.Treemap#visit(Tree, NumberColumn, BorderDrawer, Rectangle2D, int)
     */
    public int visit(
		     Tree tree, NumberColumn sizeColumn, BorderDrawer border,
		     Rectangle2D bounds, int root) {
	this.tree = tree;
	setSizeColumn(sizeColumn);
	setBorder(border);
	start();
	int ret = visit(this.initialDirection, 
			(float)bounds.getX(),
			(float)bounds.getY(),
			(float)bounds.getMaxX(),
			(float)bounds.getMaxY(),
			root);
	finish();
	return ret;
    }

}
