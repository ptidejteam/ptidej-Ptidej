package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class LoadInfoRevFileScanState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
private java.lang.String fileId;

private jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, jct.test.rsc.snpsht.verfilesystem.VerFsFileRev file)
{
this.<init>(fsm, previewState, manager);
this.file = file;

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER) == 0) 
{
this.getFsm().changeState(this.getPreviewState());

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_MARKER, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_COMMENT_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadCommentState(this.getFsm(), this, this.getManager(), this.file));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_ACTION_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadActionState(this.getFsm(), this, this.getManager(), this.file));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_AUTHOR_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadAuthorState(this.getFsm(), this, this.getManager(), this.file));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_BRANCH_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadBranchState(this.getFsm(), this, this.getManager(), this.file));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_FILE_NAME_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadFileNameState(this.getFsm(), this, this.getManager(), this.file));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_REV_ID_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadRevIDState(this.getFsm(), this, this.getManager(), this.file));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_CONTENT_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadContentState(this.getFsm(), this, this.getManager(), this.file));
this.getFsm().getState().startElement(uri, localName, name, attributes);

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_DELTA_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadDeltaState(this.getFsm(), this, this.getManager(), this.file));
this.getFsm().getState().startElement(uri, localName, name, attributes);

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_UPDATE_TIME_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadUpdateTimeState(this.getFsm(), this, this.getManager(), this.file));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_TAG_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadTagState(this.getFsm(), this, this.getManager(), this.file));

}
 else if(name.trim().toLowerCase().compareTo(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.FILE_REV_PREV_VERSION_MARKER) == 0) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadPrevVersionState(this.getFsm(), this, this.getManager(), this.file));

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.sax.DefaultUnknowMakerStateSaxFsm(this.getFsm(), this, attributes, localName, name, uri));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<LoadInfoRevFileScanState>
File ID: " + this.fileId;
return toRet;

}


}
