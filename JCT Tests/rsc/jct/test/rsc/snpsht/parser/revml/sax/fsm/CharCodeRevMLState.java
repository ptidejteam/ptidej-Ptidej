package jct.test.rsc.snpsht.parser.revml.sax.fsm;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class CharCodeRevMLState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
private org.xml.sax.Attributes attributes;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, org.xml.sax.Attributes attributes)
{
this.<init>(fsm, previewState, manager);

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.CHAR_CODE_MARKER.compareTo(name.trim().toLowerCase()) == 0) 
{
if(this.attributes != null) 
{
for(int i = 0; i < this.attributes.getLength(); i ++) 
{
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_CHAR_CODE_ATTRIBUTE.compareTo(this.attributes.getLocalName(i).trim().toLowerCase()) == 0) 
{
int j = new java.lang.Integer(this.attributes.getValue(i));
break;

}

}

}

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Unexpected end marker.
Received: " + name + "
Expected: " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_CHAR_CODE_ATTRIBUTE, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Char code maker sould be a leaf marker.", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Char code maker sould be a leaf marker.", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}


}
