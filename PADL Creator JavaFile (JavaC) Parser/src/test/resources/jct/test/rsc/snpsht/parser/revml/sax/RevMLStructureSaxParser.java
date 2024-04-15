package jct.test.rsc.snpsht.parser.revml.sax;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructEndState;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructStartState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SaxFsmParseException;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import org.xml.sax.SAXException;
public class RevMLStructureSaxParser
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

public jct.test.rsc.snpsht.verfilesystem.VerFsManager parseStructure(java.lang.String xmlFilePath, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
java.io.File xmlFile = new java.io.File(xmlFilePath);
return this.parseStructure(xmlFile, manager);

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager parseStructure(java.io.File xmlFile, jct.test.rsc.snpsht.verfilesystem.VerFsManager manager) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
this.manager = manager;
this.parseStructure(xmlFile);
return this.manager;

}

private void parseStructure(java.io.File xmlFile) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
jct.test.rsc.snpsht.parser.revml.sax.fsm.AbstractRevMLState startState = new jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructStartState(this, this.manager);
jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm st = this.parse(startState, xmlFile);
if(! (st instanceof jct.test.rsc.snpsht.parser.revml.sax.fsm.structurebuilder.BuildRepoStructEndState)) 
{
throw new jct.test.rsc.snpsht.parser.sax.SaxFsmParseException("RevML parsing error.
Error during RevML structure building.", st);

}

}


}
