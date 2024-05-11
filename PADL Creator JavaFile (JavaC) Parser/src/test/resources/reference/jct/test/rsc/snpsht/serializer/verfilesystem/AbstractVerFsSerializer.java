package jct.test.rsc.snpsht.serializer.verfilesystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsPeriod;
import jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime;
abstract public class AbstractVerFsSerializer
implements jct.test.rsc.snpsht.serializer.verfilesystem.IVerFsSerializer
{
public void <init>()
{
this.<init>();

}

public void serialize(java.io.File dest) throws java.io.IOException
{
this.getManager().setSource(dest);
this.serialize();
this.getManager().getSourceManager().update();

}

abstract public void serialize() throws java.io.IOException
{

}

protected java.lang.String genMarker(java.lang.String marker, java.lang.String content)
{
if(content == null || content.compareTo("") == 0) 
{
return "<" + marker + "/>";

}
 else 
{
return "<" + marker + ">" + content + "</" + marker + ">";

}

}

protected void serializeTimeOnlyToXML(java.io.FileWriter writer, jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime time) throws java.io.IOException
{
if(! (time instanceof jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsPeriod)) this.serializeTimeToXML(writer, time);

}

protected void serializeTimeToXML(java.io.FileWriter writer, jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsTime time) throws java.io.IOException
{
jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsPeriod period;
java.lang.String strTime;
strTime = java.lang.Long.toString(time.getTime().getTime());
try
{
period = (jct.test.rsc.snpsht.verfilesystem.attribute.IVerFsPeriod)time;
strTime = "<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_UPDATE_TIME_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_UPDATE_TIME_TYPE_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_PERIOD_ATT_VAL + "\">";
strTime += this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_TIME_MAKER, java.lang.Long.toString(period.getTime().getTime()));
strTime += this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_PERIOD_START_MAKER, java.lang.Long.toString(period.getStartTime().getTime()));
strTime += this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_PERIOD_END_MAKER, java.lang.Long.toString(period.getEndTime().getTime()));

}
catch(java.lang.Exception e) 
{
strTime = "<" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_UPDATE_TIME_MAKER + " " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_UPDATE_TIME_TYPE_ATTRIBUTE + "=\"" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_TIME_ATT_VAL + "\">";
strTime += this.genMarker(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_TIME_MAKER, java.lang.Long.toString(time.getTime().getTime()));

}
strTime += "</" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_UPDATE_TIME_MAKER + ">";
writer.write(strTime);

}

protected java.io.File getTmpDir()
{
java.io.File tmpDir;
tmpDir = new java.io.File(java.lang.System.getProperty("java.io.tmpdir"), this.getClass().getName() + "_" + this.hashCode());
return tmpDir;

}


}
