package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
public class Snapshot
implements java.lang.Comparable
{
final public static java.util.Date INFINITE_TIME = new java.util.Date(java.lang.Long.MAX_VALUE);

private java.util.Set systemState;

private jct.test.rsc.snpsht.utils.Pair snapshotPeriod;

private java.util.List coveredCommit;

public void <init>()
{
this.<init>(new java.util.HashSet());

}

public void <init>(java.util.Set systemState)
{
this.<init>();
this.systemState = systemState;
this.snapshotPeriod = new jct.test.rsc.snpsht.utils.Pair();
this.coveredCommit = new java.util.ArrayList();

}

public void <init>(java.util.Set systemState, jct.test.rsc.snpsht.utils.Pair snapshotPeriod)
{
this.<init>(systemState);
this.setSnapshotPeriod(snapshotPeriod);

}

public void setSnapshotStartTime(java.util.Date startTime)
{
this.snapshotPeriod.car(startTime);

}

public java.util.Date getSnapshotStartTime()
{
return this.snapshotPeriod.car();

}

public void setSnapshotEndTime(java.util.Date endTime)
{
this.snapshotPeriod.cdr(endTime);

}

public java.util.Date getSnapshotEndTime()
{
return this.snapshotPeriod.cdr();

}

public jct.test.rsc.snpsht.utils.Pair getSnapshotPeriod()
{
return this.snapshotPeriod;

}

public void setSnapshotPeriod(java.util.Date startTime, java.util.Date endTime)
{
this.snapshotPeriod.car(startTime);
this.snapshotPeriod.cdr(endTime);

}

public void setSnapshotPeriod(jct.test.rsc.snpsht.utils.Pair snapshotPeriod)
{
this.snapshotPeriod.copy(snapshotPeriod);

}

public java.util.Set getFilesRev()
{
return this.systemState;

}

public java.util.Set getFilesRev(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file)
{
java.util.Set toRet = new java.util.HashSet();
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev : this.systemState) 
{
if(rev.getFile() == file) toRet.add(rev);

}
return toRet;

}

public java.util.Set getFiles()
{
java.util.Set toRet = new java.util.HashSet();
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev : this.systemState) 
{
toRet.add(rev.getFile());

}
return toRet;

}

public void addCoveredCommit(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit commit)
{
this.coveredCommit.add(commit);

}

public void addAllCoveredCommits(java.util.Collection commits)
{
this.coveredCommit.addAll(commits);

}

public java.util.List getCoveredCommits()
{
return this.coveredCommit;

}

public void add(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision revision)
{
this.systemState.add(revision);

}

public void addAll(java.util.Collection revisions)
{
this.systemState.addAll(revisions);

}

public boolean containsFile(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file)
{
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev : this.systemState) 
{
if(rev.getFile() == file) return true;

}
return false;

}

public void generateDataIn(java.io.File targetDir)
{

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotDiff filesRevDiff(jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot from)
{
java.util.Set added = new java.util.HashSet();
java.util.Set removed = new java.util.HashSet();
added.addAll(this.getFilesRev());
added.removeAll(from.getFilesRev());
removed.addAll(from.getFilesRev());
removed.removeAll(this.getFilesRev());
return new jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotDiff(added, removed);

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotDiff filesDiff(jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot from)
{
java.util.Set added = new java.util.HashSet();
java.util.Set removed = new java.util.HashSet();
added.addAll(this.getFiles());
added.removeAll(from.getFiles());
removed.addAll(from.getFiles());
removed.removeAll(this.getFiles());
return new jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotDiff(added, removed);

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotDiff dataDiff(jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot from)
{
java.util.Set added = new java.util.HashSet();
java.util.Set removed = new java.util.HashSet();
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev : this.getFilesRev()) 
{
added.add(rev.getFileRevLocation());

}
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev : from.getFilesRev()) 
{
added.remove(rev.getFileRevLocation());

}
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev : from.getFilesRev()) 
{
removed.add(rev.getFileRevLocation());

}
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev : this.getFilesRev()) 
{
removed.remove(rev.getFileRevLocation());

}
return new jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotDiff(added, removed);

}

public int compareTo(jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot o)
{
int result;
result = this.getSnapshotStartTime().compareTo(o.getSnapshotStartTime());
if(result != 0) return result;
result = this.getSnapshotEndTime().compareTo(o.getSnapshotEndTime());
if(result != 0) return result;
result = this.getCoveredCommits().size() - o.getCoveredCommits().size();
if(result != 0) return result;
for(int i = 0; i < this.getCoveredCommits().size(); i ++) 
{
if(this.getCoveredCommits().get(i) != o.getCoveredCommits().get(i)) 
{
result = this.getCoveredCommits().get(i).getCommittingTime().getTime().compareTo(o.getCoveredCommits().get(i).getCommittingTime().getTime());
if(result != 0) return result;
 else return -1;

}

}
result = this.getFilesRev().size() - o.getFilesRev().size();
if(result != 0) return result;
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev : this.getFilesRev()) 
{
if(! o.getFilesRev().contains(rev)) 
{
return 1;

}

}
return 0;

}


}
