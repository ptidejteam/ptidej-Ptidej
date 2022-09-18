public JCTKind getResultKind()
{ throw new UnsupportedOperationException("A File Offset Path can not know the kind of the element it designs."); }

public String getData()
{ throw new UnsupportedOperationException("A File Offset Path can not know the data of the element it designs."); }

public Integer getIndex()
{ throw new UnsupportedOperationException("A File Offset Path can not know the index of the element it designs."); }

public IJCTSourceCodePart walk(final IJCTRootNode aRootNode)
{
    IJCTCompilationUnit cu = null;
    for(final IJCTCompilationUnit it : ((IJCTContainer<?>)aRootNode).getAllEnclosedElements(JCTKind.COMPILATION_UNIT, IJCTCompilationUnit.class, true))
        if(it.getSourceFile().equals(this.file))
        {
            cu = it;
            break;
        }

    if(null == cu
       || null == cu.getStoredSourceCodeLength())
        return null;

    if(null == this.getOffset()
       || 0 == this.getOffset())
        return cu;

    return this.walkDownThePath(cu, cu);
}

private IJCTSourceCodePart walkDownThePath(final IJCTSourceCodePart aSourceCodePart, final IJCTCompilationUnit topLevel)
{
    if(this.getOffset().equals(aSourceCodePart.getStoredSourceCodeOffset(topLevel)))
        return aSourceCodePart;

    if(aSourceCodePart instanceof IJCTContainer)
        for(final IJCTSourceCodePart scp : ((IJCTContainer<IJCTSourceCodePart>)aSourceCodePart).getEnclosedElements())
        {
            if(null == scp)
                continue;
            final Integer OabsoluteOffset = scp.getStoredSourceCodeOffset(topLevel);
            if(null == OabsoluteOffset)
                continue;
            final int absoluteOffset = OabsoluteOffset;
            if(this.offset >= absoluteOffset
               && this.offset < absoluteOffset + scp.getStoredSourceCodeLength())
                return this.walkDownThePath(scp, topLevel);
        }

    return aSourceCodePart;
}

public void addPart(final IJCTPathPart part)
{ throw new UnsupportedOperationException("A File Offset Path can not be cut in parts."); }

public IJCTPathPart getFirstPart()
{ return null; }

public IJCTPathPart getLastPart()
{ return null; }

/**
 * Since JCTFileOffsetPath are immutable, no clone implementation is needed.
 *
 * @return {@code this}
 */
public JCTFileOffsetPath clone()
{ return this; }

public boolean equals(final Object that)
{
    if(this == that)
        return true;

    if(!(that instanceof JCTFileOffsetPath))
        return false;

    final JCTFileOffsetPath path = (JCTFileOffsetPath)that;

    return this.getFile().equals(path.getFile())
        && (null == this.getOffset() ? null == path.getOffset() : this.getOffset().equals(path.getOffset()))
        && (null == this.getInformativeData() ? null == path.getInformativeData() : Arrays.equals(this.getInformativeData(), path.getInformativeData()));
}

public boolean isEnclosing(final IJCTPath that)
{ throw new UnsupportedOperationException("A File Offset Path can not know if another path is enclosing it."); }
