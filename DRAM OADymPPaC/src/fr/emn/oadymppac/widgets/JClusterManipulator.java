package fr.emn.oadymppac.widgets;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import fr.emn.oadymppac.tree.StateNode;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * A subclass of <code>JTreeBrowser</code> that handles a 
 * listener list and fires <code>ClusterEvent</code>s upon
 * cluster manipulation.
 */
public class JClusterManipulator extends JTreeBrowser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9032708969195827109L;
	ClusterEvent event;
	Vector listeners = new Vector();

	/*************************************
	 * Mouse listener interface methods
	 *************************************
	 */

	StateNode tmpNode;

	StateNode targetNode;

	/**
	 * Constructor for JClusterManipulator.
	 */
	public JClusterManipulator() {
		super();
		this.setToolTipText("Cluster Manipulator");
	}

	/**
	 * Constructor for JClusterManipulator.
	 * @param tree
	 */
	public JClusterManipulator(final TreeModel tree) {
		this(tree, IcicleTreeBrowser.DEFAULT_ORIENTATION);
	}

	/**
	 * Constructor for JClusterManipulator.
	 * @param tree
	 * @param orientation
	 */
	public JClusterManipulator(final TreeModel tree, final int orientation) {
		super(tree, orientation);
		this.setToolTipText("Cluster Manipulator");
	}

	public void addClusterListener(final ClusterListener l) {
		this.listeners.addElement(l);
	}

	public void fireClusterChanged() {
		System.out.println("Leaf Count : " + this.root.getLeafCount());
		if (this.listeners.size() == 0) {
			return;
		}
		for (int i = 0; i < this.listeners.size(); i++) {
			((ClusterListener) this.listeners.elementAt(i))
				.clusterChanged(this.event);
		}
	}

	String[] getChildrenArray(final StateNode parent) {
		final String[] res = new String[parent.getChildCount()];
		for (int i = 0; i < parent.getChildCount(); i++) {
			res[i] =
				(String) ((StateNode) parent.getChildAt(i)).getUserObject();
		}
		return res;
	}
	public String getToolTipText(final MouseEvent e) {
		StateNode tmp;

		if (e.getSource() == this) {
			tmp = this.findNodeAt(e.getX(), e.getY());
			if (tmp != null) {
				this.getToolTipLocation(e);
				return tmp.toString();
			}
		}

		return "Cluster Manipulator";
	}

	/**
	 * Upon a double click, the target node state is reversed 
	 * (it is expanded if it were collapsed and vice versa). If
	 * the SHIFT key is held during the double click, the whole 
	 * subtree of the target node is either collapsed or expanded.
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
	 */
	public void mouseClicked(final MouseEvent e) {
		super.mouseClicked(e);
	}

	/**
	 * When the mouse is dragged, if there is a node underneath, 
	 * it is looked up. In this case, we keep track of the mouse
	 * position and update the display accordingly, thus moving the 
	 * selection around.
	 * 
	 * @see java.awt.event.MouseMotionListener#mouseDragged(MouseEvent)
	 */
	public void mouseDragged(final MouseEvent e) {
		if (this.draggedNode == null) {
			this.draggedNode = this.findNodeAt(e.getX(), e.getY());
		}
		if (this.draggedNode != null) {
			this.dragging = true;

			// uncomment the following line to enable smart painting
			//repaint((int)clipX, (int)clipY, (int)clipWidth+1, (int)clipHeight+1);

			this.pickedx = e.getX();
			this.pickedy = e.getY();

			// uncomment the following line to enable smart painting
			//repaint((int)clipX, (int)clipY, (int)clipWidth+1, (int)clipHeight+1);

			// comment the following line when enabling smart painting
			this.repaint();
		}
	}

	/**
	 * @see java.awt.event.MouseListener#mouseEntered(MouseEvent)
	 */
	public void mouseEntered(final MouseEvent e) {
	}

	/**
	 * @see java.awt.event.MouseListener#mouseExited(MouseEvent)
	 */
	public void mouseExited(final MouseEvent e) {
	}

	/**
	 * @see java.awt.event.MouseMotionListener#mouseMoved(MouseEvent)
	 */
	public void mouseMoved(final MouseEvent e) {
		this.getToolTipText(e);
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
	 */
	public void mousePressed(final MouseEvent e) {
		this.dragStart = e.getPoint();
	}

	/*******************************************
	 * Mouse motion listener interface methods
	 *******************************************
	 */

	/**
	 * Hold the CTRL key and drop the dragged node 
	 * on a sibling to swap them.
	 * 
	 * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
	 */
	public void mouseReleased(final MouseEvent e) {
		if (this.dragging) {
			this.targetNode = this.findNodeAt(e.getX(), e.getY());
			if (this.targetNode != null) {
				if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) > 0) {
					if (this.targetNode.isNodeSibling(this.draggedNode)) {
						if (this.targetNode.isLeaf()
								&& this.draggedNode.isLeaf()) {
							// swap two leaves in the same cluster
							this.event =
								new ClusterEvent(
									this,
									(String) this.targetNode.getUserObject(),
									(String) this.draggedNode.getUserObject(),
									ClusterEvent.SINGLE_SWAP);
						}
						else if (!this.targetNode.isLeaf()
								&& !this.draggedNode.isLeaf()) {
							// swap two clusters
							this.event =
								new ClusterEvent(
									this,
									this.getChildrenArray(this.targetNode),
									this.getChildrenArray(this.draggedNode),
									ClusterEvent.ARRAY_SWAP);
						}
						((StateNode) this.targetNode.getParent()).swapChildren(
							this.targetNode,
							this.draggedNode);
						this.fireClusterChanged();
					}
				}
				else if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) > 0) {
					// merge clusters
					if (this.targetNode.isNodeSibling(this.draggedNode)
							&& !this.targetNode.isLeaf()
							&& !this.draggedNode.isLeaf()) {
						this.event =
							new ClusterEvent(
								this,
								(String) ((StateNode) this.targetNode
									.getLastChild()).getUserObject(),
								this.getChildrenArray(this.draggedNode),
								ClusterEvent.ARRAY_INSERT);
						((StateNode) this.targetNode.getParent())
							.mergeChildren(this.targetNode, this.draggedNode);
						this.fireClusterChanged();
					}
				}
				else if (this.draggedNode.isLeaf() && !this.targetNode.isLeaf()) {
					// reparent a leaf
					this.event =
						new ClusterEvent(
							this,
							(String) ((StateNode) this.targetNode
								.getLastChild()).getUserObject(),
							(String) this.draggedNode.getUserObject(),
							ClusterEvent.SINGLE_INSERT);
					if (this.draggedNode.getParent().getChildCount() == 1) {
						((StateNode) this.draggedNode.getParent())
							.removeFromParent();
					}
					this.targetNode.add(this.draggedNode);
					this.fireClusterChanged();
					// TODO : consider reparenting clusters
				}
				((DefaultTreeModel) this.treeModel)
					.nodeStructureChanged(this.root);
			}
			this.draggedNode = null;
			this.targetNode = null;
		}
		this.dragging = false;
		this.repaint();
	}

	public void removeAllListeners() {
		this.listeners.removeAllElements();
	}

	public void removeClusterListener(final ClusterListener l) {
		this.listeners.remove(l);
	}

}
