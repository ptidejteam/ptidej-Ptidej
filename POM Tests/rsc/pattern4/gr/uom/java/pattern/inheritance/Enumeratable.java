package gr.uom.java.pattern.inheritance;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

public interface Enumeratable {

    public Enumeration getEnumeration();

    public int size();

    public DefaultMutableTreeNode getNode(String nodeName);
}
