package jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl;
import java.io.IOException;
import jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsActionsIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsAuthorsIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsBranchesIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommitsIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsFileSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsFilesIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsStructureSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsTagsIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsValidationDataSerializer;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
public class CvsFsCompleteSerializer
extends jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer
{
private jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager manager;

public void <init>(jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager manager)
{
this.<init>();
this.manager = manager;

}

public jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager getManager()
{
return this.manager;

}

public void setManager(jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager manager)
{
this.manager = manager;

}

public void serialize() throws java.io.IOException
{
jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer serializer;
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsFileSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsActionsIndexSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsAuthorsIndexSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsBranchesIndexSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommitsIndexSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsFilesIndexSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsStructureSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsTagsIndexSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsGeneralInfoSerializer(this.manager);
serializer.serialize();
serializer = new jct.test.rsc.snpsht.serializer.verfilesystem.VerFsValidationDataSerializer(this.manager);
serializer.serialize();
this.manager.getSourceManager().update();

}


}
