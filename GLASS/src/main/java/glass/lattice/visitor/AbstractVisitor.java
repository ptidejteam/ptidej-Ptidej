package glass.lattice.visitor;

import java.util.HashSet;
import java.util.Set;

import glass.lattice.model.ILattice;
import glass.lattice.model.ILatticeNode;

public abstract class AbstractVisitor implements IVisitor{
	/**
	 * a set that stores the visited nodes
	 */
	private Set<ILatticeNode> visitedNodes = new HashSet<ILatticeNode>();

	private Direction currentDirection = Direction.Undefined;

	@Override
	public void visitLatticeFromTop(ILattice aLattice) {
		// first set direction to TopDown
		currentDirection = Direction.TopDown;

		// get the top node, and start visiting it TopDown
		ILatticeNode top = aLattice.getTop();

		// visit the top, TopDown
		top.acceptVisitor(this, currentDirection);
	}

	@Override
	public void visitLatticeFromBottom(ILattice aLattice) {
		// first set direction to TopDown
		currentDirection = Direction.BottomUp;

		// get the bottom node, and start visiting it TopDown
		ILatticeNode bottom = aLattice.getBottom();

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
	public void visitLatticeNode(ILatticeNode latticeNode, Direction direction) {
		
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

				for (ILatticeNode child : latticeNode.getChildren()) {
					visitLatticeNode(child, direction);
				}
			}
			break;
		case BottomUp:
			// in this case, look for the parents of the lattice node, and visit
			// the ones that were not visited
			if (!latticeNode.getParents().isEmpty()) {

				preprocessParents(latticeNode);

				for (ILatticeNode parent : latticeNode.getParents()) {
					visitLatticeNode(parent, direction);
				}
			}
		default:
			System.out.println(" Houston! we have a problem: an alien visitor with no direction!");
			break;
		}

	}

	@Override
	public abstract void processNode(ILatticeNode node);

	/**
	 * hook method in case I wanted to insert a preprocessing ahead of visiting
	 * children. Subclasses my override. Didn't want to make it abstract to not
	 * force subclasses to have to supply an implementation since most of the
	 * time, there is nothing to put.
	 * 
	 * @param node
	 */
	public void preprocessChildren(ILatticeNode node) {
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
	public void preprocessParents(ILatticeNode node) {
		return;
	}

	/**
	 * the default implementation does nothing
	 */
	public void processVisitedNode(ILatticeNode node) {
		return;
	}

	/**
	 * the default implementation simply resets the direction and empties the visitedNodes set. Concrete
	 * subclasses may do something fancier
	 */
	public void reset() {
		visitedNodes = new HashSet<ILatticeNode>();
		currentDirection = Direction.Undefined;
	}
}
