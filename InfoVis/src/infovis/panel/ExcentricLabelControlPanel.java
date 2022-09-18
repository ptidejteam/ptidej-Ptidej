/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import infovis.Visualization;
import infovis.visualization.DefaultExcentricLabels;
import infovis.visualization.ExcentricLabels;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ExcentricLabelControlPanel extends AbstractControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ExcentricLabels excentricLabels;
    JCheckBox enable;
    JSlider radiusSlider;
    JSlider maxCountSlider;
    JCheckBox opaque;

    /**
     * Constructor for ExcentricLabelControlPanel.
     * @param axis
     */
    public ExcentricLabelControlPanel(Visualization vis) {
        super(vis);
        this.excentricLabels = vis.getExcentric();
        if (this.excentricLabels == null)
            this.excentricLabels = new DefaultExcentricLabels();
        
        this.enable = new JCheckBox("Enable Excentric Labels");
        this.enable.setSelected(vis.getExcentric() != null);
        this.enable.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (ExcentricLabelControlPanel.this.enable.isSelected()) {
                    getVisualization().setExcentric(ExcentricLabelControlPanel.this.excentricLabels);
                    ExcentricLabelControlPanel.this.radiusSlider.setEnabled(true);
                    ExcentricLabelControlPanel.this.maxCountSlider.setEnabled(true);
                    ExcentricLabelControlPanel.this.opaque.setEnabled(true);
                }
                else {
                    getVisualization().setExcentric(null);
                    ExcentricLabelControlPanel.this.radiusSlider.setEnabled(false);
                    ExcentricLabelControlPanel.this.maxCountSlider.setEnabled(false);
                    ExcentricLabelControlPanel.this.opaque.setEnabled(false);
                }
            }
        });
        add(this.enable);
        
        this.radiusSlider = new JSlider(1, 300);
        this.radiusSlider.setValue((int)(this.excentricLabels.getFocusSize()/2));
        this.radiusSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ExcentricLabelControlPanel.this.excentricLabels.setFocusSize(2*ExcentricLabelControlPanel.this.radiusSlider.getValue());
            }
        });
        this.radiusSlider.setBorder(BorderFactory.createTitledBorder("Radius"));
        add(this.radiusSlider);
        
        this.maxCountSlider = new JSlider(10, 100);
        this.maxCountSlider.setValue(this.excentricLabels.getMaxLabels());
        this.maxCountSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ExcentricLabelControlPanel.this.excentricLabels.setMaxLabels(ExcentricLabelControlPanel.this.maxCountSlider.getValue());
            }
        });
        this.maxCountSlider.setBorder(BorderFactory.createTitledBorder("Max Labels"));
        add(this.maxCountSlider);
        
        this.opaque = new JCheckBox("Opaque Labels");
        this.opaque.setSelected(this.excentricLabels.isOpaque());
        this.opaque.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ExcentricLabelControlPanel.this.excentricLabels.setOpaque(ExcentricLabelControlPanel.this.opaque.isSelected());
            }
        });
        add(this.opaque);
    }

}
