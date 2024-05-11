package jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class BuildFileRevState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
private jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file;

private java.lang.String fileId;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, java.lang.String fileId)
{
this.<init>(fsm, previewState, manager);
this.fileId = fileId;

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER) == 0) 
{
if(this.file == null) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_NAME_MARKER, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}
 else 
{
this.getFsm().changeState(this.getPreviewState());

}

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_NAME_MARKER) == 0) 
{
this.getFsm().changeState(new this.CreateFileRevStateRevMLSaxFsm(this.getFsm(), this, this.getManager()));

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm(this.getFsm(), this, attributes, localName, name, uri));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<BuildFileRevState>
 File ID: " + this.fileId;
return toRet;

}

private class CreateFileRevStateRevMLSaxFsm
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
private java.lang.String name;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(fsm, previewState, manager);
this.name = "";

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{
this.name += new java.lang.String(ch, start, length);

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_NAME_MARKER) == 0) 
{
this.file = this.getManager().addSimpleRevision(this.name, this.fileId);
this.getFsm().changeState(this);

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<CreateFileRevStateRevMLSaxFsm>
 File name: " + this.name;
return toRet;

}


}


}
