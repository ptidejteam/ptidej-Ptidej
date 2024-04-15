private static IJCTField createLength(final JCTArrayType anArrayType)
{
    final IJCTFactory f = anArrayType.getRootNode().getFactory();

    final IJCTField vLength = f.createField(Constants.LENGTH_NAME);
    vLength.setType(anArrayType.getRootNode().getType(JCTPrimitiveTypes.INTEGER));
    vLength.addModifier(JCTModifiers.PUBLIC);
    vLength.addModifier(JCTModifiers.FINAL);
    ((JCTElement)vLength).updateEnclosingElement(anArrayType);
    return vLength;
}

private static IJCTMethod createClone(final JCTArrayType anArrayType)
{
    final IJCTFactory f = anArrayType.getRootNode().getFactory();

    final IJCTMethod mClone = f.createMethod(Constants.CLONE_NAME);
    mClone.setReturnType(anArrayType);
    mClone.addModifier(JCTModifiers.PUBLIC);
    mClone.addModifier(JCTModifiers.FINAL);
    mClone.addModifier(JCTModifiers.NATIVE);
    ((JCTElement)mClone).updateEnclosingElement(anArrayType);
    return mClone;
}

public boolean equals(final Object o)
{
    if(!(o instanceof IJCTArrayType))
        return false;

    final IJCTArrayType at = (IJCTArrayType)o;

    if(null != this.getUnderlyingType()
       && null != at.getUnderlyingType())
        return this.getUnderlyingType().equals(at.getUnderlyingType());
    else
    {
        final String underlyingTypeName = this.getUnderlyingTypeName();
        return null == underlyingTypeName
            ? null == at.getUnderlyingTypeName()
            : underlyingTypeName.equals(at.getUnderlyingTypeName());
    }
}

private static final long serialVersionUID = 2877865367379685701L;
