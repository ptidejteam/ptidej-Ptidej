package gr.uom.java.pattern.inheritance;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Vector;
import java.util.Enumeration;

public class NonInheritingClassVector implements Enumeratable {
    private Vector<DefaultMutableTreeNode> nonInheritingClasses;

    public NonInheritingClassVector() {
        nonInheritingClasses = new Vector<DefaultMutableTreeNode>();
    }

    public DefaultMutableTreeNode getNode(String nodeName) {
        Enumeration e = nonInheritingClasses.elements();
        while(e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)e.nextElement();
            if(node.getUserObject().equals(nodeName))
                return node;
        }
		return null;
    }

    public void add(DefaultMutableTreeNode node) {
        nonInheritingClasses.add(node);
    }

    public Enumeration getEnumeration() {
        return nonInheritingClasses.elements();
    }

    public int size() {
        return nonInheritingClasses.size();
    }

    public boolean equals(InheritanceHierarchy ih) {
        return false;
    }

    public String toString() {
        DefaultMutableTreeNode node = nonInheritingClasses.get(0);
        if(node != null)
            return (String)node.getUserObject();
        else
            return null;
    }
}
