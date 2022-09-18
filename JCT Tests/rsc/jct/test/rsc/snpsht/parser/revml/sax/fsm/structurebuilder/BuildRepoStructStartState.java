package jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class BuildRepoStructStartState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(fsm, previewState, manager);

}

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(fsm, null, manager);

}

public void endDocument() throws org.xml.sax.SAXException
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "RevMl start marker not found", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.REVML_MARKER_NOT_FOUND));

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.REVML_DOCUMENT_MARKER.compareTo(name.trim().toLowerCase()) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructScanState(this.getFsm(), this, this.getManager(), attributes));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<BuildRepoStructStartState>";
return toRet;

}


}
