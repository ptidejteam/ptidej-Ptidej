package org.tigris.giant.model;

import org.tigris.giant.ui.*;
import org.tigris.gef.base.*;
import org.tigris.gef.presentation.*;
import org.tigris.gef.graph.presentation.*;

/** A NetEdge subclass to represent a dependency between tagets.
 */

public class DependsEdge extends NetEdge {
    
    private String name;
    
    /** Construct a new Depends. */
    public DependsEdge() {
    }

    public String getId() {
        return toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FigEdge makePresentation(Layer lay) {
        return new DependsFig();
    }

} /* end class EdgeEther */
