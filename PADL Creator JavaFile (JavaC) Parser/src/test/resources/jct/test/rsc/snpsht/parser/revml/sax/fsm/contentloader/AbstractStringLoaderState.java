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
abstract public class AbstractStringLoaderState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
private java.lang.String loadedString;

private jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file;

private jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm callBackState;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.<init>(fsm, previewState, manager);
this.loadedString = "";
this.file = file;
this.callBackState = previewState;

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{
this.loadedString += new java.lang.String(ch, start, length);

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(name.trim().toLowerCase().compareTo(this.getWaitedMarker()) == 0) 
{
this.processString(this.getLoadedString());
this.getFsm().changeState(this.callBackState);

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + this.getWaitedMarker(), jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm(this.getFsm(), this, attributes, localName, name, uri));

}

public java.lang.String getLoadedString()
{
return this.loadedString;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsFileRev getFile()
{
return this.file;

}

protected void setCallBackSate(jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm state)
{
this.callBackState = state;

}

abstract protected void processString(java.lang.String loadedString)
{

}

abstract protected java.lang.String getWaitedMarker()
{

}

abstract public java.lang.String toString()
{

}


}
