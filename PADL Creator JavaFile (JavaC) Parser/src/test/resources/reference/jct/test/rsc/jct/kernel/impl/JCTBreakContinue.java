package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTLabel;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSourceCodePart;
abstract class JCTBreakContinue
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTSourceCodePart
{
private jct.test.rsc.jct.kernel.IJCTLabel label = null;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode)
{
this.<init>(aRootNode);

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
aWriter.append(this.getKeyword());
if(null != this.getLabel()) aWriter.append(' ').append(this.getLabel().getName());
return aWriter.append(";
");

}

abstract protected java.lang.String getKeyword()
{

}

public void setLabel(final jct.test.rsc.jct.kernel.IJCTLabel label)
{
this.label = label;

}

public jct.test.rsc.jct.kernel.IJCTLabel getLabel()
{
return this.label;

}


}
