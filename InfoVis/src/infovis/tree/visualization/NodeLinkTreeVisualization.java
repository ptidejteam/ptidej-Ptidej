/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License a
 * copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.tree.visualization;

import infovis.Table;
import infovis.Tree;
import infovis.column.BooleanColumn;
import infovis.column.FilterColumn;
import infovis.column.FloatColumn;
import infovis.column.IntColumn;
import infovis.utils.RowIterator;
import infovis.visualization.Fisheyes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

/**
 * Node Link Diagram Visualization of Trees.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NodeLinkTreeVisualization extends TreeVisualization {
    protected int margin = 10;
    protected FloatColumn prelim;
    protected FloatColumn modifier;
    protected IntColumn leftNeighbor;
    protected IntColumn prev;
    protected IntColumn next;
    private int[] previousNodeAtLevel;
    private float[] maxNodeHightAtLevel;
    private float[] maxNodeWidthAtLevel;
    protected int maxDepth = Integer.MAX_VALUE;
    protected float siblingSeparation = 3;
    protected float subtreeSeparation = 10;
    protected float levelSeparation = (float) this.maxSize;
    private float topXAdjustment = 0;
    private float topYAdjustment = 0;
    private Line2D.Float tmpLine = new Line2D.Float();
    private Rectangle2D.Float bbox = new Rectangle2D.Float();
    protected boolean rescale = true;

    static {
        setControlPanelCreator(
            NodeLinkTreeVisualization.class,
            NodeLinkTreeControlPanel.class);
    }

    /**
     * Constructor for NodeLinkTreeVisualization.
     *
     * @param tree
     */
    public NodeLinkTreeVisualization(Tree tree) {
        super(tree);
    }

    /**
     * Constructor for NodeLinkTreeVisualization.
     *
     * @param tree
     * @param selection
     * @param filter
     */
    public NodeLinkTreeVisualization(
        Tree tree,
        BooleanColumn selection,
        FilterColumn filter) {
        super(tree, selection, filter);
    }

    /**
     * @see infovis.Visualization#computeShapes(Rectangle2D)
     */
    public void computeShapes(Rectangle2D bounds) {
        this.prelim = new FloatColumn("#prelim");
        this.modifier = new FloatColumn("#modifier");
        this.leftNeighbor = new IntColumn("#leftNeighbor");
        this.prev = new IntColumn("#prev");
        this.next = new IntColumn("#next");
        for (RowIterator iter = iterator(); iter.hasNext();) {
            int row = iter.nextRow();
            float s = (float) getSizeAt(row);
            Rectangle2D.Float rect =
                (Rectangle2D.Float) getShapeAt(row);
            if (rect == null) {
                rect = new Rectangle2D.Float(0, 0, s, s);
                setShapeAt(row, rect);
            }
            else {
                rect.setRect(0, 0, s, s);
            }
        }
        positionTree(this.root, bounds);
        if (! bounds.contains(this.bbox)) {
            rescaleTree(bounds);
        }

        this.prelim = null;
        this.modifier = null;
        this.leftNeighbor = null;
        this.prev = null;
        this.next = null;
    }
    
    protected void rescaleTree(Rectangle2D bounds) {
        float sx = (float)bounds.getWidth() / this.bbox.width;
        float sy = (float)bounds.getHeight() / this.bbox.height;
        float scale = (sx < sy) ? sx : sy;
        if (scale > 1)
            scale = 1;
        for (RowIterator iter = iterator(); iter.hasNext();) {
            int row = iter.nextRow();
            Rectangle2D.Float rect =
                (Rectangle2D.Float) getShapeAt(row);
            if (rect == null) {
                continue;
            }
            rect.x = (rect.x - this.bbox.x) * scale + (float)bounds.getX();
            rect.y = (rect.y - this.bbox.y) * scale + (float)bounds.getY();
            rect.width *= scale;
            rect.height *= scale;
        }
    }

    /**
     * @see infovis.Visualization#paintShape(Graphics2D, int)
     */
    public void paintShape(Graphics2D graphics, int row) {
        super.paintShape(graphics, row);
        for (RowIterator iter = childrenIterator(row);
            iter.hasNext();
            ) {
            paintLink(graphics, row, iter.nextRow());
        }
    }

    /**
     * Paint a link
     *
     * @param graphics DOCUMENT ME!
     * @param fromNode DOCUMENT ME!
     * @param toNode DOCUMENT ME!
     */
    public void paintLink(
        Graphics2D graphics,
        int fromNode,
        int toNode) {
        Shape fromShape = getShapeAt(fromNode);
        Shape toShape = getShapeAt(toNode);
        if (fromShape == null || toShape == null)
            return;
        linkStart(fromShape, this.tmpLine);
        linkEnd(toShape, this.tmpLine);
        graphics.setColor(Color.BLACK);
        graphics.draw(this.tmpLine);
    }

    /**
     * Computes the starting point of link.
     *
     * @param s DOCUMENT ME!
     * @param line DOCUMENT ME!
     */
    protected void linkStart(Shape s, Line2D.Float line) {
        Rectangle2D bounds = s.getBounds2D();
        switch (getOrientation()) {
            case ORIENTATION_SOUTH :
                line.x1 = (float) bounds.getCenterX();
                line.y1 = (float) bounds.getMaxY();
                break;
            case ORIENTATION_NORTH :
                line.x1 = (float) bounds.getCenterX();
                line.y1 = (float) bounds.getMinY();
                break;
            case ORIENTATION_EAST :
                line.x1 = (float) bounds.getMaxX();
                line.y1 = (float) bounds.getCenterY();
                break;
            case ORIENTATION_WEST :
                line.x1 = (float) bounds.getMinX();
                line.y1 = (float) bounds.getCenterY();
                break;
        }
        Fisheyes f = getFisheyes();
        if (f != null) {
            float scale = f.getScale(line.x1, line.y1);
            line.x1 = f.transformX(line.x1, scale);
            line.y1 = f.transformY(line.y1, scale);
        }
    }

    /**
     * Compute the ending point of link.
     *
     * @param s DOCUMENT ME!
     * @param line DOCUMENT ME!
     */
    protected void linkEnd(Shape s, Line2D.Float line) {
        Rectangle2D bounds = s.getBounds2D();
        switch (getOrientation()) {
            case ORIENTATION_NORTH :
                line.x2 = (float) bounds.getCenterX();
                line.y2 = (float) bounds.getMaxY();
                break;
            case ORIENTATION_SOUTH :
                line.x2 = (float) bounds.getCenterX();
                line.y2 = (float) bounds.getMinY();
                break;
            case ORIENTATION_WEST :
                line.x2 = (float) bounds.getMaxX();
                line.y2 = (float) bounds.getCenterY();
                break;
            case ORIENTATION_EAST :
                line.x2 = (float) bounds.getMinX();
                line.y2 = (float) bounds.getCenterY();
                break;
        }
        Fisheyes f = getFisheyes();
        if (f != null) {
            float scale = f.getScale(line.x2, line.y2);
            line.x2 = f.transformX(line.x2, scale);
            line.y2 = f.transformY(line.y2, scale);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param left DOCUMENT ME!
     */
    public void setLeftNeighbor(int node, int left) {
        this.leftNeighbor.setExtend(node, left);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getLeftNeighbor(int node) {
        return this.leftNeighbor.get(node);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean hasLeftSibling(int node) {
        return this.prev.get(node) != Table.NIL;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getLeftSibling(int node) {
        return this.prev.get(node);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param left DOCUMENT ME!
     */
    public void setLeftSibling(int node, int left) {
        this.prev.setExtend(node, left);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public int getRightSibling(int node) {
        return this.next.get(node);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param right DOCUMENT ME!
     */
    public void setRightSibling(int node, int right) {
        this.next.setExtend(node, right);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float getXCoord(int node) {
        Rectangle2D.Float rect = (Rectangle2D.Float) getShapeAt(node);
        if (rect == null)
            return 0;
        else
            return rect.x;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param x DOCUMENT ME!
     */
    public void setXCoord(int node, float x) {
        Rectangle2D.Float rect = (Rectangle2D.Float) getShapeAt(node);
        if (rect != null)
            rect.x = x;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float getYCoord(int node) {
        Rectangle2D.Float rect = (Rectangle2D.Float) getShapeAt(node);
        if (rect == null)
            return 0;
        else
            return rect.y;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param y DOCUMENT ME!
     */
    public void setYCoord(int node, float y) {
        Rectangle2D.Float rect = (Rectangle2D.Float) getShapeAt(node);
        if (rect != null)
            rect.y = y;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float getLayoutSize(int node) {
        switch (getOrientation()) {
            case ORIENTATION_NORTH :
            case ORIENTATION_SOUTH :
                return getWidth(node);
            case ORIENTATION_EAST :
            case ORIENTATION_WEST :
                return getHeight(node);
        }
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float getOppositeLayoutSize(int node) {
        switch (getOrientation()) {
            case ORIENTATION_NORTH :
            case ORIENTATION_SOUTH :
                return getHeight(node);
            case ORIENTATION_EAST :
            case ORIENTATION_WEST :
                return getWidth(node);
        }
        return 0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param leftNode DOCUMENT ME!
     * @param rightNode DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float getMeanNodeSize(int leftNode, int rightNode) {
        float nodeSize = 0;
        if (leftNode != Table.NIL)
            nodeSize += getLayoutSize(leftNode) / 2;
        if (rightNode != Table.NIL)
            nodeSize += getLayoutSize(rightNode) / 2;
        return nodeSize;
        //        return getLayoutSize(leftNode);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected float getHeight(int node) {
        Rectangle2D.Float rect = (Rectangle2D.Float) getShapeAt(node);
        if (rect != null)
            return rect.height;
        return (float) this.defaultSize;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected float getWidth(int node) {
        Rectangle2D.Float rect = (Rectangle2D.Float) getShapeAt(node);
        if (rect != null)
            return rect.width;
        return (float) this.defaultSize;
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float getPrelim(int node) {
        return this.prelim.get(node);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param p DOCUMENT ME!
     */
    public void setPrelim(int node, float p) {
        this.prelim.setExtend(node, p);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float getModifier(int node) {
        return this.modifier.get(node);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param mod DOCUMENT ME!
     */
    public void setModifier(int node, float mod) {
        this.modifier.setExtend(node, mod);
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     */
    public void positionTree(int node, Rectangle2D bounds) {
        if (node != Table.NIL) {
            initPrevNodeList();
            setLeftSibling(node, Table.NIL);
            setRightSibling(node, Table.NIL);
            firstWalk(node, 0);
            this.topXAdjustment = (float)getMaxSize()/2 + (float)bounds.getX();
            this.topYAdjustment = (float)bounds.getY();
            this.bbox.setRect((float)bounds.getX(), (float)bounds.getY(), getMaxSize(), getMaxSize());
            secondWalk(node, 0, 0);
            resetPrevNodeList();
        }
    }

    /**
     * DOCUMENT ME!
     */
    protected void resetPrevNodeList() {
        this.previousNodeAtLevel = null;
        this.maxNodeHightAtLevel = null;
        this.maxNodeWidthAtLevel = null;
    }

    /**
     * DOCUMENT ME!
     */
    protected void initPrevNodeList() {
        this.previousNodeAtLevel = new int[100];
        Arrays.fill(this.previousNodeAtLevel, 0, 100, Table.NIL);
        this.maxNodeHightAtLevel = new float[100];
        this.maxNodeWidthAtLevel = new float[100];
    }

    /**
     * Computes the preliminary position and modifier.
     *
     * @param node appex node.
     * @param level depth level.
     */
    protected void firstWalk(int node, int level) {
        setModifier(node, 0);
        setPrelim(node, 0);

        updateLevelHeight(node, level);
        updateLevelWidth(node, level);
        updateLeftAndRightNeighborsAtLevel(node, level);

        if (isLeaf(node) || level == this.maxDepth) {
            if (hasLeftSibling(node)) {
                int left = getLeftSibling(node);
                setPrelim(
                    node,
                    getPrelim(left)
                        + this.siblingSeparation
                        + getMeanNodeSize(left, node));
                //getLayoutSize(left));
            }
            else {
                setPrelim(node, 0);
            }
        }
        else {
            int rightmost = Table.NIL;
            for (RowIterator iter = childrenIterator(node);
                iter.hasNext();
                ) {
                int child = iter.nextRow();
                // update the left/right sibling tables here
                setLeftSibling(child, rightmost);
                if (rightmost != Table.NIL) {
                    setRightSibling(rightmost, child);
                }
                firstWalk(child, level + 1);
                rightmost = child;
            }
            setRightSibling(rightmost, Table.NIL);

            int leftmost = getFirstChild(node);
            float midpoint = (getPrelim(leftmost) + getPrelim(rightmost)) / 2;

            if (hasLeftSibling(node)) {
                int left = getLeftSibling(node);
                setPrelim(node, getPrelim(left) + this.siblingSeparation +
                //getLayoutSize(left));
                getMeanNodeSize(left, node));
                setModifier(node, getPrelim(node) - midpoint);
                apportion(node, level);
            }
            else {
                setPrelim(node, midpoint);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param level DOCUMENT ME!
     */
    protected void apportion(int node, int level) {
        int leftmost = getFirstChild(node);
        int neighbor = getLeftNeighbor(leftmost);
        // neighbor is on the left of leftmost
        int depthToStop = this.maxDepth - level;

        for (int compareDepth = 1;
            leftmost != Table.NIL
                && neighbor != Table.NIL
                && compareDepth <= depthToStop;
            ) {
            float leftModSum = 0;
            float rightModSum = 0;
            int ancestorLeftmost = leftmost;
            int ancestorNeighbor = neighbor;

            for (int i = 0; i < compareDepth; i++) {
                ancestorLeftmost = getParent(ancestorLeftmost);
                ancestorNeighbor = getParent(ancestorNeighbor);
                rightModSum += getModifier(ancestorLeftmost);
                leftModSum += getModifier(ancestorNeighbor);
            }

            float moveDistance =
                (getPrelim(neighbor)
                    + leftModSum
                    + this.subtreeSeparation
                    + getMeanNodeSize(leftmost, neighbor))
                    - (getPrelim(leftmost) + rightModSum);

            if (moveDistance > 0) {
                int tmp = node;
                int leftSiblings = 0;
                while (tmp != Table.NIL && tmp != ancestorNeighbor) {
                    leftSiblings++;
                    tmp = getLeftSibling(tmp);
                }

                if (tmp != Table.NIL) {
                    float portion = moveDistance / leftSiblings;
                    tmp = node;

                    while (tmp != ancestorNeighbor) {
                        setPrelim(tmp, getPrelim(tmp) + moveDistance);
                        setModifier(
                            tmp,
                            getModifier(tmp) + moveDistance);
                        moveDistance -= portion;
                        tmp = getLeftSibling(tmp);
                    }
                }
            }
//            else {
//                return;
//            }
            compareDepth++;
            if (isLeaf(leftmost)) {
                leftmost = getLeftMost(node, 0, compareDepth);
            }
            else {
                leftmost = getFirstChild(leftmost);
            }
            if (leftmost != Table.NIL)
                neighbor = getLeftNeighbor(leftmost);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param node DOCUMENT ME!
     * @param level DOCUMENT ME!
     * @param depth DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    protected int getLeftMost(int node, int level, int depth) {
        if (level >= depth)
            return node;
        if (isLeaf(node))
            return Table.NIL;
        for (RowIterator iter = childrenIterator(node);
            iter.hasNext();
            ) {
            int child = iter.nextRow();
            int leftMost = getLeftMost(child, level + 1, depth);
            if (leftMost != Table.NIL)
                return leftMost;
        }
        return Table.NIL;
    }

    private void updateLevelHeight(int node, int level) {
        if (this.maxNodeHightAtLevel[level] < getHeight(node)) {
            this.maxNodeHightAtLevel[level] = getHeight(node);
        }
    }

    private void updateLevelWidth(int node, int level) {
        if (this.maxNodeWidthAtLevel[level] < getWidth(node)) {
            this.maxNodeWidthAtLevel[level] = getWidth(node);
        }
    }

    private void updateLeftAndRightNeighborsAtLevel(
        int node,
        int level) {
        int prev = this.previousNodeAtLevel[level];
        setLeftNeighbor(node, prev);
        this.previousNodeAtLevel[level] = node;
    }
    
    private void updateBbox(int node) {
        this.bbox.add((Rectangle2D.Float)getShapeAt(node));        
    }

    /**
     * Second pass to propagate the modifiers to actual positions.
     *
     * @param node the appex node.
     * @param level the depth level.
     * @param xModifierSum the accumulated modifiers for X.
     * @param yModifierSum the accumulated modifiers for Y.
     */
    protected void secondWalk(int node, int level, float ModifierSum) {
        if (level < this.maxDepth) {
            float x = 0;
            float y = 0;

            switch (getOrientation()) {
                case ORIENTATION_NORTH :
                    x = this.topXAdjustment + getPrelim(node) + ModifierSum
                        - getLayoutSize(node)/2;
                    y = this.topYAdjustment
                        - level * ((float) getMaxSize() + this.levelSeparation);
                    break;
                case ORIENTATION_SOUTH :
                    x = this.topXAdjustment + getPrelim(node) + ModifierSum
                        - getLayoutSize(node)/2;
                    y = this.topYAdjustment
                        + level * ((float) getMaxSize() + this.levelSeparation);
                    break;
                case ORIENTATION_EAST :
                    y = this.topXAdjustment + getPrelim(node) + ModifierSum
                       - getLayoutSize(node)/2;
                    x = this.topYAdjustment
                        + level * ((float) getMaxSize() + this.levelSeparation);
                    break;
                case ORIENTATION_WEST :
                    y = this.topXAdjustment + getPrelim(node) + ModifierSum
                       - getLayoutSize(node)/2;
                    x = this.topYAdjustment
                        - level * ((float) getMaxSize() + this.levelSeparation);
                    break;
            }

            setXCoord(node, x);
            setYCoord(node, y);
            updateBbox(node);
            if (!isLeaf(node)) {
                secondWalk(
                    getFirstChild(node),
                    level + 1,
                    ModifierSum + getModifier(node));
            }
            int right = getRightSibling(node);
            if (right != Table.NIL) {
                secondWalk(right, level, ModifierSum);
            }
        }
    }

    public float getSiblingSeparation() {
        return this.siblingSeparation;
    }

    public void setSiblingSeparation(float f) {
        if (f < 0)
            f = 0;
        this.siblingSeparation = f;
    }

    public float getSubtreeSeparation() {
        return this.subtreeSeparation;
    }

    public void setSubtreeSeparation(float f) {
        if (f < 0)
            f = 0;
        this.subtreeSeparation = f;
    }

    public float getLevelSeparation() {
        return this.levelSeparation;
    }

    public void setLevelSeparation(float f) {
        this.levelSeparation = f;
    }

}
