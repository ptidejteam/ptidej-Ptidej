/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis;

import infovis.utils.RowIterator;


/**
 * Base proxy for considering a table as a tree structure.
 *
 * <p>A tree references a table and make sure it has the topological
 * information stored as four columns.  An index represents a node
 * number, with all associated values stored in the columns.  The four
 * internal columns are: <ul> <li>child: the index of the first child
 * for a given node <li>next: the next sibling node <li>last: the
 * index of the last child for a given node <li>parent: the index of
 * the parent for a given node.  </ul></p>
 *
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 */
public interface Tree extends Table {
    /** Value of the tree's ROOT */
    public static final int ROOT = 0;
    /**
     * Name of the IntColumn referencing the first child of a node.
     */
    public static final String CHILD_COLUMN = "#child";
    /**
     * Name of the IntColumn referencing the next sibling of a node.
     */
    public static final String NEXT_COLUMN = "#next";
    /**
     * Name of the IntColumn referencing the last child of a node.
     */
    public static final String LAST_COLUMN = "#last";
    /**
     * Name of the IntColumn referencing the parent of a node.
     */
    public static final String PARENT_COLUMN = "#parent";
    /**
     * Name of the optional IntColumn referencing the depth of a node.
     */
    public static final String DEPTH_COLUMN = "#depth";
    /**
     * Name of the optional IntColumn referencing the degree of a node.
     */
    public static final String DEGREE_COLUMN = "#degree";

    /**
     * Returns the first child of a node.
     *
     * @param node the node
     *
     * @return the first child or NIL value if node has no child
     */
    //public int getFirstChild(int node);
    
    /**
     * Returns the nth child of a node.
     *
     * @param node the node
     * @param index the index if the requested child
     *
     * @return the requested child or NIL if node has not so many children.
     */
    public int getChild(int node, int index);

    /**
     * Returns the next sibling of a node.
     *
     * @param node the node
     *
     * @return the next sibling or the NIL value if node is the last sibling
     */
    //public int getNextSibling(int node);

    /**
     * Returns the last child of a node.
     *
     * @param node the node
     *
     * @return the last child or the NIL value if node has no child.
     */
    //public int getLastChild(int node);

    /**
     * Returns the parent of a node.
     *
     * @param node the node
     *
     * @return the parent or the NIL value if node is the top node
     */
    public int getParent(int node);

    /**
     * Returns the iterator over the children of a node.
     *
     * @param node the node
     *
     * @return the iterator over the children of the node
     */
    public RowIterator childrenIterator(int node);

    /**
     * Adds a node to the tree.
     *
     * @param par the parent of the node.
     *
     * @return the created node.
     *
     * @throws ArrayIndexOutOfBoundsException DOCUMENT ME!
     */
    public int addNode(int par);

    /**
     * Returns the next node to be returned by addNode.
     *
     * @return the next node to be returned by addNode.
     */
    public int nextNode();

    /**
     * Change the parent of a specified node, changing the structure.
     *
     * @param node the node.
     * @param newparent the new parent.
     *
     * @throws RuntimeException if the node is an ancestor of the parent.
     */
    public void reparent(int node, int newparent);
    
    /**
     * Returns true if the first node has the second node as ancestor.
     *
     * @param node the node.
     * @param par the tested ancestor.
     *
     * @return true if the first node has the second node as ancestor.
     */
    public boolean isAncestor(int node, int par);

    /**
     * Creates a column to hold the computed depth of each node,
     * avoiding its computation when needed.
     * Does nothing if the column already exists.
     */
    public void createDepthColumn();

    /**
     * Returns the depth of a node using either a depth column if it has been computed or
     * the computeDepth method.
     *
     * @param node the node.
     *
     * @return the depth of the node.
     */
    public int getDepth(int node);

    /**
     * Creates a column holding the degree of nodes.
     *
     * <p>Accelerates degree queries if they are frequent but
     * slows down structural changes since the column needs to
     * be updated.
     *
     */
    public void createDegreeColumn();
    
    /**
     * Returns the degree of a node using either a degree column if it has been computed or
     * the computeDegree method.
     *
     * @param node the node.
     *
     * @return the depth of the node.
     */
    public int getDegree(int node);

    /**
     * Returns true if the node is a leaf_node. Use this method rather than
     * degree(node)==0
     *
     * @param node the node
     *
     * @return true if the node is a leaf_node.
     */
    public boolean isLeaf(int node);
}
