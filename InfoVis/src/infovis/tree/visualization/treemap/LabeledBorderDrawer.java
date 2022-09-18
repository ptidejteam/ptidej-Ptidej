/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.visualization.treemap;

import infovis.Tree;
import infovis.tree.visualization.TreemapVisualization;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * Draw borders and labels for treemaps.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class LabeledBorderDrawer extends BorderDrawer {
	
    /**
     * Constructor for LabeledBorderDrawer.
     */
    public LabeledBorderDrawer(Tree tree) {
	super(tree, 3, 3, 3, 3);
    }


    /**
     * Constructor for LabeledBorderDrawer.
     * @param left
     * @param right
     * @param top
     * @param bottom
     */
    public LabeledBorderDrawer(Tree tree,
			       float left,
			       float right,
			       float top,
			       float bottom) {
	super(tree, left, right, top, bottom);
    }
    
    /**
     * @see infovis.tree.visualization.treemap.BorderDrawer#getTop(int)
     */
    public float getTop(int node) {
        if (this.tree.isLeaf(node))
            return super.getTop(node);
        return 12 + super.getTop(node);
            
    }

    /**
     * @see infovis.tree.visualization.treemap.BorderDrawer#paintBorder(Graphics2D, TreemapVisualization, Rectangle2D.Float, int)
     */
    public void paintBorder(
			    Graphics2D graphics,
			    TreemapVisualization visualization,
			    Rectangle2D.Float box,
			    int node) {
	super.paintBorder(graphics, visualization, box, node);
		
	String label = visualization.getLabelAt(node);
	if (label == null || label.length() == 0)
	    return;
	Shape clip = graphics.getClip();
        float top = getTop(node);
	this.rect.setRect(
		     box.x,
		     box.y,
		     box.width,
		     top);
        Shape s = visualization.transformShape(this.rect);
	graphics.clip(s);
        Rectangle2D bounds = s.getBounds2D();
	graphics.setColor(Color.BLACK);
	graphics.drawString(
            label,
            (float)(bounds.getX()+getLeft(node)),
            (float)(bounds.getY()+ top - 3));
	graphics.setClip(clip);
    }

}
