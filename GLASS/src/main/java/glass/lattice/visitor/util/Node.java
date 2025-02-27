package glass.lattice.visitor.util;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private Node _parent;
	private List<Node> _childs;
	private Type _type;

	public Node(Type type) {
		_childs = new ArrayList<Node>();
		_type = type;
		_parent = null;
	}

	public Node(Type type, Node parent) {
		_childs = new ArrayList<Node>();
		_type = type;
		_parent = parent;
		if (parent != null) {
			parent.appendChild(this);
		}
	}

	public void appendChild(Node child) {
		_childs.add(child);
	}

	public List<Node> getChilds() {
		return _childs;
	}

	public Type getType() {
		return _type;
	}

	public boolean isRoot() {
		return _parent == null;
	}

	public Node getParent() {
		return _parent;
	}

	public boolean isParentFirstChild() {
		return (_parent != null && _parent.getChilds().indexOf(this) == 0);
	}

	public boolean isParentLastChild() {
		return (_parent != null && _parent.getChilds().lastIndexOf(this) == _parent.getChilds().size() - 1);
	}

	public Node asSubTree() {
		if (isRoot()) {
			return this;
		}

		Node newRoot = new Node(_type);

		for (Node child : _childs) {
			@SuppressWarnings("unused")
			Node newSubNode = new Node(child.getType(), newRoot);
		}

		return newRoot;
	}

	@Override
	public String toString() {
		return super.toString() + "[" + _childs.size() + " childs]";
	}
}
