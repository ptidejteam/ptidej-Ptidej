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
import infovis.column.NumberColumn;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <code>BoundedFloatModel</ocde> for <code>NumberColumn</code>s.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NumberColumnBoundedRangeModel
    extends DefaultDoubleBoundedRangeModel
    implements ChangeListener, DynamicQuery {
    protected NumberColumn column;
    protected FilterColumn filter;
    protected DoubleRangeSlider component;
	
    /**
     * Constructor from a NumberColumn
     */
    public NumberColumnBoundedRangeModel(NumberColumn column) {
	setColumn(column);	
    }

    public void stateChanged(ChangeEvent e) {
	if (e.getSource() == this.column) {
	    update();
	}
    }

    public void update() {
	setRangeProperties(this.column.getDoubleMin(),
			   this.column.getDoubleMax()-this.column.getDoubleMin(),
			   this.column.getDoubleMin(),
			   this.column.getDoubleMax(),
			   false);
    }
	
    public boolean isFiltered(int row) {
	if (this.column.isValueUndefined(row))
	    return false;
	double v = this.column.getDoubleAt(row);
        double min = this.column.round(getValue());
        double max = this.column.round(getValue()+getExtent());
	return ! (v >= min && v <= max);
    }

    /**
     * Returns the column.
     * @return Column
     */
    public Column getColumn() {
	return this.column;
    }

    /**
     * Sets the column.
     * @param column The column to set
     */
    public void setColumn(NumberColumn column) {
	if (column == this.column)
	    return;
	if (this.column != null)
	    this.column.removeChangeListener(this);
	this.column = column;
	if (this.column != null)
	    this.column.addChangeListener(this);
	update();
    }
    
    public FilterColumn getFilterColumn() {
        return this.filter;
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

    public void apply() {
	if (this.filter != null)
            this.filter.applyDynamicQuery(this, this.column.iterator());
    }

    protected void fireStateChanged() {
	super.fireStateChanged();
	apply();
    }

    public JComponent getComponent() {
	if (this.component == null) {
	    this.component = new DoubleRangeSlider(this);
	    this.component.setEnabled(true);
	}
	return this.component;
    }


}
