package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTCase;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTCase
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTCase
{
final private jct.test.rsc.jct.util.reference.NullableReference label = this.createNullableInternalReference();

final private java.util.List statements = this.createInternalList();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.label, this.statements));

}

public boolean isDefaultCase()
{
return null == this.getLabel();

}

public void setLabel(final jct.test.rsc.jct.kernel.IJCTExpression label)
{
this.label.set(label);

}

public jct.test.rsc.jct.kernel.IJCTExpression getLabel()
{
return this.label.get();

}

public void addStatement(final int anIndex, final jct.test.rsc.jct.kernel.IJCTStatement aStatement)
{
this.statements.add(anIndex, aStatement);

}

public void addStatement(final jct.test.rsc.jct.kernel.IJCTStatement aStatement)
{
this.statements.add(aStatement);

}

public void removeStatement(final jct.test.rsc.jct.kernel.IJCTStatement aStatement)
{
this.statements.remove(aStatement);

}

public void removeStatement(final int anIndex)
{
this.statements.remove(anIndex);

}

public java.util.List getStatements()
{
return java.util.Collections.unmodifiableList(this.statements);

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.CASE;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitCase(this, aP);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
if(! this.isDefaultCase()) this.getLabel().getSourceCode(aWriter.append("case "));
 else aWriter.append("default");
aWriter.append(":
");
for(jct.test.rsc.jct.kernel.IJCTStatement s : this.getStatements()) s.getSourceCode(aWriter);
return aWriter;

}


}
