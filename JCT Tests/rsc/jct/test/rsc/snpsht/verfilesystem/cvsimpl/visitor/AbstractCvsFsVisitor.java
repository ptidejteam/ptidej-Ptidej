package jct.test.rsc.snpsht.verfilesystem.cvsimpl.visitor;
import jct.test.rsc.snpsht.verfilesystem.IVerFsElement;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsRepository;
import jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor;
abstract public class AbstractCvsFsVisitor
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
return this.visit((jct.test.rsc.snpsht.verfilesystem.VerFsRepository)toVisit);

}
catch(java.lang.Exception e1) 
{
try
{
return this.visit((jct.test.rsc.snpsht.verfilesystem.VerFsFileRev)toVisit);

}
catch(java.lang.Exception e2) 
{
throw new java.lang.IllegalArgumentException("Illegal class element : " + toVisit.getClass().toString());

}

}

}

abstract public java.lang.Object visit(jct.test.rsc.snpsht.verfilesystem.VerFsRepository toVisit)
{

}

abstract public java.lang.Object visit(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev toVisit)
{

}


}
