package org.tigris.giant.persistance;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigPoly;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.tigris.giant.actions.OpenAction;
import org.tigris.giant.model.TargetNode;
import org.tigris.giant.ui.DependsFig;
import org.tigris.giant.ui.TargetFig;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;


public class GiantLoader {
    private static final Logger LOG = Logger.getLogger(OpenAction.class);
    
    public boolean load(File dir, File file) {
        String filename = file.getName();
        String path = dir.getPath();
        
        FileInputStream inputStream = null;
        try {
            int dotPosn = filename.lastIndexOf('.');
            if (dotPosn < 0) {
                filename += ".giant";
            } else {
                filename = filename.substring(0, dotPosn) + ".giant";
            }
            String fullname = path + filename;
        
            if (filename != null) {
                LOG.debug("Reading " + fullname + "...");
                file = new File(dir, filename);
                inputStream = new FileInputStream(file);
                InputSource inputSource = new InputSource(inputStream);
                DOMParser parser = new DOMParser();
                parser.parse(inputSource);
                Node doc = parser.getDocument();

                NodeList nodeList = doc.getChildNodes();
                int nodeCount = nodeList.getLength();
                
                for (int i=0; i < nodeCount; ++i) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        positionGraphNodes(node);
                    }
                }
                
                inputStream.close();
            }
        } catch (FileNotFoundException e1) {
            return false;
        } catch (SAXException e1) {
            e1.printStackTrace();
            return false;
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return true;
    }
    
    
    public void positionGraphNodes(Node projectXmlNode) {
        NodeList nodeList = projectXmlNode.getChildNodes();
        positionTargets(nodeList);
        positionDependencies(nodeList);
    }
    
    private void positionTargets(NodeList nodeList) {
        int nodeCount = nodeList.getLength();

        for (int i=0; i < nodeCount; ++i) {
            Node xmlNode = nodeList.item(i);
            if (xmlNode.getNodeType() == Node.ELEMENT_NODE
                    && xmlNode.getNodeName().equals("target")) {
                positionTarget(xmlNode);
            }
        }
    }
        
    private void positionDependencies(NodeList nodeList) {
        int nodeCount = nodeList.getLength();

        for (int i=0; i < nodeCount; ++i) {
            Node xmlNode = nodeList.item(i);
            if (xmlNode.getNodeType() == Node.ELEMENT_NODE
                    && xmlNode.getNodeName().equals("target")) {
                positionDependanciesForTarget(xmlNode);
            }
        }
    }
    
    /**
     * Position the target refered to in the given XML node
     * according to the co-ordinate data in that same node.
     * @param targetXmlNode
     */
    private void positionTarget(Node targetXmlNode) {
    
        NamedNodeMap xmlAttributeMap = targetXmlNode.getAttributes();
            
        String targetName = xmlAttributeMap.getNamedItem("name").getNodeValue();
        int x = Integer.parseInt(xmlAttributeMap.getNamedItem("x").getNodeValue());
        int y = Integer.parseInt(xmlAttributeMap.getNamedItem("y").getNodeValue());
        int width = Integer.parseInt(xmlAttributeMap.getNamedItem("width").getNodeValue());
        int height = Integer.parseInt(xmlAttributeMap.getNamedItem("height").getNodeValue());
        
        getTargetFig(targetName).setBounds(x, y, width, height);
    }

    /**
     * Get the target fig given the targets name.
     * @param targetName the name of the required target
     * @return the target fig or null if not found
     * TODO - this will be slow, need to put figs in a 
     * map indexed by name
     */
    private TargetFig getTargetFig(String targetName) {
        Editor editor = Globals.curEditor();
        Enumeration e = editor.figs();
        Fig f = null;
        while (e.hasMoreElements()) {
            f = (Fig)e.nextElement();
            if ((f instanceof TargetFig) 
                    && ((TargetNode)f.getOwner()).getName().equals(targetName)) {
                return (TargetFig)f;
            }
        }
        return null;
    }
    
    /**
     * Get the depends fig given the dependency name.
     * @param dependencyName the name of the required dependency
     * @return the dependency fig or null if not found
     * TODO - this will be slow, need to put figs in a 
     * map indexed by name
     */
    private DependsFig getDependsFig(String dependencyName) {
        Editor editor = Globals.curEditor();
        Enumeration e = editor.figs();
        Fig f = null;
        while (e.hasMoreElements()) {
            f = (Fig)e.nextElement();
            if ((f instanceof DependsFig) 
                    && ((DependsFig)f).getName().equals(dependencyName)) {
                return (DependsFig)f;
            }
        }
        return null;
    }
    
    /**
     * Position the dependencies of the target refered to in the
     * given XML node according to the co-ordinate data in its
     * sub-nodes
     * @param targetXmlNode
     */
    private void positionDependanciesForTarget(Node targetXmlNode) {
    
        NamedNodeMap xmlAttributeMap = targetXmlNode.getAttributes();
        String targetName = xmlAttributeMap.getNamedItem("name").getNodeValue();
        
        NodeList nodeList = targetXmlNode.getChildNodes();
            
        List dependsElements = getNamedElements(nodeList, "depends");
        
        int nodeCount = dependsElements.size();
        for (int i=0; i < nodeCount; ++i) {
            positionDependency(targetName, (Node)dependsElements.get(i));
        }
    }
    
    
    private void positionDependency(String targetName, Node dependsXmlNode) {
    
        NamedNodeMap xmlAttributeMap = dependsXmlNode.getAttributes();
        String dependsName = xmlAttributeMap.getNamedItem("name").getNodeValue();
        String points = xmlAttributeMap.getNamedItem("points").getNodeValue();
        
        DependsFig df = getDependsFig(targetName + "\t" + dependsName);
        
        Point startPoint = null;

        StringTokenizer st = new StringTokenizer(points, ";");
        for (int i=0; st.hasMoreElements(); ++i) {
            String pointString = st.nextToken().trim();
            if (!pointString.startsWith("(") || !pointString.endsWith(")")) {
                throw new IllegalArgumentException("points string invalid");
            }
            int commaPosn = pointString.indexOf(",");
            String xString = pointString.substring(1,commaPosn).trim();
            String yString = pointString.substring(commaPosn+1, pointString.length()-1).trim();
            int x = Integer.parseInt(xString);
            int y = Integer.parseInt(yString);
            if (i == 0) {
                startPoint = new Point(x, y);
            } else if (i == 1) {
                df.setEndPoints(startPoint, new Point(x, y));
            } else {
                df.insertPoint(i-1, x, y);
            }
            FigPoly fp = (FigPoly)df.getFig();
            fp.setComplete(true);
        }
        df.calcBounds();
    }
    

    /**
     * Get the nodes from the given NodeList which are elements of the
     * given name
     * @param nodeList the NodeList of DOM nodes
     * @param elementName the name of the required element nodes
     * @return a collection of matching nodes
     */        
    private List getNamedElements(NodeList nodeList, String elementName) {

        int nodeCount = nodeList.getLength();
        ArrayList matchList = new ArrayList();        

        for (int i=0; i < nodeCount; ++i) {
            Node xmlNode = nodeList.item(i);
            if (xmlNode.getNodeType() == Node.ELEMENT_NODE
                    && xmlNode.getNodeName().equals(elementName)) {
                matchList.add(xmlNode);
            }
        }
        
        return matchList;
    }
}