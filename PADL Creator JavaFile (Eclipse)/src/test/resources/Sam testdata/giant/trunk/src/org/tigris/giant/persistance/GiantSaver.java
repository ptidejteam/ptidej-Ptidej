package org.tigris.giant.persistance;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigEdge;
import org.tigris.giant.ui.DependsFig;
import org.tigris.giant.ui.TargetFig;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * 
 * @author Bob Tarling
 * @since 23-Feb-04
 */
public class GiantSaver {
    
    private static final Logger LOG = Logger.getLogger(GiantSaver.class);
    
    public void save(OutputStream out, Document doc) {
        try {
            Element root = doc.createElement("project");
            
            Editor editor = Globals.curEditor();
            Enumeration e = editor.figs();
            Fig f = null;
            while (e.hasMoreElements()) {
                f = (Fig)e.nextElement();
                if (f instanceof TargetFig) {
                    TargetFig tf = (TargetFig)f;
                    Element target = doc.createElement("target");
                    root.appendChild(target);
                    
                    Attr name = doc.createAttribute("name");
                    name.setNodeValue(tf.getName());
                    target.setAttributeNode(name);
                    
                    Attr x = doc.createAttribute("x");
                    x.setNodeValue(tf.getX()+"");
                    target.setAttributeNode(x);
                    
                    Attr y = doc.createAttribute("y");
                    y.setNodeValue(tf.getY()+"");
                    target.setAttributeNode(y);
                    
                    Attr width = doc.createAttribute("width");
                    width.setNodeValue(tf.getWidth()+"");
                    target.setAttributeNode(width);
                    
                    Attr height = doc.createAttribute("height");
                    height.setNodeValue(tf.getHeight()+"");
                    target.setAttributeNode(height);
                    
                    Iterator dependsIter = tf.getFigEdges(null).iterator();
                    while (dependsIter.hasNext()) {
                        DependsFig df = (DependsFig)dependsIter.next();
                        TargetFig dependsTarget = (TargetFig)df.getDestFigNode();
                        if (!dependsTarget.equals(tf)) {
                            Element depends = doc.createElement("depends");
                            target.appendChild(depends);
                    
                            Attr dname = doc.createAttribute("name");
                            dname.setNodeValue(dependsTarget.getName());
                            depends.setAttributeNode(dname);
                            
                            System.out.println("Finding points for " + tf.getName() + " to " + dependsTarget.getName());
                            
                            Attr points = doc.createAttribute("points");
                            points.setNodeValue(getPointsString(df));
                            depends.setAttributeNode(points);
                        }
                    }
                }
            }
            
            doc.appendChild(root);
            
            OutputFormat of = new OutputFormat();
            of.setIndenting(true);
            XMLSerializer xs = new XMLSerializer(out, of);
            xs.serialize(doc);
        } catch (IOException e) {
            LOG.error("got a ParseConfigurationException ", e);
        }
    }

    /**
     * Create a string of all the points making up the edge.
     * This is formated as (x1,y1);(x2,y2);(x3,y3)
     * @param edge
     * @return A string representation of the edge points
     */    
    private String getPointsString(FigEdge edge) {
        String pointsString = "";
                            
        int[] xs = edge.getFig().getXs();
        int[] ys = edge.getFig().getYs();
        int pointCount = edge.getNumPoints();
        for (int i=0; i < pointCount; ++i) {
            if (pointsString.length() > 0) {
                pointsString += ";";
            }
            pointsString += "(" + xs[i] + "," + ys[i] + ")";
        }
        return pointsString;
    }
}
