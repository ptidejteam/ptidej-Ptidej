/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.panel;

import infovis.Visualization;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * Component managing a visualization.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class VisualizationPanel extends JComponent {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Visualization visualization;
    public VisualizationPanel(Visualization visualization) {
	this.visualization = visualization;
	visualization.setParent(this);
	setPreferredSize(new Dimension(500,500));
    }
	
    /**
     * @see javax.swing.JComponent#printComponent(Graphics)
     */
    protected void paintComponent(Graphics g) {
	Graphics2D g2 = (Graphics2D)g;
	this.visualization.paint(g2, getBounds());
    }

    /**
     * Returns the visualization.
     * @return Visualization
     */
    public Visualization getVisualization() {
	return this.visualization;
    }

    /**
     * Sets the visualization.
     * @param visualization The visualization to set
     */
    public void setVisualization(Visualization visualization) {
	this.visualization.setParent(null);
	this.visualization = visualization;
	this.visualization.setParent(this);
    }

    /**
     * @see javax.swing.JComponent#getToolTipText(MouseEvent)
     */
    public String getToolTipText(MouseEvent event) {
	if (this.visualization.getExcentric() != null) {
	    return null;
	}
	int row = this.visualization.pickTop(event.getX(), event.getY(), getBounds());
	if (row == -1)
	    return null;
	return this.visualization.getLabelAt(row);
    }

}
