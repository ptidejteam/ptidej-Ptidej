package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
public class LoadActionState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.AbstractStringLoaderState
{
public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.<init>(fsm, previewState, manager, file);

}

protected void processString(java.lang.String loadedString)
{
this.getManager().setAction(loadedString, this.getFile());

}

public java.lang.String toString()
{
java.lang.String toRet = "<LoadActionState>
Action: " + this.getLoadedString();
return toRet;

}

protected java.lang.String getWaitedMarker()
{
return jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_ACTION_MARKER;

}


}
