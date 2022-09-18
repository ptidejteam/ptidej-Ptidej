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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractControlPanel extends Box
    implements ListDataListener, ActionListener, ChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Visualization visualization;
    Dimension    maxSliderDimension = new Dimension(Integer.MAX_VALUE, 30);

    /**
     * Constructor for AbstractControlPanel.
     * @param axis
     */
    public AbstractControlPanel(Visualization vis) {
        super(BoxLayout.Y_AXIS);
        this.visualization = vis;
    }
    
    public void setTitleBorder(JComponent comp, String title) {
        comp.setBorder(BorderFactory.createTitledBorder(title));
    }
    
    public void setMaximumSize(JComponent rs) {
        rs.setMaximumSize(this.maxSliderDimension);
    }

    /**
     * @see javax.swing.event.ListDataListener#contentsChanged(ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e) {
    }

    /**
     * @see javax.swing.event.ListDataListener#intervalAdded(ListDataEvent)
     */
    public void intervalAdded(ListDataEvent e) {
    }

    /**
     * @see javax.swing.event.ListDataListener#intervalRemoved(ListDataEvent)
     */
    public void intervalRemoved(ListDataEvent e) {
    }
    
    /**
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
    }

    /**
     * Returns the visualization.
     * @return the Visualization
     */
    public Visualization getVisualization() {
        return this.visualization;
    }

}
