package jct.test.rsc.snpsht.parser.revml.modeler;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsResurrectAction;
public class FileGrouper
implements jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler
{
public void <init>()
{
this.<init>();

}

private jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction create;

private jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction resurect;

public jct.test.rsc.snpsht.verfilesystem.VerFsManager modl(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.groupFilesRevInFile(manager);
return manager;

}

private void groupFilesRevInFile(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
java.util.Set file;
java.util.List createAndResurectFileRev = new java.util.ArrayList();
this.create = manager.getAction(class);
createAndResurectFileRev.addAll(this.create.getChildren());
this.resurect = manager.getAction(class);
createAndResurectFileRev.addAll(this.resurect.getChildren());
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : createAndResurectFileRev) 
{
file = new java.util.HashSet();
this.groupAFile(fileRev, file);
manager.setAsFile(file.toArray(new jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[0]));

}

}

private void groupAFile(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev, java.util.Set file)
{
file.add(fileRev);
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextRev : fileRev.getNextRevisions()) 
{
if(nextRev.getAction() != this.resurect) this.groupAFile(nextRev, file);

}

}


}
