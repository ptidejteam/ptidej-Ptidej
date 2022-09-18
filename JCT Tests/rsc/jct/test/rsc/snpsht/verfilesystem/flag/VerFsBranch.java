package jct.test.rsc.snpsht.verfilesystem.flag;
import java.util.Date;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;
public class VerFsBranch
extends jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag
implements jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch
{
private java.lang.String branch;

protected jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime creationTime;

public void <init>(java.lang.String branch)
{
this.<init>();
this.branch = branch;

}

public java.lang.String getValue()
{
return this.branch;

}

public jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime getCreationTime()
{
java.util.Date min = null;
java.util.Date max = null;
java.util.Date curr;
jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod currRevPeriod;
if(this.creationTime != null) 
{
return this.creationTime;

}
 else 
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : this.getChildren()) 
{
if(! (fileRev.getAction() instanceof jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction)) continue;
currRevPeriod = (jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod)fileRev.getUpdateTime();
curr = currRevPeriod.getStartTime();
if(curr != null) 
{
if(min == null || min.compareTo(curr) < 0) 
{
min = curr;

}

}
curr = currRevPeriod.getEndTime();
if(curr != null) 
{
if(max == null || max.compareTo(curr) > 0) 
{
max = curr;

}

}

}
return new jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod(min, max, false);

}

}

public void setCreationTime(java.util.Date creationTime)
{
this.setCreationTime(new jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime(creationTime));

}

public void setCreationTime(jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime creationTime)
{
this.creationTime = creationTime;

}


}
