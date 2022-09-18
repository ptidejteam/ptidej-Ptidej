import infovis.Table;
import infovis.Tree;
import infovis.metadata.AdditiveAggregation;
import infovis.tree.DepthFirst;
import infovis.tree.visualization.IcicleTreeVisualization;

import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class EditableIcicleTree extends IcicleTreeVisualization {
    int startX;
    int startY;
    boolean dragging = false;
    int dragThreshold = 4;
    int draggedNode;

    /**
     * Constructor for EditableIcicleTree.
     */
    public EditableIcicleTree(Tree tree) {
        super(tree);
    }

    /**
     * @see infovis.Visualization#mousePressed(MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        this.startX = e.getX();
        this.startY = e.getY();
        super.mousePressed(e);
    }

    /**
     * @see infovis.Visualization#mouseDragged(MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
        if (! this.dragging &&
            (Math.abs(e.getX() - this.startX)+Math.abs(e.getY() - this.startY)) > this.dragThreshold) {
            this.draggedNode = pickTop(this.startX, this.startY, this.parent.getBounds());
            if (this.draggedNode != Table.NIL)
                this.dragging = true;
                this.startX = e.getX();
                this.startY = e.getY();
        }
        else if (this.dragging) {
            final int dx = e.getX() - this.startX;
            final int dy = e.getY() - this.startY;
            this.startX = e.getX();
            this.startY = e.getY();
            moveNodeBy(this.draggedNode, dx, dy);
            repaint();
        }
    }

    protected void moveNodeBy(int draggedNode, final int dx, final int dy) {
        DepthFirst.visit(this.tree, new DepthFirst.Visitor() {
            /**
             * @see infovis.tree.DepthFirst.Visitor#preorder(int)
             */
            public boolean preorder(int node) {
                Rectangle2D.Float rect = (Rectangle2D.Float)getShapeAt(node);
                if (rect == null)
                    return true;
                rect.x += dx;
                rect.y += dy;
                return true;
            }
        
            /**
             * @see infovis.tree.DepthFirst.Visitor#inorder(int)
             */
            public void inorder(int node) {
            }
        
            /**
             * @see infovis.tree.DepthFirst.Visitor#postorder(int)
             */
            public void postorder(int node) {
            }
        }, draggedNode);
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseReleased(MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        if (this.dragging) {
            // avoid the node picking itelf
            moveNodeBy(this.draggedNode, -1000, -1000);
            int newParent = pickTop(e.getX(), e.getY(), this.parent.getBounds());
            if (newParent != Table.NIL) {
                if (! this.tree.isAncestor(this.draggedNode, newParent)) {
                    this.tree.reparent(this.draggedNode, newParent);
                    setVisualColumn(VISUAL_SIZE, AdditiveAggregation.buildDegreeAdditiveWeight(this.tree));
                }
            }
            invalidate();
            this.dragging = false;
            this.draggedNode = Table.NIL;
        }
        super.mouseReleased(e);
    }

}
