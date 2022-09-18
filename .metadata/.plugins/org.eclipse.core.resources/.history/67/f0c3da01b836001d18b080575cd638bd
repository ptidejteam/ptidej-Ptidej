package jct.test.rsc.snpsht.parser.sax;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
public class SimpleSaxFsmParser
extends org.xml.sax.helpers.DefaultHandler
{
final public static javax.xml.parsers.SAXParserFactory SAX_FACTORY = javax.xml.parsers.SAXParserFactory.newInstance();

private jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm state;

private javax.xml.parsers.SAXParser parser;

public void <init>()
{
this.<init>();

}

public jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm parse(jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm startState, java.lang.String xmlFilePath) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException
{
java.io.File xmlFile = new java.io.File(xmlFilePath);
return this.parse(startState, xmlFile);

}

public jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm parse(jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm startState, java.io.File xmlFile) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException
{
this.makeParser();
this.changeState(startState);
this.parser.parse(xmlFile, this);
return this.state;

}

public jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm getState()
{
return this.state;

}

public void changeState(jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm state)
{
this.state = state;

}

public javax.xml.parsers.SAXParser makeParser() throws javax.xml.parsers.ParserConfigurationException, org.xml.sax.SAXException
{
if(this.parser == null) 
{
this.parser = jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser.SAX_FACTORY.newSAXParser();

}
return this.parser;

}

public javax.xml.parsers.SAXParser getParser()
{
return this.parser;

}

public void characters(char[] ch, int start, int length) throws org.xml.sax.SAXException
{
this.state.characters(ch, start, length);

}

public void endDocument() throws org.xml.sax.SAXException
{
this.endDocument();

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
this.state.endElement(uri, localName, name);

}

public void error(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException
{
this.state.error(e);

}

public void fatalError(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException
{
this.state.fatalError(e);

}

public void endPrefixMapping(java.lang.String prefix) throws org.xml.sax.SAXException
{
this.state.endPrefixMapping(prefix);

}

public void ignorableWhitespace(char[] ch, int start, int length) throws org.xml.sax.SAXException
{
this.state.ignorableWhitespace(ch, start, length);

}

public void notationDecl(java.lang.String name, java.lang.String publicId, java.lang.String systemId) throws org.xml.sax.SAXException
{
this.state.notationDecl(name, publicId, systemId);

}

public void processingInstruction(java.lang.String target, java.lang.String data) throws org.xml.sax.SAXException
{
this.state.processingInstruction(target, data);

}

public void setDocumentLocator(org.xml.sax.Locator locator)
{
this.state.setDocumentLocator(locator);

}

public void skippedEntity(java.lang.String name) throws org.xml.sax.SAXException
{
this.state.skippedEntity(name);

}

public void startDocument() throws org.xml.sax.SAXException
{
this.state.startDocument();

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
this.state.startElement(uri, localName, name, attributes);

}

public void startPrefixMapping(java.lang.String prefix, java.lang.String uri) throws org.xml.sax.SAXException
{
this.state.startPrefixMapping(prefix, uri);

}

public void unparsedEntityDecl(java.lang.String name, java.lang.String publicId, java.lang.String systemId, java.lang.String notationName) throws org.xml.sax.SAXException
{
this.state.unparsedEntityDecl(name, publicId, systemId, notationName);

}

public void warning(org.xml.sax.SAXParseException e) throws org.xml.sax.SAXException
{
this.state.warning(e);

}


}
