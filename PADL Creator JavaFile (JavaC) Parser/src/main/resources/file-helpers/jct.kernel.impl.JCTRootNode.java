private class OrphanList extends ListOfUnique<IJCTElement> implements Serializable
{
    private static final long serialVersionUID = 4627063330787906314L;

    public OrphanList()
    { super(new Identity<IJCTElement>()); }

    @Override
        public void add(final int i, final IJCTElement e)
    {
        final int old_index = this.indexOf(e);

        if(i == old_index)
            return;

        if(-1 != old_index)
        {
            final int max = i > old_index ? i : old_index;
            final ListIterator<IJCTElement> it = this.listIterator(i < old_index ? i : old_index);
            while(it.hasNext() && it.nextIndex() <= max)
            {
                final IJCTElement elem = it.next();
                if(elem instanceof JCTElement)
                    ((JCTElement)elem).discardCachedPathPartBuilderIndex();
            }
        }

        super.add(i, e);

        if(-1 == old_index)
            JCTRootNode.this.discardOrphansCachedPathPartBuilderIndex((JCTElement)e);
    }

    @Override
        public IJCTElement remove(final int i)
    {
        final JCTElement e = (JCTElement)this.get(i);
        JCTRootNode.this.discardOrphansCachedPathPartBuilderIndex(e);
        return super.remove(i);
    }
}

/**
 * Discards the Cached Path Part Builder Index stored in each orphan after e
 */
private void discardOrphansCachedPathPartBuilderIndex(final JCTElement e)
{
    final Iterator<IJCTElement> it = this.getOrphans().iterator();
    while(it.hasNext() && e != it.next());
    while(it.hasNext())
    {
        final IJCTElement elem = it.next();
        if(null != elem && elem instanceof JCTElement)
            ((JCTElement)elem).discardCachedPathPartBuilderIndex();
    }
}

private           final Map<JCTPrimitiveTypes, IJCTPrimitiveType>        primitiveTypes    = new HashMap<JCTPrimitiveTypes, IJCTPrimitiveType                  >();
private transient final Map<String, WeakReference<IJCTArrayType>>        arrayTypes        = new HashMap<String,            WeakReference<IJCTArrayType>       >();
private transient final Map<String, WeakReference<IJCTIntersectionType>> intersectionTypes = new HashMap<String,            WeakReference<IJCTIntersectionType>>();
private transient final Set<WeakReference<IJCTClassType>>                classesTypes      = new HashSet<WeakReference<IJCTClassType>                          >();

private IJCTArrayType getCachedArrayType(final String name)
{
    final WeakReference<IJCTArrayType> ref = this.arrayTypes.get(name);

    if(null == ref)
        return null;

    if(null == ref.get())
    {
        this.arrayTypes.remove(name);
        return null;
    }

    return ref.get();
}

private IJCTIntersectionType getCachedIntersectionType(final String name)
{
    final WeakReference<IJCTIntersectionType> ref = this.intersectionTypes.get(name);

    if(null == ref)
        return null;

    if(null == ref.get())
    {
        this.intersectionTypes.remove(name);
        return null;
    }

    return ref.get();
}

/**
 * Returns the type designed by the path
 */
public <T extends IJCTType> T getType(final String path, final Class<T> type)
{
    if(1 == path.length())
        return type.cast(this.getType(JCTPrimitiveTypes.resolveType(path)));

    if(Constants.ARRAY_MARKER == path.charAt(0))
    {
        IJCTArrayType t = this.getCachedArrayType(path);
        if(null == t)
            this.arrayTypes.put(path,
                                new WeakReference<IJCTArrayType>(t = new JCTArrayType(this, this.getType(path.substring(1),
                                                                                                         IJCTType.class))));
        return type.cast(t);
    }

    if(Constants.INTERSECTION_MARKER == path.charAt(0))
    {
        final String names[] = Constants.INTERSECTION_SPLITTER_PATTERN.split(path.substring(1));
        if(1 == names.length)
            return this.getType(path.substring(1), type);

        IJCTIntersectionType t = this.getCachedIntersectionType(path);
        if(null == t)
        {
            final IJCTType types[] = new IJCTType[names.length];
            for(int i = 0; i < names.length; ++i)
                types[i] = this.getType(names[i], IJCTType.class);
            this.intersectionTypes.put(path, new WeakReference<IJCTIntersectionType>(t = new JCTIntersectionType(this, types)));
        }
        return type.cast(t);
    }

    if(Constants.CLASS_MARKER == path.charAt(0))
    {
        final JCTPath p = this.getPath();

        final int dotIndex = path.lastIndexOf(Constants.DOT_SEPARATOR);
        String classes[];
        if(-1 != dotIndex)
        {
            //p.addPart(new JCTPathPartBuilder(JCTKind.PACKAGE).setData(path.substring(1, dotIndex)).createPathPart());
            classes = Constants.DOLLAR_SPLITTER_PATTERN.split(path.substring(dotIndex + 1));
        }
        else
            classes = Constants.DOLLAR_SPLITTER_PATTERN.split(path.substring(1));

        for(final String s : classes)
            p.addPart(new JCTPathPartBuilder(JCTKind.CLASS).setData(s).createPathPart());

        final IJCTClass c = (IJCTClass)p.walk(this);

        return type.cast(null == c ? null : c.createClassType());
    }

    throw new IllegalArgumentException("A Type path must be a primitive type name (length = 1) or start by '"
                                       + Constants.ARRAY_MARKER + "', '"
                                       + Constants.CLASS_MARKER + "' or '"
                                       + Constants.INTERSECTION_MARKER + "'.\n"
                                       + path);
}

public IJCTType getType(final IJCTType... types)
{
    if(0 == types.length)
        return this.getType(JCTPrimitiveTypes.VOID);
    if(1 == types.length)
        return types[0];

    final String names[] = new String[types.length];

    for(int i = 0; i < types.length; ++i)
        names[i] = types[i].getTypeName();

    final StringBuilder typeName = new StringBuilder(Constants.INTERSECTION_MARKER);

    for(final String name : names)
        typeName.append(name).append(Constants.INTERSECTION_SEPARATOR);

    typeName.setLength(typeName.length() - 1);

    IJCTIntersectionType t = this.getCachedIntersectionType(typeName.toString());
    if(null == t)
        this.intersectionTypes.put(typeName.toString(),
                                   new WeakReference<IJCTIntersectionType>(t = new JCTIntersectionType(this, types)));

    return t;
}

public IJCTArrayType registerArrayType(final IJCTType underlyingType, final String underlyingTypeName)
{
    final String arrayTypePath = Constants.ARRAY_MARKER
        + (null == underlyingTypeName
           ? underlyingType.getTypeName()
           : underlyingTypeName);

    IJCTArrayType t = this.getCachedArrayType(arrayTypePath);
    if(null == t)
        this.arrayTypes.put(arrayTypePath,
                            new WeakReference<IJCTArrayType>(t = new JCTArrayType(this, underlyingType, underlyingTypeName)));
    else if(!t.getUnderlyingType().getTypeName().equals(underlyingType.getTypeName()))
        throw new IllegalStateException("An array type is registered with this name (" + underlyingTypeName + "), but does not use the same underlying type.");

    return t;
}

protected void registerClassType(final IJCTClassType aClassType)
{ this.classesTypes.add(new WeakReference<IJCTClassType>(aClassType)); }

private ListOfElements<IJCTClassType> getAllClassTypes()
{
    final ListOfElements<IJCTClassType> result = new ListOfElements<IJCTClassType>();
    final Iterator<WeakReference<IJCTClassType>> it = this.classesTypes.iterator();
    while(it.hasNext())
    {
        final WeakReference<IJCTClassType> wct = it.next();
        if(null == wct.get())
            it.remove();
        else
            result.add(wct.get());
    }
    return result;
}

@Override
    public String getSourceCode()
{
    try
    {
        final StringWriter w = new StringWriter();

        final String packageSeparator = new String(Character.toChars(0x1D)); // GROUP SEPARATOR control character

        final Iterator<IJCTPackage> it = this.getPackages().iterator();
        while(it.hasNext())
        {
            it.next().getSourceCode(w);
            if(it.hasNext())
                w.append(packageSeparator);
        }

        return w.toString();
    }
    catch (final IOException e) { throw new RuntimeException(e); }
}

protected <T extends IJCTElement> ListOfElements<T> seeNextPathStep(final JCTKind aKind)
{
    final ListOfElements<T> result = new ListOfElements<T>();

    switch(aKind)
    {
        case INTERSECTION_TYPE:
            {
                final Iterator<Map.Entry<String, WeakReference<IJCTIntersectionType>>> it = this.intersectionTypes.entrySet().iterator();
                while(it.hasNext())
                {
                    final Map.Entry<String, WeakReference<IJCTIntersectionType>> entry = it.next();
                    final IJCTIntersectionType type = entry.getValue().get();
                    if(null == type)
                        it.remove();
                    else
                        result.add((T)type);
                }
            }
            break;
        case ARRAY_TYPE:
            {
                final Iterator<Map.Entry<String, WeakReference<IJCTArrayType>>> it = this.arrayTypes.entrySet().iterator();
                while(it.hasNext())
                {
                    final Map.Entry<String, WeakReference<IJCTArrayType>> entry = it.next();
                    final IJCTArrayType type = entry.getValue().get();
                    if(null == type)
                        it.remove();
                    else
                        result.add((T)type);
                }
            }
            break;
        case PRIMITIVE_TYPE:
            return new ListOfElements(this.primitiveTypes.values());
        case CLASS_TYPE:
            return (ListOfElements<T>)this.getAllClassTypes();
        case ROOT_NODE:
            result.add((T)this);
            break;
        case CLASS:
            result.addAll((Collection<T>)this.getAllEnclosedElements(JCTKind.CLASS, IJCTClass.class, true));
        default:
            for(final IJCTElement e : new IndirectCollection<IJCTElement>(this.getEnclosedElements(),
                                                                          this.getOrphans()))
                if(aKind == e.getKind())
                    result.add((T)e);
    }

    return result;
}

private void writeObject(final java.io.ObjectOutputStream out) throws IOException
{
    out.defaultWriteObject();

    final Map<String, IJCTArrayType> arrayTypes = new HashMap<String, IJCTArrayType>();
    for(final Map.Entry<String, WeakReference<IJCTArrayType>> e : this.arrayTypes.entrySet())
    {
        final IJCTArrayType type = e.getValue().get();
        if(null != type)
            arrayTypes.put(e.getKey(), type);
    }
    out.writeObject(arrayTypes);

    final Map<String, IJCTIntersectionType> intersectionTypes = new HashMap<String, IJCTIntersectionType>();
    for(final Map.Entry<String, WeakReference<IJCTIntersectionType>> e : this.intersectionTypes.entrySet())
    {
        final IJCTIntersectionType type = e.getValue().get();
        if(null != type)
            intersectionTypes.put(e.getKey(), type);
    }
    out.writeObject(intersectionTypes);

    final Set<IJCTClassType> classesTypes = new HashSet<IJCTClassType>();
    for(final WeakReference<IJCTClassType> t : this.classesTypes)
    {
        final IJCTClassType type = t.get();
        if(null != type)
            classesTypes.add(type);
    }
    out.writeObject(classesTypes);
}


private void readObject(final java.io.ObjectInputStream in) throws IOException, ClassNotFoundException
{
    in.defaultReadObject();
    this.factory = new SoftReference<IJCTFactory>(null);

    try
    {
        final Field arrayTypesField = JCTRootNode.class.getDeclaredField("arrayTypes");
        arrayTypesField.setAccessible(true);
        arrayTypesField.set(this, new HashMap<String, WeakReference<IJCTArrayType>>());
        arrayTypesField.setAccessible(false);
        final Field intersectionTypesField = JCTRootNode.class.getDeclaredField("intersectionTypes");
        intersectionTypesField.setAccessible(true);
        intersectionTypesField.set(this, new HashMap<String, WeakReference<IJCTIntersectionType>>());
        intersectionTypesField.setAccessible(false);
        final Field classesTypesField = JCTRootNode.class.getDeclaredField("classesTypes");
        classesTypesField.setAccessible(true);
        classesTypesField.set(this, new HashSet<WeakReference<IJCTClassType>>());
        classesTypesField.setAccessible(false);
    }
    catch(final NoSuchFieldException ex)
    { throw new LinkageError(ex.toString()); }
    catch(final IllegalAccessException ex)
    { throw new LinkageError(ex.toString()); }

    final Map<String, IJCTArrayType> arrayTypes = (Map<String, IJCTArrayType>)in.readObject();
    for(final Map.Entry<String, IJCTArrayType> e : arrayTypes.entrySet())
        this.arrayTypes.put(e.getKey(), new WeakReference<IJCTArrayType>(e.getValue()));

    final Map<String, IJCTIntersectionType> intersectionTypes = (Map<String, IJCTIntersectionType>)in.readObject();
    for(final Map.Entry<String, IJCTIntersectionType> e : intersectionTypes.entrySet())
        this.intersectionTypes.put(e.getKey(), new WeakReference<IJCTIntersectionType>(e.getValue()));
        
    final Set<IJCTClassType> classesTypes = (Set<IJCTClassType>)in.readObject();
    for(final IJCTClassType t : classesTypes)
        this.classesTypes.add(new WeakReference<IJCTClassType>(t));
}

private static final long serialVersionUID = -2164333244376938613L;
