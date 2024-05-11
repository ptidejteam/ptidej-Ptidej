package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class LoadInfoRevisionsScanState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(fsm, previewState, manager);

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_DOCUMENT_MARKER.compareTo(name.trim().toLowerCase()) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadInfoEndState(this.getFsm(), this, this.getManager()));

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_DOCUMENT_MARKER, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
java.lang.String fileId;
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file;
if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER) == 0) 
{
if(attributes != null) 
{
for(int i = 0; i < attributes.getLength(); i ++) 
{
java.lang.String aName = attributes.getLocalName(i);
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_VERSION_ID_ATTRIBUTE.equals(aName.toLowerCase().trim())) 
{
fileId = attributes.getValue(i);
file = this.getManager().getSimpleRevision(fileId);
if(file == null) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Can't find revision file in manager.", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadInfoRevFileScanState(this.getFsm(), this, this.getManager(), file));

}
return;

}

}

}
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "File revision as no ID.", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm(this.getFsm(), this, attributes, localName, name, uri));

}

}

public void endDocument() throws org.xml.sax.SAXException
{

}

public java.lang.String toString()
{
java.lang.String toRet = "<LoadInfoRevisionsScanState>";
return toRet;

}


}
