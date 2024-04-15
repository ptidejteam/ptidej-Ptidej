package jct.test.rsc.snpsht.verfilesystem.visitor;
import jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement;
import jct.test.rsc.snpsht.verfilesystem.IVerFsElement;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
abstract public class AbstractVerFsVisitor
implements jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor
{
public void <init>()
{
this.<init>();

}

public java.lang.Object visit(jct.test.rsc.snpsht.verfilesystem.IVerFsElement toVisit)
{
try
{
return this.visit((jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement)toVisit);

}
catch(java.lang.ClassCastException e1) 
{
try
{
return this.visit((jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision)toVisit);

}
catch(java.lang.ClassCastException e2) 
{
throw new java.lang.IllegalArgumentException("Illegal class element : " + toVisit.getClass().toString());

}

}

}

abstract public java.lang.Object visit(jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement toVisit)
{

}

abstract public java.lang.Object visit(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision toVisit)
{

}


}
