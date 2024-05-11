/**
 * Cached Class Type
 */
private transient SoftReference<IJCTClassType> cachedClassType = new SoftReference<IJCTClassType>(null);

/**
 * Returns a new IJCTClassType on this class.
 * Roughly equivalent to {@code this.getRootNode().getFactory().createClassType(this.getRootNode().getFactory().createSimpleSelector(this))}
 * The result of this method is plainly cached.
 * If support of generics is added, advanced caching will be required.
 */
public IJCTClassType createClassType() {
    IJCTClassType classType = this.cachedClassType.get();
    
    if(null == classType)
    {
        final IJCTFactory f = this.getRootNode().getFactory();
        classType = f.createClassType(f.createSimpleSelector(((IJCTClass) this)));
        this.cachedClassType = new SoftReference<IJCTClassType>(classType);
    }
    
    return classType;
}

@Override
protected JCTPathPartBuilder createPathPart()
{
    final JCTPathPartBuilder p = super.createPathPart();

    if(null == p.getInformativeData())
    {
        byte informativeData = 0x00;
        if(this.getIsGhost())
            informativeData |= 0x01;
        
        if(this.getIsInterface())
            informativeData |= 0x02;

        p.setInformativeData(new byte[] { informativeData });
    }

    return p;
}

@Override
void updateEnclosingElement(final JCTElementContainer e)
{
    IJCTElement t = e;
    while(null != t)
    {
        if(this == t)
            throw new IllegalArgumentException("Auto-Enclosing class" + this + " : " + e);

        t = t.getEnclosingElement();
    }

    super.updateEnclosingElement(e);
}

private IJCTField[] createSpecialMembers()
{
    final IJCTRootNode r = this.getRootNode();
    final IJCTFactory f = r.getFactory();

    final IJCTField vThis = f.createField(Constants.THIS_NAME);
    vThis.setType(this.createClassType());
    vThis.addModifier(JCTModifiers.PRIVATE);
    vThis.addModifier(JCTModifiers.FINAL);
    ((JCTElement)vThis).updateEnclosingElement(this);

    final IJCTField vSuper = f.createField(Constants.SUPER_NAME);
    vSuper.setType(this.getDirectSuperClass() != null ? this.getDirectSuperClass() : this.createClassType());
    vSuper.addModifier(JCTModifiers.PRIVATE);
    vSuper.addModifier(JCTModifiers.FINAL);
    ((JCTElement)vSuper).updateEnclosingElement(this);

    final IJCTClassType tClass = r.getType(Constants.CLASS_BINARYNAME_CLASS, IJCTClassType.class);
    final IJCTField vClass = f.createField(Constants.CLASS_NAME);
    vClass.setType(tClass != null ? tClass : this.createClassType());
    vClass.addModifier(JCTModifiers.PUBLIC);
    vClass.addModifier(JCTModifiers.FINAL);
    vClass.addModifier(JCTModifiers.STATIC);
    ((JCTElement)vClass).updateEnclosingElement(this);

    return new IJCTField[] { vThis, vSuper, vClass };
}

public boolean getIsInterface()
{ return this.isInterface; }

// todo: referential integrity checking !!
public void setIsInterface(final boolean i)
{
    this.isInterface = i;

    if(!this.getIsInterface() &&
       null == this.getDirectSuperClass())
    {
        final IJCTClassType c = this.getRootNode().getType(Constants.CLASS_BINARYNAME_OBJECT, IJCTClassType.class);
        if(c != null)
            this.setDirectSuperClass(c);
    }
}

public String getFQN()
{
    if(null == this.getEnclosingElement())
        return this.getName();

    if(JCTKind.COMPILATION_UNIT == this.getEnclosingElement().getKind())
    {
        final IJCTPackage p = (IJCTPackage)this.getEnclosingElement().getEnclosingElement();
        return null == p || p.isUnnamed()
            ? this.getName()
            : p.getName() + Constants.DOT_SEPARATOR + this.getName();
    }
    else
        return this.getDirectEnclosingClass().getFQN() + Constants.DOT_SEPARATOR + this.getName();
}


@Override
protected List<JCTElementContainer<?>> seePreviousPathStep()
{
    final JCTClass enclosingClass = (JCTClass)this.getDirectEnclosingClass();
    final List<JCTElementContainer<?>> list = super.seePreviousPathStep();

    if(null != enclosingClass)
        list.add(enclosingClass);
    else if(null != this.getEnclosingElement()
            && JCTKind.COMPILATION_UNIT == this.getEnclosingElement().getKind())
        if(null != this.getEnclosingElement().getEnclosingElement())
            list.add((JCTElementContainer<?>)this.getEnclosingElement().getEnclosingElement());
        else
            list.add((JCTElementContainer<?>)this.getEnclosingElement());

    return list;
}


public IJCTClassType getDirectSuperClass()
{ return this.getIsInterface() ? null : this.directSuperClass; }

public void setDirectSuperClass(final IJCTClassType aJCTClassType)
{
    IJCTClassType c = aJCTClassType;

    if(!this.getIsInterface()
       && null == c)
        c = this.getRootNode().getType(Constants.CLASS_BINARYNAME_OBJECT, IJCTClassType.class);

    if(c.getSelector().getElement().getIsInterface())
        throw new IllegalArgumentException("An interface can not be extended");

    this.directSuperClass = c;

    if(null != this.specialMembers)
        this.getSuperField().setType(c);
}

public Set<IJCTClassType> getDirectlyImplementedInterfaces()
{ return Collections.unmodifiableSet(this.directlyImplementedInterfaces); }

public void addDirectlyImplementedInterface(final IJCTClassType c)
{
    if(!c.getSelector().getElement().getIsInterface())
        throw new IllegalArgumentException("A class cannot be put in the implements list");

    this.directlyImplementedInterfaces.add(c);
}

public void removeDirectlyImplementedInterface(final IJCTClassType c)
{ this.directlyImplementedInterfaces.remove(c); }

public Collection<IJCTClass> getNestedClasses(final Boolean staticOnly)
{
    final Collection<IJCTClass> result = new LinkedList<IJCTClass>();

    for(final IJCTClassMember cm : this.getEnclosedElements())
        if(cm instanceof IJCTClass
           && (null == staticOnly
               || staticOnly.equals(cm.isStatic())))
            result.add((IJCTClass)cm);

    return Collections.unmodifiableCollection(result);
}

public Collection<IJCTField> getFields(final Boolean staticOnly, final boolean includeSpecials)
{
    final Collection<IJCTField> result = new LinkedList<IJCTField>();

    for(final IJCTClassMember cm : includeSpecials
            ? this.getEnclosedElements()
            : this.getDeclaredMembers())
        if(cm instanceof IJCTField
           && (null == staticOnly
               || staticOnly.equals(cm.isStatic())))
            result.add((IJCTField)cm);

    return Collections.unmodifiableCollection(result);
}

public Collection<IJCTMethod> getMethods(final Boolean staticOnly)
{
    final Collection<IJCTMethod> result = new LinkedList<IJCTMethod>();

    for(final IJCTClassMember cm : this.getEnclosedElements())
        if(cm instanceof IJCTMethod
           && (null == staticOnly
               || staticOnly.equals(cm.isStatic())))
            result.add((IJCTMethod)cm);

    return Collections.unmodifiableCollection(result);
}

public Writer getSourceCode(final Writer w) throws IOException
{
    for(final JCTModifiers m : this.getModifiers())
        w.append(m.toString().toLowerCase()).append(' ');

    w.append(this.getIsInterface() ? "interface" : "class")
        .append(' ')
        .append(this.getName());

    if(null != this.getDirectSuperClass()
       && ! "Ljava.lang.Object".equals(this.getDirectSuperClass().getTypeName()))
    {
        w.append("\nextends ");
        this.getDirectSuperClass().getSourceCode(w);
    }

    if(this.getDirectlyImplementedInterfaces().size() > 0)
    {
        w.append('\n')
            .append(this.getIsInterface() ? "extends" : "implements")
            .append(' ');

        final SortedSet<IJCTClassType> implemented = new TreeSet<IJCTClassType>(new Comparator<IJCTClassType>() { public int compare(final IJCTClassType o1, final IJCTClassType o2) { return o1.getSelector().getElement().getFQN().compareTo(o2.getSelector().getElement().getFQN()); } });
        implemented.addAll(this.getDirectlyImplementedInterfaces());

        final Iterator<IJCTClassType> it = implemented.iterator();
        while(it.hasNext())
        {
            it.next().getSourceCode(w);
            if(it.hasNext())
                w.append(", ");
        }
    }

    w.append("\n{\n");

    for(final IJCTClassMember cm : this.getDeclaredMembers())
        cm.getSourceCode(w).append('\n');

    return w.append("\n}\n");
}

private static final Map<JCTModifiers, Integer> modifiersIncompatibility = new HashMap<JCTModifiers, Integer>();

static
{
    JCTClass.modifiersIncompatibility.put(JCTModifiers.ABSTRACT,
                                          JCTModifiers.FINAL.getFlag());

    JCTClass.modifiersIncompatibility.put(JCTModifiers.FINAL,
                                          JCTModifiers.ABSTRACT.getFlag());

    JCTClass.modifiersIncompatibility.put(JCTModifiers.PRIVATE,
                                          JCTModifiers.PROTECTED.getFlag()
                                          | JCTModifiers.PUBLIC.getFlag());

    JCTClass.modifiersIncompatibility.put(JCTModifiers.PROTECTED,
                                          JCTModifiers.PRIVATE.getFlag()
                                          | JCTModifiers.PUBLIC.getFlag());

    JCTClass.modifiersIncompatibility.put(JCTModifiers.PUBLIC,
                                          JCTModifiers.PRIVATE.getFlag()
                                          | JCTModifiers.PROTECTED.getFlag());

    JCTClass.modifiersIncompatibility.put(JCTModifiers.STATIC, 0);
    JCTClass.modifiersIncompatibility.put(JCTModifiers.STRICTFP, 0);
};

protected boolean hasIncompatibleModifier(final JCTModifiers m)
{
    final Integer incompatibility = JCTClass.modifiersIncompatibility.get(m);
    if(null == incompatibility)
        throw new IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") is not supported by classes.");
    return (this.getModifierFlags() & incompatibility) != 0;
}

private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
{
    in.defaultReadObject();
    this.cachedClassType = new SoftReference<IJCTClassType>(null);
}

private static final long serialVersionUID = 5600081698093488662L;
