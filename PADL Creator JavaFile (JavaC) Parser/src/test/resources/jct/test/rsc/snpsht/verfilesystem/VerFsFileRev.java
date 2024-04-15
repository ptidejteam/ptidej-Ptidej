package jct.test.rsc.snpsht.verfilesystem;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag;
import jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction;
public class VerFsFileRev
extends jct.test.rsc.snpsht.verfilesystem.AbstractVerFsElement
implements jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision
{
private java.lang.String id;

private jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment comment;

private jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction action;

private jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor author;

private jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch;

private jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime updateDate;

private jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName fileName;

private jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID revID;

private java.util.Set tags;

private jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit commit;

private jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file;

private jct.test.rsc.snpsht.filesystem.IFsFileEntity fileRevLocation;

private jct.test.rsc.snpsht.verfilesystem.VerFsFileRev prevRevision;

private java.util.Set nextRevisions;

protected void <init>(java.lang.String id, java.lang.String name, jct.test.rsc.snpsht.verfilesystem.VerFsRepository parent)
{
this.<init>(name, parent);
this.id = id;
this.tags = new java.util.HashSet();
this.nextRevisions = new java.util.HashSet();

}

public java.lang.String getId()
{
return this.id;

}

public java.lang.String prettyPrint()
{
return "+-" + this.name + " (" + this.id + ")";

}

protected void setFileRevLocation(jct.test.rsc.snpsht.filesystem.IFsFileEntity fileRev)
{
this.fileRevLocation = fileRev;

}

public jct.test.rsc.snpsht.filesystem.IFsFileEntity getFileRevLocation()
{
return this.fileRevLocation;

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment getComment()
{
return this.comment;

}

protected void setComment(jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment comment)
{
this.comment = comment;

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor getAuthor()
{
return this.author;

}

protected void setAuthor(jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor author)
{
this.author = author;

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction getAction()
{
return this.action;

}

protected void setAction(jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction action)
{
this.action = action;

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch getBranch()
{
return this.branch;

}

protected void setBranch(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch)
{
this.branch = branch;

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName getFileName()
{
return this.fileName;

}

protected void setFileName(jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName fileName)
{
this.fileName = fileName;

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID getRevID()
{
return this.revID;

}

protected void setRevID(jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID revID)
{
this.revID = revID;

}

public java.util.Set getTags()
{
return this.tags;

}

protected void addTag(jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag tag)
{
this.tags.add(tag);

}

public jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit getCommit()
{
return this.commit;

}

public void setCommit(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsCommit commit)
{
this.commit = commit;

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile getFile()
{
return file;

}

protected void setFile(jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file)
{
this.file = file;

}

public jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime getUpdateTime()
{
return (this.updateDate == null ? this.compUpdatePeriod() : this.updateDate);

}

private jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod compUpdatePeriod()
{
return new jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdatePeriod(this.getFileRevMinDate(), this.getFileRevMaxDate(), false);

}

private java.util.Date getFileRevMinDate()
{
return this.getFileRevMinDate(this);

}

private java.util.Date getFileRevMinDate(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev)
{
if(this.getPrevRevision() == null || this.getPrevRevision() instanceof jct.test.rsc.snpsht.verfilesystem.IVerFsNullRevision) 
{
return null;

}
 else 
{
if(this.getUpdateTime() == null) 
{
return this.getFileRevMinDate(this.getPrevRevision());

}
 else 
{
return new java.util.Date(this.getUpdateTime().getTime().getTime() + 1);

}

}

}

private java.util.Date getFileRevMaxDate()
{
return this.getFileRevMaxDate(this);

}

private java.util.Date getFileRevMaxDate(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev)
{
java.util.Date min = null;
java.util.Date curr;
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextRev : this.getNextRevisions()) 
{
if(this.getUpdateTime() == null) 
{
curr = this.getFileRevMaxDate(nextRev);

}
 else 
{
curr = this.getUpdateTime().getTime();

}
if(curr != null && (min == null || curr.compareTo(min) < 0)) 
{
min = curr;

}

}
return (min == null ? null : new java.util.Date(min.getTime() - 1));

}

public void setUpdateTime(jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime date)
{
this.updateDate = date;

}

public java.util.Set getNextRevisions()
{
return this.nextRevisions;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsFileRev getPrevRevision()
{
return this.prevRevision;

}

protected void setPrevRevision(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev prevRevision)
{
this.prevRevision = prevRevision;

}

protected void addNextRevision(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextRevision)
{
this.nextRevisions.add(nextRevision);

}

protected void removeNextRevision(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextRevision)
{
this.nextRevisions.remove(nextRevision);

}


}
