package glass.lattice.model.impl;

import glass.lattice.model.ILattice;
import glass.lattice.model.ILatticeNode;
import glass.lattice.visitor.IVisitor;

public class Lattice implements ILattice{
	
	private ILatticeNode top;
	private ILatticeNode bottom;

	@Override
	public ILatticeNode getTop() {
		return top;
	}

	@Override
	public ILatticeNode getBottom() {
		return bottom;
	}

	@Override
	public void setTop(ILatticeNode top) {
		this.top = top;
	}

	@Override
	public void setBottom(ILatticeNode bottom) {
		this.bottom = bottom;
	}

	@Override
	public void acceptTopVisitor(IVisitor aVisitor) {
		aVisitor.visitLatticeFromTop(this);
	}

	@Override
	public void acceptBottomVisitor(IVisitor aVisitor) {
		aVisitor.visitLatticeFromBottom(this);
	}

}
