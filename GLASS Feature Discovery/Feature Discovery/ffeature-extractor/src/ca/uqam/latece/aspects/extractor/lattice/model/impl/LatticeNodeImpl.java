package ca.uqam.latece.aspects.extractor.lattice.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import ca.uqam.latece.aspects.extractor.lattice.graph.model.NodeFeatureType;
import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;

public class LatticeNodeImpl implements LatticeNode {

	private String name;
	

	private Set<Object> intent;

	private Set<Object> extent;

	private transient Set<LatticeNode> parents;

	private Set<LatticeNode> children;

	private List<NodeFeatureType> types = new ArrayList<NodeFeatureType>();
	

	

	
	public LatticeNodeImpl() {
		intent = new HashSet<Object>();
		extent = new HashSet<Object>();
		parents = new HashSet<LatticeNode>();
		children = new HashSet<LatticeNode>();
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
	public Set<LatticeNode> getParents() {
		return parents;
	}

	@Override
	public Set<LatticeNode> getChildren() {
		return children;
	}

	@Override
	public void addChild(LatticeNode childNode) {
		children.add(childNode);

	}
	
	public List<NodeFeatureType> getTypes() {
		return types;
	}
	public void setTypes(List<NodeFeatureType> types) {
		this.types = types;
	}

	@Override
	public void addParent(LatticeNode parentNode) {
		parents.add(parentNode);
	}

	@Override
	public void removeChild(LatticeNode childNode) {
		children.remove(childNode);

	}

	@Override
	public void removeParent(LatticeNode parentNode) {
		parents.remove(parentNode);

	}

	@Override
	public void acceptVisitor(Visitor aVisitor, Visitor.Direction direction) {
		aVisitor.visitLatticeNode(this, direction);
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
	public boolean hasParent(LatticeNode parentNode) {
		return parents.contains(parentNode);
	}

	@Override
	public boolean hasChild(LatticeNode childNode) {
		return children.contains(childNode);
	}

	@Override
	public LatticeNode copy() {
		LatticeNode copy = new LatticeNodeImpl();
		copy.addCollectionToExtent(getExtent());
		copy.addCollectionToIntent(getIntent());
		return copy;
	}

	public void takePlaceOf(LatticeNode another){
		// first connect to children of another, and 
		// unhook them from another
		for (LatticeNode child: another.getChildren()) {
			// hook self
			this.addChild(child);
			child.addParent(this);
			
			// unhook another
			another.removeChild(child);
			child.removeParent(another);
		}
		
		// second connect to parents of another
		// and unhook them from another
		for (LatticeNode parent: another.getParents()){
			// hook self
			this.addParent(parent);
			parent.addChild(this);
			
			// unhook another
			another.removeParent(parent);
			parent.removeChild(another);
		}
	}

@Override
	public int hashCode() {
		return Objects.hash(extent, intent);
	}


	public void setIntent(Set<Object> intent) {
		this.intent = intent;
	}
	
	public void setExtent(Set<Object> extent) {
		this.extent = extent;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
