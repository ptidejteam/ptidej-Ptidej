/**
 * Discards the Cached Path Part Builder Index stored in each enclosed element after e
 */
private void discardEnclosedElementsCachedPathPartBuilderIndex(final JCTElement e)
{
    final Iterator<Element> it = this.getEnclosedElements().iterator();
    while(it.hasNext() && e != it.next());
    while(it.hasNext())
    {
        final Element elem = it.next();
        if(null != elem && elem instanceof JCTElement)
            ((JCTElement)elem).discardCachedPathPartBuilderIndex();
    }
}

/**
 * Backpatch the (Enclosed) Elements field
 */
protected void backpatchElements(final Collection<Element> elements)
{
    try
    {
        final Field rootField = JCTElementContainer.class.getDeclaredField("elements");
        rootField.setAccessible(true);
        rootField.set(this, elements);
        rootField.setAccessible(false);
    }
    catch(final NoSuchFieldException ex)
    { throw new LinkageError(ex.toString()); }
    catch(final IllegalAccessException ex)
    { throw new LinkageError(ex.toString()); }
}

protected <E extends Element> List<E> createInternalList()
{ return this.new InternalList<E>(); }

protected <E extends Element> List<E> createInternalList(final Collection<? extends E> c)
{ return this.new InternalList<E>(c); }

protected class InternalList<E extends Element> extends ListOfUnique<E>
{
    private static final long serialVersionUID = -2225408481133433783L;

    protected InternalList()
    { }

    protected InternalList(final Collection<? extends E> c)
    { super(c); }

    @Override
        public void add(final int i, final E e)
    {
        final int old_index = this.indexOf(e);

        if(i == old_index)
            return;

        if(-1 == old_index)
            ((JCTElement)e).updateEnclosingElement(JCTElementContainer.this);
        else
        {
            final int max = i > old_index ? i : old_index;
            final ListIterator<E> it = this.listIterator(i < old_index ? i : old_index);
            while(it.hasNext() && it.nextIndex() <= max)
            {
                final E elem = it.next();
                if(elem instanceof JCTElement)
                    ((JCTElement)elem).discardCachedPathPartBuilderIndex();
            }
        }

        super.add(i, e);

        if(-1 == old_index)
            JCTElementContainer.this.discardEnclosedElementsCachedPathPartBuilderIndex((JCTElement)e);
    }

    @Override
        public E remove(final int i)
    {
        final JCTElement e = (JCTElement)this.get(i);
        JCTElementContainer.this.discardEnclosedElementsCachedPathPartBuilderIndex(e);
        e.updateEnclosingElement(null);
        return super.remove(i);
    }

    @Override
        public E set(final int i, final E e)
    {
        final E old = this.get(i);

        if(e != old)
        {
            ((JCTElement)old).updateEnclosingElement(null);
            ((JCTElement)e).updateEnclosingElement(JCTElementContainer.this);
            return super.set(i, e);
        }

        return e;
    }
}

protected <E extends Element> Set<E> createInternalSet()
{ return this.new InternalSet<E>(); }

protected <E extends Element> Set<E> createInternalSet(final Collection<? extends E> c)
{ return this.new InternalSet<E>(c); }

protected class InternalSet<E extends Element> extends AbstractSet<E> implements Serializable
{
    private static final long serialVersionUID = -1401830580503719064L;

    private final Set<E> set;

    private void preRemoveClean(final E e)
    {
        JCTElementContainer.this.discardEnclosedElementsCachedPathPartBuilderIndex((JCTElement)e);
        ((JCTElement)e).updateEnclosingElement(null);
    }

    protected InternalSet()
    { this.set = new HashSet<E>(); }

    protected InternalSet(final Collection<? extends E> c)
    { this.set = new HashSet<E>(c); }

    @Override
        public int size()
    { return this.set.size(); }

    @Override
        public Iterator<E> iterator()
    { return this.new InternalIterator(this.set.iterator()); }

    @Override
        public boolean add(final E e)
    {
        if(this.contains(e))
            return false;

        ((JCTElement)e).updateEnclosingElement(JCTElementContainer.this);
        return this.set.add(e);
    }

    public boolean remove(final E e)
    {
        if(!this.contains(e))
            return false;

        this.preRemoveClean(e);
        return this.set.remove(e);
    }

    public boolean contains(final E e)
    { return this.set.contains(e); }

    protected class InternalIterator implements Iterator<E>
    {
        private final Iterator<E> it;
        private E last;

        protected E getLast()
        { return this.last; }

        public InternalIterator(final Iterator<E> it)
        { this.it = it; }

        public boolean hasNext()
        { return this.it.hasNext(); }

        public E next()
        { return this.last = this.it.next(); }

        public void remove()
        {
            InternalSet.this.preRemoveClean(this.last);
            this.it.remove();
        }
    }
}

protected <E extends Element> NullableReference<E> createNullableInternalReference()
{ return this.new NullableInternalReference<E>(); }

protected <E extends Element> NullableReference<E> createNullableInternalReference(final Collection<? extends E> c)
{ return this.new NullableInternalReference<E>(c); }

protected <E extends Element> NullableReference<E> createNullableInternalReference(final E element)
{ return this.new NullableInternalReference<E>(element); }

protected class NullableInternalReference<E extends Element>
    extends StrongReference<E>
    implements NullableReference<E>
{
    private static final long serialVersionUID = -5438887130354559416L;

    protected NullableInternalReference()
    { }

    protected NullableInternalReference(final Collection<? extends E> c)
    { super(c); }

    protected NullableInternalReference(final E element)
    { super(element); }

    @Override
        public E set(final E e)
    {
        if(this.get() == e)
            return e;

        final E old = super.set(e);
        try
        {
            if(null != e)
                ((JCTElement)e).updateEnclosingElement(JCTElementContainer.this);

            if(null != old)
                ((JCTElement)old).updateEnclosingElement(null);

        } catch(final RuntimeException ex)
        {
            super.set(old);
            throw ex;
        }

        return old;
    }
}

protected <E extends Element> NotNullableReference<E> createInternalReference(final E element)
{ return this.new InternalReference<E>(element); }

protected class InternalReference<E extends Element>
    extends StrongReference<E>
    implements NotNullableReference<E>
{
    private static final long serialVersionUID = 1009922541030252910L;

    protected InternalReference(final E element)
    {
        super(element);
        ((JCTElement)this.get()).updateEnclosingElement(JCTElementContainer.this);
    }

    @Override
        public E set(final E e)
    {
        if(this.get() == e)
            return e;

        final E old = super.set(e);

        try
        {
            ((JCTElement)e).updateEnclosingElement(JCTElementContainer.this);
            ((JCTElement)old).updateEnclosingElement(null);
        } catch(final RuntimeException ex)
        {
            super.set(old);
            throw ex;
        }

        return super.set(e);
    }
}

private static final long serialVersionUID = -3260402134579560937L;
