package padl.alternativeVisitor;

import java.util.Optional;
import padl.kernel.IConstituent;
import padl.visitor.IVisitor;

/* Decorator to visit subtrees but skip some of them. */
public class SkipIterator<T extends IVisitor> implements JavaAlternativelyPatterenedGenerator.ActionIterator<T> {
    private final JavaAlternativelyPatterenedGenerator.ActionIterator<T> iter;
    public SkipIterator(JavaAlternativelyPatterenedGenerator.ActionIterator<T> iter) {this.iter = iter;}
    /* Returns whether the constituent should be traversed */
    protected boolean shouldKeep(final IConstituent aConstituent) {return true;}
    public Optional<Action> getNext() {
        for (;;) {
            final Optional<Action> action = this.iter.getNext();
            if (!action.isPresent() || this.shouldKeep(action.get().getConstituent())) return action;
        }
    }
    public void enter(final IConstituent aConstituent, final boolean supportsOpenClose) {this.iter.enter(aConstituent, supportsOpenClose);}
    public void leave(final IConstituent aConstituent, final boolean supportsOpenClose) {this.iter.leave(aConstituent, supportsOpenClose);}
}
