public Identifiable getElement()
{ return this.element; }

public void setElement(final Identifiable e)
{
    if(e == null)
        throw new NullPointerException("e must not be null");

    this.element = e;
}

// TODO : case of static qualified class member
// TODO : not fully qualified class names (context-dependent)
// TODO : take care of enclosing classes
@SuppressWarnings("fallthrough")
public Writer getSourceCode(final Writer w) throws IOException
{
    if(JCTKind.SIMPLE_IDENTIFIER == this.getKind())
        w.append(this.getElement().getName());
    else
    {
        final Identifiable e = this.getElement();
        switch(e.getKind())
        {
            case PACKAGE:
                w.append(e.getName());
                break;
            case PRIMITIVE_TYPE:
                w.append(((IJCTPrimitiveType)e).getType().toString().toLowerCase());
                break;
            case CLASS:
                if(null == ((IJCTClassMember)e).isStatic())
                {
                    w.append(((IJCTClass)e).getFQN()); // FQN !!
                    break;
                }
            case VARIABLE:
            case FIELD:
            case PARAMETER:
                if(null == ((IJCTClassMember)e).isStatic())
                {
                    w.append(e.getName());
                    break;
                }
            case METHOD:
                {
                    final IJCTClassMember m = (IJCTClassMember)e;
                    final Boolean isStatic = m.isStatic();
                    if(null != isStatic && isStatic) // FQN !!
                        w.append(((IJCTClass)m.getEnclosingElement()).getFQN())
                            .append('.')
                            .append(m.getName());
                    else
                        w.append(null == isStatic ? "<NULL>." : "this.")
                            .append(m.getName());
                }
                break;
            default:
                throw new AssertionError("the element selected by a selector must be an identifiable element (i.e. either a package, a primitive type or a class member).");
        }
    }

    return w;
}

@Override
public boolean equals(final Object o)
{
    if(o instanceof IJCTSelector)
        return this.getElement().equals(((IJCTSelector)o).getElement());
    else
        return false;
}

@Override
public int hashCode()
{ return this.getElement().hashCode(); }

public JCTKind getKind()
{
    if(null != this.getEnclosingElement()
       && JCTKind.MEMBER_SELECTOR == this.getEnclosingElement().getKind()
       && this == ((IJCTMemberSelector)this.getEnclosingElement()).getMemberSelector())
        if(!(this.getElement() instanceof IJCTClassMember)
           || null == ((IJCTClassMember)this.getElement()).getDirectEnclosingClass())
            throw new AssertionError("This element (" + this.getElement().getPath() + ") can not be selected as the right part of a member selector");
        else
            return JCTKind.SIMPLE_IDENTIFIER;
    else
        return JCTKind.SIMPLE_SELECTOR;
}

public IJCTType getTypeResult()
{
    if(JCTKind.VARIABLE != this.getElement().getKind())
        throw new UnsupportedOperationException("Can not get the type of an identifiable other than a variable.");

    return ((IJCTVariable)this.getElement()).getType();
}

private static final long serialVersionUID = -6216619544675856345L;
