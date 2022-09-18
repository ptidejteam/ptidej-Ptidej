/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import infovis.Visualization;
import infovis.visualization.Fisheyes;

import java.awt.event.ActionEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class FisheyesControlPanel extends AbstractControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Fisheyes fisheyes;
    JCheckBox enable;
    Box metrics;
    ButtonGroup metricsGroup;
    JRadioButton metricsL1;
    JRadioButton metricsL2;
    JRadioButton metricsLinf;
    JComboBox lens;
    JSlider maxScale;
    RangeSlider radius;
    JSlider tolerance;

    /**
     * Constructor for FisheyesControlPanel.
     * @param axis
     */
    public FisheyesControlPanel(Visualization vis) {
        super(vis);
        this.fisheyes = vis.getFisheyes();
        if (this.fisheyes == null) 
            this.fisheyes = new Fisheyes();
        
        this.enable = new JCheckBox("Enable Fisheyes");
        this.enable.setSelected(vis.getFisheyes() != null);
        this.enable.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (FisheyesControlPanel.this.enable.isSelected()) {
                    if (FisheyesControlPanel.this.fisheyes == null) {
                        FisheyesControlPanel.this.fisheyes = new Fisheyes();
                    }
                    getVisualization().setFisheyes(FisheyesControlPanel.this.fisheyes);
                    FisheyesControlPanel.this.metricsL1.setEnabled(true);
                    FisheyesControlPanel.this.metricsL2.setEnabled(true);
                    FisheyesControlPanel.this.metricsLinf.setEnabled(true);
                    FisheyesControlPanel.this.lens.setEnabled(true);
                    FisheyesControlPanel.this.maxScale.setEnabled(true);
                    FisheyesControlPanel.this.radius.setEnabled(true);
                    FisheyesControlPanel.this.tolerance.setEnabled(true);
                }
                else {
                    getVisualization().setFisheyes(null);
                    FisheyesControlPanel.this.metricsL1.setEnabled(false);
                    FisheyesControlPanel.this.metricsL2.setEnabled(false);
                    FisheyesControlPanel.this.metricsLinf.setEnabled(false);
                    FisheyesControlPanel.this.lens.setEnabled(false);
                    FisheyesControlPanel.this.maxScale.setEnabled(false);
                    FisheyesControlPanel.this.radius.setEnabled(false);
                    FisheyesControlPanel.this.tolerance.setEnabled(false);
                }
                getVisualization().repaint();
            }
        });
        add(this.enable);
        
        this.metrics = new Box(BoxLayout.X_AXIS);
        setTitleBorder(this.metrics, "Metrics");
        
        this.metricsGroup = new ButtonGroup();
        
        this.metricsL1 = new JRadioButton("L1");
        addMetrics(this.metricsL1);
        this.metricsL1.setSelected(this.fisheyes.getDistanceMetric() == Fisheyes.DISTANCE_L1);
        
        this.metricsL2 = new JRadioButton("L2");
        addMetrics(this.metricsL2);
        this.metricsL2.setSelected(this.fisheyes.getDistanceMetric() == Fisheyes.DISTANCE_L2);
        
        this.metricsLinf = new JRadioButton("Linf");
        addMetrics(this.metricsLinf);
        this.metricsLinf.setSelected(this.fisheyes.getDistanceMetric() == Fisheyes.DISTANCE_LINF);
        
        add(this.metrics);
        
        this.lens = new JComboBox(getLensTypes());
        this.lens.setSelectedIndex(this.fisheyes.getLensType());
        setTitleBorder(this.lens, "Lens Shape");
        setMaximumSize(this.lens);
        this.lens.addActionListener(this);
        add(this.lens);
        
        int scale = (int)Math.round(this.fisheyes.getMaximumScale());
        this.maxScale = new JSlider(SwingConstants.HORIZONTAL, 1,10,
            scale);
        this.maxScale.setMajorTickSpacing(1);
        this.maxScale.setPaintTicks(true);
        this.maxScale.setPaintLabels(true);
        setTitleBorder(this.maxScale, "Maximum Scale");
        this.maxScale.addChangeListener(this);
        add(this.maxScale);
        
        this.radius = new RangeSlider(0, 200,
            (int)this.fisheyes.getFocusRadius(), (int)this.fisheyes.getLensRadius());
        this.radius.getModel().addChangeListener(this);
    
        setMaximumSize(this.radius);
        setTitleBorder(this.radius, "Lens Radius");
        this.radius.setEnabled(true);
        add(this.radius);
        
        this.tolerance = new JSlider(SwingConstants.HORIZONTAL, 1, 10,
            (int)this.fisheyes.getTolerance());
        
        this.tolerance.setMajorTickSpacing(1);
        this.tolerance.setPaintTicks(true);
        this.tolerance.setPaintLabels(true);
        setTitleBorder(this.tolerance, "Shape Tolerance");
        this.tolerance.addChangeListener(this);
        add(this.tolerance);
    }
    
    protected void addMetrics(JRadioButton button) {
        String name = button.getName();
        button.setActionCommand(name);
        button.addActionListener(this);
        this.metricsGroup.add(button);
        this.metrics.add(button);
    }
    
    
    public String[] getLensTypes() {
        String[] str = { "Gaussian", "Cosine", "Hemisphere", "Linear" };
        return str;
    }
    
    /**
     * @see infovis.panel.AbstractControlPanel#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("L1")) {
            this.fisheyes.setDistanceMetric(Fisheyes.DISTANCE_L1);
            getVisualization().repaint();
        }
        else if (e.getActionCommand().equals("L2")) {
            this.fisheyes.setDistanceMetric(Fisheyes.DISTANCE_L2);
            getVisualization().repaint();
        }
        else if (e.getActionCommand().equals("Linf")) {
            this.fisheyes.setDistanceMetric(Fisheyes.DISTANCE_LINF);
            getVisualization().repaint();
        }
        else if (e.getSource() == this.lens) {
            this.fisheyes.setLensType((short)this.lens.getSelectedIndex());
            getVisualization().repaint();
        }
        else
            super.actionPerformed(e);
    }

    /**
     * @see infovis.panel.AbstractControlPanel#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.maxScale) {
            int scale = this.maxScale.getValue();
            this.fisheyes.setMaximumScale(scale);
            getVisualization().repaint();
        }
        else if (e.getSource() == this.radius.getModel()) {
            BoundedRangeModel model = this.radius.getModel();
            this.fisheyes.setRadii(model.getValue(), model.getValue()+model.getExtent());
            getVisualization().repaint();
        }
        else if (e.getSource() == this.tolerance) {
            int t = this.tolerance.getValue();
            this.fisheyes.setTolerance(t);
            getVisualization().repaint();
        }
        else
            super.stateChanged(e);
    }

}
