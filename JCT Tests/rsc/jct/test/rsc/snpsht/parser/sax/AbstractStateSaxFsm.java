package jct.test.rsc.snpsht.parser.sax;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
abstract public class AbstractStateSaxFsm
extends org.xml.sax.helpers.DefaultHandler
{
private jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState;

private jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState)
{
this.<init>();
fsm = fsm;
this.previewState = previewState;

}

public jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser getFsm()
{
return fsm;

}

public void setFsm(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm)
{
this.fsm = fsm;

}

public jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm getPreviewState()
{
return this.previewState;

}

public void endDocument() throws org.xml.sax.SAXException
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.sax.DefaultErrorStateSaxFsm(this.getFsm(), this, "Unexpected document end."));

}


}
