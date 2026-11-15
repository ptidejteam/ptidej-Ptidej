package padl.visitor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.util.Iterator;
import java.util.Set;

import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.kernel.IPackage;

public class OwnTraversingVisitor extends EmptyVisitor implements IGenerator {
    private final IGenerator delegate;

    // Caching the set of completed subtree
    private final Set<IConstituent> completed =
            Collections.newSetFromMap(new HashMap<>());

    // Inspired by Constituent.cachedCorrespondingInterfaceNames.
    // Caching for Map<String, Method> for performance, if there is no Method with the string, a null entry is entered into methodCache
    private final Map<String, Method> methodCache = new HashMap<>();

    public OwnTraversingVisitor(IGenerator delegate) {
        this.delegate = delegate;
    }

    private void traverseAll(final Iterator anIterator) {
        while (anIterator.hasNext()) {
            traverse((IConstituent) anIterator.next());
        }
    }

    private Method findVisitorMethod(final String aName, final Class<?> aType) {
        final String key = aName + '(' + aType.getName() + ')';
        if (methodCache.containsKey(key)) {
            return methodCache.get(key);
        }
        Method method;
        try {
            method = IVisitor.class.getMethod(aName, aType);
        }
        catch (final NoSuchMethodException e) {
            method = null;
        }
        methodCache.put(key, method);
        return method;
    }

    private void call(final Method aMethod, final IConstituent aConstituent) {
        try {
            aMethod.invoke(this.delegate, aConstituent);
        }
        catch (final InvocationTargetException e) {
            // re-throw exception from invoke
            final Throwable cause = e.getCause();
            if (cause instanceof RuntimeException re) {
                throw re;
            }
            throw new RuntimeException(cause);
        }
        catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private void traverse(final IConstituent c) {
        if (!allowed(c) || completed.contains(c)) {
            return;
        }
        for (Class<?> k = c.getClass(); k != null; k = k.getSuperclass()) {
            for (final Class<?> i : k.getInterfaces()) {
                final Method open = findVisitorMethod("open", i);
                final Method close = findVisitorMethod("close", i);
                if (open != null && close != null) {
                    call(open, c);
                    if (c instanceof IContainer container) {
                        traverseChildrenOf(container);
                    }
                    call(close, c);
                    completed.add(c);
                    return;
                }
                final Method visit = findVisitorMethod("visit", i);
                if (visit != null) {
                    call(visit, c);
                    completed.add(c);
                    return;
                }
            }
        }

        this.delegate.unknownConstituentHandler("visit", c);
        this.completed.add(c);
    }

    private void traverseChildrenOf(final IContainer c) {
        if (c instanceof IPackage) {
            traverseAll(c.getIteratorOnConstituents());
        }
        else {
            traverseAll(c.getConcurrentIteratorOnConstituents());
        }
    }

    private boolean allowed(Object o) {
        return (o instanceof IConstituent);
    }

    @Override
    public String getCode() {
        return delegate.getCode();
    }

    @Override
    public void reset() {
        delegate.reset();
        completed.clear();
    }

    @Override
    public void open(IAbstractModel anAbstractModel) {
        completed.clear();
        delegate.open(anAbstractModel);
        traverseAll(anAbstractModel.getIteratorOnConstituents());
    }

    @Override
    public void close(IAbstractModel anAbstractModel) {
        delegate.close(anAbstractModel);
    }
}