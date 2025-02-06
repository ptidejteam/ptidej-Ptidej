package ca.uqam.latece.aspects.extractor.lattice.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import ca.uqam.latece.aspects.extractor.lattice.graph.model.NodeFeatureType;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor.Direction;

public interface LatticeNode {
	
	public Set<Object> getExtent();
	
	public void addToExtent(Object anObject);
	
	public void addCollectionToExtent(Collection<Object> objects);
	
	public void addCollectionToIntent(Collection<Object> properties);
	
	public void removeFromExtent(Object anObject);
	
	public void removeFromIntent(Object anObject);
	
	public void addToIntent(Object anObject);
	
	public Set<Object> getIntent();
	
	public Set<LatticeNode> getParents();
	
	public Set<LatticeNode> getChildren();
	
	public void addChild(LatticeNode childNode);
	
	public void addParent(LatticeNode parentNode);
	
	public void removeChild(LatticeNode childNode);
	
	public boolean hasParent(LatticeNode parentNode);
	
	public boolean hasChild(LatticeNode childNode);
	
	public void removeParent(LatticeNode parentNode);
	
	public void acceptVisitor(Visitor aVisitor, Direction direction);
		
	/**
	 * returns a new LatticeNode that has same extent and same intent, BUT
	 * 1) does not copy children
	 * 2) does not copy parents
	 * 3) has visited status to false 
	 * @return
	 */
	public LatticeNode copy();
	
	/**
	 * this method makes receiver take place of <code>another</code>
	 * in the lattice that contains <code>another</code>.
	 * 
	 * It does so by attaching the children and parents of <code>another</code> and then
	 * unhooking it
	 * @param another
	 */
	public void takePlaceOf(LatticeNode another);
	
	
	public void setIntent(Set<Object> intent);
	
	public void setExtent(Set<Object> extent);
	
	public String getName();
	public void setName(String name);
	public List<NodeFeatureType> getTypes();
	public void setTypes(List<NodeFeatureType> types);
}
