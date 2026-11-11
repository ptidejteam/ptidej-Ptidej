package padl.visitor.traversal;

import java.util.ArrayDeque;
import java.util.Deque;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.visitor.IFilter;
import padl.visitor.IVisitor;

/**
 * Breadth-first traversal: all the constituents at one depth are traversed before any constituent
 * at the next depth. Because the children of a container are traversed later, a container is
 * opened and closed immediately, so open() and close() calls are balanced but never nested. Only
 * the model is opened before and closed after all its constituents.
 *
 * <p>Visitors that rely on nested open() and close() calls, such as JavaGenerator, need
 * DepthFirstTraversal instead.
 *
 * <p>Unlike BFVisitor, which records the depth-first callbacks of accept() and replays only the
 * class callbacks level by level, this traversal reads the levels from the model directly and
 * dispatches every constituent type.
 *
 * @author Chen Qian
 */
public class BreadthFirstTraversal extends AbstractTraversal {
  public BreadthFirstTraversal() {
    super();
  }

  public BreadthFirstTraversal(final IFilter aFilter) {
    super(aFilter);
  }

  public void traverse(final IAbstractModel anAbstractModel, final IVisitor aVisitor) {
    aVisitor.open(anAbstractModel);

    final Deque<IConstituent> queue = new ArrayDeque<>(this.getChildren(anAbstractModel));
    while (!queue.isEmpty()) {
      final IConstituent constituent = queue.removeFirst();
      if (ConstituentDispatcher.isContainer(constituent)) {
        ConstituentDispatcher.open(constituent, aVisitor);
        ConstituentDispatcher.close(constituent, aVisitor);
        queue.addAll(this.getChildren((IContainer) constituent));
      } else {
        ConstituentDispatcher.visit(constituent, aVisitor);
      }
    }

    aVisitor.close(anAbstractModel);
  }
}
