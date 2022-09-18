/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel.color;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class DefaultAlphaChooserPanel
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultAlphaChooserPanel
    extends AbstractColorChooserPanel
    implements ChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JSlider alphaSlider;
    protected JSpinner alphaField;
    protected boolean isAdjusting = false;

    public DefaultAlphaChooserPanel() {
    }
    
    private void setColor(Color newColor) {
        int alpha = newColor.getAlpha();
        
        if (this.alphaSlider.getValue() != alpha) {
            this.alphaSlider.setValue(alpha);
        }
        if (((Integer)this.alphaField.getValue()).intValue() != alpha) {
            this.alphaField.setValue(Integer.valueOf(alpha));
        }
    }

    public String getDisplayName() {
        String name = UIManager.getString("ColorChooser.alphaNameText");
        if (name == null)
            name = "Alpha";
        return name;
    }
    
    public int getMnemonic() {
        Object value = UIManager.get("ColorChooser.alphaMnemonic");
        if (value instanceof Integer) {
            return ((Integer)value).intValue();
        }
        if (value instanceof String) {
            try {
                return Integer.parseInt((String)value);
            } catch(NumberFormatException nfe) {
            }
        }
        return 'a';
    }

    public void updateChooser() {
        if (! this.isAdjusting) {
            this.isAdjusting = true;
            setColor(getColorFromModel());
            this.isAdjusting = false;
        }
    }

    protected void buildChooser() {
        setLayout(new BorderLayout());
        Color color = getColorFromModel();
        
        Box box = new Box(BoxLayout.X_AXIS);
        add(box, BorderLayout.CENTER);
        
        JLabel l = new JLabel("Alpha");
        box.add(l);
        this.alphaSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 255, color.getAlpha());
        this.alphaSlider.setMajorTickSpacing(85);
        this.alphaSlider.setMinorTickSpacing(17);
        this.alphaSlider.setPaintTicks(true);
        this.alphaSlider.setPaintLabels(true);
        box.add(this.alphaSlider);
        
        this.alphaField = new JSpinner(
            new SpinnerNumberModel(color.getAlpha(), 0, 255, 1));
        l.setLabelFor(this.alphaSlider);
        box.add(this.alphaField);
        this.alphaSlider.addChangeListener(this);
        this.alphaSlider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
    }

    public Icon getSmallDisplayIcon() {
        return null;
    }

    public Icon getLargeDisplayIcon() {
        return null;
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JSlider && ! this.isAdjusting) {
            int alpha = this.alphaSlider.getValue();
            Color orig = getColorFromModel();
            Color color = new Color(orig.getRed(), orig.getGreen(), orig.getBlue(), alpha);
            getColorSelectionModel().setSelectedColor(color);
        }
        else if (e.getSource() instanceof JSpinner && ! this.isAdjusting) {
            int alpha = ((Integer)this.alphaField.getValue()).intValue();
            Color orig = getColorFromModel();
            Color color = new Color(orig.getRed(), orig.getGreen(), orig.getBlue(), alpha);
            getColorSelectionModel().setSelectedColor(color);
        }
    }

}
