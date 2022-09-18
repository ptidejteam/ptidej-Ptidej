package jct.test.rsc.snpsht.parser.sax;
public class DefaultErrorStateSaxFsm
extends jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm
{
private java.lang.String message;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, java.lang.String message)
{
this.<init>(fsm, previewState);
this.message = message;

}

public java.lang.String getMessage()
{
return this.message;

}

public java.lang.String toString()
{
java.lang.String toRet = "<DefaultErrorStateSaxFsm>
" + "Error during file parsing.
Message: " + this.getMessage() + "
Preview state: " + this.getPreviewState();
return toRet;

}


}
