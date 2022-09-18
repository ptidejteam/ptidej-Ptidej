/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.visualization;

import infovis.Column;
import infovis.Graph;
import infovis.Table;
import infovis.column.FloatColumn;
import infovis.column.IntColumn;
import infovis.column.NumberColumn;
import infovis.column.ObjectColumn;
import infovis.column.StringColumn;
import infovis.column.filter.NotNumberFilter;
import infovis.graph.io.DOTGraphReader;
import infovis.graph.io.DOTGraphWriter;
import infovis.utils.ArrayChildrenIterator;
import infovis.utils.IntVector;
import infovis.utils.NullRowIterator;
import infovis.utils.Permutation;
import infovis.utils.RowComparator;
import infovis.utils.RowIterator;
import infovis.utils.Sort;
import infovis.visualization.ColorVisualization;
import infovis.visualization.DefaultVisualColumn;
import infovis.visualization.color.ColorVisualizationFactory;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class NodeLinkGraphVisualization
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NodeLinkGraphVisualization extends GraphVisualization {
    public static final String VISUAL_EDGE_SORT = "edgeSort";
    /** Name of the shape visual column */
    public static final String VISUAL_EDGE_SHAPE = "#edgeShape";
    public static final String VISUAL_EDGE_COLOR = "edgeColor";
    public static final String VISUAL_EDGE_WIDTH = "edgeWidth";
    public static final String SORTEDEDGES_COLUMN = "#sortedEdges";
    /** Column containing the link shapes */
    protected ObjectColumn linkShapes;
    protected String layoutProgram = "neato";
    protected String layoutRatio = null;
    protected StringColumn vertexPosition;
    protected FloatColumn vertexWidth;
    protected FloatColumn vertexHeight;
    protected StringColumn edgePosition;
    protected boolean paintingLinks = true;
    protected boolean debugging = true;
    protected transient Rectangle2D.Float bbox;
    protected transient float scale;
    protected Column edgeSortColumn;
    protected RowComparator edgeComparator;
    protected Permutation edgePermutation;
    protected ObjectColumn sortedEdges;
    /** The column used for edge color */
    protected Column edgeColorColumn;
    /** The edge ColorVisualization */
    protected ColorVisualization edgeColorVisualization;
    /** The edge default color */
    protected Color defaultEdgeColor = new Color(0, 0, 0, 0.5f);
    protected NumberColumn edgeWidthColumn;

    static {
        setControlPanelCreator(
            NodeLinkGraphVisualization.class,
            NodeLinkGraphControlPanel.class);
    }
    
    public NodeLinkGraphVisualization(Graph graph) {
        super(graph, graph.getVertexTable());
        this.linkShapes = new ObjectColumn(VISUAL_EDGE_SHAPE);
        this.sortedEdges =
            ObjectColumn.findColumn(graph.getEdgeTable(), SORTEDEDGES_COLUMN);
        setDefaultColor(VISUAL_COLOR, new Color(0, 0, 0.6f, 0.5f));
    }
    
    protected void declareVisualColumns() {
        super.declareVisualColumns();
        putVisualColumn(VISUAL_EDGE_COLOR,
            new DefaultVisualColumn(false) {
                public void setColumn(Column column) {
                    assert(column != null || getGraph().getEdgeTable().indexOf(column) != -1);
                    super.setColumn(column);
                    NodeLinkGraphVisualization.this.edgeColorColumn = column;
                    if (column != null) {
                        NodeLinkGraphVisualization.this.edgeColorVisualization =
                            ColorVisualizationFactory.createColorVisualization(column);
                    }
                    else {
                        NodeLinkGraphVisualization.this.edgeColorVisualization = null;
                    }
                }
        });
        putVisualColumn(VISUAL_EDGE_WIDTH,
            new DefaultVisualColumn(false, NotNumberFilter.sharedInstance()) {
                public void setColumn(Column column) {
                    super.setColumn(column);
                    NodeLinkGraphVisualization.this.edgeWidthColumn = (NumberColumn)column;
                }
        });
    }

    
    public void computeShapes(Rectangle2D bounds) {
        recomputeSizes();
        this.bbox = callGraphvizProgram();
        if (this.bbox == null)
            return;
        computeScale(bounds);
        this.vertexPosition = StringColumn.findColumn(getVertexTable(), DOTGraphReader.DOT_POS_COLUMN);
        this.vertexWidth = FloatColumn.findColumn(getVertexTable(), DOTGraphReader.DOT_WIDTH_COLUMN);
        this.vertexHeight = FloatColumn.findColumn(getVertexTable(), DOTGraphReader.DOT_HEIGHT_COLUMN);
        this.edgePosition = StringColumn.findColumn(getEdgeTable(), DOTGraphReader.DOT_POS_COLUMN);
        for (RowIterator iter = getVertexTable().iterator(); iter.hasNext(); ) {
            int v = iter.nextRow();
            parseVertexLayout(v);
            for (RowIterator eiter = edgeIterator(v); eiter.hasNext(); ) {
                int e = eiter.nextRow();
                parseEdgeLayout(e);
            }
        }
        this.bbox = null;  
    }
    
    public void paintShape(Graphics2D graphics, int vertex) {
        super.paintShape(graphics, vertex);
        for (RowIterator iter = edgeIterator(vertex); iter.hasNext(); ) {
            int edge= iter.nextRow();
            if (this.paintingLinks)
                paintLink(graphics, edge);
        }
    }
    
    public ColorVisualization getColorVisualization(String visualColor) {
        if (visualColor.equals(VISUAL_EDGE_COLOR)) {
            return this.edgeColorVisualization;
        }
        return super.getColorVisualization(visualColor);
    }

    public void setDefaultColor(
        String visualColor,
        Color defaultColor) {
        if (visualColor.equals(VISUAL_EDGE_COLOR)) {
            this.defaultEdgeColor = defaultColor;
        }
        else
            super.setDefaultColor(visualColor, defaultColor);
    }
    
    public Color getDefaultColor(String visualColor) {
        if (visualColor.equals(VISUAL_EDGE_COLOR)) {
            return this.defaultEdgeColor;
        }
        return super.getDefaultColor(visualColor);
    }


    
    public int getEdgeColorValue(int edge) {
        if (this.edgeColorVisualization == null) {
            return this.defaultEdgeColor.getRGB();
        }
        else {
            return this.edgeColorVisualization.getColorValue(edge);
        }
    }
    
    public Color getEdgeColorAt(int edge) {
        if (this.edgeColorVisualization == null) {
            return this.defaultEdgeColor;
        }
        else {
            return this.edgeColorVisualization.getColor(edge);
        }
    }
    
    public void setEdgeColorFor(Graphics2D graphics, int edge) {
        if (this.aglGraphics != null) {
            this.aglGraphics.setColor(getEdgeColorValue(edge));
        }
        else {
            graphics.setColor(getEdgeColorAt(edge));
        }
    }
    
    protected void paintLink(Graphics2D graphics, int edge) {
        if (this.paintingLinks) {
            Shape s = (Shape)this.linkShapes.getObjectAt(edge);
            if (s != null) {
                setEdgeColorFor(graphics, edge);
                graphics.draw(transformShape(s));
            }
        }
    }
    
    public String getLayoutProgram() {
        return this.layoutProgram;
    }
    
    public void setLayoutProgram(String program) {
        if (! this.layoutProgram.equals(program)) {
            this.layoutProgram = program;
            invalidate();
        }
    }
    
    public String getLayoutRatio() {
        return this.layoutRatio;
    }

    public void setLayoutRatio(String string) {
        if (this.layoutRatio != string &&
            (this.layoutRatio == null || !this.layoutRatio.equals(string))) {
            this.layoutRatio = string;
            invalidate();
        }
    }

    public boolean isPaintingLinks() {
        return this.paintingLinks;
    }

    public void setPaintingLinks(boolean b) {
        if (this.paintingLinks != b) {
            this.paintingLinks = b;
            repaint();
        }
    }

    public RowIterator edgeIterator(int vertex) {
        if (this.comparator != null) {
            int[] edges = (int[])this.sortedEdges.getObjectAt(vertex);
            if (edges == null) {
                return NullRowIterator.sharedInstance();
            }
            else {
                return new ArrayChildrenIterator(0, edges);
            }
        }
        return super.edgeIterator(vertex);
    }

    /**
     * Returns the edgeSortColumn.
     *
     * @return Column
     */
    public Column getEdgeSortColumn() {
        return this.edgeSortColumn;
    }
    
    public RowIterator vertexIterator() {
        return this.graph.vertexIterator();
    }
    

    /**
     * Sets the edgeSortColumn.
     *
     * @param sortColumn The sortColumn to set.
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setEdgeSortColumn(Column sortColumn) {
        if (changeManagedColumn(VISUAL_EDGE_SORT, this.edgeSortColumn, sortColumn)) {
            this.edgeSortColumn = sortColumn;
            this.edgeComparator = sortColumn;
            permuteEdgeRows();
            return true;
        }
        return false;
    }
    
    protected void permuteEdgeRows() {
        RowComparator comp = this.edgeComparator;
        if (comp == null) {
            this.edgePermutation = null;
            this.sortedEdges.clear();
            return;
        }
        Table table = getGraph().getEdgeTable();
        if (this.edgePermutation == null) {
            
            this.edgePermutation = new Permutation(
                IntColumn.findColumn(table, PERMUTATION_COLUMN),
                IntColumn.findColumn(table, INVERSEPERMUTATION_COLUMN),
                table.getRowCount());
        }
        this.edgePermutation.sort(table.getRowCount(), comp);
        IntVector edges = new IntVector();
        for (RowIterator iter = iterator(); iter.hasNext(); ) {
            int vertex = iter.nextRow();
            edges.clear();
            for (RowIterator eiter = edgeIterator(vertex); eiter.hasNext(); ) {
                int edge = eiter.nextRow();
                if (this.edgePermutation.getInverse(edge) != -1)
                    edges.push(edge);
            }
            if (edges.size() == 0) {
                this.sortedEdges.setValueUndefined(vertex, true);
            }
            else {
                int[] cTable = edges.toArray(null);
                if (cTable.length > 1)
                    Sort.sort(cTable, comp);
                this.sortedEdges.setExtend(vertex, cTable);   
            }
        }
    }
    
    protected void computeScale(Rectangle2D bounds) {
        double sx = bounds.getWidth() / this.bbox.getWidth();
        double sy = bounds.getHeight() / this.bbox.getHeight();
        
        this.scale = (float)Math.min(sx, sy);
    }
    
    protected float transformX(float x) {
        return (x - this.bbox.x) * this.scale;
    }
    
    protected float transformY(float y) {
        return (y - this.bbox.y) * this.scale;
    }
    
    protected void recomputeSizes() {
        for (RowIterator iter = iterator(); iter.hasNext(); ) {
            int v = iter.nextRow();
            
            Rectangle2D.Float rect = (Rectangle2D.Float)getShapeAt(v);
            if (rect == null) {
                rect = new Rectangle2D.Float();
                setShapeAt(v, rect);
            }
            double s = getSizeAt(v);
            rect.width = (float)s;
            rect.height = (float)s;
        }
    }
    
    protected Rectangle2D.Float callGraphvizProgram() {
        OutputStream out = null;
        InputStream in = null;

         try {
             Process proc = Runtime.getRuntime().exec(this.layoutProgram);
             out = proc.getOutputStream();
             in = proc.getInputStream();
         }
          catch (Exception ex) {
             System.err.println("Exception while setting up Process: " + 
                                ex.getMessage() + 
                                "\nTrying URLConnection...");
             out = null;
             in = null;
         }

         if (out== null) {
             try {
                 URLConnection urlConn = (new URL("http://www.research.att.com/~john/cgi-bin/format-graph")).openConnection();

                 urlConn.setDoInput(true);
                 urlConn.setDoOutput(true);
                 urlConn.setUseCaches(false);
                 urlConn.setRequestProperty("Content-Type", 
                                            "application/x-www-form-urlencoded");
                 out = urlConn.getOutputStream();
                 in = urlConn.getInputStream();
             }
              catch (Exception ex) {
                 System.err.println(
                         "Exception while setting up URLConnection: " + 
                         ex.getMessage() + 
                         "\nLayout not performed.");
                 out = null;
                 in = null;
             }
         }

         if (out == null || in == null)
             return null;
        Cursor saved = getParent().getCursor();
        getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
         try {
             StringWriter wout= new StringWriter();
    
             DOTGraphWriter writer = new DOTGraphWriter(wout, this);
             writer.setShapes(getShapes());
             writer.setSize(
                new Dimension(
                    (int)getBounds().getWidth(),
                    (int)getBounds().getHeight()));
             writer.setOrientation(getOrientation());
             writer.setLayoutRatio(this.layoutRatio);
             if (! writer.write())
                 return null;
             wout.flush();
             String content = wout.toString();
             wout.close();
             wout = null;
             writer = null;
        
             BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(out));
             bout.write(content);
             bout.flush();
             bout.close();
             bout = null;
             if (this.debugging) {
                 bout = new BufferedWriter(new FileWriter("debug.dot"));
                 bout.write(content);
                 bout.flush();
                 bout.close();
                 bout = null;
             }
            
             BufferedReader bin = new BufferedReader(new InputStreamReader(in));
             DOTGraphReader reader = new DOTGraphReader(bin, this.layoutProgram, this);
             reader.setUpdating(true);
             if (! reader.load())
                 return null;
             return reader.getBbox();
         }
         catch(IOException e) {
             return null;
         }
         finally {
             getParent().setCursor(saved);
         }
    }
    
    protected void parseVertexLayout(int v) {
        String pos = this.vertexPosition.get(v);
        if (pos == null) {
            setShapeAt(v, null);
            return;
        }
        Rectangle2D.Float rect = (Rectangle2D.Float)getShapeAt(v);
        if (rect == null) {
            rect = new Rectangle2D.Float();
            setShapeAt(v, rect);
        }
        rect.width = this.scale*this.vertexWidth.get(v)*72;
        rect.height = this.scale*this.vertexHeight.get(v)*72;
        int colonIndex = pos.indexOf(',');
        rect.x = transformX(Integer.parseInt(pos.substring(0, colonIndex))) - rect.width/2;
        rect.y = transformY(Integer.parseInt(pos.substring(colonIndex+1))) - rect.height/2;
    }
    
    protected int parsePoint(String pos, int index, Point p) {
        int i = pos.indexOf(',', index+1);
        if (i == -1)
            return -1;
        p.x = Integer.parseInt(pos.substring(index+1, i));
        index = pos.indexOf(' ', i+1);
        if (index == -1)
            p.y = Integer.parseInt(pos.substring(i+1));
        else 
            p.y = Integer.parseInt(pos.substring(i+1, index)); 
        return index;
    }
    
    protected void parseEdgeLayout(int e) {
        String pos = this.edgePosition.get(e);
        if (pos == null) {
            this.linkShapes.setValueUndefined(e, true);
            return;
        }
        GeneralPath p = (GeneralPath)this.linkShapes.getObjectAt(e);
        if (p == null) {
            p = new GeneralPath();
            this.linkShapes.setExtend(e, p);
        }
        else {
            p.reset();
        }
        
        int index = 0;
        Point pt = new Point();
        float x1 = 0, y1 = 0;
        float x2, y2, x3, y3, x4 = 0, y4 = 0;
        float arrow_x = 0, arrow_y = 0;

        if (pos.charAt(index)=='e' ||
            pos.charAt(index)=='s') {
            index = parsePoint(pos, 1, pt);
            arrow_x = transformX(pt.x);
            arrow_y = transformY(pt.y);
        }
        
        if (index == -1)
            return;
        index = parsePoint(pos, index, pt);
        x1 = transformX(pt.x);
        y1 = transformY(pt.y);
        p.moveTo(x1, y1);
        
        while (index != -1) {
            index = parsePoint(pos, index, pt);
            x2 = transformX(pt.x);
            y2 = transformY(pt.y);
            if (index != -1) {
                index = parsePoint(pos, index, pt);
                x3 = transformX(pt.x);
                y3 = transformY(pt.y);
            }
            else {
                x3 = x2;
                y3 = y2;
            }
            if (index != -1) {
                index = parsePoint(pos, index, pt);
                x4 = transformX(pt.x);
                y4 = transformY(pt.y);
            }
            else {
                x4 = x3;
                y4 = y3;
            }
            p.curveTo(x2, y2, x3, y3, x4, y4);
        }
        if (pos.charAt(0)=='e') {
            addArrow(x4, y4, arrow_x, arrow_y, p);
        }
        else if (pos.charAt(0)=='s') {
            addArrow(x1, y1, arrow_x, arrow_y, p);
        }
    }
    
    public void addArrow(float from_x, float from_y, float to_x, float to_y, GeneralPath p) {
        float dx = to_x - from_x;
        float dy = to_y - from_y;

        p.moveTo(to_x, to_y);
        p.lineTo(from_x + dy/2, from_y - dx/2);
        p.lineTo(from_x - dy/2, from_y + dx/2);
        p.closePath();        
    }

}
