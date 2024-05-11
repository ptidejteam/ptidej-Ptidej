package jct.test.rsc.snpsht.verfilesystem.snapshooter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jct.test.rsc.snpsht.utils.Pair;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile;
public class SystemState
{
private java.util.Map systemSate;

private jct.test.rsc.snpsht.utils.Pair period;

public void <init>()
{
this.<init>();
this.systemSate = new java.util.HashMap();

}

public java.util.Map getState()
{
return this.systemSate;

}

public void setPeriod(jct.test.rsc.snpsht.utils.Pair period)
{
this.period = period;

}

public jct.test.rsc.snpsht.utils.Pair getPeriod()
{
return this.period;

}

public boolean containsFile(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file)
{
return this.systemSate.containsKey(file);

}

public boolean containsFile(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev)
{
if(rev != null) 
{
return this.containsFile(rev.getFile());

}
 else 
{
return false;

}

}

public void addFile(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file)
{
this.systemSate.put(file, new this.FileState());

}

public boolean addFileIfNotExist(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file)
{
if(! this.containsFile(file)) 
{
this.addFile(file);
return true;

}
 else 
{
return false;

}

}

public void setFileSate(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file, this.FileState state)
{
this.systemSate.put(file, state);

}

public this.FileState getFileState(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsFile file)
{
return this.systemSate.get(file);

}

public java.util.Set getFiles()
{
return this.systemSate.keySet();

}

public class FileState
{
private java.util.Map fileState;

public void <init>()
{
this.<init>();
this.fileState = new java.util.HashMap();

}

public java.util.Map getFileSate()
{
return this.fileState;

}

public boolean containsBranch(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
return this.fileState.containsKey(branch);

}

public void addBranch(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
this.fileState.put(branch, new java.util.ArrayList());

}

public boolean addBranchIfNotExist(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
if(! this.containsBranch(branch)) 
{
this.addBranch(branch);
return true;

}
 else 
{
return false;

}

}

public void addRevision(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev)
{
this.addRevision(rev, rev.getBranch());

}

public void addRevision(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision rev, jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
if(! this.containsBranch(branch)) 
{
throw new java.lang.IllegalArgumentException("Unknow branch " + branch.getValue());

}
 else 
{
this.fileState.get(branch).add(rev);

}

}

public java.util.Set getBranches()
{
return this.fileState.keySet();

}

public java.util.List getBranchState(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
return this.fileState.get(branch);

}

public void setBranchSate(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch, java.util.List state)
{
this.fileState.put(branch, state);

}


}


}
