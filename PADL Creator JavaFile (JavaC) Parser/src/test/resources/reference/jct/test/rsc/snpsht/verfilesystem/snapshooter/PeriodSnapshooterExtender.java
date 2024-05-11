package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.Date;
import jct.test.rsc.snpsht.utils.Pair;
public class PeriodSnapshooterExtender
extends jct.test.rsc.snpsht.verfilesystem.snapshooter.AbstractSnapshooterExtender
{
private jct.test.rsc.snpsht.utils.Pair period;

public void <init>(jct.test.rsc.snpsht.verfilesystem.snapshooter.ISnapshooter extendedSnapshooter)
{
this.<init>(extendedSnapshooter);
this.period = new jct.test.rsc.snpsht.utils.Pair();
this.setShotPeriod(extendedSnapshooter.getSnapshot(0).getSnapshotStartTime(), extendedSnapshooter.getSnapshot(extendedSnapshooter.getSnapshotCount() - 1).getSnapshotStartTime());

}

public void setShotPeriod(java.util.Date startTime, java.util.Date endTime) throws java.lang.IllegalArgumentException
{
jct.test.rsc.snpsht.utils.Pair bounds = this.getExtendedSnapshooter().getPeriodBounds(startTime, endTime);
if(bounds.car() > bounds.cdr()) throw new java.lang.IllegalArgumentException("Illegal period : start time > end time");
if(bounds.car() < 0) bounds.car(0);
if(bounds.car() > this.getExtendedSnapshooter().getSnapshotCount() - 1) bounds.car(this.getExtendedSnapshooter().getSnapshotCount() - 1);
if(bounds.cdr() > this.getExtendedSnapshooter().getSnapshotCount()) bounds.cdr(this.getExtendedSnapshooter().getSnapshotCount());
this.period.copy(bounds);

}

public jct.test.rsc.snpsht.utils.Pair getPeriodBounds(java.util.Date startTime, java.util.Date endTime) throws java.lang.IllegalArgumentException
{
int index;
jct.test.rsc.snpsht.utils.Pair bounds = this.getExtendedSnapshooter().getPeriodBounds(startTime, endTime);
index = bounds.car() - this.period.car();
bounds.car(java.lang.Math.min(java.lang.Math.max(-1, index), this.getSnapshotCount()));
index = bounds.cdr() - this.period.car();
bounds.cdr(java.lang.Math.min(java.lang.Math.max(-1, index), this.getSnapshotCount()));
return bounds;

}

public jct.test.rsc.snpsht.verfilesystem.snapshooter.Snapshot getSnapshot(int i) throws java.lang.IllegalArgumentException
{
if(i < 0 || i >= this.getSnapshotCount()) throw new java.lang.IllegalArgumentException("Index out of bounds");
return this.getExtendedSnapshooter().getSnapshot(i + this.period.car());

}

public int getSnapshotCount()
{
return this.period.cdr() - this.period.car();

}

public int getSnapshotIndex(java.util.Date d)
{
int index = this.getExtendedSnapshooter().getSnapshotIndex(d) - this.period.car();
return java.lang.Math.min(java.lang.Math.max(-1, index), this.getSnapshotCount());

}


}
