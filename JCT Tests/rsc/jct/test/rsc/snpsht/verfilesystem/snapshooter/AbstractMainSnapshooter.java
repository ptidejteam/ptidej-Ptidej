package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.Arrays;
import java.util.Date;
import javax.swing.event.EventListenerList;
import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.comparator.VerFsCommitStartTimeComparator;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
import jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState.FileState;
abstract public class AbstractMainSnapshooter
implements jct.test.rsc.snpsht.verfilesystem.snapshooter.IMainSnapshooter
{
private jct.test.rsc.snpsht.verfilesystem.VerFsManager manager;

private javax.swing.event.EventListenerList listeners;

private jct.test.rsc.snpsht.utils.Pair[] systemStates;

public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>();
this.manager = manager;
this.listeners = new javax.swing.event.EventListenerList();
this.initCommitsMap();

}

protected void initCommitsMap()
{
jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit[] commits = this.manager.getAllCommits().toArray(new jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit[0]);
java.util.Arrays.sort(commits, new jct.test.rsc.snpsht.verfilesystem.comparator.VerFsCommitStartTimeComparator());
this.systemStates = new jct.test.rsc.snpsht.utils.Pair[commits.length];
for(int i = 0; i < commits.length; i ++) 
{
this.systemStates[i] = new jct.test.rsc.snpsht.utils.Pair(commits[i]);

}

}

public void setManager(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.manager = manager;
this.initCommitsMap();

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager getManager()
{
return this.manager;

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit getCommit(int i)
{
return this.systemStates[i].car();

}

public int getSnapshotIndex(java.util.Date d)
{
int i = -1;
jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit currCommit;
for(jct.test.rsc.snpsht.utils.Pair pair : this.systemStates) 
{
currCommit = pair.car();
if(currCommit.getCommittingTime().getTime().compareTo(d) <= 0) i ++;
 else break;

}
return i;

}

public jct.test.rsc.snpsht.utils.Pair getPeriodBounds(java.util.Date startTime, java.util.Date endTime) throws java.lang.IllegalArgumentException
{
int startIndex = -1;
int endIndex = 0;
jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit currCommit;
if(endTime.compareTo(startTime) <= 0) 
{
throw new java.lang.IllegalArgumentException("Illegal period : start time >= end time");

}
for(jct.test.rsc.snpsht.utils.Pair pair : this.systemStates) 
{
currCommit = pair.car();
if(currCommit.getCommittingTime().getTime().compareTo(startTime) <= 0) startIndex ++;
if(currCommit.getCommittingTime().getTime().compareTo(endTime) < 0) endIndex ++;
 else break;

}
return new jct.test.rsc.snpsht.utils.Pair(startIndex, endIndex);

}

private jct.test.rsc.snpsht.utils.Pair getSystemStatePeriodValidity(int index) throws java.lang.IllegalArgumentException
{
jct.test.rsc.snpsht.utils.Pair period = new jct.test.rsc.snpsht.utils.Pair();
if(index < 0 || index >= this.getSnapshotCount()) 
{
throw new java.lang.IllegalArgumentException("Index out of bounds");

}
for(int i = 0; i <= index && i < this.getSnapshotCount(); i ++) 
{
if(period.cdr() == null || this.getCommit(i).getCommittingTime().getTime().compareTo(period.cdr()) >= 0) 
{
period.car(this.getCommit(i).getCommittingTime().getTime());

}
 else 
{
period.car(new java.util.Date(period.cdr().getTime()));

}
if(i < this.getSnapshotCount() - 1) 
{
if(this.getCommit(i + 1).getCommittingTime().getTime().compareTo(period.car()) > 0) 
{
period.cdr(this.getCommit(i + 1).getCommittingTime().getTime());

}
 else 
{
period.cdr(new java.util.Date(period.car().getTime() + 1));
period.cdr(this.getCommit(i + 1).getCommittingTime().getTime());

}

}
 else 
{
period.cdr(jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot.INFINITE_TIME);

}

}
return period;

}

private jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState generateNextSystemState(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit nextCommit, jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState currSystemSate)
{
jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState snapshotContent = new jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState();
this.FileState fileContent;
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision fileRev : nextCommit.getChildren()) 
{
snapshotContent.addFileIfNotExist(fileRev.getFile());
fileContent = snapshotContent.getFileState(fileRev.getFile());
fileContent.addBranchIfNotExist(fileRev.getBranch());
fileContent.addRevision(fileRev);

}
for(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file : currSystemSate.getFiles()) 
{
if(! snapshotContent.containsFile(file)) 
{
snapshotContent.setFileSate(file, currSystemSate.getFileState(file));

}
 else 
{
fileContent = snapshotContent.getFileState(file);
for(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch : currSystemSate.getFileState(file).getBranches()) 
{
if(! fileContent.containsBranch(branch)) 
{
fileContent.setBranchSate(branch, currSystemSate.getFileState(file).getBranchState(branch));

}

}

}

}
return snapshotContent;

}

protected jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState getSystemSate(int i)
{
jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState prevSs;
jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState currSs;
if(i < 0 || i > this.systemStates.length - 1) return null;
currSs = this.systemStates[i].cdr();
if(currSs != null) return currSs;
if(i == 0) 
{
currSs = this.generateNextSystemState(this.systemStates[i].car(), new jct.test.rsc.snpsht.verfilesystem.snapshooter.SystemState());

}
 else 
{
prevSs = this.getSystemSate(i - 1);
currSs = this.generateNextSystemState(this.systemStates[i].car(), prevSs);

}
currSs.setPeriod(this.getSystemStatePeriodValidity(i));
this.systemStates[i].cdr(currSs);
return currSs;

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot getSnapshot(java.util.Date at)
{
return this.getSnapshot(this.getSnapshotIndex(at));

}

public int getSnapshotCount()
{
return this.systemStates.length;

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotIterator iterator()
{
return new jct.test.rsc.snpsht.verfilesystem.snapshooter.SnapshotIterator(this);

}

public void addListener(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener listener)
{
this.listeners.add(class, listener);

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener[] getListeners()
{
return this.listeners.getListeners(class);

}

public void removeListener(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooterListener listener)
{
this.listeners.remove(class, listener);

}


}
