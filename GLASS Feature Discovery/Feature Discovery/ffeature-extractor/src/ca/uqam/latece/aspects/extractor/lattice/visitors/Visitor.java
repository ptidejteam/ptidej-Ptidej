package ca.uqam.latece.aspects.extractor.lattice.visitors;

import ca.uqam.latece.aspects.extractor.lattice.model.Lattice;
import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;

public interface Visitor {
	
	public enum Direction {Undefined,TopDown, BottomUp};
	
	/**
	 * this method will start with the top node, and then recursively descends down visiting the children,
	 * until it reaches the bottom. Implementations will call visitLatticeNode(LatticeNode node)
	 * and visitChildren(LatticeNode node)
	 * @param aLattice
	 */
	public void visitLatticeFromTop(Lattice aLattice);
	
	/**
	 * this method will start with the bottom node, and then recursively goes up visiting the parents,
	 * until it reaches the top. Implementations will call visitLatticeNode(LatticeNode node)
	 * and visitParent(LatticeNode node)
	 * @param aLattice
	 */
	public void visitLatticeFromBottom(Lattice aLattice);
	
	/**
	 * returns the direction of the current visit. If current visit started with visitLatticeFromTop,
	 * then the direction is TopDown. If the current visit started with visitLatticeFromBottom, then the
	 * result is BottomUp. Else, it returns Undefined.
	 * Watch out. after a visitor has visited a Lattice, it will conserve its direction for the next call
	 * unless visitLatticeFromTop or visitLatticeFromBottom is called again.
	 * @return
	 */
	public Direction getCurrentVisitDirection();
	
	/**
	 * does whatever needs to be done on the node (calls processNode), sets visited to true,
	 * then, depending on direction, either visits children or parents.
	 * 
	 * This method will be called from either visitLatticeFromTop or visitLatticeFromBottom
	 * 
	 * @param latticeNode
	 * @param direction
	 */
	public void visitLatticeNode(LatticeNode latticeNode, Direction direction);
	
	/**
	 * this method processes the current node
	 * @param node
	 */
	public void processNode(LatticeNode node);
	
	
	/**
	 * this method processes visited nodes. Normally, visited nodes are not processed. But in some
	 * case, we may want to process them but differently from the first time we came across them.
	 * For example, if we are traversing a graph, the first time we come across the node, we print its contents.
	 * The second time we "hit it", we simply put a reference to it
	 */
	public void processVisitedNode(LatticeNode node);
	
	/**
	 * a method used to reset a visitor between visits. If the visitor collects/maintains data during its visits, this is 
	 * the opportunity to clean that data to prepare it for other visits.
	 */
	public void reset() ;
}
