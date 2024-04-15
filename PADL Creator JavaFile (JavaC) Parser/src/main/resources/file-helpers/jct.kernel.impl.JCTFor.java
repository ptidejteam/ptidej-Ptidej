public Writer getSourceCode(final Writer aWriter) throws IOException
{
    aWriter.append("for(");

    final Iterator<IJCTStatement> sit = this.getInitializers().iterator();
    while(sit.hasNext())
    {
        final String var = sit.next().getSourceCode();
        aWriter.append(var.substring(0, var.length() - 2));
        if(sit.hasNext())
            aWriter.append(", ");
    }

    aWriter.append("; ");
    this.getCondition().getSourceCode(aWriter)
        .append("; ");

    final Iterator<IJCTExpressionStatement> eit = this.getUpdaters().iterator();
    while(eit.hasNext())
    {
        final String update = eit.next().getSourceCode();
        aWriter.append(update.substring(0, update.length() - 2));
        if(eit.hasNext())
            aWriter.append(", ");
    }

    aWriter.append(") ");

    return this.getBody().getSourceCode(aWriter);
}

private static final long serialVersionUID = -5853852778113348131L;
