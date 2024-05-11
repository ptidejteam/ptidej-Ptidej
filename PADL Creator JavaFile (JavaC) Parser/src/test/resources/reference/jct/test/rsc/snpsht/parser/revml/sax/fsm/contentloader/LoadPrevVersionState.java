package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
public class LoadPrevVersionState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.AbstractStringLoaderState
{
public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.<init>(fsm, previewState, manager, file);

}

protected void processString(java.lang.String loadedString)
{
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev prevFile = this.getManager().getSimpleRevision(loadedString);
if(prevFile == null) 
{
this.setCallBackSate(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Can't file previous file '" + loadedString + "' in manager.
File ID doesn't exist", jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.UNEXPECTED_FILE_ID));

}
 else 
{
this.getManager().setPrevRevision(prevFile, this.getFile());

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<LoadPrevVersionState>
Previous version id: " + this.getLoadedString();
return toRet;

}

protected java.lang.String getWaitedMarker()
{
return jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_PREV_VERSION_MARKER;

}


}
