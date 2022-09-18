/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;


/**
 * Defaut implementation of BoundedRangeModel for double
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultDoubleBoundedRangeModel implements DoubleBoundedRangeModel {
    protected transient ChangeEvent changeEvent;
    protected EventListenerList     listenerList = new EventListenerList();
    private double				    value = 0;
    private double				    extent = 0;
    private double				    min = 0;
    private double				    max = 100;
    private boolean				    isAdjusting = false;

    /**
     * Creates a new DefaultDoubleBoundedRangeModel object.
     */
    public DefaultDoubleBoundedRangeModel() {
    }

    /**
     * Creates a new DefaultDoubleBoundedRangeModel object.
     * 
     * Maintains min &lt;= value &lt;= (value+extent) &lt;= max
     *
     * @param value the current value
     * @param extent the current extent
     * @param min the minimum value
     * @param max the maximum value
     */
    public DefaultDoubleBoundedRangeModel(double value, double extent,
					  double min, double max) {
	if ((max >= min) && (value >= min) && (value + extent) >= value &&
	    (value + extent) <= max) {
	    this.value = value;
	    this.extent = extent;
	    this.min = min;
	    this.max = max;
	} else {
	    throw new IllegalArgumentException("invalid range properties");
	}
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#getValue()
     */
    public double getValue() {
	return this.value;
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#getExtent()
     */
    public double getExtent() {
	return this.extent;
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#getMinimum()
     */
    public double getMinimum() {
	return this.min;
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#getMaximum()
     */
    public double getMaximum() {
	return this.max;
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#setValue(double)
     */
    public void setValue(double n) {
	double newValue = Math.max(n, this.min);
	if ((newValue + this.extent) > this.max) {
	    newValue = this.max - this.extent;
	}
	setRangeProperties(newValue, this.extent, this.min, this.max, this.isAdjusting);
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#setExtent(double)
     */
    public void setExtent(double n) {
	double newExtent = Math.max(0, n);
	if ((this.value + newExtent) > this.max) {
	    newExtent = this.max - this.value;
	}
	setRangeProperties(this.value, newExtent, this.min, this.max, this.isAdjusting);
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#setMinimum(double)
     */
    public void setMinimum(double n) {
	double newMax = Math.max(n, this.max);
	double newValue = Math.max(n, this.value);
	double newExtent = Math.min(newMax - newValue, this.extent);
	setRangeProperties(newValue, newExtent, n, newMax, this.isAdjusting);
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#setMaximum(double)
     */
    public void setMaximum(double n) {
	double newMin = Math.min(n, this.min);
	double newExtent = Math.min(n - newMin, this.extent);
	double newValue = Math.min(n - newExtent, this.value);
	setRangeProperties(newValue, newExtent, newMin, n, this.isAdjusting);
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#setValueIsAdjusting(boolean)
     */
    public void setValueIsAdjusting(boolean b) {
	setRangeProperties(this.value, this.extent, this.min, this.max, b);
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#getValueIsAdjusting()
     */
    public boolean getValueIsAdjusting() {
	return this.isAdjusting;
    }

    /**
     * Sets all of the <code>DoubleBoundedRangeModel</code> properties after forcing
     * the arguments to obey the usual constraints:
     * <pre>
     *     minimum <= value <= value+extent <= maximum
     * </pre>
     * <p>
     * At most, one <code>ChangeEvent</code> is generated.
     *
     * @see DoubleBoundedRangeModel#setRangeProperties(double, double, double, double, boolean)
     * @see #setValue
     * @see #setExtent
     * @see #setMinimum
     * @see #setMaximum
     * @see #setValueIsAdjusting
     */
    public void setRangeProperties(double newValue, double newExtent,
				   double newMin, double newMax,
				   boolean adjusting) {
	if (newMin > newMax) {
	    newMin = newMax;
	}
	if (newValue > newMax) {
	    newMax = newValue;
	}
	if (newValue < newMin) {
	    newMin = newValue;
	}
	if ((newExtent + newValue) > newMax) {
	    newExtent = newMax - newValue;
	}
	if (newExtent < 0) {
	    newExtent = 0;
	}
	boolean isChange = (newValue != this.value) ||
            (newExtent != this.extent) ||
	    (newMin != this.min) ||
            (newMax != this.max) ||
	    (adjusting != this.isAdjusting);

	if (isChange) {
	    this.value = newValue;
	    this.extent = newExtent;
	    this.min = newMin;
	    this.max = newMax;
	    this.isAdjusting = adjusting;

	    fireStateChanged();
	}
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#addChangeListener(ChangeListener)
     */
    public void addChangeListener(ChangeListener l) {
	this.listenerList.add(ChangeListener.class, l);
    }

    /**
     * @see infovis.panel.DoubleBoundedRangeModel#removeChangeListener(ChangeListener)
     */
    public void removeChangeListener(ChangeListener l) {
	this.listenerList.remove(ChangeListener.class, l);
    }

    /**
     * Returns an array of all the change listeners
     * registered on this <code>DefaultDoubleBoundedRangeModel</code>.
     *
     * @return all of this sizeModel's <code>ChangeListener</code>s
     *         or an empty
     *         array if no change listeners are currently registered
     *
     * @see #addChangeListener
     * @see #removeChangeListener
     */
    public ChangeListener[] getChangeListeners() {
	return (ChangeListener[])this.listenerList.getListeners(ChangeListener.class);
    }

    /**
     * Runs each <code>ChangeListener</code>'s <code>stateChanged</code> method.
     *
     * @see #setRangeProperties
     * @see EventListenerList
     */
    protected void fireStateChanged() {
	Object[] listeners = this.listenerList.getListenerList();
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	    if (listeners[i] == ChangeListener.class) {
		if (this.changeEvent == null) {
		    this.changeEvent = new ChangeEvent(this);
		}
		((ChangeListener)listeners[i + 1]).stateChanged(this.changeEvent);
	    }
	}
    }
}
