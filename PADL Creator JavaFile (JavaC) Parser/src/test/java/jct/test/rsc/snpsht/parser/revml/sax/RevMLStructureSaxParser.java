/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
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

public class RevMLStructureSaxParser extends SimpleSaxFsmParser {

	private VerFsManager manager;

	public RevMLStructureSaxParser() {
		super();
	}

	public VerFsManager getManager() {
		return this.manager;
	}

	public VerFsManager parseStructure(String xmlFilePath, VerFsManager manager)
			throws SAXException, IOException, ParserConfigurationException,
			SaxFsmParseException {
		File xmlFile = new File(xmlFilePath);

		return parseStructure(xmlFile, manager);
	}

	public VerFsManager parseStructure(File xmlFile, VerFsManager manager) throws SAXException,
			IOException, ParserConfigurationException, SaxFsmParseException {

		this.manager = manager;

		parseStructure(xmlFile);

		return this.manager;
	}

	private void parseStructure(File xmlFile) throws SAXException, IOException,
			ParserConfigurationException, SaxFsmParseException {

		AbstractRevMLState startState =
			new BuildRepoStructStartState(this, this.manager);

		// First we build the repository/file structure
		AbstractStateSaxFsm st = this.parse(startState, xmlFile);

		// If the structure was not correctly built
		if (!(st instanceof BuildRepoStructEndState)) {
			throw new SaxFsmParseException(
				"RevML parsing error.\nError during RevML structure building.",
				st);
		}
	}
}
