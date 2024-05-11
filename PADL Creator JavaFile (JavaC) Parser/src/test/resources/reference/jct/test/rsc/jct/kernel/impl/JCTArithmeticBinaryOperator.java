package jct.test.rsc.jct.kernel.impl;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTIntersectionType;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
abstract class JCTArithmeticBinaryOperator
extends jct.test.rsc.jct.kernel.impl.JCTBinaryOperator
{
void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
this.<init>(aRootNode, leftOperand, rightOperand);

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return jct.test.rsc.jct.kernel.impl.JCTArithmeticBinaryOperator.computeTypeResult(this.getLeftOperand().getTypeResult(), this.getRightOperand().getTypeResult());

}

private static jct.test.rsc.jct.kernel.IJCTType resolveType(final jct.test.rsc.jct.kernel.IJCTType t)
{
if(null == t) return null;
if(t instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType) return t;
final jct.test.rsc.jct.kernel.IJCTClass c = ((jct.test.rsc.jct.kernel.IJCTClassType)t).getSelector().getElement();
if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_STRING.equals(c.getFQN())) return t;
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_DOUBLE.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.DOUBLE);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_FLOAT.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.FLOAT);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_LONG.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.LONG);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_INT.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.INTEGER);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_SHORT.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.SHORT);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_BYTE.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.BYTE);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_BOOLEAN.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.BOOLEAN);
 else return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.VOID);

}

private static jct.test.rsc.jct.kernel.IJCTType computeTypeResult(final jct.test.rsc.jct.kernel.IJCTType l, final jct.test.rsc.jct.kernel.IJCTType r)
{
if((l instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType) || (r instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType)) 
{
final java.util.Set result = new java.util.HashSet();
if((l instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType) && (r instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType)) for(jct.test.rsc.jct.kernel.IJCTType li : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)l).getTypes()) for(jct.test.rsc.jct.kernel.IJCTType ri : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)r).getTypes()) result.add(jct.test.rsc.jct.kernel.impl.JCTArithmeticBinaryOperator.computeTypeResult(li, ri));
 else if(l instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType) for(jct.test.rsc.jct.kernel.IJCTType li : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)l).getTypes()) result.add(jct.test.rsc.jct.kernel.impl.JCTArithmeticBinaryOperator.computeTypeResult(li, r));
 else for(jct.test.rsc.jct.kernel.IJCTType ri : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)r).getTypes()) result.add(jct.test.rsc.jct.kernel.impl.JCTArithmeticBinaryOperator.computeTypeResult(l, ri));
return l.getRootNode().getType(new jct.test.rsc.jct.kernel.impl.JCTIntersectionType(l.getRootNode(), result.toArray(new jct.test.rsc.jct.kernel.IJCTType[0])).getTypeName(), class);

}
 else 
{
jct.test.rsc.jct.kernel.JCTPrimitiveTypes lp = null;
jct.test.rsc.jct.kernel.JCTPrimitiveTypes rp = null;
jct.test.rsc.jct.kernel.IJCTType type = jct.test.rsc.jct.kernel.impl.JCTArithmeticBinaryOperator.resolveType(l);
if(type instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType) lp = ((jct.test.rsc.jct.kernel.IJCTPrimitiveType)type).getType();
 else return type;
type = jct.test.rsc.jct.kernel.impl.JCTArithmeticBinaryOperator.resolveType(r);
if(type instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType) rp = ((jct.test.rsc.jct.kernel.IJCTPrimitiveType)type).getType();
 else return type;
return l.getRootNode().getType(lp.compareTo(rp) > 0 ? lp : rp);

}

}


}
