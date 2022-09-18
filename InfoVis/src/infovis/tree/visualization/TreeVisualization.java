/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization;

import infovis.Column;
import infovis.Table;
import infovis.Tree;
import infovis.Visualization;
import infovis.column.BooleanColumn;
import infovis.column.ColumnFilter;
import infovis.column.FilterColumn;
import infovis.column.ObjectColumn;
import infovis.metadata.AdditiveAggregation;
import infovis.metadata.Aggregation;
import infovis.metadata.AggregationCategory;
import infovis.metadata.AggregationConstants;
import infovis.metadata.AtLeafAggregation;
import infovis.tree.DepthFirst;
import infovis.utils.ArrayChildrenIterator;
import infovis.utils.IntVector;
import infovis.utils.NullRowIterator;
import infovis.utils.RowIterator;
import infovis.utils.Sort;
import infovis.visualization.VisualColumnProxy;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Abstract base class for tree visualizations.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class TreeVisualization
    extends Visualization
    implements Tree, DepthFirst.Visitor {
    public static final String SORTEDCHILDREN_COLUMN =
        "#sortedChildren";
    protected Tree tree;
    protected int root;
    volatile Graphics2D graphics;
    protected ObjectColumn sortedChildren;

    static {
        setControlPanelCreator(
            TreeVisualization.class,
            TreeControlPanel.class);
    }

    /**
     * Constructor for TreeVisualization.
     * @param tree the Tree.
     */
    public TreeVisualization(Tree tree) {
        this(tree, null, null);
    }

    public TreeVisualization(
        Tree tree,
        BooleanColumn selection,
        FilterColumn filter) {
        super(tree, selection, filter);
        this.tree = tree;
        tree.createDegreeColumn();
        tree.createDepthColumn();
        this.root = Tree.ROOT;
        this.sortedChildren =
            ObjectColumn.findColumn(tree, SORTEDCHILDREN_COLUMN);
    }

    protected void declareVisualColumns() {
        super.declareVisualColumns();
        ColumnFilter filter =
            new AdditiveAggregation.NonAdditiveFilter(this.tree);
        setVisualColumnFilter(VISUAL_SORT, filter);
        putVisualColumn(
            VISUAL_SORT,
            new VisualColumnProxy(
                getVisualColumnDescriptor(VISUAL_SORT)) {
            public void setColumn(Column sortColumn) {
                if (sortColumn != null) {
                    Aggregation aggr = null;
                    if (AtLeafAggregation.isAtLeaf(sortColumn, TreeVisualization.this.tree)
                        == AggregationConstants.AGGREGATE_YES) {
                        // choose additive aggregation as default, doesn't make sense for dates
                        aggr = AdditiveAggregation.sharedInstance();
                    }
                    else {
                        AggregationCategory factory =
                            AggregationCategory.sharedInstance();
                        aggr = factory.getAggregation(sortColumn, TreeVisualization.this);
                    }
                    if (aggr != null) {
                        Column col =
                            aggr.aggregate(sortColumn, TreeVisualization.this, null);
                        if (col != null) {
                            sortColumn = col;
                        }
                    }
                }
                super.setColumn(sortColumn);
            }
        });
    }

    /**
     * Returns the tree.
     * @return Tree
     */
    public Tree getTree() {
        return this.tree;
    }

    /**
     * @see infovis.tree.DepthFirst.Visitor#preorder(int)
     */
    public boolean preorder(int node) {
        if (!isFiltered(node)) {
            paintShape(this.graphics, node);
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
    }

    /**
     * @see infovis.Visualization#paintItems(Graphics2D, Rectangle2D)
     */
    public void paintItems(Graphics2D graphics, Rectangle2D bounds) {
        this.graphics = graphics;
        DepthFirst.visit(this, this, this.root);
    }

    /**
     * Returns the root.
     * @return int
     */
    public int getRoot() {
        return this.root;
    }

    /**
     * Sets the root.
     * @param root The root to set
     */
    public void setRoot(int root) {
        if (this.root != root) {
            if (!this.tree.isRowValid(root))
                return;
            if (root != Tree.ROOT && this.tree.isLeaf(root))
                root = this.tree.getParent(root);
            this.root = root;
            invalidate();
        }
    }

//    public boolean setSortColumn(Column sortColumn) {
//        if (sortColumn != null) {
//            Aggregation aggr = null;
//            if (AtLeafAggregation.isAtLeaf(sortColumn, tree)
//                == Aggregation.AGGREGATE_YES) {
//                // choose additive aggregation as default, doesn't make sense for dates
//                aggr = AdditiveAggregation.sharedInstance();
//            }
//            else {
//                AggregationCategory factory =
//                    AggregationCategory.sharedInstance();
//                aggr = factory.getAggregation(sortColumn, this);
//            }
//            if (aggr != null) {
//                Column col = aggr.aggregate(sortColumn, this, null);
//                if (col != null) {
//                    sortColumn = col;
//                }
//            }
//        }
//        return super.setVisualColumn(VISUAL_SORT, sortColumn);
//    }

    protected void permuteRows() {
        super.permuteRows();
        if (this.permutation == null) {
            this.sortedChildren.clear();
            return;
        }
        DepthFirst.Visitor visitor = new DepthFirst.Visitor() {
            public boolean preorder(int node) {
                if (TreeVisualization.this.permutation.getInverse(node) == -1) {
                    TreeVisualization.this.sortedChildren.setValueUndefined(node, true);
                    return false;
                }
                IntVector children = new IntVector();
                for (RowIterator citer = TreeVisualization.this.tree.childrenIterator(node);
                    citer.hasNext();
                    ) {
                    int c = citer.nextRow();
                    if (TreeVisualization.this.permutation.getInverse(c) != -1)
                        children.push(c);
                }
                if (children.size() == 0) {
                    TreeVisualization.this.sortedChildren.setValueUndefined(node, true);
                }
                else {
                    int[] cTable = children.toArray(null);
                    if (cTable.length > 1 && TreeVisualization.this.comparator != null)
                        Sort.sort(cTable, TreeVisualization.this.comparator);
                    TreeVisualization.this.sortedChildren.setExtend(node, cTable);
                }
                return true;
            }

            public void inorder(int node) {
            }

            public void postorder(int node) {
            }
        };
        DepthFirst.visit(this.tree, visitor, Tree.ROOT);

    }

    // Tree Interface

    public RowIterator childrenIterator(int node) {
        if (this.permutation != null) {
            int[] children = (int[]) this.sortedChildren.getObjectAt(node);
            if (children == null)
                return NullRowIterator.sharedInstance();
            else
                return new ArrayChildrenIterator(0, children);
        }
        else {
            return this.tree.childrenIterator(node);
        }
    }

    public int addNode(int par) {
        return this.tree.addNode(par);
    }

    public void createDegreeColumn() {
        this.tree.createDegreeColumn();
    }

    public void createDepthColumn() {
        this.tree.createDepthColumn();
    }

    public int getChild(int node, int index) {
        if (this.permutation != null) {
            int[] children = (int[]) this.sortedChildren.getObjectAt(node);
            if (children != null && children.length > index)
                return children[index];
            return Table.NIL;
        }
        return this.tree.getChild(node, index);
    }

    public int getDegree(int node) {
        if (this.permutation != null) {
            int[] children = (int[]) this.sortedChildren.getObjectAt(node);
            if (children == null)
                return 0;
            return children.length;
        }
        return this.tree.getDegree(node);
    }

    public int getDepth(int node) {
        return this.tree.getDepth(node);
    }

    public int getFirstChild(int node) {
        return getChild(node, 0);
    }

    public int getParent(int node) {
        return this.tree.getParent(node);
    }

    public boolean isAncestor(int node, int par) {
        return this.tree.isAncestor(node, par);
    }

    public boolean isLeaf(int node) {
        if (this.permutation != null) {
            int[] children = (int[]) this.sortedChildren.getObjectAt(node);
            return children == null;
        }
        return this.tree.isLeaf(node);
    }

    public void reparent(int node, int newparent) {
        this.tree.reparent(node, newparent);
    }

    public int nextNode() {
        return this.tree.nextNode();
    }
}
