package padl.kernel.impl;

import padl.kernel.IConstituent;
import padl.visitor.IPruningConditions;
import padl.visitor.ITraverser;
import padl.visitor.IVisitor;

import java.util.Iterator;

public class DepthFirstTraverser implements ITraverser {

    public void traverse(final IVisitor visitor,
                         final Iterator iterator,
                         final IPruningConditions pruningConditions) {

        while (iterator.hasNext()) {
            final IConstituent constituent = (IConstituent) iterator.next();

            if (!pruningConditions.shouldBePruned(constituent)) {

                try {
                    System.out.println("Visiting: " + constituent.getDisplayName());
                }
                catch (Exception ex) {
                    System.out.println("Visiting: " + constituent.hashCode());
                }

                constituent.accept(visitor);
            }
        }
    }

    public void resetState() {
        // nothing to do
    }

}
