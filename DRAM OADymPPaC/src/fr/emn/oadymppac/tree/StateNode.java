package fr.emn.oadymppac.tree;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 * 
 * This class implements a node that can tell whether 
 * it is expanded or collapsed and can disclose its 
 * path in the tree where it belongs.
 */
public class StateNode extends DefaultMutableTreeNode {

	/**
	 * @author Mohammad Ghoniem
	 *
	 * This weight getter returns 1 as the node's own weight
	 * and returns its child count as a total. 
	 */
	public static class ChildCountGetter implements WeightGetter {
		public double getTotalWeight(final StateNode node) {
			return node.getChildCount();
		}

		public double getWeight(final StateNode node) {
			return 1;
		}
	}
	/**
	 * @author Mohammad Ghoniem
	 *
	 * This interface defines the behaviour of a ColorGetter object:
	 * a ColorGetter returns a color for this node. It also has a type.
	 */
	public interface ColorGetter {
		public Color getColor();
		public int getType();
	}
	/**
	 * Returns <code>Color.yellow</code> when the node is a leaf
	 * and returns <code>Color.red</code> otherwise.
	 */
	public class DefaultColorGetter implements ColorGetter {
		public Color getColor() {
			return StateNode.this.isLeaf() ? Color.yellow : Color.red;
		}
		public int getType() {
			return StateNode.LEAF_NON_LEAF;
		}
	}
	/**
	 * @author Mohammad Ghoniem
	 *
	 * According to this weight getter, the node's own weight equals:
	 * <ul>
	 * <li>0 if the node is hidden (it's parent is collapsed),</li>
	 * <li>1 if the node is collapsed,</li>
	 * <li>its child count, if the node is expanded.</li>
	 * </ul>
	 * Its total weight equals its descendants count at the deepest
	 * visible level.
	 * 
	 * Instances of this class can be seen as a mix of 
	 * <code>ChildCountGetter</code> and <code>LeafCountGetter</code>
	 * objects.
	 */
	public static class ExpandedNodeCountGetter implements WeightGetter {
		public double getTotalWeight(final StateNode node) {
			final int level = node.getFirstCollapsedChild().getLevel();
			return node.getDescendantCountAtLevel(level);
		}

		public double getWeight(final StateNode node) {
			if (!node.isRoot() && !((StateNode) node.getParent()).isExpanded()) {
				return 0;
			}

			if (!node.isExpanded()) {
				return 1;
			}

			if (node.isExpanded()) {
				return node.getChildCount();
			}

			System.err.println("error in ExpandedNodeCountGetter");
			return -1;
		}
	}
	/**
	 * Returns white for leaves and darker grey shades 
	 * as the nodes get closer to the root.
	 */
	public class IntensityGradientColorGetter implements ColorGetter {
		public Color getColor() {
			return new Color(
				Color
					.HSBtoRGB(0.f, 0.f, 0.25f + 0.75f * ((float) StateNode.this
						.getLevel() / ((DefaultMutableTreeNode) StateNode.this
						.getRoot()).getDepth())));
		}

		public int getType() {
			return StateNode.INTENSITY_TOP_DOWN;
		}
	}
	/**
	 * @author Mohammad Ghoniem
	 *
	 * This weight getter returns the leaf count as the node's own weight
	 * as well as its total weight.
	 */
	public static class LeafCountGetter implements WeightGetter {
		public double getTotalWeight(final StateNode node) {
			return node.getLeafCount();
		}

		public double getWeight(final StateNode node) {
			return node.getLeafCount();
		}
	}
	/**
	 * The hue varie accross the leaves of the tree. The color of deeper
	 * nodes corresponds to the average of its children color.
	 * 
	 * TODO : improve complexity by creating a custom Color class that 
	 * holds a nodes own color and the total of its children 
	 * like we did with weights.
	 */
	public class RainbowAccrossLeavesColorGetter implements ColorGetter {
		/**
		 * Computes the average color of this node's children.
		 */
		private Color getAverageColor() {
			if (StateNode.this.isLeaf()) {
				return this.getLeafColor();
			}
			final Enumeration e = StateNode.this.children();
			StateNode current;
			Color currentColor;
			int r = 0;
			int g = 0;
			int b = 0;
			while (e.hasMoreElements()) {
				current = (StateNode) e.nextElement();
				currentColor =
					((RainbowAccrossLeavesColorGetter) current.getColorGetter())
						.getAverageColor();
				r += currentColor.getRed();
				g += currentColor.getGreen();
				b += currentColor.getBlue();
			}
			r /= StateNode.this.getChildCount();
			g /= StateNode.this.getChildCount();
			b /= StateNode.this.getChildCount();
			return new Color(r, g, b);
		}

		public Color getColor() {
			return StateNode.this.isLeaf() ? this.getLeafColor() : this
				.getAverageColor();
		}

		/**
		 * Computes the color of this leaf.
		 */
		private Color getLeafColor() {
			int index = -1;
			if (StateNode.this.isLeaf()) {
				StateNode current;
				final Enumeration e =
					((DefaultMutableTreeNode) StateNode.this.getRoot())
						.depthFirstEnumeration();
				while (e.hasMoreElements()) {
					current = (StateNode) e.nextElement();
					if (current.isLeaf()) {
						index++;
					}
					if (current == StateNode.this) {
						break;
					}
				}
				return new Color(Color.HSBtoRGB(
					(float) index
							/ ((DefaultMutableTreeNode) StateNode.this
								.getRoot()).getLeafCount(),
					1,
					1));
			}
			return null;
		}

		public int getType() {
			return StateNode.RAINBOW_ACCROSS_LEAVES;
		}
	}
	/**
	 * Splits the rainbow circle against the tree branches 
	 * and returns the appropriate color for the node's branch.
	 */
	public class RainbowHorizontalColorGetter implements ColorGetter {
		public Color getColor() {
			return StateNode.this.isRoot() ? new Color(0.f, 0.5f, 1)
					: new Color(Color.HSBtoRGB(
						((DefaultMutableTreeNode) StateNode.this.getRoot())
							.getIndex(StateNode.this.getPath()[1])
								/ (float) StateNode.this
									.getRoot()
									.getChildCount(),
						0.5f,
						1));
		}

		public int getType() {
			return StateNode.RAINBOW_ACCROSS;
		}
	}
	/**
	 * The hue varies accross the branches and the intensity 
	 * increases from the root to the leaves.
	 */
	public class RainbowIntensityColorGetter implements ColorGetter {
		public Color getColor() {
			return StateNode.this.isRoot() ? new Color(0.f, 0.f, 0.25f)
					: new Color(
						Color.HSBtoRGB(
							((DefaultMutableTreeNode) StateNode.this.getRoot())
								.getIndex(StateNode.this.getPath()[1])
									/ (float) StateNode.this
										.getRoot()
										.getChildCount(),
							0.25f,
							0.25f + 0.75f * ((float) StateNode.this.getLevel() / ((DefaultMutableTreeNode) StateNode.this
								.getRoot()).getDepth())));
		}

		public int getType() {
			return StateNode.RAINBOW_AND_INTENSITY;
		}
	}
	/**
	 * Splits the rainbow circle against the tree depth 
	 * and returns the appropriate color for the node depth.
	 */
	public class RainbowVerticalColorGetter implements ColorGetter {
		public Color getColor() {
			return new Color(Color.HSBtoRGB(
				(float) StateNode.this.getLevel()
						/ ((DefaultMutableTreeNode) StateNode.this.getRoot())
							.getDepth(),
				0.5f,
				1));
		}

		public int getType() {
			return StateNode.RAINBOW_TOP_DOWN;
		}
	}
	public static class StoredWeightGetter implements WeightGetter {
		public double getTotalWeight(final StateNode node) {
			return node.getTotalWeight();
		}

		public double getTotalWeight(final StateNode node, final int index) {
			return node.getTotalWeight(index);
		}

		public double getWeight(final StateNode node) {
			return node.getWeight();
		}

		public double getWeight(final StateNode node, final int index) {
			return node.getWeight(index);
		}
	}
	/**
	 * @author Mohammad Ghoniem
	 *
	 * An object implementing this interface must be able
	 * to return a weight for the passed node and a total
	 * weight associated to its children. 
	 */
	public interface WeightGetter {
		public double getTotalWeight(StateNode node);
		public double getWeight(StateNode node);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -1053855526967426184L;
	public static final boolean DEFAULT_EXPANDED_STATE = false;
	public static final boolean DEFAULT_OWN_WEIGHT_ADDED = false;

	public static final int WEIGHT_CHILD_COUNT = 0;
	public static final int WEIGHT_LEAF_COUNT = 1;
	public static final int WEIGHT_STORED_VALUE = 2;
	public static final int WEIGHT_EXPANDED_NODE_COUNT = 3;
	public static int DEFAULT_WEIGHT = StateNode.WEIGHT_LEAF_COUNT;
	public static final int LEAF_NON_LEAF = 0;
	public static final int INTENSITY_TOP_DOWN = 1;

	public static final int RAINBOW_TOP_DOWN = 2;

	public static final int RAINBOW_ACCROSS = 3;

	public static final int RAINBOW_AND_INTENSITY = 4;

	public static final int RAINBOW_ACCROSS_LEAVES = 5;

	public static int DEFAULT_COLOR = StateNode.LEAF_NON_LEAF;

	private boolean expanded;

	private TreePath path;

	private int current;

	private Vector weights;

	private boolean ownWeightAdded;

	private WeightGetter weightGetter;

	private ColorGetter colorGetter;

	/**
	 * Default constructor for StateNode.
	 */
	public StateNode() {
		super();
		this.init();
	}

	/**
	 * Constructor for StateNode.
	 * @param exp
	 */
	public StateNode(final boolean exp) {
		super();
		this.expanded = exp;
		this.weights = new Vector();
		this.init();
	}

	/**
	 * Constructor for StateNode.
	 * @param userObject
	 */
	public StateNode(final Object userObject) {
		super(userObject);
		this.init();
	}

	/**
	 * Constructor for StateNode.
	 * @param userObject
	 * @param allowsChildren
	 */
	public StateNode(final Object userObject, final boolean allowsChildren) {
		super(userObject, allowsChildren);
		this.init();
	}

	/**
	 * This method sets the path of the passed child 
	 * and adds it to the children of the receiver.
	 */
	public void add(final StateNode child) {
		if (this.path == null) {
			this.path = new TreePath(this);
		}

		child.setPath(this.path.pathByAddingChild(child));
		child.setOwnWeightAdded(this.isOwnWeightAdded());
		super.add(child);
		child.setColorGetter(this.colorGetter.getType());
	}

	/**
	 * Adds a new <code>Weight</code> to this node.
	 */
	public void addWeight(final double w) {
		this.weights.addElement(new Weight(w));
	}

	/**
	 * Checks whether the weights were correctly computed through
	 * the nodes of the subtree rooted at this node.
	 */
	public boolean check(final int sz) {
		if (sz < this.weightLength()) {
			return false;
		}
		/**
		 * if the node has no children, total weights should 
		 * be the same as individual weights.
		 */
		if (this.isLeaf()) {
			for (int index = 0; index < sz; index++) {
				if (this.getTotalWeight(index) != this.getWeight(index)) {
					return false;
				}
			}
			return true;
		}

		/**
		 * if this node has children, the subtree rooted 
		 * at this node is checked from bottom to top.
		 */
		for (int index = 0; index < sz; index++) {
			double total = this.isOwnWeightAdded() ? this.getWeight(index) : 0;
			for (int i = 0; i < this.children.size(); i++) {
				final StateNode n = (StateNode) this.getChildAt(i);
				if (!n.check(sz)) {
					return false;
				}
				total += n.getTotalWeight(index);
			}
			if (total != this.getTotalWeight(index)) {
				return false;
			}
		}

		return true;
	}

	/****************************************
	 * Methods related to weight operations
	 ****************************************
	 */

	/**
	 * Expands or collapses all the nodes at the
	 * passed level.
	 */
	public void expandLevel(final int level, final boolean expand) {
		final Enumeration e =
			((StateNode) this.getRoot()).breadthFirstEnumeration();
		StateNode child;
		if (level > ((StateNode) this.getRoot()).getDepth()) {
			return;
		}
		while (e.hasMoreElements()) {
			child = (StateNode) e.nextElement();
			if (child.getLevel() == level) {
				child.setExpanded(expand);
			}
		}
	}

	/**
	 * Expands or collapses all the nodes of the 
	 * subtree rooted at this node.
	 */
	public void expandSubTree(final boolean expand) {
		final Enumeration e = this.depthFirstEnumeration();
		StateNode child;
		this.setExpanded(expand);
		while (e.hasMoreElements()) {
			child = (StateNode) e.nextElement();
			if (!child.isLeaf()) {
				child.setExpanded(expand);
			}
		}
	}

	/**
	 * Expands or collapses all the nodes between the root and
	 * the passed level. The nodes of the passed level 
	 * keep their state unchanged.
	 */
	public void expandToLevel(final int level, final boolean expand) {
		final Enumeration e =
			((StateNode) this.getRoot()).breadthFirstEnumeration();
		StateNode child;
		if (level > ((StateNode) this.getRoot()).getDepth()) {
			System.err.println("Warning: required level " + level
					+ "exceeds tree depth!");
		}

		while (e.hasMoreElements()) {
			child = (StateNode) e.nextElement();
			if (child.getLevel() < level) {
				child.setExpanded(expand);
			}
		}
	}

	/**
	 * Returns the colorGetter.
	 * @return ColorGetter
	 */
	public ColorGetter getColorGetter() {
		if (this.colorGetter == null) {
			switch (StateNode.DEFAULT_COLOR) {
				case LEAF_NON_LEAF :
					return new DefaultColorGetter();
				case INTENSITY_TOP_DOWN :
					return new IntensityGradientColorGetter();
				case RAINBOW_TOP_DOWN :
					return new RainbowVerticalColorGetter();
				case RAINBOW_ACCROSS :
					return new RainbowHorizontalColorGetter();
				case RAINBOW_AND_INTENSITY :
					return new RainbowIntensityColorGetter();
				case RAINBOW_ACCROSS_LEAVES :
					return new RainbowAccrossLeavesColorGetter();
				default :
					return new DefaultColorGetter();
			}
		}
		return this.colorGetter;
	}

	/**
	 * Returns the current weight index.
	 * @return int
	 */
	public int getCurrent() {
		return this.current;
	}

	/**
	 * Returns the number of descendants of this node whose level
	 * equals <code>level</code>. If the level of this node equals
	 * the passed level, this method returns 1. If the passed level
	 * is smaller than the level of this node, it returns -1.
	 * 
	 * @param level
	 * @return
	 */
	public int getDescendantCountAtLevel(final int level) {
		if (level < this.getLevel()) {
			System.err.println("Warning: required level " + level
					+ "exceeds parent level!");
			return 0;
		}

		//if (level == this.getLevel()) return 1;

		final Enumeration e = this.breadthFirstEnumeration();
		int count = 0;
		StateNode child;
		while (e.hasMoreElements()) {
			child = (StateNode) e.nextElement();

			// since children are enumerated in breadth first order
			// there is no need to count beyond the required level
			if (child.getLevel() > level) {
				return count;
			}

			if (child.getLevel() == level) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Returns the first (shallowest) collapsed child. 
	 * 
	 * @return
	 */
	public StateNode getFirstCollapsedChild() {
		final Enumeration e =
			((StateNode) this.getRoot()).breadthFirstEnumeration();
		StateNode res = this;
		while (e.hasMoreElements()) {
			res = (StateNode) e.nextElement();
			if (!res.isExpanded()) {
				break;
			}
		}
		return res;
	}

	/**
	 * Returns the first (shallowest) expanded child. 
	 * 
	 * @return
	 */
	public StateNode getFirstExpandedChild() {
		final Enumeration e =
			((StateNode) this.getRoot()).breadthFirstEnumeration();
		StateNode res = this;
		while (e.hasMoreElements()) {
			res = (StateNode) e.nextElement();
			if (res.isExpanded()) {
				break;
			}
		}
		return res;
	}

	/**
	 * Returns the leaves contained in the subtree
	 * rooted at this node as an array.
	 */
	public StateNode[] getLeaves() {
		final Vector v = new Vector();
		final Enumeration e = this.depthFirstEnumeration();
		StateNode node;
		while (e.hasMoreElements()) {
			node = (StateNode) e.nextElement();
			if (node.isLeaf()) {
				v.addElement(node);
			}
		}
		final StateNode[] res = new StateNode[v.size()];
		for (int i = 0; i < v.size(); i++) {
			res[i] = (StateNode) v.elementAt(i);
		}
		return res;
	}

	/**
	 * Returns the number of nodes whose level equals <code>level</code>
	 * accross the whole tree.
	 * 
	 * @param level
	 * @return
	 */
	public int getNodeCountAtLevel(final int level) {
		return ((StateNode) this.getRoot()).getDescendantCountAtLevel(level);
	}

	/**
	 * Returns the nodes at the passed level belonging
	 * to the same tree as this node.
	 */
	public StateNode[] getNodesAtLevel(final int level) {
		final Vector v = new Vector();

		if (level > 0) {
			final Enumeration e =
				((StateNode) this.getRoot()).breadthFirstEnumeration();
			StateNode node;
			while (e.hasMoreElements()) {
				node = (StateNode) e.nextElement();

				if (node.getLevel() > level) {
					break;
				}

				if (node.getLevel() == level) {
					v.addElement(node);
				}
			}
		}
		else if (level == 0) {
			v.addElement(this.getRoot());
		}

		final StateNode[] res = new StateNode[v.size()];
		for (int i = 0; i < v.size(); i++) {
			res[i] = (StateNode) v.elementAt(i);
		}
		return res;
	}

	/**
	 * Returns the total weight of the subtree 
	 * rooted at this node with regard to the current 
	 * weight index.
	 * @return double
	 */
	public double getTotalWeight() {
		return ((Weight) this.weights.elementAt(this.current)).getTotal();
	}

	/**
	 * Returns the total weight of the subtree 
	 * rooted at this node with regard to the 
	 * passed weight index.
	 * @return double
	 */
	public double getTotalWeight(final int index) {
		return ((Weight) this.weights.elementAt(index)).getTotal();
	}

	/**
	 * Returns the path of the receiver.
	 * @return TreePath
	 */
	public TreePath getTreePath() {
		return this.path;
	}

	/**
	 * Returns the weight at the current weight index.
	 */
	public double getWeight() {
		return this.getWeight(this.current);
	}

	/**
	 * Returns the weight at the passed index.
	 * @return double
	 */
	public double getWeight(final int index) {
		return ((Weight) this.weights.elementAt(index)).getValue();
	}

	/**
	 * Returns the weightGetter.
	 * @return WeightGetter
	 */
	public WeightGetter getWeightGetter() {
		if (this.weightGetter == null) {
			switch (StateNode.DEFAULT_WEIGHT) {
				case WEIGHT_CHILD_COUNT :
					return new ChildCountGetter();
				case WEIGHT_LEAF_COUNT :
					return new LeafCountGetter();
				case WEIGHT_STORED_VALUE :
					return new StoredWeightGetter();
			}
		}
		return this.weightGetter;
	}

	/**
	 * Returns all weights.
	 */
	public Vector getWeights() {
		return this.weights;
	}

	/**
	 * Increments the total of the current weight 
	 * by <code>val</code>.
	 */
	public void incrTotalWeight(final double val) {
		((Weight) this.weights.elementAt(this.current)).incrTotal(val);
	}

	/**
	 * Increments the total of the weight at the passed index
	 * by <code>val</code>.
	 */
	public void incrTotalWeight(final int index, final double val) {
		((Weight) this.weights.elementAt(index)).incrTotal(val);
	}

	private void init() {
		this.expanded = StateNode.DEFAULT_EXPANDED_STATE;
		this.weights = new Vector();
		this.ownWeightAdded = StateNode.DEFAULT_OWN_WEIGHT_ADDED;
		this.weightGetter = this.getWeightGetter();
		this.colorGetter = this.getColorGetter();
		this.path = new TreePath(this);
	}

	/**
	 * Returns <code>true</code> when the node is 
	 * expanded, and <code>false</code> otherwise.
	 * @return boolean
	 */
	public boolean isExpanded() {
		return this.expanded;
	}

	/**
	 * Returns the ownWeightAdded.
	 * @return boolean
	 */
	public boolean isOwnWeightAdded() {
		return this.ownWeightAdded;
	}

	/**
	 * Adds the children of the second node to those 
	 * of the first one and removes the second node.
	 */
	public void mergeChildren(final StateNode child1, final StateNode child2) {
		if (child1.isLeaf() || child2.isLeaf()) {
			return;
		}

		final int count = child2.getChildCount();
		for (int i = 0; i < count; i++) {
			child1.add((StateNode) child2.getFirstChild());
		}

		child2.removeFromParent();
	}

	/**
	 * Sets the colorGetter.
	 * @param colorGetter The colorGetter to set
	 */
	public void setColorGetter(final ColorGetter colorGetter) {
		this.colorGetter = colorGetter;
	}

	public void setColorGetter(final int flag) {
		switch (flag) {
			case LEAF_NON_LEAF :
				this.colorGetter = new DefaultColorGetter();
				break;
			case INTENSITY_TOP_DOWN :
				this.colorGetter = new IntensityGradientColorGetter();
				break;
			case RAINBOW_TOP_DOWN :
				this.colorGetter = new RainbowVerticalColorGetter();
				break;
			case RAINBOW_ACCROSS :
				this.colorGetter = new RainbowHorizontalColorGetter();
				break;
			case RAINBOW_AND_INTENSITY :
				this.colorGetter = new RainbowIntensityColorGetter();
				break;
			case RAINBOW_ACCROSS_LEAVES :
				this.colorGetter = new RainbowAccrossLeavesColorGetter();
				break;
			default :
				this.colorGetter = new DefaultColorGetter();
		}

		final Enumeration e = this.children();
		while (e.hasMoreElements()) {
			((StateNode) e.nextElement()).setColorGetter(flag);
		}
	}

	/**
	 * Sets the current weight index.
	 * @param current The current weight index to set
	 */
	public void setCurrent(final int current) {
		this.current = current;
	}

	/**
	 * Sets <code>expanded</code> to the passed <code>boolean</code>.
	 * @param expanded The expanded to set
	 */
	public void setExpanded(final boolean expanded) {
		this.expanded = expanded;
	}

	/**
	 * Sets the ownWeightAdded.
	 * @param ownWeightAdded The ownWeightAdded to set
	 */
	public void setOwnWeightAdded(final boolean ownWeightAdded) {
		this.ownWeightAdded = ownWeightAdded;
	}

	/**
	 * Sets the path of the receiver.
	 * @param path The path to set
	 */
	public void setPath(final TreePath path) {
		this.path = path;
	}

	/**
	 * Sets the total weight at the current index.
	 */
	public void setTotalWeight(final double val) {
		((Weight) this.weights.elementAt(this.current)).setTotal(val);
	}

	/**
	 * Sets the total weight at the passed index.
	 */
	public void setTotalWeight(final int index, final double val) {
		((Weight) this.weights.elementAt(index)).setTotal(val);
	}

	/**
	 * Sets up the weights of this node and those of the subtree 
	 * rooted at this node from the beginning till the 
	 * <code>size</code>th weight.
	 */
	public void setup(final int size) {
		// zero weight elements are added to weights 
		// vector until it reaches the required size
		while (this.weightLength() < size) {
			this.addWeight(0.0);
		}
		// if the node is a leaf, its total weight 
		// and its own weight are the same
		if (this.isLeaf() || this.isOwnWeightAdded()) {
			for (int index = 0; index < size; index++) {
				this.setTotalWeight(index, this.getWeight(index));
			}
		}
		// if the node has children, the total weight of the node
		// is the sum of the total weights of its children nodes.
		for (int i = 0; i < this.getChildCount(); i++) {
			final StateNode n = (StateNode) this.getChildAt(i);
			n.setup(size);
			for (int index = 0; index < this.weightLength(); index++) {
				this.incrTotalWeight(index, n.getTotalWeight(index));
			}
		}
	}

	/**
	 * Sets the weight at the passed index.
	 */
	public void setWeight(final int index, final double val) {
		((Weight) this.weights.elementAt(index)).setValue(val);
	}

	/**
	 * Sets the weight getter.
	 * 
	 * @param flag
	 */
	public void setWeightGetter(final int flag) {
		switch (flag) {
			case WEIGHT_CHILD_COUNT :
				this.weightGetter = new ChildCountGetter();
				break;
			case WEIGHT_LEAF_COUNT :
				this.weightGetter = new LeafCountGetter();
				break;
			case WEIGHT_STORED_VALUE :
				this.weightGetter = new StoredWeightGetter();
				break;
			case WEIGHT_EXPANDED_NODE_COUNT :
				this.weightGetter = new ExpandedNodeCountGetter();
				break;
			default :
				this.weightGetter = new LeafCountGetter();
		}

		final Enumeration e = this.depthFirstEnumeration();
		while (e.hasMoreElements()) {
			((StateNode) e.nextElement()).setWeightGetter(this.weightGetter);
		}
	}

	/**
	 * Sets the weightGetter.
	 * @param weightGetter The weightGetter to set
	 */
	public void setWeightGetter(final WeightGetter weightGetter) {
		this.weightGetter = weightGetter;
	}

	/**
	 * Swap these two nodes only if they are siblings.
	 */
	public void swapChildren(final StateNode child1, final StateNode child2) {
		if (!child1.isNodeSibling(child2)) {
			return;
		}
		final int index1 = this.getIndex(child1);
		final int index2 = this.getIndex(child2);

		this.insert(child1, index2);
		this.insert(child2, index1);
	}

	/**
	 * Returns the number of weights held by this node.
	 * @return int
	 */
	public int weightLength() {
		return this.weights.size();
	}

}
