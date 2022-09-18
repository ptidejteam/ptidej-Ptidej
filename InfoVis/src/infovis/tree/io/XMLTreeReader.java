/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.io;

import infovis.Column;
import infovis.Tree;

import java.io.BufferedReader;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Tree Reader for the treemal DTD.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class XMLTreeReader extends SimpleXMLTreeReader {
    protected boolean inTree;
    protected boolean inDeclarations;
	
    /**
     * Constructor for XMLTreeReader.
     * @param in the <code>BufferedReader</in>
     * @param name a name,
     * @param tree the Tree.
     */
    public XMLTreeReader(BufferedReader in, String name, Tree tree) {
	super(in, name, tree);
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
        if (this.firstTag) {
	   if (qName.equals("tree"))
	      this.inTree = true;
          else
            throw new RuntimeException("Expected a tree toplevel element");
          this.firstTag = false;
        }
	else if (qName.equals("declarations") && this.inTree)
	    this.inDeclarations = true;
	else if (qName.equals("attributeDecl") && this.inDeclarations)
	    declareAttribute(
			     atts.getValue("name"),
			     atts.getValue("type"),
			     atts.getValue("control"));
	else if (qName.equals("branch") && this.inTree && ! this.inDeclarations)
	    addBranch(atts);
	else if (qName.equals("leaf") && this.inTree && ! this.inDeclarations)
	    addLeaf(atts);
	else if (qName.equals("attribute") && this.inTree && ! this.inDeclarations)
	    addAttribute(atts);
    }
	
    /**
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
	throws SAXException {
	if (qName.equals("tree") && this.inTree)
	    this.inTree = false;
	else if (qName.equals("declarations") && this.inTree)
	    this.inDeclarations = false;
	else if ((qName.equals("branch") || qName.equals("leaf"))
		 && this.inTree && ! this.inDeclarations) {
	    // The super class just pops the current node out of the stack.
	    super.endElement(namespaceURI, localName, qName);
	}
    }
	
    protected void declareAttribute(String name, String type, String control) {
	Column c = this.tree.getColumn(name);
	if (c == null) {
	    c = createColumn(typeNamed(type), name);
	    this.tree.addColumn(c);
	}
	else {
	    if (c.getValueClass() != typeNamed(type))
		throw new ClassCastException("column "+name+" already exists with an incompatible type");
	}
		
    }
	
    protected void addBranch(Attributes atts) {
	int node = createNode();
	this.parent.push(node);
    }
	
    protected void addLeaf(Attributes atts) {
	addBranch(atts);
    }
	
    protected void addAttribute(Attributes atts) {
	Column c = this.tree.getColumn(atts.getValue("name"));
	if (c == null)
	    throw new RuntimeException("invalid column named "+atts.getValue("name"));
	c.setValueOrNullAt(this.parent.top(), atts.getValue("value"));
    }
}
