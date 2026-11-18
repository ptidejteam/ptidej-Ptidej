package padl.alternativeVisitor;

import java.util.Optional;
import java.util.Stack;
import padl.kernel.IConstituent;
import padl.visitor.IVisitor;

public final class DfsIterator<T extends IVisitor> implements JavaAlternativelyPatterenedGenerator.ActionIterator<T> {
    private final Stack<Action> stack;
    public DfsIterator() {this.stack = new Stack<>();}
    public Optional<Action> getNext() {
        return this.stack.isEmpty()? Optional.empty(): Optional.of(this.stack.pop());
    }
    public void enter(final IConstituent aConstituent, final boolean supportsOpenClose) {
        this.stack.push(new Action(
            supportsOpenClose? Action.Kind.Close: Action.Kind.Visit,
            aConstituent
        ));
    }
    /* Reverse order, so that the first child is on top of the stack */
    public void addChildren(final java.util.List<IConstituent> children, final java.util.function.Predicate<IConstituent> supportsOpenClose) {
        for (int i = children.size() - 1; i >= 0; i--) {
            this.enter(children.get(i), supportsOpenClose.test(children.get(i)));
            this.leave(children.get(i), supportsOpenClose.test(children.get(i)));
        }
    }
    public void leave(final IConstituent aConstituent, final boolean supportsOpenClose) {
        if (!supportsOpenClose)
            return;
        this.stack.push(new Action(Action.Kind.Open, aConstituent));
    }
}
