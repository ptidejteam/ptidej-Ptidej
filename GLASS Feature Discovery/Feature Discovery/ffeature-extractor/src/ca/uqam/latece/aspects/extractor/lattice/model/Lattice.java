package ca.uqam.latece.aspects.extractor.lattice.model;

import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;

public interface Lattice {
	
	public LatticeNode getTop();
	
	public LatticeNode getBottom();
	
	public void setTop(LatticeNode top);
	
	public void setBottom(LatticeNode bottom);
	
	public void acceptTopVisitor(Visitor aVisitor);
	
	public void acceptBottomVisitor (Visitor aVisitor);

}
