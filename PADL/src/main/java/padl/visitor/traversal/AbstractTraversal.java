package padl.visitor.traversal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.visitor.IFilter;

/**
 * Common navigation of the traversals: which children of a container are traversed. A constituent
 * rejected by the filter is skipped together with its whole subtree: it receives no callback, and
 * the filter is never consulted for its descendants.
 *
 * @author Chen Qian
 */
public abstract class AbstractTraversal implements ITraversal {
  private final IFilter filter;

  protected AbstractTraversal() {
    this(aConstituent -> true);
  }

  protected AbstractTraversal(final IFilter aFilter) {
    if (aFilter == null) {
      throw new IllegalArgumentException("The filter of a traversal cannot be null");
    }
    this.filter = aFilter;
  }

  /**
   * Reads a snapshot of the children through the concurrent iterator, the iterator that
   * IAbstractModel.walk() uses at the model level. A walker may therefore modify a container while
   * it is being traversed, but a constituent added after the children of its container have been
   * read is not visited by this traversal. The snapshot keeps the current order of the container
   * and does not sort it.
   */
  protected List<IConstituent> getChildren(final IContainer aContainer) {
    final List<IConstituent> children = new ArrayList<>();
    final Iterator iterator = aContainer.getConcurrentIteratorOnConstituents();
    while (iterator.hasNext()) {
      final IConstituent child = (IConstituent) iterator.next();
      if (this.filter.accept(child)) {
        children.add(child);
      }
    }
    return children;
  }
}
