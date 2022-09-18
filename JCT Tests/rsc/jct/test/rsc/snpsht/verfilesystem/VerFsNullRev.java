package jct.test.rsc.snpsht.verfilesystem;
import java.util.HashSet;
import java.util.Set;
public class VerFsNullRev
extends jct.test.rsc.snpsht.verfilesystem.VerFsFileRev
implements jct.test.rsc.snpsht.verfilesystem.IVerFsNullRevision
{
final public static java.lang.String FIRST_REV_DEFAULT_ID_NAME = "null_revision";

private jct.test.rsc.snpsht.verfilesystem.VerFsFileRev firstRev;

protected void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev firstRev)
{
this.<init>(firstRev, null);

}

protected void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev firstRev, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev prevRev)
{
this.<init>(null, null, null);
this.addNextRevision(firstRev);
this.setPrevRevision(prevRev);

}

public java.lang.String getId()
{
return jct.test.rsc.snpsht.verfilesystem.VerFsNullRev.FIRST_REV_DEFAULT_ID_NAME + "_" + this.getId();

}

public java.lang.String getName()
{
return jct.test.rsc.snpsht.verfilesystem.VerFsNullRev.FIRST_REV_DEFAULT_ID_NAME;

}

protected void addNextRevision(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextRev)
{
this.firstRev = nextRev;

}

public java.util.Set getNextRevisions()
{
java.util.Set nextRevs = new java.util.HashSet();
nextRevs.add(this.firstRev);
return nextRevs;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsFileRev getNextRevision()
{
return this.firstRev;

}


}
