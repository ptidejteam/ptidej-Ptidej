package padl.alternativeVisitor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import padl.kernel.IConstituent;
import padl.visitor.IVisitor;

public final class BfsIterator<T extends IVisitor> implements JavaAlternativelyPatterenedGenerator.ActionIterator<T> {
    private final Deque<Action> queue;
    public BfsIterator() {this.queue = new ArrayDeque<>();}
    public Optional<Action> getNext() {
        return this.queue.isEmpty()? Optional.empty(): Optional.of(this.queue.removeFirst());
    }
    public void enter(final IConstituent aConstituent, final boolean supportsOpenClose) {
        this.queue.addLast(new Action(
            supportsOpenClose? Action.Kind.Open: Action.Kind.Visit,
            aConstituent
        ));
    }
    public void leave(final IConstituent aConstituent, final boolean supportsOpenClose) {
        if (!supportsOpenClose)
            return;
        this.queue.addLast(new Action(Action.Kind.Close, aConstituent));
    }
}
