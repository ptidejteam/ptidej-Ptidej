/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree;

import infovis.Tree;
import infovis.utils.RowIterator;


/**
 * Depth First traversal algorithm for trees.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DepthFirst {
    /**
     * Traverse the tree starting at a node with a depth first traversal,
     * calling the visitor at each node.
     *
     * @param tree the <code>Tree</code>
     * @param visitor the <code>DepthFirst,Visitor</code>
     * @param node the root node.
     */
    public static void visit(Tree tree, Visitor visitor, int node) {
        if (visitor.preorder(node)) {
            RowIterator iter = tree.childrenIterator(node);

            if (iter.hasNext()) {
                visit(tree, visitor, iter.nextRow());
                visitor.inorder(node);

                while(iter.hasNext()) {
                    visit(tree, visitor, iter.nextRow());
                }
            }

            visitor.postorder(node);
        }
    }
    
    /**
     * Traverse the tree starting at a node with a depth first traversal,
     * calling the visitor at each node.
     *
     * @param tree the <code>Tree</code>
     * @param visitor the <code>DepthFirst,Visitor</code>
     */
    public static void visit(Tree tree, Visitor visitor) {
        visit(tree, visitor, Tree.ROOT);
    }

    /**
     * Class for traversing a tree depth first.
     */
    public interface Visitor {
        /**
         * Method called when the node is initially reached.
         *
         * @param node the reached node.
         *
         * @return true if the subtree should be traversed, false if it
         *         should be pruned from the traversal. In the latter case,
         *         the two other methods are not called for the node.
         */
        public boolean preorder(int node);

        /**
         * Method called sometimes when traversing the children of the node.
         *
         * @param node the reached node.
         */
        public void inorder(int node);

        /**
         * Method called when all the children of the node have been
         * traversed.
         *
         * @param node the reached node.
         */
        public void postorder(int node);
    }
}
