/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization;

import infovis.column.BooleanColumn;
import infovis.column.IntColumn;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * Interface LabeledComponent declares all the methods required by
 * ExcentricLabels. 
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface LabeledComponent {
    public int pickTop(double x, double y, Rectangle2D bounds);
    public BooleanColumn pickAll(Rectangle2D hitBox, Rectangle2D bounds,
                                     BooleanColumn pick);
    public IntColumn pickAll(Rectangle2D hitBox, Rectangle2D bounds,
                                 IntColumn pick);
    public JComponent getComponent();
    public String getLabelAt(int row);  
    public Shape getShapeAt(int row);
    public Color getColorAt(int row);     
    public void repaint();                               
}
