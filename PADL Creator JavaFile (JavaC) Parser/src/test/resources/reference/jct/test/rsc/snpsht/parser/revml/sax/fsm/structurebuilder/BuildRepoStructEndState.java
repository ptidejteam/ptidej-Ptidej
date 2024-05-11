package jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.SAXException;
public class BuildRepoStructEndState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(fsm, previewState, manager);

}

public void endDocument() throws org.xml.sax.SAXException
{

}

public java.lang.String toString()
{
java.lang.String toRet = "<BuildRepoStructEndState>";
return toRet;

}


}
