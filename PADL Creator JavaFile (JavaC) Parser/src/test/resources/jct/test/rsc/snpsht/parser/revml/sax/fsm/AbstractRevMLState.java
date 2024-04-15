package jct.test.rsc.snpsht.parser.revml.sax.fsm;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.SAXException;
public class AbstractRevMLState
extends jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm
{
private jct.test.rsc.snpsht.verfilesystem.VerFsManager manager;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(fsm, previewState);
this.manager = manager;

}

public void endDocument() throws org.xml.sax.SAXException
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.manager, "Unexpected document end.", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.UNEXPECTED_DOCUMENT_END));

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager getManager()
{
return this.manager;

}

public void setManager(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.manager = manager;

}


}
