package org.tigris.giant.ui;

import org.tigris.gef.presentation.ArrowHead;
import org.tigris.gef.presentation.ArrowHeadGreater;
import org.tigris.gef.presentation.FigEdgePoly;

import org.tigris.giant.model.DependsEdge;

/**
 * @author Bob Tarling
 */
public class DependsFig extends FigEdgePoly {
    private ArrowHead destArrowHead = new ArrowHeadGreater();
    
    public DependsFig() {
        setBetweenNearestPoints(true);
        setDestArrowHead(destArrowHead);
    }
    
    public String getName() {
        return ((DependsEdge)getOwner()).getName();
    }
}
