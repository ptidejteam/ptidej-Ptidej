package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
public class LoadUpdateTimeState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.AbstractStringLoaderState
{
final private static java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance();

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.<init>(fsm, previewState, manager, file);

}

protected void processString(java.lang.String loadedString)
{
java.util.Date updateTime;
try
{
updateTime = jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadUpdateTimeState.df.parse(loadedString);
this.getManager().setUpdateTime(updateTime, this.getFile());

}
catch(java.text.ParseException e) 
{
java.lang.System.out.println("Can't parse date");
this.setCallBackSate(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Can't parse file revision update time
File: " + this.getFile().getId() + "
Date: " + loadedString, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_DATE_FORMAT));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<LoadUpdateTimeState>
Update time: " + this.getLoadedString();
return toRet;

}

protected java.lang.String getWaitedMarker()
{
return jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_UPDATE_TIME_MARKER;

}


}
