package jct.test.rsc.snpsht.serializer.verfilesystem;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.VerFsNullRev;
import jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag;
public class VerFsFileSerializer
extends jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer
{
private jct.test.rsc.snpsht.verfilesystem.VerFsManager manager;

public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>();
this.setManager(manager);

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager getManager()
{
return this.manager;

}

public void setManager(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.manager = manager;

}

public void serialize() throws java.io.IOException
{
for(jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file : this.manager.getAllFiles()) 
{
this.serializeFileToXML(file);

}

}

private void serializeFileToXML(jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file) throws java.io.IOException
{
java.lang.String filePath = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.META_INFO_XML_FILES_PATH + file.getId() + "." + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.META_INFO_XML_FILE_EXT;
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity = this.manager.getSourceManager().add(filePath);
java.io.FileWriter writer = this.manager.getSourceManager().getFileWriter((jct.test.rsc.snpsht.filesystem.IFsRealEntity)entity);
this.manager.setFileLocation(entity, file);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_HEADER);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.DTD_MARKER);

{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_ID_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(file.getId()) + "\">");

{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_ID_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(file.getId())));
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVISIONS_MAKER + ">");

{
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[] files = file.getChildren().toArray(new jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[0]);
java.util.Arrays.sort(files, new jct.test.rsc.snpsht.verfilesystem.comparator.VerFsFileRevUpdateTimeComparator());
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : files) 
{
this.serializeFileRevToXML(writer, fileRev);

}

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVISIONS_MAKER + ">");

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_MAKER + ">");

}
writer.close();

}

private void serializeFileRevToXML(java.io.FileWriter writer, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev) throws java.io.IOException
{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getId()) + "\">");

{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getId())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_NAME_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getName())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_FILE_NAME_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getFileName().getValue())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPO_PATH_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.PATH2XML(fileRev.getPath())));
this.serializeTimeOnlyToXML(writer, fileRev.getUpdateTime());
if(fileRev.getFileRevLocation() == null) 
{
java.lang.System.out.println();

}
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_FILE_LOCATION_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.PATH2XML(fileRev.getFileRevLocation().getId())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_REV_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getRevID().getValue())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ACTION_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getAction().getValue())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_AUTHOR_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getAuthor().getValue())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_COMMENT_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getComment().getValue())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_BRANCH_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(fileRev.getBranch().getValue())));
this.serializeNextPrevFilesRevToXML(writer, fileRev);
this.serializeTagsToXML(writer, fileRev.getTags());

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER + ">");

}

private void serializeTagsToXML(java.io.FileWriter writer, java.util.Set tags) throws java.io.IOException
{
java.lang.String tagsXML = "";
for(jct.test.rsc.snpsht.verfilesystem.flag.VerFsTag tag : tags) 
{
tagsXML += this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_TAG_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(tag.getValue()));

}
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_TAGS_MAKER, tagsXML));

}

private void serializeNextPrevFilesRevToXML(java.io.FileWriter writer, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev) throws java.io.IOException
{
java.lang.String nextRevXML = "";
try
{
jct.test.rsc.snpsht.verfilesystem.VerFsNullRev nullRev = (jct.test.rsc.snpsht.verfilesystem.VerFsNullRev)fileRev.getPrevRevision();
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_PREV_REV_MAKER, (nullRev.getPrevRevision() == null ? "" : nullRev.getPrevRevision().getId())));

}
catch(java.lang.Exception e) 
{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_PREV_REV_MAKER, fileRev.getPrevRevision().getId()));

}
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev rev : fileRev.getNextRevisions()) 
{
nextRevXML += this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(rev.getId()));

}
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_NEXT_REV_MAKER, nextRevXML));

}


}
