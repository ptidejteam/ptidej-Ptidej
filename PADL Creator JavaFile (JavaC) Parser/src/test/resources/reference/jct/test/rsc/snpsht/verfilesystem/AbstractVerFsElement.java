package jct.test.rsc.snpsht.verfilesystem;
import jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor;
abstract public class AbstractVerFsElement
implements jct.test.rsc.snpsht.verfilesystem.IVerFsElement
{
protected jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement parent;

protected java.lang.String name;

protected void <init>(java.lang.String name, jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement parent)
{
this.<init>();
this.name = name;
this.parent = parent;

}

public java.lang.String getPath()
{
if(this.parent == null) 
{
return "/" + this.name;

}
 else 
{
return this.parent.getPath() + "/" + this.name;

}

}

public java.lang.String getName()
{
return this.name;

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement getParent()
{
return this.parent;

}

protected void setName(java.lang.String name)
{
this.name = name;

}

protected void setParent(jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement parent)
{
this.parent = parent;

}

public java.lang.Object accept(jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor visitor)
{
return visitor.visit(this);

}


}
