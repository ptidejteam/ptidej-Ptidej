/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree.visualization.treemap;

import infovis.Visualization;
import infovis.column.ColumnFilter;
import infovis.tree.visualization.TreeVisualPanel;
import infovis.tree.visualization.TreemapVisualization;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.event.ListDataEvent;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TreemapVisualPanel extends TreeVisualPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComboBox algorithmCombo;

    /**
     * Constructor for TreemapVisualPanel.
     * @param visualization
     * @param filter
     */
    public TreemapVisualPanel(Visualization visualization, ColumnFilter filter) {
        super(visualization, filter);
    }
    
    public TreemapVisualization getTreemapVisualization() {
        return (TreemapVisualization)getVisualization();
    }
    
    /**
     * @see infovis.panel.StdVisualPanel#createAll(Visualization)
     */
    protected void createAll() {
        super.createAll();
        addAlgorithmCombo();
    }

    protected void addAlgorithmCombo() {
        this.algorithmCombo = new JComboBox();
        this.algorithmCombo.setRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
                Treemap treemap = (Treemap)value;
                return super.getListCellRendererComponent(list, treemap.getName(), index, isSelected, cellHasFocus);
            }
        });
        this.algorithmCombo.setBorder(BorderFactory.createTitledBorder("Treemap Algorithm"));
        this.algorithmCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                                           (int)this.algorithmCombo.getPreferredSize()
                                                     .getHeight()));
        this.algorithmCombo.getModel().addListDataListener(this);
        addAlgorithm(new SliceAndDice());
        addAlgorithm(new Squarified());
        addAlgorithm(new Strip());
        add(this.algorithmCombo);
    }
    
    protected void addAlgorithm(Treemap treemap) {
        DefaultComboBoxModel model = (DefaultComboBoxModel)this.algorithmCombo.getModel();
        
        model.addElement(treemap);
    }

    /**
     * @see infovis.panel.StdVisualPanel#contentsChanged(ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e) {
        if (e.getSource() == this.algorithmCombo.getModel()) {
            getTreemapVisualization().setTreemap((Treemap)this.algorithmCombo.getSelectedItem());
        }
        else
            super.contentsChanged(e);
    }

}
