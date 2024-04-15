package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
import jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState.FileState;
public class MainMultiBranchPrioSnapshooter
extends jct.test.rsc.snpsht.verfilesystem.snapshooter.AbstractMainBranchPrioSnapshooter
{
public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(manager);

}

public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch defaultBranch)
{
this.<init>(manager, defaultBranch);

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot getSnapshot(int i)
{
if(i < 0 || i >= this.getSnapshotCount()) throw new java.lang.IllegalArgumentException("Index out of bounds");
jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState systemState = this.getSystemSate(i);
this.FileState fileState;
jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot snapshot = new jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot();
snapshot.setSnapshotPeriod(systemState.getPeriod());
snapshot.addCoveredCommit(this.getCommit(i));
for(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file : systemState.getFiles()) 
{
fileState = systemState.getFileState(file);
for(int j = this.branchesByPriority.size() - 1; j >= -1; j --) 
{
if(j >= 0) 
{
if(fileState.containsBranch(this.branchesByPriority.get(j))) 
{
snapshot.addAll(fileState.getBranchState(this.branchesByPriority.get(j)));
break;

}

}
 else 
{
if(fileState.containsBranch(this.defaultBranch)) 
{
snapshot.addAll(fileState.getBranchState(this.defaultBranch));

}

}

}

}
return snapshot;

}


}
