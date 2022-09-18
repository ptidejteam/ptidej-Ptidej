/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel;

import javax.swing.event.ChangeListener;
/**
 * Double version of a <code>BoundedRangeModel</code>.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 *
 * @see javax.swing.BoundedRangeModel
 */
public interface DoubleBoundedRangeModel
{
    /**
     * Returns the minimum acceptable value.
     *
     * @return the value of the minimum property
     * @see #setMinimum
     */
    double getMinimum();


    /**
     * Sets the sizeModel's minimum to <I>newMinimum</I>.   The 
     * other three properties may be changed as well, to ensure 
     * that:
     * <pre>
     * minimum <= value <= value+extent <= maximum
     * </pre>
     * <p>
     * Notifies any listeners if the sizeModel changes.
     *
     * @param newMinimum the sizeModel's new minimum
     * @see #getMinimum
     * @see #addChangeListener
     */
    void setMinimum(double newMinimum);


    /**
     * Returns the sizeModel's maximum.  Note that the upper
     * limit on the sizeModel's value is (maximum - extent).
     *
     * @return the value of the maximum property.
     * @see #setMaximum
     * @see #setExtent
     */
    double getMaximum();


    /**
     * Sets the sizeModel's maximum to <I>newMaximum</I>. The other 
     * three properties may be changed as well, to ensure that
     * <pre>
     * minimum <= value <= value+extent <= maximum
     * </pre>
     * <p>
     * Notifies any listeners if the sizeModel changes.
     *
     * @param newMaximum the sizeModel's new maximum
     * @see #getMaximum
     * @see #addChangeListener
     */
    void setMaximum(double newMaximum);


    /**
     * Returns the sizeModel's current value.  Note that the upper
     * limit on the sizeModel's value is <code>maximum - extent</code> 
     * and the lower limit is <code>minimum</code>.
     *
     * @return  the sizeModel's value
     * @see     #setValue
     */
    double getValue();


    /**
     * Sets the sizeModel's current value to <code>newValue</code> if
     * <code>newValue</code> satisfies the sizeModel's
     * constraints.
     *
     * Those constraints are: <pre> minimum <= value <= value+extent
     * <= maximum </pre> Otherwise, if <code>newValue</code> is less
     * than <code>minimum</code> it's set to <code>minimum</code>, if
     * its greater than <code>maximum</code> then it's set to
     * <code>maximum</code>, and if it's greater than
     * <code>value+extent</code> then it's set to
     * <code>value+extent</code>.  <p> When a BoundedRange sizeModel
     * is used with a scrollbar the value specifies the origin of the
     * scrollbar knob (aka the "thumb" or "elevator").  The value
     * usually represents the origin of the visible part of the object
     * being scrolled.  <p> Notifies any listeners if the sizeModel
     * changes.
     *
     * @param newValue the sizeModel's new value
     * @see #getValue
     */
    void setValue(double newValue);


    /**
     * This attribute indicates that any upcoming changes to the value
     * of the sizeModel should be considered a single event.
     *
     * <p>This attribute will be set to true at the start of a series
     * of changes to the value, and will be set to false when the
     * value has finished changing.  Normally this allows a listener
     * to only take action when the final value change in committed,
     * instead of having to do updates for all intermediate values.
     * <p> Sliders and scrollbars use this property when a drag is
     * underway.
     * 
     * @param b true if the upcoming changes to the value property are part of a series
     */
    void setValueIsAdjusting(boolean b);


    /**
     * Returns true if the current changes to the value property are
     * part of a series of changes.
     * 
     * @return the valueIsAdjustingProperty.  
     * @see #setValueIsAdjusting
     */
    boolean getValueIsAdjusting();


    /**
     * Returns the sizeModel's extent, the length of the inner range
     * that begins at the sizeModel's value.
     *
     * @return  the value of the sizeModel's extent property
     * @see     #setExtent
     * @see     #setValue
     */
    double getExtent();


    /**
     * Sets the sizeModel's extent.  The <I>newExtent</I> is forced to
     * be greater than or equal to zero and less than or equal to
     * maximum - value.
     *
     * <p> When a BoundedRange sizeModel is used with a scrollbar the
     * extent defines the length of the scrollbar knob (aka the
     * "thumb" or "elevator").  The extent usually represents how much
     * of the object being scrolled is visible. When used with a
     * slider, the extent determines how much the value can "jump",
     * for example when the user presses PgUp or PgDn.  <p> Notifies
     * any listeners if the sizeModel changes.
     *
     * @param  newExtent the sizeModel's new extent
     * @see #getExtent
     * @see #setValue
     */
    void setExtent(double newExtent);



    /**
     * This method sets all of the sizeModel's data with a single
     * method call.
     *
     * The method results in a single change event being
     * generated. This is convenient when you need to adjust all the
     * sizeModel data simultaneously and do not want individual change
     * events to occur.
     *
     * @param value  an double giving the current value 
     * @param extent an double giving the amount by which the value can "jump"
     * @param min    an double giving the minimum value
     * @param max    an double giving the maximum value
     * @param isAdjusting a boolean, true if a series of changes are in
     *                    progress
     * 
     * @see #setValue
     * @see #setExtent
     * @see #setMinimum
     * @see #setMaximum
     * @see #setValueIsAdjusting
     */
    void setRangeProperties(double value, double extent, double min, double max, boolean adjusting);


    /**
     * Adds a ChangeListener to the sizeModel's listener list.
     *
     * @param x the ChangeListener to add
     * @see #removeChangeListener
     */
    void addChangeListener(ChangeListener x);


    /**
     * Removes a ChangeListener from the sizeModel's listener list.
     *
     * @param x the ChangeListener to remove
     * @see #addChangeListener
     */
    void removeChangeListener(ChangeListener x);

}
