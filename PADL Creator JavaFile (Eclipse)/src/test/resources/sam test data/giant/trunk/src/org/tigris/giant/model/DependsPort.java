package org.tigris.giant.model;

import java.io.*;

import org.apache.log4j.Logger;
import org.tigris.gef.graph.*;
import org.tigris.gef.graph.presentation.*;

/** A port for ethernet connections only. */

public class DependsPort extends NetPort implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(DependsPort.class);

    /** Construct a new DependsPort as a port of the given NetNode. This
     * example includes the constraint that PortEther's can only be
     * part of NodeLAN's. */
    
    public DependsPort(NetNode parent) {
	super(parent);
	if (!(parent instanceof TargetNode)) {
	    // throw an exception
	    LOG.error("DependencyPorts are only to be used on TargetNodes");
	}
    }
    
    public boolean isDragConnectable() {
        return false;
    }
    
    protected Class defaultEdgeClass(NetPort otherPort) {
        if (LOG.isDebugEnabled()) LOG.debug("Getting default edge for a DependsPort");
        return null;
    }

    /** Add the constraint that DependsPorts can only be connected to
     * other ports of the same type. */
    public boolean canConnectTo(GraphModel gm, Object anotherPort) {
	return (super.canConnectTo(gm, anotherPort)) &&
	    (anotherPort.getClass() == this.getClass());
    }
}
