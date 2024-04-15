package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTNonPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSelector;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTClassType
extends jct.test.rsc.jct.kernel.impl.JCTNonPrimitiveType
implements jct.test.rsc.jct.kernel.IJCTClassType
{
final private jct.test.rsc.jct.util.reference.NotNullableReference classDeclaration;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTSelector classDeclaration)
{
this.<init>(aRootNode);
if(aRootNode instanceof jct.test.rsc.jct.kernel.impl.JCTRootNode) this.registerClassType(this);
this.classDeclaration = this.createInternalReference(classDeclaration);
this.backpatchElements(this.classDeclaration);

}

public jct.test.rsc.jct.kernel.IJCTSelector getSelector()
{
return this.classDeclaration.get();

}

public void setSelector(final jct.test.rsc.jct.kernel.IJCTSelector classSelector)
{
if(null != this.getSelector().getElement()) throw new java.lang.IllegalStateException("Can not change the selector unless it was previously erroneous");
this.classDeclaration.set(classSelector);

}

public java.util.Set getDirectSuperClasses()
{
final jct.test.rsc.jct.kernel.IJCTClass clazz = this.getSelector().getElement();
final java.util.Set result = new java.util.HashSet();
final jct.test.rsc.jct.kernel.IJCTClassType extended = clazz.getDirectSuperClass();
if(null != extended && this.getSelector().getElement() != extended.getSelector().getElement()) result.add(extended);
for(jct.test.rsc.jct.kernel.IJCTClassType implemented : clazz.getDirectlyImplementedInterfaces()) result.add(implemented);
return java.util.Collections.unmodifiableSet(result);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.CLASS_TYPE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitClassType(this, aP);

}

public java.lang.String getTypeName()
{
jct.test.rsc.jct.kernel.impl.JCTPathPart p = (jct.test.rsc.jct.kernel.impl.JCTPath)this.getSelector().getElement().getPath();
while(p.getResultKind() != jct.test.rsc.jct.kernel.JCTKind.CLASS) p = p.getNextPart();
final java.lang.StringBuilder str = new java.lang.StringBuilder(jct.test.rsc.jct.kernel.Constants.CLASS_MARKER).append(p.getData()).append(jct.test.rsc.jct.kernel.Constants.DOT_SEPARATOR);
while(null != (p = p.getNextPart())) str.append(p.getData()).append(jct.test.rsc.jct.kernel.Constants.DOLLAR_SEPARATOR);
return str.substring(0, str.length() - 1);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return this.getSelector().getSourceCode(aWriter);

}

public boolean equals(final java.lang.Object o)
{
return o instanceof jct.test.rsc.jct.kernel.IJCTClassType && this.getSelector().equals(((jct.test.rsc.jct.kernel.IJCTClassType)o).getSelector());

}

public void setName(final java.lang.String newName)
{
throw new java.lang.UnsupportedOperationException("A ClassType has a (computed and not settable) type name, but no name, therefore you can not set it !");

}

public java.lang.String getName()
{
return this.getTypeName();

}


}
