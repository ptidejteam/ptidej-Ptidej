// $Id: ZoomSlider.java 60 2006/07/11 00:00:00 tfmorris $
// Copyright (c) 2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.argoeclipse.internal.ui.util;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Globals;

import org.argouml.i18n.Translator;

/**
 * A dialog that can be used to change the zoom magnification of the current
 * diagram. It contains a horizontal slider representing the range of zoom
 * magnifications. Dragging the slider changes the zoom magnification for the
 * current diagram.
 * 
 * @author Tom Morris
 */
public class ZoomSlider extends PopupDialog {

    /**
     * The height of the slider.
     */
    private static final int SLIDER_HEIGHT = 15;

    /**
     * The window width.
     */
    private static final int WIDTH = 150;
    
    /**
     * The window height.
     */
    private static final int HEIGHT = 30;
    
    /**
     * Number of pixels below cursor to position popup.
     */
    private static final int VERTICAL_OFFSET = 12;
    
    /**
     * The minimum zoom magnification slider value.
     */
    private static final int MINIMUM_ZOOM = 5;

    /**
     * The maximum zoom magnification slider value.
     */
    private static final int MAXIMUM_ZOOM = 400;

    /**
     * The slider widget.
     */
    private Slider slider = null;

    /**
     * The text field which shows the current zoom magnification value and can
     * be used to enter new values.
     */
    private Text currentValue = null;

    /**
     * Composite which contains all the other widgets.
     */
    private Composite zoomComposite;

    /**
     * Our parent shell.
     */
    private Shell parentShell;
    
    /**
     * Construct a new zoom slider popup.
     * 
     * @param parent the shell this should be a child of
     */
    public ZoomSlider(Shell parent) {
        super(parent, INFOPOPUP_SHELLSTYLE, true, false, false, false,
                null, null);
        parentShell = parent;
    }

    /**
     * Creates the slider component.
     */
    private void createComponent(Shell parent) {
        parentShell = parent;

        zoomComposite = new Composite(parentShell, SWT.NO_TRIM );
        zoomComposite.setSize(WIDTH, HEIGHT);

        slider = new Slider(zoomComposite, SWT.HORIZONTAL);
        slider.setMinimum(MINIMUM_ZOOM);
        slider.setMaximum(MAXIMUM_ZOOM);
        slider.setLocation(0, 0);
        slider.setSize(WIDTH, SLIDER_HEIGHT);

        slider.setToolTipText(Translator.localize(
                "button.zoom.slider-tooltip")); //$NON-NLS-1$

        slider.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent event) {
                handleSliderValueChange(true);
            }

            public void widgetSelected(SelectionEvent event) {
                handleSliderValueChange(true);
            }
        });

        currentValue = new Text(zoomComposite, SWT.SINGLE | SWT.CENTER);
        currentValue.setToolTipText(Translator.localize(
                "button.zoom.current-zoom-magnification")); //$NON-NLS-1$
        updateCurrentValueLabel();
        currentValue.setSize(WIDTH, currentValue.getLineHeight());
        currentValue.setLocation(0, SLIDER_HEIGHT);
        
        currentValue.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent arg0) {
                // Handle Enter in text field
                handleTextEntry();
            }

            public void widgetSelected(SelectionEvent arg0) {
                // Should never be called
            }
        });

    }
  
    /*
     * @see org.eclipse.jface.dialogs.PopupDialog#getInitialLocation(org.eclipse.swt.graphics.Point)
     */
    protected Point getInitialLocation(Point initialSize) {
        Point cursor = parentShell.getDisplay().getCursorLocation();
        return new Point(cursor.x - (WIDTH / 2), cursor.y + VERTICAL_OFFSET);
    }
    
    /*
     * @see org.eclipse.jface.dialogs.PopupDialog#getInitialSize()
     */
    protected Point getInitialSize() {
        return new Point(WIDTH, HEIGHT);
    }
    
    /*
     * @see org.eclipse.jface.dialogs.PopupDialog#createContents(org.eclipse.swt.widgets.Composite)
     */
    protected Control createContents(Composite parent) {
        createComponent((Shell) parent);
        Editor ed = Globals.curEditor();
        if (ed != null) {
            slider.setSelection((int) (ed.getScale() * 100d));
            updateCurrentValueLabel();
        }

        return zoomComposite;
    }

    /**
     * Called when the slider value changes.
     * <p>
     * If this is called from handleTextEntry() then
     * updating the label generates a ModifyEvent and handleTextEntry()
     * is called again, who will call this again generating a loop.
     * We use a boolean value to know who called this to prevent the loop.
     * @param updateText if this is true we update the label
     */
    private void handleSliderValueChange(boolean updateText) {
        if (updateText) {
            updateCurrentValueLabel();
        }

        double zoomPercentage = slider.getSelection() / 100d;

        Editor ed = Globals.curEditor();
        if (ed == null || zoomPercentage <= 0.0) {
            return;
        }

        if (zoomPercentage != ed.getScale()) {
            ed.setScale(zoomPercentage);
            ed.damageAll();
        }

    }

    /**
     * Called when the text field value changes.
     */
    private void handleTextEntry() {        
        String value = currentValue.getText();
        if (value.endsWith("%")) { //$NON-NLS-1$
            value = value.substring(0, value.length() - 1);
        }
        try {
            int newZoom = Integer.parseInt(value);
            if (newZoom < MINIMUM_ZOOM) {
                newZoom = MINIMUM_ZOOM;
            }
            if (newZoom > MAXIMUM_ZOOM) {
                newZoom = MAXIMUM_ZOOM;
            }
            slider.setSelection(newZoom);            
            handleSliderValueChange(false);
        } catch (NumberFormatException ex) {
            getShell().getDisplay().beep();
        }
        updateCurrentValueLabel();
    }

    /**
     * Sets the current value label's text to the current slider value.
     */
    private void updateCurrentValueLabel() {
        currentValue.setText(String.valueOf(slider.getSelection()) + '%');
    }
}
