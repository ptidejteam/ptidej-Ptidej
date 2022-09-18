package org.tigris.giant;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.MutableGraphModel;
import org.tigris.gef.presentation.Fig;

import org.tigris.giant.model.DependsEdge;
import org.tigris.giant.model.DependsPort;
import org.tigris.giant.model.TargetNode;
import org.tigris.giant.ui.TargetFig;


public class Layouter {
    public void layout() {
        Editor editor = Globals.curEditor();
        GraphModel gm = editor.getGraphModel();
        if(!(gm instanceof MutableGraphModel)) {
            return;
        }
        MutableGraphModel mgm = (MutableGraphModel)gm;
        List unpositionedNodes = mgm.getNodes();
        List positionedNodes = new ArrayList();
        sizeNodes(unpositionedNodes);
    
        int x1 = 10;
        int y1 = 10;
        Enumeration e = editor.figs();
        Fig f = null;
        int height = 0;
        while (e.hasMoreElements()) {
            f = (Fig)e.nextElement();
            if (f instanceof TargetFig) {
                TargetNode tn = (TargetNode)f.getOwner();
                if (tn.getDepends() == null && tn.getPort(0).getEdges().size() > 0) {
                    height = f.getHeight() * 6;
                    positionedNodes.add(tn);
                    unpositionedNodes.remove(tn);
                    f.setLocation(x1, y1);
                    x1 += f.getWidth() + 70;
                }
            }
        }
        y1 += height;
        positionLevel2Nodes(positionedNodes, unpositionedNodes, 10, y1);
    }

    private void positionLevel2Nodes(
            List positionedNodes, 
            List unpositionedNodes, 
            int x1, 
            int y1) {

        x1 = 10;
        List nodesToPosition = getNodesToPosition(unpositionedNodes);
        if (nodesToPosition.isEmpty()) {
            positionStandaloneNodes(positionedNodes, unpositionedNodes, 10, y1);
            return;
        } 
    
        Editor editor = Globals.curEditor();
        GraphModel gm = editor.getGraphModel();
        if(!(gm instanceof MutableGraphModel)) {
            return;
        }
    
        Enumeration e = editor.figs();
        Fig f = null;
        int height = 0;
        while (e.hasMoreElements()) {
            f = (Fig)e.nextElement();
            if (f instanceof TargetFig) {
                TargetNode tn = (TargetNode)f.getOwner();
                if (tn.getDepends() != null && nodesToPosition.contains(tn)) {
                    height = f.getHeight() * 6;
                    positionedNodes.add(tn);
                    unpositionedNodes.remove(tn);
                    f.setLocation(x1, y1);
                    x1 += f.getWidth() + 70;
                }
            }
        }
        y1 += height;
        positionLevel2Nodes(positionedNodes, unpositionedNodes, 10, y1);
        //positionNodes(positionedNodes, unpositionedNodes, 10, y1);
    }

    private void positionNodes(
            List positionedNodes, 
            List unpositionedNodes, 
            int x1, 
            int y1) {
    
        Editor editor = Globals.curEditor();
        GraphModel gm = editor.getGraphModel();
        if(!(gm instanceof MutableGraphModel)) {
            return;
        }
    
        Enumeration e = editor.figs();
        Fig f = null;
        while (e.hasMoreElements()) {
            f = (Fig)e.nextElement();
            if (f instanceof TargetFig) {
                TargetNode tn = (TargetNode)f.getOwner();
                if (tn.getDepends() != null && unpositionedNodes.contains(tn)) {
                    positionedNodes.add(tn);
                    unpositionedNodes.remove(tn);
                    f.setLocation(x1, y1);
                    y1 += f.getHeight() * 2;
                }
            }
        }
        if (f != null) {
            y1 += f.getHeight() * 2;
        }
        positionStandaloneNodes(positionedNodes, unpositionedNodes, 10, y1);
    }

    private void positionStandaloneNodes(
            List positionedNodes, 
            List unpositionedNodes, 
            int x1, 
            int y1) {
        Editor editor = Globals.curEditor();
        GraphModel gm = editor.getGraphModel();
        if(!(gm instanceof MutableGraphModel)) {
            return;
        }

        Enumeration e = editor.figs();
        Fig f = null;
        while (e.hasMoreElements()) {
            f = (Fig)e.nextElement();
            if (f instanceof TargetFig) {
                TargetNode tn = (TargetNode)f.getOwner();
                if (unpositionedNodes.contains(tn)) {
                    positionedNodes.add(tn);
                    unpositionedNodes.remove(tn);
                    f.setLocation(x1, y1);
                    x1 += f.getWidth() + 70;
                }
            }
        }
    }

    /**
     * Determine which nodes to position by determing which nodes
     * have not already been positioned that are connected to a
     * node that has been positioned.
     * @param positionedNodes
     * @return a list of nodes to position
     */
    private List getNodesToPosition(List unpositionedNodes) {

        List nodesToPosition = new ArrayList();
            
        Iterator it = unpositionedNodes.iterator();
        while (it.hasNext()) {
            boolean addTarget = false;
            TargetNode tn = (TargetNode)it.next();
            Iterator edgeIter = tn.getPort(0).getEdges().iterator();
            while (edgeIter.hasNext() && !addTarget) {
                DependsEdge dependsEdge = (DependsEdge)edgeIter.next();
                DependsPort dependsPort = (DependsPort)dependsEdge.getDestPort();
                if (!unpositionedNodes.contains(dependsPort.getParentNode())) {
                    addTarget = true;
                }
            }
            if (addTarget) {
                nodesToPosition.add(tn);
            }
        }
        return nodesToPosition;
    }

    private void sizeNodes(List unpositionedNodes) {
        Editor editor = Globals.curEditor();
        GraphModel gm = editor.getGraphModel();
        if(!(gm instanceof MutableGraphModel)) {
            return;
        }

        Enumeration e = editor.figs();
        Fig f = null;
        while (e.hasMoreElements()) {
            f = (Fig)e.nextElement();
            if (f instanceof TargetFig) {
                TargetNode tn = (TargetNode)f.getOwner();
                int edgeCount = tn.getPort(0).getEdges().size();
                f.setWidth(50 + edgeCount*20);
            }
        }
    }
}