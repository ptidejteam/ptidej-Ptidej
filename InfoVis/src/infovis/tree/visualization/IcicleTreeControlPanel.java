/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.tree.visualization;

import infovis.Column;
import infovis.column.ColumnFilter;
import infovis.column.NumberColumn;
import infovis.column.filter.ComposeOrFilter;
import infovis.metadata.AdditiveAggregation;
import infovis.panel.FilteredColumnListModel;
import infovis.panel.StdVisualPanel;

import javax.swing.JComponent;


/**
 * DOCUMENT ME!
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class IcicleTreeControlPanel extends TreeControlPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Creates a new TreemapControlPanel object.
     *
     * @param visualization DOCUMENT ME!
     */
    public IcicleTreeControlPanel(IcicleTreeVisualization visualization) {
        super(visualization);
    }

    /**
     * Returns the <code>TreemapVisualization</code>
     *
     * @return the <code>TreemapVisualization</code>
     */
    public IcicleTreeVisualization getIcicleTreeVisualization() {
        return (IcicleTreeVisualization)getVisualization();
    }

    /**
     * @see infovis.panel.ControlPanel#createStdVisualPane()
     */
    protected JComponent createStdVisualPane() {
        JComponent ret = super.createStdVisualPane();
        if (this.stdVisual instanceof StdVisualPanel) {
            StdVisualPanel          visual = (StdVisualPanel)this.stdVisual;

            FilteredColumnListModel sizeModel = visual.getSizeModel();
            sizeModel.setFilter(new ComposeOrFilter(sizeModel.getFilter(),
                                                    new ColumnFilter() {
                    public boolean filter(Column column) {
                        if (column instanceof NumberColumn) {
                            NumberColumn col = (NumberColumn)column;

                            return AdditiveAggregation.isAdditive(col,
                                                                          getTree()) == AdditiveAggregation.ADDITIVE_NO;
                        }
                        return false;
                    }
                }));
        }
        return ret;
    }
}
