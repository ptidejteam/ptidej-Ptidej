public Writer getSourceCode(final Writer aWriter) throws IOException
{
    aWriter.append("for(");

    final IJCTVariable v = this.getVariable();
    if(null != v.getType())
        v.getType().getSourceCode(aWriter)
            .append(' ');

    aWriter.append(v.getName())
        .append(" : ");

    this.getIterable().getSourceCode(aWriter).append(") ");

    return this.getBody().getSourceCode(aWriter);
}

private static final long serialVersionUID = 2855514883378284257L;
