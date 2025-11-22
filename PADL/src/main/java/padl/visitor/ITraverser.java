package padl.visitor;

import java.util.Iterator;

public interface ITraverser {

    void traverse(final IVisitor visitor,
                  final Iterator iterator,
                  final IPruningConditions pruningConditions);

    void resetState();
}
