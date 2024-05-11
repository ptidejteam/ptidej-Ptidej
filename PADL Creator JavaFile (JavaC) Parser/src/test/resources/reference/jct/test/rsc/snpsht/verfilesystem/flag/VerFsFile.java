package jct.test.rsc.snpsht.verfilesystem.flag;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsNullRev;
public class VerFsFile
extends jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag
implements jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile
{
private java.lang.String id;

private jct.test.rsc.snpsht.verfilesystem.VerFsNullRev nullRev;

private jct.test.rsc.snpsht.filesystem.IFsFileEntity fileLocation;

public void <init>(java.lang.String id, jct.test.rsc.snpsht.verfilesystem.VerFsNullRev nullRev, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[] children)
{
this.<init>();
this.id = id;
this.nullRev = nullRev;
this.addChildren(children);

}

public void setFileLocation(jct.test.rsc.snpsht.filesystem.IFsFileEntity fileLocation)
{
this.fileLocation = fileLocation;

}

public jct.test.rsc.snpsht.filesystem.IFsFileEntity getFileLocation()
{
return this.fileLocation;

}

public java.lang.String getId()
{
return this.id;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsFileRev getFirstRev()
{
return this.nullRev.getNextRevision();

}

public jct.test.rsc.snpsht.verfilesystem.VerFsNullRev getNullRevision()
{
return this.nullRev;

}


}
