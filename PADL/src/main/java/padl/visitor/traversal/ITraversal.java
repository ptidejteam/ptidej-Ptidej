package padl.visitor.traversal;

import padl.kernel.IAbstractModel;
import padl.visitor.IGenerator;
import padl.visitor.IVisitor;
import padl.visitor.IWalker;

/**
 * A traversing algorithm over a PADL model, separate from the visitor it drives and from the
 * traversing hard-coded in the accept() methods of the constituents. Any existing IVisitor,
 * IGenerator, or IWalker can be passed to any ITraversal without being modified. A visitor that
 * relies on nested open() and close() calls, such as JavaGenerator, still needs a traversal that
 * nests them, such as DepthFirstTraversal.
 *
 * @author Chen Qian
 */
public interface ITraversal {
  void traverse(final IAbstractModel anAbstractModel, final IVisitor aVisitor);

  /** Counterpart of IAbstractModel.generate(IGenerator). */
  default String generate(final IAbstractModel anAbstractModel, final IGenerator aGenerator) {
    this.traverse(anAbstractModel, aGenerator);
    return aGenerator.getCode();
  }

  /** Counterpart of IAbstractModel.walk(IWalker). */
  default Object walk(final IAbstractModel anAbstractModel, final IWalker aWalker) {
    this.traverse(anAbstractModel, aWalker);
    return aWalker.getResult();
  }
}
