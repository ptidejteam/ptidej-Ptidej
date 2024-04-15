package jct.test.rsc.snpsht.parser.revml.modeler;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsAuthor;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk;
import jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction;
public class BranchModeler
implements jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler
{
public void <init>()
{
this.<init>();

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager modl(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.addTrunkBranch(manager);
this.addBrancherUsers(manager);
return manager;

}

private void addTrunkBranch(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch;
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision file : manager.getAllSimplesRevisions()) 
{
branch = file.getBranch();
if(branch == null) 
{
manager.setBranch(jct.test.rsc.snpsht.verfilesystem.flag.VerFsTrunk.TRUNK_BRANCH_NAME, (jct.test.rsc.snpsht.verfilesystem.VerFsFileRev)file);

}

}

}

private void addBrancherUsers(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
jct.test.rsc.snpsht.verfilesystem.flag.IVerFsAuthor author;
jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch;
jct.test.rsc.snpsht.verfilesystem.flag.action.IVerFsAction branchAction = manager.getAction(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_BRANCH_ACTION);
if(branchAction != null) 
{
for(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision file : branchAction.getChildren()) 
{
author = file.getAuthor();
branch = file.getBranch();
if(branch != null) 
{
if(author == null) manager.setAuthor("[" + jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager.BRANCHER_USER_NAME + ":" + branch.getValue() + "]", (jct.test.rsc.snpsht.verfilesystem.VerFsFileRev)file);

}
 else 
{
throw new java.lang.NullPointerException("File revision '" + file.getId() + "' is a first file on a new branch, and so its branch must be not null");

}

}

}

}


}
