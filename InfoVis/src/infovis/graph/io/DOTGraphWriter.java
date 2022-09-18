/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.io;

import infovis.Graph;
import infovis.column.ObjectColumn;
import infovis.column.StringColumn;
import infovis.io.AbstractTableWriter;
import infovis.utils.RowIterator;
import infovis.visualization.Orientable;

import java.awt.Dimension;
import java.awt.Shape;
import java.io.IOException;
import java.io.Writer;


/**
 * Class DOTGraphWriter
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DOTGraphWriter extends AbstractTableWriter implements Orientable {
    public static final String ID_COLUMN = "id";
    protected Graph graph;
    protected StringColumn vertexIdColumn;
    protected StringColumn edgeIdColumn;
    protected ObjectColumn shapes;
    protected Dimension size;
    protected int orientation = ORIENTATION_SOUTH;
    protected String layoutRatio;
    
    public DOTGraphWriter(Writer out, Graph graph) {
        super(out, graph.getEdgeTable());
        this.graph = graph;
        this.vertexIdColumn = StringColumn.findColumn(graph.getVertexTable(), ID_COLUMN);
        this.edgeIdColumn = StringColumn.findColumn(graph.getEdgeTable(), ID_COLUMN);
    }
    
    public String getVertexId(int vertex) {
        if (this.vertexIdColumn.isValueUndefined(vertex)) {
            String id = "v"+vertex;
            this.vertexIdColumn.setExtend(vertex, id);
            return id;
        }
        return this.vertexIdColumn.get(vertex);
    }
    
    public String getEdgeId(int edge) {
        if (this.edgeIdColumn.isValueUndefined(edge)) {
            String id = "e"+edge;
            this.edgeIdColumn.setExtend(edge, id);
            return id;
        }
        return this.edgeIdColumn.get(edge);
    }
    
    public void writeVertex(int vertex) throws IOException {
        write("\""+getVertexId(vertex)+"\"");
    }
    
    public void writeEdge(int edge) throws IOException {
        int vertex = this.graph.getInVertex(edge);
        int otherVertex = this.graph.getOutVertex(edge);

        writeVertex(vertex);
        if (this.graph.isDirected()) {
            write("->");
        }
        else {
            write("--");
        }
        writeVertex(otherVertex);
    }
    
    public boolean write() {
        try {
            if (this.graph.isDirected()) {
                write ("digraph Infovis {\n");
            }
            else {
                write("graph Infovis {\n");
            }
            if (this.size != null) {
                write(" ");
                write("size=\""+this.size.width/72.0+","+this.size.height/72.0+"\";\n");
            }
            if (this.orientation == ORIENTATION_EAST || this.orientation == ORIENTATION_WEST) {
                write(" ");
                write("rankdir=LR;\n");
            }
            if (this.layoutRatio != null) {
                write(" ");
                write("ratio=\""+this.layoutRatio+"\";\n");
            }
            if (this.shapes != null) {
                write(" ");
                write("node [label=\"\", fixedsize=true]\n");
            }
            for (RowIterator iter = this.graph.getVertexTable().iterator(); iter.hasNext(); ) {
                int vertex = iter.nextRow();
                write(" ");
                writeVertex(vertex);
                if (this.shapes != null) {
                    Shape s = (Shape)this.shapes.get(vertex);
                    if (s != null) {
                        double w = s.getBounds2D().getWidth() / 72;
                        write("[width=\""+w+"\",height=\""+w+"\"]");
                    }
                }
                write(";\n");
            }
            
            for (RowIterator iter = this.graph.getVertexTable().iterator(); iter.hasNext(); ) {
                int vertex = iter.nextRow();
                for (RowIterator eiter = this.graph.edgeIterator(vertex); eiter.hasNext(); ) {
                    int edge = eiter.nextRow();
                    write(" ");
                    writeEdge(edge);
                    write(";\n");
                }
            }
            write("}\n");
            this.out.flush();
        }
        catch(IOException e) {
            return false;
        }
        return true;
    }
    
    public ObjectColumn getShapes() {
        return this.shapes;
    }

    public void setShapes(ObjectColumn column) {
        this.shapes = column;
    }

    public Dimension getSize() {
        return this.size;
    }

    public void setSize(Dimension dimension) {
        this.size = dimension;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int s) {
        this.orientation = s;
    }

    public String getLayoutRatio() {
        return this.layoutRatio;
    }

    public void setLayoutRatio(String string) {
        this.layoutRatio = string;
    }

}
