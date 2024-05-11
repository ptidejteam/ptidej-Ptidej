package jct.test.rsc.snpsht.verfilesystem;
import java.util.ArrayList;
import java.util.List;
import jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor;
public class VerFsRepository
extends jct.test.rsc.snpsht.verfilesystem.AbstractVerFsElement
implements jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement
{
private java.util.List children;

protected void <init>(java.lang.String name, jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement parent)
{
this.<init>(name, parent, new java.util.ArrayList());

}

protected void <init>(java.lang.String name, jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement parent, java.util.List children)
{
this.<init>(name, parent);
this.children = children;

}

public java.util.List getChildren()
{
return this.children;

}

protected void setChildren(java.util.List children)
{
this.children = children;

}

protected void addChildren(java.util.List children)
{
this.children.addAll(children);

}

protected void addChild(jct.test.rsc.snpsht.verfilesystem.IVerFsElement child)
{
this.children.add(child);

}

protected void removeChildren(java.util.List children)
{
this.children.removeAll(children);

}

protected void removeChild(jct.test.rsc.snpsht.verfilesystem.IVerFsElement child)
{
this.children.remove(child);

}

public boolean containsChild(jct.test.rsc.snpsht.verfilesystem.IVerFsElement child)
{
return this.children.contains(child);

}

public java.lang.String getId()
{
return this.getPath();

}

public boolean containsChildId(java.lang.String childId)
{
for(jct.test.rsc.snpsht.verfilesystem.IVerFsElement child : this.children) 
{
if(childId.compareTo(child.getId()) == 0) 
{
return true;

}

}
return false;

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsElement getChildId(java.lang.String childId)
{
for(jct.test.rsc.snpsht.verfilesystem.IVerFsElement child : this.children) 
{
if(childId.compareTo(child.getId()) == 0) 
{
return child;

}

}
return null;

}

public boolean containsChild(java.lang.String childName)
{
for(jct.test.rsc.snpsht.verfilesystem.IVerFsElement child : this.children) 
{
if(childName.compareTo(child.getName()) == 0) 
{
return true;

}

}
return false;

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsElement getChild(java.lang.String childName)
{
for(jct.test.rsc.snpsht.verfilesystem.IVerFsElement child : this.children) 
{
if(childName.compareTo(child.getName()) == 0) 
{
return child;

}

}
return null;

}

public java.lang.String prettyPrint()
{
java.lang.String pp = "+-[" + this.getName() + "]
";
java.lang.String tmp;
for(jct.test.rsc.snpsht.verfilesystem.IVerFsElement fe : this.children) 
{
tmp = fe.prettyPrint();
for(java.lang.String line : tmp.split("
")) 
{
pp += "| " + line + "
";

}

}
return pp;

}

public java.lang.Object accept(jct.test.rsc.snpsht.verfilesystem.visitor.IVerFsVisitor visitor)
{
return visitor.visit(this);

}


}
