package jct.test.rsc.jct.kernel.impl;
import java.util.Collections;
import java.util.Set;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTField;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTField
extends jct.test.rsc.jct.kernel.impl.JCTVariableImpl
implements jct.test.rsc.jct.kernel.IJCTField
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type)
{
this.<init>(aRootNode, name, type, jct.test.rsc.jct.kernel.JCTKind.FIELD);

}


}
