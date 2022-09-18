/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License a
 * copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.tree.visualization;

import infovis.Column;
import infovis.Table;
import infovis.Tree;
import infovis.column.BooleanColumn;
import infovis.column.ColumnFilter;
import infovis.column.DoubleColumn;
import infovis.column.IntColumn;
import infovis.column.NumberColumn;
import infovis.metadata.AdditiveAggregation;
import infovis.tree.DepthFirst;
import infovis.tree.visualization.treemap.BorderDrawer;
import infovis.tree.visualization.treemap.LabeledBorderDrawer;
import infovis.tree.visualization.treemap.Treemap;
import infovis.tree.visualization.treemap.TreemapControlPanel;
import infovis.visualization.VisualColumnProxy;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * Visualization of a Tree using a Treemap layout.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreemapVisualization extends TreeVisualization {
    /** The treemap algorithm */
    protected Treemap treemap;
    /** The border drawer */
    protected BorderDrawer border;
    /** The additive weight is computed and stored in this column */
    protected DoubleColumn weightColumn;

    public static final String WEIGHT_COLUMN = "#weight";

    static {
        setControlPanelCreator(
            TreemapVisualization.class,
            TreemapControlPanel.class);
    }

    /**
     * Creates a new TreemapVisualization object.
     *
     * @param tree the Tree
     * @param size the size column.
     * @param treemap the Treemap algorithm.
     * @param border The BorderDrawer.
     */
    public TreemapVisualization(
        Tree tree,
        NumberColumn size,
        Treemap treemap,
        BorderDrawer border) {
        super(tree);
        this.treemap = treemap;
        setVisualColumn(VISUAL_SIZE, size);
        setBorder(border);
    }

    /**
     * Creates a new TreemapVisualization object.
     *
     * @param tree the Tree
     * @param size the size column.
     * @param treemap the Treemap algorithm.
     */
    public TreemapVisualization(
        Tree tree,
        NumberColumn size,
        Treemap treemap) {
        this(tree, size, treemap, new LabeledBorderDrawer(tree));
    }

    protected void declareVisualColumns() {
        super.declareVisualColumns();
        ColumnFilter filter =
            new AdditiveAggregation.NonAdditiveFilter(this.tree);
        setVisualColumnFilter(VISUAL_SORT, filter);
        setVisualColumnInvalidate(VISUAL_SORT, true);
        setVisualColumnFilter(VISUAL_SIZE, filter);
        setVisualColumnInvalidate(VISUAL_SIZE, true);
        putVisualColumn(VISUAL_SIZE,
            new VisualColumnProxy(getVisualColumnDescriptor(VISUAL_SIZE)) {
            public void setColumn(Column column) {
                super.setColumn(column);
                TreemapVisualization.this.weightColumn = null;
            }
        });
    }

    /**
     * @see infovis.Visualization#getShapeAt(int)
     */
    public Shape getShapeAt(int row) {
        Shape s;
        do {
            s = super.getShapeAt(row);
            row = this.tree.getParent(row);
        }
        while (s == null && row != Table.NIL);
        return s;
    }

    public void paintItems(
        final Graphics2D graphics,
        Rectangle2D bounds) {
        TreemapVisitor visitor = new TreemapVisitor() {
            /**
             * @see infovis.tree.DepthFirst.Visitor#preorder(int)
             */
            public boolean preorder(int node) {
                if (!isFiltered(node)) {
                    Shape s = getShapeAt(node);
                    if (s != null)
                        TreemapVisualization.this.border.paint(
                            graphics,
                            TreemapVisualization.this,
                            (Rectangle2D.Float) s,
                            node);
                    else
                        return false;
                }
                return true;
            }
        };
        DepthFirst.visit(this, visitor, this.root);
    }

    /**
     * @see infovis.Visualization#computeShapes(Rectangle2D)
     */
    public void computeShapes(Rectangle2D bounds) {
        this.treemap.setShapesColumn(getShapes());
        getWeightColumn();
        this.treemap.visit(this, this.weightColumn, this.border, bounds, this.root);
    }

    public DoubleColumn getWeightColumn() {
        if (this.weightColumn == null) {
            this.weightColumn =
                new DoubleColumn(WEIGHT_COLUMN, this.tree.getRowCount());
            if (this.sizeColumn == null) {
                AdditiveAggregation.buildDegreeAdditiveWeight(
                    this.weightColumn,
                    this);
            }
            else {
                AdditiveAggregation.buildAdditiveWeight(
                    this.weightColumn,
                    this,
                    this.sizeColumn);
            }
        }
        return this.weightColumn;
    }
//
//    public boolean setSortColumn(Column sortColumn) {
//        if (sortColumn == sizeColumn)
//            return super.setSortColumn(getWeightColumn());
//        return super.setSortColumn(sortColumn);
//    }

    /**
     * @see infovis.Visualization#pickTop(Rectangle2D hitBox, Rectangle2D)
     */
    public int pickTop(final Rectangle2D hitBox, Rectangle2D bounds) {
        validateShapes(bounds);
        TreemapVisitor visitor = new TreemapVisitor() {
            public boolean preorder(int node) {
                Rectangle2D.Float b =
                    (Rectangle2D.Float) getShapeAt(node);
                if (getLastPick() != -1 || isFiltered(node))
                    return false;
                if (b == null) {
                    return true;
                    // no shape computed so we are too small.
                }

                return b.intersects(hitBox);
            }

            public void postorder(int node) {
                if (getLastPick() == -1)
                    setLastPick(node);
            }
        };
        DepthFirst.visit(this, visitor, this.root);
        return visitor.getLastPick();
    }

    /**
     * @see infovis.Visualization#pickAll(Rectangle2D, Rectangle2D,
     *      BooleanColumn)
     */
    public BooleanColumn pickAll(
        final Rectangle2D hitBox,
        Rectangle2D bounds,
        BooleanColumn pick) {
        validateShapes(bounds);
        if (pick == null)
            pick = new BooleanColumn("pickAll");
        else
            pick.clear();
        final BooleanColumn p = pick;
        TreemapVisitor visitor = new TreemapVisitor() {
            public boolean preorder(int node) {
                Rectangle2D.Float b =
                    (Rectangle2D.Float) getShapeAt(node);
                if (isFiltered(node))
                    return false;
                if (b == null) {
                    return true;
                }

                return b.intersects(hitBox);
            }

            public void postorder(int node) {
                if (TreemapVisualization.this.tree.isLeaf(node))
                    p.setExtend(node, true);
            }
        };
        DepthFirst.visit(this, visitor, this.root);
        return pick;
    }

    /**
     * @see infovis.Visualization#pickAll(Rectangle2D, Rectangle2D,
     *      IntColumn)
     */
    public IntColumn pickAll(
        final Rectangle2D hitBox,
        Rectangle2D bounds,
        IntColumn pick) {
        validateShapes(bounds);
        if (pick == null)
            pick = new IntColumn("pickAll");
        else
            pick.clear();
        final IntColumn p = pick;
        TreemapVisitor visitor = new TreemapVisitor() {
            public boolean preorder(int node) {
                Rectangle2D.Float b =
                    (Rectangle2D.Float) getShapeAt(node);
                if (isFiltered(node))
                    return false;

                if (b == null) {
                    return true;
                }

                return b.intersects(hitBox);
            }

            public void postorder(int node) {
                if (TreemapVisualization.this.tree.isLeaf(node))
                    p.add(node);
            }
        };
        DepthFirst.visit(this, visitor, this.root);
        return pick;
    }
    
    protected void permuteRows() {
        super.permuteRows();
        this.weightColumn = null;
    }


    /**
     * Returns the Treemap.
     *
     * @return Treemap
     */
    public Treemap getTreemap() {
        return this.treemap;
    }

    /**
     * Sets the treemap.
     *
     * @param treemap The treemap to set
     */
    public void setTreemap(Treemap treemap) {
        this.treemap = treemap;
        invalidate();
    }

    static class TreemapVisitor implements DepthFirst.Visitor {
        protected int depth;
        protected int lastPick;

        public TreemapVisitor() {
            this.depth = 0;
            this.lastPick = -1;
        }

        public boolean preorder(int node) {
            this.depth++;
            return true;
        }

        public void postorder(int node) {
            this.depth--;
        }

        public void inorder(int node) {
        }

        /**
         * Returns the lastPick.
         *
         * @return int
         */
        public int getLastPick() {
            return this.lastPick;
        }

        /**
         * Sets the lastPick.
         *
         * @param lastPick The lastPick to set
         */
        public void setLastPick(int lastPick) {
            this.lastPick = lastPick;
        }
    }

    /**
     * Returns the border.
     *
     * @return BorderDrawer
     */
    public BorderDrawer getBorder() {
        return this.border;
    }

    /**
     * Sets the border.
     *
     * @param border The border to set
     */
    public void setBorder(BorderDrawer border) {
        this.border = border;
        repaint();
    }

    /**
     * @see java.awt.event.MouseAdapter#mouseClicked(MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getClickCount() == 1
            && e.getButton() != MouseEvent.BUTTON1) {
            setRoot(Tree.ROOT);
        }
        if (e.getClickCount() != 2) {
            return;
        }
        final int x = e.getX();
        final int y = e.getY();
        TreemapVisitor visitor = new TreemapVisitor() {
            public boolean preorder(int node) {
                Rectangle2D.Float b =
                    (Rectangle2D.Float) getShapeAt(node);

                if (b == null) {
                    return false;
                }

                return b.contains(x, y);
            }

            public void postorder(int node) {
                if (getLastPick() == -1)
                    setLastPick(node);
            }
        };
        DepthFirst.visit(this.tree, visitor, this.root);
        int r = visitor.getLastPick();
        if (r != -1)
            setRoot(r);
    }
}
