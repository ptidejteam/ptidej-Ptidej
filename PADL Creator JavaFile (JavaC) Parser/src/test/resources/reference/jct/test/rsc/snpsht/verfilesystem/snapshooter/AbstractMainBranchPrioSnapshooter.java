package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.LinkedList;
import java.util.List;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
abstract public class AbstractMainBranchPrioSnapshooter
extends jct.test.rsc.snpsht.verfilesystem.snapshooter.AbstractMainSnapshooter
{
protected java.util.LinkedList branchesByPriority;

protected jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch defaultBranch;

public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(manager, manager.getTrunk());

}

public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch defaultBranch)
{
this.<init>(manager);
this.branchesByPriority = new java.util.LinkedList();
this.defaultBranch = defaultBranch;

}

public void setBranchPriority(java.util.List branchesByPriority)
{
this.branchesByPriority.clear();
this.branchesByPriority.addAll(branchesByPriority);

}

public void addBranchHighestPriority(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
this.branchesByPriority.addLast(branch);

}

public void addBranchLowestPriority(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
this.branchesByPriority.addFirst(branch);

}

public java.util.List getBranchPriority()
{
return this.branchesByPriority;

}


}
