package padl.kernel.impl;

import padl.kernel.IConstituent;
import padl.visitor.IPruningConditions;
import padl.visitor.ITraverser;
import padl.visitor.IVisitor;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;

public class BreadthFirstTraverser implements ITraverser {

    Queue<IConstituent> queue = new ArrayDeque<>();
    HashSet<IConstituent> visitedConstituents = new HashSet<>();

    public void traverse(final IVisitor visitor,
                         final Iterator iterator,
                         final IPruningConditions pruningConditions) {



        while (iterator.hasNext()) {

            final IConstituent constituent = (IConstituent) iterator.next();

            if (!visitedConstituents.contains(constituent)
                && (pruningConditions == null
                    || !pruningConditions.shouldBePruned(constituent))) {
                queue.add(constituent);
            }
        }

        while (!queue.isEmpty()) {

            final IConstituent constituent = queue.remove();

            try {
                System.out.println("Visiting: " + constituent.getDisplayName());
            }
            catch (Exception ex) {
                System.out.println("Visiting: " + constituent.hashCode());
            }

            constituent.accept(visitor);

            visitedConstituents.add(constituent);
        }
    }

    public void resetState() {
        queue = new ArrayDeque<>();
        visitedConstituents = new HashSet<>();
    }

}
