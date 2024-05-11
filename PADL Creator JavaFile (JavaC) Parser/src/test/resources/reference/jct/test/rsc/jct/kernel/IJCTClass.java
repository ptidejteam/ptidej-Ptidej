package jct.test.rsc.jct.kernel;
import java.util.Collection;
import java.util.List;
import java.util.Set;
public interface IJCTClass
extends jct.test.rsc.jct.kernel.IJCTClassMember, jct.test.rsc.jct.kernel.IJCTElementContainer
{
public java.lang.String getFQN()
{

}

public jct.test.rsc.jct.kernel.IJCTClassType createClassType()
{

}

public jct.test.rsc.jct.kernel.IJCTField getThisField()
{

}

public jct.test.rsc.jct.kernel.IJCTField getSuperField()
{

}

public jct.test.rsc.jct.kernel.IJCTField getClassField()
{

}

public java.util.Collection getNestedClasses(final java.lang.Boolean staticOnly)
{

}

public java.util.Collection getFields(final java.lang.Boolean staticOnly, final boolean includeSpecials)
{

}

public java.util.Collection getMethods(final java.lang.Boolean staticOnly)
{

}

public void setDirectSuperClass(final jct.test.rsc.jct.kernel.IJCTClassType directSuperClass)
{

}

public jct.test.rsc.jct.kernel.IJCTClassType getDirectSuperClass()
{

}

public void setIsInterface(final boolean isInterface)
{

}

public boolean getIsInterface()
{

}

public void addDeclaredMember(final int anIndex, final jct.test.rsc.jct.kernel.IJCTClassMember declaredMember)
{

}

public void addDeclaredMember(final jct.test.rsc.jct.kernel.IJCTClassMember declaredMember)
{

}

public void removeDeclaredMember(final jct.test.rsc.jct.kernel.IJCTClassMember declaredMember)
{

}

public void removeDeclaredMember(final int anIndex)
{

}

public java.util.List getDeclaredMembers()
{

}

public void setIsGhost(final boolean isGhost)
{

}

public boolean getIsGhost()
{

}

public void addDirectlyImplementedInterface(final jct.test.rsc.jct.kernel.IJCTClassType directlyImplementedInterface)
{

}

public void removeDirectlyImplementedInterface(final jct.test.rsc.jct.kernel.IJCTClassType directlyImplementedInterface)
{

}

public java.util.Set getDirectlyImplementedInterfaces()
{

}


}
