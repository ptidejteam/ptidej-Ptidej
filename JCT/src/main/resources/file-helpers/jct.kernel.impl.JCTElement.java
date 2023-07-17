/**
 * Cached path builder
 */
private transient SoftReference<JCTPathPartBuilder> cachedPathPartBuilder = new SoftReference<JCTPathPartBuilder>(null);

void updateEnclosingElement(final JCTElementContainer e)
{
    if(null == this.enclosingElement)
        ((JCTRootNode)this.getRootNode()).removeOrphan(this);
    else if(null != this.enclosingElement.elements && null != e)
        this.enclosingElement.elements.remove(this);

    if(e == null)
        ((JCTRootNode)this.getRootNode()).addOrphan(this);
    else
        if(!e.getRootNode().equals(this.getRootNode()))
            throw new IllegalArgumentException("An element's Root Node and its enclosing element's Root Node must be equals");

    this.enclosingElement = e;

    this.discardCachedPathPartBuilderIndex();
}

/**
 * Discards the cached path part builder index
 */
protected final void discardCachedPathPartBuilderIndex()
{
    if(null != this.cachedPathPartBuilder.get())
        this.cachedPathPartBuilder.get().setIndex(null);
}

/**
 * Returns the {@link jct.kernel.IJCTPath Path} to this element.
 */
public final JCTPath getPath()
{
    if(this instanceof IJCTRootNode)
        return new JCTPath();
    final JCTPath p = this.seePreviousPathStep().get(0).getPath();
    p.addPart(this.createPathPart().createPathPart());
    return p;
}

/**
 * Returns the {@link jct.kernel.impl.JCTPathPartBuilder} which will be
 * used to build the path to this element.
 * Any class overriding this method should also overrides
 * {@link #isDesignatedBy(String)} and obtains its JCTPathPartBuilder
 * by calling {@code super.createPathPart()}.
 */
protected JCTPathPartBuilder createPathPart()
{
    JCTPathPartBuilder p = this.cachedPathPartBuilder.get();

    if(null == p)
    {
        p = new JCTPathPartBuilder(this.getKind());
        this.cachedPathPartBuilder = new SoftReference<JCTPathPartBuilder>(p);
    }

    if(null == p.getIndex())
    {
        int i = 0;
        final Iterator<?> it = this.seePreviousPathStep().get(0).seeNextPathStep(this.getKind()).iterator();
        while(it.hasNext())
            if(this == it.next())
            {
                p.setIndex(i);
                break;
            }
            else
                ++i;

        if(null == p.getIndex())
            throw new AssertionError("lists: for(List<JCTElementContainer> l : this.seePreviousPathStep())\n"
                                     + "{\n"
                                     + "    for(IJCTElement e : l.seeNextPathStep(this.getKind))\n"
                                     + "        if(this == e)\n"
                                     + "            continue lists;\n"
                                     + "    return false;\n"
                                     + "}\n"
                                     + "return true;\n"
                                     + "must always return true. \n\n"
                                     + "This is not valid for :\n"
                                     + "- enclosing n°0 : " + this.seePreviousPathStep().get(0).getClass() + "\n"
                                     + "- enclosed : " + this.getClass() + "/" + this.getName() + "\n"
                                     + "- enclosed Kind : " + this.getKind() + "\n"
                                     + "- enclosed Name : " + this.getName());
    }

    return p.setData(this.getName());
}

/**
 * Returns the element desginated by {@code this.getPath().getPathToEnclosingElement()}.
 * {@code lists: for(List<IJCTElement> l : this.seePreviousPathStep()) { for(IJCTElement e :
 * l.seeNextPathStep(this.getKind)) if(this == e) continue lists; return false; {@literal }}
 * return true; }
 * must always return true.
 */
protected List<JCTElementContainer<?>> seePreviousPathStep()
{
    return new LinkedList<JCTElementContainer<?>>()
    {{ this.add((JCTElementContainer<?>)(null == JCTElement.this.enclosingElement
                                         ? JCTElement.this.getRootNode()
                                         : JCTElement.this.enclosingElement)); }};
}

/**
 * Determines whether the {@link jct.kernel.impl.JCTPathPart Path} designates
 * this element or not.
 *
 * Any class overriding this method should also override
 * {@link #createPathPart()} and call {@code super.isDesignatedBy}.
 */
protected boolean isDesignatedBy(final String designator)
{ return designator == null || designator.equals(this.createPathPart().getData()); }

public String toString()
{
    try
    {
        return this.getPath().toString();
    } catch(final Throwable t)
    {
        t.printStackTrace();
        return this.getClass() + " : " + this.getName();
    }
}

private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
{
    in.defaultReadObject();
    this.cachedPathPartBuilder = new SoftReference<JCTPathPartBuilder>(null);
}

private static final long serialVersionUID = 135324744073668097L;

to implement :: , java.io.Serializable
