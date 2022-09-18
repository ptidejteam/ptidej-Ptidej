package fr.emn.oadymppac.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import fr.emn.oadymppac.tree.StateNode;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.3 $
 *
 * A swing component that displays hierarchical data 
 * as an icicle tree and allows interaction with its 
 * nodes.
 */
public class JTreeBrowser extends JComponent implements IcicleTreeBrowser,
		TreeExpansionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2134824948934884023L;
	int rowHeight = IcicleTreeBrowser.DEFAULT_ROW_HEIGHT;
	int orientation = IcicleTreeBrowser.DEFAULT_ORIENTATION;

	int toggleCount = 2;

	StateNode root;
	boolean rootVisible = false;
	boolean alignLeaves = IcicleTreeBrowser.DEFAULT_LEAF_ALIGNMENT;
	boolean drawFrames = true;

	TreeModel treeModel;

	Vector treeExpansionListerners = new Vector();
	Point dragStart;

	protected boolean dragging = false;

	protected StateNode draggedNode = null;

	protected int pickedx;

	protected int pickedy;

	protected float deltax;

	protected float deltay;

	protected float clipWidth;

	protected float clipHeight;

	protected float clipX;

	protected float clipY;

	/*************************************
	 * Mouse listener interface methods
	 *************************************
	 */

	StateNode tmpNode;

	StateNode targetNode;

	/**
	 * Constructor for JClusterNavigator.
	 */
	public JTreeBrowser() {
		this(
			new DefaultTreeModel(new StateNode("root")),
			IcicleTreeBrowser.DEFAULT_ORIENTATION);
	}

	public JTreeBrowser(final TreeModel tree) {
		this(tree, IcicleTreeBrowser.DEFAULT_ORIENTATION);
	}

	public JTreeBrowser(final TreeModel tree, final int orientation) {
		this.orientation = orientation;
		this.setTreeModel(tree);
		this.init();
	}

	/**
	 * Adds a listener for <code>TreeExpansionEvent</code> events.
	 */
	public void addTreeExpansionListener(final TreeExpansionListener listener) {
		if (listener != null) {
			this.treeExpansionListerners.add(listener);
		}
	}

	/**
	 * Returns the node located at x and y.
	 */
	protected StateNode findNodeAt(final int x, final int y) {
		if (this.orientation == IcicleTreeBrowser.VERTICAL) {
			return this
				.findNodeVerticalAt(x, y, this.root, this.getHeight(), 0);
		}
		return this.findNodeAt(x, y, this.root, this.getWidth(), 0);
	}
	/**
	 * Returns the node located at x and y among the 
	 * descendants of the passed node.
	 */
	protected StateNode findNodeAt(
		final int x,
		final int y,
		final StateNode parentNode,
		final float parentWidth,
		final float parentOffset) {

		if (parentNode == this.draggedNode) {
			return null;
		}

		if (y > parentNode.getLevel() * this.rowHeight
				&& y < (parentNode.getLevel() + 1) * this.rowHeight) {
			return parentNode;
		}

		if (!parentNode.isExpanded()) {
			return null;
		}

		final Enumeration e = parentNode.children();
		//	int h =
		//		isRootVisible()
		//			? rowHeight * (parentNode.getLevel() + 1)
		//			: rowHeight * parentNode.getLevel();
		final float w =
			(float) (parentWidth / parentNode.getWeightGetter().getTotalWeight(
				parentNode));
		float offset = 0;
		double k;
		StateNode node;
		final int rootDepth = this.root.getDepth();

		while (e.hasMoreElements()) {
			node = (StateNode) e.nextElement();
			k = node.getWeightGetter().getWeight(node);
			/**
			 * Found the right branch.
			 */
			if (x > parentOffset + offset && x < parentOffset + offset + k * w) {
				if (this.isAlignLeaves() && node.isLeaf()
						&& y > parentNode.getLevel() * this.rowHeight
						&& y < (rootDepth + 1) * this.rowHeight) {
					return node == this.draggedNode ? null : node;
				}
				return this.findNodeAt(
					x,
					y,
					node,
					(float) (k * w),
					parentOffset + offset);
			}
			offset += k * w;
		}
		return null;
	}
	/**
	 * Returns the node located at x and y among the 
	 * descendants of the passed node.
	 */
	protected StateNode findNodeVerticalAt(
		final int x,
		final int y,
		final StateNode parentNode,
		final float parentHeight,
		final float parentOffset) {

		if (parentNode == this.draggedNode) {
			return null;
		}

		if (x > parentNode.getLevel() * this.rowHeight
				&& x < (parentNode.getLevel() + 1) * this.rowHeight) {
			return parentNode;
		}

		if (!parentNode.isExpanded()) {
			return null;
		}

		final Enumeration e = parentNode.children();
		//	int w =
		//		isRootVisible()
		//			? rowHeight * (parentNode.getLevel() + 1)
		//			: rowHeight * parentNode.getLevel();
		final float h =
			(float) (parentHeight / parentNode
				.getWeightGetter()
				.getTotalWeight(parentNode));
		float offset = 0;
		double k;
		StateNode node;
		final int rootDepth = this.root.getDepth();

		while (e.hasMoreElements()) {
			node = (StateNode) e.nextElement();
			k = node.getWeightGetter().getWeight(node);
			/**
			 * Found the right branch.
			 */
			if (y > parentOffset + offset && y < parentOffset + offset + k * h) {
				if (this.isAlignLeaves() && node.isLeaf()
						&& y > parentNode.getLevel() * this.rowHeight
						&& y < (rootDepth + 1) * this.rowHeight) {
					return node == this.draggedNode ? null : node;
				}
				return this.findNodeVerticalAt(
					x,
					y,
					node,
					(float) (k * h),
					parentOffset + offset);
			}
			offset += k * h;
		}
		return null;
	}
	/**
	 * Notifies all listeners that have registered interest for notification on this event type.
	 */
	public void fireTreeCollapsed(final TreePath path) {
		final TreeExpansionEvent event = new TreeExpansionEvent(this, path);
		for (int i = 0; i < this.treeExpansionListerners.size(); i++) {
			((TreeExpansionListener) this.treeExpansionListerners.elementAt(i))
				.treeCollapsed(event);
		}
	}
	/**
	 * Notifies all listeners that have registered interest for notification on this event type.
	 */
	public void fireTreeExpanded(final TreePath path) {
		final TreeExpansionEvent event = new TreeExpansionEvent(this, path);
		for (int i = 0; i < this.treeExpansionListerners.size(); i++) {
			((TreeExpansionListener) this.treeExpansionListerners.elementAt(i))
				.treeExpanded(event);
		}
	}
	/**
	 * Returns the orientation.
	 * @return int
	 */
	public int getOrientation() {
		return this.orientation;
	}
	/**
	 * Returns the rowHeight.
	 * @return int
	 */
	public int getRowHeight() {
		return this.rowHeight;
	}
	/**
	 * Returns the treeModel.
	 * @return TreeModel
	 */
	public TreeModel getTreeModel() {
		return this.treeModel;
	}
	private void init() {
		this.setPreferredSize(new Dimension(600, 200));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addTreeExpansionListener(this);
	}
	/**
	 * Returns the alignLeaves.
	 * @return boolean
	 */
	public boolean isAlignLeaves() {
		return this.alignLeaves;
	}

	/**
	 * Returns the drawFrames.
	 * @return boolean
	 */
	public boolean isDrawFrames() {
		return this.drawFrames;
	}

	/**
	 * Returns the rootVisible.
	 * @return boolean
	 */
	public boolean isRootVisible() {
		return this.rootVisible;
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
		if (e.getClickCount() == this.toggleCount) {
			this.tmpNode = this.findNodeAt(e.getX(), e.getY());
			if (this.tmpNode != null) {
				if (!this.tmpNode.isLeaf()) {
					if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) > 0) {
						this.tmpNode.expandSubTree(!this.tmpNode.isExpanded());
					}
					else if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) > 0) {
						this.tmpNode.expandLevel(
							this.tmpNode.getLevel(),
							!this.tmpNode.isExpanded());
					}
					else {
						this.tmpNode.setExpanded(!this.tmpNode.isExpanded());
					}

					if (this.tmpNode.isExpanded()) {
						this.fireTreeExpanded(this.tmpNode.getTreePath());
					}
					else {
						this.fireTreeCollapsed(this.tmpNode.getTreePath());
					}
				}
			}
		}
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
	}

	/**
	 * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
	 */
	public void mousePressed(final MouseEvent e) {
		this.dragStart = e.getPoint();
	}

	/****************************************************************
	 * Methods related to TreeExpansion listeners of this component
	 ****************************************************************
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
						((StateNode) this.targetNode.getParent()).swapChildren(
							this.targetNode,
							this.draggedNode);
					}
				}
				else if ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) > 0) {
					if (this.targetNode.isNodeSibling(this.draggedNode)) {
						((StateNode) this.targetNode.getParent())
							.mergeChildren(this.targetNode, this.draggedNode);
					}
				}
				else {
					((StateNode) this.draggedNode.getParent())
						.remove(this.draggedNode);
					this.targetNode.add(this.draggedNode);
					this.targetNode.setExpanded(true);
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

	/**
	 * Paints the children of the passed node.
	 * 
	 * TODO : postpone the display of the dragged subtree to the end.
	 */
	protected void paintChildren(
		final Graphics2D g,
		final StateNode parentNode,
		final float parentWidth,
		final float parentOffset) {

		final Enumeration e = parentNode.children();
		final int h =
			this.isRootVisible() ? this.rowHeight * (parentNode.getLevel() + 1)
					: this.rowHeight * parentNode.getLevel();
		final float w =
			(float) (parentWidth / parentNode.getWeightGetter().getTotalWeight(
				parentNode));
		final Rectangle2D.Float rect =
			new Rectangle2D.Float(0, h, w, this.rowHeight);
		StateNode node;
		double k;
		float offset = 0;
		final int rootDepth = this.root.getDepth();

		while (e.hasMoreElements()) {
			node = (StateNode) e.nextElement();
			k = node.getWeightGetter().getWeight(node);
			this.deltax =
				this.dragStart != null ? this.dragStart.x
						- (parentOffset + offset) : 0;
			this.deltay = this.dragStart != null ? this.dragStart.y - h : 0;

			rect.height =
				node.isLeaf() && this.isAlignLeaves() ? (rootDepth
						- node.getLevel() + 1)
						* this.rowHeight : this.rowHeight;

			// the current node matches the dragged node
			if (node == this.draggedNode) {
				rect.x = this.pickedx - this.deltax;
				rect.y = this.pickedy - this.deltay;
				rect.width = (float) (k * w);
				this.clipWidth = (float) (k * w);
				this.clipHeight =
					node.isExpanded() ? (node.getDepth() + 1) * this.rowHeight
							: this.rowHeight;
				this.clipX = this.pickedx - this.deltax;
				this.clipY = this.pickedy - this.deltay;
				// the current node is a descendant of the dragged node
			}
			else if (node.isNodeAncestor(this.draggedNode)) {
				rect.x = this.pickedx - this.deltax;
				rect.y = this.pickedy - this.deltay;
				rect.width = (float) (k * w);
				// other cases
			}
			else {
				rect.x = parentOffset + offset;
				rect.y = h;
				rect.width = (float) (k * w);
			}
			g.setColor(node.getColorGetter().getColor());
			g.fill(rect);
			if (this.isDrawFrames()) {
				g.setColor(Color.black);
				g.draw(rect);
			}

			if (node.isExpanded()) {
				this.paintChildren(g, node, (float) (w * k), parentOffset
						+ offset);
			}

			offset += k * w;
		}
	}

	/**
	 * Paints the children of the passed node.
	 * 
	 * TODO : postpone the display of the dragged subtree to the end.
	 */
	protected void paintChildrenVertical(
		final Graphics2D g,
		final StateNode parentNode,
		final float parentHeight,
		final float parentOffset) {

		final Enumeration e = parentNode.children();
		final int w =
			this.isRootVisible() ? this.rowHeight * (parentNode.getLevel() + 1)
					: this.rowHeight * parentNode.getLevel();
		final float h =
			(float) (parentHeight / parentNode
				.getWeightGetter()
				.getTotalWeight(parentNode));
		final Rectangle2D.Float rect =
			new Rectangle2D.Float(w, 0, this.rowHeight, h);
		StateNode node;
		double k;
		float offset = 0;
		final int rootDepth = this.root.getDepth();

		while (e.hasMoreElements()) {
			node = (StateNode) e.nextElement();
			k = node.getWeightGetter().getWeight(node);
			this.deltay =
				this.dragStart != null ? this.dragStart.y
						- (parentOffset + offset) : 0;
			this.deltax = this.dragStart != null ? this.dragStart.x - w : 0;

			rect.width =
				node.isLeaf() && this.isAlignLeaves() ? (rootDepth
						- node.getLevel() + 1)
						* this.rowHeight : this.rowHeight;

			// the current node matches the dragged node
			if (node == this.draggedNode) {
				rect.x = this.pickedx - this.deltax;
				rect.y = this.pickedy - this.deltay;
				rect.height = (float) (k * h);
				this.clipHeight = (float) (k * h);
				this.clipWidth =
					node.isExpanded() ? (node.getDepth() + 1) * this.rowHeight
							: this.rowHeight;
				this.clipX = this.pickedx - this.deltax;
				this.clipY = this.pickedy - this.deltay;
				// the current node is a descendant of the dragged node
			}
			else if (node.isNodeAncestor(this.draggedNode)) {
				rect.x = this.pickedx - this.deltax;
				rect.y = this.pickedy - this.deltay;
				rect.height = (float) (k * h);
				// other cases
			}
			else {
				rect.y = parentOffset + offset;
				rect.x = w;
				rect.height = (float) (k * h);
			}
			g.setColor(node.getColorGetter().getColor());
			g.fill(rect);
			if (this.isDrawFrames()) {
				g.setColor(Color.black);
				g.draw(rect);
			}

			if (node.isExpanded()) {
				this.paintChildrenVertical(
					g,
					node,
					(float) (h * k),
					parentOffset + offset);
			}

			offset += k * h;
		}
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(Graphics)
	 */
	protected void paintComponent(final Graphics g) {
		switch (this.orientation) {
			case HORIZONTAL :
				this.paintHorizontal(g);
				break;
			case VERTICAL :
				this.paintVertical(g);
				break;
			default :
				this.paintHorizontal(g);
		}
	}

	protected void paintHorizontal(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		//g2.setColor(Color.white);
		//g2.fillRect(0, 0, getWidth(), getHeight());
		if (this.isRootVisible()) {
			g2.setColor(this.root.getColorGetter().getColor());
			g2.fillRect(0, 0, this.getWidth(), this.rowHeight);
			if (this.isDrawFrames()) {
				g2.setColor(Color.black);
				g2.drawRect(0, 0, this.getWidth(), this.rowHeight);
			}
		}
		if (this.root.isExpanded()) {
			this.paintChildren(g2, this.root, this.getWidth(), 0);
		}
	}

	/***************************************
	 *  TreeModelListener interface methods
	 ***************************************
	 */

	protected void paintVertical(final Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		if (this.isRootVisible()) {
			g2.setColor(this.root.getColorGetter().getColor());
			g2.fillRect(0, 0, this.rowHeight, this.getHeight());
			if (this.isDrawFrames()) {
				g2.setColor(Color.black);
				g2.drawRect(0, 0, this.rowHeight, this.getHeight());
			}
		}
		if (this.root.isExpanded()) {
			this.paintChildrenVertical(g2, this.root, this.getHeight(), 0);
		}
	}

	/**
	 * Removes all <code>TreeExpansionListener</code>s
	 */
	public void removeAllTreeExpansionListeners() {
		this.treeExpansionListerners.clear();
	}

	/**
	 * Removes a listener for <code>TreeExpansionEvent</code> events.
	 */
	public void removeTreeExpansionListener(final TreeExpansionListener listener) {
		if (listener != null) {
			this.treeExpansionListerners.remove(listener);
		}
	}

	/**
	 * Sets the alignLeaves.
	 * @param alignLeaves The alignLeaves to set
	 */
	public void setAlignLeaves(final boolean alignLeaves) {
		this.alignLeaves = alignLeaves;
		this.repaint();
	}

	/**************************************
	 * Tree expansion interface methods
	 **************************************
	 */

	/**
	 * Sets the drawFrames.
	 * @param drawFrames The drawFrames to set
	 */
	public void setDrawFrames(final boolean drawFrames) {
		this.drawFrames = drawFrames;
		this.repaint();
	}

	/**
	 * Sets the orientation.
	 * @param orientation The orientation to set
	 */
	public void setOrientation(final int orientation) {
		this.orientation = orientation;
	}

	/**
	 * Sets the rootVisible.
	 * @param rootVisible The rootVisible to set
	 */
	public void setRootVisible(final boolean rootVisible) {
		this.rootVisible = rootVisible;
	}
	/**
	 * Sets the rowHeight.
	 * @param rowHeight The rowHeight to set
	 */
	public void setRowHeight(final int rowHeight) {
		this.rowHeight = rowHeight;
		this.repaint();
	}

	/**
	 * Sets the treeModel.
	 * @param treeModel The treeModel to set
	 */
	public void setTreeModel(final TreeModel treeModel) {
		if (this.treeModel != null) {
			this.treeModel.removeTreeModelListener(this);
		}

		this.treeModel = treeModel;
		this.root = (StateNode) this.treeModel.getRoot();
		this.treeModel.addTreeModelListener(this);
	}

	/**
	 * @see javax.swing.event.TreeExpansionListener#treeCollapsed(TreeExpansionEvent)
	 */
	public void treeCollapsed(final TreeExpansionEvent event) {
		this.repaint();
	}

	/**
	 * @see javax.swing.event.TreeExpansionListener#treeExpanded(TreeExpansionEvent)
	 */
	public void treeExpanded(final TreeExpansionEvent event) {
		this.repaint();
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesChanged(TreeModelEvent)
	 */
	public void treeNodesChanged(final TreeModelEvent e) {
		this.repaint();
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesInserted(TreeModelEvent)
	 */
	public void treeNodesInserted(final TreeModelEvent e) {
		System.out.println("tree nodes inserted");
	}

	/*******************************************
	 * Mouse motion listener interface methods
	 *******************************************
	 */

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesRemoved(TreeModelEvent)
	 */
	public void treeNodesRemoved(final TreeModelEvent e) {
		System.out.println("tree nodes removed");
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeStructureChanged(TreeModelEvent)
	 */
	public void treeStructureChanged(final TreeModelEvent e) {
		System.out.println("tree structure changed");
	}

}
