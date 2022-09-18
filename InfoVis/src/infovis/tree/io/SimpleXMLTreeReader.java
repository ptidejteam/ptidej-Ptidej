/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.io;

import infovis.Tree;
import infovis.column.StringColumn;
import infovis.io.AbstractXMLReader;
import infovis.utils.IntVector;

import java.io.BufferedReader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Simple Reader for an XML tree.  Reads the tag names.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class SimpleXMLTreeReader extends AbstractXMLReader {
    Tree tree;
    IntVector parent;
    StringColumn qname;
	
    /**
     * Constructor for SimpleXMLTreeReader.
     *
     * @param in the BufferedReader.
     * @param name the file name.
     * @param tree the Tree.
     */
    public SimpleXMLTreeReader(BufferedReader in, String name, Tree tree) {
	super(in, name);
	this.tree = tree;
	this.parent = new IntVector();
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {
	this.parent.pop();
    }

    /**
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException {
	this.parent.clear();
		
    }

    public int createNode() {
        return this.parent.isEmpty() ? Tree.ROOT : this.tree.addNode(this.parent.top());
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
     */
    public void startElement(
			     String namespaceURI,
			     String localName,
			     String qName,
			     Attributes atts)
	throws SAXException {
	if (this.qname == null)
	    this.qname = StringColumn.findColumn(this.tree, "qname");
        
	int node = createNode();
	this.qname.set(node, qName);
	this.parent.push(node);
    }


}
