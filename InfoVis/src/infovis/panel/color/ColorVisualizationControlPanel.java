/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.panel.color;

import infovis.Visualization;
import infovis.visualization.ColorVisualization;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JColorChooser;
import javax.swing.JDialog;


/**
 * Base class for Color BasicVisualization control panels.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ColorVisualizationControlPanel extends Box {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Visualization visualization;
    protected String        visualColor;
    protected ColorVisualization colorVisualization;
    protected JDialog            colorDialog;
    protected JColorChooser      colorChooser;
    protected Color              colorChosen;
    ActionListener     cancelAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        }
    };
    ActionListener okAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ColorVisualizationControlPanel.this.colorChosen = ColorVisualizationControlPanel.this.colorChooser.getColor();
        }
    };

    /**
     * Creates a new ColorVisualizationControlPanel object.
     *
     * @param visualization the ColorVisualization.
     * @param layout the Box layout, could be <code>BoxLayout.X_AXIS</code>
     * or <code>BoxLayout.Y_AXIS</code>.
     */
    public ColorVisualizationControlPanel(Visualization visualization,
                                          String visualColor) {
        super(BoxLayout.X_AXIS);
        this.visualization = visualization;
        this.visualColor = visualColor;
        this.colorVisualization = visualization.getColorVisualization(visualColor);
    }

    /**
     * Returns the colorVisualization.
     * @return ColorVisualization
     */
    public ColorVisualization getColorVisualization() {
        return this.colorVisualization;
    }

    void createColorChooser() {
        if (this.colorChooser == null) {
            this.colorChooser = new JColorChooser(this.colorChosen);
            this.colorChooser.addChooserPanel(new DefaultAlphaChooserPanel());
            this.colorDialog = JColorChooser.createDialog(this, "Choose Color",
                                                     true, this.colorChooser,
                                                     this.okAction, this.cancelAction);
        }
    }

    /**
     * Returns the JColorChooser, creating it if necessary.
     *
     * @return the JColorChooser, creating it if necessary.
     */
    public JColorChooser getColorChooser() {
        createColorChooser();
        return this.colorChooser;
    }

    /**
     * Returns the JDialog containing the JColorChooser,
     * creating it if necessary.
     *
     * @return the JDialog containing the JColorChooser,
     * creating it if necessary.
     */
    public JDialog getColorDialog() {
        createColorChooser();
        return this.colorDialog;
    }

    /**
     * Trigger a color chooser to change a specified color.
     *
     * @param init the Color
     *
     * @return a color chooser to change a specified color.
     */
    public Color chooseColor(Color init) {
        this.colorChosen = init;
        getColorChooser().setColor(init);
        getColorDialog().setVisible(true);
        return this.colorChosen;
    }

    /**
     * Returns the visualization.
     * @return BasicVisualization
     */
    public Visualization getVisualization() {
        return this.visualization;
    }
}
