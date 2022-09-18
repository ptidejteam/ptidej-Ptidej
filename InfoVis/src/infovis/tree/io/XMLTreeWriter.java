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
import infovis.io.AbstractXMLWriter;
import infovis.tree.DepthFirst;

import java.io.Writer;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.megginson.sax.XMLWriter;

/**
 * Writer according to the treeml DTD.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class XMLTreeWriter extends AbstractXMLWriter {
    protected Tree tree;
    /**
     * Constructor for XMLTreeWriter.
     * @param out
     * @param tree
     */
    public XMLTreeWriter(Writer out, Tree tree) {
	super(out, tree);
	this.tree = tree;
    }

    /**
     * @see infovis.io.AbstractTableWriter#write()
     */
    public boolean write() {
	int col;
		
		
	ArrayList labels = getColumnLabels();
	if (labels == null) {
	    addAllColumnLabels();
	    labels = getColumnLabels();
	}
		
	if (labels.size() == 0)
	    return false;
	final XMLWriter writer = new XMLWriter(this.out);
	writer.setStandalone(false);
	this.depth = 0;
		
	try {
	    writer.startDocument();
	    writer.flush();
	    this.out.write("<!DOCTYPE tree SYSTEM \"treeml.dtd\">\n");
	    writer.startElement("tree");
	    this.depth++;
	    indent(writer);
	    writer.startElement("declarations");
	    this.depth++;
	    for (col = 0; col < labels.size(); col++) {
		String label = getColumnLabelAt(col);
		AttributesImpl atts = new AttributesImpl();
			
		Column c = this.tree.getColumn(label);
		indent(writer);
		atts.addAttribute("", "name", "", "CDATA", label);
		atts.addAttribute("", "type", "", "CDATA", namedType(c.getValueClass()));
		writer.emptyElement("", "attributeDecl", "", atts);
	    }
	    this.depth--;
	    indent(writer);
	    writer.endElement("declarations");
		
	    DepthFirst.visit(this.tree, new DepthFirst.Visitor() {
		    public boolean preorder(int node) {
			//AttributesImpl atts = createAttributes(node);
			indent(writer);
				
			try {
			    if (XMLTreeWriter.this.tree.isLeaf(node)) {
				writer.startElement("leaf");
				writeAttributes(node, writer);
				writer.endElement("leaf");
				return false;
			    }
			    else {
				XMLTreeWriter.this.depth++;
				writer.startElement("branch");
				writeAttributes(node, writer);
				return true;
			    }
			}
			catch(SAXException e) {
			}
			return false;
		    }
		    public void postorder(int node) {
			XMLTreeWriter.this.depth--;
			indent(writer);
			if (! XMLTreeWriter.this.tree.isLeaf(node)) {
			    try {
				writer.endElement("branch");
			    }
			    catch(SAXException e) {
			    }
			}
		    }
		    /**
		     * @see infovis.Tree.DepthFirstVisitor#inorder(int)
		     */
		    public void inorder(int node) {
		    }

		});
	    this.depth--;
	    indent(writer);
	    writer.endElement("tree");
	    writer.endDocument();
	}
	catch(Exception e) {
	    return false;
	}
	return true;
    }
}
