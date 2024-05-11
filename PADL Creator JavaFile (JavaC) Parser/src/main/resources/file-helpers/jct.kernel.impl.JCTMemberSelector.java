@SuppressWarnings("unchecked")
private void check()
{
    if(JCTKind.CLASS != this.getElement().getEnclosingElement().getKind())
        throw new IllegalStateException("the element must be a class member.");

    IJCTClassType c;

    if((JCTKind.MEMBER_SELECTOR == this.getQualifyingExpression().getKind()
        || JCTKind.SIMPLE_SELECTOR == this.getQualifyingExpression().getKind())
       && JCTKind.CLASS == ((IJCTSelector)this.getQualifyingExpression()).getElement().getKind())
        c = ((IJCTSelector<IJCTClass>)this.getQualifyingExpression()).getElement().createClassType();
    else
    {
        final IJCTType t = this.getQualifyingExpression().getTypeResult();
        if(JCTKind.CLASS_TYPE == t.getKind())
            c = (IJCTClassType)t;
        else //if(JCTKind.ARRAY_TYPE == t.getKind())
            c = null; //((IJCTArrayType)t).getArrayTypeClass().createClassType();
    }

    if(!this.getElement().isMemberOf(c))
        throw new IllegalStateException("the element must be a class member of the type specified by the qualifying expression.");
}

public Member getElement()
{ return this.memberSelector.get().getElement(); }

// todo : implements verification that there is no misuse of JCTMemberSelector as JCTSimpleSelector
public void setElement(final Member e)
{
    final Member old = this.getElement();
    this.memberSelector.get().setElement(e);
    try
    {
        this.check();
    }
    catch(final IllegalStateException ex)
    {
        this.memberSelector.get().setElement(old);
        throw new IllegalArgumentException(ex);
    }
}

public IJCTExpression getQualifyingExpression()
{ return this.qualifyingExpression.get(); }

public void setQualifyingExpression(final IJCTExpression e)
{
    final IJCTExpression old = this.getQualifyingExpression();
    this.qualifyingExpression.set(e);
    try
    {
        this.check();
    }
    catch(final IllegalStateException ex)
    {
        this.qualifyingExpression.set(old);
        throw new IllegalArgumentException(ex);
    }
}

public IJCTSimpleSelector<Member> getMemberSelector()
{ return this.memberSelector.get(); }

public void setMemberSelector(final IJCTSimpleSelector<Member> e)
{
    final IJCTSimpleSelector<Member> old = this.getMemberSelector();
    this.memberSelector.set(e);
    try
    {
        this.check();
    }
    catch(final IllegalStateException ex)
    {
        this.memberSelector.set(old);
        throw new IllegalArgumentException(ex);
    }
}

public Writer getSourceCode(final Writer w) throws IOException
{
    return this.getQualifyingExpression().getSourceCode(w)
        .append('.')
        .append(this.getElement().getName());
}

public IJCTType getTypeResult()
{ return this.getMemberSelector().getTypeResult(); }

@Override
public String toString()
{ return this.getSourceCode(); }

private static final long serialVersionUID = 6368668412833343974L;
