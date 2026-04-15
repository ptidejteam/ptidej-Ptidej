/**
 *
    QuickUML; A simple UML tool that demonstrates one use of the 
    Java Diagram Package 

    Copyright (C) 2001  Eric Crahen <crahen@cse.buffalo.edu>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

 */

package uml.ui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

/**
 * @class NoteBorder
 *
 * @date 08-20-2001
 * @author Eric Crahen
 * @version 1.0
 *
 * Draws a line like border with a fold in the upper right corner.
 */
public class NoteBorder extends AbstractBorder {

  private int[] xPoints = new int[3];
  private int[] yPoints = new int[3];

  protected int thickness;
  protected int foldThickness;
  protected Color lineColor;
  protected boolean fillFold;

  /**
   * Create a NoteBorder of thickness 2 and foldThickness 6.
   */
  public NoteBorder() {
    this(2, 6, null, true);
  }

  /**
   * Create a new NoteBorder
   *
   * @param int
   * @param int
   * @param Color
   * @param boolean
   *
   * @post if the Color is null the darker color of the component background
   * will be used.
   */
  public NoteBorder(int thickness, int foldThickness, Color lineColor, boolean fillFold) {

    this.thickness = thickness;
    this.foldThickness = foldThickness;
    this.lineColor = lineColor;
    this.fillFold = fillFold;

  }

  /**
   * Returns the insets of the border.
   * @param c the component for which this border insets value applies
   */
  public Insets getBorderInsets(Component c)       {
    return new Insets(thickness, foldThickness, thickness, thickness);
  }

  /**
   * Reinitialize the insets parameter with this Border's current Insets.
   * @param c the component for which this border insets value applies
   * @param insets the object to be reinitialized
   */
  public Insets getBorderInsets(Component c, Insets insets) {
    insets.left = insets.right = insets.bottom = thickness;
    insets.top = foldThickness;
    return insets;
  }

  /**
   * Returns the color of the border.
   *
   * @return Color
   */
  public Color getLineColor() {
    return lineColor;
  }

  /**
   * Returns the thickness of the border.
   *
   * @return int
   */
  public int getThickness() {
    return thickness;
  }

  /**
   * Returns the thickness of the border.
   *
   * @return int
   */
  public int getFoldThickness() {
    return foldThickness;
  }

  /**
   * Paint the components border.
   *
   * @param Component
   * @param Graphics
   * @param int
   * @param int
   * @param int
   * @param int
   */
  public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

    // Set the border color
    g.setColor((lineColor == null) ? c.getBackground().darker() : lineColor);

    // Set up the polygon
    xPoints[0] = (x+w) - (foldThickness + thickness);
    xPoints[1] = xPoints[0];
    xPoints[2] = (x+w-1);

    yPoints[0] = y;
    yPoints[1] = y + foldThickness + (thickness - 1);
    yPoints[2] = yPoints[1];

    if(fillFold)
      g.fillPolygon(xPoints, yPoints, 3);

    int topX = (x + w) - (foldThickness + thickness) - 1;
    int topY = (y + foldThickness + thickness);

    // Line border
    for(int i = 0; i < thickness; i++)  {

      g.drawLine(x+i, y+i, x+i, y+h+i); // left
      g.drawLine(x+i, y+i, topX, y+i); // top

      // Draw the fold
      g.drawPolygon(xPoints, yPoints, 3);

      xPoints[0]--; xPoints[1]--;
      yPoints[1]++; yPoints[2]++;

      g.drawLine(x+w-i-1, topY, x+w-i-1, y+h-i-1);
      g.drawLine(x+i, y+h-i-1, w-i-1, h-i-1); // bottom

    }

  }

}
