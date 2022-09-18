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
 * Squarified Treemap Algorithm.
 * 
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 */
public class Squarified extends Treemap {

    /**
     * Constructor for Squarified.
     */
    public Squarified() {
    }
    
    public String getName() {
        return "Squarified";
    }

    /**
     * @see infovis.tree.visualization.treemap.Treemap#visit(Tree, NumberColumn, BorderDrawer, Rectangle2D, int)
     */
    public int visit(Tree tree, NumberColumn sizeColumn, BorderDrawer border,
		     Rectangle2D bounds, int root) {
	this.tree = tree;
	setSizeColumn(sizeColumn);
	setBorder(border);
	start();

	int ret = visit((float)bounds.getX(), (float)bounds.getY(), 
			(float)bounds.getMaxX(), (float)bounds.getMaxY(), 
			root);
	finish();

	return ret;
    }

	
    protected int visit(float xmin, float ymin, float xmax, float ymax, int node) {
	if (!beginBox(xmin, ymin, xmax, ymax, node)) {
	    return 0;
	}

	int ret = 1;
	Rectangle2D.Float box = new Rectangle2D.Float(xmin, ymin, xmax - 
						      xmin, ymax - ymin);
	removeBorder(box, node);

	if (! this.tree.isLeaf(node)) {
	    ret = visitStrips(box.x, box.y, box.x+box.width, box.y+box.height, node);
	}

	return ret;
    }

    protected boolean isVertical(float xmin, float ymin, float xmax, 
				 float ymax, int node) {
	return (xmax - xmin) > (ymax - ymin);
    }

    /**
     * Computes the rectangles that fill the containing rectangle allocated to
     * the specified node.
     * 
     */
    protected int visitStrips(float xmin, float ymin, float xmax, float ymax, 
			      int node) {
	float tw = this.sizeColumn.getFloatAt(node);
	int ret = 1;

	// The sizeColumn of the current node -- sum of weights of children nodes --
	// is tw.  It will fill a surface of width*height so the scale is
	// surface / tw.
	float scale = ((xmax - xmin) * (ymax - ymin)) / tw;

	if (scale == 0) {
	    return 0;
	}

	// Split in strips
	for (RowIterator it = this.tree.childrenIterator(node);
	     it.hasNext();) {
	    if (isVertical(xmin, ymin, xmax, ymax, node)) {
		// Vertical strip case, height is fixed, compute the heights of strips
		float h = ymax - ymin;
		float y = ymin; // vertical position of this stip
                RowIterator it2 = it.copy();
		// Compute the end of the strip, leaving the first of next strip
		// in the iterator.
		// invariant: squarify always advances the iterator or return 0.
		// returns the strip width, given its height.
		float width = squarify(it, h, scale);

		if (width == 0) {
		    return ret;
		}

		while(it2.peekRow() != it.peekRow()) {
                    int i = it2.nextRow();
		    // compute the node height.
		    float nh = (this.sizeColumn.getFloatAt(i) * scale) / width;

		    ret += visit(xmin, y, xmin + width, y + nh, i);
		    y += nh;
		}

		xmin += width;
	    }
	    else {
		float w = xmax - xmin;
		float x = xmin;
		RowIterator it2 = it.copy();

		float height = squarify(it, w, scale);

		if (height == 0) {
		    return ret;
		}

		while(it2.peekRow() != it.peekRow()) {
                    int i = it2.nextRow();
		    float nw = (this.sizeColumn.getFloatAt(i) * scale) / height;

		    ret += visit(x, ymin, x + nw, ymin + height, i);
		    x += nw;
		}

		ymin += height;
	    }
	}
		
	//assert(Math.abs(xmin-xmax)<1 || Math.abs(ymin-ymax)<1);

	return ret;
    }

    protected float squarify(RowIterator it, float length, 
			     float scale) {
	//assert (length != 0);

	// Find the strip or children whith the most sqare aspect ratio,
	// given the final length of the strip.
	float s = 0;

	// First, find an initial non-empty rectangle to start with
	while (it.hasNext() && (s == 0)) {
	    s = this.sizeColumn.getFloatAt(it.nextRow()) * scale;
	}

	// We have a first tentative width now
	float width = s / length;

	// We could have reached the end (the width might be zero then)
	if (!it.hasNext()) {
	    return width;
	}

	// Prepare to iterator until the the worst aspect ratio stop to improve.
	float s2 = s * s;
	float min_area = s;
	float max_area = s;
	float worst = Math.max(length / width, width / length);
	float w2 = length * length;

	while (it.hasNext()) {
	    // See if adding the next rectangle will improve the worst aspect ratio
	    float area = this.sizeColumn.getFloatAt(it.peekRow()) * scale;

	    // Skip empty rectangles.
	    if (area == 0) {
		it.nextRow();

		continue;
	    }

	    s += area;
	    s2 = s * s;

	    if (area < min_area) {
		min_area = area;
	    }
	    else if (area > max_area) {
		max_area = area;
	    }

	    float cur_worst = Math.max((w2 * max_area) / s2, 
				       s2 / (w2 * min_area));

	    if (cur_worst > worst) {
		// If result is worst, revert to previous area and return
		s -= area;

		break;
	    }

	    worst = cur_worst;
	    it.nextRow();
	}

	return s / length;
    }

}
