/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree;

import infovis.Tree;
import infovis.table.TableProxy;
import infovis.utils.RowIterator;


/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreeProxy extends TableProxy implements Tree {
    /** The real tree */
    protected Tree tree;

    public TreeProxy(Tree tree) {
        super(tree);
        this.tree = tree;
    }

    public int addNode(int par) {
        return this.tree.addNode(par);
    }

    public RowIterator childrenIterator(int node) {
        return this.tree.childrenIterator(node);
    }

    public void createDegreeColumn() {
        this.tree.createDegreeColumn();
    }

    public void createDepthColumn() {
        this.tree.createDepthColumn();
    }

    public int getChild(int node, int index) {
        return this.tree.getChild(node, index);
    }

    public int getDegree(int node) {
        return this.tree.getDegree(node);
    }

    public int getDepth(int node) {
        return this.tree.getDepth(node);
    }

    public int getParent(int node) {
        return this.tree.getParent(node);
    }

    public boolean isAncestor(int node, int par) {
        return this.tree.isAncestor(node, par);
    }

    public boolean isLeaf(int node) {
        return this.tree.isLeaf(node);
    }

    public int nextNode() {
        return this.tree.nextNode();
    }

    public void reparent(int node, int newparent) {
        this.tree.reparent(node, newparent);
    }
}
