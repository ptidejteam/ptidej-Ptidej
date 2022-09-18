package jct.test.rsc.snpsht.parser.revml.sax.fsm;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.SAXException;
public class ErrorStateRevMLState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
final public static int REVML_MARKER_NOT_FOUND = 0;

final public static int UNEXPECTED_DOCUMENT_END = 1;

final public static int MISFORMED_XML_DOCUMENT = 2;

final public static int MISFORMED_DATE_FORMAT = 3;

final public static int MISFORMED_REVML_DOCUMENT = 4;

final public static int UNEXPECTED_FILE_ID = 5;

final public static int MISFORMED_XML_MARKER = 6;

final public static int EXCEPTION_CATCHED = 7;

private int errorType;

private java.lang.String message;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, java.lang.String message, int errorType)
{
this.<init>(fsm, previewState, manager);
this.message = message;
this.errorType = errorType;

}

public int getErrorType()
{
return this.errorType;

}

public java.lang.String getMessage()
{
return this.message;

}

public java.lang.String toString()
{
java.lang.String toRet = "<ErrorStateRevMLSaxFsm>
" + "Error during revml file parsing.
Message: " + this.getMessage() + "
Error type: " + this.errorType + "
Preview state: " + this.getPreviewState();
return toRet;

}

public void endDocument() throws org.xml.sax.SAXException
{

}


}
