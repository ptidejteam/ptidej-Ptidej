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
package jct.test.rsc.snpsht.parser.verfilesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import util.io.ProxyConsole;

public abstract class AbstractVerFsParser implements IVerFsParser {
	protected VerFsManager manager;

	/** DOM Parser Factory */
	private static final DocumentBuilderFactory factory =
		DocumentBuilderFactory.newInstance();

	@Override
	public VerFsManager getManager() {
		return this.manager;
	}

	protected Document getDocument(File xmlFile, DocumentBuilder dom)
			throws VerFsParserException {
		try {
			return getDocument(new FileInputStream(xmlFile), dom);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new VerFsParserException(
				VerFsParserException.SOURCE_NOT_FOUND,
				e);
		}
	}

	protected Document getDocument(
		InputStream xmlInputStream,
		DocumentBuilder dom) throws VerFsParserException {

		try {
			return dom.parse(xmlInputStream);
		}
		catch (SAXException e) {
			e.printStackTrace();
			throw new VerFsParserException(
				VerFsParserException.PARSING_FAILED,
				e);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new VerFsParserException(
				VerFsParserException.PARSING_FAILED,
				e);
		}
	}

	protected Document getDocument(
		VerFsManager manager,
		String xmlFileId,
		DocumentBuilder dom) throws VerFsParserException {
		IFsManager fsManager;
		IFsFileEntity entity;

		fsManager = manager.getSourceManager();

		entity = fsManager.getFileEntity(xmlFileId);
		if (entity == null || entity instanceof IFsNullEntity) {
			throw new VerFsParserException(
				VerFsParserException.SOURCE_NOT_FOUND,
				"Can't find files index XML file. Should be in " + xmlFileId);
		}

		try {
			return getDocument(
				fsManager.getInputStrem((IFsRealEntity) entity),
				dom);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new VerFsParserException(
				VerFsParserException.PARSING_FAILED,
				e);
		}

	}

	protected DocumentBuilder getParser(String schemaResourcePath)
			throws VerFsParserException {

		// Set schema to factory
		try {
			File schema =
				new File(VerFsFileRevParser.class.getResource(
					schemaResourcePath).toURI());
			factory.setSchema(SchemaFactory.newInstance(
				XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schema));
			factory.setNamespaceAware(true);
		}
		catch (java.net.URISyntaxException e) {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println("Invalid Schema/Cann't find the Schema");
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new VerFsParserException(
				VerFsParserException.PARSING_FAILED,
				e);
		}
		catch (SAXException e) {
			ProxyConsole
				.getInstance()
				.errorOutput()
				.println("Invalid Schema/Cann't find the Schema");
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			throw new VerFsParserException(
				VerFsParserException.PARSING_FAILED,
				e);
		}

		// Build jct.test.rsc.snpsht.parser, and add error handler
		// Error handler will set this.parsingError, so a value != null means an
		// error appears
		try {
			DocumentBuilder db = factory.newDocumentBuilder();

			return db;
		}
		catch (ParserConfigurationException e) {
			throw new VerFsParserException(
				VerFsParserException.PARSING_FAILED,
				e);
		}
	}

	protected Element findChildElement(Node parent, String childName) {
		Node child = findChildType(parent, childName, Node.ELEMENT_NODE);
		return (child == null ? null : (Element) child);
	}

	protected Text findChildText(Node parent, String childName) {
		Node child = findChildType(parent, childName, Node.TEXT_NODE);
		return (child == null ? null : (Text) child);
	}

	protected Node findChild(Node parent, String childName) {
		for (Node child = parent.getFirstChild(); child != null; child =
			child.getNextSibling()) {
			if (child.getNodeName().compareTo(childName) == 0) {
				return child;
			}
		}
		return null;
	}

	protected boolean existChild(Node parent, String childName) {
		return (findChild(parent, childName) != null);
	}

	private Node findChildType(Node parent, String childName, short type) {
		for (Node child = parent.getFirstChild(); child != null; child =
			child.getNextSibling()) {
			if (child.getNodeType() == type
					&& child.getNodeName().compareTo(childName) == 0) {
				return child;
			}
		}
		return null;
	}
}
