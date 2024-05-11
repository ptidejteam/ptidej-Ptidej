package jct.test.rsc.jct.kernel;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
public interface IJCTPackage
extends jct.test.rsc.jct.kernel.IJCTElementContainer, jct.test.rsc.jct.kernel.IJCTImportable
{
public boolean isUnnamed()
{

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{

}

public java.lang.String getSourceCode()
{

}

public void setPackageDeclaration(final jct.test.rsc.jct.kernel.IJCTCompilationUnit packageDeclaration)
{

}

public jct.test.rsc.jct.kernel.IJCTCompilationUnit getPackageDeclaration()
{

}

public void addCompilationUnit(final int anIndex, final jct.test.rsc.jct.kernel.IJCTCompilationUnit aCompilationUnit)
{

}

public void addCompilationUnit(final jct.test.rsc.jct.kernel.IJCTCompilationUnit aCompilationUnit)
{

}

public void removeCompilationUnit(final jct.test.rsc.jct.kernel.IJCTCompilationUnit aCompilationUnit)
{

}

public void removeCompilationUnit(final int anIndex)
{

}

public java.util.List getCompilationUnits()
{

}

public void setIsGhost(final boolean isGhost)
{

}

public boolean getIsGhost()
{

}


}
