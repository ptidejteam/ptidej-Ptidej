package jct.test.rsc.snpsht.verfilesystem.flag;
import java.util.Date;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;
public class VerFsTrunk
extends jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch
implements jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch
{
final public static java.lang.String TRUNK_BRANCH_NAME = "<trunk>";

public void <init>()
{
this.<init>(null);

}

public java.lang.String getValue()
{
return jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk.TRUNK_BRANCH_NAME;

}

public jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime getCreationTime()
{
java.util.Date min = null;
java.util.Date curr;
if(this.creationTime != null) 
{
return this.creationTime;

}
 else 
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : this.getChildren()) 
{
curr = fileRev.getUpdateTime().getTime();
if(curr != null) 
{
if(min == null || min.compareTo(curr) > 0) 
{
min = curr;

}

}

}
return new jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime(min);

}

}


}
