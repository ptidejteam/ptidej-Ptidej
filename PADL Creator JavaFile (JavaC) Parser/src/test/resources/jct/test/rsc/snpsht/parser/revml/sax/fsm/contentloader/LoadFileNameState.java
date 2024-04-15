package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
public class LoadFileNameState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.AbstractStringLoaderState
{
private java.lang.String fileName = "";

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.<init>(fsm, previewState, manager, file);

}

protected void processString(java.lang.String loadedString)
{
java.lang.String[] tokens = loadedString.split("/");
this.fileName = tokens[tokens.length - 1];
this.getManager().setFileName(this.fileName, this.getFile());

}

public java.lang.String toString()
{
java.lang.String toRet = "<LoadFileNameState>
File Name: " + this.fileName;
return toRet;

}

protected java.lang.String getWaitedMarker()
{
return jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_FILE_NAME_MARKER;

}


}
