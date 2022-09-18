public IJCTType getTypeResult()
{
    final IJCTMethod m = this.getMethodSelector().getElement();

    if(null == m)
        return null;

    return m.getReturnType();
}

public Writer getSourceCode(final Writer w) throws IOException
{
    this.getMethodSelector().getSourceCode(w)
        .append('(');

    final Iterator<IJCTExpression> it = this.getArguments().iterator();
    while(it.hasNext())
    {
        it.next().getSourceCode(w);
        if(it.hasNext())
            w.append(", ");
    }

    return w.append(')');
}

private static final long serialVersionUID = 579416475301872235L;
