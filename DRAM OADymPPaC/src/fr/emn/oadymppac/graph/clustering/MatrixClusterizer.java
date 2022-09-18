/*
 * Created on 23 juil. 2003
 *
 */
package fr.emn.oadymppac.graph.clustering;

import java.io.File;
import javax.swing.tree.TreeModel;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This interface must be implemented by all classes
 * encapsulating a clustering algorithm for adjacency 
 * matrices.
 */
public interface MatrixClusterizer {
	public void clusterize();
	public int getColumnCount();
	public TreeModel getHorizontalTreeModel();
	public int getRowCount();
	public TreeModel getVerticalTreeModel();
	public void loadClustering(File file);
	public void setHorizontalTreeModel(TreeModel tree);
	public void setVerticalTreeModel(TreeModel tree);
	public void storeClustering(File file);
}
