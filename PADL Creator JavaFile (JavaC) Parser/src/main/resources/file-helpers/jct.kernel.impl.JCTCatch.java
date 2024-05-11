public Writer getSourceCode(final Writer aWriter) throws IOException
{
    aWriter.append("catch(");

    final IJCTVariable v = this.getVariable();
    if(null != v.getType())
        v.getType().getSourceCode(aWriter)
            .append(' ');

    aWriter.append(v.getName())
        .append(") ");

    return this.getBody().getSourceCode(aWriter);
}
