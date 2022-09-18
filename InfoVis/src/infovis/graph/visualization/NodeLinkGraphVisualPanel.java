/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.visualization;

import infovis.Visualization;
import infovis.column.ColumnFilter;
import infovis.panel.StdVisualPanel;
import infovis.panel.color.ColorPanel;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.event.ListDataEvent;

/**
 * Class NodeLinkGraphVisualPanel
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NodeLinkGraphVisualPanel extends StdVisualPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JComboBox layoutProgramCombo;
    protected JComboBox layoutRatioCombo;
    protected JCheckBox paintLinksButton;
    protected ColorPanel edgeColorPanel;
    
    public NodeLinkGraphVisualPanel(Visualization visualization, ColumnFilter filter) {
        super(visualization, filter);
    }
    
    public NodeLinkGraphVisualization getNodeLinkGraphVisualization() {
        return (NodeLinkGraphVisualization)getVisualization();
    }
    
    protected void createAll() {
        super.createAll();
        addOrientationButtons();
        addEdgeAttributes();
        addVisibilityButtons();
        addLayoutControls();
    }
    
    public Object[] getLayoutPrograms() {
        Object[] programs = {
            "dot",
            "neato",
            "twopi",
        };
        return programs;
    }
    
    protected void addEdgeAttributes() {
        this.edgeColorPanel = new ColorPanel(
            getVisualization(),
            NodeLinkGraphVisualization.VISUAL_EDGE_COLOR, "Color edge by");
        add(this.edgeColorPanel);
    }
    
    protected void addVisibilityButtons() {
        this.paintLinksButton = new JCheckBox("Paint Links");
        this.paintLinksButton.setSelected(
            getNodeLinkGraphVisualization().isPaintingLinks());
        this.paintLinksButton.addActionListener(this);
        add(this.paintLinksButton);
    }
    
    protected void addLayoutControls() {
        this.layoutProgramCombo = new JComboBox(getLayoutPrograms());
        this.layoutProgramCombo.setEditable(true);
        this.layoutProgramCombo.setSelectedItem(
            getNodeLinkGraphVisualization().getLayoutProgram());
        addJCombo("Layout program", this.layoutProgramCombo);
        Object[] ratios = {
            null,
            "fill",
            "compress",
            "auto"
        };
        this.layoutRatioCombo = new JComboBox(ratios);
        this.layoutRatioCombo.setEditable(true);
        this.layoutRatioCombo.setSelectedItem(
            getNodeLinkGraphVisualization().getLayoutRatio());
        addJCombo("Ratio options", this.layoutRatioCombo);
    }
    
    public void contentsChanged(ListDataEvent e) {
        if (e.getSource() == this.layoutProgramCombo.getModel()) {
            getNodeLinkGraphVisualization().setLayoutProgram(
                (String)this.layoutProgramCombo.getSelectedItem());
        }
        else if (e.getSource() == this.layoutRatioCombo.getModel()) {
            getNodeLinkGraphVisualization().setLayoutRatio(
                (String)this.layoutRatioCombo.getSelectedItem());
        }
        else
            super.contentsChanged(e);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.paintLinksButton) {
            getNodeLinkGraphVisualization().setPaintingLinks(
                this.paintLinksButton.isSelected());
            getVisualization().repaint();
        }
        else
            super.actionPerformed(e);
    }
    
}
