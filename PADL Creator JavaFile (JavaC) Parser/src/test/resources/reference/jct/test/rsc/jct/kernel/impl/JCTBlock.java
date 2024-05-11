package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTBlock;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTStatement;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTBlock
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTBlock
{
final private java.util.List statements = this.createInternalList();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);
this.backpatchElements(this.statements);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("
{
");
for(jct.test.rsc.jct.kernel.IJCTStatement s : this.getStatements()) s.getSourceCode(aWriter);
return aWriter.append("
}
");

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
return jct.test.rsc.jct.kernel.JCTKind.BLOCK;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitBlock(this, aP);

}


}
