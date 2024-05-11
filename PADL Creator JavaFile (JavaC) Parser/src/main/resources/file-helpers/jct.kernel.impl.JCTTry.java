public Writer getSourceCode(final Writer aWriter) throws IOException
{
    aWriter.append("try");
    this.getTryBlock().getSourceCode(aWriter);

    for(final IJCTCatch c : this.getCatchBlocks())
        c.getSourceCode(aWriter);

    if(null != this.getFinallyBlock())
    {
        if(0 == this.getCatchBlocks().size())
            aWriter.append('\n');

        aWriter.append("finally");
        this.getFinallyBlock().getSourceCode(aWriter);
    }

    return aWriter;
}

private static final long serialVersionUID = 3699474650123258854L;
