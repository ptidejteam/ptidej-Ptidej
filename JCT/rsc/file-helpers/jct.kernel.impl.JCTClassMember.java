private int modifiers = 0x0;

public Set<JCTModifiers> getModifiers()
{
    final Set<JCTModifiers> result = new TreeSet<JCTModifiers>();
    for(final JCTModifiers m : JCTModifiers.values())
        if((this.modifiers & m.getFlag()) != 0)
            result.add(m);

    return Collections.unmodifiableSet(result);
}

protected int getModifierFlags()
{ return this.modifiers; }

protected abstract boolean hasIncompatibleModifier(final JCTModifiers m);

public void addModifier(final JCTModifiers m)
{
    if(this.hasIncompatibleModifier(m))
        throw new IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") has an uncompatibility with one of the modifiers of this class member (0x" + Integer.toString(this.getModifierFlags(), 16) + ")");

    this.modifiers |= m.getFlag();
}

public void removeModifier(final JCTModifiers m)
{ this.modifiers &= ~m.getFlag(); }

public boolean isMemberOf(final IJCTNonPrimitiveType c)
{
    if(null == this.isStatic()) 
        return false;

    return c.getAllSuperClasses().contains(this.getEnclosingElement().getKind() == JCTKind.ARRAY_TYPE
                                           ? (IJCTArrayType)this.getEnclosingElement()
                                           : ((IJCTClass)this.getEnclosingElement()).createClassType());
}

public Boolean isStatic()
{
    if(null == this.getEnclosingElement()
       || (JCTKind.ARRAY_TYPE != this.getEnclosingElement().getKind()
           && JCTKind.CLASS != this.getEnclosingElement().getKind()))
        return null;

    return this.getModifiers().contains(JCTModifiers.STATIC);
}

public IJCTClass getDirectEnclosingClass()
{
    IJCTElement e = this.getEnclosingElement();

    while (null != e
           && JCTKind.CLASS != e.getKind()
           && JCTKind.ARRAY_TYPE != e.getKind())
        e = e.getEnclosingElement();

    return (IJCTClass) e;
}

public IJCTClass getTopLevelEnclosingClass()
{
    IJCTClass c = this.getDirectEnclosingClass();

    if(null == c)
        return null;

    IJCTClass ce = c.getDirectEnclosingClass();

    while(null != ce)
    {
        c = ce;
        ce = c.getDirectEnclosingClass();
    }

    return c;
}

private static final long serialVersionUID = -6979445944788269364L;
