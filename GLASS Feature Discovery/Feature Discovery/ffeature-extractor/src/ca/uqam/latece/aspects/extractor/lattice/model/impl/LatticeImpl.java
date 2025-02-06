package ca.uqam.latece.aspects.extractor.lattice.model.impl;

import ca.uqam.latece.aspects.extractor.lattice.model.Lattice;
import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;

public class LatticeImpl implements Lattice {
	
	private LatticeNode top;
	
	private LatticeNode bottom;

	@Override
	public LatticeNode getTop() {
		return top;
	}

	@Override
	public LatticeNode getBottom() {
		return bottom;
	}

	@Override
	public void setTop(LatticeNode top) {
		this.top = top;
	}

	@Override
	public void setBottom(LatticeNode bottom) {
		this.bottom = bottom;
	}

	@Override
	public void acceptTopVisitor(Visitor aVisitor) {
		aVisitor.visitLatticeFromTop(this);
	}

	@Override
	public void acceptBottomVisitor(Visitor aVisitor) {
		aVisitor.visitLatticeFromBottom(this);
	}

}
