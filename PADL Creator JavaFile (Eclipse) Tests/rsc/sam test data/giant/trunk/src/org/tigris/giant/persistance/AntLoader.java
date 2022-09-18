package org.tigris.giant.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.tigris.gef.base.Globals;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.graph.presentation.DefaultGraphModel;
import org.tigris.gef.graph.presentation.NetList;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.tigris.giant.model.DependsEdge;
import org.tigris.giant.model.TargetNode;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class AntLoader {
    public boolean load(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputSource inputSource = new InputSource(inputStream);
            DOMParser parser = new DOMParser();
            parser.parse(inputSource);
            Node doc = parser.getDocument();

            NodeList nodeList = doc.getChildNodes();
            int nodeCount = nodeList.getLength();
                
            for (int i=0; i < nodeCount; ++i) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    processProject(node);
                }
            }
            
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (SAXException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    
    public void processProject(Node projectXmlNode) {
        System.out.println("Processing project");
        NetList project = new NetList();
        DefaultGraphModel gm = (DefaultGraphModel) Globals.curEditor().getGraphModel();
        gm.removeAll();
    
        createTargets(projectXmlNode);
        createDepends(project, projectXmlNode);
    }

    private void createTargets(Node projectXmlNode) {
        NodeList nodeList = projectXmlNode.getChildNodes();
        int nodeCount = nodeList.getLength();
            
        for (int i=0; i < nodeCount; ++i) {
            Node xmlNode = nodeList.item(i);
            if (xmlNode.getNodeType() == Node.ELEMENT_NODE
                    && xmlNode.getNodeName().equals("target")) {
                processTarget(xmlNode);
            }
        }
    }

    private void createDepends(NetList project, Node projectXmlNode) {
        GraphModel gm = Globals.curEditor().getGraphModel();
        if(!(gm instanceof MutableGraphModel)) {
            return;
        }

        MutableGraphModel mgm = (MutableGraphModel)gm;
        List targetList = mgm.getNodes();
        int size = targetList.size();
        for (int i=0; i<size; ++i) {
            TargetNode targetNode = (TargetNode)targetList.get(i);
            String dependsList = targetNode.getDepends();
            if (dependsList != null) {
                StringTokenizer tokenizer = new StringTokenizer(dependsList,",");
                while (tokenizer.hasMoreTokens()) {
                    String depends = tokenizer.nextToken().trim();
                    TargetNode dependsNode = getTarget(project, depends);
                    DependsEdge dependsEdge = new DependsEdge();
                    dependsEdge.setName(targetNode.getName() + "\t" + dependsNode.getName());
                    dependsEdge.connect(mgm, targetNode.getPort(0), dependsNode.getPort(0));
                    mgm.addEdge(dependsEdge);
                }
            }
        }
    }

    // TODO This is slow, should store targets in a map for quick
    // reference.
    private TargetNode getTarget(NetList project, String name) {
        GraphModel gm = Globals.curEditor().getGraphModel();
        if(!(gm instanceof MutableGraphModel)) {
            return null;
        }

        MutableGraphModel mgm = (MutableGraphModel)gm;
        Iterator it = mgm.getNodes().iterator(); 
        while (it.hasNext()) {
            TargetNode targetNode = (TargetNode)it.next();
            if (targetNode.getName().equals(name)) return targetNode;
        }
        // If the target is not found then this target is assumed
        // to be in another file.
        TargetNode targetNode = new TargetNode();
        targetNode.setName(name);
        targetNode.setVirtual(true);
        
        if(mgm.canAddNode(targetNode)) {
            mgm.addNode(targetNode);
        }  
        return targetNode;
    }

    private void processTarget(Node targetXmlNode) {
    
        NamedNodeMap xmlAttributeMap = targetXmlNode.getAttributes();
            
        TargetNode targetNode = new TargetNode();
        targetNode.setName(xmlAttributeMap.getNamedItem("name").getNodeValue());
        if (xmlAttributeMap.getNamedItem("description") != null) {
            targetNode.setDescription(xmlAttributeMap.getNamedItem("description").getNodeValue());    
        }
        if (xmlAttributeMap.getNamedItem("if") != null) {
            targetNode.setIfCondition(xmlAttributeMap.getNamedItem("if").getNodeValue());    
        }
        if (xmlAttributeMap.getNamedItem("unless") != null) {
            targetNode.setUnlessCondition(xmlAttributeMap.getNamedItem("unless").getNodeValue());
        }
        if (xmlAttributeMap.getNamedItem("depends") != null) {
            targetNode.setDepends(xmlAttributeMap.getNamedItem("depends").getNodeValue());
        }

        GraphModel gm = Globals.curEditor().getGraphModel();
        if(!(gm instanceof MutableGraphModel)) {
            return;
        }

        MutableGraphModel mgm = (MutableGraphModel)gm;
        if(mgm.canAddNode(targetNode)) {
            mgm.addNode(targetNode);
        }  
    }
}