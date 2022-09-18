/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization;

import infovis.Visualization;
import infovis.column.ColumnFilter;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class NodeLinkTreeVisualPanel
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NodeLinkTreeVisualPanel extends TreeVisualPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JSlider siblingSepSlider;
    protected JSlider subtreeSepSlider;
    protected JSlider levelSepSlider;
    
    public NodeLinkTreeVisualPanel(Visualization visualization, ColumnFilter filter) {
        super(visualization, filter);
    }
    
    public NodeLinkTreeVisualization getNodeLinkTreeVisualization() {
        return (NodeLinkTreeVisualization)getVisualization();
    }

    protected void createAll() {
        super.createAll();
        addParameterControls();
    }
    
    protected void addParameterControls() {
        this.siblingSepSlider = new JSlider(0, 50,
            (int)getNodeLinkTreeVisualization().getSiblingSeparation());
        this.siblingSepSlider.setPaintLabels(true);
        this.siblingSepSlider.setPaintTicks(true);
        this.siblingSepSlider.setMajorTickSpacing(10);
        this.siblingSepSlider.setMinorTickSpacing(5);
        this.siblingSepSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                getNodeLinkTreeVisualization().setSiblingSeparation(
                    NodeLinkTreeVisualPanel.this.siblingSepSlider.getValue());
                getVisualization().invalidate();
            }
        });
        setTitleBorder(this.siblingSepSlider, "Sibling Separator");
        add(this.siblingSepSlider);
        
        this.subtreeSepSlider = new JSlider(0, 100,
            (int)getNodeLinkTreeVisualization().getSubtreeSeparation());
        this.subtreeSepSlider.setPaintLabels(true);
        this.subtreeSepSlider.setPaintTicks(true);
        this.subtreeSepSlider.setMajorTickSpacing(50);
        this.subtreeSepSlider.setMinorTickSpacing(10);
        this.subtreeSepSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                getNodeLinkTreeVisualization().setSubtreeSeparation(
                    NodeLinkTreeVisualPanel.this.subtreeSepSlider.getValue());
                getVisualization().invalidate();
            }
        });
        setTitleBorder(this.subtreeSepSlider, "Subtree Separator");
        add(this.subtreeSepSlider);
        
        this.levelSepSlider = new JSlider(0, 100,
            (int)getNodeLinkTreeVisualization().getLevelSeparation());
        this.levelSepSlider.setPaintLabels(true);
        this.levelSepSlider.setPaintTicks(true);
        this.levelSepSlider.setMajorTickSpacing(50);
        this.levelSepSlider.setMinorTickSpacing(10);
        this.levelSepSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                getNodeLinkTreeVisualization().setLevelSeparation(
                    NodeLinkTreeVisualPanel.this.levelSepSlider.getValue());
                getVisualization().invalidate();
            }
        });
        setTitleBorder(this.levelSepSlider, "Level Separator");
        add(this.levelSepSlider);
    }
}
