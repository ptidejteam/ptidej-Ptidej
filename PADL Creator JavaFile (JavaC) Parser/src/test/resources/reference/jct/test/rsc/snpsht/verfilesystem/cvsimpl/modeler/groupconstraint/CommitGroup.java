package jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
public class CommitGroup
implements jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup
{
private java.util.Set group;

public void <init>()
{
this.<init>();
this.group = new java.util.HashSet();

}

public void <init>(java.util.Set group)
{
this.<init>();
this.group = group;

}

public void add(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev)
{
this.group.add(fileRev);

}

public java.util.Set getGroup()
{
return this.group;

}


}
