package jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.VerFsRepository;
import org.xml.sax.SAXException;
public class BuildRootRepoState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
private java.lang.String rootRepository;

public jct.test.rsc.snpsht.verfilesystem.VerFsRepository root;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(fsm, previewState, manager);
this.rootRepository = "";

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{
this.rootRepository += new java.lang.String(ch, start, length);

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.ROOT_REPOSITORY_MARKER.compareTo(name.trim().toLowerCase()) == 0) 
{
this.root = this.getManager().makeRoot(this.rootRepository);
this.getFsm().changeState(this.getPreviewState());

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.ROOT_REPOSITORY_MARKER, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<BuildRootRepoState>
Root repository: " + this.rootRepository;
return toRet;

}


}
