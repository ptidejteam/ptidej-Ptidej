package org.tigris.giant.model;

import java.awt.Color;
import java.io.Serializable;
import java.util.Hashtable;

import org.tigris.gef.base.Layer;
import org.tigris.gef.graph.GraphModel;
import org.tigris.gef.graph.presentation.NetNode;
import org.tigris.gef.presentation.FigNode;

import org.tigris.giant.ui.TargetFig;

public class TargetNode extends NetNode implements Serializable {

    private DependsPort dependsPort;
    
    private String name="";
    private String description="";
    private String depends="";
    private String ifCondition="";
    private String unlessCondition="";
    
    /**
     * A virtual TargetNode represents a target in a file other
     * than the file represented by the graph containing the fig.
     */
    private boolean virtual;

    public FigNode makePresentation(Layer lay) {
        
        TargetFig figNode = new TargetFig();
        dependsPort = new DependsPort(this);
        addPort(dependsPort);
        figNode.bindPort(dependsPort, figNode.getBoundryFig());
        figNode.setOwner(this);
        if (virtual) {
            figNode.getBoundryFig().setLineColor(Color.red);
        }
        return figNode;
    }
    
    public void setName(String name) {
        this.name = name;
        this.firePropertyChange("name", null, name);
    }
    
    public String getName() {
        return name;
    }

    public void setDepends(String depends) {
        this.depends = depends;
    }
    
    public String getDepends() {
        return depends;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public void setIfCondition(String ifCondition) {
        this.ifCondition = ifCondition;
    }
    
    public String getIfCondition() {
        return ifCondition;
    }

    public void setUnlessCondition(String unlessCondition) {
        this.unlessCondition = unlessCondition;
    }
    
    public String getUnlessCondition() {
        return unlessCondition;
    }

    /** Initialize a new TargetNode from the given hashtable of arguments.
     */

    public void initialize(Hashtable args) {
    }

    public String getId() {
        return getName();
    }
    
    /** Add the constraint that PortEther's can only be connected to
     * other ports of the same type. */
    public boolean canConnectTo(GraphModel gm, Object otherNode, Object otherPort, Object myPort) {
        return (super.canConnectTo(gm, otherNode, otherPort, myPort)) &&
            (otherNode.getClass() == this.getClass());
    }
    
    public String toString() {
        return name;
    }
    /**
     * @return
     */
    public boolean isVirtual() {
        return virtual;
    }

    /**
     * @param b
     */
    public void setVirtual(boolean b) {
        virtual = b;
    }

}
