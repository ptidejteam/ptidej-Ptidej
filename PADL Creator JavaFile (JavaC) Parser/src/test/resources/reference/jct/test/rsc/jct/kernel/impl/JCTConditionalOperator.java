package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTConditionalOperator;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTIntersectionType;
import jct.test.rsc.jct.kernel.IJCTNonPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
class JCTConditionalOperator
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTConditionalOperator
{
final private jct.test.rsc.jct.util.reference.NotNullableReference condition;

final private jct.test.rsc.jct.util.reference.NotNullableReference thenExpression;

final private jct.test.rsc.jct.util.reference.NotNullableReference elseExpression;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTExpression thenExpression, final jct.test.rsc.jct.kernel.IJCTExpression elseExpression)
{
this.<init>(aRootNode);
this.condition = this.createInternalReference(condition);
this.thenExpression = this.createInternalReference(thenExpression);
this.elseExpression = this.createInternalReference(elseExpression);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.condition, this.thenExpression, this.elseExpression));

}

public void setCondition(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
this.condition.set(condition);

}

public jct.test.rsc.jct.kernel.IJCTExpression getCondition()
{
return this.condition.get();

}

public void setThenExpression(final jct.test.rsc.jct.kernel.IJCTExpression thenExpression)
{
this.thenExpression.set(thenExpression);

}

public jct.test.rsc.jct.kernel.IJCTExpression getThenExpression()
{
return this.thenExpression.get();

}

public void setElseExpression(final jct.test.rsc.jct.kernel.IJCTExpression elseExpression)
{
this.elseExpression.set(elseExpression);

}

public jct.test.rsc.jct.kernel.IJCTExpression getElseExpression()
{
return this.elseExpression.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.CONDITIONAL_OPERATOR;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitConditionalOperator(this, aP);

}

private static jct.test.rsc.jct.kernel.IJCTType UnboxedType(final jct.test.rsc.jct.kernel.IJCTType t)
{
if(null == t) return null;
if(t instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType) return t;
final jct.test.rsc.jct.kernel.IJCTClass c = ((jct.test.rsc.jct.kernel.IJCTClassType)t).getSelector().getElement();
if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_DOUBLE.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.DOUBLE);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_FLOAT.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.FLOAT);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_LONG.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.LONG);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_INT.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.INTEGER);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_SHORT.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.SHORT);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_BYTE.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.BYTE);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_BOOLEAN.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.BOOLEAN);
 else if(jct.test.rsc.jct.kernel.Constants.CLASSPATH_CHAR.equals(c.getFQN())) return t.getRootNode().getType(jct.test.rsc.jct.kernel.JCTPrimitiveTypes.CHARACTER);
 else return t;

}

private static jct.test.rsc.jct.kernel.IJCTType BoxedType(final jct.test.rsc.jct.kernel.IJCTType t)
{
if(! (t instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType)) return t;
switch((((jct.test.rsc.jct.kernel.IJCTPrimitiveType)t).getType()))
{
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.DOUBLE:
return t.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_DOUBLE, class);
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.FLOAT:
return t.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_FLOAT, class);
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.LONG:
return t.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_LONG, class);
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.INTEGER:
return t.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_INT, class);
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.SHORT:
return t.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_SHORT, class);
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.BYTE:
return t.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_BYTE, class);
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.BOOLEAN:
return t.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_BOOLEAN, class);
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.CHARACTER:
return t.getRootNode().getType(jct.test.rsc.jct.kernel.Constants.CLASS_BINARYNAME_CHAR, class);
case jct.test.rsc.jct.kernel.JCTPrimitiveTypes.VOID:
default:
return t;
}

}

private static void GetLUB(final jct.test.rsc.jct.kernel.IJCTClassType c, final java.util.Set grid, final java.util.Set result)
{
if(grid.contains(c)) 
{
result.add(c);
return;

}
final jct.test.rsc.jct.kernel.IJCTClassType e = c.getSelector().getElement().getDirectSuperClass();
if(null != e) jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.GetLUB(e, grid, result);
for(jct.test.rsc.jct.kernel.IJCTClassType i : c.getSelector().getElement().getDirectlyImplementedInterfaces()) jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.GetLUB(i, grid, result);

}

public jct.test.rsc.jct.kernel.IJCTType getTypeResult()
{
return jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.GetTypeResult(this.getThenExpression().getTypeResult(), this.getElseExpression().getTypeResult());

}

private static jct.test.rsc.jct.kernel.IJCTType GetTypeResult(final jct.test.rsc.jct.kernel.IJCTType type, final jct.test.rsc.jct.kernel.IJCTType expression)
{
jct.test.rsc.jct.kernel.IJCTType t = type;
jct.test.rsc.jct.kernel.IJCTType e = expression;
if(t == e) return t;
final java.util.Set result = new java.util.HashSet();
if((t instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType) && (e instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType)) for(jct.test.rsc.jct.kernel.IJCTType ti : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)t).getTypes()) for(jct.test.rsc.jct.kernel.IJCTType ei : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)e).getTypes()) result.add(jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.GetTypeResult(ti, ei));
 else if(t instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType) for(jct.test.rsc.jct.kernel.IJCTType ti : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)t).getTypes()) result.add(jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.GetTypeResult(ti, e));
 else if(e instanceof jct.test.rsc.jct.kernel.IJCTIntersectionType) for(jct.test.rsc.jct.kernel.IJCTType ei : ((jct.test.rsc.jct.kernel.IJCTIntersectionType)e).getTypes()) result.add(jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.GetTypeResult(t, ei));
 else 
{
t = jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.UnboxedType(t);
e = jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.UnboxedType(e);
if((t instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType) && (e instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType)) return ((jct.test.rsc.jct.kernel.IJCTPrimitiveType)t).getType().compareTo(((jct.test.rsc.jct.kernel.IJCTPrimitiveType)e).getType()) > 0 ? t : e;
if((t instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType) && jct.test.rsc.jct.kernel.JCTPrimitiveTypes.VOID == ((jct.test.rsc.jct.kernel.IJCTPrimitiveType)t).getType()) return e;
if((e instanceof jct.test.rsc.jct.kernel.IJCTPrimitiveType) && jct.test.rsc.jct.kernel.JCTPrimitiveTypes.VOID == ((jct.test.rsc.jct.kernel.IJCTPrimitiveType)e).getType()) return t;
t = jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.BoxedType(t);
e = jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.BoxedType(e);
if(! (t instanceof jct.test.rsc.jct.kernel.IJCTClass) || ! (e instanceof jct.test.rsc.jct.kernel.IJCTClass)) return null;
final jct.test.rsc.jct.kernel.IJCTClassType ct = (jct.test.rsc.jct.kernel.IJCTClassType)t;
final jct.test.rsc.jct.kernel.IJCTClassType ce = (jct.test.rsc.jct.kernel.IJCTClassType)e;
jct.test.rsc.jct.kernel.impl.JCTConditionalOperator.GetLUB(ce, ct.getAllSuperClasses(), result);

}
return t.getRootNode().getType(new jct.test.rsc.jct.kernel.impl.JCTIntersectionType(t.getRootNode(), result.toArray(new jct.test.rsc.jct.kernel.IJCTType[0])).getTypeName(), class);

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{
this.getCondition().getSourceCode(w).append(" ? ");
this.getThenExpression().getSourceCode(w).append(" : ");
return this.getElseExpression().getSourceCode(w);

}


}
