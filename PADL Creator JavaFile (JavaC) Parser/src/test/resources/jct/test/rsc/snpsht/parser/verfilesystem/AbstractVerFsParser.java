package jct.test.rsc.snpsht.parser.verfilesystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.filesystem.IFsManager;
import jct.test.rsc.snpsht.filesystem.IFsNullEntity;
import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
abstract public class AbstractVerFsParser
implements jct.test.rsc.snpsht.parser.verfilesystem.IVerFsParser
{
public void <init>()
{
this.<init>();

}

protected jct.test.rsc.snpsht.verfilesystem.VerFsManager manager;

final private static javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();

public jct.test.rsc.snpsht.verfilesystem.VerFsManager getManager()
{
return this.manager;

}

protected org.w3c.dom.Document getDocument(java.io.File xmlFile, javax.xml.parsers.DocumentBuilder dom) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
try
{
return this.getDocument(new java.io.FileInputStream(xmlFile), dom);

}
catch(java.io.FileNotFoundException e) 
{
e.printStackTrace();
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.SOURCE_NOT_FOUND, e);

}

}

protected org.w3c.dom.Document getDocument(java.io.InputStream xmlInputStream, javax.xml.parsers.DocumentBuilder dom) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
try
{
return dom.parse(xmlInputStream);

}
catch(org.xml.sax.SAXException e) 
{
e.printStackTrace();
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.PARSING_FAILED, e);

}
catch(java.io.IOException e) 
{
e.printStackTrace();
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.PARSING_FAILED, e);

}

}

protected org.w3c.dom.Document getDocument(jct.test.rsc.snpsht.verfilesystem.VerFsManager manager, java.lang.String xmlFileId, javax.xml.parsers.DocumentBuilder dom) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
jct.test.rsc.snpsht.filesystem.IFsManager fsManager;
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity;
fsManager = manager.getSourceManager();
entity = fsManager.getFileEntity(xmlFileId);
if(entity == null || entity instanceof jct.test.rsc.snpsht.filesystem.IFsNullEntity) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.SOURCE_NOT_FOUND, "Can't find files index XML file. Should be in " + xmlFileId);

}
try
{
return this.getDocument(fsManager.getInputStrem((jct.test.rsc.snpsht.filesystem.IFsRealEntity)entity), dom);

}
catch(java.io.IOException e) 
{
e.printStackTrace();
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.PARSING_FAILED, e);

}

}

protected javax.xml.parsers.DocumentBuilder getParser(java.lang.String schemaResourcePath) throws jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException
{
try
{
java.io.File schema = new java.io.File(class.getResource(schemaResourcePath).toURI());
jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser.factory.setSchema(javax.xml.validation.SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schema));
jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser.factory.setNamespaceAware(true);

}
catch(java.net.URISyntaxException e) 
{
java.lang.System.err.println("Invalid Schema/Cann't find the Schema");
e.printStackTrace();
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.PARSING_FAILED, e);

}
catch(org.xml.sax.SAXException e) 
{
java.lang.System.err.println("Invalid Schema/Cann't find the Schema");
e.printStackTrace();
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.PARSING_FAILED, e);

}
try
{
javax.xml.parsers.DocumentBuilder db = jct.test.rsc.snpsht.parser.verfilesystem.AbstractVerFsParser.factory.newDocumentBuilder();
return db;

}
catch(javax.xml.parsers.ParserConfigurationException e) 
{
throw new jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException(jct.test.rsc.snpsht.parser.verfilesystem.VerFsParserException.PARSING_FAILED, e);

}

}

protected org.w3c.dom.Element findChildElement(org.w3c.dom.Node parent, java.lang.String childName)
{
org.w3c.dom.Node child = this.findChildType(parent, childName, org.w3c.dom.Node.ELEMENT_NODE);
return (child == null ? null : (org.w3c.dom.Element)child);

}

protected org.w3c.dom.Text findChildText(org.w3c.dom.Node parent, java.lang.String childName)
{
org.w3c.dom.Node child = this.findChildType(parent, childName, org.w3c.dom.Node.TEXT_NODE);
return (child == null ? null : (org.w3c.dom.Text)child);

}

protected org.w3c.dom.Node findChild(org.w3c.dom.Node parent, java.lang.String childName)
{
for(org.w3c.dom.Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) 
{
if(child.getNodeName().compareTo(childName) == 0) 
{
return child;

}

}
return null;

}

protected boolean existChild(org.w3c.dom.Node parent, java.lang.String childName)
{
return (this.findChild(parent, childName) != null);

}

private org.w3c.dom.Node findChildType(org.w3c.dom.Node parent, java.lang.String childName, short type)
{
for(org.w3c.dom.Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) 
{
if(child.getNodeType() == type && child.getNodeName().compareTo(childName) == 0) 
{
return child;

}

}
return null;

}


}
