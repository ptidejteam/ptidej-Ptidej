package jct.test.rsc.jct.kernel.impl;
import java.io.IOException;
import java.io.Writer;
import jct.test.rsc.jct.kernel.IJCTComment;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
class JCTComment
extends jct.test.rsc.jct.kernel.impl.JCTSourceCodePart
implements jct.test.rsc.jct.kernel.IJCTComment
{
private java.lang.String text;

private boolean isEndOfLine;

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode aRootNode, final boolean isEndOfLine, final java.lang.String text)
{
this.<init>(aRootNode);
this.text = text;
this.isEndOfLine = isEndOfLine;

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{
return aWriter.append(this.getIsEndOfLine() ? "//" : "/*").append(this.getText()).append(this.getIsEndOfLine() ? "" : "*/");

}

public void setText(final java.lang.String text)
{
this.text = text;

}

public java.lang.String getText()
{
return this.text;

}

public void setIsEndOfLine(final boolean isEndOfLine)
{
this.isEndOfLine = isEndOfLine;

}

public boolean getIsEndOfLine()
{
return this.isEndOfLine;

}

public jct.test.rsc.jct.kernel.JCTKind getKind()
{
return jct.test.rsc.jct.kernel.JCTKind.COMMENT;

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor visitor, final java.lang.Object aP)
{
return visitor.visitComment(this, aP);

}


}
