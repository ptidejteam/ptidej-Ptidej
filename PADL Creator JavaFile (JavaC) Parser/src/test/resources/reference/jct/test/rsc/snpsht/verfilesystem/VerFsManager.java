package jct.test.rsc.snpsht.verfilesystem;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import jct.test.rsc.snpsht.filesystem.FsManagerFactory;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsManager;
import jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.visitor.FindOrMakeFileRev;
import jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk;
import jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsEditAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsMoveAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsResurrectAction;
public class VerFsManager
implements jct.test.rsc.snpsht.verfilesystem.IVerFsManager
{
private jct.test.rsc.snpsht.filesystem.IFsManager fileManager;

private jct.test.rsc.snpsht.verfilesystem.VerFsRootRepository root;

private java.lang.String revmlVersion;

private java.lang.String description;

private java.util.Date updateDate;

private java.util.Map filesRevMap;

private java.util.Map fileMap;

private java.util.Map commentMap;

private java.util.Map authorMap;

private java.util.Map branchMap;

private java.util.Map fileNameMap;

private java.util.Map revIDMap;

private java.util.Map tagsMap;

private jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction[] actions;

private java.util.Set commitSet;

public void <init>()
{
this.<init>();
this.filesRevMap = new java.util.HashMap();
this.fileMap = new java.util.HashMap();
this.commentMap = new java.util.HashMap();
this.authorMap = new java.util.HashMap();
this.branchMap = new java.util.HashMap();
this.fileNameMap = new java.util.HashMap();
this.revIDMap = new java.util.HashMap();
this.tagsMap = new java.util.HashMap();
this.commitSet = new java.util.HashSet();

}

public jct.test.rsc.snpsht.verfilesystem.VerFsRootRepository makeRoot(java.lang.String repositoryPath)
{
java.lang.String repoName;
this.filesRevMap.clear();
this.commentMap.clear();
this.authorMap.clear();
this.branchMap.clear();
this.fileNameMap.clear();
this.revIDMap.clear();
this.tagsMap.clear();
this.actions = new jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction[] { new jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction(), new jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction(), new jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction(), new jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsEditAction(), new jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsMoveAction(), new jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsResurrectAction() };
this.branchMap.put(jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk.TRUNK_BRANCH_NAME, new jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk());
this.commitSet.clear();
if(repositoryPath.endsWith("/")) 
{
repoName = repositoryPath.substring(0, repositoryPath.length() - 1);

}
 else 
{
repoName = repositoryPath;

}
if(repositoryPath.startsWith("/")) 
{
repoName = repositoryPath.substring(1);

}
 else 
{

}
this.root = new jct.test.rsc.snpsht.verfilesystem.VerFsRootRepository(repoName);
return this.root;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsRootRepository getRoot()
{
return this.root;

}

public java.lang.String getRevmlVersion()
{
return this.revmlVersion;

}

public void setRevmlVersion(java.lang.String revmlVersion)
{
this.revmlVersion = revmlVersion;

}

public java.lang.String getDescription()
{
return this.description;

}

public void setDescription(java.lang.String description)
{
this.description = description;

}

public java.util.Date getUpdateDate()
{
return this.updateDate;

}

public void setUpdateDate(java.util.Date updateDate)
{
this.updateDate = updateDate;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsFileRev getElement(java.lang.String path)
{
return null;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsFileRev getSimpleRevision(java.lang.String id)
{
return this.filesRevMap.get(id);

}

public java.util.Collection getAllSimplesRevisions()
{
return this.filesRevMap.values();

}

public java.lang.String toFullPath(java.lang.String path)
{
if(! path.startsWith("/")) 
{
return "/" + this.root.getName() + "/" + path;

}
 else 
{
return path;

}

}

public java.lang.String toRelativePath(java.lang.String path)
{
java.lang.String rootPath;
if(! path.startsWith("/")) 
{
return path;

}
 else 
{
rootPath = "/" + this.root.getName() + "/";
if(path.startsWith(rootPath) && path.length() > rootPath.length()) 
{
return path.substring(("/" + this.root.getName() + "/").length());

}
 else 
{
return null;

}

}

}

public boolean isValidPath(java.lang.String path)
{
java.lang.String fullPath = this.toFullPath(path);
java.lang.String[] pathTokens = fullPath.split("/");
if(pathTokens.length > 2 && pathTokens[0].compareTo("") == 0 && pathTokens[1].compareTo(this.root.getName()) == 0 && pathTokens[2].compareTo("") != 0) 
{
return true;

}
 else 
{
return false;

}

}

public jct.test.rsc.snpsht.verfilesystem.VerFsRepository addComplexElement(jct.test.rsc.snpsht.verfilesystem.VerFsRepository parent, java.lang.String name)
{
jct.test.rsc.snpsht.verfilesystem.VerFsRepository newRep;
if(parent == null || name.compareTo("") == 0 || parent.containsChildId(name)) 
{
return null;

}
 else 
{
newRep = new jct.test.rsc.snpsht.verfilesystem.VerFsRepository(name, parent);
parent.addChild(newRep);
return newRep;

}

}

public jct.test.rsc.snpsht.verfilesystem.VerFsFileRev addSimpleRevision(jct.test.rsc.snpsht.verfilesystem.VerFsRepository parent, java.lang.String name, java.lang.String id)
{
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev newFile;
if(parent == null || name.compareTo("") == 0 || this.filesRevMap.containsKey(id)) 
{
return null;

}
 else 
{
newFile = new jct.test.rsc.snpsht.verfilesystem.VerFsFileRev(id, name, parent);
parent.addChild(newFile);
this.filesRevMap.put(id, newFile);
return newFile;

}

}

public jct.test.rsc.snpsht.verfilesystem.VerFsFileRev addSimpleRevision(java.lang.String path, java.lang.String id)
{
jct.test.rsc.snpsht.verfilesystem.IVerFsElement newElem;
jct.test.rsc.snpsht.verfilesystem.cvsimpl.visitor.FindOrMakeFileRev visitor = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.visitor.FindOrMakeFileRev(this);
newElem = visitor.findOrCreate(this.toRelativePath(path), id);
if(newElem == null) 
{
return null;

}
 else 
{
return (jct.test.rsc.snpsht.verfilesystem.VerFsFileRev)newElem;

}

}

private void removeFileFromFlag(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file, jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag flag)
{
if(file != null && flag != null) 
{
flag.removeChild(file);

}

}

public void setFileRevLocation(jct.test.rsc.snpsht.filesystem.IFsFileEntity fileLocation, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
file.setFileRevLocation(fileLocation);

}

public void setFileLocation(jct.test.rsc.snpsht.filesystem.IFsFileEntity fileLocation, jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file)
{
file.setFileLocation(fileLocation);

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment setComment(java.lang.String commentString, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.removeFileFromFlag(file, file.getComment());
jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment comment = this.commentMap.get(commentString);
if(comment == null) 
{
comment = new jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment(commentString);
this.commentMap.put(commentString, comment);

}
comment.addChild(file);
file.setComment(comment);
return comment;

}

public java.util.Collection getAllComments()
{
return this.commentMap.values();

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsComment getComment(java.lang.String commentString)
{
return this.commentMap.get(commentString);

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction setAction(java.lang.String actionString, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.removeFileFromFlag(file, file.getAction());
jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction action = this.getAction(actionString);
if(action != null) 
{
((jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag)action).addChild(file);
file.setAction((jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction)action);

}
return action;

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction setAction(java.lang.Class actionClass, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.removeFileFromFlag(file, file.getAction());
jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction action = this.getAction(actionClass);
if(action != null) 
{
((jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag)action).addChild(file);
file.setAction(action);

}
return action;

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction[] getAllActions()
{
return this.actions;

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction getAction(java.lang.String actionString)
{
for(jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction action : this.actions) 
{
if(action.getValue().compareTo(actionString) == 0) return action;

}
return null;

}

public jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction getAction(java.lang.Class c)
{
for(jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction action : this.actions) 
{
if(action.getClass() == c) return action;

}
return null;

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor setAuthor(java.lang.String authorString, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.removeFileFromFlag(file, file.getAuthor());
jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor author = this.authorMap.get(authorString);
if(author == null) 
{
author = new jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor(authorString);
this.authorMap.put(authorString, author);

}
author.addChild(file);
file.setAuthor(author);
return author;

}

public java.util.Collection getAllAuthors()
{
return this.authorMap.values();

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsAuthor getAuthor(java.lang.String authorString)
{
return this.authorMap.get(authorString);

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch setBranch(java.lang.String branchString, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.removeFileFromFlag(file, (jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch)file.getBranch());
jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch branch = this.branchMap.get(branchString);
if(branch == null) 
{
branch = new jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch(branchString);
this.branchMap.put(branchString, branch);

}
branch.addChild(file);
file.setBranch(branch);
return branch;

}

public java.util.Collection getAllBranches()
{
return this.branchMap.values();

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch getBranch(java.lang.String branchString)
{
return this.branchMap.get(branchString);

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsBranch getTrunk()
{
return this.branchMap.get(jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk.TRUNK_BRANCH_NAME);

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName setFileName(java.lang.String fnString, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.removeFileFromFlag(file, file.getFileName());
jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName fileName = this.fileNameMap.get(fnString);
if(fileName == null) 
{
fileName = new jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName(fnString);
this.fileNameMap.put(fnString, fileName);

}
fileName.addChild(file);
file.setFileName(fileName);
return fileName;

}

public java.util.Collection getAllFilesNames()
{
return this.fileNameMap.values();

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsFileName getFileName(java.lang.String fnString)
{
return this.fileNameMap.get(fnString);

}

public jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime setUpdateTime(java.util.Date updateTime, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime updTime;
updTime = new jct.test.rsc.snpsht.verfilesystem.attribute.VerFsUpdateTime(updateTime);
file.setUpdateTime(updTime);
return updTime;

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID setRevID(java.lang.String idString, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.removeFileFromFlag(file, file.getRevID());
jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID revID = this.revIDMap.get(idString);
if(revID == null) 
{
revID = new jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID(idString);
this.revIDMap.put(idString, revID);

}
revID.addChild(file);
file.setRevID(revID);
return revID;

}

public java.util.Collection getAllRevIDs()
{
return this.revIDMap.values();

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsRevID getRevID(java.lang.String idString)
{
return this.revIDMap.get(idString);

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile setAsFile(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[] filesRev)
{
return this.setAsFile(filesRev, null);

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile setAsFile(final jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[] filesRev, final java.lang.String id)
{
jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file;
jct.test.rsc.snpsht.verfilesystem.VerFsNullRev nullRev;
jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction create = this.getAction(class);
jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction resurect = this.getAction(class);
if(filesRev.length < 1) return null;
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev currFile : filesRev) 
{
this.removeFileFromFlag(currFile, currFile.getFile());

}
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev = filesRev[0];
while(fileRev.getAction() != create && fileRev.getAction() != resurect) fileRev = fileRev.getPrevRevision();
if(fileRev.getPrevRevision() != null) 
{
this.removeNextRevision(fileRev, fileRev.getPrevRevision());
nullRev = new jct.test.rsc.snpsht.verfilesystem.VerFsNullRev(fileRev, fileRev.getPrevRevision());

}
 else 
{
nullRev = new jct.test.rsc.snpsht.verfilesystem.VerFsNullRev(fileRev);

}
this.setPrevRevision(nullRev, fileRev);
final java.lang.String id2;
if(id == null) 
{
id2 = this.getFileNewIdFromName(fileRev.getName());

}
 else 
{
id2 = id;

}
file = new jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile(id2, nullRev, filesRev);
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev currFile : filesRev) 
{
file.addChild(currFile);
currFile.setFile(file);

}
this.fileMap.put(id, file);
return file;

}

private java.lang.String getFileNewIdFromName(java.lang.String name)
{
int i = 0;
java.lang.String id;
do 
{
id = i + "_" + name;
i ++;

}
 while(this.fileMap.containsKey(id));
return id;

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile addToFile(java.lang.String idString, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRevToAdd)
{
jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file = this.fileMap.get(idString);
if(file == null) return null;
this.removeFileFromFlag(fileRevToAdd, fileRevToAdd.getFile());
file.addChild(fileRevToAdd);
fileRevToAdd.setFile(file);
return file;

}

public java.util.Collection getAllFiles()
{
return this.fileMap.values();

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile getFile(java.lang.String idString)
{
return this.fileMap.get(idString);

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag addTag(java.lang.String tagString, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag tag = this.tagsMap.get(tagString);
if(tag == null) 
{
tag = new jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag(tagString);
this.tagsMap.put(tagString, tag);

}
tag.addChild(file);
file.addTag(tag);
return tag;

}

public java.util.Collection getAllTags()
{
return this.tagsMap.values();

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag getTag(java.lang.String tagString)
{
return this.tagsMap.get(tagString);

}

public void setPrevRevision(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev prevRevision, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
file.setPrevRevision(prevRevision);

}

public void addNextRevision(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextRevision, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
file.addNextRevision(nextRevision);

}

public void removeNextRevision(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextRevision, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
file.removeNextRevision(nextRevision);

}

public jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit setAsCommit(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[] filesRev)
{
jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit commit = new jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit();
if(filesRev.length < 1) return null;
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev currFile : filesRev) 
{
this.removeFileFromFlag(currFile, (jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit)currFile.getCommit());
currFile.setCommit(commit);
commit.addChild(currFile);

}
this.commitSet.add(commit);
return commit;

}

public java.util.Set getAllCommits()
{
return this.commitSet;

}

public jct.test.rsc.snpsht.filesystem.IFsManager getSourceManager()
{
return this.fileManager;

}

public void setSource(java.io.File source) throws java.io.IOException
{
this.fileManager = jct.test.rsc.snpsht.filesystem.FsManagerFactory.getNewManager(source);

}


}
