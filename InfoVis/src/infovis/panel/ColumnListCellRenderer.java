/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import infovis.Column;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;


/**
 * ListCellRendered for Columns.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ColumnListCellRenderer extends DefaultListCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(JList, Object, int, boolean, boolean)
     */
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus) {
        Column col = (Column)value;
        String newValue;
        if (col == null) {
            newValue = "(None)";
        } else {
            String colName = col.getName();
                //col.isInternal() ? "["+col.getName().substring(1)+"]" : col.getName();
            String type = col.getValueClass().getName();
            int    offset = type.lastIndexOf('.');
            if (offset != -1) {
                type = type.substring(offset + 1);
            }
            newValue = colName + " (" + type + ")";
        }
        return super.getListCellRendererComponent(list, newValue, index,
                                                  isSelected, cellHasFocus);
    }
}
