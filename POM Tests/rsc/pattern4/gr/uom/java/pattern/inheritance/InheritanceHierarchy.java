package gr.uom.java.pattern.inheritance;

import java.util.*;
import javax.swing.tree.*;

public class InheritanceHierarchy implements Enumeratable {
	private DefaultMutableTreeNode rootNode;
	
	public InheritanceHierarchy() {
		rootNode = null;
	}
	
	public DefaultMutableTreeNode getNode(String nodeName) {
		if(rootNode != null) {
            Enumeration e = rootNode.breadthFirstEnumeration();
            while(e.hasMoreElements()) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
                if(node.getUserObject().equals(nodeName))
                    return node;
            }
        }
		return null;
	}
	
	public void addChildToParent(String childNode, String parentNode) {
		DefaultMutableTreeNode cNode = getNode(childNode);
		if(cNode == null) {
			cNode = new DefaultMutableTreeNode(childNode);
		}
        DefaultMutableTreeNode pNode = getNode(parentNode);
		if(pNode == null) {
			pNode = new DefaultMutableTreeNode(parentNode);
			//the new parentNode becomes the rootNode of the tree
			rootNode = pNode;
		}
        pNode.add(cNode);
	}

	public void addChildRootNodeToParent(DefaultMutableTreeNode childRootNode, String parentNode) {
		DefaultMutableTreeNode pNode = getNode(parentNode);
		if(pNode == null) {
			pNode = new DefaultMutableTreeNode(parentNode);
			//the new parentNode becomes the rootNode of the tree
			rootNode = pNode;
		}
		pNode.add(childRootNode);
	}

    public DefaultMutableTreeNode deepClone(DefaultMutableTreeNode root) {
        DefaultMutableTreeNode nroot = (DefaultMutableTreeNode)root.clone();
	    Enumeration children = root.children();
	    while (children.hasMoreElements()) {
	        DefaultMutableTreeNode child = (DefaultMutableTreeNode)children.nextElement();
	        DefaultMutableTreeNode nchild = deepClone(child);
	        nroot.add(nchild);
	    }
	    return nroot;
    }

    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o instanceof InheritanceHierarchy) {
            InheritanceHierarchy inheritanceHierarchy = (InheritanceHierarchy)o;
            rootNode.getUserObject().equals(inheritanceHierarchy.rootNode.getUserObject());
        }
        return false;
    }
    
    public DefaultMutableTreeNode getRootNode() {
		return rootNode;
	}

    public Enumeration getEnumeration() {
        return rootNode.breadthFirstEnumeration();
    }

    public int size() {
        Enumeration e = rootNode.breadthFirstEnumeration();
        int counter = 0;
        while(e.hasMoreElements()) {
            e.nextElement();
            counter++;
        }
        return counter;
    }

    public String toString() {
        return (String)rootNode.getUserObject();
    }
}