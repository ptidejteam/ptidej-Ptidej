package padl.visitor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;

/**
 * The BreadthFirstModelTraverser class is a breadth-first traversal for the self-traversing Visitor variant.
 * All the constituents at one depth are processed before any constituent at the next depth.
 * 
 * The accept() methods cannot do this since they hardcode the depth-first order.
 * The dispatch is inherited unchanged from ModelTraverser, so only the traversal changes here.
 *
 * The children of a container are processed later, so a container is opened and closed immediately.
 * The open() and close() calls are balanced but not nested.
 * A visitor that relies on nesting to track its context needs the depth-first ModelTraverser instead.
 *
 * @author Vinisha Manek
 * @since 2026/11/11
 */
public class BreadthFirstModelTraverser extends ModelTraverser {
	public BreadthFirstModelTraverser(final IVisitor aVisitor) {
		super(aVisitor);
	}

	public void traverse(final IAbstractModel anAbstractModel) {
		this.visitor.open(anAbstractModel);

		final Deque queue = new ArrayDeque();
		this.enqueueConstituentsOf(anAbstractModel, queue);
		while (!queue.isEmpty()) {
			final IConstituent constituent = (IConstituent) queue.removeFirst();
			if (this.isContainer(constituent)) {
				this.openContainer(constituent);
				this.closeContainer(constituent);
				this.enqueueConstituentsOf((IContainer) constituent, queue);
			}
			else {
				this.visitLeaf(constituent);
			}
		}

		this.visitor.close(anAbstractModel);
	}

	private void enqueueConstituentsOf(
		final IContainer aContainer,
		final Deque aQueue) {

		final Iterator iterator =
			aContainer.getConcurrentIteratorOnConstituents();
		while (iterator.hasNext()) {
			aQueue.addLast(iterator.next());
		}
	}
}
