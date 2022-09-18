package jct.test.rsc.snpsht.parser.verfilesystem.cvsimpl;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser;
import jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings;
import jct.test.rsc.snpsht.utils.cvsutils.CVSRoot;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import org.w3c.dom.Document;
public class CvsFsGeneralInfoParser
extends jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser
{
protected jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager manager;

private javax.xml.parsers.DocumentBuilder domGeneralInfoIndex;

public void <init>()
{
this.<init>();

}

public jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager getManager()
{
return this.manager;

}

private void loadCvsInfo(org.w3c.dom.Document generalInfo) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
try
{
javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
java.lang.String descr;
java.lang.String revMlVer;
descr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_INFO_DESCR_MAKER, generalInfo));
revMlVer = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_INFO_REVML_VERSION_MAKER, generalInfo));
this.manager.setDescription(descr);
this.manager.setRevmlVersion(revMlVer);

}
catch(javax.xml.xpath.XPathExpressionException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.BAD_XPATH, e);

}

}

private void loadCvsRoot(org.w3c.dom.Document generalInfo) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
java.lang.String server;
java.lang.String protocol;
java.lang.String user;
java.lang.String pass;
java.lang.String separator;
java.lang.String rootRepo;
java.lang.String currRepo;
jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot;
try
{
javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
protocol = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_PROTOCOL_MAKER, generalInfo));
server = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_SERVER_MAKER, generalInfo));
user = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_USER_MAKER, generalInfo));
pass = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_PASSWORD_MAKER, generalInfo));
currRepo = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_CURRENT_REPO_MAKER, generalInfo));
rootRepo = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_ROOT_REPO_MAKER, generalInfo));
separator = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl.CvsFsCommonStrings.CVS_ROOT_SEPARATOR_MAKER, generalInfo));
cvsRoot = new jct.test.rsc.snpsht.utils.cvsutils.CVSRoot(protocol, server, rootRepo, currRepo);
if(user != null && user.compareTo("") != 0) 
{
cvsRoot.setUser(user);

}
if(pass != null && pass.compareTo("") != 0) 
{
cvsRoot.setPassword(pass);

}
if(separator != null && separator.compareTo("") != 0) 
{
cvsRoot.setSeparator(separator);

}
this.manager.setCvsRoot(cvsRoot);

}
catch(javax.xml.xpath.XPathExpressionException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.BAD_XPATH, e);

}

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager parse(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
try
{
this.manager = (jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager)manager;

}
catch(java.lang.Exception e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.ILLEGAL_MANAGER_TYPE, "Manger must be a CvsFsManager but is " + manager.getClass().getName());

}
if(this.domGeneralInfoIndex == null) 
{
this.domGeneralInfoIndex = this.getParser("/jct.test.rsc.snpsht.verfilesystem/resource/repo_general_info.xsd");

}
org.w3c.dom.Document generalInfo = this.getDocument(manager, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.GENERAL_INFO_XML_FILE_NAME, this.domGeneralInfoIndex);
this.loadCvsInfo(generalInfo);
this.loadCvsRoot(generalInfo);
return this.manager;

}


}
