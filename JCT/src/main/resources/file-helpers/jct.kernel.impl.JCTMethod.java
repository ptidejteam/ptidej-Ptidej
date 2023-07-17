@Override
public JCTPathPartBuilder createPathPart()
{
    final StringBuilder str = new StringBuilder(this.getName());

    str.append(Constants.METHOD_MARKER);
    final Iterator<IJCTParameter> it = this.getParameters().iterator();

    while (it.hasNext())
    {
        final IJCTParameter p = it.next();
        str.append(null == p.getType() ? null : p.getType().getTypeName());
        if (it.hasNext())
            str.append(Constants.PARAMETER_SEPARATOR);
    }

    return super.createPathPart().setData(str.toString());
}

@Override
public boolean equals(final Object o)
{
    if(this == o)
        return true;

    if(!(o instanceof IJCTMethod))
        return false;

    final IJCTMethod m = (IJCTMethod)o;

    if(Constants.INSTANCE_INITIALIZER_NAME.equals(m.getName())
       || Constants.CLASS_INITIALIZER_NAME.equals(m.getName()))
        return false;

    if(!this.getName().equals(m.getName()))
        return false;

    if(this.getParameters().size() != m.getParameters().size())
        return false;

    final Iterator<IJCTParameter> tit = this.getParameters().iterator();
    final Iterator<IJCTParameter> mit =    m.getParameters().iterator();

    while(mit.hasNext())
    {
        final IJCTType targtype = tit.next().getType();
        final IJCTType margtype = mit.next().getType();
        if(null == margtype || null == targtype
           || !margtype.equals(targtype))
            return false;
    }

    return true;
}

public String getID()
{
    final StringBuffer result = new StringBuffer();

    result.append(this.getName())
        .append('(');

    final Iterator<IJCTParameter> pit = this.getParameters().iterator();
    while(pit.hasNext())
    {
        final String var = pit.next().getSourceCode();
        result.append(var.substring(0, var.length() - 2));
        if(pit.hasNext())
            result.append(", ");
    }

    return result.append(')').append(super.getID()).toString();
}

public Writer getSourceCode(final Writer w) throws IOException
{
    for(final JCTModifiers m : this.getModifiers())
        w.append(m.toString().toLowerCase())
            .append(' ');

    this.getReturnType().getSourceCode(w)
        .append(' ')
        .append(this.getName())
        .append('(');

    final Iterator<IJCTParameter> pit = this.getParameters().iterator();
    while(pit.hasNext())
    {
        final String var = pit.next().getSourceCode();
        w.append(var.substring(0, var.length() - 2));
        if(pit.hasNext())
            w.append(", ");
    }

    w.append(')');

    if(this.getThrownExceptions().size() > 0)
    {
        w.append(" throws ");
        final SortedSet<IJCTClassType> thrown = new TreeSet<IJCTClassType>(new Comparator<IJCTClassType>() { public int compare(final IJCTClassType o1, final IJCTClassType o2) { return o1.getSelector().getElement().getFQN().compareTo(o2.getSelector().getElement().getFQN()); } });
        thrown.addAll(this.getThrownExceptions());

        final Iterator<IJCTClassType> cit = thrown.iterator();
        while(cit.hasNext())
        {
            cit.next().getSourceCode(w);
            if(cit.hasNext())
                w.append(", ");
        }
    }

    if(null != this.getBody())
        this.getBody().getSourceCode(w);
    else
        w.append(";\n");

    return w;
}

private static final Map<JCTModifiers, Integer> modifiersIncompatibility = new HashMap<JCTModifiers, Integer>();

static
{
    JCTMethod.modifiersIncompatibility.put(JCTModifiers.ABSTRACT,
                                           JCTModifiers.PRIVATE.getFlag()
                                           | JCTModifiers.FINAL.getFlag()
                                           | JCTModifiers.STATIC.getFlag()
                                           | JCTModifiers.NATIVE.getFlag()
                                           | JCTModifiers.STRICTFP.getFlag()
                                           | JCTModifiers.SYNCHRONIZED.getFlag());

    JCTMethod.modifiersIncompatibility.put(JCTModifiers.FINAL,
                                           JCTModifiers.ABSTRACT.getFlag());

    JCTMethod.modifiersIncompatibility.put(JCTModifiers.NATIVE,
                                           JCTModifiers.STRICTFP.getFlag()
                                           | JCTModifiers.ABSTRACT.getFlag());

    JCTMethod.modifiersIncompatibility.put(JCTModifiers.PRIVATE,
                                           JCTModifiers.PROTECTED.getFlag()
                                           | JCTModifiers.PUBLIC.getFlag()
                                           | JCTModifiers.ABSTRACT.getFlag());

    JCTMethod.modifiersIncompatibility.put(JCTModifiers.PROTECTED,
                                           JCTModifiers.PRIVATE.getFlag()
                                           | JCTModifiers.PUBLIC.getFlag());

    JCTMethod.modifiersIncompatibility.put(JCTModifiers.PUBLIC,
                                           JCTModifiers.PRIVATE.getFlag()
                                           | JCTModifiers.PROTECTED.getFlag());

    JCTMethod.modifiersIncompatibility.put(JCTModifiers.STATIC,
                                           JCTModifiers.ABSTRACT.getFlag());

    JCTMethod.modifiersIncompatibility.put(JCTModifiers.STRICTFP,
                                           JCTModifiers.ABSTRACT.getFlag()
                                           | JCTModifiers.NATIVE.getFlag());

    JCTMethod.modifiersIncompatibility.put(JCTModifiers.SYNCHRONIZED,
                                           JCTModifiers.ABSTRACT.getFlag());
};

protected boolean hasIncompatibleModifier(final JCTModifiers m)
{
    final Integer incompatibility = JCTMethod.modifiersIncompatibility.get(m);
    if(null == incompatibility)
        throw new IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") is not supported by methods.");
    return (this.getModifierFlags() & incompatibility) != 0;
}

private static final long serialVersionUID = -8854746396705409982L;
