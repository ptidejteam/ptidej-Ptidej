public JCTPathPartBuilder(final JCTKind aKind)
{ this.kind = aKind; }

public JCTPathPart createPathPart()
{ return this.kind == null ? null : new JCTPathPart(this.kind, this.index, this.data, this.informativeData); }

/**
 * Modifies the kind of this path part builder
 *
 * @param kind the new kind
 * @return {@code this}
 */
public JCTPathPartBuilder setKind(final JCTKind kind)
{
    this.kind = kind;
    return this;
}

/**
 * Returns the kind of this path part builder
 */
public JCTKind getKind()
{
    return this.kind;
}

/**
 * Modifies the index of this path part builder
 *
 * @param index the new index
 * @return {@code this}
 */
public JCTPathPartBuilder setIndex(final Integer index)
{
    this.index = index;
    return this;
}

/**
 * Returns the index of this path part builder
 */
public Integer getIndex()
{
    return this.index;
}

/**
 * Modifies the data of this path part builder
 *
 * @param data the new data
 * @return {@code this}
 */
public JCTPathPartBuilder setData(final String data)
{
    this.data = data;
    return this;
}

/**
 * Returns the data of this path part builder
 */
public String getData()
{
    return this.data;
}

/**
 * Modifies the informative data of this path part builder
 *
 * @param informativeData the new informative data
 * @return {@code this}
 */
public JCTPathPartBuilder setInformativeData(final byte[] informativeData)
{
    this.informativeData = informativeData;
    return this;
}

/**
 * Returns the informative data of this path part builder
 */
public byte[] getInformativeData()
{
    return this.informativeData;
}
