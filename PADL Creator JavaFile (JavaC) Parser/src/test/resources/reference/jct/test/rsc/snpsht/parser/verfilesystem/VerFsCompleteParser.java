package jct.test.rsc.snpsht.parser.verfilesystem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jct.test.rsc.snpsht.filesystem.FsManagerFactory;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsManager;
import jct.test.rsc.snpsht.filesystem.IFsNullEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import org.w3c.dom.Document;
public class VerFsCompleteParser
extends jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser
{
private javax.xml.parsers.DocumentBuilder domGeneralInfoIndex;

private java.util.List parsers;

public void <init>()
{
this.<init>();
this.parsers = new java.util.ArrayList();

}

public void addParser(jct.test.rsc.snpsht.parser.verfilesystem.IVerFsParser parser)
{
this.parsers.add(parser);

}

public void removeParser(jct.test.rsc.snpsht.parser.verfilesystem.IVerFsParser parser)
{
this.parsers.remove(parser);

}

public java.util.List getParsers()
{
return this.parsers;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager parse(java.io.File source) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity;
java.lang.String type;
jct.test.rsc.snpsht.filesystem.IFsManager fsManager;
try
{
fsManager = jct.test.rsc.snpsht.filesystem.FsManagerFactory.getNewManager(source);

}
catch(java.io.IOException e1) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.SOURCE_NOT_FOUND, "Can't build a IFsManager with " + source.getAbsolutePath());

}
entity = fsManager.getFileEntity(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_XML_FILE_NAME);
if(entity == null || entity instanceof jct.test.rsc.snpsht.filesystem.IFsNullEntity) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.SOURCE_NOT_FOUND, "Can't find general info XML file. Should be in " + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_XML_FILE_NAME);

}
try
{
if(this.domGeneralInfoIndex == null) 
{
this.domGeneralInfoIndex = this.getParser("/jct.test.rsc.snpsht.verfilesystem/resource/repo_general_info.xsd");

}
org.w3c.dom.Document generalInfo;
try
{
generalInfo = this.getDocument(fsManager.getInputStrem((jct.test.rsc.snpsht.filesystem.IFsRealEntity)entity), this.domGeneralInfoIndex);

}
catch(java.io.IOException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.SOURCE_NOT_FOUND, e.getMessage());

}
javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
type = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_TYPE_MAKER, generalInfo));
if(type.compareTo(jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_TYPE_ATTRIBUTE_VALUE) == 0) 
{
this.manager = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager();
try
{
this.manager.setSource(source);

}
catch(java.io.IOException e1) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.SOURCE_NOT_FOUND, e1.getMessage());

}

}
 else 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.UNEXPECTED_NODE_VALUE, "Can't build a manager for type : " + type);

}

}
catch(javax.xml.xpath.XPathExpressionException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.BAD_XPATH, e);

}
return this.parse(this.manager);

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager parse(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
for(jct.test.rsc.snpsht.parser.verfilesystem.IVerFsParser parser : this.parsers) 
{
this.manager = parser.parse(this.manager);

}
return this.manager;

}


}
