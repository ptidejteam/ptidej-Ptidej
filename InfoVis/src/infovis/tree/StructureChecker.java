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
import infovis.utils.IntSortedMap;
import infovis.utils.IntVector;


/**
 * Checks the integrity of a tree.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class StructureChecker implements DepthFirst.Visitor {
    Tree tree;
    IntSortedMap nodeMap;
    IntVector parentStack = new IntVector();
    
    /**
     * Constructor for StructureChecker.
     */
    public StructureChecker(Tree tree) {
        this.tree = tree;
        this.nodeMap = new IntSortedMap();
        for (int i = 0; i < tree.getRowCount(); i++) {
            this.nodeMap.put(i, tree);
        }
        this.parentStack.push(Table.NIL);
        DepthFirst.visit(tree, this, Tree.ROOT);
        if (! this.nodeMap.isEmpty())
        	fail(Tree.ROOT);
    }

    void fail(int node) {
    	throw new RuntimeException("failed at node "+node);
    }
    public void inorder(int node) {
    	if (this.parentStack.top() != this.tree.getParent(node))
    		fail(node);
    	if (! this.nodeMap.containsKey(node))
    		fail(node);
    	this.nodeMap.remove(node);
    	this.parentStack.push(node);
    }
    public boolean preorder(int node) {
        return false;
    }
    public void postorder(int node) {
    	this.parentStack.pop();
    }
    
    public static boolean check(Tree tree) {
    	try {
    	    new StructureChecker(tree);
    	}
    	catch(RuntimeException e) {
    	    return false;
    	}
    	return true;
    }
}
