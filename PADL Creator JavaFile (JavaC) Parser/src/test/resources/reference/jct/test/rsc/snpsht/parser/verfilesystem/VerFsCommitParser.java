package jct.test.rsc.snpsht.parser.verfilesystem;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jct.test.rsc.snpsht.parser.verfilesystem.cvsimpl.CvsFsConstraintParser;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class VerFsCommitParser
extends jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser
{
public void <init>()
{
this.<init>();

}

private javax.xml.parsers.DocumentBuilder domCommitsIndex;

public jct.test.rsc.snpsht.verfilesystem.VerFsManager parse(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
org.w3c.dom.NodeList commits;
org.w3c.dom.NodeList revs;
org.w3c.dom.Node commit;
org.w3c.dom.Node rev;
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev;
java.util.List commitFilesRev;
java.lang.String fileRevId;
try
{
this.manager = manager;
if(this.domCommitsIndex == null) 
{
this.domCommitsIndex = this.getParser("/jct.test.rsc.snpsht.verfilesystem/resource/commits_index.xsd");

}
org.w3c.dom.Document commitsIndex = this.getDocument(manager, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.COMMITS_INDEX_XML_FILE_NAME, this.domCommitsIndex);
javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
commits = (org.w3c.dom.NodeList)xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.COMMITS_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.COMMIT_MAKER, commitsIndex, javax.xml.xpath.XPathConstants.NODESET);
for(int i = 0; i < commits.getLength(); i ++) 
{
commit = commits.item(i);
commitFilesRev = new java.util.ArrayList();
revs = (org.w3c.dom.NodeList)xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVISIONS_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER, commit, javax.xml.xpath.XPathConstants.NODESET);
for(int j = 0; j < revs.getLength(); j ++) 
{
rev = revs.item(j);
fileRevId = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(rev.getAttributes().getNamedItem(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE).getNodeValue());
fileRev = this.manager.getSimpleRevision(fileRevId);
if(fileRev == null) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.UNEXPECTED_NODE_VALUE, "Can't find file revision in manager : " + fileRevId);

}
commitFilesRev.add(fileRev);

}
this.manager.setAsCommit(commitFilesRev.toArray(new jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[0]));

}
try
{
jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager cvsManager = (jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager)this.manager;
jct.test.rsc.snpsht.parser.verfilesystem.cvsimpl.CvsFsConstraintParser constraintParser = new jct.test.rsc.snpsht.parser.verfilesystem.cvsimpl.CvsFsConstraintParser();
this.manager = constraintParser.parse(cvsManager);

}
catch(java.lang.Exception e) 
{

}

}
catch(javax.xml.xpath.XPathExpressionException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.BAD_XPATH, e);

}
return this.manager;

}


}
