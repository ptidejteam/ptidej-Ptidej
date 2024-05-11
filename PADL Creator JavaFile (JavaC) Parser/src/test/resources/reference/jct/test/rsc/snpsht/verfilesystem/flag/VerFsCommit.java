package jct.test.rsc.snpsht.verfilesystem.flag;
import java.util.Date;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;
public class VerFsCommit
extends jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag
implements jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit
{
public void <init>()
{
this.<init>();

}

public jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime committingTime;

public jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime getCommittingTime()
{
if(this.committingTime != null) 
{
return this.committingTime;

}
 else 
{
java.util.Date min = null;
java.util.Date max = null;
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev child : this.getChildren()) 
{
if(min == null || child.getUpdateTime().getTime().compareTo(min) < 0) 
{
min = child.getUpdateTime().getTime();

}
if(max == null || child.getUpdateTime().getTime().compareTo(max) > 0) 
{
max = child.getUpdateTime().getTime();

}

}
return new jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod(min, max);

}

}

public void setCommittingTime(java.util.Date d)
{
this.setCommittingTime(new jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime(d));

}

public void setCommittingTime(jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime committingTime)
{
this.committingTime = committingTime;

}


}
