public IJCTClassMember getElement()
{ 
    final IJCTIdentifiable element = this.getIdentifier().getElement();
    if(null != element
       && !(element instanceof IJCTClassMember))
        throw new IllegalStateException("Erroneous Simple Selector, please use this.getSelector().getMember() instead.");
    return (IJCTClassMember)element;
}

//@SuppressWarnings("unchecked")
public void setElement(final IJCTIdentifiable e)
{ this.getIdentifier().setElement(e); }

public IJCTExpression getQualifyingExpression()
{
    if(JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
        return ((IJCTMemberSelector)this.getIdentifier()).getQualifyingExpression();
    else
        throw new UnsupportedOperationException("getQualifyingExpression is implemented only by MEMBER_SELECTOR");
}

public void setQualifyingExpression(final IJCTExpression e)
{
    if(JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
        ((IJCTMemberSelector)this.getIdentifier()).setQualifyingExpression(e);
    else
        throw new UnsupportedOperationException("setQualifyingExpression is implemented only by MEMBER_SELECTOR");
}

public IJCTSimpleSelector getMemberSelector()
{
    if(JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
        return ((IJCTMemberSelector)this.getIdentifier()).getMemberSelector();
    else
        throw new UnsupportedOperationException("getMemberSelector is implemented only by MEMBER_SELECTOR");
}

//@SuppressWarnings("unchecked")
public void setMemberSelector(final IJCTSimpleSelector e)
{
    if(JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
        ((IJCTMemberSelector)this.getIdentifier()).setMemberSelector(e);
    else
        throw new UnsupportedOperationException("setMemberSelector is implemented only by MEMBER_SELECTOR");
}


public Writer getSourceCode(final Writer w) throws IOException
{ return this.getIdentifier().getSourceCode(w); }

@Override
    public boolean equals(final Object o)
{ return this.getIdentifier().equals(o); }

@Override
    public int hashCode()
{ return this.getIdentifier().hashCode(); }

public JCTKind getKind()
{ return this.getIdentifier().getKind(); }

public IJCTType getTypeResult()
{ return this.getIdentifier().getTypeResult(); }

@Override
public String toString()
{ return this.getIdentifier().toString(); }

@Override
//@SuppressWarnings("unchecked")
public Collection<IJCTExpression> getEnclosedElements()
{
    if(JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
        return ((JCTMemberSelector)this.getIdentifier()).getEnclosedElements();
    else
        throw new UnsupportedOperationException("getEnclosedElements is implemented only by MEMBER_SELECTOR");
}

public <R, P> R accept(final IJCTVisitor<R, P> v, final P p)
{
    if(this.getIdentifier() instanceof JCTUnresolvedSimpleSelector)
        return v.visitErroneousSelector(this, p);
    else
        return this.getIdentifier().accept(v, p);
}

public void setElement(final IJCTClassMember e)
{ this.setElement((IJCTIdentifiable)e); }

private static final long serialVersionUID = 5562018097630802866L;
