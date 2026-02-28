package padl.visitor;

import padl.kernel.IConstituent;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;

public class FilteringVisitor extends EmptyVisitor implements IGenerator{

    private final IGenerator delegate;
    private final IFilter filter;

    public FilteringVisitor(IGenerator delegate, IFilter filter) {
        this.delegate = delegate;
        this.filter = filter;
    }

    private boolean allowed(Object o) {
        if(o instanceof IConstituent && filter != null) {
            return filter.accept((IConstituent) o);
        }

        return true;
    }

    @Override
    public String getCode() {
        return delegate.getCode();
    }

    @Override
    public void reset() {
        delegate.reset();
    }

    @Override
    public void open(IAbstractModel anAbstractModel) {
        if(allowed(anAbstractModel)) {
            delegate.open(anAbstractModel);
        }
    }

    @Override
    public void close(IAbstractModel anAbstractModel) {
        if(allowed(anAbstractModel)) {
            delegate.close(anAbstractModel);
        }
    }

    @Override
    public void open(IClass aClass) {
        if(allowed(aClass)) {
            delegate.open(aClass);
        }
    }

    @Override
    public void close(IClass aClass) {
        if(allowed(aClass)) {
            delegate.close(aClass);
        }
    }
}
