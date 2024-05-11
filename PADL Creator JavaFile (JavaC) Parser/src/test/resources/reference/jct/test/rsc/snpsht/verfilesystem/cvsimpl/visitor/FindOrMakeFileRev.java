package jct.test.rsc.snpsht.verfilesystem.cvsimpl.visitor;
import jct.test.rsc.snpsht.verfilesystem.IVerFsElement;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.VerFsRepository;
public class FindOrMakeFileRev
extends jct.test.rsc.snpsht.verfilesystem.cvsimpl.visitor.AbstractCvsFsVisitor
{
private java.lang.String[] pathTokens;

private java.lang.String id;

private int index;

private jct.test.rsc.snpsht.verfilesystem.VerFsManager manager;

public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>();
this.manager = manager;

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsElement findOrCreate(java.lang.String relativePath, java.lang.String id)
{
this.pathTokens = relativePath.split("/");
this.index = 0;
this.id = id;
java.lang.Object o = this.manager.getRoot().accept(this);
if(o == null) return null;
 else return (jct.test.rsc.snpsht.verfilesystem.IVerFsElement)o;

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsElement visit(jct.test.rsc.snpsht.verfilesystem.VerFsRepository toVisit)
{
java.lang.Object o;
jct.test.rsc.snpsht.verfilesystem.IVerFsElement elem;
if(this.index == this.pathTokens.length - 1) 
{
return this.manager.addSimpleRevision(toVisit, this.pathTokens[this.index], this.id);

}
 else 
{
elem = toVisit.getChild(this.pathTokens[this.index]);
if(elem == null) 
{
elem = this.manager.addComplexElement(toVisit, this.pathTokens[this.index]);

}
this.index ++;
o = elem.accept(this);
if(o == null) return null;
 else return (jct.test.rsc.snpsht.verfilesystem.IVerFsElement)o;

}

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsElement visit(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev toVisit)
{
if(toVisit.getId().compareTo(this.id) == 0) 
{
return toVisit;

}
 else 
{
return null;

}

}


}
