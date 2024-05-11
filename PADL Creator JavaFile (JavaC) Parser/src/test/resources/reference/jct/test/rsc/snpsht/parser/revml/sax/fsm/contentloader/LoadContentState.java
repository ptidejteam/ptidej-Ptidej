package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.utils.Base64Coder;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class LoadContentState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.AbstractStringLoaderState
{
private this.IContentWriter writer;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.<init>(fsm, previewState, manager, file);

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
java.lang.String aName;
if(name.trim().toLowerCase().compareTo(this.getWaitedMarker()) == 0) 
{
if(attributes != null) 
{
for(int i = 0; i < attributes.getLength(); i ++) 
{
aName = attributes.getLocalName(i);
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_ENCODING_ATTRIBUTE.equals(aName.toLowerCase().trim())) 
{
java.lang.String type = attributes.getValue(i);
if(type.compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_NO_ENCODING_ATTR_VALUE) == 0) 
{
java.lang.System.out.println(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_NO_ENCODING_ATTR_VALUE);
this.writer = new this.CommonWriter();

}
 else if(type.compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_BASE64_ENCODING_ATTR_VALUE) == 0) 
{
java.lang.System.out.println(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_BASE64_ENCODING_ATTR_VALUE);
this.writer = new this.Base64Writer();

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Unexpected " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_ENCODING_ATTRIBUTE + " attribute value: " + type, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));

}
return;

}

}

}
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Content marker have no encoding attribute.", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));

}
 else 
{
super.startElement(uri, localName, name, attributes);

}

}

protected void processString(java.lang.String loadedString)
{
java.lang.System.out.println(loadedString);
java.lang.String targetFilePath = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVS_FILES_PATH + this.getManager().toRelativePath(this.getFile().getPath()).replace("/", java.io.File.separator) + "." + this.getFile().getRevID().getValue().replace('.', '_') + ".rev";
try
{
jct.test.rsc.snpsht.filesystem.IFsRealEntity entity = this.getManager().getSourceManager().add(targetFilePath);
this.writer.write(entity, loadedString);

}
catch(java.io.IOException e) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Catched ecpetion.
Type: " + e.getClass() + "
Message: " + e.getMessage(), jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.EXCEPTION_CATCHED));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<LoadRevIDState>
Rev ID: " + this.getLoadedString();
return toRet;

}

protected java.lang.String getWaitedMarker()
{
return jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_CONTENT_MARKER;

}

private interface IContentWriter
{
public void write(jct.test.rsc.snpsht.filesystem.IFsRealEntity entity, java.lang.String toWrite) throws java.io.IOException
{

}


}

private class Base64Writer
implements this.IContentWriter
{
private void <init>()
{
this.<init>();

}

public void write(jct.test.rsc.snpsht.filesystem.IFsRealEntity entity, java.lang.String toWrite) throws java.io.IOException
{
java.io.OutputStream os = entity.getOutputStream();
java.lang.String[] lines = toWrite.split("
");
for(java.lang.String line : lines) 
{
os.write(jct.test.rsc.snpsht.utils.Base64Coder.decode(line));

}
os.close();

}


}

private class CommonWriter
implements this.IContentWriter
{
private void <init>()
{
this.<init>();

}

public void write(jct.test.rsc.snpsht.filesystem.IFsRealEntity entity, java.lang.String toWrite) throws java.io.IOException
{
java.io.OutputStream os = entity.getOutputStream();
os.write(toWrite.getBytes());
os.close();

}


}


}
