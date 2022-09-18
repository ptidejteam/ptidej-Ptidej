package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import jct.test.rsc.jct.kernel.IJCTBlock;
import jct.test.rsc.jct.kernel.IJCTCatch;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTTry;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;
import jct.test.rsc.jct.util.reference.NullableReference;
class JCTTry
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTTry
{
final private jct.test.rsc.jct.util.reference.NotNullableReference tryBlock;

final private java.util.List catchBlocks = this.createInternalList();

final private jct.test.rsc.jct.util.reference.NullableReference finallyBlock = this.createNullableInternalReference();

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);
this.tryBlock = this.createInternalReference(this.getRootNode().getFactory().createBlock());
this.backpatchElements(new jct.test.rsc.jct.util.collection.IndirectCollection(this.tryBlock, this.catchBlocks, this.finallyBlock));

}

public void setTryBlock(final jct.test.rsc.jct.kernel.IJCTBlock tryBlock)
{
this.tryBlock.set(tryBlock);

}

public jct.test.rsc.jct.kernel.IJCTBlock getTryBlock()
{
return this.tryBlock.get();

}

public void addCatchBlock(final int anIndex, final jct.test.rsc.jct.kernel.IJCTCatch catchBlock)
{
this.catchBlocks.add(anIndex, catchBlock);

}

public void addCatchBlock(final jct.test.rsc.jct.kernel.IJCTCatch catchBlock)
{
this.catchBlocks.add(catchBlock);

}

public void removeCatchBlock(final jct.test.rsc.jct.kernel.IJCTCatch catchBlock)
{
this.catchBlocks.remove(catchBlock);

}

public void removeCatchBlock(final int anIndex)
{
this.catchBlocks.remove(anIndex);

}

public java.util.List getCatchBlocks()
{
return java.util.Collections.unmodifiableList(this.catchBlocks);

}

public void setFinallyBlock(final jct.test.rsc.jct.kernel.IJCTBlock finallyBlock)
{
this.finallyBlock.set(finallyBlock);

}

public jct.test.rsc.jct.kernel.IJCTBlock getFinallyBlock()
{
return this.finallyBlock.get();

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.TRY;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitTry(this, aP);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append("try");
this.getTryBlock().getSourceCode(aWriter);
for(jct.test.rsc.jct.kernel.IJCTCatch c : this.getCatchBlocks()) c.getSourceCode(aWriter);
if(null != this.getFinallyBlock()) 
{
if(0 == this.getCatchBlocks().size()) aWriter.append('
');
aWriter.append("finally");
this.getFinallyBlock().getSourceCode(aWriter);

}
return aWriter;

}


}
