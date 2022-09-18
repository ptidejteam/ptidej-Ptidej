package jct.test.rsc.jct.kernel;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
public interface IJCTRootNode
extends jct.test.rsc.jct.kernel.IJCTElementContainer
{
public jct.test.rsc.jct.kernel.IJCTElement walk(final jct.test.rsc.jct.kernel.IJCTPath aPath)
{

}

public jct.test.rsc.jct.kernel.IJCTFactory getFactory()
{

}

public java.lang.String getName()
{

}

public jct.test.rsc.jct.kernel.IJCTType getType(final java.lang.String path, final java.lang.Class typeClass)
{

}

public jct.test.rsc.jct.kernel.IJCTType getType(final jct.test.rsc.jct.kernel.IJCTType[] types)
{

}

public jct.test.rsc.jct.kernel.IJCTArrayType registerArrayType(final jct.test.rsc.jct.kernel.IJCTType underlyingType)
{

}

public jct.test.rsc.jct.kernel.IJCTArrayType registerArrayType(final jct.test.rsc.jct.kernel.IJCTType underlyingType, final java.lang.String underlyingTypeName)
{

}

public jct.test.rsc.jct.kernel.IJCTPrimitiveType getType(final jct.test.rsc.jct.kernel.JCTPrimitiveTypes aPrimitiveTypeConstant)
{

}

public java.lang.String getSourceCode()
{

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{

}

public boolean isInitialized()
{

}

public void assumeInitialized()
{

}

public void addPackage(final jct.test.rsc.jct.kernel.IJCTPackage aPackage)
{

}

public void removePackage(final jct.test.rsc.jct.kernel.IJCTPackage aPackage)
{

}

public java.util.Set getPackages()
{

}

public void addOrphan(final int anIndex, final jct.test.rsc.jct.kernel.IJCTElement orphan)
{

}

public void addOrphan(final jct.test.rsc.jct.kernel.IJCTElement orphan)
{

}

public void removeOrphan(final jct.test.rsc.jct.kernel.IJCTElement orphan)
{

}

public void removeOrphan(final int anIndex)
{

}

public java.util.List getOrphans()
{

}


}
