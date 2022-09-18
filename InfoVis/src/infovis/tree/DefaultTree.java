/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree;

import infovis.Table;
import infovis.Tree;
import infovis.column.IntColumn;
import infovis.table.DefaultTable;
import infovis.table.TableProxy;
import infovis.utils.IntVector;
import infovis.utils.RowComparator;
import infovis.utils.RowIterator;
import infovis.utils.Sort;


/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultTree extends TableProxy implements Tree {
    protected IntColumn    child;
    protected IntColumn    next;
    protected IntColumn    last;
    protected IntColumn    parent;
    protected IntColumn    depth;
    protected IntColumn    degree;
    
    static final int[] nullInt = new int[0];

    /**
     * Creates a new DefaultTree object.
     */
    public DefaultTree() {
        this(new DefaultTable());
    }

    /**
     * Creates a new Tree object from an Table
     *
     * @param table the Table
     */
    public DefaultTree(Table table) {
        super(table);
        this.child = IntColumn.findColumn(this, CHILD_COLUMN);
        this.next = IntColumn.findColumn(this, NEXT_COLUMN);
        this.last = IntColumn.findColumn(this, LAST_COLUMN);
        this.parent = IntColumn.findColumn(this, PARENT_COLUMN);
//        depth = IntColumn.getColumn(this, DEPTH_COLUMN);
//        degree = IntColumn.getColumn(this, DEGREE_COLUMN);
        clear();
    }

    /**
     * Returns the first child of a node.
     *
     * @param node the node
     *
     * @return the first child or NIL value if node has no child
     */
    public int getFirstChild(int node) {
        if (node == NIL)
            return NIL;
        return this.child.get(node);
    }

    /**
     * @see infovis.Tree#getChild(int, int)
     */
    public int getChild(int node, int index) {
        if (index < 0)
            return NIL;
        for (int c = getFirstChild(node); c != Table.NIL; c = getNextSibling(c)) {
            if (index-- == 0)
                return c;
        }
        return NIL;
//        int[] children = children(node);
//        if (children.length <= index)
//            return NIL;
//        return children[index];
    }

    /**
     * Returns the next sibling of a node.
     *
     * @param node the node
     *
     * @return the next sibling or the NIL value if node is the last sibling
     */
    public int getNextSibling(int node) {
        if (node == NIL)
            return NIL;
        return this.next.get(node);
    }

    /**
     * Returns the last child of a node.
     *
     * @param node the node
     *
     * @return the last child or the NIL value if node has no child.
     */
    public int getLastChild(int node) {
        if (node == NIL)
            return NIL;
        return this.last.get(node);
    }

    /**
     * Returns the parent of a node.
     *
     * @param node the node
     *
     * @return the parent or the NIL value if node is the top node
     */
    public int getParent(int node) {
        if (node == NIL)
            return NIL;
        return this.parent.get(node);
    }

    /**
     * Returns the iterator over the children of a node.
     *
     * @param node the node
     *
     * @return the iterator over the children of the node
     */
    public RowIterator childrenIterator(int node) {
        if (! isRowValid(node))
            return null;
        return new ChildrenIterator(this.child.get(node));
    }

    /**
     * Adds a node to the tree.
     *
     * @param par the parent of the node.
     *
     * @return the created node.
     *
     * @throws ArrayIndexOutOfBoundsException DOCUMENT ME!
     */
    public int addNode(int par) {
        if (par == NIL) {
            throw new ArrayIndexOutOfBoundsException(" NIL is an invalid parent except for the root" +
                                                     par);
        }
        int node = nextNode();

        if (par > node) {
            throw new ArrayIndexOutOfBoundsException("invalid parent node " +
                                                     par);
        }

        this.parent.setExtend(node, par);
        this.next.setExtend(node, NIL);
        this.child.setExtend(node, NIL);
        this.last.setExtend(node, NIL);

        if (this.last.get(par) == NIL) {
            this.child.set(par, node);
            if (this.depth != null) {
                // created a child so update depth along the top
                this.depth.setExtend(node, 0);
                for (int i = par; i != NIL; i = getParent(i)) {
                    this.depth.set(i, this.depth.get(i) + 1);
                }
            }
        } else {
            this.next.set(this.last.get(par), node);
        }

        this.last.set(par, node);
        if (this.degree != null) {
            this.degree.setExtend(node, 0);
            this.degree.set(par, this.degree.get(par) + 1);
        }

        return node;
    }

    /**
     * Returns the next node to be returned by addNode.
     *
     * @return the next node to be returned by addNode.
     */
    public int nextNode() {
        return this.child.getRowCount();
    }

    /**
     * Change the parent of a specified node, changing the structure.
     *
     * @param node the node.
     * @param newparent the new parent.
     *
     * @throws RuntimeException if the node is an ancestor of the parent.
     */
    public void reparent(int node, int newparent) {
        if (isAncestor(newparent, node))
            throw new RuntimeException("cannot reparent into a child");
        int par = getParent(node);
        if (par == newparent)
            return;
        removeChild(node);
        addChild(node, newparent);
        updateDegree();
        updateDepth();
    }

    protected void removeChild(int node) {
        int par = getParent(node);
        this.parent.set(node, NIL);
        if (getFirstChild(par) == node) {
            this.child.set(par, getNextSibling(node));
            if (getLastChild(par) == node) {
                this.last.set(par, NIL);
            }

            // If we are first, no previous to update
            // If we are also last, nothing else to do
            return;
        }

        // Not first, chase the previous to change its next pointer.
        int n;
        for (n = getFirstChild(par); n != NIL; n = getNextSibling(n)) {
            if (getNextSibling(n) == node) {
                // got it
                this.next.set(n, getNextSibling(node));
                break;
            }
        }
        if (getLastChild(par) == node) {
            assert (getNextSibling(node) == NIL);
            this.last.set(par, n);
        }
    }

    protected void addChild(int node, int par) {
        this.parent.setExtend(node, par);
        this.next.setExtend(node, NIL);

        if (this.last.get(par) == NIL) {
            this.child.set(par, node);
        } else {
            this.next.set(this.last.get(par), node);
        }

        this.last.set(par, node);

    }

    /**
     * Returns true if the first node has the second node as ancestor.
     *
     * @param node the node.
     * @param par the tested ancestor.
     *
     * @return true if the first node has the second node as ancestor.
     */
    public boolean isAncestor(int node, int par) {
        while (node != NIL) {
            if (node == par) {
                return true;
            }

            node = getParent(node);
        }

        return false;
    }

    /**
     * Computes the depth of the node in the tree and return it.
     *
     * @param node the node.
     *
     * @return the depth of the node in the tree.
     */
    public int computeDepth(int node) {
        int depth = 0;
        while (node != ROOT) {
            node = getParent(node);
            depth++;
        }
        return depth;
    }

    /**
     * Creates a column to hold the computed depth of each node,
     * avoiding its computation when needed.
     * Does nothing if the column already exists.
     */
    public void createDepthColumn() {
        if (this.depth != null)
            return;
        this.depth = IntColumn.findColumn(this, DEPTH_COLUMN);
        updateDepth();
    }

    protected void updateDepth() {
        if (this.depth == null)
            return;
        for (int node = 0; node < this.parent.getRowCount(); node++) {
            this.depth.setExtend(node, computeDepth(node));
        }
    }

    /**
     * Returns true if a depth column has been computed.
     *
     * @return true if a depth column has been computed.
     */
    public boolean hasDepthColumn() {
        return this.depth != null;
    }

    /**
     * Returns the depth of a node using either a depth column if it has been computed or
     * the computeDepth method.
     *
     * @param node the node.
     *
     * @return the depth of the node.
     */
    public int getDepth(int node) {
        if (this.depth != null)
            return this.depth.get(node);
        return computeDepth(node);
    }

    /**
     * Returns the degree of a node, i.e. the number of children
     *
     * @param node the node
     *
     * @return the degree of the node
     */
    public int computeDegree(int node) {
        int degree = 0;

        for (int n = this.child.get(node); n != NIL; n = this.next.get(n)) {
            degree++;
        }

        return degree;
    }

    /**
     * Creates a column holding the degree of nodes.
     *
     * <p>Accelerates degree queries if they are frequent but
     * slows down structural changes since the column needs to
     * be updated.
     *
     */
    public void createDegreeColumn() {
        if (this.degree != null)
            return;
        this.degree = IntColumn.findColumn(this, DEGREE_COLUMN);
        updateDegree();
    }

    protected void updateDegree() {
        if (this.degree == null)
            return;
        for (int node = 0; node < this.child.getRowCount(); node++) {
            this.degree.setExtend(node, computeDegree(node));
        }
    }

    /**
     * Returns the degree of a node using either a degree column if it has been computed or
     * the computeDegree method.
     *
     * @param node the node.
     *
     * @return the depth of the node.
     */
    public int getDegree(int node) {
        if (this.degree != null)
            return this.degree.get(node);
        return computeDegree(node);
    }

    /**
     * Returns true if the node is a leaf_node. Use this method rather than
     * degree(node)==0
     *
     * @param node the node
     *
     * @return true if the node is a leaf_node.
     */
    public boolean isLeaf(int node) {
        if (!isRowValid(node))
            return false;
        return getFirstChild(node) == NIL;
    }

    /**
     * @see infovis.Table#clear()
     */
    public void clear() {
        super.clear();

        this.child.clear();
        this.child.add(NIL);
        addColumn(this.child);

        this.next.clear();
        this.next.add(NIL);
        addColumn(this.next);

        this.last.clear();
        this.last.add(NIL);
        addColumn(this.last);

        this.parent.clear();
        this.parent.add(NIL);
        addColumn(this.parent);

        if (this.depth != null) {
            this.depth.clear();
            updateDepth();
        }
        if (this.degree != null) {
            this.degree.clear();
            updateDegree();
        }
    }
    

    /**
     * Sorts the node children according to a <code>RowComparator</code>
     *
     * @param node the node.
     * @param comp the comparator.
     */
    protected void sortChildren(int node, RowComparator comp) {
        int[] c = children(node);

        if (c == null || c.length < 2) {
            return;
        }

        Sort.sort(c, comp);
    }

    /**
     * Returns a table of children of this node.
     *
     * @param node the node.
     *
     * @return a table of childen of the given node
     * or null is the node has no child.
     */
    public int[] children(int node) {
        int   n = getDegree(node);
        int[] c;
        if (n == 0)
            c = nullInt;
        else {
            c = new int[n];
            int i = 0;

            for (int child = getFirstChild(node); child != NIL;
                     child = getNextSibling(child))
                c[i++] = child;
        }
        return c;
    }

    /**
     * Traverse the tree with a depth first traversal, calling the visitor at
     * each node.
     *
     * @param visitor the <code>DepthFirstVisitor</code>.
     */
    public void visit(DepthFirst.Visitor visitor) {
        DepthFirst.visit(this, visitor, ROOT);
    }

    /**
     * Traverse the tree with a depth first traversal, calling the visitor at
     * each node.
     *
     * @param visitor the <code>DepthFirstVisitor</code>.
     */
    public void visit(BreadthFirst.Visitor visitor) {
        BreadthFirst.visit(this, visitor, ROOT, new IntVector());
    }

    class ChildrenIterator implements RowIterator {
        int node;

        public ChildrenIterator(int node) {
            this.node = node;
        }

        public boolean hasNext() {
            return this.node != NIL;
        }

        public Object next() {
            return Integer.valueOf(nextRow());
        }

        public void remove() {
        }

        public int nextRow() {
            int n = this.node;
            this.node = DefaultTree.this.next.get(this.node);

            return n;
        }

        public int peekRow() {
            return this.node;
        }
        
        /**
         * @see infovis.utils.RowIterator#copy()
         */
        public RowIterator copy() {
            return new ChildrenIterator(this.node);
        }

    }
}
