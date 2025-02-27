package glass.lattice.model;

import glass.lattice.visitor.IVisitor;

public interface ILattice {
	public ILatticeNode getTop();
	
	public ILatticeNode getBottom();
	
	public void setTop(ILatticeNode top);
	
	public void setBottom(ILatticeNode bottom);
	
	public void acceptTopVisitor(IVisitor aVisitor);
	
	public void acceptBottomVisitor (IVisitor aVisitor);
}
