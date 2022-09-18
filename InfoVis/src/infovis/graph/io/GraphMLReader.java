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
import infovis.column.StringColumn;
import infovis.io.AbstractXMLReader;
import infovis.io.WrongFormatException;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


/**
 * GraphML format reader.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class GraphMLReader extends AbstractXMLReader {
    protected Graph        graph;
    protected int          inNode;
    protected int          inEdge;
    protected String       keyFor;
    protected String       ID;
    protected StringBuffer characters;
    protected StringColumn nodeIdColumn;
    protected StringColumn edgeIdColumn;
    protected Map          nodeMap;

    /**
     * Constructor for GraphMLReader.
     * 
     * @param in the BufferedReader
     * @param name the graph name
     * @param graph the graph.
     */
    public GraphMLReader(BufferedReader in, String name, Graph graph) {
        super(in, name);
        this.graph = graph;
    }

    /**
     * Declare a key.
     *
     * @param keyFor category the key is for, can be graph, edge or node.
     * @param ID identificator for this key.
     * @param type data type of this key.
     */
    public void declareKey(String keyFor, String ID, String type) {
        Class cl = typeNamed(type);
        if (cl == null)
            cl = String.class;

        if (keyFor.equals("node")) {
            Column c = this.graph.getVertexTable().getColumn(ID);
            if (c == null) {
                c = createColumn(cl, ID);
                this.graph.getVertexTable().addColumn(c);
            } else if (c.getClass() != cl) {
                throw new RuntimeException("bad column type " + c.getClass() +
                                           " instead of " + cl);
            }
        } else if (keyFor.equals("graph")) {
        } else if (keyFor.equals("edge")) {
            Table  edgeTable = this.graph.getEdgeTable();
            Column c = edgeTable.getColumn(ID);
            if (c == null) {
                c = createColumn(cl, ID);
                edgeTable.addColumn(c);
            } else if (c.getClass() != cl) {
                throw new RuntimeException("bad column type " + c.getClass() +
                                           " instead of " + cl);
            }
        }
    }

    /**
     * Returns a n ode given its unique id.
     *
     * @param id the unique id.
     *
     * @return a n ode given its unique id.
     */
    public int findNode(String id) {
        Integer i = (Integer)this.nodeMap.get(id);
        if (i == null) {
            int node = this.graph.addVertex();
            this.nodeIdColumn.setExtend(node, id);
            this.nodeMap.put(id, Integer.valueOf(node));
            return node;
        } else
            return i.intValue();
    }

    /**
     * @see org.xml.sax.ContentHandler#startDocument()
     */
    public void startDocument() throws SAXException {
        this.inNode = this.inEdge = Graph.NIL;
        this.nodeMap = new HashMap();
        this.nodeIdColumn = StringColumn.findColumn(this.graph.getVertexTable(), "id");
        this.edgeIdColumn = StringColumn.findColumn(this.graph.getEdgeTable(), "id");
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
     */
    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts)
                      throws SAXException {
        if (this.firstTag) {
       if (!qName.equals("graphml"))
            throw new WrongFormatException("Expected a graph toplevel element");
          this.firstTag = false;
        }
        if (qName.equals("key")) {
            this.keyFor = atts.getValue("for");
            this.ID = atts.getValue("id");
            this.characters = new StringBuffer();
        } else if (qName.equals("node")) {
            this.ID = atts.getValue("id");
            this.inNode = findNode(this.ID);
        } else if (qName.equals("edge")) {
            String source = atts.getValue("source");
            String target = atts.getValue("target");

            this.ID = atts.getValue("id");
            int from = findNode(source);
            int to = findNode(target);
            this.inEdge = this.graph.addEdge(from, to);
            this.edgeIdColumn.setExtend(this.inEdge, this.ID);
        } else if (qName.equals("data")) {
            if (this.inNode != Graph.NIL || this.inEdge != Graph.NIL) {
                this.keyFor = atts.getValue("key");
                this.characters = new StringBuffer();
            }
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(String, String, String)
     */
    public void endElement(String namespaceURI, String localName, String qName)
                    throws SAXException {
        if (qName.equals("key")) {
            declareKey(this.keyFor, this.ID, this.characters.toString());
            this.characters = null;
            this.ID = null;
        } else if (qName.equals("node")) {
            this.inNode = Graph.NIL;
            this.ID = null;
        } else if (qName.equals("edge")) {
            this.inEdge = Graph.NIL;
            this.ID = null;
        } else if (qName.equals("data")) {
            Column c = null;
            int row = Graph.NIL;
            if (this.inNode != Graph.NIL) {
                c = this.graph.getVertexTable().getColumn(this.keyFor);
                row = this.inNode;
            }
            else if (this.inEdge != Graph.NIL) {
                c = this.graph.getEdgeTable().getColumn(this.keyFor);
                row = this.inEdge;
            }
            
            if (c != null) {
                String value = this.characters.toString();
                c.setValueOrNullAt(row, value);
            }
            this.characters = null;
            this.keyFor = null;
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char[], int, int)
     */
    public void characters(char[] ch, int start, int length)
                    throws SAXException {
        if (this.characters != null) {
            this.characters.append(ch, start, length);
        }
    }
}
