public IJCTClassType getClassType()
{ return this.classType; }

public void setClassType(final IJCTClassType classType)
{
    if(null != this.getAnnonymousClass()
       && !classType.isExtendingOrImplementing(this.getAnnonymousClass().createClassType()))
        throw new IllegalArgumentException("The annonymous declared class must extend or implements the identified class");

    if(null == this.getAnnonymousClass()
       && classType.getSelector().getElement().getIsInterface())
        throw new IllegalArgumentException("Instanciating an interface is not allewd.");

    this.classType = classType;
}

public IJCTClass getAnnonymousClass()
{ return this.annonymousClass.get(); }

public void setAnnonymousClass(final IJCTClass annonymousClass)
{
    if(null != annonymousClass)
    {
        if(!this.getClassType().isExtendingOrImplementing(annonymousClass.createClassType()))
            throw new IllegalArgumentException("The annonymous declared class must extend or implements the identified class");

        if(annonymousClass.getIsInterface())
            throw new IllegalArgumentException("Annonymous interface does not exists");
    }
    else
        if(this.getClassType().getSelector().getElement().getIsInterface())
            throw new IllegalArgumentException("Instanciating an interface is not allewd.");

    this.annonymousClass.set(annonymousClass);
}

public IJCTType getTypeResult()
{
    final IJCTClass c = this.getAnnonymousClass();
    return null != c ? c.createClassType() : this.getClassType();
}

public Writer getSourceCode(final Writer aWriter) throws IOException
{
    if(null != this.getSelectingExpression())
        this.getSelectingExpression().getSourceCode(aWriter).append('.');

    aWriter.append("new ");
    this.getClassType().getSourceCode(aWriter)
        .append('(');

    final Iterator<IJCTExpression> it = this.getArguments().iterator();
    while(it.hasNext())
    {
        it.next().getSourceCode(aWriter);
        if(it.hasNext())
            aWriter.append(", ");
    }
    aWriter.append(')');

    if(null != this.getAnnonymousClass())
    {
        aWriter.append("\n{\n");

        for(final IJCTClassMember cm : this.getAnnonymousClass().getDeclaredMembers())
            cm.getSourceCode(aWriter).append('\n');

        aWriter.append('}');
    }

    return aWriter;
}

private static final long serialVersionUID = -3639816725484866682L;
