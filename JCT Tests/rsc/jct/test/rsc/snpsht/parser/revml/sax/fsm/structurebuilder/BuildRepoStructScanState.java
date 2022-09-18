package jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class BuildRepoStructScanState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, org.xml.sax.Attributes attributes)
{
this.<init>(fsm, previewState, manager);
if(attributes != null) 
{
for(int i = 0; i < attributes.getLength(); i ++) 
{
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_DOCUMENT_VERSION_ATTRIBUTE.compareTo(attributes.getLocalName(i).trim().toLowerCase()) == 0) 
{
this.getManager().setRevmlVersion(attributes.getValue(i));
break;

}

}

}

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_DOCUMENT_MARKER.compareTo(name.trim().toLowerCase()) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructEndState(this.getFsm(), this, this.getManager()));

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
if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.ROOT_REPOSITORY_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRootRepoState(this.getFsm(), this, this.getManager()));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.ROOT_REPOSITORY_TIME_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRootRepoTimeState(this.getFsm(), this, this.getManager()));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.ROOT_REPOSITORY_DESCR_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRootRepoDescrState(this.getFsm(), this, this.getManager()));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER) == 0) 
{
if(this.getManager().getRoot() == null) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "First revision found before root repository path.", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));

}
 else 
{
if(attributes != null) 
{
for(int i = 0; i < attributes.getLength(); i ++) 
{
java.lang.String aName = attributes.getLocalName(i);
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_VERSION_ID_ATTRIBUTE.equals(aName.toLowerCase().trim())) 
{
fileId = attributes.getValue(i);
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildFileRevState(this.getFsm(), this, this.getManager(), fileId));
return;

}

}

}
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "File revision as no ID.", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));

}

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm(this.getFsm(), this, attributes, localName, name, uri));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<BuildRepoStructScanState>";
return toRet;

}


}
