/**
 * Checks import validity
 */
protected void check() throws IllegalStateException
{
    if(this.isOnDemand && this.isStatic // if on demand and static
       && (JCTKind.CLASS != this.importedElement .getKind() // and not top level class
           || null != ((IJCTClass)this.importedElement).getDirectEnclosingClass()))
        throw new IllegalStateException("On demand, static import must be on class");

    if(this.isOnDemand && !this.isStatic // if on demand and not static
       && JCTKind.PACKAGE != this.importedElement.getKind()) // and not package
        throw new IllegalStateException("On demand, non static import must be on package");

    if(!this.isOnDemand && this.isStatic  // if not on demand and static and ...
       && (JCTKind.PACKAGE == this.importedElement.getKind() // ... package ...
           || (JCTKind.CLASS == this.importedElement.getKind() // ... or top level class ...
               && null == ((IJCTClass)this.importedElement).getDirectEnclosingClass())
           || !((IJCTClassMember)this.importedElement).isStatic())) // ... or non static class member
        throw new IllegalStateException("Static import must be on static class members");

    if(!this.isOnDemand && !this.isStatic // if not on demand and not static
       && JCTKind.CLASS != this.importedElement.getKind()) // and not class
        throw new IllegalStateException("Non static import must be on class");
}

/**
 * Returns whether the import is qualified as "static" or not
 */
public boolean getIsStatic()
{ return this.isStatic; }

/**
 * Modifies the staticity qualification of the import
 */
public void setIsStatic(final boolean s)
{
    if(s == this.isStatic)
        return;

    final boolean save = this.isStatic;
    this.isStatic = s;
    try { this.check(); }
    catch(final IllegalStateException e)
    {
        this.isStatic = save;
        throw new IllegalArgumentException(e);
    }
}

/**
 * Returns whether the import is "on demand" (the last character is a *)
 */
public boolean getIsOnDemand()
{ return this.isOnDemand; }

/**
 * Modifies the "on demand" qualification of the import
 */
public void setIsOnDemand(final boolean od)
{
    if(od == this.isOnDemand)
        return;

    final boolean save = this.isOnDemand;
    this.isOnDemand = od;
    try { this.check(); }
    catch(final IllegalStateException e)
    {
        this.isOnDemand = save;
        throw new IllegalArgumentException(e);
    }
}

/**
 * Returns the element imported
 */
public IJCTImportable getImportedElement()
{ return this.importedElement; }

/**
 * Modifies the imported element
 */
public void setImportedElement(final IJCTImportable i)
{
    final IJCTImportable save = this.importedElement;
    this.importedElement = i;
    try { this.check(); }
    catch(final IllegalStateException e)
    {
        this.importedElement = save;
        throw new IllegalArgumentException(e);
    }
}

public Writer getSourceCode(final Writer w) throws IOException
{
    w.append("import ");

    if(this.getIsStatic())
        w.append("static ");

    switch(this.getImportedElement().getKind())
    {
        case PACKAGE:
            w.append(this.getImportedElement().getName());
            break;
        case CLASS:
            w.append(((IJCTClass)this.getImportedElement()).getFQN());
            break;
        default:
            {
                final IJCTClass c = (IJCTClass)this.getImportedElement().getEnclosingElement();
                w.append(c.getFQN() + "." + this.getImportedElement().getName());
            }
    }

    if(this.getIsOnDemand())
        w.append(".*");

    return w.append(";\n");
}

@Override
public JCTPathPartBuilder createPathPart()
{
    final StringBuilder str = new StringBuilder();

    str.append(this.getIsStatic() ? '1' : '0');
    str.append(this.getIsOnDemand() ? '1' : '0');
    switch(this.getImportedElement().getKind())
    {
        case PACKAGE:
            str.append(this.getImportedElement().getName());
            break;
        case CLASS:
            str.append(((IJCTClass)this.getImportedElement()).getFQN());
            break;
        default: //Class Member
            {
                final IJCTClass c = (IJCTClass)this.getImportedElement().getEnclosingElement();
                str.append(c.getFQN()).append('.').append(this.getImportedElement().getName());
            }
            break;
    }

    return super.createPathPart().setData(str.toString());
}

@Override
public boolean equals(final Object o)
{ return this.toString().equals(o.toString()); }

@Override
public int hashCode()
{ return this.toString().hashCode(); }

@Override
public String toString()
{ return this.getSourceCode(); }
