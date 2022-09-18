/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree;

import infovis.Tree;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class Algorithms {
    static class TreeDepth implements DepthFirst.Visitor {
        int depth;
        int maxDepth;
    
        public TreeDepth() {
           this.depth = 0;
           this.maxDepth = 0;
        }
        public boolean preorder(int node) {
            this.depth++;
            if (this.depth > this.maxDepth)
                this.maxDepth = this.depth;
            return true;
        }
        public void postorder(int node) {
            this.depth--;
        }
        public void inorder(int node) {
        }
    }

    public static int treeDepth(Tree tree) {
        TreeDepth visitor = new TreeDepth();
        DepthFirst.visit(tree, visitor);
        return visitor.maxDepth;
    }

}
