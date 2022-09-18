package jct.test.rsc.snpsht.serializer.verfilesystem;
import java.io.FileWriter;
import java.io.IOException;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement;
import jct.test.rsc.snpsht.verfilesystem.IVerFsElement;
import jct.test.rsc.snpsht.verfilesystem.IVerFsManager;
import jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision;
import jct.test.rsc.snpsht.verfilesystem.visitor.AbstractVerFsVisitor;
public class VerFsStructureSerializer
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
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity = this.manager.getSourceManager().add(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILES_REV_STRUCT_XML_FILE_NAME);
java.io.FileWriter writer = this.manager.getSourceManager().getFileWriter((jct.test.rsc.snpsht.filesystem.IFsRealEntity)entity);
this.StructVisitor visitor = new this.StructVisitor(writer);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_HEADER);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.DTD_MARKER);

{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILES_MAKER + ">");

{
if((java.lang.Boolean)this.manager.getRoot().accept(visitor) != true) 
{
throw new java.io.IOException("Can't serialize version file system structure.");

}

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILES_MAKER + ">");

}
writer.close();

}

private class StructVisitor
extends jct.test.rsc.snpsht.verfilesystem.visitor.AbstractVerFsVisitor
{
private java.io.FileWriter writer;

public void <init>(java.io.FileWriter writer)
{
this.<init>();
this.writer = writer;

}

public java.lang.Boolean visit(jct.test.rsc.snpsht.verfilesystem.IVerFsComplexElement toVisit)
{
try
{
this.writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPOSITORY_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPO_ID_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(toVisit.getId()) + "\">");

{
this.writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPO_NAME_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(toVisit.getName())));
this.writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPO_PATH_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.PATH2XML(toVisit.getPath())));
this.writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPO_CONTENT_LIST_MAKER + ">");

{
for(jct.test.rsc.snpsht.verfilesystem.IVerFsElement elem : toVisit.getChildren()) 
{
elem.accept(this);

}

}
this.writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPO_CONTENT_LIST_MAKER + ">");

}
this.writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPOSITORY_MAKER + ">");

}
catch(java.io.IOException e) 
{
e.printStackTrace();
return false;

}
return true;

}

public java.lang.Boolean visit(jct.test.rsc.snpsht.verfilesystem.IVerFsSimpleRevision toVisit)
{
try
{
this.writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(toVisit.getId()) + "\">");

{
this.writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(toVisit.getId())));
this.writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_NAME_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(toVisit.getName())));
this.writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_PATH_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(toVisit.getPath())));
this.serializeTimeOnlyToXML(this.writer, toVisit.getUpdateTime());
this.writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_LOCATION_MARKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.PATH2XML(toVisit.getFile().getFileLocation().getId())));
this.writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_FILE_LOCATION_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.PATH2XML(toVisit.getFileRevLocation().getId())));
this.writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_BRANCH_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(toVisit.getBranch().getValue())));

}
this.writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER + ">");

}
catch(java.io.IOException e) 
{
e.printStackTrace();
return false;

}
return true;

}


}


}
