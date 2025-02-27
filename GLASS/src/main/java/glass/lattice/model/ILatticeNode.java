package glass.lattice.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import glass.lattice.model.impl.NodeFeatureType;
import glass.lattice.visitor.IVisitor;
import glass.lattice.visitor.IVisitor.Direction;

public interface ILatticeNode {
	public Set<Object> getExtent();
	
	public void addToExtent(Object anObject);
	
	public void addCollectionToExtent(Collection<Object> objects);
	
	public void addCollectionToIntent(Collection<Object> properties);
	
	public void removeFromExtent(Object anObject);
	
	public void removeFromIntent(Object anObject);
	
	public void addToIntent(Object anObject);
	
	public Set<Object> getIntent();
	
	public Set<ILatticeNode> getParents();
	
	public Set<ILatticeNode> getChildren();
	
	public void addChild(ILatticeNode childNode);
	
	public void addParent(ILatticeNode parentNode);
	
	public void removeChild(ILatticeNode childNode);
	
	public boolean hasParent(ILatticeNode parentNode);
	
	public boolean hasChild(ILatticeNode childNode);
	
	public void removeParent(ILatticeNode parentNode);
	
	public void acceptVisitor(IVisitor aVisitor, Direction direction);
		
	/**
	 * returns a new LatticeNode that has same extent and same intent, BUT
	 * 1) does not copy children
	 * 2) does not copy parents
	 * 3) has visited status to false 
	 * @return
	 */
	public ILatticeNode copy();
	
	/**
	 * this method makes receiver take place of <code>another</code>
	 * in the lattice that contains <code>another</code>.
	 * 
	 * It does so by attaching the children and parents of <code>another</code> and then
	 * unhooking it
	 * @param another
	 */
	public void takePlaceOf(ILatticeNode another);
	
	
	public void setIntent(Set<Object> intent);
	
	public void setExtent(Set<Object> extent);
	
	public String getName();
	public void setName(String name);
	public List<NodeFeatureType> getTypes();
	public void setTypes(List<NodeFeatureType> types);
}
