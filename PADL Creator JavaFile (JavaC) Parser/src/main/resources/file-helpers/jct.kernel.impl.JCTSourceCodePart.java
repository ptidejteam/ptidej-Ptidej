/**
 * Returns the offset of the stored code source representation of
 * this element, within its direct enclosing element's.
 *
 * @return null iff not (indirectly) enclosed in a {@link jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
 */
public Integer getStoredSourceCodeOffset()
{
    return this.getEnclosingCompilationUnit() == null
        || this.getEnclosingCompilationUnit().getStoredSourceCode() == null
        ? null
        : this.getStoredSourceCodeOffset((IJCTSourceCodePart)this.getEnclosingElement());
}

public Integer getStoredSourceCodeOffset(final IJCTSourceCodePart enclosingElement)
{
    if (null == this.getEnclosingCompilationUnit() || null == this.storedSourceCodeOffset)
        return null;

	try
    {
        if(enclosingElement == this.getEnclosingElement() || null == enclosingElement)
            return this.storedSourceCodeOffset;
        else
            return this.storedSourceCodeOffset + ((IJCTSourceCodePart) this.getEnclosingElement()).getStoredSourceCodeOffset(enclosingElement);
	}
    catch (final ClassCastException e)
    { throw new IllegalArgumentException("enclosingElement must be an Enclosing Element of this", e); }
    catch (final NullPointerException e)
    { return null; }
}

public void setStoredSourceCodeOffset(final Integer offset)
{ this.storedSourceCodeOffset = offset; }

protected void updateStoredSourceCode(final JCTSourceCodePart part, final int absoluteOffset, final int delta)
{
    if(this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit()) > absoluteOffset)
        this.setStoredSourceCodeOffset(this.getStoredSourceCodeOffset() + delta);
    else if(this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit()) + this.getStoredSourceCodeLength() < absoluteOffset)
        return; // throw new IllegalArgumentException("updateStoredSourceCode should be called only on elements whose end is after absoluteOffset");
    else if(this != part)
        for(final IJCTElement enclosingPart : this.getEnclosedElements())
        {
            if(!(enclosingPart instanceof JCTSourceCodePart) || null == ((JCTSourceCodePart)enclosingPart).getStoredSourceCodeOffset())
                continue;
            final JCTSourceCodePart scp = (JCTSourceCodePart)enclosingPart;
            if(scp.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit()) + scp.getStoredSourceCodeLength() >= absoluteOffset)
                scp.updateStoredSourceCode(part, absoluteOffset, delta);
        }
}

/**
 * Returns the length of the stored code source representation of this element.
 *
 * @return null iff not (indirectly) enclosed in a {@link jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
 */
public Integer getStoredSourceCodeLength()
{
    return this.getEnclosingCompilationUnit() == null
        || this.getEnclosingCompilationUnit().getStoredSourceCode() == null
        ? null
        : (null == this.storedSourceCodeLength ? this.getSourceCode().length() : this.storedSourceCodeLength);
}

public void setStoredSourceCodeLength(final Integer length)
{ this.storedSourceCodeLength = length; }

/**
 * Returns the stored code source representation of this element.
 *
 * @return null iff not (indirectly) enclosed in a {@link jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
 */
public String getStoredSourceCode()
{
    if(null == this.getEnclosingCompilationUnit() || null == this.storedSourceCodeLength)
        return null;

    final String cuRepr = this.getEnclosingCompilationUnit().getStoredSourceCode();
    if(null == cuRepr)
        return null;

    final int absoluteOffset = this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit());

    return cuRepr.substring(absoluteOffset, absoluteOffset + this.storedSourceCodeLength);
}

public void setStoredSourceCode(final String storedSourceCode)
{
    final String actualStoredSourceCode = null == storedSourceCode
        ? ""
        : storedSourceCode;

    ((JCTCompilationUnit)this.getEnclosingCompilationUnit()).updateStoredSourceCode(this, actualStoredSourceCode);
    this.storedSourceCodeLength = actualStoredSourceCode.length();
}

public IJCTCompilationUnit getEnclosingCompilationUnit()
{
    IJCTElement e = this;

    while (null != e
           && JCTKind.COMPILATION_UNIT != e.getKind())
        e = e.getEnclosingElement();

    return (IJCTCompilationUnit) e;
}

private static final long serialVersionUID = -5351711300068082445L;
