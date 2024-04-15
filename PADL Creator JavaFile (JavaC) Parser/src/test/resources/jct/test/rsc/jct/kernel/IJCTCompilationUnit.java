package jct.test.rsc.jct.kernel;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
public interface IJCTCompilationUnit
extends jct.test.rsc.jct.kernel.IJCTElementContainer, jct.test.rsc.jct.kernel.IJCTSourceCodePart
{
public boolean isPackageDeclaration()
{

}

public java.io.Writer getSourceCode(final java.io.Writer aWriter) throws java.io.IOException
{

}

public java.lang.String getSourceCode()
{

}

public void setSourceFile(final java.io.File sourceFile)
{

}

public java.io.File getSourceFile()
{

}

public void addImported(final jct.test.rsc.jct.kernel.IJCTImport imported)
{

}

public void removeImported(final jct.test.rsc.jct.kernel.IJCTImport imported)
{

}

public java.util.Set getImporteds()
{

}

public void addClazz(final jct.test.rsc.jct.kernel.IJCTClass clazz)
{

}

public void removeClazz(final jct.test.rsc.jct.kernel.IJCTClass clazz)
{

}

public java.util.Set getClazzs()
{

}

public void addComment(final int anIndex, final jct.test.rsc.jct.kernel.IJCTComment aComment)
{

}

public void addComment(final jct.test.rsc.jct.kernel.IJCTComment aComment)
{

}

public void removeComment(final jct.test.rsc.jct.kernel.IJCTComment aComment)
{

}

public void removeComment(final int anIndex)
{

}

public java.util.List getComments()
{

}


}
