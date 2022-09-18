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
import infovis.visualization.color.OrderedColor;

import java.util.ArrayList;


/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ColorVisualizationControlPanelFactory {
    static ColorVisualizationControlPanelFactory sharedInstance = new ColorVisualizationControlPanelFactory();
    ArrayList                                    creators = new ArrayList();

    /**
     * Creates a new ColorVisualizationControlPanelFactory object.
     */
    public ColorVisualizationControlPanelFactory() {
        addDefaultCreators();
    }

    /**
     * Adds the default creators.
     */
    protected void addDefaultCreators() {
        add(new Creator() {
                public ColorVisualizationControlPanel create(Visualization visualization, String visualColor) {
                    ColorVisualization colorVisualization = visualization.getColorVisualization(visualColor);
                    if (colorVisualization == null) {
                        return new DefaultColorVisualizationControlPanel(visualization, visualColor);
                    }
                    return null;
                }
            });
        add(new Creator() {
                public ColorVisualizationControlPanel create(Visualization visualization, String visualColor) {
                    ColorVisualization colorVisualization = visualization.getColorVisualization(visualColor);
                    if (colorVisualization instanceof OrderedColor)
                        return new OrderedColorControlPanel(visualization, visualColor);
                    return null;
                }
            });
    }

    static ColorVisualizationControlPanelFactory sharedInstance() {
        return sharedInstance;
    }

    static ColorVisualizationControlPanel createControlPanel(Visualization visualization, String visualColor) {
        return sharedInstance().create(visualization, visualColor);
    }

    /**
     * Creates a color visualization from a Visualization.
     *
     * @param visualization the Visualization.
     *
     * @return a color visualization from a Visualization.
     */
    public ColorVisualizationControlPanel create(Visualization visualization, String visualColor) {
        for (int i = 0; i < this.creators.size(); i++) {
            ColorVisualizationControlPanel panel = getCreatorAt(i).create(visualization, visualColor);
            if (panel != null)
                return panel;
        }
        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Creator getCreatorAt(int index) {
        return (Creator)this.creators.get(index);
    }

    /**
     * DOCUMENT ME!
     *
     * @param c DOCUMENT ME!
     */
    public void add(Creator c) {
        this.creators.add(c);
    }

    /**
     * DOCUMENT ME!
     *
     * @param c DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean remove(Creator c) {
        return this.creators.remove(c);
    }

    public interface Creator {
        ColorVisualizationControlPanel create(Visualization visualization, String visualColor);
    }
}
