package fr.emn.oadymppac.widgets;

import fr.emn.oadymppac.tree.StateNode;
import fr.emn.oadymppac.utils.FloatStack;
import gl4java.GLContext;
import gl4java.GLEnum;
import gl4java.awt.GLCanvas;
import gl4java.utils.glut.fonts.GLUTFuncLightImplWithFonts;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.3 $
 *
 * An openGL component that displays hierarchical data 
 * as an icicle tree and allows interaction with its 
 * nodes.
 */
public class GLTreeBrowser extends GLCanvas implements IcicleTreeBrowser,
		TreeExpansionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6350775920419050707L;
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

	GLUTFuncLightImplWithFonts glut;
	FloatStack vertices;
	FloatStack colors;
	int width;
	int height;

	ClusterEvent event;
	Vector listeners = new Vector();

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

	public GLTreeBrowser() {
		this(
			new DefaultTreeModel(new StateNode("root")),
			IcicleTreeBrowser.HORIZONTAL,
			300,
			50);
	}

	public GLTreeBrowser(final TreeModel tree) {
		this(tree, IcicleTreeBrowser.HORIZONTAL, 300, 50);
	}

	public GLTreeBrowser(final TreeModel tree, final int orientation) {
		this(tree, orientation, 300, 50);
	}

	public GLTreeBrowser(
		final TreeModel tree,
		final int orientation,
		final int width,
		final int height) {
		super(width, height);
		this.orientation = orientation;
		this.treeModel = tree;
		this.treeModel.addTreeModelListener(this);
		this.addMouseMotionListener(this);
		this.addTreeExpansionListener(this);
		this.root = (StateNode) tree.getRoot();
		this.glut = new GLUTFuncLightImplWithFonts(this.gl, this.glu);
		this.vertices = new FloatStack();
		this.colors = new FloatStack();
	}
	public void addClusterListener(final ClusterListener l) {
		this.listeners.addElement(l);
	}
	void addRect(
		final float x1,
		final float y1,
		final float x2,
		final float y2,
		final float z1,
		final float z2,
		final float z3,
		final float z4,
		final float[] rgb) {
		this.vertices.push(x1);
		this.vertices.push(y1);
		this.vertices.push(z1);
		this.colors.push(rgb[0]);
		this.colors.push(rgb[1]);
		this.colors.push(rgb[2]);

		this.vertices.push(x2);
		this.vertices.push(y1);
		this.vertices.push(z2);
		this.colors.push(rgb[0]);
		this.colors.push(rgb[1]);
		this.colors.push(rgb[2]);

		this.vertices.push(x2);
		this.vertices.push(y2);
		this.vertices.push(z3);
		this.colors.push(rgb[0]);
		this.colors.push(rgb[1]);
		this.colors.push(rgb[2]);

		this.vertices.push(x2);
		this.vertices.push(y2);
		this.vertices.push(z3);
		this.colors.push(rgb[0]);
		this.colors.push(rgb[1]);
		this.colors.push(rgb[2]);

		this.vertices.push(x1);
		this.vertices.push(y2);
		this.vertices.push(z4);
		this.colors.push(rgb[0]);
		this.colors.push(rgb[1]);
		this.colors.push(rgb[2]);

		this.vertices.push(x1);
		this.vertices.push(y1);
		this.vertices.push(z1);
		this.colors.push(rgb[0]);
		this.colors.push(rgb[1]);
		this.colors.push(rgb[2]);
	}
	void addRect(
		final float x1,
		final float y1,
		final float x2,
		final float y2,
		final float[] rgb) {
		if (this.vertices.size() > 100000) {
			this.flush();
		}

		final float z1 = this.fisheye(x1, y1);
		final float z2 = this.fisheye(x2, y1);
		final float z3 = this.fisheye(x2, y2);
		final float z4 = this.fisheye(x1, y2);

		final boolean allNull = z1 == 0 && z2 == 0 && z3 == 0 && z4 == 0;
		final boolean anyNonNull = z1 != 0 && z2 != 0 || z3 != 0 || z4 != 0;
		final boolean shouldSplit = anyNonNull && !allNull;

		if (shouldSplit) {
			final float x12 = (x1 + x2) / 2;
			final float y12 = (y1 + y2) / 2;
			final float z12 = this.fisheye(x12, y1);
			final float z23 = this.fisheye(x2, y12);
			final float z34 = this.fisheye(x12, y2);
			final float z41 = this.fisheye(x1, y12);
			final float zmid = this.fisheye(x12, y12);
			this.addRect(x1, y1, x12, y12, z1, z12, zmid, z41, rgb);
			this.addRect(x12, y1, x2, y12, z12, z2, z23, zmid, rgb);
			this.addRect(x1, y12, x12, y2, z41, zmid, z34, z4, rgb);
			this.addRect(x12, y12, x2, y2, zmid, z23, z3, z34, rgb);
		}
		else {
			this.addRect(x1, y1, x2, y2, z1, z2, z3, z4, rgb);
		}
	}
	/**
	 * Adds a listener for <code>TreeExpansionEvent</code> events.
	 */
	public void addTreeExpansionListener(final TreeExpansionListener listener) {
		if (listener != null) {
			this.treeExpansionListerners.add(listener);
		}
	}
	public void display() {
		/* Standard GL4Java Init */
		if (this.glj.gljMakeCurrent() == false) {
			System.out.println("problem with gljMakeCurrent in tree browser");
			System.out.println("isEnabled=" + this.glj.isEnabled());
			System.out.println("isCurrent=" + this.glj.gljIsCurrent());
			System.out.println("isInit=" + this.glj.gljIsInit());
			System.out.println("isRequested=" + this.glj.gljIsRequested());
			System.out.println("current=" + GLContext.gljGetCurrentContext());
			return;
		}

		float w = this.width;
		float h = this.height;

		if (w < h) {
			h = w;
		}
		else {
			w = h;
		}

		this.gl
			.glClear(GLEnum.GL_COLOR_BUFFER_BIT | GLEnum.GL_DEPTH_BUFFER_BIT);
		this.glj.gljCheckGL();
		this.gl.glEnableClientState(GLEnum.GL_VERTEX_ARRAY);
		//gl.glEnableClientState(GL_COLOR_ARRAY);
		this.gl.glEnable(GLEnum.GL_DEPTH_TEST);

		switch (this.orientation) {
			case HORIZONTAL :
				this.paintHorizontal();
				break;
			case VERTICAL :
				this.paintVertical();
				break;
			default :
				this.paintHorizontal();
		}
		this.gl.glFlush();
		//flush();

		this.gl.glDisable(GLEnum.GL_DEPTH_TEST);

		this.gl.glColor3f(0, 0, 0);
		this.glj.gljSwap();
		this.glj.gljCheckGL();
		this.glj.gljFree();

		this.gl.glDisableClientState(GLEnum.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL_COLOR_ARRAY);        
	}
	void drawRect(
		final float x1,
		final float y1,
		final float x2,
		final float y2,
		final float[] rgb) {
		this.gl.glColor3fv(rgb);
		this.gl.glRectf(x1, y1, x2, y2);
		this.gl.glColor3f(0, 0, 0);
		this.gl.glLineWidth(2);
		this.gl.glBegin(GLEnum.GL_LINE_LOOP);
		this.gl.glVertex3f(x1, y1, 0);
		this.gl.glVertex3f(x2, y1, 0);
		this.gl.glVertex3f(x2, y2, 0);
		this.gl.glVertex3f(x1, y2, 0);
		this.gl.glEnd();
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
		// int h = isRootVisible()?rowHeight*(parentNode.getLevel()+1):rowHeight*parentNode.getLevel();
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
		// int w = isRootVisible()?rowHeight*(parentNode.getLevel()+1):rowHeight*parentNode.getLevel();
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

	public float fisheye(final float x, final float y) {
		//	if (lens != null)
		//        return lens.pointAltitude(x, y);
		return 0;
	}

	protected void flush() {
		//    	System.out.println("*************************");
		//    	for (int i=0; i < colors.size(); i = i+3){
		//	    	System.out.println(colors.get(i)+" "+colors.get(i+1)+" "+colors.get(i+2));
		//    	}

		//        gl.glColorPointer(3, GL_FLOAT, 0, colors.getData());
		//        gl.glVertexPointer(3, GL_FLOAT, 0, vertices.getData());
		//        gl.glDrawArrays(GL_TRIANGLES, 0, vertices.size());
		//glj.gljSwap();
		if (this.isDrawFrames()) {
			this.gl.glDisableClientState(GLEnum.GL_COLOR_ARRAY);
			this.gl.glColor3f(0, 0, 0);
			this.gl.glDrawArrays(GLEnum.GL_LINE_LOOP, 0, this.vertices.size());
		}
		this.vertices.clear();
	}

	String[] getChildrenArray(final StateNode parent) {
		final String[] res = new String[parent.getChildCount()];
		for (int i = 0; i < parent.getChildCount(); i++) {
			res[i] =
				(String) ((StateNode) parent.getChildAt(i)).getUserObject();
		}
		return res;
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

	/**
	 * @see gl4java.awt.GLCanvas#init()
	 */
	public void init() {
		this.gl.glClearColor(1, 1, 1, 1);
		this.gl.glShadeModel(GLEnum.GL_FLAT);
		this.gl.glDisable(GLEnum.GL_DITHER);
		this.gl.glEnable(GLEnum.GL_POLYGON_SMOOTH);
		this.gl.glEnable(GLEnum.GL_LINE_SMOOTH);
		this.glj.gljCheckGL();
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

			this.pickedx = e.getX();
			this.pickedy = e.getY();

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

	protected void paintChildren(
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
		final float[] rgb = new float[3];

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
			this.drawRect(rect.x, rect.y, rect.x + rect.width, rect.y
					+ rect.height, node
				.getColorGetter()
				.getColor()
				.getRGBColorComponents(rgb));
			//			addRect(rect.x, rect.y, rect.x+rect.width, rect.y + rect.height, 
			//					node.getColorGetter().getColor().getRGBColorComponents(rgb)); 

			if (node.isExpanded()) {
				this
					.paintChildren(node, (float) (w * k), parentOffset + offset);
			}

			offset += k * w;
		}
	}

	protected void paintChildrenVertical(
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
		final float[] rgb = new float[3];

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
			this.drawRect(rect.x, rect.y, rect.x + rect.width, rect.y
					+ rect.height, node
				.getColorGetter()
				.getColor()
				.getRGBColorComponents(rgb));
			//			addRect(rect.x, rect.y, rect.x + rect.width, rect.y + rect.height, 
			//				node.getColorGetter().getColor().getRGBColorComponents(rgb));
			if (node.isExpanded()) {
				this.paintChildrenVertical(node, (float) (h * k), parentOffset
						+ offset);
			}

			offset += k * h;
		}
	}

	protected void paintHorizontal() {
		final float[] rgb = new float[3];
		if (this.isRootVisible()) {
			this.drawRect(0, 0, this.getWidth(), this.rowHeight, this.root
				.getColorGetter()
				.getColor()
				.getRGBColorComponents(rgb));
			//			addRect(0, 0, getWidth(), rowHeight, root.getColorGetter().getColor().getRGBColorComponents(rgb));
		}
		if (this.root.isExpanded()) {
			this.paintChildren(this.root, this.getWidth(), 0);
		}
	}

	/****************************************************************
	 * Methods related to TreeExpansion listeners of this component
	 ****************************************************************
	 */

	protected void paintVertical() {
		final float[] rgb = new float[3];
		if (this.isRootVisible()) {
			this.drawRect(0, 0, this.rowHeight, this.getHeight(), this.root
				.getColorGetter()
				.getColor()
				.getRGBColorComponents(rgb));
			//			addRect(0, 0, columnWidth, getHeight(), root.getColorGetter().getColor().getRGBColorComponents(rgb));
		}
		if (this.root.isExpanded()) {
			this.paintChildrenVertical(this.root, this.getHeight(), 0);
		}
	}

	/**
	 * @see gl4java.awt.GLCanvas#preInit()
	 */
	public void preInit() {
		this.doubleBuffer = true;
	}

	public void removeAllListeners() {
		this.listeners.removeAllElements();
	}

	/**
	 * Removes all <code>TreeExpansionListener</code>s
	 */
	public void removeAllTreeExpansionListeners() {
		this.treeExpansionListerners.clear();
	}

	public void removeClusterListener(final ClusterListener l) {
		this.listeners.remove(l);
	}

	/**
	 * Removes a listener for <code>TreeExpansionEvent</code> events.
	 */
	public void removeTreeExpansionListener(final TreeExpansionListener listener) {
		if (listener != null) {
			this.treeExpansionListerners.remove(listener);
		}
	}

	public void reshape(final int w, final int h) {
		//System.out.println("w = "+w+" h="+h);
		this.width = w;
		this.height = h;
		this.gl.glViewport(0, 0, w, h);
		this.gl.glMatrixMode(GLEnum.GL_PROJECTION);
		this.gl.glLoadIdentity();
		this.gl.glOrtho(0, this.width, this.height, 0, -1, 1);
		this.gl.glMatrixMode(GLEnum.GL_MODELVIEW);
		this.gl.glLoadIdentity();

		this.glj.gljCheckGL();
	}

	/**
	 * Sets the alignLeaves.
	 * @param alignLeaves The alignLeaves to set
	 */
	public void setAlignLeaves(final boolean alignLeaves) {
		this.alignLeaves = alignLeaves;
	}

	/**
	 * Sets the drawFrames.
	 * @param drawFrames The drawFrames to set
	 */
	public void setDrawFrames(final boolean drawFrames) {
		this.drawFrames = drawFrames;
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
	}

	/**
	 * Sets the treeModel.
	 * @param treeModel The treeModel to set
	 */
	public void setTreeModel(final TreeModel treeModel) {
		this.treeModel = treeModel;
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
	}

	/*******************************************
	 * Mouse motion listener interface methods
	 *******************************************
	 */

	/**
	 * @see javax.swing.event.TreeModelListener#treeNodesRemoved(TreeModelEvent)
	 */
	public void treeNodesRemoved(final TreeModelEvent e) {
	}

	/**
	 * @see javax.swing.event.TreeModelListener#treeStructureChanged(TreeModelEvent)
	 */
	public void treeStructureChanged(final TreeModelEvent e) {
		System.out.println("ho hoh hoh");
		System.out.println("new leaf count="
				+ ((StateNode) this.treeModel.getRoot()).getLeafCount());
		this.repaint();
	}

}
