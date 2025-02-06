package ca.uqam.latece.aspects.extractor.lattice.visitors.impl;

import java.util.HashSet;
import java.util.Set;

import ca.uqam.latece.aspects.extractor.lattice.model.Lattice;
import ca.uqam.latece.aspects.extractor.lattice.model.LatticeNode;
import ca.uqam.latece.aspects.extractor.lattice.visitors.Visitor;

/**
 * the abstract visitor for lattices. It maintains locally the visited status of the lattice nodes because I could
 * have, at any given point in time, different visitors doing different things
 * @author Hafedh
 *
 */
public abstract class AbstractVisitor implements Visitor {
	
	/**
	 * a set that stores the visited nodes
	 */
	private Set<LatticeNode> visitedNodes = new HashSet<LatticeNode>();

	private Direction currentDirection = Direction.Undefined;

	@Override
	public void visitLatticeFromTop(Lattice aLattice) {
		// first set direction to TopDown
		currentDirection = Direction.TopDown;

		// get the top node, and start visiting it TopDown
		LatticeNode top = aLattice.getTop();

		// visit the top, TopDown
		top.acceptVisitor(this, currentDirection);
	}

	@Override
	public void visitLatticeFromBottom(Lattice aLattice) {
		// first set direction to TopDown
		currentDirection = Direction.BottomUp;

		// get the bottom node, and start visiting it TopDown
		LatticeNode bottom = aLattice.getBottom();

		// visit the top, TopDown
		bottom.acceptVisitor(this, currentDirection);
	}

	@Override
	public Direction getCurrentVisitDirection() {
		return currentDirection;
	}

	/**
	 * the default behaviour does not refrain from visiting when a node has been visisted
	 */
	@Override
	public void visitLatticeNode(LatticeNode latticeNode, Direction direction) {
		
		// first check if node has been visited, in which case
		// call processVisitedNodes and return
		if (visitedNodes.contains(latticeNode)){
			processVisitedNode(latticeNode);
			return;
		}
		
		// ELSE
		// first process node
		processNode(latticeNode);

		// set the node to visited
		visitedNodes.add(latticeNode);

		switch (direction) {
		case TopDown:
			// in this case, look for the children of the lattice node, and
			// visit them
			if (!latticeNode.getChildren().isEmpty()) {

				preprocessChildren(latticeNode);

				for (LatticeNode child : latticeNode.getChildren()) {
					visitLatticeNode(child, direction);
				}
			}
			break;
		case BottomUp:
			// in this case, look for the parents of the lattice node, and visit
			// the ones that were not visited
			if (!latticeNode.getParents().isEmpty()) {

				preprocessParents(latticeNode);

				for (LatticeNode parent : latticeNode.getParents()) {
					visitLatticeNode(parent, direction);
				}
			}
		default:
			System.out.println(" Houston! we have a problem: an alien visitor with no direction!");
			break;
		}

	}

	@Override
	public abstract void processNode(LatticeNode node);

	/**
	 * hook method in case I wanted to insert a preprocessing ahead of visiting
	 * children. Subclasses my override. Didn't want to make it abstract to not
	 * force subclasses to have to supply an implementation since most of the
	 * time, there is nothing to put.
	 * 
	 * @param node
	 */
	public void preprocessChildren(LatticeNode node) {
		return;
	}

	/**
	 * hook method in case I wanted to insert a preprocessing ahead of visiting
	 * parents. Subclasses may override. Didn't want to make it abstract to not
	 * force subclasses to have to supply an implementation since most of the
	 * time, there is nothing to put.
	 * 
	 * @param node
	 */
	public void preprocessParents(LatticeNode node) {
		return;
	}

	/**
	 * the default implementation does nothing
	 */
	public void processVisitedNode(LatticeNode node) {
		return;
	}

	/**
	 * the default implementation simply resets the direction and empties the visitedNodes set. Concrete
	 * subclasses may do something fancier
	 */
	public void reset() {
		visitedNodes = new HashSet<LatticeNode>();
		currentDirection = Direction.Undefined;
	}
}
