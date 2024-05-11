public Writer getSourceCode(final Writer aWriter) throws IOException
{
    if(!this.isDefaultCase())
        this.getLabel().getSourceCode(aWriter.append("case "));
    else
        aWriter.append("default");

    aWriter.append(":\n");

    for(final IJCTStatement s : this.getStatements())
        s.getSourceCode(aWriter);

    return aWriter;
}

private static final long serialVersionUID = 2954700554419386991L;
