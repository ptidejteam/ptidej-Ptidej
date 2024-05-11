private static IJCTType resolveType(final IJCTType t)
{
    if(null == t)
        return null;

    if(t instanceof IJCTPrimitiveType)
        return t;

    final IJCTClass c = ((IJCTClassType)t).getSelector().getElement();

    if(     Constants.CLASSPATH_STRING .equals(c.getFQN())) return t;
    else if(Constants.CLASSPATH_DOUBLE .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.DOUBLE);
    else if(Constants.CLASSPATH_FLOAT  .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.FLOAT);
    else if(Constants.CLASSPATH_LONG   .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.LONG);
    else if(Constants.CLASSPATH_INT    .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.INTEGER);
    else if(Constants.CLASSPATH_SHORT  .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.SHORT);
    else if(Constants.CLASSPATH_BYTE   .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.BYTE);
    else if(Constants.CLASSPATH_BOOLEAN.equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.BOOLEAN);
    else                                                    return t.getRootNode().getType(JCTPrimitiveTypes.VOID);
}

private static IJCTType computeTypeResult(final IJCTType l, final IJCTType r)
{
    if((l instanceof IJCTIntersectionType)
       || (r instanceof IJCTIntersectionType))
    {
        final Set<IJCTType> result = new HashSet<IJCTType>();

        if((l instanceof IJCTIntersectionType)
           && (r instanceof IJCTIntersectionType))
            for(final IJCTType li : ((IJCTIntersectionType)l).getTypes())
                for(final IJCTType ri : ((IJCTIntersectionType)r).getTypes())
                    result.add(JCTArithmeticBinaryOperator.computeTypeResult(li, ri));
        else if(l instanceof IJCTIntersectionType)
            for(final IJCTType li : ((IJCTIntersectionType)l).getTypes())
                result.add(JCTArithmeticBinaryOperator.computeTypeResult(li, r));
        else //if(r instanceof IJCTIntersectionType)
            for(final IJCTType ri : ((IJCTIntersectionType)r).getTypes())
                result.add(JCTArithmeticBinaryOperator.computeTypeResult(l, ri));

        return l.getRootNode().getType(new JCTIntersectionType(l.getRootNode(), result.toArray(new IJCTType[0])).getTypeName(), IJCTType.class);
    }
    else
    {
        JCTPrimitiveTypes lp = null;
        JCTPrimitiveTypes rp = null;

        IJCTType type = JCTArithmeticBinaryOperator.resolveType(l);

        if(type instanceof IJCTPrimitiveType)
            lp = ((IJCTPrimitiveType)type).getType();
        else
            return type;

        type = JCTArithmeticBinaryOperator.resolveType(r);

        if(type instanceof IJCTPrimitiveType)
            rp = ((IJCTPrimitiveType)type).getType();
        else
            return type;

        return l.getRootNode().getType(lp.compareTo(rp) > 0 ? lp : rp);
    }
}

private static final long serialVersionUID = 3579689325212112308L;
