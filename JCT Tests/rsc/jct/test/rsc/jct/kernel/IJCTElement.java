package jct.test.rsc.jct.kernel;
import java.io.IOException;
import java.io.Writer;
public interface IJCTElement
{
public jct.test.rsc.jct.kernel.JCTKind getKind()
{

}

public java.lang.Object accept(final jct.test.rsc.jct.kernel.IJCTVisitor v, final java.lang.Object p)
{

}

public jct.test.rsc.jct.kernel.IJCTElement getEnclosingElement()
{

}

public jct.test.rsc.jct.kernel.IJCTRootNode getRootNode()
{

}

public java.lang.String getID()
{

}

public java.lang.String getSourceCode()
{

}

public java.io.Writer getSourceCode(final java.io.Writer w) throws java.io.IOException
{

}

public java.lang.String toString()
{

}

public jct.test.rsc.jct.kernel.IJCTPath getPath()
{

}


}
