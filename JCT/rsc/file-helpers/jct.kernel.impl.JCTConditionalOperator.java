private static IJCTType UnboxedType(final IJCTType t)
{
    if(null == t)
        return null;

    if(t instanceof IJCTPrimitiveType)
        return t;

    final IJCTClass c = ((IJCTClassType)t).getSelector().getElement();

    if(     Constants.CLASSPATH_DOUBLE .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.DOUBLE);
    else if(Constants.CLASSPATH_FLOAT  .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.FLOAT);
    else if(Constants.CLASSPATH_LONG   .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.LONG);
    else if(Constants.CLASSPATH_INT    .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.INTEGER);
    else if(Constants.CLASSPATH_SHORT  .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.SHORT);
    else if(Constants.CLASSPATH_BYTE   .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.BYTE);
    else if(Constants.CLASSPATH_BOOLEAN.equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.BOOLEAN);
    else if(Constants.CLASSPATH_CHAR   .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.CHARACTER);
    else                                                    return t;
}

private static IJCTType BoxedType(final IJCTType t)
{
    if(!(t instanceof IJCTPrimitiveType))
        return t;

    switch(((IJCTPrimitiveType)t).getType())
    {
        case DOUBLE:
            return t.getRootNode().getType(Constants.CLASS_BINARYNAME_DOUBLE,  IJCTClassType.class);
        case FLOAT:
            return t.getRootNode().getType(Constants.CLASS_BINARYNAME_FLOAT,   IJCTClassType.class);
        case LONG:
            return t.getRootNode().getType(Constants.CLASS_BINARYNAME_LONG,    IJCTClassType.class);
        case INTEGER:
            return t.getRootNode().getType(Constants.CLASS_BINARYNAME_INT,     IJCTClassType.class);
        case SHORT:
            return t.getRootNode().getType(Constants.CLASS_BINARYNAME_SHORT,   IJCTClassType.class);
        case BYTE:
            return t.getRootNode().getType(Constants.CLASS_BINARYNAME_BYTE,    IJCTClassType.class);
        case BOOLEAN:
            return t.getRootNode().getType(Constants.CLASS_BINARYNAME_BOOLEAN, IJCTClassType.class);
        case CHARACTER:
            return t.getRootNode().getType(Constants.CLASS_BINARYNAME_CHAR,    IJCTClassType.class);
        case VOID:
        default:
            return t;
    }
}

private static void GetLUB(final IJCTClassType c, final Set<IJCTNonPrimitiveType> grid, final Set<IJCTType> result)
{
    if(grid.contains(c))
    {
        result.add(c);
        return;
    }

    final IJCTClassType e = c.getSelector().getElement().getDirectSuperClass();

    if(null != e)
        JCTConditionalOperator.GetLUB(e, grid, result);

    for(final IJCTClassType i : c.getSelector().getElement().getDirectlyImplementedInterfaces())
        JCTConditionalOperator.GetLUB(i, grid, result);
}

public IJCTType getTypeResult()
{
    return JCTConditionalOperator.GetTypeResult(this.getThenExpression().getTypeResult(),
                                                this.getElseExpression().getTypeResult());
}

private static IJCTType GetTypeResult(final IJCTType type, final IJCTType expression)
{
    IJCTType t = type;
    IJCTType e = expression;

    if(t == e)
        return t; // same type propagation, trivial propagation

    final Set<IJCTType> result = new HashSet<IJCTType>();

    if((t instanceof IJCTIntersectionType)
       && (e instanceof IJCTIntersectionType))
        for(final IJCTType ti : ((IJCTIntersectionType)t).getTypes())
            for(final IJCTType ei : ((IJCTIntersectionType)e).getTypes())
                result.add(JCTConditionalOperator.GetTypeResult(ti, ei));
    else if(t instanceof IJCTIntersectionType)
        for(final IJCTType ti : ((IJCTIntersectionType)t).getTypes())
            result.add(JCTConditionalOperator.GetTypeResult(ti, e));
    else if(e instanceof IJCTIntersectionType)
        for(final IJCTType ei : ((IJCTIntersectionType)e).getTypes())
            result.add(JCTConditionalOperator.GetTypeResult(t, ei));
    else
    {
        t = JCTConditionalOperator.UnboxedType(t);
        e = JCTConditionalOperator.UnboxedType(e);

        if((t instanceof IJCTPrimitiveType)
           && (e instanceof IJCTPrimitiveType))
            return ((IJCTPrimitiveType)t).getType().compareTo(((IJCTPrimitiveType)e).getType()) > 0 ?
                t : e; // Primitive type propagation

        if((t instanceof IJCTPrimitiveType)
           && JCTPrimitiveTypes.VOID == ((IJCTPrimitiveType)t).getType())
            return e;

        if((e instanceof IJCTPrimitiveType)
           && JCTPrimitiveTypes.VOID == ((IJCTPrimitiveType)e).getType())
            return t;

        t = JCTConditionalOperator.BoxedType(t);
        e = JCTConditionalOperator.BoxedType(e);

        if(!(t instanceof IJCTClass)
           || !(e instanceof IJCTClass))
            return null; // not same array type !

        final IJCTClassType ct = (IJCTClassType)t;
        final IJCTClassType ce = (IJCTClassType)e;

        JCTConditionalOperator.GetLUB(ce, ct.getAllSuperClasses(), result);
    }

    return t.getRootNode().getType(new JCTIntersectionType(t.getRootNode(), result.toArray(new IJCTType[0])).getTypeName(), IJCTType.class);
}

public Writer getSourceCode(final Writer w) throws IOException
{
    this.getCondition().getSourceCode(w).append(" ? ");
    this.getThenExpression().getSourceCode(w).append(" : ");
    return this.getElseExpression().getSourceCode(w);
}

private static final long serialVersionUID = -7187953040938908437L;
