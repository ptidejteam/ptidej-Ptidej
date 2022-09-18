package jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator;
public class SlidingWindowConstraint
implements jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroupingConstraint
{
final public static double SLIDING_WINDOWS_DEFAULT_SIZE_SEC = 5.0 * 60.0;

private double slidingSizeMilSec;

public void <init>()
{
this.<init>(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SlidingWindowConstraint.SLIDING_WINDOWS_DEFAULT_SIZE_SEC);

}

public void <init>(double slidingSizeSec)
{
this.<init>();
this.slidingSizeMilSec = slidingSizeSec * 1000.0;

}

public java.util.Set applyContrainst(java.util.Set groups)
{
java.util.Set outGroups = new java.util.HashSet();
for(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup group : groups) 
{
outGroups.addAll(this.applyConstraint(group));

}
return outGroups;

}

private java.util.Set applyConstraint(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup group)
{
jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.IGroup currGroup;
java.util.Date currTime = null;
java.util.Set groups = new java.util.HashSet();
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[] files = group.getGroup().toArray(new jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[0]);
java.util.Arrays.sort(files, new jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator());
if(files.length > 0) 
{
currGroup = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.CommitGroup();
groups.add(currGroup);
currTime = files[0].getUpdateTime().getTime();
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : files) 
{
if(fileRev.getUpdateTime().getTime().getTime() - currTime.getTime() > this.slidingSizeMilSec) 
{
currGroup = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.CommitGroup();
groups.add(currGroup);

}
currGroup.add(fileRev);
currTime = fileRev.getUpdateTime().getTime();

}

}
 else 
{
groups.add(group);

}
return groups;

}

public java.lang.String[][] getAttributes()
{
java.lang.Double sizeSec = this.slidingSizeMilSec / 1000.0;
return new java.lang.String[][] { [] { "size", sizeSec.toString() } };

}

public java.lang.String getDescription()
{
return "Sliding windows grouping constraint";

}

public java.lang.String getName()
{
return "slidingWindows";

}


}
