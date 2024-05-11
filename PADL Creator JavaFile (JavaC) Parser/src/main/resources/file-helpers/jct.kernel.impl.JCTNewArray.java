public IJCTArrayType getTypeResult()
{
    final StringBuilder str = new StringBuilder();
    final int dims = this.getDimensions().size() + this.getUnspecifiedDimensions();
    for(int i = 0; i < dims; ++i)
        str.append(Constants.ARRAY_MARKER);
    return this.getRootNode().getType(str.toString() + this.getElementType().getTypeName(),
                                      IJCTArrayType.class);
}

public Writer getSourceCode(final Writer aWriter) throws IOException
{
    if(null != this.getElementType())
    {
        aWriter.append("new ");
        this.getElementType().getSourceCode(aWriter);
    }

    for(final IJCTExpression d : this.getDimensions())
    {
        aWriter.append('[');
        d.getSourceCode(aWriter).append(']');
    }

    for(int i = 0; i < this.getUnspecifiedDimensions(); ++i)
        aWriter.append("[]");

    if(this.getInitializers().size() > 0)
    {
        aWriter.append(" { ");
        final Iterator<IJCTExpression> it = this.getInitializers().iterator();
        while(it.hasNext())
        {
            it.next().getSourceCode(aWriter);
            if(it.hasNext())
                aWriter.append(", ");
        }
        aWriter.append(" }");
    }

    return aWriter;
}

private static final long serialVersionUID = -8940260999687664933L;
