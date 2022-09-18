/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


/**
 * Abstract Reader for XML format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractXMLReader extends AbstractReader
    implements ContentHandler, EntityResolver {
    /** <code>true</code> when reading the first tag. */
    protected boolean firstTag;
    protected XMLReader reader;
    protected SAXParser parser;
    /**
     * Creates a new AbstractXMLReader object.
     *
     * @param in DOCUMENT ME!
     * @param name DOCUMENT ME!
     */
    public AbstractXMLReader(BufferedReader in, String name) {
        super(in, name);
    }

    /**
     * @see infovis.io.AbstractTableReader#load()
     */
    public boolean load() {
        this.firstTag = true;
        try {
            SAXParserFactory p = SAXParserFactory.newInstance();
            p.setValidating(false);
            this.parser = p.newSAXParser();
            this.reader = this.parser.getXMLReader();
            this.reader.setContentHandler(this);
            this.reader.setEntityResolver(this);
            InputSource source = new InputSource(this.in);
            source.setSystemId(getName());
            this.reader.parse(source);
        } catch (FactoryConfigurationError e) {
            return false;
        } catch(Exception e) {
            return false;
        } finally {
            try {
                this.in.close();
            } catch (IOException e) {
            }
        }
        return true;
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int length)
	throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#endDocument()
     */
    public void endDocument() throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#endPrefixMapping(String)
     */
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
     */
    public void ignorableWhitespace(char[] ch, int start, int length)
	throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#processingInstruction(String, String)
     */
    public void processingInstruction(String target, String data)
	throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#setDocumentLocator(Locator)
     */
    public void setDocumentLocator(Locator locator) {
    }

    /**
     * @see org.xml.sax.ContentHandler#skippedEntity(String)
     */
    public void skippedEntity(String name) throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
     */
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts)
	throws SAXException {
    }

    /**
     * @see org.xml.sax.ContentHandler#startPrefixMapping(String, String)
     */
    public void startPrefixMapping(String prefix, String uri)
	throws SAXException {
    }

    /**
     * @see org.xml.sax.EntityResolver#resolveEntity(String, String)
     */
    public InputSource resolveEntity(String publicId, String systemId)
	throws SAXException, IOException {
        File file = new File(systemId);

        if (file.exists()) {
            return new InputSource(file.getAbsolutePath());
        }
        
        File dir = new File(getName());
        file = new File(dir.getParentFile(), systemId);
        if (file.exists()) {
            return new InputSource(file.getAbsolutePath());
        }
        
        return null;
    }
}
