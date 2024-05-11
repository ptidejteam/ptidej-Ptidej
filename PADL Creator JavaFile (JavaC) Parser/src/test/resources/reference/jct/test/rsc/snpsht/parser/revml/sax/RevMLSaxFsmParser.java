package jct.test.rsc.snpsht.parser.revml.sax;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadInfoEndState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadInfoStartState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructEndState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructStartState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SaxFsmParseException;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
public class RevMLSaxFsmParser
extends jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser
{
private jct.test.rsc.snpsht.verfilesystem.VerFsManager manager;

public void <init>()
{
this.<init>();

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager getManager()
{
return this.manager;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager buildRepository(java.lang.String xmlFilePath, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
java.io.File xmlFile = new java.io.File(xmlFilePath);
return this.buildRepository(xmlFile, manager);

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager buildRepository(java.io.File xmlFile, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
this.manager = manager;
this.buildStruct(xmlFile);
this.parseInfo(xmlFile);
return this.manager;

}

private void buildStruct(java.io.File xmlFile) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState startState = new jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructStartState(this, this.manager);
jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm st = this.parse(startState, xmlFile);
if(! (st instanceof jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructEndState)) 
{
throw new jct.test.rsc.snpsht.parser.sax.SaxFsmParseException("RevML parsing error.
Error during RevML structure building.", st);

}

}

private void parseInfo(java.io.File xmlFile) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm startState = new jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadInfoStartState(this, this.manager);
jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm st = this.parse(startState, xmlFile);
if(! (st instanceof jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader.LoadInfoEndState)) 
{
throw new jct.test.rsc.snpsht.parser.sax.SaxFsmParseException("RevML parsing error.
Error during RevML info loading.", st);

}

}

public void endElement(java.lang.String uri, java.lang.String localName, java.lang.String name) throws org.xml.sax.SAXException
{
super.endElement(uri, localName, name);

}

public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String name, org.xml.sax.Attributes attributes) throws org.xml.sax.SAXException
{
super.startElement(uri, localName, name, attributes);

}


}
