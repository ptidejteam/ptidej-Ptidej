package fr.emn.oadymppac.widgets;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.SwingConstants;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;

/**
 * @author Mohammad Ghoniem
 *
 * This interface defines the common behavior tree visualizations
 * components must support.
 */
public interface IcicleTreeBrowser extends TreeModelListener, MouseListener,
		MouseMotionListener {

	public static final int HORIZONTAL = SwingConstants.HORIZONTAL;
	public static final int VERTICAL = SwingConstants.VERTICAL;

	public static final int DEFAULT_ROW_HEIGHT = 10;
	public static final int DEFAULT_ORIENTATION = IcicleTreeBrowser.HORIZONTAL;
	public static final boolean DEFAULT_LEAF_ALIGNMENT = false;

	public int getOrientation();
	public TreeModel getTreeModel();
	public void setAlignLeaves(boolean b);
	public void setDrawFrames(boolean b);
	public void setRowHeight(int i);
	public void setTreeModel(TreeModel model);
}
