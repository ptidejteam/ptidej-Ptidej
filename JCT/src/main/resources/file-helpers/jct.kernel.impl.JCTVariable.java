public Writer getSourceCode(final Writer w) throws IOException
{
    for(final JCTModifiers m : this.getModifiers())
        w.append(m.toString().toLowerCase())
            .append(' ');

    if(null != this.getType())
        this.getType().getSourceCode(w)
            .append(' ');

    w.append(this.getName());

    if(null != this.getInitialValue())
        this.getInitialValue().getSourceCode(w.append(" = "));

    return w.append(";\n");
}

private static final Map<JCTModifiers, Integer> modifiersIncompatibility = new HashMap<JCTModifiers, Integer>();

static
{
    JCTVariable.modifiersIncompatibility.put(JCTModifiers.FINAL,
                                             JCTModifiers.VOLATILE.getFlag());

    JCTVariable.modifiersIncompatibility.put(JCTModifiers.PRIVATE,
                                             JCTModifiers.PROTECTED.getFlag()
                                             | JCTModifiers.PUBLIC.getFlag());

    JCTVariable.modifiersIncompatibility.put(JCTModifiers.PROTECTED,
                                             JCTModifiers.PRIVATE.getFlag()
                                             | JCTModifiers.PUBLIC.getFlag());

    JCTVariable.modifiersIncompatibility.put(JCTModifiers.PUBLIC,
                                             JCTModifiers.PRIVATE.getFlag()
                                             | JCTModifiers.PROTECTED.getFlag());

    JCTVariable.modifiersIncompatibility.put(JCTModifiers.STATIC, 0);

    JCTVariable.modifiersIncompatibility.put(JCTModifiers.TRANSIENT, 0);

    JCTVariable.modifiersIncompatibility.put(JCTModifiers.VOLATILE,
                                             JCTModifiers.FINAL.getFlag());
};

protected boolean hasIncompatibleModifier(final JCTModifiers m)
{
    final Integer incompatibility = JCTVariable.modifiersIncompatibility.get(m);
    if(null == incompatibility)
        throw new IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") is not supported by variables and fields.");
    return (this.getModifierFlags() & incompatibility) != 0;
}

private static final long serialVersionUID = -1751968487848114731L;
