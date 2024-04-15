package jct.test.rsc.snpsht.verfilesystem.flag;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
public class AbstractVerFsFlag
implements jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFlag
{
protected java.util.Set files;

public void <init>()
{
this.<init>();
this.files = new java.util.HashSet();

}

public void addChild(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev child)
{
this.files.add(child);

}

public void addChildren(java.util.Collection children)
{
this.files.addAll(children);

}

public void addChildren(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[] children)
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev child : children) this.files.add(child);

}

public void removeChild(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev child)
{
this.files.remove(child);

}

public java.util.Set getChildren()
{
return this.files;

}


}
