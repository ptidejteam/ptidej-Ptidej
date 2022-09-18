/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.panel;

import infovis.Column;
import infovis.Table;
import infovis.column.filter.InternalFilter;

import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * Panel for configuring the labeling of a visualization.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class LabelPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Table table;
    FilteredColumnListModel model;
    JComboBox combo;
	
    public LabelPanel(Table table) {
	this.table = table;
	this.model = new FilteredColumnListModel(table);
	this.model.setFilter(InternalFilter.sharedInstance());
	this.combo = new JComboBox(this.model);
	add(this.combo);
    }
	
    public Column getSelectedColumn() {
	return (Column)this.model.getSelectedItem();
    }
	
    public void setSelectedColumn(Column column) {
	this.model.setSelectedItem(column);
    }
}
