/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.metadata;

import infovis.Column;
import infovis.Tree;
import infovis.column.ColumnFilter;
import infovis.column.DoubleColumn;
import infovis.column.IntColumn;
import infovis.column.NumberColumn;
import infovis.tree.DepthFirst;
import infovis.utils.RowIterator;

/**
 * Manage number columns that are additive from the leaves to the
 * root, as required by treemaps.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class AdditiveAggregation implements Aggregation {
    static AdditiveAggregation sharedInstance = new AdditiveAggregation();
    /**
     * Value returned by analyzeAdditiveWeight for a non-additive
     * column.
     */
    public static final short ADDITIVE_NO = AGGREGATE_NO;
    /**
     * Value returned by analyzeAdditiveWeight for an additive column.
     */
    public static final short ADDITIVE_YES = AGGREGATE_YES;
    /**
     * Value returned by analyzeAdditiveWeight for a column that can
     * be transformed into an additive column without breaking any
     * defined value.
     */
    public static final short ADDITIVE_COMPATIBLE = AGGREGATE_COMPATIBLE;

    /**
     * Name of the column containing the Additive degree of a tree.
     */
    public static final String ADDEDDEGREE_COLUMN = "#addedDegree";

    public short isAggregating(Column col, Tree tree) {
        if (col instanceof NumberColumn) {
            NumberColumn column = (NumberColumn) col;
            short ret = analyzeAdditiveWeight(column, tree);
            if (ret == AGGREGATE_YES) {
                col.getMetadata().put(
                    AGGREGATION_TYPE,
                    AGGREGATION_TYPE_ADDITIVE);
            }
            return ret;
        }
        return AGGREGATE_NO;
    }

    public Column aggregate(Column src, Tree tree, Column dst) {
        if ((dst == null || (dst instanceof NumberColumn)) &&
            (src instanceof NumberColumn)) {
            if (dst == null)
                dst = new DoubleColumn("#aggregationOf_"+src.getName());
            buildAdditiveWeight((NumberColumn)dst, tree, (NumberColumn)src);
            return dst;                       
        }
        return null;
    }

    public String getType() {
        return AGGREGATION_TYPE_ADDITIVE;
    }
    
    public static AdditiveAggregation sharedInstance() {
        return sharedInstance;
    }

    /**
     * Returns whether a NumberColumn is a valid sizeColumn.
     *
     * @param col the NumberColumn
     * @param tree the Tree.
     *
     * @return <code>ADDITIVE_YES</code> if the NumberColumn is a
     * valid sizeColumn, <code>ADDITIVE_NO</code> if the column is not
     * a valid sizeColumn and cannot be turned into one, and
     * <code>ADDITIVE_COMPATIBLE</code> otherwise.
     */
    public static short isAdditive(NumberColumn col, Tree tree) {
        String aggregationType =
            AggregationCategory.sharedInstance().getAggregationType(col, tree);
        if (aggregationType.equals(AGGREGATION_TYPE_ADDITIVE))
            return ADDITIVE_YES;
        if (aggregationType.equals(AGGREGATION_TYPE_ATLEAF))
            return ADDITIVE_COMPATIBLE;
        return ADDITIVE_NO;
    }

    /**
     * Checks whether a NumberColumn is a valid sizeColumn.
     *
     * @param col the NumberColumn
     * @param tree the Tree.
     *
     * @return <code>ADDITIVE_YES</code> if the NumberColumn is a
     * valid sizeColumn, <code>ADDITIVE_NO</code> if the column is not
     * a valid sizeColumn and cannot be turned into one, and
     * <code>ADDITIVE_COMPATIBLE</code> otherwise.
     */
    public static short analyzeAdditiveWeight(
        NumberColumn col,
        Tree tree) {
        AnalyzeCompatibility analyzer =
            new AnalyzeCompatibility(tree, col);
        try {
            DepthFirst.visit(tree, analyzer);
        }
        catch (Exception e) {
        }

        return analyzer.valid;
    }

    static class AnalyzeCompatibility implements DepthFirst.Visitor {
        public short valid = ADDITIVE_YES;
        Tree tree;
        NumberColumn col;

        AnalyzeCompatibility(Tree tree, NumberColumn col) {
            this.tree = tree;
            this.col = col;
        }

        public boolean preorder(int node) {
            if (this.tree.isLeaf(node)) {
                if (this.col.isValueUndefined(node)) {
                    this.valid = ADDITIVE_NO;
                    throw new RuntimeException("NO");
                }

                return false;
            }
            
            return true;
        }

        public void postorder(int node) {
            double sum = 0;

            for (RowIterator iter = this.tree.childrenIterator(node);
                iter.hasNext();
                ) {
                int c = iter.nextRow();
                sum += this.col.getDoubleAt(c);
            }

            if (this.col.getDoubleAt(node) != sum) {
                if (this.col.isValueUndefined(node)) {
                    this.valid = ADDITIVE_COMPATIBLE;
                }
                else {
                    this.valid = ADDITIVE_NO;
                    throw new RuntimeException("NO");
                }
            }
        }

        /**
         * @see infovis.Tree.DepthFirstVisitor#inorder(int)
         */
        public void inorder(int node) {
        }
    }

    /**
     * Transform a NumberColumn to be a valid sizeColumn by computing
     * the sum of the leaves and undefining non leaf nodes.
     *
     * @param col the NumberColumn.
     * @param tree the Tree.
     */
    public static void buildAdditiveWeight(
        final NumberColumn col,
        final Tree tree) {
        DepthFirst.visit(tree, new DepthFirst.Visitor() {
            public boolean preorder(int node) {
                return true;
            }

            public void postorder(int node) {
                if (tree.isLeaf(node)) {
                    col.setValueUndefined(node, false);
                    return;
                }

                double sum = 0;

                for (RowIterator iter = tree.childrenIterator(node);
                    iter.hasNext();
                    ) {
                    int c = iter.nextRow();
                    sum += col.getDoubleAt(c);
                }

                col.setDoubleAt(node, sum);
                col.setValueUndefined(node, true);
            }

            /**
             * @see infovis.Tree.DepthFirstVisitor#inorder(int)
             */
            public void inorder(int node) {
            }
        });
        col.getMetadata().put(
            AGGREGATION_TYPE,
            AGGREGATION_TYPE_ADDITIVE);
    }

    /**
     * Creates a NumberColumn to be a valid sizeColumn by computing the
     * sum of the leave and undefining non leaf nodes.  The initial
     * values are provided by a <code>WeightBuilder</code>.
     *
     * @param col the NumberColumn.
     * @param tree the tree.
     * @param builder the <code>WeightBuilder</code>.
     */
    public static void buildAdditiveWeight(
        final NumberColumn col,
        final Tree tree,
        final WeightBuilder builder) {
        DepthFirst.visit(tree, new DepthFirst.Visitor() {
            public boolean preorder(int node) {
                return true;
            }

            public void postorder(int node) {
                if (tree.isLeaf(node)) {
                    col.setDoubleAt(node, builder.weight(node));

                    return;
                }

                double sum = 0;

                for (RowIterator iter = tree.childrenIterator(node);
                    iter.hasNext();
                    ) {
                    int c = iter.nextRow();
                    sum += col.getDoubleAt(c);
                }

                col.setDoubleAt(node, sum);
                //col.setValueUndefined(node, true);
            }

            /**
             * @see infovis.Tree.DepthFirstVisitor#inorder(int)
             */
            public void inorder(int node) {
            }
        });
        col.getMetadata().put(
            AGGREGATION_TYPE,
            AGGREGATION_TYPE_ADDITIVE);
    }
    
    /**
         * Creates a NumberColumn to be a valid sizeColumn by computing the
         * sum of the leave and undefining non leaf nodes.  The initial
         * values are provided by a <code>WeightBuilder</code>.
         *
         * @param col the NumberColumn.
         * @param tree the tree.
         * @param builder the <code>WeightBuilder</code>.
         */
        public static void buildAdditiveWeight(
            final NumberColumn col,
            final Tree tree,
            final NumberColumn weight) {
            DepthFirst.visit(tree, new DepthFirst.Visitor() {
                public boolean preorder(int node) {
                    return true;
                }

                public void postorder(int node) {
                    if (tree.isLeaf(node)) {
                        col.setDoubleAt(node, weight.getDoubleAt(node));

                        return;
                    }

                    double sum = 0;

                    for (RowIterator iter = tree.childrenIterator(node);
                        iter.hasNext();
                        ) {
                        int c = iter.nextRow();
                        sum += col.getDoubleAt(c);
                    }

                    col.setDoubleAt(node, sum);
                    //col.setValueUndefined(node, true);
                }

                /**
                 * @see infovis.Tree.DepthFirstVisitor#inorder(int)
                 */
                public void inorder(int node) {
                }
            });
            col.getMetadata().put(
                AGGREGATION_TYPE,
                AGGREGATION_TYPE_ADDITIVE);
        }

    /**
     * DOCUMENT ME!
     *
     * @param tree DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static IntColumn buildDegreeAdditiveWeight(final Tree tree) {
        IntColumn degree = new IntColumn(ADDEDDEGREE_COLUMN);
        buildAdditiveWeight(
            degree,
            tree,
            new AdditiveAggregation.WeightBuilder() {
            /**
             * @see infovis.tree.visualization.treemap.Treemap.WeightBuilder#sizeColumn(int)
             */
            public double weight(int node) {
                return tree.isLeaf(node) ? 1 : 0;
            }
        });
        tree.addColumn(degree);
        return degree;
    }

    public static void buildDegreeAdditiveWeight(
        final NumberColumn degree,
        final Tree tree) {
        buildAdditiveWeight(
            degree,
            tree,
            new AdditiveAggregation.WeightBuilder() {
            /**
             * @see infovis.tree.visualization.treemap.Treemap.WeightBuilder#sizeColumn(int)
             */
            public double weight(int node) {
                return tree.isLeaf(node) ? 1 : 0;
            }
        });
    }

    /**
     * DOCUMENT ME!
     *
     * @param tree DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static IntColumn findDegreeColumn(Tree tree) {
        IntColumn degree =
            IntColumn.getColumn(tree, ADDEDDEGREE_COLUMN);
        if (degree != null)
            return degree;
        return buildDegreeAdditiveWeight(tree);
    }

    /**
     * Interface to build new sizeColumn columns.
     */
    public interface WeightBuilder {
        /**
         * Returns the sizeColumn of the leaf at a given node.
         *
         * @param node the node.
         *
         * @return the sizeColumn of the leaf at the given node.
         */
        double weight(int node);
    }

    public static class NonAdditiveFilter implements ColumnFilter {
        Tree tree;
        public NonAdditiveFilter(Tree tree) {
            this.tree = tree;
        }
        public boolean filter(Column column) {
            if (column instanceof NumberColumn) {
                NumberColumn col = (NumberColumn) column;
                return (isAdditive(col, this.tree) == ADDITIVE_NO);
            }
            return false;
        }
    }
}
