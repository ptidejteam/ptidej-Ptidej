/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel.color;

import infovis.Visualization;
import infovis.visualization.ColorVisualization;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultColorVisualizationControlPanel
	extends ColorVisualizationControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton button;
    
    /**
     * Creates a new OrderedColorControlPanel object.
     *
     * @param visualization the Visualization
     */
    public DefaultColorVisualizationControlPanel(Visualization visualization, final String visualColor) {
        super(visualization, visualColor);
        this.button = new JButton("Default Color");
        this.button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Color c = chooseColor(DefaultColorVisualizationControlPanel.this.button.getBackground());
                    if (! c.equals(DefaultColorVisualizationControlPanel.this.button.getBackground())) {
                        getVisualization().setDefaultColor(visualColor, c);
                        update();
                        getVisualization().repaint();
                    }
                }
            });
        add(this.button);

        update();
    }

    
    public void update() {
        this.button.setBackground(getVisualization().getDefaultColor(this.visualColor));
        this.button.setForeground(ColorVisualization.colorComplement(this.button.getBackground()));
        
    }

}
