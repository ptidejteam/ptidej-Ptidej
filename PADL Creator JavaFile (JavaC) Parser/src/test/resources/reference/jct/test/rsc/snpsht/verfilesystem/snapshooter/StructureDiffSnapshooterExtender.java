package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.Date;
import java.util.LinkedList;
import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
public class StructureDiffSnapshooterExtender
extends jct.test.rsc.snpsht.verfilesystem.snapshooter.AbstractSnapshooterExtender
{
private int[] diffList;

public void <init>(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter extendedSnapshooter)
{
this.<init>(extendedSnapshooter);
this.init();

}

private void init()
{
jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotDiff diff;
java.util.LinkedList diffList = new java.util.LinkedList();
int i = 0;
for(i = 0; i < this.getExtendedSnapshooter().getSnapshotCount(); i ++) 
{
if(this.getExtendedSnapshooter().getSnapshot(i).getFilesRev().size() != 0) 
{
diffList.addLast(i);
break;

}

}
for(i ++; i < this.getExtendedSnapshooter().getSnapshotCount(); i ++) 
{
diff = this.getExtendedSnapshooter().getSnapshot(i).filesRevDiff(this.getExtendedSnapshooter().getSnapshot(diffList.getLast()));
if(diff.isDiff()) 
{
diffList.addLast(i);

}

}
this.diffList = new int[diffList.size()];
for(i = 0; i < diffList.size(); i ++) this.diffList[i] = diffList.get(i);

}

public jct.test.rsc.snpsht.utils.Pair getPeriodBounds(java.util.Date startTime, java.util.Date endTime) throws java.lang.IllegalArgumentException
{
if(endTime.compareTo(startTime) <= 0) 
{
throw new java.lang.IllegalArgumentException("Illegal period : start time >= end time");

}
jct.test.rsc.snpsht.utils.Pair boundsExt = this.getExtendedSnapshooter().getPeriodBounds(startTime, endTime);
jct.test.rsc.snpsht.utils.Pair bounds = new jct.test.rsc.snpsht.utils.Pair(-1, 0);
for(int currIndex : this.diffList) 
{
if(boundsExt.car() >= currIndex) bounds.car(bounds.car() + 1);
if(boundsExt.cdr() >= currIndex) bounds.cdr(bounds.cdr() + 1);
 else return bounds;

}
return bounds;

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot getSnapshot(int i)
{
if(i < 0 || i >= this.getSnapshotCount()) throw new java.lang.IllegalArgumentException("Index out of bounds");
jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot sS = this.getExtendedSnapshooter().getSnapshot(this.diffList[i]);
if(i < this.getSnapshotCount() - 1) 
{
sS.setSnapshotEndTime(this.getExtendedSnapshooter().getSnapshot(this.diffList[i + 1]).getSnapshotStartTime());
for(int j = this.diffList[i] + 1; j < this.diffList[i + 1]; j ++) 
{
sS.addAllCoveredCommits(this.getExtendedSnapshooter().getSnapshot(j).getCoveredCommits());

}

}
 else 
{
sS.setSnapshotEndTime(jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot.INFINITE_TIME);
for(int j = this.diffList[i] + 1; j < this.getExtendedSnapshooter().getSnapshotCount(); j ++) 
{
sS.addAllCoveredCommits(this.getExtendedSnapshooter().getSnapshot(j).getCoveredCommits());

}

}
return sS;

}

public int getSnapshotCount()
{
return this.diffList.length;

}

public int getSnapshotIndex(java.util.Date d)
{
int i = this.getExtendedSnapshooter().getSnapshotIndex(d);
int index = -1;
for(int currIndex : this.diffList) 
{
if(i >= currIndex) index ++;
 else return index;

}
return index;

}


}
