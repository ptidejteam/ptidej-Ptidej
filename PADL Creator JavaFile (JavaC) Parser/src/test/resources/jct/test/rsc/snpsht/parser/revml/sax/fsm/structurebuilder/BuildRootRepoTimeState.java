package jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.SAXException;
public class BuildRootRepoTimeState
extends jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState
{
private java.lang.String dateString;

private java.util.Date date;

public void <init>(jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser fsm, jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm previewState, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager)
{
this.<init>(fsm, previewState, manager);
this.dateString = "";

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{
this.dateString += new java.lang.String(ch, start, length);

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
if(jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.ROOT_REPOSITORY_TIME_MARKER.compareTo(name.trim().toLowerCase()) == 0) 
{
try
{
this.date = java.text.DateFormat.getDateTimeInstance().parse(this.dateString);
this.getManager().setUpdateDate(this.date);
this.getFsm().changeState(this.getPreviewState());

}
catch(java.text.ParseException e) 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Can't parse received date.
Received date: " + this.dateString, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_DATE_FORMAT));

}

}
 else 
{
this.getFsm().changeState(new jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState(this.getFsm(), this, this.getManager(), "Misformed xml document.
End marker unexpected.
Received: " + name + "
Expected: " + jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings.ROOT_REPOSITORY_TIME_MARKER, jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState.MISFORMED_XML_DOCUMENT));

}

}

public java.lang.String toString()
{
java.lang.String toRet = "<BuildRootRepoTimeState>
Date: " + this.dateString;
return toRet;

}


}
