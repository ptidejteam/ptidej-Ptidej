package jct.test.rsc.jct.kernel.impl;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTLeftShiftAssignment;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTLeftShiftAssignment
extends jct.test.rsc.jct.kernel.impl.JCTAssignment
implements jct.test.rsc.jct.kernel.IJCTLeftShiftAssignment
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
this.<init>(aRootNode, variable, value);

}

protected java.lang.String getCompoundOperator()
{
return "<<";

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.LEFT_SHIFT_ASSIGNMENT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitLeftShiftAssignment(this, aP);

}


}
