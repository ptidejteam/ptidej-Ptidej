/*
 * $Id: Parser.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import fr.emn.oadymppac.SAXSolver;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      EMN
 * @author Mohammad Ghoniem
 * @version 1.0
 */

public class Parser implements Runnable, ErrorHandler, EntityResolver,
		DTDHandler {
	InputSource source;
	SAXSolver solver;
	XMLReader reader;

	public Parser() throws SAXException, ParserConfigurationException {
		this(new SAXSolver(), SAXParserFactory
			.newInstance()
			.newSAXParser()
			.getXMLReader());
	}
	public Parser(final InputSource source) throws SAXException,
			ParserConfigurationException {
		this(source, new SAXSolver(), SAXParserFactory
			.newInstance()
			.newSAXParser()
			.getXMLReader());
	}
	public Parser(
		final InputSource source,
		final SAXSolver solver,
		final XMLReader reader) {
		this.source = source;
		this.solver = solver;
		this.reader = reader;
		reader.setContentHandler(solver);
		reader.setErrorHandler(this);
		reader.setEntityResolver(this);
		reader.setDTDHandler(this);
	}
	public Parser(final InputStream byteStream) throws SAXException,
			ParserConfigurationException {
		this(new InputSource(byteStream));
	}
	public Parser(final Reader characterStream) throws SAXException,
			ParserConfigurationException {
		this(new InputSource(characterStream));
	}
	public Parser(final SAXSolver solver) throws SAXException,
			ParserConfigurationException {
		this(solver, SAXParserFactory
			.newInstance()
			.newSAXParser()
			.getXMLReader());
	}
	public Parser(final SAXSolver solver, final XMLReader reader) {
		this(null, solver, reader);
	}

	public Parser(final String systemId) throws SAXException,
			ParserConfigurationException {
		this(new InputSource(systemId));
	}

	public void error(final SAXParseException exception) {
		System.err.println("Error:");
		exception.printStackTrace();
	}
	public void fatalError(final SAXParseException exception) {
		System.err.println("Fatal Error:");
		exception.printStackTrace();
		System.exit(1);
	}
	public SAXSolver getSolver() {
		return this.solver;
	}
	public XMLReader getXMLReader() {
		return this.reader;
	}
	public void notationDecl(
		final String name,
		final String publicId,
		final String systemId) {
	}

	public void parse() {
		try {
			this.reader.parse(this.source);
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
	public void parse(final InputSource source) {
		this.source = source;
		this.parse();
	}
	public InputSource resolveEntity(
		final String publicId,
		final String systemId) {
		//System.err.println("pubid = " + publicId + ", sysid = " + systemId);
		try {
			return new InputSource(new FileInputStream(systemId));
		}
		catch (final FileNotFoundException e) {
			//System.err.println("Cannot open file " + systemId);
			return null;
		}
	}
	public void run() {
		this.parse();
	}
	public void unparsedEntityDecl(
		final String name,
		final String publicId,
		final String systemId,
		final String notationName) {
	}

	public void warning(final SAXParseException exception) {
		System.err.println("Warning:");
		exception.printStackTrace();
	}

}
