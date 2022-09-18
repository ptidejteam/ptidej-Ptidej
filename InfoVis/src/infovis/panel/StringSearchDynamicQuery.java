/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import infovis.Column;
import infovis.column.FilterColumn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Class StringSearchDynamicQuery
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class StringSearchDynamicQuery extends JTextField implements DynamicQuery {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Column column;
    protected FilterColumn filter;
    protected transient Pattern pattern; 
    
    public StringSearchDynamicQuery(Column col) {
        this.column = col;
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String regexp = e.getActionCommand();
                if (regexp.length() == 0)
                    StringSearchDynamicQuery.this.pattern = null;
                else
                    StringSearchDynamicQuery.this.pattern = Pattern.compile(regexp);
                apply();
            }
        });
    }

    public Column getColumn() {
        return this.column;
    }
    
    public void setFilterColumn(FilterColumn filter) {
        if (this.filter != null) {
            this.filter.removeDynamicQuery(this);
        }
        this.filter = filter;
        if (this.filter != null) {
            this.filter.addDynamicQuery(this);      
        }
    }

    public FilterColumn getFilterColumn() {
        return this.filter;
    }

    public void apply() {
        if (this.filter != null)
            this.filter.applyDynamicQuery(this, this.column.iterator());
    }


    public boolean isFiltered(int row) {
        if (this.pattern == null || this.column.isValueUndefined(row))
            return false;
        String v = this.column.getValueAt(row);
        Matcher m = this.pattern.matcher(v);
        return !m.matches();
    }

    

    public JComponent getComponent() {
        return this;
    }

}
