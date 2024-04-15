package jct.test.rsc.snpsht.parser.verfilesystem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsNullEntity;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class VerFsFileRevParser
extends jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser
{
public void <init>()
{
this.<init>();

}

private javax.xml.parsers.DocumentBuilder domFilesIndex;

private javax.xml.parsers.DocumentBuilder domFileMetaInfo;

public void buildFile(java.lang.String filePathId, java.lang.String fileId) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
org.w3c.dom.Document fileIndex = this.getDocument(this.manager, filePathId, this.domFileMetaInfo);
org.w3c.dom.NodeList revs;
org.w3c.dom.Node revNode;
java.util.List revsInFile = new java.util.ArrayList();
java.lang.String id;
javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
try
{
revs = (org.w3c.dom.NodeList)xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVISIONS_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER, fileIndex, javax.xml.xpath.XPathConstants.NODESET);
for(int i = 0; i < revs.getLength(); i ++) 
{
revNode = revs.item(i);
id = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(revNode.getAttributes().getNamedItem(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE).getNodeValue());
revsInFile.add(this.manager.getSimpleRevision(id));

}
jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file = this.manager.setAsFile(revsInFile.toArray(new jct.test.rsc.snpsht.verfilesystem.VerFsFileRev[0]), fileId);
this.manager.setFileLocation(this.manager.getSourceManager().getFileEntity(filePathId), file);

}
catch(javax.xml.xpath.XPathExpressionException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.BAD_XPATH, e);

}

}

public void linkFileFilesRev(java.lang.String fileId) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
org.w3c.dom.Document fileIndex = this.getDocument(this.manager, fileId, this.domFileMetaInfo);
org.w3c.dom.NodeList revs;
org.w3c.dom.NodeList nextRevs;
org.w3c.dom.Node revNode;
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev currRev;
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev prevRev;
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev nextRev;
java.lang.String id;
java.lang.String attr;
javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
try
{
revs = (org.w3c.dom.NodeList)xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVISIONS_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER, fileIndex, javax.xml.xpath.XPathConstants.NODESET);
for(int i = 0; i < revs.getLength(); i ++) 
{
revNode = revs.item(i);
id = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(revNode.getAttributes().getNamedItem(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE).getNodeValue());
currRev = this.manager.getSimpleRevision(id);
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_PREV_REV_MAKER, revNode));
if(attr != null && attr.compareTo("") != 0) 
{
prevRev = this.manager.getSimpleRevision(attr);
if(prevRev == null) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.UNEXPECTED_NODE_VALUE, "Can't find file revision in manager : " + attr);

}
this.manager.setPrevRevision(prevRev, currRev);

}
nextRevs = (org.w3c.dom.NodeList)xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_NEXT_REV_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_MAKER, revNode, javax.xml.xpath.XPathConstants.NODESET);
for(int j = 0; j < nextRevs.getLength(); j ++) 
{
attr = nextRevs.item(j).getFirstChild().getNodeValue();
nextRev = this.manager.getSimpleRevision(attr);
if(nextRev == null) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.UNEXPECTED_NODE_VALUE, "Can't find file revision in manager : " + attr);

}
this.manager.addNextRevision(nextRev, currRev);

}

}

}
catch(javax.xml.xpath.XPathExpressionException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.BAD_XPATH, e);

}

}

public void buildFileFilesRev(java.lang.String filePathId) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
org.w3c.dom.Document fileIndex = this.getDocument(this.manager, filePathId, this.domFileMetaInfo);
org.w3c.dom.NodeList revs;
org.w3c.dom.NodeList tags;
org.w3c.dom.Node rev;
java.lang.String id;
java.lang.String path;
java.lang.String attr;
jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev;
jct.test.rsc.snpsht.filesystem.IFsFileEntity location;
try
{
javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
revs = (org.w3c.dom.NodeList)xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVISIONS_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_MAKER, fileIndex, javax.xml.xpath.XPathConstants.NODESET);
for(int i = 0; i < revs.getLength(); i ++) 
{
rev = revs.item(i);
id = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(rev.getAttributes().getNamedItem(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ID_ATTRIBUTE).getNodeValue());
path = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2PATH(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_PATH_MAKER, rev));
fileRev = this.manager.addSimpleRevision(path, id);
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_REV_MAKER, rev));
this.manager.setRevID(attr, fileRev);
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_ACTION_MAKER, rev));
this.manager.setAction(attr, fileRev);
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_AUTHOR_MAKER, rev));
this.manager.setAuthor(attr, fileRev);
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_COMMENT_MAKER, rev));
this.manager.setComment(attr, fileRev);
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_COMMENT_MAKER, rev));
this.manager.setComment(attr, fileRev);
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_BRANCH_MAKER, rev));
this.manager.setBranch(attr, fileRev);
tags = (org.w3c.dom.NodeList)xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_TAGS_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_TAG_MAKER, rev, javax.xml.xpath.XPathConstants.NODESET);
for(int j = 0; j < tags.getLength(); j ++) 
{
this.manager.addTag(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(tags.item(j).getFirstChild().getNodeValue()), fileRev);

}
if(this.existChild(rev, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_UPDATE_TIME_MAKER)) 
{
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_UPDATE_TIME_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_TIME_MAKER, rev));
this.manager.setUpdateTime(new java.util.Date(java.lang.Long.valueOf(attr)), fileRev);

}
attr = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2PATH(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_REV_FILE_LOCATION_MAKER, rev));
location = this.manager.getSourceManager().getFileEntity(attr);
if(location == null || location instanceof jct.test.rsc.snpsht.filesystem.IFsNullEntity) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.SOURCE_NOT_FOUND, "Can't find source file in manager : " + attr);

}
this.manager.setFileRevLocation(location, fileRev);

}

}
catch(javax.xml.xpath.XPathExpressionException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.BAD_XPATH, e);

}

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager parse(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
org.w3c.dom.NodeList files;
java.lang.String metaInfoPath;
java.lang.String rootRepo;
try
{
this.manager = manager;
if(this.domFileMetaInfo == null) 
{
this.domFileMetaInfo = this.getParser("/jct.test.rsc.snpsht.verfilesystem/resource/meta-info.xsd");

}
if(this.domFilesIndex == null) 
{
this.domFilesIndex = this.getParser("/jct.test.rsc.snpsht.verfilesystem/resource/files_index.xsd");

}
org.w3c.dom.Document fileIndex = this.getDocument(manager, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILES_INDEX_XML_FILE_NAME, this.domFilesIndex);
javax.xml.xpath.XPath xpath = javax.xml.xpath.XPathFactory.newInstance().newXPath();
rootRepo = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2PATH(xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILES_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REPO_ROOT_MAKER, fileIndex));
this.manager.makeRoot(rootRepo);
files = (org.w3c.dom.NodeList)xpath.evaluate("/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILES_MAKER + "/" + jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_MAKER, fileIndex, javax.xml.xpath.XPathConstants.NODESET);
for(int i = 0; i < files.getLength(); i ++) 
{
metaInfoPath = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2PATH(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_LOCATION_MARKER, files.item(i)));
this.buildFileFilesRev(metaInfoPath);

}
for(int i = 0; i < files.getLength(); i ++) 
{
metaInfoPath = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2PATH(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_LOCATION_MARKER, files.item(i)));
this.linkFileFilesRev(metaInfoPath);

}
for(int i = 0; i < files.getLength(); i ++) 
{
java.lang.String id = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(files.item(i).getAttributes().getNamedItem(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_ID_ATTRIBUTE).getNodeValue());
metaInfoPath = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2PATH(xpath.evaluate(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.FILE_LOCATION_MARKER, files.item(i)));
this.buildFile(metaInfoPath, id);

}

}
catch(javax.xml.xpath.XPathExpressionException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.BAD_XPATH, e);

}
return this.manager;

}


}
