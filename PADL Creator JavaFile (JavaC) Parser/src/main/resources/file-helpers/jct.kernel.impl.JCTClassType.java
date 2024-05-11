public String getTypeName()
{
    IJCTClass c = this.getSelector().getElement();

    final Deque<String> names = new LinkedList<String>();

    while (null != c.getDirectEnclosingClass())
    {
        names.push(c.getName());
        c = c.getDirectEnclosingClass();
    }

    final StringBuilder str = new StringBuilder()
        .append(Constants.CLASS_MARKER)
        .append(c.getFQN());

    while (!names.isEmpty())
        str.append(Constants.DOLLAR_SEPARATOR)
            .append(names.pop());

    return str.toString();
}

public Writer getSourceCode(final Writer aWriter) throws IOException
{ return this.getSelector().getSourceCode(aWriter); }

public boolean equals(final Object o)
{
    return o instanceof IJCTClassType
        && this.getSelector().equals(((IJCTClassType)o).getSelector());
}

public void setName(final String newName)
{ throw new UnsupportedOperationException("A ClassType has a (computed and not settable) type name, but no name, therefore you can not set it !"); }

public String getName()
{ return this.getTypeName(); }

private static final long serialVersionUID = 5562018097630802866L;
