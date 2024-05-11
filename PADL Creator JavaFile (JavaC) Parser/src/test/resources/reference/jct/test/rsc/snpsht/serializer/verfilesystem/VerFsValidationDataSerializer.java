package jct.test.rsc.snpsht.serializer.verfilesystem;
import java.io.FileWriter;
import java.io.IOException;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.utils.FSUtils;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
public class VerFsValidationDataSerializer
extends jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer
{
private jct.test.rsc.snpsht.verfilesystem.VerFsManager manager;

public void <init>(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>();
this.manager = manager;

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
java.lang.String path;
jct.test.rsc.snpsht.filesystem.IFsRealEntity validFile = this.manager.getSourceManager().add(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.VALIDATION_DATA_XML_FILE_NAME);
java.io.FileWriter writer = this.manager.getSourceManager().getFileWriter(validFile);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_HEADER);
writer.write(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.DTD_MARKER);

{
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.VALIDATION_DATA_MARKER + ">");

{
for(jct.test.rsc.snpsht.filesystem.IFsRealEntity entity : this.manager.getSourceManager().getAllFilesEntity()) 
{
path = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.PATH2XML(entity.getId());
writer.write("<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.VALID_DATA_FILE_MARKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.VALID_DATA_NAME_ATTRIBUTE + "=\"" + path + "\">");

{
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.VALID_DATA_PATH_MARKER, path));
writer.write(this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.VALID_DATA_MD5_MARKER, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(jct.test.rsc.snpsht.utils.FSUtils.checksum(this.manager.getSourceManager().getInputStrem(entity)))));

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.VALID_DATA_FILE_MARKER + ">");

}

}
writer.write("</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.VALIDATION_DATA_MARKER + ">");

}
writer.close();

}


}
