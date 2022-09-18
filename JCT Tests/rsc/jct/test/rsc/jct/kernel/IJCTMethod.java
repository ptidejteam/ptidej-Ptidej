package jct.test.rsc.jct.kernel;
import java.util.List;
import java.util.Set;
public interface IJCTMethod
extends jct.test.rsc.jct.kernel.IJCTClassMember, jct.test.rsc.jct.kernel.IJCTElementContainer
{
public void setReturnType(final jct.test.rsc.jct.kernel.IJCTType returnType)
{

}

public jct.test.rsc.jct.kernel.IJCTType getReturnType()
{

}

public void addParameter(final int anIndex, final jct.test.rsc.jct.kernel.IJCTParameter aParameter)
{

}

public void addParameter(final jct.test.rsc.jct.kernel.IJCTParameter aParameter)
{

}

public void removeParameter(final jct.test.rsc.jct.kernel.IJCTParameter aParameter)
{

}

public void removeParameter(final int anIndex)
{

}

public java.util.List getParameters()
{

}

public void setBody(final jct.test.rsc.jct.kernel.IJCTBlock body)
{

}

public jct.test.rsc.jct.kernel.IJCTBlock getBody()
{

}

public void addThrownException(final jct.test.rsc.jct.kernel.IJCTClassType thrownException)
{

}

public void removeThrownException(final jct.test.rsc.jct.kernel.IJCTClassType thrownException)
{

}

public java.util.Set getThrownExceptions()
{

}


}
