public IJCTCase getDefaultCase()
{ return this.defaultCase.get(); }

public void setDefaultCase(final IJCTCase c)
{
    if(!c.isDefaultCase())
        throw new IllegalArgumentException("Use add/remove/get Case to modify normal cases");

    this.defaultCase.set(c);
}

public List<IJCTCase> getCases()
{ return Collections.unmodifiableList(this.cases); }

public void addCase(final int i, final IJCTCase c)
{
    if(c.isDefaultCase())
        throw new IllegalArgumentException("Use get/set DefaultCase to modify the default case");

    this.cases.add(i, c);
}

public void addCase(final IJCTCase c)
{ this.cases.add(c); }

public void removeCase(final IJCTCase c)
{ this.cases.remove(c); }

public void removeCase(final int i)
{ this.cases.remove(i); }

public Writer getSourceCode(final Writer w) throws IOException
{
    w.append("switch(");
    this.getExpression().getSourceCode(w)
        .append(")\n{\n");

    for(final IJCTCase c : this.getCases())
        c.getSourceCode(w);

    if(null != this.getDefaultCase())
        this.getDefaultCase().getSourceCode(w);

    return w.append("}\n");
}

private static final long serialVersionUID = 6727922564471740879L;
