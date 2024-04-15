package jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl;
import java.io.FileWriter;
import java.io.IOException;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint;
public class CvsFsGeneralInfoSerializer
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
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity = this.manager.getSourceManager().add(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_XML_FILE_NAME);
java.io.FileWriter writer = this.manager.getSourceManager().getFileWriter((jct.test.rsc.snpsht.filesystem.IFsRealEntity)entity);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_HEADER);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.DTD_MARKER);

{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_TYPE_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_TYPE_ATTRIBUTE_VALUE + "\">");

{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_TYPE_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_TYPE_ATTRIBUTE_VALUE));
this.serializeCVSRoot(writer);
this.serializeCVSManagerInfo(writer);
this.serializeCVSCommitConstraints(writer);

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + ">");

}
writer.close();

}

private void serializeCVSCommitConstraints(java.io.FileWriter writer) throws java.io.IOException
{
java.lang.String attrs = "";
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINTS_MARKER + ">");

{
for(jct.test.rsc.snpsht.verfilesystem.cvsimpl.ICvsFsCommitConstraint constr : this.manager.getConstraints()) 
{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINT_MARKER + ">");

{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_TYPE_MARKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(constr.getName())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_DESCR_MARKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(constr.getDescription())));
for(java.lang.String[] attr : constr.getAttributes()) 
{
attrs += "<" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTR_MARKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTR_TYPE_ATTR + "=\"" + attr[0] + "\">" + attr[1] + "</" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTR_MARKER + ">";

}
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTR_ATTRS_MARKER, attrs));

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINT_MARKER + ">");

}

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_COMMIT_GROUP_CONSTRAINTS_MARKER + ">");

}

private void serializeCVSRoot(java.io.FileWriter writer) throws java.io.IOException
{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + ">");

{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_STRING_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getCvsRoot().getCVSROOT(false))));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_PROTOCOL_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getCvsRoot().getProtocol())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_SERVER_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getCvsRoot().getServer())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_USER_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getCvsRoot().getUser())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_ROOT_REPO_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getCvsRoot().getRootRepository())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_CURRENT_REPO_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getCvsRoot().getCurrentRepository())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_SEPARATOR_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getCvsRoot().getSeparator())));

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + ">");

}

private void serializeCVSManagerInfo(java.io.FileWriter writer) throws java.io.IOException
{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_INFO_MAKER + ">");

{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_INFO_DESCR_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getDescription())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_CURRENT_REPO_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getRoot().getPath())));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_INFO_REVML_VERSION_MAKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(this.manager.getRevmlVersion())));

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_INFO_MAKER + ">");

}


}
