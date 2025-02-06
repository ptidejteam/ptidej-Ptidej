package ca.concordia.ptidej.prototype.lattice.model;

import ca.concordia.ptidej.prototype.lattice.visitor.IVisitor;

public interface ILattice {
	public ILatticeNode getTop();
	
	public ILatticeNode getBottom();
	
	public void setTop(ILatticeNode top);
	
	public void setBottom(ILatticeNode bottom);
	
	public void acceptTopVisitor(IVisitor aVisitor);
	
	public void acceptBottomVisitor (IVisitor aVisitor);
}
