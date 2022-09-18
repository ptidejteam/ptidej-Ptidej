protected JCTPathPart(final JCTKind resultKind)
{ this(resultKind, null); }

public JCTPathPart(final JCTKind resultKind, final Integer index)
{ this(resultKind, index, null); }

public JCTPathPart(final JCTKind resultKind, final Integer index, final String data)
{ this(resultKind, index, data, null); }

public JCTPathPart(final JCTKind resultKind, final Integer index, final String data, final byte[] informativeData)
{
    this.resultKind = resultKind;
    this.index = index;
    this.data = "null".equals(data) ? null : data;
    this.informativeData = informativeData;
}

/**
 * Returns the nextPart Part of the Path
 */
public JCTPathPart getNextPart()
{ return this.nextPart; }

public JCTPathPart getLastPart()
{
    if(null == this.lastPart)
        if(null != this.nextPart)
            return this.lastPart = this.nextPart.getLastPart();
        else
            return this.lastPart = this;

    if(null != this.lastPart.getNextPart())
        this.lastPart = this.lastPart.getLastPart();

    return this.lastPart;
}

/**
 * Returns path part leading to the element enclosing the one this path leads to
 */
public JCTPathPart getPathPartToEnclosing()
{
    if(null == this.nextPart)
        return null;

    final JCTPathPart p = new JCTPathPart(this.resultKind, this.index, this.data);
    p.addPart(this.nextPart.getPathPartToEnclosing());
    return p;
}

public void addPart(final IJCTPathPart p)
{
    if(null == p)
        return;

    final JCTPathPart part;

    if(p instanceof JCTPathPart)
        part = (JCTPathPart)p;
    else
    {
        part = new JCTPathPart(p.getResultKind(), p.getIndex(), p.getData(), p.getInformativeData());
        IJCTPathPart it = p.getNextPart();
        JCTPathPart to = part;
        while(null != it)
        {
            final JCTPathPart toAdd = new JCTPathPart(it.getResultKind(), it.getIndex(), it.getData(), it.getInformativeData());
            to.addPart(toAdd);
            to = toAdd;
            it = it.getNextPart();
        }
    }

    if(null != this.nextPart)
        this.getLastPart().addPart(part);
    else
        this.nextPart = part;

    this.lastPart = part.getLastPart();
}

/**
 * Returns the element designated by this path part
 */
public IJCTElement walk(final IJCTElementContainer<? extends IJCTElement> e)
{  return this.walk(e, e == null || e.getRootNode().isInitialized()); }

protected IJCTElement walk(final IJCTElementContainer<? extends IJCTElement> e, final boolean displayError)
{
    if(null == e)
    {
        if(displayError)
            System.err.println("NoSuchElement :: " + this + " / " + e);
        return null;
    }

    final IJCTElement r = this.resolve(e);

    return null == this.nextPart ? r : this.nextPart.walk((IJCTElementContainer)r, displayError);
}

protected IJCTElement resolve(final IJCTElementContainer<? extends IJCTElement> ee)
{
    final Collection<IJCTElement> ec = ee instanceof JCTElementContainer
        ? ((JCTElementContainer<IJCTElement>)ee).seeNextPathStep(this.resultKind)
        : ((IJCTContainer<IJCTElement>)ee).getEnclosedElements();

    IJCTElement e = null;
    if(null != this.index)
    {
        final Iterator<IJCTElement> it = ec.iterator();
        for(int i = 0; it.hasNext() && i < this.index; ++i)
            it.next();
        if(it.hasNext())
            e = it.next();
        else
            return null;
    }

    if(e == null
       || (e instanceof JCTElement
           && !((JCTElement)e).isDesignatedBy(this.data)))
        for(final IJCTElement el : ec)
            if(el instanceof JCTElement
               && ((JCTElement)el).isDesignatedBy(this.data))
            {
                e = el;
                break;
            }

    return e;
}

public IJCTElement walk(final IJCTRootNode aRootNode)
{ return this.walk((IJCTElementContainer<IJCTPackage>)aRootNode); }

public final static String KIND_INDEX_SEPARATOR = "::";
public final static String INDEX_DATA_SEPARATOR = ";";
public final static String PART_SEPARATOR = "/";

public String toString()
{
    final StringBuffer result = new StringBuffer()
        .append(JCTPathPart.PART_SEPARATOR)
        .append(this.resultKind.toString())
        .append(JCTPathPart.KIND_INDEX_SEPARATOR)
        .append(null == this.index ? null : this.index.toString())
        .append(JCTPathPart.INDEX_DATA_SEPARATOR)
        .append(this.data)
        .append(JCTPathPart.INDEX_DATA_SEPARATOR);

    if (null == this.informativeData)
        result.append("null");
    else
        for (final byte b : this.informativeData)
            result.append(String.format("%02X", b));

    if (null != this.nextPart)
        result.append(this.nextPart.toString());

    return result.toString();
}

public JCTPathPart clone()
{
    try
    {
        final JCTPathPart that = (JCTPathPart)super.clone();
        that.nextPart = that.nextPart.clone();
        that.lastPart = null;
        return that;
    }
    catch(final CloneNotSupportedException ex)
    { throw new IllegalStateException(ex); }
}

public boolean equals(final Object that)
{
    if(super.equals(that))
        return true;

    if(!(that instanceof IJCTPathPart))
        return false;

    final IJCTPathPart part = (IJCTPathPart)that;

    return this.getResultKind() == part.getResultKind()
        && (this.getIndex() == null ? part.getIndex() == null : this.getIndex().equals(part.getIndex()))
        && (this.getData() == null ? part.getData() == null : this.getData().equals(part.getData()))
        && (this.getInformativeData() == null ? part.getInformativeData() == null : Arrays.equals(this.getInformativeData(), part.getInformativeData()));
}

private static final long serialVersionUID = 8293905259074720443L;
