/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.graph.io;

import infovis.Column;
import infovis.Graph;
import infovis.Table;
import infovis.io.AbstractXMLWriter;

import java.io.Writer;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Writer for the GraphML format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class GraphMLWriter extends AbstractXMLWriter {
    protected Graph graph;
    /** DefaultTable of edge labels */
    protected ArrayList edgeLabels;
    protected AttributesImpl attrs = new AttributesImpl();
    protected boolean interlaced;
	
    public GraphMLWriter(Writer out, Graph graph) {
	super(out, graph.getEdgeTable());
	this.graph = graph;
    }

    public void addEdgeLabel(String name) {
	if (this.edgeLabels == null) {
	    this.edgeLabels = new ArrayList();
	}

	this.edgeLabels.add(name);
    }
	
    public void addAllEdgeLabels() {
	int col;
	Table edges = this.graph.getEdgeTable();
		
	for (col = 0; col < edges.getColumnCount(); col++) {
	    Column c = edges.getColumnAt(col);

	    if (c.isInternal()) {
		continue;
	    }

	    addEdgeLabel(c.getName());
	}
    }
	
    public String getEdgeLabelAt(int col) {
	return (String)this.edgeLabels.get(col);
    }
	
    public ArrayList getEdgeLabels() {
	return this.edgeLabels;
    }
	
    /**
     * Writes the data associated with a specified edge.
     *
     * @param edge the edge.
     *
     * @exception SAXException passed from the underlying XMLWriter.
     */
    protected void writeEdge(int edge) throws SAXException {
	Table edgeTable = this.graph.getEdgeTable();
		
	indent(this.writer);
	this.attrs.clear();
	this.attrs.addAttribute("", "id", "id", "ID", "e"+edge);
	this.attrs.addAttribute("", "source", "source",
			   "IDREF", "n"+this.graph.getInVertex(edge));
	this.attrs.addAttribute("", "target", "tager",
			   "IDREF", "n"+this.graph.getOutVertex(edge));
	this.writer.startElement("", "edge", "", this.attrs);
	this.depth++;
	for (int col = 0; this.edgeLabels != null && col < this.edgeLabels.size(); col++) {
	    String label = getEdgeLabelAt(col);
	    Column c = edgeTable.getColumn(label);
			
	    if (! c.isValueUndefined(edge)) {
		indent(this.writer);
		this.attrs.clear();
		this.attrs.addAttribute("", "key", "key", "IDREF", c.getName());
		this.writer.startElement("", "data", "", this.attrs);
		this.writer.characters(c.getValueAt(edge));
		this.writer.endElement("data");
	    }
	}
		
	this.depth--;
	indent(this.writer);
	this.writer.endElement("edge");	
    }

    /**
     * Writes the data associated with a specified vertex.
     *
     * @param vertex the vertex.
     *
     * @exception SAXException passed from the underlying XMLWriter.
     */
    protected void writeVertex(int vertex) throws SAXException {
	ArrayList columnLabels = getColumnLabels();
	indent(this.writer);
	this.attrs.clear();
	this.attrs.addAttribute("", "id", "id", "ID", "n"+vertex);
	this.writer.startElement("", "node", "", this.attrs);
	this.depth++;
	for (int col = 0; columnLabels != null && col < columnLabels.size(); col++) {
	    String label = getColumnLabelAt(col);
	    Column c = this.graph.getVertexTable().getColumn(label);
			
	    if (! c.isValueUndefined(vertex)) {
		indent(this.writer);
		this.attrs.clear();
		this.attrs.addAttribute("", "key", "key", "IDREF", c.getName());
		this.writer.startElement("", "data", "", this.attrs);
		this.writer.characters(c.getValueAt(vertex));
		this.writer.endElement("data");
	    }
	}		
	this.depth--;
	indent(this.writer);
	this.writer.endElement("node");	
	if (this.interlaced) {
	    for (int edge = this.graph.getFirstEdge(vertex);
		 edge != Graph.NIL; edge = this.graph.getNextEdge(edge)) {
		writeEdge(edge);
	    }
	}
    }
	
    /**
     * @see infovis.io.AbstractTableWriter#write()
     */
    public boolean write() {
	ArrayList columnLabels = getColumnLabels();
	ArrayList edgeLabels = getEdgeLabels();

	int col;
		
	if (columnLabels == null) {
	    addAllColumnLabels();
	    columnLabels = getColumnLabels();
	}
		
	if (edgeLabels == null) {
	    addAllEdgeLabels();
	    edgeLabels = getEdgeLabels();
	}
	this.depth = 0;
	try {
	    this.writer.startDocument();
	    this.writer.flush();
	    this.out.write("<!DOCTYPE graphml SYSTEM 'GraphML.dtd'>\n");
	    this.writer.startElement("graphml");
	    this.depth++;
	    indent(this.writer);
	    this.attrs.addAttribute("", "edgedefault", "edgedefault", "CDATA", "directed");
	    this.writer.startElement("", "graph", "", this.attrs);
	    this.attrs.clear();
	    this.depth++;
	    this.attrs.addAttribute("", "id", "id", "ID", "0");
	    this.attrs.addAttribute("", "for", "for", "CDATA", "node");
		
	    for (col = 0; columnLabels != null && col < columnLabels.size(); col++) {
		String label = getColumnLabelAt(col);
		Column c = this.graph.getVertexTable().getColumn(label);
			
		indent(this.writer);
		this.attrs.setAttribute(0, "", "id", "id", "ID", c.getName());
		this.writer.startElement("", "key", "", this.attrs);
		this.writer.characters(namedType(c.getValueClass()));
		this.writer.endElement("key");
	    }
		
	    this.attrs.setAttribute(1, "", "for", "for", "CDATA", "edge");
	    Table edgeTable = this.graph.getEdgeTable();
	    for (col = 0; edgeLabels != null && col < edgeLabels.size(); col++) {
		String label = getEdgeLabelAt(col);
		Column c = edgeTable.getColumn(label);
			
		indent(this.writer);
		//attNameFor[0] = "id";	
		//attName[1] = c.getName();
		this.attrs.setAttribute(0, "", "id", "id", "ID", c.getName());
		this.writer.startElement("", "key", "", this.attrs);
		this.writer.characters(namedType(c.getValueClass()));
		this.writer.endElement("key");
	    }
		
	    for (int vertex = 0; vertex < this.graph.getVerticesCount(); vertex++) {
		writeVertex(vertex);
	    }
		
	    if (! this.interlaced) {
		for (int edge = 0; edge < this.graph.getEdgesCount(); edge++) {
		    writeEdge(edge);
		}
	    }
		
	    this.depth--;
	    indent(this.writer);
	    this.writer.endElement("graph");
	    this.depth--;
	    indent(this.writer);
	    this.writer.endElement("graphml");
	    this.writer.endDocument();
	}
	catch(Exception e) {
	    e.printStackTrace();
	    return false;
	}
	return true;
    }

    /**
     * Returns the interlaced.
     * @return boolean
     */
    public boolean isInterlaced() {
	return this.interlaced;
    }

    /**
     * Sets the interlaced.
     * @param interlaced The interlaced to set
     */
    public void setInterlaced(boolean interlaced) {
	this.interlaced = interlaced;
    }

}
