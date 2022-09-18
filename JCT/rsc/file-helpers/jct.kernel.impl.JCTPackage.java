@Override
protected JCTPathPartBuilder createPathPart()
{
    final JCTPathPartBuilder p = super.createPathPart();

    if(null == p.getInformativeData())
    {
        byte informativeData = 0x00;
        if(this.getIsGhost())
            informativeData |= 0x01;
        
        p.setInformativeData(new byte[] { informativeData });
    }

    return p;
}

public String getSourceCode()
{
    try
    {
        final String fileSeparator = new String(Character.toChars(0x1C)); // File Separator

        final StringWriter w = new StringWriter();

        final Iterator<IJCTCompilationUnit> it = this.getEnclosedElements().iterator();
        while(it.hasNext())
        {
            it.next().getSourceCode(w);
            if(it.hasNext())
                w.append(fileSeparator);
        }

        return w.toString();
    }
    catch (final IOException e) { throw new RuntimeException(e); }
}

public Writer getSourceCode(final Writer w) throws IOException
{
    for(final IJCTCompilationUnit cu : this.getEnclosedElements())
        if(cu != null)
            cu.getSourceCode(w);
    return w;
}

/**
 * Returns the List of elements potentialy designated by a next path part on this kind.
 */
protected <T extends IJCTElement> ListOfElements<T> seeNextPathStep(final JCTKind aKind)
{
    if(JCTKind.CLASS == aKind)
        return new ListOfElements<T>((Collection<T>)this.getAllEnclosedElements(JCTKind.CLASS, IJCTClass.class, true));

    final ListOfElements<T> result = new ListOfElements<T>();
    for(final IJCTCompilationUnit e : this.getEnclosedElements())
        if(null != e && aKind == e.getKind())
            result.add((T)e);
    return result;
}

private static final long serialVersionUID = -3077264082215917277L;
