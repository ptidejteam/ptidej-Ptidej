/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization;

import infovis.Tree;
import infovis.column.BooleanColumn;
import infovis.column.FilterColumn;
import infovis.column.IntColumn;
import infovis.metadata.AdditiveAggregation;
import infovis.metadata.ValueCategory;
import infovis.tree.Algorithms;
import infovis.tree.DepthFirst;
import infovis.utils.IntVector;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class IcicleTreeVisualization extends TreeVisualization {
    float minItemHeight = 20;
    int maxDepth;
    IntColumn rainbow;

    static {
        setControlPanelCreator(IcicleTreeVisualization.class, IcicleTreeControlPanel.class);
    }
    
    /**
     * Constructor for IcicleTreeVisualization.
     * @param tree
     */
    public IcicleTreeVisualization(Tree tree) {
        super(tree);
        setVisualColumn(VISUAL_SIZE, AdditiveAggregation.findDegreeColumn(tree));
        this.rainbow = new IntColumn("#rainbow");
        ValueCategory.setValueCategory(this.rainbow, ValueCategory.TYPE_EXPLICIT);
        setVisualColumn(VISUAL_COLOR, this.rainbow);
        this.maxDepth = Algorithms.treeDepth(tree);
    }

    /**
     * Constructor for IcicleTreeVisualization.
     * @param tree
     * @param selection
     * @param filter
     */
    public IcicleTreeVisualization(
        Tree tree,
        BooleanColumn selection,
        FilterColumn filter) {
        super(tree, selection, filter);
        setVisualColumn(VISUAL_SIZE, AdditiveAggregation.findDegreeColumn(tree));
        this.maxDepth = Algorithms.treeDepth(tree);
    }

    /**
     * @see infovis.Visualization#computeShapes(Rectangle2D)
     */
    public void computeShapes(Rectangle2D bounds) {
        float scaleX = 1;
        float scaleY = 1;
        
        switch(this.orientation) {
        case ORIENTATION_NORTH:
        case ORIENTATION_SOUTH:
            scaleX = (float)bounds.getWidth() / this.sizeColumn.getIntAt(Tree.ROOT);
            scaleY = (float)bounds.getHeight() / this.maxDepth;
            break;
        case ORIENTATION_EAST:
        case ORIENTATION_WEST:
            scaleX = (float)bounds.getHeight() / this.sizeColumn.getIntAt(Tree.ROOT);
            scaleY = (float)bounds.getWidth() / this.maxDepth;
            break;
        }
        IcicleVisitor visitor = new IcicleVisitor(scaleX, scaleY);
        DepthFirst.visit(this.tree, visitor, Tree.ROOT);
    }

    
    class IcicleVisitor implements DepthFirst.Visitor {
        IntVector positions = new IntVector();
        float width;
        float height;
        int depth = 0;
        
        public IcicleVisitor(float width, float height) {
            this.width = width;
            this.height = height;
            this.positions.push(0);
            this.depth = -1;
            IcicleTreeVisualization.this.maxDepth = Algorithms.treeDepth(IcicleTreeVisualization.this.tree);
        }
        
        public int getPosition() {
            return this.positions.top();
        }
        
        void push() {
            this.positions.push(getPosition());
            this.depth++;
        }
        
        void pop() {
            this.positions.pop();
            this.depth--;
        }
        
        void setPosition(int pos) {
            this.positions.setTop(pos);
        }
        
        /**
         * @see infovis.tree.DepthFirst.Visitor#preorder(int)
         */
        public boolean preorder(int node) {
            push();
            int position = getPosition();
            Rectangle2D.Float rect = (Rectangle2D.Float)getShapeAt(node);
            if (rect == null) {
                rect = new Rectangle2D.Float();
                setShapeAt(node, rect);
            }
            int dp = IcicleTreeVisualization.this.sizeColumn.getIntAt(node);
            int color = Color.HSBtoRGB((position + (float)dp/2)/IcicleTreeVisualization.this.sizeColumn.getIntAt(Tree.ROOT),
                1.0f, 1.0f);
            IcicleTreeVisualization.this.rainbow.setExtend(node, color);
            switch(IcicleTreeVisualization.this.orientation) {
            case ORIENTATION_NORTH:
                rect.x = position * this.width;
                rect.y = (IcicleTreeVisualization.this.maxDepth-this.depth - 1) * this.height;
                rect.width = dp * this.width;
                rect.height = this.height;
                break;
            case ORIENTATION_SOUTH:
                rect.x = position * this.width;
                rect.y = this.depth * this.height;
                rect.width = dp * this.width;
                rect.height = this.height;
                break;
            case ORIENTATION_WEST:
                rect.y = position * this.width;
                rect.x = (IcicleTreeVisualization.this.maxDepth - this.depth - 1) * this.height;
                rect.height = dp * this.width;
                rect.width = this.height;
                break;
            case ORIENTATION_EAST:
                rect.y = position * this.width;
                rect.x = this.depth * this.height;
                rect.height = dp * this.width;
                rect.width = this.height;
                break;
            }
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
            pop();
            int position = getPosition();
            position += IcicleTreeVisualization.this.sizeColumn.getIntAt(node);
            setPosition(position);
        }

    }
}
