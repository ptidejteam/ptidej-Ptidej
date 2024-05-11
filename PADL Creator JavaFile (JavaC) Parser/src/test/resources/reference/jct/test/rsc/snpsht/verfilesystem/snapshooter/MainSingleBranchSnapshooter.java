package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
import jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState.FileState;
public class MainSingleBranchSnapshooter
extends jct.test.rsc.snpsht.verfilesystem.snapshooter.AbstractMainSnapshooter
{
private jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch;

public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
this.<init>(manager);
this.branch = branch;

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
if(fileState.containsBranch(this.branch)) 
{
snapshot.addAll(fileState.getBranchState(this.branch));

}

}
return snapshot;

}


}
