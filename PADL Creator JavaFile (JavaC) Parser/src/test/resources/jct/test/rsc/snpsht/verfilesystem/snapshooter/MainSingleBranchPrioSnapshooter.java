package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
import jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState.FileState;
public class MainSingleBranchPrioSnapshooter
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
jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch highestPrio = null;
snapshot.setSnapshotPeriod(systemState.getPeriod());
snapshot.addCoveredCommit(this.getCommit(i));
prioFound: for(int k = this.branchesByPriority.size() - 1; k >= -1; k --) 
{
if(k >= 0) 
{
highestPrio = this.branchesByPriority.get(k);
for(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file : systemState.getFiles()) 
{
fileState = systemState.getFileState(file);
if(fileState.containsBranch(highestPrio)) 
{
break prioFound;

}

}

}
 else 
{
highestPrio = this.defaultBranch;

}

}
for(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file : systemState.getFiles()) 
{
fileState = systemState.getFileState(file);
if(fileState.containsBranch(highestPrio)) 
{
snapshot.addAll(fileState.getBranchState(highestPrio));

}

}
return snapshot;

}


}
