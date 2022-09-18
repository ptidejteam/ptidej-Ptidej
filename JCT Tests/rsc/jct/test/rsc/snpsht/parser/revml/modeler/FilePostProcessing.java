package jct.test.rsc.snpsht.parser.revml.modeler;
import java.util.Collection;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction;
public class FilePostProcessing
implements jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler
{
public void <init>()
{
this.<init>();

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager modl(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.makeDoubleLink(manager);
this.setCreateAction(manager);
return manager;

}

private void makeDoubleLink(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev prevRev;
java.util.Collection allFilesRev = manager.getAllSimplesRevisions();
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : allFilesRev) 
{
prevRev = fileRev.getPrevRevision();
if(prevRev != null) manager.addNextRevision(fileRev, prevRev);

}

}

private void setCreateAction(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : manager.getAllSimplesRevisions()) 
{
if(fileRev.getPrevRevision() == null) 
{
manager.setAction(class, fileRev);

}

}

}


}
