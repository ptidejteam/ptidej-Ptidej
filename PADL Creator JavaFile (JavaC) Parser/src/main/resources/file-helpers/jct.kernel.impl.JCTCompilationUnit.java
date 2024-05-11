public String getSourceCode()
{
    try
    {
        final StringWriter w = new StringWriter()
            .append(this.getSourceFile().getPath())
            .append(new String(Character.toChars(0x00)));  // NULL control character

        return this.getSourceCode(w).toString();
    } catch (final IOException e) { throw new RuntimeException(e); }
}

public Writer getSourceCode(final Writer aWriter) throws IOException
{
    final IJCTPackage p = (IJCTPackage)this.getEnclosingElement();
    if(null != p
       && !p.isUnnamed())
        aWriter.append("package " + p.getName() + ";\n");

    final SortedSet<IJCTImport> is = new TreeSet<IJCTImport>(new Comparator<IJCTImport>() { public int compare(final IJCTImport o1, final IJCTImport o2) { return o1.toString().compareTo(o2.toString()); } });
    is.addAll(this.getImporteds());

    for(final IJCTImport i : is)
        i.getSourceCode(aWriter);

    for(final IJCTClass c : this.getClazzs())
        c.getSourceCode(aWriter);

    return aWriter;
}

public void setStoredSourceCodeLength(final Integer length)
{ throw new UnsupportedOperationException("A compilation unit is not pre-calculated"); }

public Integer getStoredSourceCodeLength()
{
    if(null != this.storedSourceCode)
        super.setStoredSourceCodeLength(this.storedSourceCode.length());

    return super.getStoredSourceCodeLength();
}

public void setStoredSourceCodeOffset(final Integer offset)
{ throw new UnsupportedOperationException("A compilation unit is always at offset 0"); }

public Integer getStoredSourceCodeOffset()
{
    super.setStoredSourceCodeOffset(0);
    return null == this.getStoredSourceCode() ? null : 0;
}

public Integer getStoredSourceCodeOffset(final IJCTSourceCodePart enclosingElement)
{
    if(this != enclosingElement)
        throw new IllegalArgumentException("enclosingElement must be an Enclosing Element of this");

    return this.getStoredSourceCodeOffset();
}

public String getStoredSourceCode()
{ return null == this.storedSourceCode ? null : this.storedSourceCode.toString(); }

public void setStoredSourceCode(final String storedSourceCode)
{ this.storedSourceCode = new StringBuffer(storedSourceCode); }

protected void updateStoredSourceCode(final JCTSourceCodePart part, final String storedSourceCode)
{
    if(null == this.storedSourceCode)
        throw new IllegalStateException("Impossible to replace a part of Stored Source Code if there is no SSC.");

    final int absoluteOffset = part.getStoredSourceCodeOffset(this);

    this.storedSourceCode.replace(absoluteOffset, absoluteOffset + part.getStoredSourceCodeLength(),
                                  storedSourceCode);

    final int delta = storedSourceCode.length() - part.getStoredSourceCodeLength();
    
    if(delta != 0)
        for(final IJCTSourceCodePart enclosingPart : this.getEnclosedElements())
        {
            if(!(enclosingPart instanceof JCTSourceCodePart) || null == enclosingPart.getStoredSourceCodeOffset())
                continue;
            final JCTSourceCodePart scp = (JCTSourceCodePart)enclosingPart;
            if(scp.getStoredSourceCodeOffset(this) + scp.getStoredSourceCodeLength() >= absoluteOffset)
                scp.updateStoredSourceCode(part, absoluteOffset, delta);
        }
}

public File getSourceFile()
{ return this.sourceFile; }

public void setSourceFile(final File sourceFile)
{
    this.sourceFile = sourceFile;
    this.setName(sourceFile.getAbsolutePath());
}

private static final long serialVersionUID = -1061725052762178233L;

@Override
protected boolean isDesignatedBy(final String data)
{
    return super.isDesignatedBy(data)
        || data.equals(this.getSourceFile().getName());
}
