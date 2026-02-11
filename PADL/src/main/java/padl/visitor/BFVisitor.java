package padl.visitor;

import padl.kernel.IAbstractModel;
import padl.kernel.IContainer;
import padl.kernel.IConstituent;
import padl.kernel.IClass;

import java.util.*;

public class BFVisitor extends EmptyVisitor implements IGenerator{

    private final IGenerator delegate;
    private final List<IConstituent> roots = new ArrayList<>();
    private final Map<IContainer, List<IConstituent>> children = new HashMap<>();
    private final Deque<IConstituent> parentStack = new ArrayDeque<>();

    public BFVisitor(IGenerator delegate) {
        this.delegate = delegate;
    }

    @Override
    public void reset() {
        delegate.reset();
        roots.clear();
        children.clear();
        parentStack.clear();
    }

    @Override
    public String getCode() {
        return delegate.getCode();
    }

    @Override
    public void open(IAbstractModel anAbstractModel) {
        roots.clear();
        children.clear();
        parentStack.clear();
    }

    @Override
    public void close(IAbstractModel anAbstractModel) {
        replayLevel(new ArrayList<>(roots));

        roots.clear();
        children.clear();
        parentStack.clear();
    }

    @Override
    public void open(IClass iClass) {
        record(iClass);
        parentStack.push(iClass);
    }

    @Override
    public void close(IClass iClass) {
        if(!parentStack.isEmpty() && parentStack.peek() == iClass) {
            parentStack.pop();
        }
    }

    private void record(IConstituent c) {
        if(parentStack.isEmpty()) {
            roots.add(c);
        } else {
            IConstituent parent = parentStack.peek();
            if(parent instanceof IContainer container) {
                children.computeIfAbsent(container, k-> new ArrayList<>()).add(c);
            } else {
                roots.add(c);
            }
        }
    }

    private void replayLevel(List<IConstituent> level) {
        if(level == null || level.isEmpty()) {
            return;
        }

        List<IConstituent> nextLevel = new ArrayList<>();

        for(IConstituent c: level) {
            if(c instanceof IClass iClass) {
                delegate.open(iClass);
                delegate.close(iClass);
            } else {
                delegate.unknownConstituentHandler("visit", c);
            }

            if(c instanceof IContainer container) {
                List<IConstituent> child = children.get(container);
                if(child != null && !child.isEmpty()) {
                    nextLevel.addAll(child);
                }
            }
        }

        replayLevel(nextLevel);
    }
}
