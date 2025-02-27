package glass.lattice.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import glass.lattice.model.ILatticeNode;
import glass.lattice.visitor.IVisitor;
import glass.lattice.visitor.IVisitor.Direction;

public class LatticeNode implements ILatticeNode{
	
	private String name;
	private Set<Object> intent;
	private Set<Object> extent;
	
	private transient Set<ILatticeNode> parents;
	private Set<ILatticeNode> children;
	private List<NodeFeatureType> types = new ArrayList<NodeFeatureType>();
	
	public LatticeNode() {
		intent = new HashSet<Object>();
		extent = new HashSet<Object>();
		parents = new HashSet<ILatticeNode>();
		children = new HashSet<ILatticeNode>();
	}
	
	@Override
	public Set<Object> getExtent() {
		return extent;
	}

	@Override
	public void addToExtent(Object anObject) {
		extent.add(anObject);
	}

	@Override
	public void addCollectionToExtent(Collection<Object> objects) {
		extent.addAll(objects);
	}

	@Override
	public void addCollectionToIntent(Collection<Object> properties) {
		intent.addAll(properties);
	}

	@Override
	public void removeFromExtent(Object anObject) {
		extent.remove(anObject);
	}

	@Override
	public void removeFromIntent(Object anObject) {
		intent.remove(anObject);
	}

	@Override
	public void addToIntent(Object anObject) {
		intent.add(anObject);
	}

	@Override
	public Set<Object> getIntent() {
		return intent;
	}

	@Override
	public Set<ILatticeNode> getParents() {
		return parents;
	}

	@Override
	public Set<ILatticeNode> getChildren() {
		return children;
	}

	@Override
	public void addChild(ILatticeNode childNode) {
		children.add(childNode);
	}

	@Override
	public void addParent(ILatticeNode parentNode) {
		parents.add(parentNode);
	}

	@Override
	public void removeChild(ILatticeNode childNode) {
		children.remove(childNode);
	}

	@Override
	public boolean hasParent(ILatticeNode parentNode) {
		return parents.contains(parentNode);
	}

	@Override
	public boolean hasChild(ILatticeNode childNode) {
		return children.contains(childNode);
	}

	@Override
	public void removeParent(ILatticeNode parentNode) {
		parents.remove(parentNode);
	}

	@Override
	public void acceptVisitor(IVisitor aVisitor, Direction direction) {
		aVisitor.visitLatticeNode(this, direction);
	}

	@Override
	public ILatticeNode copy() {
		ILatticeNode copy = new LatticeNode();
		copy.addCollectionToExtent(getExtent());
		copy.addCollectionToIntent(getIntent());
		return copy;
	}

	@Override
	public void takePlaceOf(ILatticeNode another) {
		// first connect to children of another, and 
		// unhook them from another
		for (ILatticeNode child: another.getChildren()) {
			// hook self
			this.addChild(child);
			child.addParent(this);
			
			// unhook another
			another.removeChild(child);
			child.removeParent(another);
		}
		
		// second connect to parents of another
		// and unhook them from another
		for (ILatticeNode parent: another.getParents()){
			// hook self
			this.addParent(parent);
			parent.addChild(this);
			
			// unhook another
			another.removeParent(parent);
			parent.removeChild(another);
		}
	}

	@Override
	public void setIntent(Set<Object> intent) {
		this.intent = intent;
	}

	@Override
	public void setExtent(Set<Object> extent) {
		this.extent = extent;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<NodeFeatureType> getTypes() {
		return this.types;
	}

	@Override
	public void setTypes(List<NodeFeatureType> types) {
		this.types = types;
	}

}
