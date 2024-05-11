package jct.test.rsc.snpsht.parser.revml.modeler;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsMoveAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsResurrectAction;
public class MovedFilesModeler
implements jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler
{
public void <init>()
{
this.<init>();

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager modl(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
for(jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit commit : manager.getAllCommits()) 
{
this.detectAndLinkMovedFiles(manager, commit);

}
return manager;

}

private void detectAndLinkMovedFiles(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.flag.VerFsCommit commit)
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev delFile : commit.getChildren()) 
{
if(delFile.getAction() instanceof jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction) 
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev creaFile : commit.getChildren()) 
{
if(creaFile.getAction() instanceof jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction && delFile.getName().compareTo(creaFile.getName()) == 0 && delFile.getBranch() == creaFile.getBranch()) 
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextFile : delFile.getNextRevisions()) 
{
if(nextFile.getBranch() == creaFile.getBranch()) 
{
manager.setAction(class, nextFile);

}

}
manager.setPrevRevision(delFile, creaFile);
manager.addNextRevision(creaFile, delFile);
manager.setAction(class, creaFile);

}

}

}

}

}


}
