package jct.test.rsc.snpsht.serializer.verfilesystem;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.verfilesystem.IVerFsManager;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator;
import jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch;
public class VerFsBranchesIndexSerializer
extends jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer
{
private jct.test.rsc.snpsht.verfilesystem.IVerFsManager manager;

public void <init>(jct.test.rsc.snpsht.verfilesystem.IVerFsManager manager)
{
this.<init>();
this.manager = manager;

}

public jct.test.rsc.snpsht.verfilesystem.IVerFsManager getManager()
{
return this.manager;

}

public void setManager(jct.test.rsc.snpsht.verfilesystem.IVerFsManager manager)
{
this.manager = manager;

}

public void serialize() throws java.io.IOException
{
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity = this.manager.getSourceManager().add(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.BRANCHES_INDEX_XML_FILE_NAME);
java.io.FileWriter writer = this.manager.getSourceManager().getFileWriter((jct.test.rsc.snpsht.filesystem.IFsRealEntity)entity);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_HEADER);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.DTD_MARKER);

{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.BRANCHES_MAKER + ">");

{
for(jct.test.rsc.snpsht.verfilesystem.flag.IVerFsBranch branch : this.manager.getAllBranches()) 
{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.BRANCH_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.BRANCH_NAME_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(branch.getValue()) + "\">");

{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.BRANCH_NAME_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(branch.getValue())));
this.serializeTimeToXML(writer, branch.getCreationTime());
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVISIONS_MAKER + ">");

{
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[] files = branch.getChildren().toArray(new jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[0]);
java.util.Arrays.sort(files, new jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator());
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : files) 
{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getId()) + "\">");

{
this.serializeTimeOnlyToXML(writer, fileRev.getUpdateTime());
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_LOCATION_MARKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.PATH2XML(fileRev.getFile().getFileLocation().getId())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_FILE_LOCATION_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.PATH2XML(fileRev.getFileRevLocation().getId())));

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER + ">");

}

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVISIONS_MAKER + ">");

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.BRANCH_MAKER + ">");

}

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.BRANCHES_MAKER + ">");

}
writer.close();

}


}
