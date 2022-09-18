/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.panel;

import infovis.Table;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 * ListModel for columns inside a table.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ColumnListModel extends AbstractListModel
    implements ComboBoxModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Table table;
    Object selectedObject;
    boolean nullAdded;

    /**
     * Constructor for ColumnListModel.
     */
    public ColumnListModel(Table table) {
	this.table = table;
    }

    /**
     * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index) {
	return this.table.getColumnAt(index);
    }

    /**
     * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
	return this.table.getColumnCount() + (this.nullAdded ? 1 : 0);
    }

    // implements javax.swing.ComboBoxModel
    /**
     * Sets the value of the selected item. The selected item may be null.
     * <p>
     * @param anObject The combo box value or null for no selection.
     */
    public void setSelectedItem(Object anObject) {
        if ((this.selectedObject != null && !this.selectedObject.equals( anObject )) ||
	    this.selectedObject == null && anObject != null) {
	    this.selectedObject = anObject;
	    fireContentsChanged(this, -1, -1);
        }
    }

    // implements javax.swing.ComboBoxModel
    public Object getSelectedItem() {
        return this.selectedObject;
    }
    /**
     * Returns the table.
     * @return Table
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Returns the nullAdded.
     * @return boolean
     */
    public boolean isNullAdded() {
        return this.nullAdded;
    }

    /**
     * Sets the nullAdded.
     * @param nullAdded The nullAdded to set
     */
    public void setNullAdded(boolean nullAdded) {
        if (this.nullAdded != nullAdded) {
            this.nullAdded = nullAdded;
            fireContentsChanged(this, -1, -1);
        }
    }

}
